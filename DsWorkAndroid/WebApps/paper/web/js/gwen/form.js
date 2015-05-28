$gwen = {};
$gwen.form = {};
$gwen.result = {type:"", msg:""};
$gwen.checkResult = function(responseText){
	$gwen.result = {type:"", msg:""};
	try{
		var _msg = "", _arr = (responseText + "").split(":");
		if(_arr.length > 1){_msg = _arr[1];}
		else{switch(_arr[0]){
			case "0": _msg = "操作失败！";break;
			case "1": _msg = "操作成功！";break;
			default: _msg = (!isNaN(_arr[0]))?"":_arr[0];
		}}
		$gwen.result = {type:_arr[0], msg:_msg};
	}
	catch(e){$gwen.result = {type:"", msg:""};}
	return $gwen.result.msg;
};
$(function(){
	$("#dataFormSave").click(function () {
        var options = {
            url: $("#dataForm").attr("action"),
            type: 'post',
            dataType: 'text',
            data: $("#dataForm").serialize(),
            enctype: $("#dataForm").attr("enctype"),
            success: function (data) {
            	alert($gwen.checkResult(data));
                $gwen.form.callback();
            }
        };
        $("#dataForm").ajaxSubmit(options);
        return false;
    });
	$("#dataForm").submit(function(e){
		e.preventDefault();try{$("#dataFormSave").click();}catch(e){}
	});
	$("#form_del").submit(function(e){
		e.preventDefault();
        var options = {
            url: $("#form_del").attr("action"),
            type: 'post',
            dataType: 'text',
            data: $("#form_del").serialize(),
            success: function (data) {
            	alert($gwen.checkResult(data));
                $gwen.form.callback();
            }
        };
        $.ajax(options);
        return false;
	});
});
