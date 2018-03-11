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
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
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
    <!--校验-->
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
            url: "<%=basePath%>/integral/getIntegralList.do", //获取数据的Servlet地址
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
            sidePagination: "server", //表示服务端请求
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
        $("#integralForm").submit(function(ev){ev.preventDefault();});//AJAX提交必修使用
        $('#submitBtn').click(function() {
            var bootstrapValidator = $("#integralForm").data('bootstrapValidator');//必须
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                submit();
            }else{
                alert("请按照要求填写");
                return;
            }
        });
        $(".close").click(function (){
            $('#integralForm').data('bootstrapValidator').resetForm(true);
        })
    });
    //校验
    function initValidate(){
        $('#integralForm').bootstrapValidator({
            message: '值不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                mininum:{
                    validators:{
                        notEmpty: {
                            message: '积分最小值不能为空!'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '积分最小值只能由整数组成'
                        }
                    }
                },
                maxinum:{
                    validators:{
                        notEmpty: {
                            message: '积分最大值不能为空!'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '积分最大值只能由整数组成'
                        }
                    }
                },
            }
        });
    }
    //添加和编辑提交按钮
    function submit(){
        var integralLabel=$("#integralLabel").text();//添加编辑用户窗口
        if(integralLabel.indexOf("新增") !=-1){
            saveIntegral();//添加提交
        } else{
            updateIntegral();//修改提交
        }
    }
    //初始化表单
    function init(){
        $("#uid").val("");
        $("#mininum").val("");
        $("#maxinum").val("");
        $("#divided").val("");
        $("#basedeposit").val("");
    }
    //添加窗口
    function newIntegral(){
        init();
        $("#integralLabel").html("新增");
        $('#newIntegral').modal('show');
    }
    //新增提交保存
    function saveIntegral(){
        var mininum=$("#mininum").val();
        var maxinum=$("#maxinum").val();
        var grade=$("#grade").val();
        var divided=$("#divided").val();
        var basedeposit=$("#basedeposit").val();
        $.ajax({
            type:"POST",
            url:"<%=basePath%>/integral/addIntegral.do",
            data:{
                mininum:mininum,
                maxinum:maxinum,
                grade:grade,
                divided:divided,
                basedeposit:basedeposit
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("添加成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#integralForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("添加记录失败");
                }
            }
        });
        $('#newIntegral').modal('hide');
    }

    //编辑
    function editIntegral(){
        $("#integralLabel").html("修改");
        init();
        var arr = $('#cusTable').bootstrapTable('getSelections');
        var uid = getCheckUid();
        if(arr.length>0) {
            var uids = getIdSelections();
            if (uids.length > 1) {
                warningInfo("请选择一条记录");
            } else {
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>/integral/getIntegralByUid.do",
                    data: {
                        uid: uid
                    },
                    success: function (data) {
                        var msg = eval("(" + data + ")");
                        $("#uid").val(uid.replace(",", ""));
                        $("#mininum").val(msg.mininum);
                        $("#maxinum").val(msg.maxinum);
                        $("#grade").val(msg.grade);
                        $("#divided").val(msg.divided);
                        $("#basedeposit").val(msg.basedeposit);
                    }
                });
                $('#newIntegral').modal('show');
            }
        }else{
            warningInfo("请选择一条记录");
        }
    }

    //提交更新
    function updateIntegral(){
        var uid=$("#uid").val();
        var mininum=$("#mininum").val();
        var maxinum=$("#maxinum").val();
        var grade=$("#grade").val();
        var divided=$("#divided").val();
        var basedeposit=$("#basedeposit").val();
        $.ajax({
            type:"POST",
            url:"<%=basePath%>/integral/updateIntegral.do",
            data:{
                uid:uid,
                mininum:mininum,
                maxinum:maxinum,
                grade:grade,
                divided:divided,
                basedeposit:basedeposit
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("修改成功!");
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#integralForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("修改记录失败");
                }
            }
        });
        $('#newIntegral').modal('hide');
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
        var ids = getIdSelections();
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/integral/deleteIntegralByUid.do",
            data: {
                uid:getCheckUid()
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
            uids += $(this).val();
        });
        return uids;
    }
    //清空校验
    function resetForm(){
        $('#integralForm').data('bootstrapValidator').resetForm(true);
    }
</script>
<body id="loading" class="style_body">
<div class=" style_border">
    <div id="toolbar" class="btn-group-sm">
        <button id="add" class="btn btn-info" onclick="newIntegral()">
            <i class="glyphicon glyphicon-expand"></i> 增加
        </button>
        <button id="edit" class="btn btn-info" onclick="editIntegral()">
            <i class="glyphicon glyphicon-edit"></i> 修改
        </button>
        <button id="remove" class="btn btn-info" onclick="delRow()">
            <i class="glyphicon glyphicon-remove"></i> 删除
        </button>
    </div>
    <table id="cusTable" class="table">
        <thead>
        <tr>
            <th data-field="uid" data-checkbox="true" align="center"></th>
            <th data-field="mininum" data-editable="false"  align="center">积分最小值</th>
            <th data-field="maxinum" data-editable="false" align="center">积分最大值</th>
            <th data-field="grade" data-editable="false" align="center">等级</th>
            <th data-field="divided" data-editable="false" align="center">抽成比例</th>
            <th data-field="basedeposit" data-editable="false" align="center">基础押金</th>
        </tr>
        </thead>
    </table>
    </div>
<div class="modal fade" id="newIntegral" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:400px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="integralLabel"></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div id="integralForm">
                        <div class="col-md-6">
                            <input class="form-control" id="uid" type="hidden">
                            <div class="form-group">
                                <label>积分最小值</label>
                                <input style="width:315px" class="form-control" id="mininum" name="mininum">
                            </div>
                            <!-- /.form-group -->
                            <div class="form-group">
                                <label>积分最大值</label>
                                <input style="width:315px" class="form-control" id="maxinum" name="maxinum">
                            </div>
                            <div class="form-group">
                                <label>等级</label>
                                <select style="width:315px" class="form-control" id="grade" name="grade">
                                    <option value="1">初级</option>
                                    <option value="2">中级</option>
                                    <option value="3">高级</option>
                                    <option value="4">特级</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>抽成比例</label>
                                <input style="width:315px" class="form-control" id="divided" name="divided">
                            </div>
                            <div class="form-group">
                                <label>基础押金</label>
                                <input style="width:315px" class="form-control" id="basedeposit" name="basedeposit">
                            </div>
                            <!-- /.form-group -->
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