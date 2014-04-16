$(function()
{
	$("#dataForm").submit(function(event)
	{
		event.preventDefault();
		$("#dataForm").ajaxSubmit(_options);
	});
}); 

$dswork.validCallBack = function()
{
	return true;
};