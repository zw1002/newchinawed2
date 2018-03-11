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
    <script src="<%=basePath%>/static/js/jquery-2.2.0.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/static/bootstrap/css/bootstrap.min.css">
    <link href="<%=basePath%>/static/css/bootstrap-reset.css" rel="stylesheet">
    <link href="<%=basePath%>/static/css/font-awesome.css" rel="stylesheet" />
    <link href="<%=basePath%>/static/css/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen" />
    <link rel="stylesheet" href="<%=basePath%>/static/css/owl.carousel.css" type="text/css">
    <link href="<%=basePath%>/static/bootstrap/flatlab-master/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>/static/font-awesome/css/font-awesome.min.css">
    <link href="<%=basePath%>/static/css/selectuser.css" rel="stylesheet">
    <!--引入CSS-->
    <%--提示框--%>
    <script src="<%=basePath%>/static/js/jquery.noty.packaged.min.js"></script>
    <script src="<%=basePath%>/static/js/showinfo.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/bootstrap/js/bootstrap.min.js"></script><%--<script type="text/javascript" src="<%=basePath%>/static/js/jquery.simplemodal.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/static/js/selectbox.js"></script>
    <script src="<%=basePath%>/static/js/jquery-ui.min.js"></script>
    <link href="<%=basePath%>/static/css/style-responsive.css" rel="stylesheet" />
    <script src="<%=basePath%>/static/js/echarts.js"></script>
    <script>
        $(document).ready(function() {
        });
    </script>
    <style>
        .col-md-5 {
            width: 96%;
        }
    </style>
    <title>首页</title>
</head>
<body>
<label style="width:95px;margin-top: 10px" class="control-label col-md-1">时间区间:</label>
<div class="col-md-2">
    <input style="margin-left: -22px;margin-top: 5px" type="text" id="creatTime" name="creatTime" class="form-control" readonly>
</div>
<label style="margin-left: -40px;margin-top: 10px" class="control-label col-md-1">至:</label>
<div class="col-md-2">
    <input style="margin-left: -65px;margin-top: 5px" type="text" id="creatTimes" name="creatTimes" class="form-control" readonly>
</div>
<button class="btn btn-info" style="margin-left: -15px;margin-top: 4px" onclick="selectDate()">查询</button>
<button class="btn btn-info" style="margin-left: 10px;margin-top: 4px" onclick="exportExcel()">导出收支统计表</button>
<!--折线图-->
<div class="col-md-5" align="center" id="main" style="float:left;margin-left:60px;height:480px;padding: 10px; margin-top:10px;border-radius: 10px;box-shadow: 0px 0px 2px 2px rgba(161, 159, 159, 0.18);left:-40px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    option = {
        title: {
            text: '婚秀中国网收支明细',
        },
        grid:{x:80},
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['最低数','登记趋势']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: ['店铺抽成','求图抽成','广告投放','名师讲堂','提现支出']
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value}'

            },
            max:5000
        },
        series: [
            {
                name:'人民币',
                type:'line',
                data:[1600, 2200, 2600, 2700, 3400],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
