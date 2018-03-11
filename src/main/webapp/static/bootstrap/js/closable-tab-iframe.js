//子页面不用iframe，用div展示
var closableTab = {
	
    //添加tab
	addTab:function(tabItem){ //tabItem = {id,name,url,closable}

		var id = "tab_seed_" + tabItem.id;
		var container ="tab_container_" + tabItem.id;
		$("li[id^=tab_seed_]").removeClass("active");
		$("div[id^=tab_container_]").removeClass("active");

		if(!$('#'+id)[0]){
			var li_tab = '<li role="presentation" class="" id="'+id+'"><a href="#'+container+'"  role="tab" data-toggle="tab" style="position: relative;border-radius: 20px;border-bottom: #ddd;">'+tabItem.name;
			if(tabItem.closable){
				li_tab = li_tab + '<span class="fa fa-remove" tabclose="'+id+'" style="position: absolute;right:-2px;top: 14px;font-size: 10px;cursor:pointer;"  onclick="closableTab.closeTab(this)"></span></a></li> ';
			}else{
				li_tab = li_tab + '</a></li>';
			}

		 	var tabpanel = '<div role="tabpanel" class="tab-pane" id="'+container+'" style="width: 100%;">'+
	    					  '正在加载...'+
	    				   '</div>';
			$('.nav-tabs').append(li_tab);
			$('.tab-content').append(tabpanel);
			$('#'+container).html("<iframe class='innerIframe' style='width:100%;' frameborder='0' name='Frame_"+tabItem.id+"' id='Frame_"+tabItem.id
				+"' src='"+tabItem.url+"' scrolling='auto' ></iframe>");
			//$('#'+container).load(tabItem.url,function(response,status,xhr){
			//	if(status=='error'){//status的值为success和error，如果error则显示一个错误页面
			//		$(this).html(response);
			//	}
			//});
			$('.innerIframe').attr('height',document.documentElement.clientHeight-119);//设置iframe高度
		}
		$("#"+id).addClass("active");
		$("#"+container).addClass("active");
	},

	//关闭tab
	closeTab:function(item){
		var val = $(item).attr('tabclose');
		var containerId = "tab_container_"+val.substring(9);
   	    
   	    if($('#'+containerId).hasClass('active')){
   	    	$('#'+val).prev().addClass('active');
   	    	$('#'+containerId).prev().addClass('active');
   	    }


		$("#"+val).remove();
		$("#"+containerId).remove();
	},
	//自写关闭
	closeTab2:function(id){
		var val ="tab_seed_"+id;// $('#'+id).attr('tabclose');tab_seed_150
		var containerId = "tab_container_"+id;//val.substring(9);

		if($('#'+containerId).hasClass('active')){
			$('#'+val).prev().addClass('active');
			$('#'+containerId).prev().addClass('active');
		}
		$("#"+val).remove();
		$("#"+containerId).remove();
	}
}