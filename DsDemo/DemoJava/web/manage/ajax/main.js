require.config({
	shim:{
		'jquery.easyui': ['jquery'],
		'jquery.form': ['jquery'],
		'jskey': ['jquery.easyui'],
		'dswork':['jskey','jquery.form','jquery.easyui'],
		'get':['dswork']
	},
    paths: {
        'jquery': '/web/js/jquery/jquery',
        'jquery.easyui': '/web/js/easyui/jquery.easyui',
        'jquery.form': '/web/js/jquery/jquery.form',
        'jskey': '/web/js/jskey/jskey_core',
        'dswork': '/web/js/dswork/dswork',
        'get': '/web/js/dswork/get'
    }
});