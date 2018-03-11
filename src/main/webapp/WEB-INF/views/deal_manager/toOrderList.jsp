<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>交易订单管理</title>
    <meta charset="utf-8">
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
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/language/zh_CN.js"></script>
    <%--自建公共js文件--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/common-creat.js"></script>
    <!--时间-->
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
    function initTable(flag,creatTime,creatTimes) {
        //先销毁表格
        $('#cusTable').bootstrapTable('destroy');
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            contentType: "application/x-www-form-urlencoded",
            url: "<%=basePath%>/dealOrderMgr/getDealorderList.do", //获取数据的Servlet地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            toolbar:"#toolbar",
            showRefresh: true,
            pageSize: 10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            exportDataType : "all",
            clickToSelect:false,
            idField:"uid",
            sidePagination: "server", //表示服务端请求
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType : "limit",
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    limit: params.limit,
                    offset: params.offset,
                    flag:flag,
                    creatTime:creatTime,
                    creatTimes:creatTimes
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
        initTable(3);
        InitializationTime();
    });
    //初始化时间控件
    function InitializationTime() {
        $('#creatTime').datetimepicker({
            format: 'yyyy-mm-dd',
            minView: "month",
            firstDay: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN',
            currentText: '今天',
        });
        $('#creatTimes').datetimepicker({
            format: 'yyyy-mm-dd',
            minView: "month",
            firstDay: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN',
            currentText: '今天',
        });
    };
    //查询数据
    function selectDate() {
        var flag = $("#flag").val();
        var creatTime = $("#creatTime").val();
        var creatTimes = $("#creatTimes").val();
        if(creatTime != "" && creatTimes == ""){
            errorInfo("时间区间不全");
        }else if(creatTimes != "" && creatTime == ""){
            errorInfo("时间区间不全");
        }else{
            initTable(flag,creatTime,creatTimes);
        }
    };
    //导出
    function exportExcel(){
        var flag = $("#flag").val();
        var creatTime = $("#creatTime").val();
        var creatTimes = $("#creatTimes").val();
        if(creatTime != "" && creatTimes == ""){
            errorInfo("时间区间不全");
        }else if(creatTimes != "" && creatTime == ""){
            errorInfo("时间区间不全");
        }else{
            window.location.href = "<%=basePath%>/dealOrderMgr/exportExcelDealorderList.do?flag="+flag+"&creatTime="+creatTime+"&creatTimes="+creatTimes;
        }
    }
    //重置
    function refersh(){
      $("#creatTime").val("");
      $("#creatTimes").val("");
    }
</script>
<body id="loading" class="style_body">
<div class="style_border">
    <div>
        <div id="toolbar" class="btn-group-sm">
            <label style="width:95px;margin-top: 5px" class="control-label col-md-1">订单类型:</label>
            <div style="margin-top: 5px;width: 130px;margin-left: -25px" class="col-md-4">
                <select id="flag" class="form-control">
                    <option value="3">全部</option>
                    <option value="0">进行中</option>
                    <option value="1">已完成</option>
                </select>
            </div>
            <label style="width:95px;margin-top: 5px;margin-left: -15px" class="control-label col-md-1">时间区间:</label>
            <div class="col-md-2">
                <input style="margin-left: -22px;margin-top: 5px" type="text" id="creatTime" name="creatTime" class="form-control" readonly>
            </div>
            <label style="margin-left: -40px;margin-top: 5px" class="control-label col-md-1">至:</label>
            <div class="col-md-2">
                <input style="margin-left: -45px;margin-top: 5px" type="text" id="creatTimes" name="creatTimes" class="form-control" readonly>
            </div>
            <button class="btn btn-info" style="margin-left: -20px;margin-top: 5px" onclick="refersh()">重置</button>
            <button class="btn btn-info" style="margin-left: 13px;margin-top: 5px" onclick="selectDate()">查询</button>
            <button class="btn btn-info" style="margin-left: 10px;margin-top: 5px" onclick="exportExcel()">导出订单统计表</button>
        </div>
        <table id="cusTable" class="table" >
            <thead>
            <tr>
                <th data-field="dealuid" data-checkbox="true" align="center">订单号</th>
                <th data-field="payuser"  data-editable="false" align="center">购买人</th>
                <th data-field="workname"  data-editable="false" align="center">作品信息</th>
                <th data-field="dealtime"  data-editable="false" align="center">交易时间</th>
                <th data-field="dealprice"  data-editable="false" align="center">交易金额</th>
                <th data-field="dealstate"  data-editable="false" align="center">交易状态</th>
            </tr>
            </thead>
        </table>
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