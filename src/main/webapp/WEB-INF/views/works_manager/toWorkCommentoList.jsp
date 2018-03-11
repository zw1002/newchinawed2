<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <meta charset="utf-8">
    <title>作品评论管理</title>
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
    <script type="text/javascript" src="<%=basePath%>/static/js/selectbox.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/language/zh_CN.js"></script>
    <link href="<%=basePath%>/static/css/selectuser.css" rel="stylesheet">
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
        .modal-content{
            width:400px;
            margin-left: auto;
            margin-right: auto;
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
    //初始化表格
    function initTable() {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/worksCommentMgr/getCommentList.do", //获取数据的Servlet地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            toolbar:"#toolbar",
            showRefresh: true,
            //showExport:true,
            // height:400,
            pageSize:10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            exportDataType : "all",
            clickToSelect:true,
            //search:true,
            idField:"uuid",
            //showFooter:true,
            sidePagination: "server", //表示服务端请求
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType : "limit",
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    limit: params.limit,
                    offset: params.offset,delflg:'0'
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
        initTable();
        $("button[title='刷新']").hide();
    });

    //显示新建窗口
    function newModule(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length===1){
            (confirmInfo("确认用户评论无问题?")).then(function (status) {
                if (status==true) {

                    $.ajax({
                        type: "post",
                        url: "<%=basePath%>/worksCommentMgr/checkComment.do",
                        data: {
                            uid:arr[0].uid,
                            delflag:'1'
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
    <div >
        <div id="toolbar" class="btn-group-sm">
            <!--<button id="add" class="btn btn-info" onclick="newModule()">
                <i class="glyphicon glyphicon-expand"></i> 增加
            </button>-->
            <button id="edit" class="btn btn-info" onclick="newModule()">
                <i class="glyphicon glyphicon-edit"></i> 审核
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
                <th data-field="uuid" data-checkbox="true" align="center"></th>
                <th data-field="worksid" data-editable="false"  align="center" >评论作品</th>
                <th data-field="commentuserid" data-editable="false"  align="center" >评论人</th>
                <th data-field="commenttime" data-editable="false"  align="center" >评论时间</th>
                <th data-field="commentinfo" data-editable="false"  align="center" >评论内容</th>
                <%--<th data-field="dpinum" data-editable="false"  align="center" >被评论内容</th>--%>
                <th data-field="oknums" data-editable="false"  align="center" >评论点赞数</th>
            </tr>
            </thead>
        </table>
    </div>


</div>
<script src="<%=basePath%>/static/bootstrap/js/bootstrap.min.js"></script>
<%--模态弹窗引用jquery-ui设置可拖动--%>
<script src="<%=basePath%>/static/js/jquery-ui.min.js"></script>
</body>

