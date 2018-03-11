<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017-12-04
  Time: 0:42
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
    <title>轮播图管理</title>
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
            url: "<%=basePath%>/playImageMgr/getPlayimgList.do", //获取数据的Servlet地址
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
    // var $table = $('#cusTable');

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

                //  saveModule();
                submit();
            }else{
                alert("请按照要求填写");
                return;
            }
        });

    });


    //显示新建窗口
    function newModule(){
        //initNewModule();
        $('#newModal').modal('show');
        $("#myModalLabel").html("新建轮播图");
        $('#up_imgfile').val('');
        $('#txt_imgurl').val('');
    $('#playsort').val('');
    $('#txt_navurl').val('');
    $('#sel_playtype').val('');
    $('#txt_remark').val('');
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

    function initValidate(){
        $('#defaultForm').bootstrapValidator({
            message: '值不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                sel_playtype: {
                    validators: {
                        notEmpty: {
                            message: '轮播图类型不能为空'
                        }
                    }
                },
                playsort: {
                    validators: {
                        notEmpty: {
                            message: '轮播图顺序不能为空'
                        }
                    }
                }
            }
        });
    }

    //提交保存
    function saveModule(){
        var files =document.getElementById('up_imgfile').files;
        if(files.length==0&&$('#txt_imgurl').val()=='')
        {
            alert("请选择上传图片或输入图片路径!!");
            return;
        }

        if(window.FormData) {
            var formData = new FormData();
            // 建立一个upload表单项，值为上传的文件
            formData.append('upload', document.getElementById('up_imgfile').files[0]);
            formData.append('worksid', $('#txt_imgurl').val());
            formData.append('playsort', $('#playsort').val());
            formData.append('navurl',  $('#txt_navurl').val());
            formData.append('playtype',$('#sel_playtype').find("option:selected").text());//$('#sel_playtype').val()
            formData.append('typeremark', $('#txt_remark').val());
            $.ajax({
                type: "POST",
                url: "<%=basePath%>/playImageMgr/addPlayimg.do",
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
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
            return;
        }
        else
            alert("请更换浏览器，当前浏览器不支持文件上传!!");

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
                $("#myModalLabel").html("修改轮播图");
                $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
                    $('#fuid').val(row.playuid);
                    $('#txt_imgurl').val(row.imgurl);
                    $('#playsort').val(row.playsort);
                    $("#sel_playtype").find("option[text='"+row.playtype+"']").attr("selected",true);
                    //$('#sel_playtype').text();
                    $('#txt_navurl').val(row.navurl);
                    $('#txt_remark').val(row.typeremark);
                });

            }
        } else{
            warningInfo("请选择要修改的记录");
        }
    }
    //更新
    function updateModule(){
        var files =document.getElementById('up_imgfile').files;
        if(files.length==0&&$('#txt_imgurl').val()=='')
        {
            alert("请选择上传图片或输入图片路径!!");
            return;
        }

        if(window.FormData) {
            var formData = new FormData();
            // 建立一个upload表单项，值为上传的文件
            formData.append('upload', document.getElementById('up_imgfile').files[0]);
            formData.append('worksid', $('#txt_imgurl').val());
            formData.append('playsort', $('#playsort').val());
            formData.append('navurl',  $('#txt_navurl').val());
            formData.append('playtype',$('#sel_playtype').find("option:selected").text());//$('#sel_playtype').val()
            formData.append('typeremark', $('#txt_remark').val());
            formData.append('fuid', $('#fuid').val());
            $.ajax({
                type: "POST",
                url: "<%=basePath%>/playImageMgr/updatePlayimg.do",
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
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
            return;
        }
        else
            alert("请更换浏览器，当前浏览器不支持文件上传!!");


        $('#newModal').modal('hide');
    }

    function getCheckUid(){
        var uids="";
        $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
            uids += row.playuid+',';
        });
        return uids;
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
        var delids = getIdSelections();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/playImageMgr/delPlayimgList.do",
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
                        values: delids
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
            <button id="edit" class="btn btn-info" onclick="editModule()">
                <i class="glyphicon glyphicon-edit"></i> 修改
            </button>
            <button id="remove" class="btn btn-info" onclick="delRow()">
                <i class="glyphicon glyphicon-remove"></i> 删除
            </button>
            <button id="refresh" class="btn btn-info" name="refresh" >
                <i class="glyphicon glyphicon-refresh"></i> 刷新
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="uid" data-checkbox="true" align="center"></th>
                <th data-field="playtype" data-editable="false"  align="center" >轮播图类型</th>
                <th data-field="imgurl"  data-editable="false" align="center">轮播图地址</th>
                <th data-field="navurl"  data-editable="false" align="center">导航地址</th>
                <th data-field="playsort"  data-editable="false" align="center">图片顺序</th>
                <th data-field="typeremark"  data-editable="false" align="center">图片说明</th>
                <th data-field="creator"  data-editable="false" align="center">创建人</th>
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
                    <input type="hidden" name="fuid" id="fuid"/>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>轮播图类型</label>
                            <select class="form-control" id="sel_playtype"  name="sel_playtype" >
                                <option value="1">专题</option>
                                <option value="2">广告</option>
                                <option value="3">宣传</option>
                                <option value="4">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>轮播图顺序</label>
                            <input class="form-control" id="playsort"  name="playsort" placeholder="轮播图顺序" type="text">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="up_imgfile">上传轮播图片</label>
                            <input  id="up_imgfile"  name="up_imgfile"  type="file" accept=".jpg,.jpeg,.gif,.png">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label>图片地址</label>
                            <input class="form-control" id="txt_imgurl"  name="txt_imgurl" placeholder="可上传或手工输入图片地址" type="text">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                    <div class="form-group">
                        <label>导航地址</label>
                        <input class="form-control" id="txt_navurl"  name="txt_navurl" placeholder="可输入或粘贴地址" type="text">
                    </div></div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                    <div class="form-group">
                        <label>图片说明</label>
                        <input class="form-control" id="txt_remark"  name="txt_remark" placeholder="轮播图片说明信息" type="text">
                    </div></div>
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
