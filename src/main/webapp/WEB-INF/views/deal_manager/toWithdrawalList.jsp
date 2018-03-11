<%--提现管理
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017-12-14
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>提现记录管理</title>
    <meta charset="utf-8">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/css/trip.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <%--表格样式--%>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-table/bootstrap-table.css">
    <link rel="stylesheet" href="<%=basePath%>/static/font-awesome/css/font-awesome.min.css">
    <%--<script src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>--%>
    <%--表格JS--%>
    <script src="<%=basePath%>/static/bootstrap/bootstrap-table/bootstrap-table.js"></script>
    <%--表格导出--%>
    <script src="<%=basePath%>/static/bootstrap/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
    <script src="<%=basePath%>/static/js/tableExport.js"></script>
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
    function initTable() {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/cashMgr/getCashList.do", //获取数据的Servlet地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            toolbar:"#toolbar",
            showRefresh: true,
            //showExport:true,
            // height:400,
            pageSize: 10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            exportDataType : "all",
            clickToSelect:true,
            //search:true,
            idField:"uid",
            //showFooter:true,
            sidePagination: "server", //表示服务端请求
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType : "limit",

            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    limit: params.limit,
                    offset: params.offset,
                    cashstate:'0'//待审核
                };
                return param;
            },
            onLoadSuccess: function(){  //加载成功时执行

            },
            onLoadError: function(){  //加载失败时执行

            }
        });
    }
    // var $table = $('#cusTable');

    /**
     * 初始化
     */
    $(document).ready(function () {

        //调用函数，初始化表格
        initTable();
        //initValidate();
        $("button[title='刷新']").hide();

    });


    //显示新建窗口
    function newModule(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length===1){
            (confirmInfo("确认同意用户的提现申请?")).then(function (status) {
                if (status==true) {

                    $.ajax({
                        type: "post",
                        url: "<%=basePath%>/cashMgr/checkCash.do",
                        data: {
                            cashuid:arr[0].cashuid,
                            cashstate:'1'
                        },
                        success: function (data) {

                            if(data!=="failed"){
                                successInfo("保存成功!");
                            }else{
                                errorInfo("保存失败");
                            }
                            $('#cusTable').bootstrapTable('refresh');
                        }
                    });
                }
            });
        } else{
            warningInfo("请选择一条要审核的记录");
        }
    }


</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div>
        <div id="toolbar" class="btn-group-sm">
            <button id="add" class="btn btn-info" onclick="newModule()">
                <i class="glyphicon glyphicon-expand"></i> 审核
            </button>
            <%--<button id="remove" class="btn btn-info" onclick="delRow()">--%>
                <%--<i class="glyphicon glyphicon-remove"></i> 查看--%>
            <%--</button>--%>
            <button id="refresh" class="btn btn-info" name="refresh" >
                <i class="glyphicon glyphicon-refresh"></i> 刷新
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="uid" data-checkbox="true" align="center"></th>
                <th data-field="applypeople"  data-editable="false" align="center">申请人</th>
                <th data-field="applytime"  data-editable="false" align="center">申请时间</th>
                <th data-field="applynum"  data-editable="false" align="center">申请金额</th>
                <th data-field="cashtype" data-editable="false"  align="center" >提现类型</th>
                <th data-field="cashaccount" data-editable="false"  align="center" >提现账户</th>
                <th data-field="cashstate"  data-editable="false" align="center">申请状态</th>
                <th data-field="checkpeoplename"  data-editable="false" align="center">审核人</th>
                <th data-field="checktime"  data-editable="false" align="center">审核时间</th>
                <%--<th data-field="creator"  data-editable="false" align="center">创建人</th>--%>
            </tr>
            </thead>
        </table>
    </div>

</div>


<script src="<%=basePath%>/static/bootstrap/js/bootstrap.min.js"></script>
<%--模态弹窗引用jquery-ui设置可拖动--%>
<script src="<%=basePath%>/static/js/jquery-ui.min.js"></script>

</body>
