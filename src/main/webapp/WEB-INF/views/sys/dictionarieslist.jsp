<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>字典管理</title>
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
    <!--时间控件-->
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <!--树-->
    <script type="text/javascript" src="<%=basePath%>/static/js/ztree3d5/js/jquery.ztree.all.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/js/ztree3d5/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <link rel="stylesheet" href="<%=basePath%>/static/js/ztree3d5/css/demo.css" type="text/css" />
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
    function initTable(treeId) {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/dictionaries/getDictList.do", //获取数据的Servlet地址
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
                    offset: params.offset,
                    treeId:treeId
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
        $.fn.zTree.init($("#DictTree"), setting);//页面树初始化
        //调用函数，初始化表格
        initTable();
        //校验
        initValidate();
        $("#dictForm").submit(function(ev){ev.preventDefault();});//AJAX提交必修使用
        $('#submitBtn').click(function() {
            var bootstrapValidator = $("#dictForm").data('bootstrapValidator');//必须
            bootstrapValidator.validate();
            if(bootstrapValidator.isValid()) {
                submit();
            }else{
                alert("请按照要求填写");
                return;
            }
        });
        $(".close").click(function (){
            $('#dictForm').data('bootstrapValidator').resetForm(true);
        })
    });
    //页面树数据源
    var setting = {
        check: {
            enable: false,
        },
        data: {
            simpleData: {
                enable: true,
            }
        },
        async: {
            enable: true,
            url:"<%=basePath%>/dictionaries/getDictTree.do"//树数据路径
        },
        //回调函数
        callback: {
            onClick: getDict
        }
    };
    //树单击事件
    function getDict(event, treeId, treeNode) {
        initTable(treeNode.id);
        $("#saveparentid").val(treeNode.id);
        $("#saveparentname").val(treeNode.name);
    }
    //校验
    function initValidate(){
        $('#dictForm').bootstrapValidator({
            message: '值不能为空',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                groupname:{
                    validators:{
                        notEmpty: {
                            message: '组名不能为空!'
                        }
                    }
                },
                keyname:{
                    validators:{
                        notEmpty: {
                            message: '键名不能为空!'
                        }
                    }
                },
                keyvalue:{
                    validators:{
                        notEmpty: {
                            message: '键值不能为空!'
                        }
                    }
                },
            }
        });
    }
//添加和编辑提交按钮
    function submit(){
        var myDictLabel=$("#myDictLabel").text();//添加编辑用户窗口
        if(myDictLabel.indexOf("添加") !=-1){
            saveDict();//添加提交
        } else{
            updateDict();//修改提交
        }
    }
    //初始化表单
    function init(){
        $("#uid").val("");
        $("#groupname").val("");
        $("#keyname").val("");
        $("#parentid").val("");
        $("#keyvalue").val("");
        $("#remarket").val("");
    }
//添加窗口
    function newDict(){
        init();
        var parentid=$("#saveparentid").val();
        var parentname=$("#saveparentname").val();
        if(parentid == ""){
            $("#saveparentid").val("0");
        }
        $("#parentid").val(parentname);
        $("#myDictLabel").html("添加");
        $('#newDict').modal('show');
    }
//添加用户提交保存
    function saveDict(){
        var groupname=$("#groupname").val();
        var keyname=$("#keyname").val();
        var parentid=$("#saveparentid").val();
        var keyvalue=$("#keyvalue").val();
        var remarket=$("#remarket").val();
        $.ajax({
           type:"POST",
            url:"<%=basePath%>/dictionaries/addDict.do",
            data:{
                groupname:groupname,
                keyname:keyname,
                parentid:parentid,
                keyvalue:keyvalue,
                remarket:remarket
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("添加成功!");
                    $.fn.zTree.init($("#DictTree"), setting);//页面树初始化
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#dictForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("添加记录失败");
                }
            }
        });
        $('#newDict').modal('hide');
    }
//编辑
    function editDictionaries(){
        $("#myDictLabel").html("修改");
        init();
        var arr = $('#cusTable').bootstrapTable('getSelections');
        var ids = getCheckUid();
        if(arr.length>0) {
            var uids = getIdSelections();
            if (uids.length > 1) {
                warningInfo("请选择一条记录");
            } else {
                $.ajax({
                    type: "POST",
                    url: "<%=basePath%>/dictionaries/getDictById.do",
                    data: {
                        ids: ids
                    },
                    success: function (data) {
                        var msg = eval("(" + data + ")");
                        $("#uid").val(ids);
                        $("#groupname").val(msg[0].groupname);
                        $("#keyname").val(msg[0].keyname);
                        $("#keyvalue").val(msg[0].keyvalue);
                        $("#saveparentid").val(msg[0].parentid);
                        $("#parentid").find("option").remove();
                        var varItem2="";
                        if(msg[0].parentName == ""){
                            varItem2 = new Option("无","");
                        }else{
                            varItem2 = new Option(msg[0].parentName,"");
                        }
                        $("#parentid").append(varItem2);
                        $("#remarket").val(msg[0].remarket);
                    }
                });
                $('#newDict').modal('show');
            }
        }else{
            warningInfo("请选择一条记录");
        }

    }
    //提交更新
    function updateDict(){
        var uid=$("#uid").val();
        var groupname=$("#groupname").val();
        var keyname=$("#keyname").val();
        var keyvalue=$("#keyvalue").val();
        var parentid=$("#saveparentid").val();
        var remarket=$("#remarket").val();
        $.ajax({
            type:"POST",
            url:"<%=basePath%>/dictionaries/updateDict.do",
            data:{
                uid:uid,
                groupname:groupname,
                keyname:keyname,
                keyvalue:keyvalue,
                parentid:parentid,
                remarket:remarket
            },
            success:function(data){
                if(data!=="failed"){
                    successInfo("修改成功!");
                    $.fn.zTree.init($("#DictTree"), setting);//页面树初始化
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                    $('#dictForm').data('bootstrapValidator').resetForm(true);
                }else{
                    errorInfo("修改记录失败");
                }
            }
        });
        $('#newDict').modal('hide');
    }

function delRow(){
    var arr = $('#cusTable').bootstrapTable('getSelections');
    if(arr.length>0) {
        var uid = getIdSelections()
        if (uid.length > 1) {
            warningInfo("请选择一条记录");
        } else {
            (confirmInfo("确认删除当前记录?")).then(function (status) {
                if (status == true) {
                    deleteDictionaries();
                }
            });
        }
    }else{
        warningInfo("请选择要删除的记录");
    }
}
    //删除
function deleteDictionaries(){
    var ids = getIdSelections();
     $.ajax({
        type: "POST",
        url: "<%=basePath%>/dictionaries/delDictById.do",
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
        uids += $(this).val();
    });
    return uids;
}
    //清空校验
    function resetForm(){
        $('#dictForm').data('bootstrapValidator').resetForm(true);
    }
</script>
<body id="loading" class="style_body">
<div style="padding:0 10px;float: left;">
    <ul style="background: none;border: none;" id="DictTree" class="ztree"></ul>
</div>
<div class=" style_border">
    <div id="toolbar" class="btn-group-sm">
        <button id="add" class="btn btn-info" onclick="newDict()">
            <i class="glyphicon glyphicon-expand"></i> 增加
        </button>
        <button id="edit" class="btn btn-info" onclick="editDictionaries()">
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
            <th data-field="groupname" data-editable="false"  align="center">组名</th>
            <th data-field="keyname" data-editable="false" align="center">键名</th>
            <th data-field="keyvalue" data-editable="false" align="center">键值</th>
            <th data-field="parentid" data-editable="false" align="center">父级</th>
            <th data-field="remarket" data-editable="false" align="center">备注</th>
        </tr>
        </thead>
    </table>
    </div>

    <div class="modal fade" id="newDict" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myDictLabel"></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                        <div id="dictForm" method="post">
                            <div class="col-md-6">
                                <input class="form-control" id="uid"  type="hidden">
                                <div class="form-group">
                                    <label>组名</label>
                                    <input class="form-control" id="groupname" name="groupname"  type="text">
                                </div>
                                <div class="form-group">
                                    <label>键名</label>
                                    <input class="form-control" id="keyname" name="keyname" type="text">
                                </div>
                                <div class="form-group">
                                    <label>键值</label>
                                    <input class="form-control" id="keyvalue" name="keyvalue" type="text">
                                </div>
                                <!-- /.form-group -->
                            </div>
                            <!-- /.col -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>父级</label>
                                    <input id="saveparentid" type="hidden"/>
                                    <input id="saveparentname" type="hidden"/>
                                    <input readonly class="form-control" id="parentid" name="parentid"  type="text"/>
                                </div>
                                <div class="form-group">
                                    <label>备注</label>
                                    <input class="form-control" id="remarket" type="text">
                                </div>
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