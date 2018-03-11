var selectboxHtml = "<div id='selectbox' class='modal fade  'style='display:none'><div class='modal-dialog'>"
	+"<table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;background-color:#fff' width='500px'>"
	+"<tr class='tit2 alert-info' style='line-height:24px;height:24px'><td width='480' align='left'><strong>&nbsp;&nbsp;人员选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='fa fa-times'/></td></tr>"
	+"<tr><td colspan='2' valign='top' height='280'><iframe width='100%' height='295px' style='padding:10px' scrolling='no' src='../user/showSelectUser.do?alone=false' frameborder='0'></iframe></td></tr>"
	+"<tr><td colspan='2'><div id='selectTip' class='tip_select alert-success' style='padding: 6px 15px;'></div></td></tr><tr style='display:none'><td colspan='2'><div class='selected_user'><ul style='padding-left: 0px'></ul></div>&nbsp;</td></tr>"
	+"<tr><td colspan='2' align='right'><div calss='panel-body' style='margin: 15px;'><button class='btn btn-primary btn-sm' id='saveSelected'>确定</button> <button class='btn btn-primary btn-sm' id='cancelSelected' style='margin-left: 4px;'>取消</button></div></td></tr>"
	+"</table></div></div>";


var selectboxHtmlAlone = "<div id='selectbox' class='modal fade  'style='display:none'><div class='modal-dialog'>"
	+"<table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;background-color:#fff' width='500px'>"
	+"<tr class='tit2 alert-info' style='line-height:24px;height:24px'><td width='480' align='left'><strong>&nbsp;&nbsp;人员选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='fa fa-times'/></td></tr>"
	+"<tr><td colspan='2' valign='top' height='280'><iframe width='100%' height='295px' style='padding:10px' scrolling='no' src='../user/showSelectUser.do?alone=true' frameborder='0'></iframe></td></tr>"
	+"<tr><td colspan='2'><div id='selectTip' class='tip_select alert-success' style='padding: 6px 15px;'></div></td></tr><tr style='display:none'><td colspan='2'><div class='selected_user'><ul style='padding-left: 0px'></ul></div>&nbsp;</td></tr>"
	+"<tr><td colspan='2' align='right'><div calss='panel-body' style='margin: 15px;'><button class='btn btn-primary btn-sm' id='saveSelected'>确定</button> <button class='btn btn-primary btn-sm' id='cancelSelected' style='margin-left: 4px;'>取消</button></div></td></tr>"
	+"</table></div></div>";


var editboxHtml = "<div id='editbox' class='editbox'><div class='title'><strong>人员清单</strong><em id='selectedcount'></em></div><div id='editbox_content' class='editbox_content'></div></div>";
var selectunitboxHtml = "<div id='selectbox' style='display:none'><table border=' 0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;部门选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='280'><iframe width='100%' height='280px' scrolling='no' src='/hr.t?op=showUnitSelectBox&alone=false' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select'></div></td></tr><tr style='display:none'><td colspan='2'><div class='selected_user'></div></td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectunitboxHtmlAlone = "<div id='selectbox' style='display:none'><table border=' 0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;部门选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='280'><iframe width='100%' height='280px' scrolling='no' src='/hr.t?op=showUnitSelectBox&alone=true' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select'></div></td></tr><tr style='display:none'><td colspan='2'><div class='selected_user'></div></td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var editunitboxHtml = "<div id='editbox' class='editbox'><div class='title'><strong>部门清单</strong><em id='selectedcount'></em></div><div id='editbox_content' class='editbox_content'></div></div>";
var selectfileboxHtml = "<div id='selectbox' style='display:none;'><table border='0' width='600px' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;background-color:#fff'><tr class='tit2'><td width='580' align='left'><strong>&nbsp;&nbsp;附文选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top'><iframe width='100%' height='320' src='/workflow.t?op=showSelectfileBox' scrolling='no' frameborder='0' id='selectFileIframe'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select'></div></td></tr><tr><td colspan='2'><div class='selected_user' style='width:98%'></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectroleHtml = "<div id='selectbox' style='display:none'><table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;按职务选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='280'><iframe width='100%' height='280px' scrolling='no' src='/hrroler.t?op=showRolerForSelect&isonly=true&alone=false' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select' style='margin-left:5px'></div></td></tr><tr><td colspan='2'><div class='selected_user' ></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectroleHtmlAlone = "<div id='selectbox' style='display:none'><table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;按职务选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='280'><iframe width='100%' height='280px' scrolling='no' src='/hrroler.t?op=showRolerForSelect&isonly=true&alone=true' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select' style='margin-left:5px'></div></td></tr><tr><td colspan='2'><div class='selected_user' ></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectpositionHtml = "<div id='selectbox' style='display:none'><table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;按职务选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='290'><iframe width='100%' height='290px' scrolling='no' src='/hrposition.t?op=showPositionForSelect&alone=false' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select' style='margin-left:5px'></div></td></tr><tr><td colspan='2'><div class='selected_user' ></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectpositionHtmlAlone = "<div id='selectbox' style='display:none'><table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;按职务选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='290'><iframe width='100%' height='290px' scrolling='no' src='/hrposition.t?op=showPositionForSelect&alone=true' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select' style='margin-left:5px'></div></td></tr><tr><td colspan='2'><div class='selected_user' ></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectjobHtml = "<div id='selectbox' style='display:none'><table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;按职位选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='290'><iframe width='100%' height='290px' scrolling='no' src='/hrroler.t?op=showRolerForSelect&alone=false' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select' style='margin-left:5px'></div></td></tr><tr><td colspan='2'><div class='selected_user' ></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";
var selectjobHtmlAlone = "<div id='selectbox' style='display:none'><table border='0' ellpadding='0' cellspacing='0' style='border: 1px solid #C5C5C5;;background-color:#fff' width='500px'><tr class='tit2'><td width='480' align='left'><strong>&nbsp;&nbsp;按职位选择</strong></td>	<td width='20' align='left'><a onclick='SelectBox.close()' id='' href='#' class='modal_btn_cancel'/></td></tr><tr><td colspan='2' valign='top' height='290'><iframe width='100%' height='290px' scrolling='no' src='/hrroler.t?op=showRolerForSelect&alone=true' frameborder='0'></iframe></td></tr><tr><td colspan='2'><div id='selectTip' class='tip_select' style='margin-left:5px'></div></td></tr><tr><td colspan='2'><div class='selected_user' ></div>&nbsp;</td></tr><tr><td colspan='2' align='right' class='btn_bar'><button class='button_yes' id='saveSelected'>确定</button><button class='button_yes' id='cancelSelected'>取消</button></td></tr></table></div>";

var callBack;
function SelectBox(){
}

function EditBox(){}

$(document).ready(function(){
	SelectBox.show = fShow;
	SelectBox.appendItem = fAppendItem;
	SelectBox.isExist = fIsExist;
	SelectBox.removeItem = fRemoveItem;
	SelectBox.close = fClose;
	SelectBox.saveData = fSaveData;
	SelectBox.initData = initData;
	 
	EditBox.show = eShow;
	EditBox.close = eClose;
	EditBox.appendItem = eAppendItem;
});

function initEditBox(o,boxType){
	var editbox = o;
	if($("#editbox").length>0){
	    $("#editbox").remove();
	}
	if ($("#editbox").length==0){
	   if(boxType === "user")
		$("body").append(editboxHtml);
	   else
	    $("body").append(editunitboxHtml);
	}
}

function getSelectboxHtml(alone){
	if (alone)
		return selectboxHtmlAlone;
	else 
		return selectboxHtml;
}

function getSelectunitboxHtml(alone){
	if (alone)
		return selectunitboxHtmlAlone;
	else 
		return selectunitboxHtml;
}

function getSelectpositionHtml(alone){
	if (alone)
		return selectpositionHtmlAlone;
	else 
		return selectpositionHtml;
}
function getSelectjobHtml(alone){
	if (alone)
		return selectjobHtmlAlone;
	else 
		return selectjobHtml;
}
function getSelectroleHtml(alone){
	if (alone)
		return selectroleHtmlAlone;
	else 
		return selectroleHtml;
}

function initSelectBox(o,boxType,alone){
	selectbox = o;
	if($("#selectbox").length>0){
	  $("#selectbox").remove();
	}
	if ($("#selectbox").length==0){
	    if(boxType === "user")
		   $("body").append(getSelectboxHtml(alone));
	    else if(boxType === "unit")
		   $("body").append(getSelectunitboxHtml(alone));
		else if(boxType === "file")
		   $("body").append(selectfileboxHtml);
		else if(boxType === "roler")
	       $("body").append(getSelectroleHtml(alone));
	    else if(boxType === "position")    
	       $("body").append(getSelectpositionHtml(alone));
	    else if(boxType === "job")    
	       $("body").append(getSelectjobHtml(alone));
	}
	if($("#tid").val()){
       selectbox.userId = $("#tid").val();
    }
	
	//$(".user").each(function(){
		//$(this).hover(
		//function(){
		//$(this).addClass("userselect");
		//$(this).children(".deleteuser").show("fast");
		//},
		//function(){
		//$(this).removeClass("userselect");
		//$(this).children(".deleteuser").hide("fast");
		//}
	//);
	//});
	
	$("#saveSelected").click(function(){
		debugger;
		var ids = new Array();
		var names = new Array();
		var allNames = new Array();
		
		var values = "";
		var orgValues = "";
		var userCount = $("#selectbox").find(".user").length;
		var idx = 0;
		
		$("#selectbox").find(".user").each(function(){
			ids[idx] = this.id;
			names[idx] = $(this).text();
			allNames[idx] = $(this).attr("orgValue");
			values += $(this).text()+"；";	
			orgValues += $(this).attr("orgValue")+"；";
			idx = idx+1;
		});
		selectbox.component.data("ids",ids);
		selectbox.component.data("names",names);
		try{
			selectbox.component.data("allNames",allNames);
		}catch (err) {}
		if (ids.length > 3){
			selectbox.component.val(names[0]+"；"+names[1]+"；"+names[2]+"；.....");
		}else{
			selectbox.component.val(values);
		}
		selectbox.component.attr("title",orgValues);
		//selectbox.close();
		//selectbox.modal('hide');

		$('#selectbox').modal('hide');
		if($.isFunction(selectbox.callBack)){
	    	selectbox.callBack();
	    }
	});
	
	$("#cancelSelected").click(function(){
		$('#selectbox').modal('hide');
		//selectbox.close();
		//selectbox.modal('hide');
	});

}





/*
callBack 点击确定的回调方法 boxType 判断人员选择(==user)、部门选择(==unit)默认是人员选择框  
*/
function fShow(component,alone,boxType,callBack,options){
    if(!boxType){
    	boxType = "user";
    }
    SelectBox.component = component;
	SelectBox.alone = alone;
	SelectBox.callBack = callBack;
	
	initSelectBox(SelectBox,boxType,alone);
	var ids = component.data("ids");
	var names = component.data("names");
	var allNames = component.data("allNames");
	if(typeof(allNames)=='undefined'){
		allNames = names;
	}
	if(ids && names){
		for(var i=0;i<ids.length;i++){
			if(ids[i]!="")
			{
			 try {	
				SelectBox.appendItem(ids[i],names[i],allNames[i]);
			} catch (err) {
			
			}
			}
		}
	}else{
		component.data("ids",[]);
		component.data("names",[]);
		component.data("allNames",[]);
	}
	//模态窗口实例
	var modalInstance=null;
	if(options)	{//如果有自定义参数设置，那么使用自定义优先
		modalInstance=$("#selectbox").modal("show");
	}else{
		modalInstance=$("#selectbox").modal("show");
	}	
	if (alone){
		$('#selectTip').text("提示：此次选择为单选选择。");
	}else{
		$('#selectTip').text("提示：此次选择为多选选择。");
	}
	//$(".selected_user").children("ul").sortable();
	$(".selected_user").sortable();

	return modalInstance;
}

function fIsExist(key){
	if ($("#selectbox").find("#"+key).length>0){
		return true;
	}
	return false;
}

function fAppendItem(key,value,oValue){
	if ($("#selectbox").find("#"+key).length>0){
		return;
	}
	var count = 0;
	var orgValue = value;
	if(oValue){
		orgValue = oValue;
	}
	if (value.length>10) {
		value=value.replace(/<.*?>/g,"");
		value=value.substr(0,10)+"...";
	}
	var li = "<li style='float:left;margin:3px;padding:0px' id=\""+key+"\" class=\"user\" orgValue=\"" + orgValue + "\">"+value+"<a href='#' class=\"deleteuser\" onclick=\"javascript:deleteuser($(this).parent());\"  width=\"7\" height=\"7\"  title=\"删除\"><img src=\"/img/del.gif\" /></a></li>";
//	if (this.alone && $("#selectbox").find(".selected_user").children(".user").length<1){
//		$("#selectbox").find(".selected_user").append(li);
//	}
	//单选时处理.移除原有,增加新
	if (this.alone){
		
		$("#selectbox").find(".selected_user").children("ul").children(".user").remove();
		$("#selectbox").find(".selected_user").children("ul").append(li);
		//$("#selectbox").remove();
	}
	if (!this.alone){
		
		$("#selectbox").find(".selected_user").children("ul").append(li);
	}
	
	$("#selectbox").find("#"+key+">a").each(function(){
		$(this).bind("click",function(){
			deleteuser($(this).parent());
		});
	});
	
	$("#selectbox").find("#"+key).each(function(){
		$(this).hover(
			function(){
				$(this).addClass("userselect");
				$(this).children(".deleteuser").show("fast");
			},
			function(){
				$(this).removeClass("userselect");
				$(this).children(".deleteuser").hide("fast");
			}
		);
	});
	$(".selected_user").parent().parent().show();
}



function fRemoveItem(key){
	$("#selectbox").find("#"+key).hide("fast",function(){
		$(this).remove();
		if($(".selected_user").children("ul").children(".user").length <= 0){
			$(".selected_user").parent().parent().hide();
		}
	});
}

function fClose(){
    if(SelectBox.component){
        SelectBox.component.focus();
    }
	//$.modal.close();
	$('#selectbox').modal('hide');
	//$.modal.modal('hide');
}

function fSaveData(ids,names,allNames){
	this.component.data("ids",ids);
	this.component.data("names",names);
	
	this.component.attr("title",allNames);
	this.component.val(names);
	
	this.component.focus();
	//$(".autohint_searchComponent").focus();
	
	this.close();
}

function eShow(component,showPoisition,boxType){
	debugger;
    if(!boxType)  
      boxType = "user";
    if($("#editbox_content").length > 0){
       $("#editbox_content").remove();
    }
    initEditBox(EditBox,boxType);
	this.component = component;
	var editbox = this;
	//this.alone = alone;

	var ids = component.data("ids");
	var names = component.data("names");
	var allNnames = component.data("allNnames");
	$("#editbox_content").html("");
	if (ids && names){
		for(var i=0;i<ids.length;i++){
			this.appendItem(ids[i],names[i],allNnames[i]);
		}
	}
	
	if (component.data("ids") == null)
		return;
		
	component.mouseout(function(){
		$("#editbox").fadeOut("slow");
	});

	if (showPoisition === "down")
		$("#editbox").css("top",component.offset().top+20);
	else
		$("#editbox").css("top",component.offset().top-$("#editbox").height());
	$("#editbox").css("left",component.offset().left);
	
	$("#editbox").hover(	//设定编辑框的显示与隐藏
		function(){
			$(this).stop();
			$(this).fadeTo("fast",1);
		},
		function(){
			editbox.close();			
		}
	);
	
	$("#editbox_content").sortable({filter: 'div' });
	$("#editbox_content").selectable({filter: 'div' });
	
	document.getElementById("editbox_content").onkeydown = eDeleteItem;
	
	$("#selectedcount").text("("+ids.length+"个)");
	
	/**
	$("#editbox_content").keypress(function(event){
		switch (event.keyCode) {
			case 8:
			case 46:
			$(".editboxitem").each(function(){
				$(this).remove();
			});
			break;
			default:
			break;
		}
		
	});
	**/
	$("#editbox").fadeTo("fast",1);
	
}

function eClose(){
	var ids = new Array();
	var names = new Array();
	var allNames = new Array();
	var values = "";
	var orgValues = "";
	
	//var userCount = $(".editboxitem").length;
	var idx = 0;
	$(".editboxitem").each(function(){
		ids[idx] = $(this).attr("key");
		allNames[idx] = $(this).attr("orgValue");
		names[idx] = $(this).text();
		values += $(this).text()+"；";
		orgValues += $(this).attr("orgValue") + "；";
		idx = idx+1;
	});
	this.component.data("ids",ids);
	this.component.data("names",names);
	this.component.attr("title",orgValues);
	if (ids.length > 3){
			this.component.val(names[0]+"；"+names[1]+"；"+names[2]+"；.....");
		}else{
			this.component.val(values);
		}
	debugger;
	$("#editbox").fadeOut("slow");
}

//在人员备选框中增加选择的人员.
function eAppendItem(key,value){
	var itemDiv = "<div id='edit_"+key+"' key='"+key+"' class='editboxitem'>"+value+"</div>";
	$("#editbox_content").append(itemDiv);
	
	$(".editboxitem").each(function(){
		$(this).mousedown(function(){
			$(".editboxitem").each(function(){
				$(this).removeClass("ui-selecting");
				$(this).removeClass("ui-selected");
			});
			$(this).addClass("ui-selecting");
		});
		$(this).mouseup(function(){
			$(this).removeClass("ui-selecting").addClass("ui-selected");
		});
	});
}

function eDeleteItem(e){
	var pressedKey;
	 if (!e) {
     	e = window.event;
     }
     
     if (document.all){
            pressedKey = e.keyCode;
     } else{
            pressedKey = e.which;
     }
     
     switch(pressedKey){
     	case 8:
		case 46:
		$(".ui-selected").each(function(){
				$(this).remove();
			});
		if (document.all){
			e.keyCode = 0; 
        	e.returnValue = false;
        }else{
        	e.which = 0;
        	e.returnValue = false;
        }		
		break;
		default:
		break;
     }
     
     $("#selectedcount").text("("+$(".editboxitem").length+"个)");

}

function initData(obj,ids,names){
	var idArr = new Array();
	var nameArr = new Array();
	
	var idResArr = new Array();
	var nameResArr = new Array();
	
	var objValue = "";
	
	if(ids && names){
		idArr = ids.split(",");
		nameArr = names.split(",");
		
		for(var i=0;i<idArr.length;i++){
			if(idArr[i] !== ""){
				idResArr[i] = idArr[i];
			}
		}
		
		for(var i=0;i<nameArr.length;i++){
			if(nameArr[i] !== ""){
				nameResArr[i] = nameArr[i];
				objValue += nameResArr[i] + "；";
			}
		}
	}
	
	obj.data("ids",idResArr);
	obj.data("names",nameResArr);
	obj.attr("title",names);
	if (nameArr.length > 3){
			obj.val(nameArr[0]+"；"+nameArr[1]+"；"+nameArr[2]+"；.....");
	}else{
			obj.val(objValue);
	}
}

function deleteuser(o){
	o.hide("fast",function(){
		o.remove();
	});
}
