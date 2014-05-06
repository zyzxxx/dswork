/**
 * 蒙版信息控件
 * 调用方法
 * var obj=new MaskControl();//初始化
 * obj.show("显示的提示信息");//显示蒙版提示信息
 * obj.hide();//隐藏蒙版提示信息
 * obj.autoDelayHide=function(html,timeOut)//显示提示信息，并隔timeOut(1000代表1秒)自动关闭
 */
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

$(function()
{
	var obj = new MaskControl();
	function showRequest(formData, jqForm, options)
	{
		var queryString = $.param(formData); //组装数据，插件会自动提交数据
		obj.show("<img src='/web/js/dswork/loading.gif' />正在保存……");
		return true;
	}
	function showResponse(responseText, statusText)
	{
		obj.autoDelayHide($dswork.checkResult(responseText), 2000);
		obj.callBack = $dswork.callback;
	}
	_options = 
	{
		beforeSubmit:showRequest, //提交前
		success:showResponse, //提交后 
		resetForm:false //成功提交后，重置所有的表单元素的值
	};
	$dswork.beforeSubmit = function()
	{
		if($dswork.validCallBack != null)
		{
			var rtn = $dswork.validCallBack();
			if(!rtn) return false;
		}
		if(!$jskey.validator.Validate("dataForm", $dswork.validValue || 3))
		{
			return false;
		}
		return true;
	};
	jQuery("#dataFormSave").click(function()
	{
		if($dswork.beforeSubmit())
		{
			if(confirm("确定保存吗？"))
			{
				$("#dataForm").submit();
				return true;
			}
		}
		return false;
	});
	try{$(".form_title").css("width", "20%");}catch(e){}
});