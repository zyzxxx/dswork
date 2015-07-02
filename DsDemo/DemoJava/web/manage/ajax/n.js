// $.getScript("/web/js");
$.ajaxSetup({cache:true}); 
$jskey.$link("/web/js/easyui/themes/default/easyui.css");
$jskey.$link("/web/themes/default/frame.css");
$.getScript("/web/js/jquery/jquery.form.js", function(){
	$.getScript("/web/js/easyui/jquery.easyui.js", function(){
		$.getScript("/web/js/dswork/dswork.js", function(){
			$dswork.doAjax = true;
			$.getScript("/web/js/dswork/get.js", function(){
				$dswork.loaded();
			});
		});
	});
});