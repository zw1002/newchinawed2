(function($){
	$.fn.flow = function(option){
		var defaultOptions = {
			padding: 5,
			width : 55,
			height : 10,
			lineWidth : 90,//75,
			lineHeight : 35,
			slider : 10,
			handle : "draw",
			img : {
				right : "/img/workflow/flowimages/right_sword.png",
				up : "/img/workflow/flowimages/right_sword.png"
			},
			hover : function(){
				
			},
			remove : function(){
				
			},
			click : function(){
				
			}
		}
		var opts = $.extend(defaultOptions,option);
		return this.each(function(){
			var currentQuery = $(this);
			var drawLine = function(start,end){
				if($(start).data($(end).attr("id")) == true){
					return;
				}
				var offsetLeft = $("#" + opts.handle).offset().left-55;//35;
				var offsetTop = $("#" + opts.handle).offset().top-34;
				var x1 = start.offset().left - offsetLeft + opts.padding + opts.width;
				var x2 = start.offset().top - offsetTop + opts.padding + opts.height;
				var y1 = end.offset().left - offsetLeft-50;//35;
				var y2 = end.offset().top - offsetTop + opts.padding + opts.height;
				var jg_draw = new jsGraphics(opts.handle);
				jg_draw.setStroke(2); 
				//alert(x1 + ":" + x2 + "," + y1 + ":" + y2);
				//jg_draw.setStroke(Stroke.DOTTED); 
				//jg_draw.drawImage("images/left_sword.png",0,0,10,15,"onclick='alert($(\"#draw\").html())'");
				if(x2 == y2){
					jg_draw.drawLine(x1,x2,y1,y2);
					jg_draw.drawImage(opts.img.right,y1-5,y2-7,10,15);
				}else{
					if(x2 < y2){
						jg_draw.drawLine(x1,x2,x1 + opts.slider,x2);
						jg_draw.drawLine(x1 + opts.slider,x2,x1 + opts.slider,y2);
						jg_draw.drawLine(x1 + opts.slider,y2,y1,y2);
						jg_draw.drawImage(opts.img.up,y1-5,y2-7,10,15);
					}else{
						jg_draw.drawLine(x1,x2,y1 - opts.slider,x2);
						jg_draw.drawLine(y1-opts.slider,x2,y1 - opts.slider,y2);
						jg_draw.drawLine(y1 - opts.slider,y2,y1,y2);
						jg_draw.drawImage(opts.img.right,y1-5,y2-7,10,15);
					}
				}
				jg_draw.paint();
				$(start).data($(end).attr("id"),true);
			}	
		
			var createObjectList = function(id,level){
				var root = {
					id:0,
					next:"",
					name:"",
					child:[],
					level : 0,
					getSize : function(){
						return this.child.length;
					},
					getChild:function(i){
						return $(".before[id=" + this.child[i].id + "]");
					}
				};
				if (typeof(id) == "undefined") { 
				  return; 
				}  
				var $rootObject = $(".before[id=" + id + "]",currentQuery);
				root.id=$rootObject.attr("id");
				root.next=$rootObject.attr("next");
				root.name=$rootObject.text();
				root.level = level;
				var childID = root.next.split(",");
				
				for(var i = 0;i < childID.length;i++){
					var child = $(".before[id=" + childID[i] + "]",currentQuery);
					if(child.size() == 0){
						continue;	
					}
					var childObject = createObjectList(child.attr("id"),level + 1);
					root.child.push(childObject);
				}
				return root;
			}
		
			var rootID = $(".before[begin]",currentQuery).attr("id");
			var myObjectList = createObjectList(rootID,0);
			var drawDiv = function(objectList,spanID,left,top,maxHeight){
				var span = $("#" + spanID);
				var $root = $(".before[id=" + objectList.id + "]",currentQuery).clone();
				$(".before[id=" + objectList.id + "]",currentQuery).remove();
				if($(".before[id=" + objectList.id + "]").size() > 0){
					$root = $(".before[id=" + objectList.id + "]").eq(0);
					return;
				}else{
					$("#" + opts.handle + " .before").each(function(){
						var leftPX = $(this).css("left");
						var topPX = $(this).css("top");

						if(leftPX == (left+'px') && topPX == (top+'px')){
							top = top + opts.lineHeight;
						}
					});	
					$root.appendTo(span);
				}
				
				$root.css("position","absolute");
				$root.css("left",left);
				$root.css("top",top);	
				/**
				*节点状态 -1 未到达 0 处理中 1已处理
				 */
				switch($root.attr("nodeStatus")){
					case "-1":
						if($root.attr("assign") == "true"){
							$root.css("background","url(/img/workflow/flowimages/bak_center_noper.png) repeat-x");
						}else{
							$root.css("background","url(/img/workflow/flowimages/bak_center.png) repeat-x");
						}
						break;
					case "0":
						$root.css("background","url(/img/workflow/flowimages/bak_yellow_center.png) repeat-x");
						break;
					case "1":
						$root.css("background","url(/img/workflow/flowimages/bak_green_center.png) repeat-x");
						break;
					default:
						$root.css("background","url(/img/workflow/flowimages/bak_center.png) repeat-x");
						break;
				}

				if(objectList.getSize() == 0){
					return;
				};
				var nextLeft = opts.lineWidth ;//* (objectList.level +1) ;
				nextLeft=left +nextLeft+45;
				var nextHeight = top;
				for(var i = 0; i < objectList.getSize();i++){
					if( objectList.getSize() > 1 && i > 0){
						top+=15;
					}
					
					for(var j = 0; j < i; j++){
						if(objectList.child[j].getSize() >= 2){
							top = top + 50*(objectList.child[j].getSize()-1)-45 -(objectList.child[j].getSize()-1)*5;
						}
					}
					
					drawDiv(objectList.child[i],opts.handle,nextLeft,opts.lineHeight * (i) +top,nextHeight);
					try{
						drawLine($root,objectList.getChild(i));
					}catch(e){
						//alert(e.description);
						break;
					}
				}
				return;
			}
			try{
				drawDiv(myObjectList,opts.handle,0,0,300);
				$("#" + opts.handle).find(".before").hover(opts.hover,opts.remove).click(opts.click);
			}catch(e){
				//alert(e.description)
			}
		});
	}
})(jQuery)