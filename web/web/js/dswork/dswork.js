//jquery.js和jskey_core.js支持
//此文件的MaskControl需要
var $dswork = {};
//日期时间
$dswork.showDate = function(){
	var obj = arguments[0];
	var f = arguments[1] || "yyyy-MM-dd";
	$jskey.calendar.show(obj,{
		skin:'default', lang:0,
		beginYear:1970, endYear:2070,
		format:f, sample:f
	});
};
//获得站点虚拟根目录，例如："/root"
$dswork.getWebRootPath = function(){
	var p = window.location.pathname;
	var i = p.indexOf("/");
	if(i != 0){
		p = p.substring(0, i);
		return "/" + p;
	}
	return (p.match(/\/\w+/))[0];
};
var ctx = $dswork.getWebRootPath();
$dswork.getChoose = function(m){
	m.url = ctx + "/commons/share/getChoose.jsp";
	return $jskey.dialog.show(m);
};
$dswork.getChooseByKey = function(m){
	m.url = ctx + "/commons/share/getChooseByKey.jsp";
	return $jskey.dialog.show(m);
};
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");//移除首尾空格
};
String.prototype.replaceAll = function(t, u){
	var i = this.indexOf(t);
	var r = "";
	if(i == -1) return this;
	r += this.substring(0, i) + u;
	if(i + t.length < this.length){
		r += (this.substring(i + t.length, this.length)).replaceAll(t, u);
	}
	return r;
};
function attachUrl(){
	var url = arguments[0];
	var frameId = arguments[1] || "mainFrame";
	if(!url){
		url = "about:blank";
	}
	else if(url.indexOf("/") == 0){
		url = ctx + url;
	}
	document.getElementById(frameId).src = url;
}
//操作导航目录
$dswork.showNavigation = function(title){
	try{
		var url = window.location.href;
		top.frames['middleFrame'].navigation.addUrl(title, url);
		top.frames['middleFrame'].navigation.show();
	}
	catch(ex){
		//window.alert(ex.name);
		//window.alert(ex.message);
	}
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
		if(_arr.length > 1){
			_msg = _arr[1];
		}
		else{
			switch(_arr[0]){
				case "0": _msg = "操作失败！";break;
				case "1": _msg = "操作成功！";break;
				default: _msg = (!isNaN(_arr[0]))?"":_arr[0];
			}
		}
		$dswork.result = {type:_arr[0], msg:_msg};
	}
	catch(e){
		$dswork.result = {type:"", msg:""};
	}
	return $dswork.result.msg;
};
$dswork.doAjax = false;
$dswork.doAjaxObject = new MaskControl();
/**
 * 蒙版信息控件
 * 调用方法
 * var obj=new MaskControl();//初始化
 * obj.show("显示的提示信息");//显示蒙版提示信息
 * obj.hide();//隐藏蒙版提示信息
 * obj.autoDelayHide=function(html,timeOut)//显示提示信息，并隔timeOut(1000代表1秒)自动关闭
 */
function MaskControl(){
	this.callBack = null;
	var self = this;
	this.show = function(html){
		var loader = $("#div_maskContainer");
		if(loader.length == 0){
			var str = "<div id='div_maskContainer'><div id='div_loading'></div><div id='div_Mask'></div>";
			if(document.all){//ie中将层浮于顶层
				str += '<iframe style="position:absolute; z-index:2000; width:expression(this.previousSibling.offsetWidth); ';
				str += 'height:expression(this.previousSibling.offsetHeight); ';
				str += 'left:expression(this.previousSibling.offsetLeft); top:expression(this.previousSibling.offsetTop); ';
				str += 'display:expression(this.previousSibling.style.display); " scrolling="no" frameborder="no"></iframe>';
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
		if(self.callBack != null){
			self.callBack();
		}
		this.callBack = null;
	},
	this.autoDelayHide = function(html, timeOut){
		var loader = $("#div_maskContainer");
		if(loader.length == 0){
			this.show(html);
		}
		else{
			var tipDiv = $("#div_loading");
			tipDiv.html(html);
		}
		if(timeOut == undefined){
			timeOut = 3000;
		}
		window.setTimeout(this.hide, timeOut);
	}
}
$(function(){
	function showRequest(formData, jqForm, options){
		var queryString = $.param(formData); //组装数据，插件会自动提交数据
		$dswork.doAjaxObject.show("<img src='/web/js/dswork/loading.gif' />正在保存……");
		return true;
	}
	function showResponse(responseText, statusText){
		$dswork.doAjaxObject.autoDelayHide($dswork.checkResult(responseText), 2000);
		$dswork.doAjaxObject.callBack = $dswork.callback;
	}
	_options = {
		beforeSubmit:showRequest, //提交前
		success:showResponse, //提交后 
		resetForm:false //成功提交后，重置所有的表单元素的值
	};
	
	try{
	// m={id:"dsworkDialog",title:"Window",width:"400",height:"300",args:{url:"",any:*}}
	$dswork.getChooseDialog = function(m){
		m.url = ctx + "/commons/share/getChoose.jsp";
		$jskey.dialog.showDialog(m);
	};
	$dswork.getChooseDialogByKey = function(m){
		m.url = ctx + "/commons/share/getChooseByKey.jsp";
		$jskey.dialog.showDialog(m);
	};
	}catch(e){}
});