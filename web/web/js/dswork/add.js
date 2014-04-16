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
		obj.autoDelayHide($tecamo.checkResult(responseText), 2000);
		obj.callBack = $tecamo.callback;
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