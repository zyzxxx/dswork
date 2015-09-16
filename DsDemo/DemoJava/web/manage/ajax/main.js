require.config({
	baseUrl: '/web/js',
	shim:{
		'dswork':['jquery','jquery.form','jquery.easyui'],
		'dswork.get':['dswork']
	},
    paths: {
        'jquery'       :'./jquery/jquery',
        'jquery.easyui':'./easyui/jquery.easyui',
        'jquery.form'  :'./jquery/jquery.form',
        'jskey'        :'./jskey/jskey_core',
        'dswork'       :'./dswork/dswork',
        'dswork.get'   :'./dswork/get'
    }
});
require(['jskey'], function(p) {
	$jskey.$link("/web/js/easyui/themes/default/easyui.css");
	$jskey.$link("/web/themes/default/frame.css");
});
