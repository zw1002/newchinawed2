<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017-12-04
  Time: 0:43
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

    Date dNow = new Date( );
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    String strCurrent=ft.format(dNow);

%>
<head>
    <meta charset="utf-8">
    <title>广告发布管理</title>
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
    <%--日期样式--%>
    <%--<link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">--%>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
    <%--日期JS--%>
    <%--<script src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/moment-with-locales.min.js"></script>--%>
    <script src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
    <%-- <script type="text/javascript"  src="<%=basePath%>/static/js/ztree3d5/js/jquery.ztree.all.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/js/ztree3d5/css/zTreeStyle/zTreeStyle.css" type="text/css" />
       <link rel="stylesheet" href="<%=basePath%>/js/ztree3d5/css/demo.css" type="text/css" />--%>
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
            url: "<%=basePath%>/advertInfoMgr/getAdvertInfoList.do", //获取数据的Servlet地址
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
        //$('#Modal').modal('show');
        $('.form_datetime').datetimepicker({
            format: "yyyy-mm-dd",
            language:  'zh-CN',
            weekStart: 1,
//            todayBtn:  1,
//            autoclose: 1,
//            todayHighlight: 1,
            startView: 2,
            minView:2,maxView:2,
            pickerPosition: "top-left",
            showMeridian: 1
        });
        //newModule();
        //初始广告位置列表
        $.ajax({
            type:"post",
            url: "<%=basePath%>/advert_pos/getAdPositionList.do",
            data:{limit:0},
            success: function(data){
                if(data!=="failed"){
                    var obj = eval('(' + data + ')');
                    $('#sel_adpos').append("<option value=''></option>");
                    if(obj.rows!==undefined){
                        for(var i=0;i<obj.rows.length;i++){
                            $('#sel_adpos').append("<option value=\'"+obj.rows[i].uid+"\'>"+obj.rows[i].adposition+"</option>");
                        }
                    }
                }
            }
        });
        //初始客户信息列表
        $.ajax({
            type:"post",
            url: "<%=basePath%>/adClientMgr/getClientList.do",
            data:{limit:0},
            success: function(data){
                if(data!=="failed"){
                    var obj = eval('(' + data + ')');
                    $('#sel_adclient').append("<option value=''></option>");
                    if(obj.rows!==undefined){
                        for(var i=0;i<obj.rows.length;i++){
                            $('#sel_adclient').append("<option value=\'"+obj.rows[i].clintuid+"\'>"+obj.rows[i].clientname+"</option>");
                        }
                    }
                }
            }
        });

    });

    //添加和编辑提交按钮
    function submit(){

        saveModule();
    }
    //显示新建窗口
    function newModule(){

        $('#fid').val('');
        $('#txt_adprice').val('');
        $('#txt_url').val('');
        $('#txt_adurl').val('');
        $("#sel_adpos").find("option[value='']").attr("selected",true);
        $("#sel_adclient").find("option[value='']").attr("selected",true);
        $("#sel_adtype").find("option[value='']").attr("selected",true);
        document.getElementById('up_imgfile').files=null;
        $('[data-link-field="dt_etime"]').find(".form-control").val('');
        $('[data-link-field="dt_stime"]').find(".form-control").val('');

        $('#dt_etime').val('');
        $('#dt_stime').val('');
        $("#myModalLabel").html("发布广告");
        $('#newModal').modal('show');
        resetForm();
    }
    function editModule(){
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length>0){
            var fid = getIdSelections();
            if(fid.length>1){
                warningInfo("请选择一条记录");
            }else {
                resetForm();
                $("#myModalLabel").html("编辑广告信息");
                $('#newModal').modal('show');
                resetForm();
                $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
                    $('#fid').val(row.aduid);
                    $('#txt_adprice').val(row.adprice);
                    $('#txt_url').val(row.imgurl);
                    $('#txt_adurl').val(row.adurl);
                    $("#sel_adpos").find("option[value='"+row.adpositionid+"']").attr("selected",true);
                    $("#sel_adclient").find("option[value='"+row.clientuid+"']").attr("selected",true);
                    $("#sel_adtype").find("option[value='"+row.adtype+"']").attr("selected",true);

                    $('[data-link-field="dt_etime"]').find(".form-control").val(row.adendtime.substring(0,10));
                    $('[data-link-field="dt_stime"]').find(".form-control").val(row.adstarttime.substring(0,10));
                    $('#dt_etime').val(row.adendtime);
                    $('#dt_stime').val(row.adstarttime);
                });

            }
        } else{
            warningInfo("请选择要修改的记录");
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
        var files =document.getElementById('up_imgfile').files;
        if(files.length==0&&$('#txt_url').val()=='')
        {
            alert("请选择上传图片或输入图片路径!!");
            return;
        }

        var opurl=$('#fid').val()==""?"<%=basePath%>/advertInfoMgr/addAdvertInfo.do":"<%=basePath%>/advertInfoMgr/updateAdvertInfo.do";
        if(window.FormData) {
            var formData = new FormData();
            // 建立一个upload表单项，值为上传的文件
            formData.append('upload', document.getElementById('up_imgfile').files[0]);
            formData.append('uid', $('#fid').val());
            formData.append('imgurl', $('#txt_url').val());
            formData.append('adpositionid', $('#sel_adpos').val());//.find("option:selected").text()
            formData.append('adprice',  $('#txt_adprice').val());
            formData.append('adurl',$('#txt_adurl').val());
            formData.append('clientuid', $('#sel_adclient').val());
            formData.append('adtype', $('#sel_adtype').val());
            formData.append('adstarttime', $('#dt_stime').val());
            formData.append('adendtime', $('#dt_etime').val());
            formData.append('delflag', $("input[name='isstart']:checked").val());
            $.ajax({
                type: "POST",
                url: opurl,
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
            uids += row.aduid+',';
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
            url: "<%=basePath%>/advertInfoMgr/delAdvertInfoList.do",
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
    <div >
        <div id="toolbar" class="btn-group-sm">
            <button id="add" class="btn btn-info" onclick="newModule()">
                <i class="glyphicon glyphicon-expand"></i> 发布广告
            </button>
            <button id="edit" class="btn btn-info" onclick="editModule()">
                <i class="glyphicon glyphicon-edit"></i> 内容编辑
            </button>
            <button id="remove" class="btn btn-info" onclick="delRow()">
                <i class="glyphicon glyphicon-remove"></i> 删除
            </button>
            <%--<button id="view" class="btn btn-info" onclick="viewImage()">--%>
                <%--<i class="glyphicon glyphicon-remove"></i> 预览--%>
            <%--</button>--%>
            <button id="refresh" class="btn btn-info" name="refresh" >
                <i class="glyphicon glyphicon-refresh"></i> 刷新
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="uid" data-checkbox="true" align="center"></th>
                <th data-field="adposname" data-editable="false"  align="center" >广告位名称</th>
                <%--<th data-field="imgurl" data-editable="false"  align="center" >缩略图</th>--%>
                <th data-field="adtype" data-editable="false"  align="center" >广告类型</th>
                <th data-field="adclientname" data-editable="false"  align="center" >所属客户</th>
                <th data-field="adprice" data-editable="false"  align="center" >发布价格</th>
                <th data-field="starttime" data-editable="false"  align="center" >开始时间</th>
                <th data-field="endtime" data-editable="false"  align="center" >结束时间</th>
                <th data-field="clickcount" data-editable="false"  align="center" >点击量</th>
                <th data-field="creator" data-editable="false"  align="center" >发布人</th>
                <th data-field="createtime" data-editable="false"  align="center" >发布时间</th>
            </tr>
            </thead>
        </table>
    </div>
    <!-- -->
    <div class="modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content" style="width:700px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-body" id="defaultForm" method="post">
                    <div class="row">
                        <input type="hidden" name="fid" id="fid"/>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>广告位置</label>
                                <select class="form-control" id="sel_adpos"  name="sel_adpos" >

                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>广告价格</label>
                                <input class="form-control" id="txt_adprice"  name="txt_adprice" placeholder="广告价格" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>广告类型</label>

                                <select class="form-control" id="sel_adtype"  name="sel_adtype" >
                                    <option value="图片">图片</option>
                                    <option value="视频">视频</option>
                                    <option value="链接">链接</option>
                                    <option value="其他">其他</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>广告导航</label>
                                <input class="form-control" id="txt_adurl"  name="txt_adurl" placeholder="导航地址" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>开始时间</label>
                                <!--指定 date标记-->
                                <div class="input-group date form_datetime" data-date="<%=strCurrent%>"
                                     data-date-format="yyyy-mm-dd" data-link-field="dt_stime">
                                    <input class="form-control" size="16" type="text" value="" readonly>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                </div>
                                <input type="hidden" id="dt_stime" value="" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>结束时间</label>
                                <!--指定 date标记-->
                                <div class="input-group date form_datetime" data-date="<%=strCurrent%>"
                                     data-date-format="yyyy-mm-dd " data-link-field="dt_etime">
                                    <input class="form-control" size="16" type="text" value="" readonly>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                                </div>
                                <input type="hidden" id="dt_etime" value="" />
                                <%--<input class="form-control" id="dt_etime"  name="dt_etime" placeholder="结束时间" type="text">--%>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="up_imgfile">上传广告图片</label>
                                <input  id="up_imgfile"  name="up_imgfile"  type="file">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>图片地址</label>
                                <input class="form-control" id="txt_url"  name="txt_url" placeholder="可上传或手工输入图片地址" type="text">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>广告联系人</label>
                                <select class="form-control" id="sel_adclient"  name="sel_adclient" >

                                </select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group" >
                                <label>是否启用</label>
                                <div class="form-control"><%--<div class="form-control">--%>
                                    <label class="radio-inline">
                                        <input type="radio" name="isstart" id="inlineRadio1" value="open" checked> 开启
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="isstart" id="inlineRadio2" value="close">关闭
                                    </label>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"  onclick="resetForm()">关闭</button>
                    <button type="button" class="btn btn-primary" id="validateBtn">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
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
