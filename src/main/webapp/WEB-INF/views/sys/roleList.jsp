<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>角色管理</title>
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
    <%--日期--%>
    <%-- <script src="<%=basePath%>/My97/WdatePicker.js"></script>>--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/selectbox.js"></script>
    <%--  <script type="text/javascript" src="<%=basePath%>/js/jquery.simplemodal.js"></script>--%>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/language/zh_CN.js"></script>
    <script type="text/javascript"  src="<%=basePath%>/static/js/ztree3d5/js/jquery.ztree.all.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/js/ztree3d5/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <%--    <link rel="stylesheet" href="<%=basePath%>/js/ztree3d5/css/demo.css" type="text/css" />--%>
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
            url: "<%=basePath%>/role/getRoleList.do", //获取数据的Servlet地址
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
    var $table = $('#cusTable');

    /**
     * 初始化
     */
    $(document).ready(function () {
        //调用函数，初始化表格
        initTable();
        $.fn.zTree.init($("#moduleTree"), setting);
        initValidate();
        $("button[title='刷新']").hide();
        $("#defaultForm").submit(function(ev){ev.preventDefault();});
        $('#validateBtn').click(function() {
            var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');//必须
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                var val=$("input[name='roleName']").val();
                var val=$("input[name='roleDescription']").val();
                var val=$("input[name='roleCreator']").val();
                saveModule();
            }
        });
        //关联用户得到选中的值，ajax操作使用
        $("#subSelectUser").click(function() {
            var strId="";
            var strName="";
            $("input[name='cb_box']:checkbox:checked").each(function(){
                strId+=$(this).val()+",";
                strName+=$(this).attr("userName")+",";
            });
            $("#saveSelectUserId").val(strId);
            $("#selectcheck").val(strName);
            $("#newUnitUser").modal("hide");
        });
    });
    //获取后台用户列表
    function searchUnitUsers(){
        $(".users").children("ul").html("");
        $.ajax({
            type:"POST",
            url:"<%=basePath%>/sysuser/getSysUser.do",
            async:false,
            beforeSend:function(){
            },
            error:function(){
                errorInfo("获取人员失败，请重新操作。");
            },
            success:function(data) {
                var users = eval("(" + data + ")");
                var userHtml="";
                for (var i = 0; i< users.length; i++){
                        userHtml += "<li style='list-style-type:none'><span><input name='cb_box' type='checkbox' value='"+users[i].uid+"' userName='"+users[i].fristname+"'></input></span>" + users[i].fristname + "</li>"
                }
                $("#sysUsers").append(userHtml);
            }
        });
        var val = document.getElementById("saveUserId").value.split(",");
        var boxes = document.getElementsByName("cb_box");
        for(i=0;i<boxes.length;i++){
            for(j=0;j<val.length;j++){
                if(boxes[i].value == val[j]){
                    boxes[i].checked = true;
                    break
                }
            }
        }
    }
    //树配置
    var setting = {
        check: {
            enable: true,
            // chkStyle: "text",
        },
        data: {
            simpleData: {
                enable: true,
            }
        },
        async: {
            enable: true,
            url: "<%=basePath%>/module/getModuleTree.do"
        },
        callback: {
            onCheck: zTreeOnCheck
        }
    };
    //回调函数
    function zTreeOnCheck(e, treeId, treeNode) {
        var treeObj=$.fn.zTree.getZTreeObj("moduleTree"),
                nodes=treeObj.getCheckedNodes(true),
                v="";
        var treeNodeId="";
        for(var i=0;i<nodes.length;i++){
            v+=nodes[i].name + ",";
            treeNodeId+=nodes[i].id + ",";//获取选中节点的值
            //treeObj.expandNode(nodes[i], false); //展开选中的
            //treeObj.checkNode(nodes[i], true);
        }
        $("#addNode").val(treeNodeId);
    }
    //显示新建窗口
    function newModule(){
        $('#roleName').val("");
        $('#roleDescription').val("");
        $('#roleCreator').val("");
        $("#myModalLabel").html("新建角色");
        $('#newModal').modal('show');
    }
    function initValidate(){
        $('#defaultForm').bootstrapValidator({
            message: '值不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                roleName: {
                    validators: {
                        notEmpty: {
                            message: '角色名不能为空'
                        }
                    }
                },
                roleDescription: {
                    validators: {
                        notEmpty: {
                            message: '角色描述不能为空'
                        }
                    }
                },
                roleCreator: {
                    validators: {
                        notEmpty: {
                            message: '创建者不能为空'
                        }
                    }
                }
            }
        });
    }
    //提交保存
    function saveModule(){
        var opurl=$('#fid').val()==""?"<%=basePath%>/role/addRole.do":"<%=basePath%>/role/updateRole.do";
        $.ajax({
            type:"post",
            url: opurl,
            data:{
                fid:$('#fid').val(),
                roleName:$('#roleName').val(),
                roleDescription: $('#roleDescription').val(),
                roleCreator:  $('#roleCreator').val(),
                roleCtime:$('#roleCtime').val(),
                roleEnabled: $('#roleEnabled').val(),
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    successInfo("保存成功!")
                    $('#defaultForm').data('bootstrapValidator').resetForm(true);
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("保存失败");
                    $('#defaultForm').data('bootstrapValidator').resetForm(true);
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#newModal').modal('hide');
    }
    //编辑
    function editModule(){
        $("#myModalLabel").html("修改角色");
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            var fid = getIdSelections();
            if(fid.length>1){
                warningInfo("请选择一条记录");
            }else{
                $.ajax({
                    type:"post",
                    url: "<%=basePath%>/role/findRoleByFid.do",
                    data: {fid:getCheckFid()},
                    success: function(data){
                        if(data!=="failed") {
                            $('#newModal').modal('show');
                            var msg = eval("(" + data + ")");
                            $('#fid').val(msg.uid);
                            $('#roleName').val(msg.roleName);
                            $('#roleDescription').val(msg.roleDescription);
                            $('#roleCreator').val(msg.roleCreator);
                            $('#roleCtime').val(msg.roleCtime);
                            $('#roleEnabled').val(msg.roleEnabled);
                        }
                    }
                })
            }
        } else{
            warningInfo("请选择要修改的记录");
        }
    }
    function delRow(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            (confirmInfo("确认删除当前记录?")).then(function (status) {
                if (status==true) {
                    deleteRole()
                }
            });
        } else{
            warningInfo("请选择要删除的记录");
        }
    }
    //删除菜单
    function deleteRole(){
        console.info("deleteRole");
        var ids = getIdSelections();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/role/delRoleFids.do",
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
                    $table.bootstrapTable('remove', {
                        field: 'fid',
                        values: ids
                    });
                    successInfo("删除成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("删除记录失败");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#newModal').modal('hide');
    }
    //权限分配
    function roleModule(){
        //$.fn.zTree.init($("#moduleTree"), setting);
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            var fid = getIdSelections();
            if(fid.length>1){
                warningInfo("请选择一条记录");
            }else{
                var ids = getCheckFid();
                var roleid = ids.substring(0, (ids.length - 1));
                $("#role_id").val(roleid);
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>/role/getModuleByRoleId.do",
                    data:"roleid="+roleid,
                    success:function(data){
                        var msg=eval("("+data+")");
                        var zTree = $.fn.zTree.getZTreeObj("moduleTree");
                        var nodes = zTree.getNodes();
                        var	nodes_array = zTree.transformToArray (nodes);
                        for(var j=0;j<nodes_array.length;j++){
                            zTree.checkNode(nodes_array[j], false);
                            for(var i=0;i<msg.length;i++){
                                if(msg[i].roleid == nodes_array[j].id){
                                    zTree.checkNode(nodes_array[j], true);
                                    zTree.expandNode(nodes_array[j].getParentNode(), true, true, true);
                                }
                            }
                        }
                    }
                });
                $('#moduleTreeModal').modal('show');
            }
        } else{
            warningInfo("请选择角色");
        }
    }
    function getRoleModule(){
        var role_id = $("#role_id").val();
        $.ajax({
            type:"post",
            url: "<%=basePath%>/role/getRoleMoudles.do",
            data:{
                role_id:role_id,
            },
            success: function(data){
                if(data!=="failed"){
                    var msg = eval("(" + data + ")");
                    var md_id="";
                    $.each(msg, function(name, value) {
                        md_id = md_id + value.mdId + ",";
                    });
                }else{
                    errorInfo("保存失败");
                }
            }
        });
    }

    function addRoleModules(){
        var role_id =$('#role_id').val();
        var zTree = $.fn.zTree.getZTreeObj("moduleTree");
        var nodes = zTree.getCheckedNodes();
        var module_id="";
        for(var i=0;i<nodes.length;i++){
            module_id +=nodes[i].id+",";
        }
        $.ajax({
            type:"post",
            url: "<%=basePath%>/role/addRoleMoudle.do",
            data:{
                role_id:role_id,
                module_id:module_id,
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    successInfo("保存成功!")
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("保存失败");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#moduleTreeModal').modal('hide');
    }

    //关联用户
    function roleUser(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0) {
            var fid = getIdSelections();
            if (fid.length > 1) {
                warningInfo("请选择一条记录");
            }else {
                $('#roleUserModal').modal('show');
                var ids = getCheckFid();
                var roleid = ids.substring(0, (ids.length - 1));
                $("#roleId").val(roleid);
                $.ajax({
                    type:"POST",
                    url:"<%=basePath%>/role/getUserByRileId.do",
                    data:"roleid="+roleid,
                    success:function(data){
                        var msg=eval("("+data+")");
                        var res="";
                        var resId="";
                        for(var i=0;i<msg.length;i++){
                            res += msg[i].fname +",";
                            resId +=msg[i].fid +",";
                        }
                        $("#selectcheck").val(res);
                        $("#saveUserName").val(res);
                       // $("#saveSelectUserId").val(resId);
                        $("#saveUserId").val(resId);
                    }
                });
            }
        }else{
            warningInfo("请选择角色");
        }
    }
    function addRoleUser(){
        var userIds=$("#saveSelectUserId").val();
        var roleId =$('#roleId').val();
        $.ajax({
            type:"post",
            url: "<%=basePath%>/role/addRoleUser.do",
            data:{
                roleId:roleId,
                user_id:userIds,
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    successInfo("保存成功!")
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#roleUserModal').modal('hide');
                }else{
                    errorInfo("保存失败");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#roleUserModal').modal('hide');
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
    //获取FID用于后台操作
    function getCheckFid(){
        var fids="";
        $('#cusTable').find('input[name="btSelectItem"]:checked').each(function(){
            fids += $(this).val()+',';
        });
        return fids;
    }
    //选人
    function SelectUser(){
        searchUnitUsers();
        $("#newUnitUser").modal("show");
    }
    function resetForm(){
        $('#defaultForm').data('bootstrapValidator').resetForm(true);
    }

</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div >
        <div id="toolbar" class="btn-group-sm">
            <button id="add" class="btn btn-info" onclick="newModule()">
                <i class="glyphicon glyphicon-expand"></i> 增加
            </button>
            <button id="remove" class="btn btn-info" onclick="delRow()">
                <i class="glyphicon glyphicon-remove"></i> 删除
            </button>
            <button id="edit" class="btn btn-info" onclick="editModule()">
                <i class="glyphicon glyphicon-edit"></i> 修改
            </button>
            <button id="selectRoleModule" class="btn btn-info"  onclick="roleModule()">
                <i class="glyphicon glyphicon-edit"></i> 分配权限
            </button>
            <button id="selectRoleUser" class="btn btn-info"  onclick="roleUser()">
                <i class="glyphicon glyphicon-edit"></i> 关联用户
            </button>
            <button id="refresh" class="btn btn-info" name="refresh" >
                <i class="glyphicon glyphicon-refresh"></i> 刷新
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="uid" data-checkbox="true" align="center"></th>
                <th data-field="roleName" data-editable="false"  align="center" >角色名称</th>
                <th data-field="roleDescription"  data-editable="false" align="center">角色描述</th>
                <th data-field="roleCreator"  data-editable="false" align="center">创建者</th>
            </tr>
            </thead>
        </table>
    </div>

    <div class="modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width:400px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-body" id="defaultForm" method="post">
                    <div class="row">
                        <input type="hidden" name="fid" id="fid"/>
                        <div>
                            <div class="form-group">
                                <label>角色名称</label>
                                <input class="form-control" id="roleName"  name="roleName" placeholder="角色名称" type="text">
                            </div>
                            <!-- /.form-group -->
                            <div class="form-group">
                                <label>角色描述</label>
                                <input class="form-control" id="roleDescription" name="roleDescription"  placeholder="角色描述" type="text">
                            </div>
                            <div class="form-group">
                                <label>创&nbsp;建&nbsp;者</label>
                                <input class="form-control" id="roleCreator" name="roleCreator" placeholder="创建者" type="text">
                            </div>
                            <!-- /.form-group -->
                        </div>
                        <!-- /.col -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"  onclick="resetForm()">关闭</button>
                    <button type="button" class="btn btn-primary" id="validateBtn">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="moduleTreeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width:400px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="moduleTreeModalLabel">分配权限</h4>
                </div>
                <div class="modal-body">
                    <div class="row">

                        <div class="col-md-6">
                            <div class="form-group">
                                <label>菜单名称</label>
                                <ul id="moduleTree" class="ztree" style="overflow:auto;height: 400px;width: 300px"></ul>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="role_id" id="role_id"/>
                <input type="hidden" name="addNode" id="addNode"/>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addRoleModules()">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="roleUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="roleUserModalLabel">关联用户</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-6">
                            <input id="saveUserId" type="hidden"/>
                            <input id="saveUserName" type="hidden"/>
                            <div class="form-group">
                                <label>用户信息</label>
                                <div class="form-group">
                                    <span><input type="text" name="manageUserId" id="selectcheck" readonly style="width: 240px"/></span>
                                    <i style="margin-left: 250px;margin-top: -20px" class="fa fa-user" onclick="SelectUser()"></i>
                                    <input type="hidden" id="saveSelectUserId"/>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="roleId" id="roleId"/>
                <input type="hidden" name="user_id" id="user_id"/>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="addRoleUser()">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
<div class="modal fade" id="newUnitUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:400px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myUnitUser">选择关联用户</h4>
            </div>
            <div class="users" style="margin-left16px;height:151px;overflow: auto">
                <ul id="sysUsers">
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="resetForm()">关闭</button>
                <button type="button" class="btn btn-primary" id="subSelectUser">提交</button>
            </div>
        </div>
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