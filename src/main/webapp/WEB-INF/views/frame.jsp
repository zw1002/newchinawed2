<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hnqj.core.MenuTree" %>

<%
    ArrayList<MenuTree> menuLists = (ArrayList<MenuTree>) request.getAttribute("menuList");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<html lang="cn">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>婚秀中国网后台管理系统</title>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=basePath%>/static/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>/static/css/app.css">
    <link rel="stylesheet" href="<%=basePath%>/static/css/lanse.css" id="changeStyle4">
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <!--浏览器显示图标-->
    <link rel="icon" href="<%=basePath%>/static/img/ico.ico" type="image/x-icon"/>
    <link href="<%=basePath%>/static/bootstrap/flatlab-master/css/slidebars.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<%=basePath%>/static/bootstrap/flatlab-master/css/style.css" rel="stylesheet">
    <link href="<%=basePath%>/static/bootstrap/flatlab-master/css/style-responsive.css" rel="stylesheet"/>
    <script src="<%=basePath%>/static/js/common.js"></script>

    <%--<link href="<%=basePath%>/css/bootstrap-addtabs.css" rel="stylesheet">--%>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
      <script src="<%=basePath%>/js/html5shiv.min.js"></script>
      <script src="<%=basePath%>/js/respond.min.js"></script>
      <!--校验-->
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/validate/js/language/zh_CN.js"></script>
    <%--提示框--%>
    <script src="<%=basePath%>/static/js/jquery.noty.packaged.min.js"></script>
    <script src="<%=basePath%>/static/js/showinfo.js"></script>
    <script src="<%=basePath%>/static/js/app.js"></script>
    <script>
        $(function () {
            var item = {'id': '1', 'name': '首页', 'url': '<%=basePath%>/demo.do', 'closable': false};
            closableTab.addTab(item);
            $(".liclick").on("click", function () {
                var id = $(this).attr('tabId');
                var name = $(this).attr('title');
                var uri = $(this).attr('url');
                //var closable =1;
                var item = {'id': id, 'name': name, 'url': uri, 'closable': true};
                closableTab.addTab(item);
            });
            initValidate();//修改密码校验
            $("#userForm").submit(function (ev) {
                ev.preventDefault();
            });//AJAX提交必修使用
            $('#submitBtn').click(function () {
                var bootstrapValidator = $("#userForm").data('bootstrapValidator');//必须
                bootstrapValidator.validate();
                if (bootstrapValidator.isValid()) {
                    updatePersonPassword();
                } else {
                    alert("请按照要求填写");
                    return;
                }
            });
        });

        function addTabs(id, name, url) {
            var basepath = '<%=basePath%>';
            var item = {'id': id, 'name': name, 'url': basepath + url, 'closable': true};
            closableTab.addTab(item);
        }
        //关闭Tab标签
        function closeTab(id) {
            closableTab.closeTab2(id);
        }
        //设置iframe高度
        window.onload = function () {
            var ifHeight = document.documentElement.clientHeight;
            $('.innerIframe').attr('height', ifHeight - 119);
            $('#siteMinHeight').attr('height', ifHeight - 118);
        };
        //窗口改变设置iframe高度
        window.onresize = function () {
            var aftHeight = document.documentElement.clientHeight;
            $('.innerIframe').attr('height', aftHeight - 119);
            $('#siteMinHeight').attr('height', aftHeight - 118);
        };
        function personal() {
            $.ajax({
                url: "<%=basePath%>/account/getDateByUserId.do",
                type: "POST",
                success: function (data) {
                    var msg = eval("(" + data + ")");
                    $("#account").val(msg.account);
                }
            });
            $("#personal").modal("show");
            $("#myPersonal").text("个人信息");
        }
        function personalSet() {
            $("#personalSet").modal("show");
            $("#myPersonalSet").text("个人设置");
        }
        //修改密码校验
        function initValidate() {
            $('#userForm').bootstrapValidator({
                message: '值不能为空',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    oldPassword: {
                        validators: {
                            notEmpty: {
                                message: '原始不能为空!'
                            },
                            remote: {
                                url: '<%=basePath%>/user/checkPassword.do',
                                delay: 200,
                                message: '原始密码有误'
                            }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: '新密码不能为空!'
                            }
                        }
                    },
                    confirmPassword: {
                        validators: {
                            notEmpty: {
                                message: '确认密码不能为空'
                            },
                            identical: {
                                field: 'newPassword',
                                message: '确认密码错误'
                            }
                        }
                    }
                }
            });
        }
        //修改密码
        function updatePersonPassword() {
            var newPassword = $("#newPassword").val();
            $.ajax({
                url: "<%=basePath%>/account/updatePersonPassword.do",
                type: "POST",
                data: {
                    newPassword: newPassword
                },
                success: function (data) {
                    if (data !== "failed") {
                        successInfo("修改成功!");
                        $("#personalSet").modal("hide");
                    } else {
                        errorInfo("修改失败");
                    }
                }
            });
        }
        //清空校验
        function resetForm() {
            $('#userForm').data('bootstrapValidator').resetForm(true);
        }
        //          点击箭头缩进
        $('.sidebar-toggle-box').click(function () {
            $(this).find("i").toggleClass("transf");
        })
    </script>
    <style type="text/css">
        body {
            overflow-x: hidden;
        }

        .nav-tabs > li.active > a, .nav-tabs > li.active > a:focus, .nav-tabs > li.active > a:hover {
            /*background-color: #57AED5;*/
            color: #fff;
            background:none;
            border:none;
        }

        .transf {
            transform: rotate(-180deg);
            /*transition-duration: 0.3s;*/
        }

        ul.nav-tabs > li {
            border-bottom: 25px solid rgba(0, 0, 0, 0.2);
            border-left: 20px solid transparent;
            border-right:20px solid transparent;
            height: 31px;
        }
        ul.nav-tabs > li.active{
            border-bottom: 25px solid  rgba(0, 0, 0, 0.4);
            border-left: 20px solid transparent;
            border-right:20px solid transparent;
            height: 31px;
            margin-left: 1px;
            z-index: 99;
        }
        span.fa.fa-remove:hover {
            -webkit-transform: scale(1.2);
            -moz-transform: scale(1.2);
            -ms-transform: scale(1.2);
            -o-transform: scale(1.2);
            transform: scale(1.2);;
        }
        .dropdown-menu {
            min-width: 60px;
            text-align: center;
        }
        .nav>li>a:focus, .nav>li>a:hover{
            background: none;
            border: none;
            color: #fff;
        }
        .nav>li>a:hover{
            border: 1px solid transparent;
        }
        .nav-primary ul.nav > li >a:hover{
            border: none;
        }
    </style>
</head>


<section id="container">
    <!--header start-->
    <header class="header white-bg">
        <div style="float: left;">
            <!--<img style="height: 50px; width: 180px" src="<%=basePath%>/static/img/logo.gif">-->
        </div>
        <a href="javascript:;" class="logo" style="color: white">婚秀中国网后台管理系统</a>
        <!--logo end-->
        <%--<div class="nav notify-row" id="top_menu">--%>
        <%--<!--  notification start -->--%>
        <div class="top-nav ">
            <ul class="nav pull-right top-menu" style="margin-top: 4px;">
                <!-- user login dropdown start-->
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <img alt="退出" title="退出" src="<%=basePath%>/static/img/avatar1_small.jpg">
                    </a>
                    <ul class="dropdown-menu extended logout">
                        <div class="log-arrow-up"></div>
                        <li onclick="personal()"><a href="#"><i class=" fa fa-suitcase"></i>个人信息</a></li>
                        <li onclick="personalSet()"><a href="#"><i class="fa fa-cog"></i> 个人设置</a></li>
                        <li><a href="<%=basePath%>/toLogin.do"><i class="fa fa-key"></i> 退出</a></li>
                    </ul>
                </li>
                <!-- user login dropdown end -->
            </ul>
        </div>
    </header>
    <section class="hbox stretch">
        <aside class="bg-black dk aside hidden-print " id="nav">
            <section class="vbox">
                <section class="w-f-md scrollable">
                    <div id="sidebar" class="slim-scroll" style="overflow-y: auto;margin-top: 82px">
                        <nav class="nav-primary hidden-xs">
                            <!-- sidebar menu start-->
                            <ul class="sidebar-menu nav bg" id="nav-accordion">

                                <li style="line-height:35px">
                                    <a href="javascript:;" class="active">
                                        <i class="fa fa-home" class="liclick" tabId="1" url="<%=basePath%>/demo.do"
                                           title="首页"></i>
                                        <span>首页</span>
                                    </a>
                                </li>
                                <%for (MenuTree menu : menuLists) {%>
                                <li class="sub-menu">
                                    <a href="javascript:;" class="dcjq-parent ">
                                        <i class="fa <%=menu.getIcon()%>"></i>
                                        <span><%=menu.getTitle() %></span>
                                    </a>
                                    <ul class="sub">
                                        <% for (MenuTree childrenMenu : menu.getChildrenMenu()) {%>
                                        <li class="liclick " tabId="<%=childrenMenu.getFid()%>"
                                            url="<%=basePath + childrenMenu.getUrl()%>"
                                            title="<%=childrenMenu.getTitle()%>"><a
                                                href="javascript:;"><%=childrenMenu.getTitle()%>
                                        </a></li>
                                        <%}%>
                                    </ul>
                                </li>
                                <% }%>
                            </ul>
                            <!-- sidebar menu end-->
                        </nav>
                    </div>
                </section>
            </section>
        </aside>
        <!--main content start-->

        <section id="main-content">
            <section id="siteMinHeight" class="wrapper site-min-height">
                <ul class="nav nav-tabs" role="tablist"
                    style="padding-bottom: 2px;">
                </ul>

                <div class="tab-content" id="tabContent">
                    <footer class="site-footer" style="position: fixed;bottom: 0;width: 100%;left: 0;">
                        <div class="text-center" style="font-size: 12px;line-height:20px;">
                            技术支持:河南青居电子商务有限公司
                        </div>
                    </footer>
                    <!--footer end-->
                </div>
            </section>
        </section>
        <!--main content end-->
    </section>
    <!--sidebar end-->
</section>
<div class="modal fade" id="personal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myPersonal"></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <input class="form-control" id="fid" placeholder="ID" type="hidden">
                        <div class="form-group">
                            <label>姓名</label>
                            <input class="form-control" readonly id="fname" name="fname"
                                   placeholder="${user.getFristname()}" type="text">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div id="accountId" class="form-group">
                            <label>帐号</label>
                            <input class="form-control" readonly id="account" type="text">
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="personalSet" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:400px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myPersonalSet"></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div id="userForm">
                        <div class="col-md-6">
                            <input class="form-control" id="setFid" placehold er="ID" type="hidden">
                            <div class="form-group">
                                <label>原始密码</label>
                                <input style="width:350px" class="form-control" id="oldPassword" name="oldPassword"
                                       type="password">
                            </div>
                            <!-- /.form-group -->
                            <div class="form-group">
                                <label>新密码</label>
                                <input style="width:350px" class="form-control" id="newPassword" name="newPassword"
                                       type="password">
                            </div>
                            <div class="form-group">
                                <label>密码确认</label>
                                <input style="width:350px" class="form-control" id="confirmPassword"
                                       name="confirmPassword" type="password">
                            </div>
                            <!-- /.form-group -->
                        </div>
                    </div>
                    <!-- /.col -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="resetForm()">关闭</button>
                <button type="button" class="btn btn-primary" id="submitBtn">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- js placed at the end of the document so the pages load faster -->
<script src="<%=basePath%>/static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/static/bootstrap/flatlab-master/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="<%=basePath%>/static/bootstrap/flatlab-master/js/jquery.scrollTo.min.js"></script>
<script src="<%=basePath%>/static/bootstrap/flatlab-master/js/jquery.nicescroll.js" type="text/javascript"></script>
<script src="<%=basePath%>/static/bootstrap/flatlab-master/js/slidebars.min.js"></script>
<!--common script for all pages-->
<script src="<%=basePath%>/static/bootstrap/flatlab-master/js/common-scripts.js"></script>
<script src="<%=basePath%>/static/bootstrap/js/closable-tab-iframe.js"></script>
<%--模态弹窗引用jquery-ui设置可拖动--%>
<script src="<%=basePath%>/static/js/jquery-ui.min.js"></script>
<script>
    $(document).ready(function () {
        $(".modal-content").draggable({cursor: "move"});//为模态对话框添加拖拽
    })
</script>
</html>
