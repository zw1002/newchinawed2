<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<%=basePath%>/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap.min.css">
    <link href="<%=basePath%>/bootstrap/flatlab-master/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>/font-awesome/css/font-awesome.min.css">

    <link href="<%=basePath%>/css/selectuser.css" rel="stylesheet">
    <!--引入CSS-->
    <%--提示框--%>
    <script src="<%=basePath%>/js/jquery.noty.packaged.min.js"></script>
    <script src="<%=basePath%>/js/showinfo.js"></script>


    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/selectbox.js"></script>
    <script src="<%=basePath%>/js/jquery-ui.min.js"></script>
    <script>
        function SelectUserCallbackNew() {
           // $autohint.initFromOuter($("#manageUserId"));
        };
        $(document).ready(function() {
            initData($("#editselect"),"06fc57d2-4fb1-491a-8bbd-7a04755c4ca8","楚凯");

        });
        function showids(){
            var userIds = $("#selectcheck").data("ids") + "";
            alertInfo(userIds);
        }

    </script>
    <title>首页</title>
</head>
<body>
<div class="container">
    <div class="row">
        <!-- form: -->
        <section>
            <div class="col-lg-8 col-lg-offset-2">
                <div class="page-header">
                    <h2>人员选择</h2>
                </div>

                <div class="form-group">
                    <span><input type="text" name="manageUserId" id="selectcheck" readonly style="width: 240px"/></span>
                    <i class="fa fa-user" onclick="SelectBox.show($('#selectcheck'), false,'user')"></i>
                </div>

                <div class="form-group">
                    <span><input type="text" name="manageUserId" id="selectradio" readonly style="width: 240px"/></span>
                    <i class="fa fa-user" onclick="SelectBox.show($('#selectradio'), true,'user')"></i>
                </div>


                <div class="form-group">
                    <span><input type="text" name="manageUserId" id="editselect" readonly style="width: 240px"/></span>
                    <i class="fa fa-user" onclick="SelectBox.show($('#editselect'), false,'user')"></i>
                </div>

                <button type="button" class="btn btn-info" id="validateBtn" onclick="showids()">取值</button>
            </div>


        </section>
        <!-- :form -->
    </div>
</div>
</body>
</html>
