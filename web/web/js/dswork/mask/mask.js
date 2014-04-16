/**
 * 蒙版信息控件
 * 用法：引用mask.js,自动加载css
 * 调用方法
 * var obj=new MaskControl();//初始化
 * obj.show("显示的提示信息");//显示蒙版提示信息
 * obj.hide();//隐藏蒙版提示信息
 * obj.autoDelayHide=function(html,timeOut)//显示提示信息，并隔timeOut(1000代表1秒)自动关闭
 */

if(true){
	var _jsPath=document.getElementsByTagName("script")[document.getElementsByTagName("script").length - 1].src.substring(0, document.getElementsByTagName("script")[document.getElementsByTagName("script").length - 1].src.lastIndexOf("/") + 1);
	var _ref=document.createElement("link");
	_ref.setAttribute("rel","stylesheet");
	_ref.setAttribute("type","text/css");
	_ref.setAttribute("href",_jsPath+"mask.css");
	var _head=document.getElementsByTagName("head");
	_head[_head.length-1].appendChild(_ref);
}
function MaskControl()
{
	this.callBack = null;
	var self = this;
	this.show = function(html)
	{
		var loader = $("#div_maskContainer");
		if(loader.length == 0)
		{
			var str = "<div id='div_maskContainer'><div id='div_loading'></div><div id='div_Mask'></div>";
			if(document.all)//ie中将层浮于顶层
			{
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
		if(html == undefined)
			html = "";
		tipDiv.html(html);
		loader.show();
		var x = (w - tipDiv.width()) / 2;
		var y = (h - tipDiv.height()) / 2;
		tipDiv.css("left", x);
		tipDiv.css("top", y);
	},
	this.hide = function()
	{
		var loader = $("#div_maskContainer");
		if(loader.length == 0)
			return;
		loader.remove();
		if(self.callBack != null)
		{
			self.callBack();
		}
		this.callBack = null;
	},
	this.autoDelayHide = function(html, timeOut)
	{
		var loader = $("#div_maskContainer");
		if(loader.length == 0)
		{
			this.show(html);
		}
		else
		{
			var tipDiv = $("#div_loading");
			tipDiv.html(html);
		}
		if(timeOut == undefined)
			timeOut = 3000;
		window.setTimeout(this.hide, timeOut);
	}
}
