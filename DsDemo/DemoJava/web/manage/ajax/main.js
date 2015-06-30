require.config({
	shim:{
		'jskey': ['jquery.easyui'],
		'dswork':['jskey','jquery.form','jquery.easyui'],
		'get':['dswork']
	},
    paths: {
        'jquery.easyui': '/web/js/easyui/jquery.easyui',
        'jquery.form': '/web/js/jquery/jquery.form',
        'jskey': '/web/js/jskey/jskey_core',
        'dswork': '/web/js/dswork/dswork',
        'get': '/web/js/dswork/get'
    }
});
require(['jskey'], function() {// 这里随便 搞一个名称
	$jskey.$link("/web/js/easyui/themes/default/easyui.css");
	$jskey.$link("/web/themes/default/frame.css");
});
