<%--作品审核管理
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017-12-04
  Time: 0:45
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
    <meta charset="utf-8">
    <title>作品管理</title>
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
            method: "post",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/worksMgr/getWorksList.do", //获取数据的Servlet地址
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
                    offset: params.offset,
                    displayFlag:'0'
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
    });


    //提交保存
    function saveModule(){

        $.ajax({
            type:"post",
            async:false,
            url: "<%=basePath%>/worksMgr/updateWorksState.do",
            data:{
                uid:$('#fid').val(),
                displayFlag:'2'
            },
            success: function(data){
                hideWait();
                if(data!=="failed"){
                    successInfo("审核成功!")
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("保存失败");

                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }
            }
        });
        $('#newModal').modal('hide');
    }
    //审核
    function editModule() {
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length==1) {
            //var row = getSelection();
            $("#myModalLabel").html("审核作品信息");
            $('#txt_workname').html("<span class='label label-primary'>" + arr[0].worksname + "</span>");
            $('#txt_worklabel').html("<span class='label label-primary'>" + arr[0].worklabel + "</span>");
            $('#txt_workremark').html(arr[0].workremark);
            $('#fid').val(arr[0].uid);
            $("#img_sh").attr('src', '<%=basePath%>/' + arr[0].worksurl);
            $('#newModal').modal('show');
        } else  warningInfo("请选择单条记录!");
    }
    //退回
    function delRow() {
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length==1){

            $.ajax({
                type:"post",
                async:false,
                url: "<%=basePath%>/worksMgr/updateWorksState.do",
                data:{
                    uid:arr[0].uid,
                    displayFlag:'1'
                },
                success: function(data){
                    hideWait();
                    if(data!=="failed"){
                        successInfo("退回成功!")
                        $('#cusTable').bootstrapTable('refresh');//初始化数据
                    }else{
                        errorInfo("退回失败,请重试");

                        $('#cusTable').bootstrapTable('refresh');//初始化数据
                    }
                }
            });
        }else  warningInfo("请选择单条记录!");
    }
    //查看图片
    function viewImage() {
        var arr = $('#cusTable').bootstrapTable('getSelections');
        if(arr.length==1){

            $("#img_info").attr('src','<%=basePath%>/'+arr[0].worksurl);
            $('#viewImgModel').modal('show');
        }
        else  warningInfo("请选择单条记录!");

    }
    //取表格行数
    function getIdSelections() {
        return $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
            return row.uuid;
        });
    }
    //获取选择行数据
    function getSelection() {
        return $.map($('#cusTable').bootstrapTable('getAllSelections'), function (row) {
            return row;
        });
    }
</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div >
        <div id="toolbar" class="btn-group-sm">
            <!--<button id="add" class="btn btn-info" onclick="newModule()">
                <i class="glyphicon glyphicon-expand"></i> 增加
            </button>-->
            <button id="edit" class="btn btn-info" onclick="editModule()">
                <i class="glyphicon glyphicon-edit"></i> 审核
            </button>
            <button id="remove" class="btn btn-info" onclick="delRow()">
                <i class="glyphicon glyphicon-remove"></i> 退回
            </button>
            <button id="viewImage" class="btn btn-info" onclick="viewImage()">
                <i class="glyphicon glyphicon-remove"></i> 预览图片
            </button>
            <button id="refresh" class="btn btn-info" name="refresh" >
                <i class="glyphicon glyphicon-refresh"></i> 刷新
            </button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="uuid" data-checkbox="true" align="center"></th>
                <th data-field="worktypename" data-editable="false"  align="center" >作品类型</th>
                <th data-field="worksname" data-editable="false"  align="center" >作品名称</th>
                <th data-field="dpinum" data-editable="false"  align="center" >分辨率</th>
                <th data-field="imgsize" data-editable="false"  align="center" >图片大小</th>
                <th data-field="imgformart" data-editable="false"  align="center" >图片格式</th>
                <th data-field="colrmodel" data-editable="false"  align="center" >颜色模式</th>
                <th data-field="worklabel" data-editable="false"  align="center" >作品标签</th>
                <th data-field="workremark" data-editable="false"  align="center" >作品简介</th>
                <th data-field="merchname" data-editable="false"  align="center" >作品所有人</th>
                <th data-field="uptime" data-editable="false"  align="center" >上传时间</th>
            </tr>
            </thead>
        </table>
    </div>

    <div class="modal fade" id="newModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content" style="width:980px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-body" id="defaultForm" method="post" style="    overflow-y: auto;height: 580px;">
                    <div class="row">
                        <input type="hidden" name="fid" id="fid"/>
                        <div class="col-md-12">
                            <img id="img_sh" src="" style="width:900px;height: 500px;">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6" style="padding-top: 15px;">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <label for="txt_workname">作品名称：</label>
                                    <div id="txt_workname"></div>

                                </div>
                            </div>
                        </div>
                        <div class="col-md-6" style="padding-top: 15px;">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <label for="txt_worklabel">作品标签：</label>
                                    <div id="txt_worklabel"></div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="row">

                        <div class="col-md-12">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h3 class="panel-title">作品简介</h3>
                                </div>
                                <div class="panel-body" id="txt_workremark">

                                </div>
                            </div>

                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"  onclick="resetForm()">关闭</button>
                    <button type="button" class="btn btn-primary" id="validateBtn" onclick="saveModule()">审核通过</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <div class="modal fade" id="viewImgModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 900px;height: 580px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="vi_title">查看图片</h4>
                </div>
                <div class="modal-body" style="height: 460px;overflow-y: auto;overflow-x: auto;">
                    <img id="img_info" src="">
                </div>

                <div class="modal-footer">
                    <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
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
