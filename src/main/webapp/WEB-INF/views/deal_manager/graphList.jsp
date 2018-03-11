<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>求图管理</title>
    <meta charset="utf-8">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/css/trip.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
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
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/language/zh_CN.js"></script>
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
        #toolbar>button.btn-info{
            background-color:#337ab7;
            border-color: #2e6da4;
        }
        #toolbar>button.btn-info:hover{
            background-color: #286090;
        }
    </style>
</head>
<script >
    //初始化表格
    function initTable(flag) {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/graph/getGraphList.do", //获取数据的Servlet地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            toolbar:"#toolbar",
            showRefresh: true,
            pageSize: 10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            exportDataType : "all",
            clickToSelect:false,
            idField:"uid",
            sidePagination: "server", //表示服务端请求
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType : "limit",
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    limit: params.limit,
                    offset: params.offset,
                    flag:flag
                };
                return param;
            },
            onLoadSuccess: function(){  //加载成功时执行

            },
            onLoadError: function(){  //加载失败时执行

            }
        });
    }
    /**
     * 初始化
     */
    $(document).ready(function () {
        //调用函数，初始化表格
        initTable(0);
    });
    //添加操作按钮
    function nameFormatter(value, row, index) {
        var uid = row.uid;
        return '<i><a href="javascript:;" onclick="adopt(\'' + uid + '\')">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="reject(\'' + uid + '\')">驳回</a></i>'
    }
    //通过
    function adopt(uid){
        var statu="2";
        $.ajax({
            url:"<%=basePath%>/graph/examineGraph.do",
            type:"POST",
            data:{
                uid:uid,
                statu:statu
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("审核成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("审核失败");
                }
            }
        });
    }
    //驳回
    function reject(uid){
        var statu="1";
        $.ajax({
            url:"<%=basePath%>/graph/examineGraph.do",
            type:"POST",
            data:{
                uid:uid,
                statu:statu
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("审核成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("审核失败");
                }
            }
        });
    }
</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div>
        <div id="toolbar" class="btn-group-sm">
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="username"  data-editable="false" align="center">求助会员</th>
                <th data-field="graphtitle" data-checkbox="false" align="center">任务标题</th>
                <th data-field="graphtype"  data-editable="false" align="center">任务类别</th>
                <th data-field="graphclassification"  data-editable="false" align="center">任务分类</th>
                <th data-field="moneyreward"  data-editable="false" align="center">赏金</th>
                <th data-field="addtime" data-editable="false"  align="center" >创建时间</th>
                <th data-field="endtime"  data-editable="false" align="center">投稿截止时间</th>
                <th data-field="uid" data-formatter="nameFormatter">操作</th>
            </tr>
            </thead>
        </table>
    </div>
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