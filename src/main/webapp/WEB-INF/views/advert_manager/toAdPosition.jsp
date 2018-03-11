<%--广告位管理
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2018-02-13
  Time: 0:44
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
    <title>广告位管理</title>
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
    /**
     * 初始化
     */
    $(document).ready(function () {

        //调用函数，初始化表格
        initTable();
        initValidate();
        $("button[title='刷新']").hide();
        $("#defaultForm").submit(function(ev){ev.preventDefault();});
        $('#validateBtn').click(function() {
            var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');//必须
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                var val=$("input[name='adName']").val();
                var val=$("input[name='adprice']").val();
                var val=$("input[name='adwidth']").val();
                var val=$("input[name='adheight']").val();
                submit();
            }else{
                alert("请按照要求填写");
                return;
            }
        });

    });
    //初始化表格
    function initTable() {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/advert_pos/getAdPositionList.do", //获取数据的Servlet地址
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




    //显示新建窗口
    function newModule(){
        initNewModule();
        $('#newModal').modal('show');
        $("#myModalLabel").html("新建广告位置");

    }

    //添加和编辑提交按钮
    function submit(){
        var UserLabel=$("#myModalLabel").text();//添加编辑用户窗口
        if(UserLabel.indexOf("新建") !=-1){
            saveModule();//添加提交
        } else{
            updateModule();//修改提交
        }
    }
    //初始化新建表单
    function initNewModule(){
        $('#fid').val("");
        $('#adName').val("");
        $('#adprice').val("");
        $('#adwidth').val("");
        $('#adheight').val("");
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
                adName: {
                    validators: {
                        notEmpty: {
                            message: '广告位名称不能为空'
                        }
                    }
                },
                adprice: {
                    validators: {
                        notEmpty: {
                            message: '价格不能为空'
                        }
                    }
                },
                adwidth: {
                    validators: {
                        notEmpty: {
                            message: '宽度不能为空'
                        }
                    }
                },
                adheight: {
                    validators: {
                        notEmpty: {
                            message: '高度不能为空'
                        }
                    }
                }
            }
        });
    }

    //提交保存
    function saveModule(){
        $.ajax({
            type:"post",
            url: "<%=basePath%>/advert_pos/addAdvert.do",
            data:{
                adname:$('#adName').val(),
                adprice: $('#adprice').val(),
                adwidth:  $('#adwidth').val(),
                adheight:$('#adheight').val()
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    successInfo("保存成功!")
                    $('#defaultForm').data('bootstrapValidator').resetForm(true);
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("保存失败");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#newModal').modal('hide');
    }
    //编辑
    function editModule(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            var fid = getIdSelections();
            if(fid.length>1){
                warningInfo("请选择一条记录");
            }else {

                $('#newModal').modal('show');
                $("#myModalLabel").html("修改广告位信息");
                $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
                    $('#fid').val(getCheckFid().replace(',',''));
                    $('#adName').val(row.adposition);
                    $('#adprice').val(row.adprice);
                    $('#adwidth').val(row.adwidth);
                    $('#adheight').val(row.adheight);
                });
            }
        } else{
            warningInfo("请选择要修改的记录");
        }
    }
    //更新
    function updateModule(){

        $.ajax({
            type:"post",
            url:  "<%=basePath%>/advert_pos/updateAdvert.do",
            data:{
                uid:$('#fid').val(),
                adname:$('#adName').val(),
                adprice: $('#adprice').val(),
                adwidth:$('#adwidth').val(),
                adheight: $('#adheight').val()
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    successInfo("保存成功!")
                    $('#defaultForm').data('bootstrapValidator').resetForm(true);
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("保存失败");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#newModal').modal('hide');
    }

    function delRow(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            (confirmInfo("确认删除当前记录?")).then(function (status) {
                if (status==true) {
                    deleteModule();
                }
            });
        } else{
            warningInfo("请选择要删除的记录");
        }
    }
    //删除
    function deleteModule(){
        console.info("deleteModule");
        var ids = getIdSelections();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/advert_pos/delAdvert.do",
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
                        field: 'uid',
                        values: ids
                    });
                    successInfo("删除成功!")
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("删除记录失败");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#newModal').modal('hide');
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
    function resetForm(){
        $('#defaultForm').data('bootstrapValidator').resetForm(true);
    }

</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div>
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
            <button id="refresh" class="btn btn-info" name="refresh" >
                <i class="glyphicon glyphicon-refresh"></i> 刷新
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="uid" data-checkbox="true" align="center"></th>
                <th data-field="adposition" data-editable="false"  align="center" >广告位名称</th>
                <th data-field="adprice"  data-editable="false" align="center">广告位价格</th>
                <th data-field="adwidth"  data-editable="false" align="center">广告位宽度</th>
                <th data-field="adheight"  data-editable="false" align="center">广告位高度</th>
            </tr>
            </thead>
        </table>
    </div>

</div>

<div class="modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新建</h4>
            </div>
            <div class="modal-body" id="defaultForm" method="post">
                <div class="row">
                    <input type="hidden" name="fid" id="fid"/>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>广告位名称</label>
                            <input class="form-control" id="adName" name="adName" placeholder="广告位置(如首页-中间，二级页面-中间，左停靠，右停靠等)" type="text">
                        </div>
                        <!-- /.form-group -->
                        <div class="form-group">
                            <label>广告位价格</label>
                            <input class="form-control" id="adprice"  name="adprice" placeholder="输入价格"  type="text">
                        </div>
                        <!-- /.form-group -->
                    </div>
                    <!-- /.col -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>位置宽度</label>
                            <input class="form-control" id="adwidth"  name="adwidth" placeholder="输入整数，根据广告位实际大小输入" type="text">
                        </div>
                        <!-- /.form-group -->
                        <div class="form-group">
                            <label>位置高度</label>
                            <input class="form-control" id="adheight"  name="adheight" placeholder="输入整数，根据广告位实际大小输入" type="text">
                        </div>
                        <!-- /.form-group -->
                    </div>
                    <!-- /.col -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="resetForm()" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="validateBtn" >提交</button>
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