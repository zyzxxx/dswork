//$jskey.calendar.show,需要jskey_core.js
var $dswork = {};
//日期时间
$dswork.showDate = function()
{
	var obj = arguments[0];
	var f = arguments[1] || "yyyy-MM-dd";
	$jskey.calendar.show(obj,
	{
		skin:'default', lang:0,
		beginYear:1970, endYear:2070,
		format:f, sample:f
	});
};
//获得站点虚拟根目录，例如："/root"
$dswork.getWebRootPath = function()
{
	var p = window.location.pathname;
	var i = p.indexOf("/");
	if(i != 0)
	{
		p = p.substring(0, i);
		return "/" + p;
	}
	return (p.match(/\/\w+/))[0];
};
var ctx = $dswork.getWebRootPath();
$dswork.getChoose = function(m)
{
	m.url = ctx + "/commons/share/getChoose.jsp";
	return $jskey.dialog.show(m);
};
$dswork.getChooseByKey = function(m)
{
	m.url = ctx + "/commons/share/getChooseByKey.jsp";
	return $jskey.dialog.show(m);
};
String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/g, "");//移除首尾空格
};
String.prototype.replaceAll = function(t, u) {
   var i = this.indexOf(t);
   var r = "";
   if(i == -1) return this;
   r += this.substring(0, i) + u;
   if(i + t.length < this.length)
   {
       r += (this.substring(i + t.length, this.length)).replaceAll(t, u);
   }
   return r;
}
function attachUrl()
{
	var url = arguments[0];
	var frameId = arguments[1] || "mainFrame";
	url = (url)?(ctx + url):"about:blank";
	document.getElementById(frameId).src = url;
}
//操作导航目录
$dswork.showNavigation = function(title)
{
	try
	{
		var url = window.location.href;
		top.frames['middleFrame'].navigation.addUrl(title, url);
		top.frames['middleFrame'].navigation.show();
	}
	catch(ex)
	{
		//window.alert(ex.name);   
		//window.alert(ex.message);   
	}
};

//form
$dswork.callback = null;
$dswork.validCallBack = null;
$dswork.result = {type:"", msg:""};
$dswork.checkResult = function(responseText)
{
	$dswork.result = {type:"", msg:""};
	try
	{
		var _arr = (responseText + "").split(":");
		var _msg = "";
		if(_arr.length > 1)
		{
			_msg = _arr[1];
		}
		else
		{
			switch(_arr[0])
			{
				case "0": _msg = "操作失败！";break;
				case "1": _msg = "操作成功！";break;
				default: _msg = (!isNaN(_arr[0]))?"":_arr[0];
			}
		}
		$dswork.result = {type:_arr[0], msg:_msg};
	}
	catch(e)
	{
		$dswork.result = {type:"", msg:""};
	}
	return $dswork.result.msg;
}
