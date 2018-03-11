<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
    String basePaths = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<head>
    <title>商户审核管理</title>
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
    /**
     * 初始化数据
     */
    $(document).ready(function () {
        //调用函数，初始化数据
        var merchId=$("#merchId").val();
        var statu=$("#statu").val();
        if(statu == 1){
            $("#font14Row").css("display","none")
            $("#submitBtn").css("display","none");
        }else{
            $("#submitBtns").css("display","none");
            $("#submitBtnss").css("display","none");
            $("#font14Rows").css("display","none");
        }
        $.ajax({
            url:"<%=basePath%>/merchant/getExamineMerchantList.do",
            type:"POST",
            data:{
                merchId:merchId
            },
            success:function(data){
                if(data!=="failed"){
                    var msg=eval("("+data+")");
                    $("#merchname").text(msg[0].merchname);
                    $("#username").text(msg[0].username);
                    $("#userIcCode").text(msg[0].userIcCode);
                    $("#telephone").text(msg[0].telephone);
                    $("#bondvalue").text(msg[0].bondvalue);
                    $("#builddatetime").text(msg[0].builddatetime);
                    $("#merchscroe").text(msg[0].merchscroe);
                    $("#merchscroes").text(msg[0].merchscroes);
                    $("#bankaddress").text(msg[0].bankaddress);
                    $("#bankaccount").text(msg[0].bankaccount);
                    $("#bankname").text(msg[0].bankname);
                    if(msg[0].idpic != ""){
                        $("#idpic").html("<img src='<%=basePaths%>/chinaweb"+msg[0].idpic+"'>");
                    }
                    if(msg[0].statu != 1){
                        $("#font14Row").css("display","none");
                        $("#submitBtn").css("display","none");
                    }
                }else{
                    errorInfo("获取数据失败");
                }
            }
        });
    });
    //扣分提交
    function submit(){
        var id=$("#merchId").val();
        var delmerchscroe=$("#delmerchscroe").val();
        var merchscroes=$("#merchscroes").text();
        if(delmerchscroe == 0){
            window.parent.closeTab(id);
        }else{
            $.ajax({
                url:"<%=basePath%>/merchant/downgrade.do",
                type:"POST",
                data:{
                    id:id,
                    delmerchscroe:delmerchscroe,
                    merchscroes:merchscroes
                },
                success:function(data){
                    if(data!=="failed"){
                        (confirmInfo("扣除积分成功，是否关闭页面?")).then(function (status) {
                            if (status == true) {
                                window.parent.closeTab(id);
                            }
                        });
                    }else{
                        errorInfo("扣除积分失败");
                    }
                }
            });
        }
    }
    //填充商户的抽成周期
    function updateCycle(){
        var uid=$("#merchId").val();
        var cycle=$("#cycle").val();
        $.ajax({
            url:"<%=basePath%>/merchant/updateCycle.do",
            type:"POST",
            data:{
                uid:uid,
                cycle:cycle
            },
            success:function(data){
                if(data!=="failed"){
                    adopt();
                }else{
                    errorInfo("审核失败");
                }
            }
        });
    }
    //审核通过
    function adopt(){
        var uid=$("#merchId").val();
        var statu="1";
        $.ajax({
            url:"<%=basePath%>/merchant/examineOrFrozen.do",
            type:"POST",
            data:{
                uid:uid,
                statu:statu
            },
            success:function(data){
                if(data!=="failed"){
                    (confirmInfo("审核成功，是否关闭页面?")).then(function (status) {
                        if (status == true) {
                            window.parent.closeTab(uid);
                        }else{
                            $("#submitBtns").css("display","none");
                            $("#submitBtnss").css("display","none");
                        }
                    });
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("审核失败");
                }
            }
        });

    }
    //驳回
    function regect(){
        var uid=$("#merchId").val();
        var statu="2";
        $.ajax({
            url:"<%=basePath%>/merchant/examineOrFrozen.do",
            type:"POST",
            data:{
                uid:uid,
                statu:statu
            },
            success:function(data){
                if(data!=="failed"){
                    (confirmInfo("审核成功，是否关闭页面?")).then(function (status) {
                        if (status == true) {
                            window.parent.closeTab(uid);
                        }else{
                            $("#submitBtns").css("display","none");
                            $("#submitBtnss").css("display","none");
                        }
                    });
                    $('#cusTable').bootstrapTable('refresh');//初始化数据
                }else{
                    errorInfo("审核失败");
                }
            }
        });
    }
</script>
<body id="loading" class="style_body">
<img src="<%=basePath%>/static/img/bac1.png" style="position: fixed;top: -20px;left:-20px;width:10%;opacity: 0.3;">
<img src="<%=basePath%>/static/img/bac2.png" style="position: fixed;bottom: -20px;left:0;width:10%;opacity: 0.5;">
<img src="<%=basePath%>/static/img/bac3.png" style="position: fixed;top: 0;right:0;width:15%;opacity: 0.3;">
<img src="<%=basePath%>/static/img/bac4.png" style="position: fixed;bottom: 0;right:0;width:12%;opacity: 0.3;">
<div class="container" >
    <div class="style_border">
        <input type="hidden" id="merchId" value="<%= request.getAttribute("id")%>">
        <input type="hidden" id="statu" value="<%= request.getAttribute("statu")%>">
        <div class=" font14_row">
            <div>
                <h4 class="font14 control-label" >基本资料</h4>
                <div>
                    <div class="col-md-2" style="width:20%">
                        <div class="control-group">
                            <label>会员姓名:</label>
                            <label class="control-label" id="username" >
                            </label>
                        </div>
                    </div>
                    <div class="col-md-2" style="width:30%">
                        <div class="control-group">
                            <label>会员电话:</label>
                            <label class="control-label" id="telephone"></label>
                        </div>
                    </div>
                    <div class="col-md-2" style="width:30%">
                        <div class="control-group">
                            <label>身份证:</label>
                            <label class="control-label" id="userIcCode"></label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class=" font14_row">
            <div>
                <h4 class="font14 control-label" >认证信息</h4>
                <div id="idpic">
                </div>
            </div>
        </div>
        <div class="font14_row">
            <h4 class="font14">账户信息</h4>
            <div id="parentDiv" style="overflow:auto;">

                <div class="col-md-2" style="width:20%">
                    <div class="control-group">
                        <label>银行名称:</label>
                        <label class="control-label" id="bankname"></label>
                    </div>
                </div>
                    <div class="col-md-2" style="width:33%">
                        <div class="control-group">
                            <label>银行账号:</label>
                            <label class="control-label" id="bankaccount"></label>
                        </div>
                    </div>
                <div class="col-md-2" style="width:40%">
                    <div class="control-group">
                        <label>银行地址:</label>
                        <label class="control-label" id="bankaddress"></label>
                    </div>
                </div>
            </div>
        </div>
        <div class=" font14_row" style="height:85px;line-height:25px;margin-bottom: 20px;padding:25px 20px">
            <h4 class="font14">店铺信息</h4>
            <div class="col-md-2" style="width:50%">
                <div class="control-group">
                    <label>店铺名称:</label>
                    <label class="control-label" id="merchname" >
                    </label>
                </div>
            </div>
            <div class="col-md-2" style="width:21%">
                <div class="control-group">
                    <label>押金:</label>
                    <label class="control-label" id="bondvalue"></label>
                </div>
            </div>
            <div class="col-md-2" style="width:50%">
                <div class="control-group">
                    <label>申请时间:</label>
                    <label class="control-label" id="builddatetime"></label>
                </div>
            </div>
            <div class="col-md-2" style="width:20%">
                <div class="control-group">
                    <label>等级:</label>
                    <label class="control-label" id="merchscroe"></label>
                </div>
            </div>
        </div>
        <div id="font14Row" class="font14_row" style="height:85px;line-height:25px;margin-bottom: 20px;padding:25px 20px">
            <h4 class="font14">降级管理</h4>
            <div class="col-md-2" style="width:20%">
                <div class="control-group">
                    <label>店铺积分:</label>
                    <label class="control-label" id="merchscroes"></label>
                </div>
            </div>
            <div class="col-md-2" style="width:50%">
                <div class="control-group">
                    <label>扣除积分:</label>
                    <select style="margin-left: 70px;margin-top: -35px" class="form-control" id="delmerchscroe">
                        <option value="0">0</option>
                        <option value="500">500</option>
                        <option value="500">1000</option>
                    </select>
                </div>
            </div>
        </div>
        <div id="font14Rows" class="font14_row" style="height:85px;line-height:25px;margin-bottom: 20px;padding:25px 20px">
            <h4 class="font14">结算管理</h4>
            <div class="col-md-2" style="width:20%">
                <div class="control-group">
                    <label>结算周期:</label>
                    <select style="margin-left: 70px;margin-top: -35px" class="form-control" id="cycle">
                        <option value="0">周</option>
                        <option value="1">月</option>
                        <option value="2">季度</option>
                        <option value="3">年</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="" style="margin-left:46%;"><button type="button" onclick="submit()" id="submitBtn" class="btn btn-primary">确定</button></div>
        <div class="" style="margin-left:46%;">
            <button style="margin-left: -70px" type="button" onclick="updateCycle()" id="submitBtns" class="btn btn-primary">审核通过</button>
            <button style="margin-left: 50px" type="button" onclick="regect()" id="submitBtnss" class="btn btn-primary">驳回</button>
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