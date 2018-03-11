/**
 * Created by wangyong on 2017/2/24.
 */
function download(fileId){
    window.open("/upload/downloadFile.do?accessoryId="+fileId);
}

//加载附件 必须在页面中加入以下DIV
//<div ext="attch"
//id="业务ID"
//style="display: none; padding-left: 10px">
//</div>
/**
 * 在页面初始化时使用
 */
function loadAttch(){
    $("div[ext=attch]").each(function(){
        var fid=$(this).attr("id");
        $.ajax({
            type:"POST",
            url:"/upload/findRelateFile.do",
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

