<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>商户管理</title>
    <meta charset="utf-8">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/css/trip.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
    <%--表格样式--%>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-table/bootstrap-table.css">
    <link rel="stylesheet" href="<%=basePath%>/static/font-awesome/css/font-awesome.min.css">
    <%--表格JS--%>
    <script src="<%=basePath%>/static/bootstrap/bootstrap-table/bootstrap-table.js"></script>
    <%--语言包--%>
    <script src="<%=basePath%>/static/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <%--提示框--%>
    <script src="<%=basePath%>/static/js/jquery.noty.packaged.min.js"></script>
    <script src="<%=basePath%>/static/js/showinfo.js"></script>
    <%--自建公共js文件--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/common-creat.js"></script>
    <style>
        .modal-title{
            font-size: 18px;
            color: #337AB7;
            font-weight: 700;
            height:20px;
            line-height: 20px;
        }
        .modal-body{
            padding:15px 40px;
        }
        .form-group label{
            font-weight: normal;
        }
        ul.ztree{
            max-height: 152px;
            max-width: 150px;
        }
        #toolbar>button.btn-info{
            background-color:#58ABD1;
        }
        #toolbar>button.btn-info:hover{
            background-color: #3092B8;
        }
    </style>
</head>
<script >

    var statu=1;
    var status="";
    var grade="";
    var merchname="";
    //初始化表格
    function initTable() {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/merchant/getMerchantList.do", //获取数据的Servlet地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            toolbar:"#toolbar",
            showRefresh: true,
            pageSize: 10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            exportDataType : "all",
            clickToSelect:true,
            search:false,
            idField:"uid",
            sidePagination: "server", //表示服务端请求
            queryParamsType : "limit",
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    limit: params.limit,
                    offset: params.offset,
                    statu:statu,
                    status:status,
                    grade:grade,
                    merchname:merchname
                };
                return param;
            },
            onLoadSuccess: function(){  //加载成功时执行
                $('#cusTable').bootstrapTable('hideColumn', 'statu');
            },
            onLoadError: function(){  //加载失败时执行
            }
        });
    }

    /**
     * 初始化数据
     */
    $(document).ready(function () {
        //调用函数，初始化表格
        initTable();
    });
    //添加操作按钮
    function nameFormatter(value, row, index) {
        var id = row.uids;
        if(row.statu == 1){
            return '<i><a href="javascript:;" onclick="examine(\'' + id + '\')">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="frozen(\'' + id + '\')">冻结</a></i>'
        }else if(row.statu == 2){
            return '<i><a href="javascript:;" onclick="examine(\'' + id + '\')">查看</a></i>'
        }else{
            return '<i><a href="javascript:;" onclick="thaw(\'' + id + '\')">解冻</a></i>'
        }

    }
    //查看店铺明细
    function examine(id){
        var statu="2";
        var name = "商户信息明细";
        var url = "/merchant/toExamineMerchantList.do?id=" + id+"&statu="+statu;
        window.parent.addTabs(id, name, url);
    }
    //冻结
    function frozen(uid){
        var statu="3";
        $.ajax({
            url:"<%=basePath%>/merchant/examineOrFrozen.do",
            type:"POST",
            data:{
                uid:uid,
                statu:statu
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("冻结成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("冻结失败");
                }
            }
        });
    }
    //解冻
    function thaw(uid) {
        var statu = "1";
        $.ajax({
            url: "<%=basePath%>/merchant/examineOrFrozen.do",
            type: "POST",
            data: {
                uid: uid,
                statu: statu
            },
            success: function (data) {
                if (data !== "failed") {
                    successInfo("解冻成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                } else {
                    errorInfo("解冻失败");
                }
            }
        });
    }
    //查询
    function selectDate(){
        statu=2;
        status=$("#statu").val();
        grade=$("#grade").val();
        merchname=$("#merchname").val();
        initTable();
    }
</script>
<body id="loading" class="style_body">
<div class=" style_border">
    <div id="toolbar" class="btn-group-sm">
        <form style="margin-left: -20px" class="form-horizontal">
            <div>
                <label style="width: 70px" class="control-label col-md-1">状态:</label>
                <div style="width: 150px;margin-top: 7px;margin-left: -20px" class=" col-md-2">
                    <select style="" class="form-control" id="statu">
                        <option value="0" class="form-control">全部</option>
                        <option value="1" class="form-control">通过</option>
                        <option value="2" class="form-control">驳回</option>
                        <option value="3" class="form-control">冻结</option>
                    </select>
                </div>
                <label style="width: 70px;margin-left: -18px" class="control-label col-md-1">等级:</label>
                <div style="width: 150px;margin-top: 7px;margin-left: -20px" class=" col-md-2">
                    <select style="" class="form-control" id="grade">
                        <option value="0" class="form-control">全部</option>
                        <option value="1" class="form-control">初级</option>
                        <option value="2" class="form-control">中级</option>
                        <option value="3" class="form-control">高级</option>
                        <option value="4" class="form-control">特级</option>
                    </select>
                </div>
                <label style="width: 70px;margin-left: -18px" class="control-label col-md-1">名称:</label>
                <div style="width: 150px;margin-top: 7px;margin-left: -20px" class=" col-md-2">
                    <input style="" class="form-control" id="merchname"/>
                </div>
            </div>
        </form>
        <button style="margin-top: -31px;margin-left: 545px" class="btn btn-info"  id="add" onclick="selectDate()">
            <i class="glyphicon glyphicon-expand"></i> 查询
        </button>
    </div>
    <table id="cusTable" class="table">
        <thead>
        <tr>
            <th data-field="uid" data-checkbox="true" align="center"></th>
            <th data-field="merchname" data-editable="false"  align="center">商铺名称</th>
            <th data-field="userinfouid" data-editable="false" align="center">会员名称</th>
            <th data-field="bondvalue" data-editable="false" align="center">保证金金额</th>
            <th data-field="builddatetime" data-editable="false" align="center">创建时间</th>
            <th data-field="merchscroe" data-editable="false" align="center">店铺等级</th>
            <th data-field="status" data-editable="false" align="center">状态</th>
            <th data-field="statu" data-editable="false" align="center"></th>
            <th data-field="uids" data-formatter="nameFormatter">操作</th>
        </tr>
        </thead>
    </table>
    </div>

<script src="<%=basePath%>/static/bootstrap/js/bootstrap.min.js"></script>
<%--模态弹窗引用jquery-ui设置可拖动--%>
<script src="<%=basePath%>/static/js/jquery-ui.min.js"></script>
<script>
    $(document).ready(function(){
        $(".modal-content").draggable({ cursor: "move" });//为模态对话框添加拖拽
    })
</script>
</body>