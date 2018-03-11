<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<%!
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 基础CSS类库可随意更改 -->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/less.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/basic.css">
    <link rel="icon" href="<%=basePath%>/static/img/ico.ico" type="image/x-icon"/>
    <title>婚秀中国网后台管理系统</title>
    <script type="text/javascript" src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/js/cookie.min.js"></script>
    <%--提示框--%>
    <script src="<%=basePath%>/static/js/jquery.noty.packaged.min.js"></script>
    <script src="<%=basePath%>/static/js/showinfo.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#account").val(cookie.get('account'));
            if ($.trim($("#account").val()) == "") {
                $("#account").focus();
            } else {
                $("#password").focus();
            }

            $("#account").keydown(function (event) {
                switch (event.keyCode) {
                    case 13:
                        $("#password").focus();
                        break;
                    default:
                        break;
                }
            });

            $("#password").keydown(function (event) {
                switch (event.keyCode) {
                    case 13:
                        login();
                        break;
                    default:
                        break;
                }
            });
//           记住账户
            if (!localStorage.enable) {
                document.getElementById("checkboxId").checked = false;
            }else {
                document.getElementById("checkboxId").checked = true;
            }

            var account = document.getElementById("account");
            if (localStorage.account) {
                account.value = localStorage.account;
            }
        });
        //验证用户信息
        function checkInfo() {
            var message = "";
            return true;
        }

        function login() {
            if (!checkInfo()) {
                return;
            }
            //           记住账户
            var account = document.getElementById("account");
            if (document.getElementById("checkboxId").checked) {
                localStorage.enable = true;
                localStorage.account = account.value;
            } else {
                localStorage.enable = "";
                localStorage.account = "";
            }

            //用户名和密码都填写时，向服务端提交用戶信息验证
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "<%=basePath%>/dologin.do",
                data: JSON.stringify({
                    account: $("#account").val(),
                    password: $("#password").val()
                }),
                success: function (data) {
                    var msg = eval("(" + data + ")");
                    if (msg.state !== "false") {
                        cookie.set('account', $("#account").val());
                        alertInfo(msg.msg);
                        document.location.href = '<%=basePath%>/index.do';
                    } else {
                        $("#account").val("");
                        $("#account").focus();
                        $("#password").val("");
                        errorInfo(msg.msg);
                    }
                }
            });
        }

    </script>
    <!-- 基础CSS类库可随意更改 -->

    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: arial, "微软雅黑"
        }

        body {
            position: relative;
            background: url(<%=basePath%>/static/img/cbd2.png) no-repeat fixed 0 0;
            background-size: cover;
            height: 100%;
            padding: 0;
            margin: 0;
            overflow: hidden;
        }

        .title-text {
            font-size:40px;
            position: absolute;
            left: 10%;
            top: 38%;
            color: #fff;
            line-height: 56px;
            text-shadow: 3px 3px 3px #005190;
            word-wrap: break-word;
            width: 40%;
            padding: 10px;
        }

        .login-topStyle3 {
            background: url(<%=basePath%>/static/img/login_bg_white.png) center center repeat;
            width: 280px;
            height: auto;
            overflow: hidden;
            top: 26%;
            right: 15%;
            position: absolute;
            border-radius: 5px;
            padding: 34px 34px;
            box-shadow: 0 3px 12px rgba(0, 0, 0, 0.4);
        }

        .login-topStyle4 {
            background: url(<%=basePath%>/static/img/login_bg_white.png) center center repeat;
            width: 280px;
            eight: auto;
            overflow: hidden;
            top: 26%;
            right: 51%;
            position: absolute;
            border-radius: 5px;
            padding: 34px 34px;
        }

        .login-topStyle3 h3 {
            font-size: 22px;
            line-height: 33px;
            color: #0000;
            padding-bottom: 22px;
            text-align: center;
        }
        .login-topStyle4 h3 {
            font-size: 22px;
            line-height: 33px;
            color: #ffffff;
            padding-bottom: 22px;
            text-align: center;
        }

        .error-information {
            color: #d02530;
            font-size: 12px;
            padding-bottom: 3px;
        }

        .ui-form-item {
            margin-bottom: 15px;
            font-size: 12px;
            line-height: 18px;
        }

        .ui-form-item.loginUsername input[type="username"], .ui-form-item.loginPassword input[type="password"] {
            width: 100%;
            border: 1px solid	#A9A9A9;
            padding: 8px 0px 8px 5px;

        }

        .login_reme {
            padding-bottom: 8px;
            margin-top: 20px \0;
        }

        .login_reme a.reme1 {
            font-size: 12px;
            line-height: 18px;
            vertical-align: top;
        }

        span.error_xinxi {
            font-size: 14px;
            line-height: 22px;
        }

        button.btn-register {
            color: #ffffff;
            background-color: #3598db;
            width: 280px;
            font-size: 14px;
            height: 36px;
            border: none;
            cursor: pointer;
            margin: 10px 0px 0px 0px;
        }

        .footer {
            height: 30px;;
            width: 100%;
            text-align: center;
            line-height: 30px;
            color: #fff;
            font-size: 14px;
        }
        /*新增样式*/
        .login-topStyle5 {
            background: url(<%=basePath%>/static/img/login_bg_white.png) center center repeat;
            width:100%;
            height: 15%;
            overflow: hidden;
            top: 31%;
            right:0;
            position: absolute;
            border-radius: 5px;
            padding: 34px 34px;
        }

    </style>
</head>
<body>
<div class="title-text">婚秀中国网后台管理系统</div>
<div class="login-topStyle5" ></div>
<div class="login-topStyle3" id="loginStyle" style="background-color: #ffffff">
    <h3 style="color: darkgray">用户登录</h3>
    <!--输入错误提示信息，默认是隐藏的，把display:none，变成block可看到-->
    <div class="error-information" style="display:none;">您输入的密码不正确，请重新输入</div>
    <div class="ui-form-item loginUsername">
        <input type="username" placeholder="用户名/手机号/密码" id="account">
    </div>
    <div class="ui-form-item loginPassword">
        <input type="password" placeholder="请输入密码" id="password">
    </div>
    <div class="login_reme">
        <input type="checkbox" id="checkboxId">
        <a class="reme1">记住账号</a></div>
    <span class="error_xinxi" style="display:none;">您输入的密码不正确，请重新输入</span>
    <button type="button " class="btn-register" id="submitImg" onclick="login()"> 立即登录</button>
</div>

<div class="footer" style="position: absolute;bottom:2%;"> 技术支持:河南青居电子商务有限公司</div>
</body>
</html>