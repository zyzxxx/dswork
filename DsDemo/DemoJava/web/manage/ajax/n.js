// $.getScript("/web/js");
if(typeof($dswork)!="object"){$dswork={};}
$.ajaxSetup({cache:true}); 
$.getScript("/web/js/jskey/jskey_core.js", function(){
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
});