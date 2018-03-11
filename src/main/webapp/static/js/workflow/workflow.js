/*************流程js***************/

/**
* 显示节点信息
*/
function showTips(obj)
 {
  var t = $(obj).offset().top;
  var l = $(obj).offset().left;
  $("#pro_pao").css("left",l+2);
  $("#pro_pao").css("top",t+4);
  $("#pro_pao").show();
  var showValue = $(obj).attr("des") + "</br>审批人：" + $(obj).attr("userNames") + "<br>" 
  		+ "知会人：" + $(obj).attr("notifyUserNames");
  $(".pro_paonon").html(showValue);
 }
/**
*生成flowhtml
*/
function createFlowHtml(wfId){
	if(wfId == ""){
		errorTip("流程图加载失败，找不到流程ID！");
		$("#spanBefore").html("");
		return;
	}
	$.ajax({
			type:"POST",
			url:"/workflow/createFlowHtml.do",
			data:{
				wfId:wfId
			},
			error:function(){
			  // hideWait();
			   $("#spanBefore").html("");
				errorInfo("流程图加载失败！1");
			},
			success:function(data){
				//hideWait();
				if (data == "failed"){
					$("#spanBefore").html("");
					errorInfo("流程图加载失败！2");
				}else{
					$("#spanBefore").html(data);
					$("#spanBefore").flow({hover:function(){
						$(this).addClass("hover");
						if(typeof($(this).attr("begin")) == "undefined" && $(this).attr("next") != "-1"){
							showTips(this);
						}
						
					},remove:function(){
						$("#pro_pao").hide();
						$(this).removeClass("hover");
					}});
					$("#spanBefore").html("");	
					$("#expandBox").height(document.getElementById("expandBox").scrollHeight+"px");
				}
			}
		});
		
	
 }
// /**
// *流程图隐藏显示控制
// */
//function openShutManager(isStartSend){
//	var expandV = $("#expandV").val();
//	if(expandV == 0){
//		hideDiv("expandBox");
//		$("#ctrlButton").attr("src","../images/common/dot/bt_expand.gif");
//		$("#ctrlButton").attr("title","展开");
//		$("#expandV").val(1);
//	}else{
//		$("#expandBox").css("display","block");
//		$("#ctrlButton").attr("src","../images/common/dot/top_expand.gif");
//		$("#ctrlButton").attr("title","收起");
//		$("#expandV").val(0);
//		if($("#draw").children().length == 0){
//			var isCreateSend = true;
//			if(isStartSend != true){
//				isCreateSend = false;
//			}
//			 //生成流程图
//		    createFlowHtml($("#requestWfId").val(),isCreateSend);
//	    }
//	}
//}

