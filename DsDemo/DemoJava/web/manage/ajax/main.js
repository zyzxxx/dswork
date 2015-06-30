$jskey.$link("/web/js/easyui/themes/default/easyui.css");
$jskey.$link("/web/themes/default/frame.css");
require.config({
	shim:{
		'dswork':['jquery.form','jquery.easyui'],
		'get':['dswork']
	},
    paths: {
        'jquery.easyui': '/web/js/easyui/jquery.easyui',
        'jquery.form': '/web/js/jquery/jquery.form',
        'dswork': '/web/js/dswork/dswork',
        'get': '/web/js/dswork/get'
    }
});
