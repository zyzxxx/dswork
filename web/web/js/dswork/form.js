$(function(){
	$dswork.beforeSubmit = function(){
		if(!$dswork.validCallBack()){return false;}
		return $jskey.validator.Validate("dataForm", $dswork.validValue || 3);
	};
	jQuery("#dataFormSave").click(function(){
		if($dswork.beforeSubmit()){if(confirm("确定保存吗？")){
			if($dswork.doAjax){$("#dataForm").ajaxSubmit($dswork.doAjaxOption);}
			else{$("#dataForm").submit();}
			return true;
		}}
		return false;
	});
	try{$(".form_title").css("width", "20%");}catch(e){}
});
$dswork.validCallBack = function(){return true;};