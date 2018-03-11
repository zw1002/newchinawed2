<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html;charset=UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <script src="<%=basePath%>/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/css/bootstrap.min.css">
    <link href="<%=basePath%>/bootstrap/flatlab-master/css/style.css" rel="stylesheet">
    <!--引入CSS-->


    <script type="text/javascript" src="<%=basePath%>/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/bootstrap/validate/css/bootstrapValidator.min.css"/>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/validate/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=basePath%>/bootstrap/validate/js/language/zh_CN.js"></script>

    <script>
        $(document).ready(function() {
            initValidate();
            $("#defaultForm").submit(function(ev){ev.preventDefault();});//AJAX提交必修使用
            $('#validateBtn').click(function() {
                var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');//必须
                bootstrapValidator.validate();
                if(bootstrapValidator.isValid()) {
                    var val=$("input[name='firstName']").val();
                    $("#getVals").val($("#getVals").val()+'\n'+'firstName='+val);

                    <!--单选-->
                    val=$("input[name='gender']:checked").val();
                    $("#getVals").val($("#getVals").val()+'\n'+'gender='+val);


                    <!--CHECKBOX只选一条-->
                    val=$("input[name='languages']:checked").val();
                    $("#getVals").val($("#getVals").val()+'\n'+'languages='+val);

                    <!--CHECKBOX选多条-->
                    val= $("input:checkbox[name='programs']:checked").map(function(index,elem) {
                        return $(elem).val();
                    }).get().join(',');
                    $("#getVals").val($("#getVals").val()+'\n'+'programs='+val);


                    <!--SELECT多选-->
                    val=$('#editors').val();
                    $("#getVals").val($("#getVals").val()+'\n'+'editors='+val);

                    <!--SELECT单选-->
                    val=$('#country').val();
                    $("#getVals").val($("#getVals").val()+'\n'+'country='+val);

                    alert("true");
                }else{
                    alert("false");
                    return;

                }
            });

            $('#resetBtn').click(function() {
                $('#defaultForm').data('bootstrapValidator').resetForm(true);
            });
        });
        function initValidate(){
            $('#defaultForm').bootstrapValidator({
                message: '值不能为空',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: '姓不能为空'
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: '名不能为空'
                            }
                        }
                    },
                    username: {
                        message: '用户名无效',
                        validators: {
                            notEmpty: {
                                message: '用户名不能为空'
                            },
                            stringLength: {
                                min: 6,
                                max: 30,
                                message: '用户名6-30字符'
                            },
                            regexp: {
                                regexp: /^[a-zA-Z0-9_\.]+$/,
                                message: '用户名只能由字母，数字，点和下划线组成'
                            },
                            remote: {
                                url: '<%=basePath%>/sys/checkUserName.do',
                                delay :  2000,
                                message: '用户名是不可用的'
                            },
                            different: {
                                field: 'password',
                                message: '用户名和密码不能是彼此相同的'
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: '邮箱不能为空'
                            },
                            emailAddress: {
                                message: '请输入正确的邮箱'
                            }
                        }
                    },
                    UUid: {
                        validators: {
                            notEmpty: {
                                message: '证件号为空'
                            },
                            regexp: {
                                regexp: /^(\d{15,15}|\d{18,18}|\d{17}(\d|X|x))$/,
                                message: '请输入正确的证件号'
                            }
                        }
                    },
                    phone: {
                        validators: {
                            notEmpty: {
                                message: '电话为空'
                            },
                            regexp: {
                                regexp: /^[1]{1}[3,4,5,7,8]{1}[0-9]{9}$/,
                                message: '请输入正确的电话号'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空!'
                            },
                            identical: {
                                field: 'confirmPassword',
                                message: '确认密码错误!'
                            },
                            different: {
                                field: 'username',
                                message: '用户名和密码不能是彼此相同的'
                            }
                        }
                    },
                    confirmPassword: {
                        validators: {
                            notEmpty: {
                                message: '确认密码不能为空'
                            },
                            identical: {
                                field: 'password',
                                message: '确认密码错误'
                            },
                            different: {
                                field: 'username',
                                message: '用户名和密码不能是彼此相同的'
                            }
                        }
                    },
                    birthday: {
                        validators: {
                            notEmpty: {
                                message: '日期不能为空'
                            },
                            date: {
                                format: 'YYYY/MM/DD',
                                message: '无效的日期格式'
                            }
                        }
                    },
                    gender: {
                        validators: {
                            notEmpty: {
                                message: '请选择性别'
                            }
                        }
                    },
                    'languages': {
                        validators: {
                            notEmpty: {
                                message: '请指定至少一种你可以说的语言'
                            }
                        }
                    },
                    'programs': {
                        validators: {
                            choice: {
                                min: 2,
                                max: 4,
                                message: '请选择你擅长的2 - 4种编程语言'
                            }
                        }
                    },
                    'editors': {
                        validators: {
                            choice: {
                                min: 2,
                                max: 3,
                                message: '请选择 %s - %s 种编程工具'
                            }
                        }
                    },
                    country: {
                        validators: {
                            notEmpty: {
                                message: '请选择国家'
                            }
                        }
                    }
                }
            });
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
                    <h2>Sign up</h2>
                </div>

                <div id="defaultForm" method="post" class="form-horizontal" >
                    <div class="form-group">
                        <label class="col-lg-3 control-label">全名</label>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" name="firstName" placeholder="姓" />
                        </div>
                        <div class="col-lg-4">
                            <input type="text" class="form-control" name="lastName" placeholder="名" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">用户名</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="username" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">邮箱地址</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="email" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">身份证</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="UUid" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">电话</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="phone" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">密码</label>
                        <div class="col-lg-5">
                            <input type="password" class="form-control" name="password" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">确认密码</label>
                        <div class="col-lg-5">
                            <input type="password" class="form-control" name="confirmPassword" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">性别</label>
                        <div class="col-lg-5">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="男" /> 男
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="女" /> 女
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gender" value="其它" /> 其它
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">生日</label>
                        <div class="col-lg-5">
                            <input type="text" class="form-control" name="birthday" /> (YYYY/MM/DD)
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">语言(最少要选择1条)</label>
                        <div class="col-lg-5">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages" value="英文" /> 英文
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages" value="法文" /> 法文
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="languages" value="德文" /> 德文
                                </label>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">开发语言(要选择2-4条)</label>
                        <div class="col-lg-5">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="net" /> .Net
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="java" /> Java
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="c" /> C/C++
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="php" /> PHP
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="perl" /> Perl
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="ruby" /> Ruby
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="python" /> Python
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="programs" value="javascript" /> Javascript
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                    <select class="form-control m-bot15" name="editors" id="editors" multiple="multiple" style="height: 200px;">
                        <option value="" disabled>选择2-3个开发工具</option>
                        <option value="atom">Atom</option>
                        <option value="eclipse">Eclipse</option>
                        <option value="netbeen">NetBean</option>
                        <option value="nodepadplusplus">Nodepad++</option>
                        <option value="phpstorm">PHP Storm</option>
                        <option value="sublime">Sublime</option>
                        <option value="webstorm">Web Storm</option>
                    </select>
                    </div>
                    <div class="form-group">
                    <select class="form-control m-bot15" name="country" id="country">
                        <option value="">-- 请选择国家--</option>
                        <option value="fr">France</option>
                        <option value="de">Germany</option>
                        <option value="it">Italy</option>
                        <option value="jp">Japan</option>
                        <option value="ru">Russia</option>
                        <option value="gb">United Kingdom</option>
                        <option value="us">United State</option>
                    </select>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-9 col-lg-offset-3">
                            <button type="button" class="btn btn-info" id="validateBtn">校验</button>
                            <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                        </div>
                    </div>
                    <div class="form-group">
                       <textarea id="getVals"></textarea>
                    </div>


                </div>
            </div>
        </section>
        <!-- :form -->
    </div>
</div>


</body>
</html>
