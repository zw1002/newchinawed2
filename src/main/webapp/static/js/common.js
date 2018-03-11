function setIframeHeight(){
    $('.innerIframe').each(function(){

        var nHight=document.documentElement.clientHeight-125;

      //  var ifm= document.getElementById("Mainframe");
        $(this).height=document.documentElement.clientHeight-125;
        //var iframeHeight = function(){
        //    var obj = iframeHeight.obj;
        //    try{
        //        var bHeight = obj.contentWindow.document.body.scrollHeight;
        //        var dHeight = obj.contentWindow.document.documentElement.scrollHeight;
        //        var height = Math.max(bHeight, dHeight);
        //        if(obj.height == height || height < 300){
        //            return;
        //        }
        //        obj.height = nHight;//height;
        //    }catch (ex){
        //        alert(ex);
        //    }
        //};
        //iframeHeight.obj = this;
        //setInterval(iframeHeight, nHight);

        //重新加载iframe时，对iframe高度进行初始化；
        $(this).load(function(){
            this.height = nHight; //iframe初始高度
        });
    });
}