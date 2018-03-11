/**
 * Created by wangyong on 2016/11/4.
 */
function showInfo(type, msg) {
    var n = noty({
        text        : msg,
        type        : type,
        dismissQueue: false,
        killer: true,
        timeout: false,
        layout      : 'center',
        theme       : 'bootstrapTheme',
        closeWith   : ['click'],
        maxVisible  : 1
    });
}



function  alertInfo(msg){
    showInfo('alert', msg);
}

function  successInfo(msg){
    showInfo('success ', msg);
}
function  errorInfo(msg){
    showInfo('error', msg);
}

function  warningInfo(msg){
    showInfo('warning', msg);
}
function  informationInfo(msg){
    showInfo('information', msg);
}

function confirmInfo(msg){
    var self = this;
    self.dfd = $.Deferred();
    var n = noty({
        text: msg,
        type: 'confirm',
        dismissQueue: true,
        killer : true,
        layout: 'center',
        theme: 'defaultTheme',
        //modal: true,
        buttons:
            [{
                addClass: 'btn btn-primary', text: '确认', onClick: function ($noty) {
                    $noty.close();
                    self.dfd.resolve(true);

                }
            },
                {
                    addClass: 'btn btn-danger', text: '取消', onClick: function ($noty) {
                    $noty.close();
                    self.dfd.resolve(false);
                }
                }]
    });
    return self.dfd.promise();
}
var waitHtml = "<div id='waitstatus' class='modal fade' style='z-index:100000' valign='center' align='center'><image src='../static/img/blue-loading.gif' style='POSITION: relative; TOP: 150px;'/><span></span></div>";

//显示等待处理状态框
function showWait(message){
    if ($("#waitstatus").length===0){
        $("body").append(waitHtml);
    }
    if (message != null)
        $("#waitstatus>span").text(message);
    $("#waitstatus").modal('show');
}
//隐藏等待处理状态框
function hideWait(message){
    if (message != null)
        $("#waitstatus>span").text(message);
    $("#waitstatus").modal('hide');
}

function forwardWait(){
    showWait("页面跳转中....");
}

function submitWait(){
    showWait("数据提交中....");
}

function hideSubmitWait(){
    hideWait();
}


