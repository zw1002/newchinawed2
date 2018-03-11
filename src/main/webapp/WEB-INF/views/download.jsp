<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
    <title>下载</title>
    <meta charset="utf-8">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/css/trip.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <%--附件下载--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/download.js"></script>
    <style type="text/css">
        /**{font-family: arial,"微软雅黑"}*/
    </style>
</head>
<script >
    /**
     * 初始化
     */
    $(document).ready(function () {
        //附件下载
        loadAttch();
    });
    function loadAttch(){
        $("div[ext=attch]").each(function(){
            var fid=$(this).attr("id");
            $.ajax({
                type:"POST",
                url:"<%=basePath%>/upload/findRelateFile.do",
                data:{
                    refId:fid
                },
                error:function(){
                },
                success:function(data){
                    if(data !== "failed"){
                        $("div[ext=attch][id="+fid+"]").html("");
                        $("div[ext=attch][id="+fid+"]").append(data);
                        $("div[ext=attch][id="+fid+"]").show();
                        $("tr[ext=attch][id="+fid+"]").show();
                    }
                }
            });
        });
    }
    function download(fileId) {
        location.href="<%=basePath%>/upload/downloadFile.do?accessoryId=" + fileId;
    }
</script>
<body id="loading" class="style_body">
<div class="container">
    <div class="style_border">
        <div class="font14_row" style="padding: 25px 35px;margin-bottom: 10px;">
            <h4 class="font14">附  件</h4>
            <div style="overflow: auto">
                <!--此ID是关联ID-->
                <div ext="attch" id="22222222222222" style="display: none;float: left;width:33%;">
                </div>
            </div>
        </div>
    </div>
</div>
</body>