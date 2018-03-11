<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>帐号管理</title>
    <meta charset="utf-8">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/css/style.css">
    <link rel="stylesheet" href="<%=basePath%>/static/css/trip.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
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
    <%--选人--%>
    <link href="<%=basePath%>/static/css/selectuser.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>/static/js/selectbox.js"></script>
    <%--校验--%>
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
            url: "<%=basePath%>/account/getAccountList.do", //获取数据的Servlet地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            toolbar:"#toolbar",
            showExport:false,
            // height:400,
            pageSize: 10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            exportDataType : "all",
            clicktoselect:false,
            search:false,
            idField:"fid",
            //showFooter:true,
            sidePagination: "server", //表示服务端请求
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType : "limit",

            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    limit: params.limit,
                    offset: params.offset
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
        initValidate();
        $("#accountForm").submit(function(ev){ev.preventDefault();});//AJAX提交必修使用
        $('#submitBtn').click(function() {
            var bootstrapValidator = $("#accountForm").data('bootstrapValidator');//必须
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                saveAccount();
            }else{
                alert("请按照要求填写");
                return;
            }
        });
        $(".close").click(function (){
            $('#accountForm').data('bootstrapValidator').resetForm(true);
        })
    });
    function initValidate(){
        $('#accountForm').bootstrapValidator({
            message: '值不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                account: {
                    message: '用户名无效',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '用户名6-30字符'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_\.]+$/,
                            message: '用户名只能由字母，数字，点和下划线组成'
                        },
                        remote: {
                            url: '<%=basePath%>/account/checkUserName.do',
                            delay :  200,
                            message: '用户名是不可用的'
                        },
                        different: {
                            field: 'passwd',
                            message: '用户名和密码不能是彼此相同的'
                        }
                    }
                },
                passwd: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空!'
                        },
                        different: {
                            field: 'account',
                            message: '用户名和密码不能是彼此相同的'
                        }
                    }
                },
                manageUserId:{
                    validators:{
                        notEmpty: {
                            message: '用户不能为空!'
                        }
                    }
                },
            }
        });
    }

    //添加窗口
    function newAccount(){
        $("#fid").val("");
        $("#account").val("");
        $("#passwd").val("");
        $("#userid").val("");
        $("#myAccountLabel").html("添加帐号");
        $('#newAccount').modal('show');
    }
    //添加账户提交保存
    function saveAccount(){
        var account=$("#account").val();
        var passwd=$("#passwd").val();
        var usemobile=$("#usemobile").val();
        var userIds = $("#userid").data("ids") + "";
        $.ajax({
            type:"POST",
            url:"<%=basePath%>/account/addAccount.do",
            data:{
                account:account,
                passwd:passwd,
                usemobile:usemobile,
                userIds:userIds
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("添加成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#accountForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("添加记录失败");
                }
            }
        });
        $('#newAccount').modal('hide');
    }

    function delRow(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            var fid = getIdSelections();
            if (fid.length > 1) {
                warningInfo("请选择一条记录");
            } else {
                (confirmInfo("确认删除当前记录?")).then(function (status) {
                    if (status == true) {
                        deleteAccount();
                    }
                });
            }
        } else{
            warningInfo("请选择要删除的记录");
        }
    }
    //删除账户
    function deleteAccount(){
        console.info("deleteUser");
        var ids = getIdSelections();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/account/delAccountIds.do",
            data: {
                ids:getCheckFid()
            },
            beforeSend : function() {
                submitWait();
            },
            error : function() {
                hideWait();
                errorInfo("删除记录失败");
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    $('#cusTable').bootstrapTable('remove', {
                        field: 'fid',
                        values: ids
                    });
                    successInfo("删除成功!")
                }else{
                    errorInfo("删除记录失败");
                }
            }
        });

    }

    //取表格行数用于表格行的移除
    function getIdSelections() {
        return $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
            return row.fid;
        });
    }
    //获取FID用于后台操作
    function getCheckFid(){
        var fids="";
        $('#cusTable').find('input[name="btSelectItem"]:checked').each(function(){
            fids += $(this).val()+',';
        });
        return fids;
    }
    //重置密码
    function resetPasswd(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        var fid="";
        $('#cusTable').find('input[name="btSelectItem"]:checked').each(function(){
            fid += $(this).val();
        });
        if(arr.length>0) {
            var fids = getIdSelections();
            if (fids.length > 1) {
                warningInfo("请选择一条记录");
            } else {
                (confirmInfo("确认初始化密码?")).then(function (status) {
                    if (status == true) {
                        $.ajax({
                            type: "POST",
                            url: "<%=basePath%>/account/resetPasswd.do",
                            data: {
                                ids: fid
                            },
                            success: function (data) {
                                if (data !== "failed") {
                                    successInfo("初始化密码成功!");
                                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                                } else {
                                    errorInfo("初始化密码失败");
                                }
                            }
                        });
                    }
                });
            }
        }else{
            warningInfo("请选择要初始化密码的记录");
        }
    }
    //清空校验
    function resetForm(){
        $('#accountForm').data('bootstrapValidator').resetForm(true);
    }
</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div>
        <div id="toolbar" class="btn-group-sm">
            <button id="remove" class="btn btn-info" onclick="delRow()">
                <i class="glyphicon glyphicon-remove"></i> 冻结
            </button>
            <button id="resetPasswd" class="btn btn-info" onclick="resetPasswd()">
                <i class="glyphicon glyphicon-edit"></i> 重置密码
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="fid" data-checkbox="true" align="center"></th>
                <th data-field="account" data-editable="false"  align="center">帐号</th>
                <th data-field="fname"  data-editable="false" align="center">用户</th>
                <th data-field="usertype"  data-editable="false" align="center">用户类型</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="newAccount" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myAccountLabel"></h4>
            </div>
            <div class="modal-body">
                <div>
                    <div id="accountForm" method="post">
                        <input class="form-control" id="fid" placeholder="ID" type="hidden">
                        <div class="form-group">
                            <label>帐号</label>
                            <input class="form-control" id="account" name="account" placeholder="帐号" type="text">
                        </div>
                        <div class="form-group" >
                            <label>密码</label>
                            <input class="form-control" id="passwd" name="passwd" placeholder="密码" type="password">
                        </div>
                        <div class="form-group">
                            <label>用户</label>
                            <input onclick="SelectBox.show($('#userid'), true,'user')" class="form-control" type="text" name="manageUserId" id="userid" readonly/>
                        </div>
                        <div class="form-group form-inline">
                            <label>使用手机</label>
                            <select class="form-control" id="usemobile" style="margin-left:10px;">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                        <!-- /.form-group -->
                    </div>
                    <!-- /.col -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="resetForm()" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="submitBtn">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
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