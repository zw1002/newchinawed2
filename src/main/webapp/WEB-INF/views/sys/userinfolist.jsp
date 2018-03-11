<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>会员管理</title>
    <meta charset="utf-8">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
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
    <!--树-->
    <script type="text/javascript" src="<%=basePath%>/static/js/ztree3d5/js/jquery.ztree.all.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/js/ztree3d5/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <link rel="stylesheet" href="<%=basePath%>/static/js/ztree3d5/css/demo.css" type="text/css" />
    <!--校验-->
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/language/zh_CN.js"></script>
    <!--时间控件-->
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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

    //初始化表格
    function initTable() {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/user/getUserInfoList.do", //获取数据的Servlet地址
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
     * 初始化数据
     */
    $(document).ready(function () {
        //调用函数，初始化表格
        initTable();
        //校验
        initValidate();
        $("#userForm").submit(function(ev){ev.preventDefault();});//AJAX提交必修使用
        $('#submitBtn').click(function() {
            var bootstrapValidator = $("#userForm").data('bootstrapValidator');//必须
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                submit();
            }else{
                alert("请按照要求填写");
                return;
            }
        });
        $(".close").click(function (){
            $('#userForm').data('bootstrapValidator').resetForm(true);
        })
    });
    //用户校验
    function initValidate(){
        $('#userForm').bootstrapValidator({
            message: '值不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                fristname:{
                    validators:{
                        notEmpty: {
                            message: '姓名不能为空!'
                        },
                        regexp: {
                            regexp: /[\u4e00-\u9fa5]/,
                            message: '姓名只能由中文字符组成'
                        }
                    }
                },
                telephone:{
                    validators:{
                        notEmpty: {
                            message: '手机号不能为空!'
                        },
                        regexp: {
                            regexp: /^[1]{1}[3,4,5,7,8]{1}[0-9]{9}$/,
                            message: '请输入正确的电话号'
                        }
                    }
                }
            }
        });
    }
//添加和编辑提交按钮
    function submit(){
        var UserLabel=$("#myUserLabel").text();//添加编辑用户窗口
        if(UserLabel.indexOf("添加") !=-1){
            saveUser();//添加提交
        } else{
            updateUser();//修改提交
        }
    }
    //初始化表单
    function init(){
        $("#uid").val("");
        $("#fristname").val("");
        $("#telephone").val("");
        $("#account").val("");
        $("#password").val("");
        $("#unitId").val("");
        $("#numbers").val("");
    }
//添加窗口
    function newUser(){
        $("#accountId").css("display","block");
        $("#passwdId").css("display","block");
        init();
        var zTree = $.fn.zTree.getZTreeObj("UnitTree");
        $("#myUserLabel").html("添加用户");
        $('#newUser').modal('show');
    }
//添加用户提交保存
    function saveUser(){
        var fristname=$("#fristname").val();
        var telephone=$("#telephone").val();
        var account=$("#account").val();
        var password=$("#password").val();
       var unitId=$("#saveUnitid").val();
        var numbers=$("#numbers").val();
        $.ajax({
           type:"POST",
            url:"<%=basePath%>/user/addUser.do",
            data:{
                fristname:fristname,
                telephone:telephone,
                account:account,
                password:password,
                unitId:unitId,
                numbers:numbers
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("添加成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#userForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("添加记录失败");
                }
            }
        });
        $('#newUser').modal('hide');
    }
//编辑
    function editUser(){
        $("#myUserLabel").html("修改用户");
        init();
        //编辑的时候不修改帐号密码
        $("#accountId").css("display","none");
        $("#passwdId").css("display","none");
        var arr = $('#cusTable').bootstrapTable('getSelections');
        var ids = getCheckUid();
        if(arr.length>0) {
            var uid = getIdSelections();
            if (uid.length > 1) {
                warningInfo("请选择一条记录");
            } else {
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>/user/getUserById.do",
                    data: {
                        ids: ids
                    },
                    success: function (data) {
                        var msg = eval("(" + data + ")");
                        $("#uid").val(ids.replace(",", ""));
                        $("#fristname").val(msg.fristname);
                        $("#telephone").val(msg.telephone);
                        $("#numbers").val(msg.numbers);
                    }
                });
                //获取部门，在树中默认选中
                        $.ajax({
                            url: "<%=basePath%>/user/getUnit.do",
                            type: "POST",
                            data: {
                                ids: ids
                            },
                            success: function (msg) {
                                var mss = eval("(" + msg + ")");
                                for (var i = 0; i < mss.length; i++) {
                                   $("#unitId").val(mss[i].fristname);
                                    $("#saveUnitid").val(mss[i].uid);
                                }
                            }
                        });
                $('#newUser').modal('show');
            }
        }else{
            warningInfo("请选择一条记录");
        }

    }
    //提交更新
    function updateUser(){
        var uid=$("#uid").val();
        var fristname=$("#fristname").val();
        var telephone=$("#telephone").val();
        var unitId=$("#saveUnitid").val();
        var numbers=$("#numbers").val();
        $.ajax({
            type:"POST",
            url:"<%=basePath%>/user/updateUser.do",
            data:{
                uid:uid,
                fristname:fristname,
                telephone:telephone,
                unitId:unitId,
                numbers:numbers
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("修改成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#userForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("修改记录失败");
                }
            }
        });
        $('#newUser').modal('hide');
    }

function delRow(){
    var arr = $('#cusTable').bootstrapTable('getSelections');
    if(arr.length>0) {
        var uid = getIdSelections();
            (confirmInfo("确认删除当前记录?")).then(function (status) {
                if (status == true) {
                    deleteUser();
                }
            });
    }else{
        warningInfo("请选择要删除的记录");
    }
}
    //删除
function deleteUser(){
    console.info("deleteUser");
    var ids = getIdSelections();
     $.ajax({
        type: "POST",
        url: "<%=basePath%>/user/delUserIds.do",
        data: {
            ids:getCheckUid()
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
                    field: 'uid',
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
        return row.uid;
    });
}
    //获取UID用于后台操作
function getCheckUid(){
    var uids="";
    $('#cusTable').find('input[name="btSelectItem"]:checked').each(function(){
        uids += $(this).val()+',';
    });
    return uids;
}
    //清空校验
    function resetForm(){
        $('#userForm').data('bootstrapValidator').resetForm(true);
    }
</script>
<body id="loading" class="style_body">
<div class=" style_border">
    <div id="toolbar" class="btn-group-sm">
        <!--
        <button id="add" class="btn btn-info" onclick="newUser()">
            <i class="glyphicon glyphicon-expand"></i> 增加
        </button>
        <button id="edit" class="btn btn-info" onclick="editUser()">
            <i class="glyphicon glyphicon-edit"></i> 修改
        </button>
        <button id="remove" class="btn btn-info" onclick="delRow()">
            <i class="glyphicon glyphicon-remove"></i> 删除
        </button>
        <button id="refresh" class="btn btn-info" name="refresh" >
            <i class="glyphicon glyphicon-refresh"></i> 刷新
        </button>
        -->
    </div>
    <table id="cusTable" class="table">
        <thead>
        <tr>
            <th data-field="uid" data-checkbox="true" align="center"></th>
            <th data-field="fristname" data-editable="false"  align="center">姓名</th>
            <th data-field="telephone" data-editable="false" align="center">手机</th>
            <th data-field="iccode" data-editable="false" align="center">身份证号</th>
            <th data-field="qqid" data-editable="false" align="center">QQ</th>
            <th data-field="bankaddr" data-editable="false" align="center">银行信息</th>
            <th data-field="bankcode" data-editable="false" align="center">银行卡号</th>
            <th data-field="bidnums" data-editable="false" align="center">求图中标数</th>
            <th data-field="winningbid" data-editable="false" align="center">求图收入</th>
        </tr>
        </thead>
    </table>
    </div>

    <div class="modal fade" id="newUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myUserLabel"></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                        <div id="userForm" method="post">
                            <div class="col-md-6">
                                <input class="form-control" id="uid" placeholder="ID" type="hidden">
                                <!--上传附件，关联流程ID-->
                                <input type="hidden" id="relateId" value="<%= request.getAttribute("wuid")%>">
                                <input type="hidden" value="0" id="changeStatu">
                                <div class="form-group">
                                    <label>姓名</label>
                                    <input class="form-control" id="fristname" name="fristname" placeholder="姓名" type="text">
                                </div>
                                <div class="form-group">
                                    <label>部门</label>
                                    <input id="saveUnitid" type="hidden"/>
                                    <input onclick="showMenu(); return false;" placeholder="点击选择" class="form-control" id="unitId" name="unitId" type="text" readonly value=""/>
                                    <div id="menuContent" class="menuContent" style="display:none;position: absolute;">
                                        <ul id="treeDemo" class="ztree" style="width:160px;margin-top:29px;background: white;border: 1px solid lightgrey"></ul>
                                    </div>
                                </div>
                                <div id="accountId" class="form-group">
                                    <label>帐号</label>
                                    <input class="form-control" id="account" placeholder="帐号" type="text">
                                </div>

                                <!-- /.form-group -->
                            </div>
                            <!-- /.col -->
                            <div class="col-md-6">
                                <!-- /.form-group -->
                                <div class="form-group">
                                    <label>联系方式</label>
                                    <input class="form-control" id="telephone" name="telephone" placeholder="手机" type="text">
                                </div>
                                <div class="form-group">
                                    <label>工号</label>
                                    <input class="form-control" id="numbers" name="numbers" placeholder="工号" type="text">
                                </div>
                                <div id="passwdId" class="form-group">
                                    <label>密码</label>
                                    <input class="form-control" id="password" placeholder="密码" type="password">
                                </div>
                                <div class="form-group" id="uploader" style="display:none;"></div>
                            </div>
                            </div>
                            <!-- /.col -->
                        </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="resetForm()">关闭</button>
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