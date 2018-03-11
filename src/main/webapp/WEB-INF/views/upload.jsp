<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/js/webuploader/webuploader.css">
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <!--引入CSS-->
    <script type="text/javascript" src="<%=basePath%>/static/js/webuploader/webuploader.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/webuploadfile.js"></script>
    <script>
        $(function () {
            $("#uploader").powerWebUpload({auto: false,fileNumLimit:5 });
            $("#webuploadbtn").css("display","none");
        })
        function cs(){
            var oBtn = document.getElementById('webuploadbtn');
            oBtn.click();
        }
    </script>
    <title>首页</title>
</head>
<div id="uploader" style="margin-left:10px">
    <button id="webuploadbtn" class="webuploadbtn">lala</button>
</div>
<button onclick="cs()">自定义上传按钮</button>
<input type="hidden" id="relateId" value="22222222222222,2,2">
</body>
</html>
