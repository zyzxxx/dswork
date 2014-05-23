//jquery.js和jskey_core.js支持
var $dswork = {};
$dswork.showDate = function(){
	var obj = arguments[0];
	var f = arguments[1] || "yyyy-MM-dd";
	$jskey.calendar.show(obj,{
		skin:'default', lang:0,
		beginYear:1970, endYear:2070,
		format:f, sample:f
	});
};
$dswork.getWebRootPath = function(){
	var p = window.location.pathname;
	var i = p.indexOf("/");
	if(i != 0){p = p.substring(0, i);return "/" + p;}
	return (p.match(/\/\w+/))[0];
};
var ctx = $dswork.getWebRootPath();//站点虚拟根目录，没有则改为"/"
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g, "");};
String.prototype.replaceAll = function(t, u){
	var i = this.indexOf(t);
	var r = "";
	if(i == -1){return this;}
	r += this.substring(0, i) + u;
	if(i + t.length < this.length){r += (this.substring(i + t.length, this.length)).replaceAll(t, u);}
	return r;
};
function attachUrl(){
	var url = arguments[0];
	var frameId = arguments[1] || "mainFrame";
	if(!url){url = "about:blank";}
	else if(url.indexOf("/") == 0){url = ctx + url;}
	document.getElementById(frameId).src = url;
}
//操作导航目录
$dswork.showNavigation = function(title){
	try{
		var url = window.location.href;
		top.frames['middleFrame'].navigation.addUrl(title, url);
		top.frames['middleFrame'].navigation.show();
	}catch(ex){}
};

//form
$dswork.callback = null;
$dswork.validCallBack = null;
$dswork.result = {type:"", msg:""};
$dswork.checkResult = function(responseText){
	$dswork.result = {type:"", msg:""};
	try{
		var _arr = (responseText + "").split(":");
		var _msg = "";
		if(_arr.length > 1){_msg = _arr[1];}
		else{switch(_arr[0]){
			case "0": _msg = "操作失败！";break;
			case "1": _msg = "操作成功！";break;
			default: _msg = (!isNaN(_arr[0]))?"":_arr[0];
		}}
		$dswork.result = {type:_arr[0], msg:_msg};
	}
	catch(e){$dswork.result = {type:"", msg:""};}
	return $dswork.result.msg;
};
/**
 * 信息控件
 * var o=new MaskControl();//初始化
 * o.show("显示提示信息");
 * o.hide();//隐藏提示信息
 * o.autoDelayHide=function(html,time)//显示提示信息，并隔time毫秒后关闭
 */
function MaskControl(){
	this.callBack = null;
	var self = this;
	this.show = function(html){
		var loader = $("#div_maskContainer");
		if(loader.length == 0){
			var str = "<div id='div_maskContainer'><div id='div_loading'></div><div id='div_Mask'></div>";
			if(document.all){//ie中将层浮于顶层
				str += '<iframe style="left:expression(this.previousSibling.offsetLeft);top:expression(this.previousSibling.offsetTop);width:expression(this.previousSibling.offsetWidth);height:expression(this.previousSibling.offsetHeight);';
				str += 'position:absolute;z-index:2000;display:expression(this.previousSibling.style.display);" scrolling="no" frameborder="no"></iframe>';
			}
			str += "</div>"
			loader = $(str);
			$("body").append(loader);
		}
		self.loader = loader;
		var w = $(window).width();
		var h = $(window).height();
		var divMask = $("#div_Mask");
		divMask.css("top", 0).css("left", 0).css("width", w).css("height", h);
		var tipDiv = $("#div_loading");
		if(html == undefined){html = "";}
		tipDiv.html(html);
		loader.show();
		var x = (w - tipDiv.width()) / 2;
		var y = (h - tipDiv.height()) / 2;
		tipDiv.css("left", x);
		tipDiv.css("top", y);
	},
	this.hide = function(){
		var loader = $("#div_maskContainer");
		if(loader.length == 0){return;}
		loader.remove();
		if(self.callBack != null){self.callBack();}
		this.callBack = null;
	},
	this.autoDelayHide = function(html, timeOut){
		var loader = $("#div_maskContainer");
		if(loader.length == 0){this.show(html);}
		else{$("#div_loading").html(html);}
		if(timeOut == undefined){timeOut = 3000;}
		window.setTimeout(this.hide, timeOut);
	}
}
$dswork.doAjax = false;
$dswork.doAjaxObject = new MaskControl();
$dswork.showRequest = function(formData, jqForm, options){
	$dswork.doAjaxObject.show("<img src='/web/js/dswork/loading.gif' />正在保存……");return true;
};
$dswork.showResponse = function(data, status, xhr){
	$dswork.doAjaxObject.autoDelayHide($dswork.checkResult(data), 2000);
	$dswork.doAjaxObject.callBack = $dswork.callback;
};
$dswork.doAjaxShow = function(data, callback){
	$dswork.doAjaxObject.autoDelayHide($dswork.checkResult(data), 2000);
	$dswork.doAjaxObject.callBack = callback;
};
$dswork.doAjaxOption = {beforeSubmit:$dswork.showRequest,success:$dswork.showResponse};
_options = $dswork.doAjaxOption;

$dswork.getChoose = function(m){m.url = ctx + "/commons/share/getChoose.jsp";return $jskey.dialog.show(m);};
$dswork.getChooseByKey = function(m){m.url = ctx + "/commons/share/getChooseByKey.jsp";return $jskey.dialog.show(m);};
$dswork.getChooseDialog = function(m){m.url = ctx + "/commons/share/getChoose.jsp";$jskey.dialog.showDialog(m);};
$dswork.getChooseDialogByKey = function(m){m.url = ctx + "/commons/share/getChooseByKey.jsp";$jskey.dialog.showDialog(m);};

$dswork.showTree = function(o){if(typeof(o)!="object"){o={};}
	var ini = {id:"showTree"
		,title:"请选择"
		,url:function(node){return "";}
		,width:400,height:300,left:0,top:0
		,click:function(id, node){return true;}
		,dataFilter:function(id, pnode, data){return data;}
		,check:function(treeId, node){}//点击节点按钮前函数
		,async:true
		,checkEnable:true
		,chkStyle:"radio"
		,tree:null
		,nodeArray:[]
	};
	var opts = $.extend(ini, o);
	var root = {id:"0", pid:"-1", isParent:true, name:opts.title, nocheck:true};
	root = $.extend(root, opts.root);
	if(root.name)
	if(opts.nodeArray.length == 0){
		opts.nodeArray.push(root);
	}
	opts.divid = opts.id + "_div";
	var $win = $('#'+opts.divid);
	if($win.length){$win.css("display", "");}
	else{
		$win = $('<div id="'+opts.divid+'" style="z-index:100000;position:absolute;background-color:#ffffff;border:0px;"></div>').appendTo('body');
	}
	if($('#jskey_temp_div_close').length > 0){$winsub.remove();}
	$winsub = $('<div id="jskey_temp_div_close" style="filter:alpha(opacity=1);opacity:0;z-index:99999;top:0px;left:0px;position:absolute;background-color:#ffffff;">&nbsp;</div>').appendTo('body');
	$winsub.css("width", $(document).width()).css("height", $(document).height());
	$winsub.bind("click", function(event){
		$win.css("display", "none");
		$winsub.remove();
		return true;
	});
	$win.css("width", opts.width).css("height", opts.height).css("left", opts.left).css("top", opts.top);
	var $tree = $('<ul id="'+opts.id+'" class="ztree" style="border:2px solid #999999;overflow:auto;z-index:100000;background-color:#ffffff;"></ul>');
	$tree.css("width", opts.width-10).css("height", opts.height-10);
	$win.html($tree);
	var config = {view:{expandSpeed:""}
		,async:{enable:opts.async,dataFilter:opts.dataFilter,url:function(id, node){return opts.url(node);}}
		,data:{key:{name:"name"},simpleData: {enable: true,idKey:"id",pIdKey:"pid"}}
		,check:{enable:opts.checkEnable,chkStyle:opts.chkStyle,chkboxType:{"Y":"ps","N":"s"},radioType:"all"}
	};
	config.callback = {
		"beforeCheck":opts.check,
		"onCheck":function(event, treeId, node){$winsub.click();},
		"onClick":opts.click
	};
	$win.css("display", "");
	o.tree = $.fn.zTree.init($tree, config, opts.nodeArray);//放置到原来的o中
	try{
		var _p = o.tree.getNodeByParam("id", 0);//根节点
		o.tree.selectNode(_p);//选中
		o.tree.reAsyncChildNodes(_p, "refresh");//重新加载
	}catch(e){}
};
