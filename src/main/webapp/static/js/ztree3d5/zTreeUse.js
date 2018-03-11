//#######################################################################################################################
//############################################### ztree封装包 ################################################
//#######################################################################################################################
//treeDataJson 树数据， treeId, rootName
function zTreeUse(treeDataJson, treeId) {
	this.treeDataJson = treeDataJson;
	this.treeId = treeId;
	this.setting = {edit:{removeTitle: "删除",renameTitle: "修改",drag:{prev:this.dropPrev, inner:false, next:this.dropNext}, enable:true, showRemoveBtn:false, showRenameBtn:false, showRemoveBtn:this.showRemoveBtn, showRenameBtn:this.showRenameBtn, editNameSelectAll:true}, data:{simpleData:{enable:true}}, callback:{onClick:this.onClickByNode, onRemove:this.onRemove, onRename:this.onRename,beforeRename: this.zTreeBeforeRename, beforeEditName:this.zTreeBeforeEditName, beforeRemove:this.beforeRemove, beforeDrag:this.beforeDrag, beforeDrop:this.beforeDrop, onDrop:this.onDrop}, view:{showLine:false, selectedMulti:false}};
	this.zNodes = [{id:1, pId:0, name:"\u5168\u90e8", open:true, childOuter:false}];
	//初始化树数据
	this.initZTree = function () {
		var nodes = eval("(" + this.treeDataJson + ")");
		this.bulidData(nodes);
		$.fn.zTree.init($("#" + this.treeId), this.setting, this.zNodes);
	};
	//组织数据
	this.bulidData = function (nodes) {
		var tempArr;
		for (var el in nodes) {
			tempArr = {"id":nodes[el].fid, "pId":1, "name":nodes[el].fname};
			this.zNodes.push(tempArr);
		}
	};
	//节点拖动排序
	this.onDrop = function (event, treeId, treeNodes, targetNode, moveType, isCopy) {
	};
	this.beforeRemove = function (treeId, treeNode) {
		return confirm("\u786e\u8ba4\u5220\u9664\u8282\u70b9\uff1a" + treeNode.name + " \u5417\uff1f");
	};
		//节点删除事件
	this.onRemove = function (e, treeId, treeNode) {
	};
	//节点编辑事件
	this.onRename = function (e, treeId, treeNode) {
	};
	this.zTreeBeforeRename = function(treeId, treeNode, newName, isCancel) {
		if ($.trim(newName) == "") {
			errorTip("\u8282\u70b9\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01");
			return false;
		}
		
		return true;
	}
	
	this.zTreeBeforeEditName = function (treeId, treeNode) {
	};
	//判断节点是否增加删除按钮
	this.showRemoveBtn = function (treeId, treeNode) {
		return treeNode.id != "1";
	};
	
	//判断节点是否增加编辑按钮
	this.showRenameBtn = function (treeId, treeNode) {
		return treeNode.id != "1";
	};
	//新类型
	this.addTreeNode = function (treeId,nodeId,addNodeId,addNodeName) {
		var newtype = ("<li id=" +addNodeId + "><a href='#'style='word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis; display:inline-block; width:120px; padding-right:8px;' title='" + addNodeName + "'>" +addNodeName + "</a></li>");
		var nId=nodeId;
		if(nodeId == null || nodeId == ""){
			nId = "1";
		}
		var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
		var rootNode = zTreeObj.getNodeByTId(nId);
		zTreeObj.addNodes(rootNode, {id:addNodeId, pId:1, name:addNodeName});
	};
	//点击节点事件
	this.onClickByNode = function (event, treeId, treeNode, clickFlag) {
	};
	this.dropNext = function (treeId, nodes, targetNode) {
		if (targetNode.childOuter === false) {
			return false;
		}
		return true;
	};
	this.dropPrev = function (treeId, nodes, targetNode) {
		if (targetNode.childOuter === false) {
			return false;
		}
		return true;
	};
	//是否开启节点拖动
	this.beforeDrag = function (treeId, treeNodes) {
		return true;
	};
	this.beforeDrop = function (treeId, treeNodes, targetNode, moveType, isCopy) {
		return true;
	};
}
//ztree超类结束

