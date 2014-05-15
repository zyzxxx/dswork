//ztree
$dswork.ztree = {
	id:"mytree", menuName:"divMenu", tree:null, menu:null, dataName:"divTree",
	tid:-1, tpid:-1,//异步加载前选中的节点id和父节点id
	//临时变量
	cid:"", vid:""
};
$dswork.ztree.root = {//在使用数据时可指定根节点，在调用树之前更新root
	id:0, pid:-1, gid:-1, status:0, state:0, code:"", isParent:true, name:""//根节点名称
};
$dswork.ztree.nodeArray = [$dswork.ztree.root];// 如果需要自定义初始化节点，可直接定义此变量

$dswork.ztree.showMenu = function(type, x, y){}
$dswork.ztree.hideMenu = function()
{
	try{$("#" + $dswork.ztree.menuName).menu('hide');}catch(e){}
};
$dswork.ztree.getSelectedNode = function()//用于替代旧ztree的取得当前选中节点，用于单选时
{
	var _arr = $dswork.ztree.tree.getSelectedNodes();
	if(_arr.length > 0)
	{
		return _arr[0];
	}
	else
	{
		var _root = $dswork.ztree.tree.getNodeByParam("id", 0);
		$dswork.ztree.tree.selectNode(_root);//选中
		return _root;
	}
};
$dswork.ztree.beforeClick = function(treeId, treeNode, clickFlag){};//点击节点前函数
$dswork.ztree.click = function(event, treeId, treeNode, clickFlag){};//点击节点函数
$dswork.ztree.beforeDblClick = function(treeId, treeNode){return false;};//双击节点前函数
$dswork.ztree.dblClick = function(event, treeId, treeNode){};//双击节点函数，$dswork.ztree.beforeDblClick必须返回非false

$dswork.ztree.rightClick = function(event, treeId, treeNode)//右击节点函数
{
	if(treeNode && !treeNode.noR)
	{
		$dswork.ztree.tree.selectNode(treeNode);
		$dswork.ztree.click();
		$dswork.ztree.showMenu("node", event.clientX, event.clientY);
	}
};
$dswork.ztree.beforeCheck = function(treeId, treeNode){};//点击节点按钮前函数
$dswork.ztree.check = function(event, treeId, treeNode){};//点击节点按钮函数
$dswork.ztree.refreshNode = function()
{
	var _updateParent = (arguments[0]||false)?true:false;//是否需要刷新父节点，不传默认刷新当前节点
	var _ztree = $dswork.ztree.tree;
	var _node = $dswork.ztree.getSelectedNode();//当前选中节点
	if(_node == null)
	{
		return false;
	}
	var _id = parseInt((_updateParent)?_node.pid:_node.id);//需要刷新的节点id
	if(_id >= 0)
	{
		$dswork.ztree.tid = _node.id;//记录选中节点id
		$dswork.ztree.tpid = _node.pid;//记录选中节点pid
		var _tnode = _ztree.getNodeByParam("id", _id);
		_ztree.selectNode(_tnode);//选中
		if(_updateParent){_tnode.isParent = true};
		_ztree.reAsyncChildNodes(_tnode, "refresh");//重新加载
	}
};
$dswork.ztree.asyncSuccess = function(event, treeId, treeNode, msg)//异步获取数据后加载到树
{
	try
	{
		if($dswork.ztree.tid != -1)//选中刷新前选择的节点，并执行单击事件
		{
			var _ztree = $dswork.ztree.tree;
			var _node = _ztree.getNodeByParam("id", $dswork.ztree.tid);
			var _pnode = _ztree.getNodeByParam("id", $dswork.ztree.tpid);
			if(_node == null)
			{
				if(_pnode == null)
				{
					_pnode = _ztree.getNodeByParam("id", 0);//根节点
				}
				_ztree.selectNode(_pnode);//选中
				_ztree.expandNode(_pnode, true);//展开
			}
			else
			{
				_ztree.selectNode(_node);//选中
				_ztree.expandNode(_node, true);//展开
			}
			$dswork.ztree.click();
		}
	}
	catch(e)
	{
	}
	$dswork.ztree.tid = -1;//还原
	$dswork.ztree.tpid = -1;//还原
};
$dswork.ztree.dataFilter = function (treeId, parentNode, responseData)//异步获取数据后未加载到树
{
//	if(responseData){for(var i =0; i < responseData.length; i++){responseData[i]}}
	return responseData;
};
$dswork.ztree.setFontCss = function(treeId, treeNode){return {};};
$dswork.ztree.url = function(treeNode){}//必须修改，针对根进行异步加载时，treeNode = null
$dswork.ztree.config = {
	callback:null,//必须修改
	view:{
		expandSpeed:"",
		fontCss:function(treeId, treeNode){return $dswork.ztree.setFontCss(treeId, treeNode);}
	},
	async:{
		enable:true,
		dataFilter:function (treeId, parentNode, responseData){return $dswork.ztree.dataFilter(treeId, parentNode, responseData);},
		url:function(treeId, treeNode){return $dswork.ztree.url(treeNode);}
	},
	data:{
		key: {
			name: "name"
		},
		simpleData: {
			//rootPid:"-1",
			enable: true,
			idKey:"id",
			pIdKey:"pid"
		}
	},
	check: {
		enable: false,
		chkStyle: "checkbox",
		chkboxType: {"Y":"ps","N":"s"},
		radioType: "all"
	}
};
$dswork.ztree.load = function()//默认加载页面管理
{
	$dswork.ztree.config.callback = {
		"beforeCheck":$dswork.ztree.beforeCheck,
		"onCheck":$dswork.ztree.check,
		"beforeClick":$dswork.ztree.beforeClick,
		"onClick":$dswork.ztree.click,
		"beforeDblClick":$dswork.ztree.beforeDblClick,
		"onDblClick":$dswork.ztree.dblClick,
		//"beforeRightClick":,
		"onRightClick":$dswork.ztree.rightClick,
		"onAsyncSuccess":$dswork.ztree.asyncSuccess
	};
	$dswork.ztree.tree = $.fn.zTree.init($("#"+$dswork.ztree.id), $dswork.ztree.config, $dswork.ztree.nodeArray);
};
$dswork.ztree.loadData = function(){$dswork.ztree.load();};// 兼容旧版

$dswork.ztree.expandRoot = function()
{
	try
	{
		var _ztree = $dswork.ztree.tree;
		var _pnode = _ztree.getNodeByParam("id", 0);//根节点
		if(_pnode && $dswork.ztree.refreshNode)
		{
			_ztree.selectNode(_pnode);
			_ztree.expandNode(_pnode, true);
			$dswork.ztree.refreshNode();
		}
	}
	catch(e)
	{
	}
};
$dswork.ztree.expandNode = function(treeNode, expandFlag, sonSign, focus, callbackFlag)
{
	return $dswork.ztree.tree.expandNode(treeNode, expandFlag, sonSign, focus, callbackFlag);
};
$dswork.ztree.updateNode = function(treeNode, checkTypeFlag)
{
	return $dswork.ztree.tree.updateNode(treeNode, checkTypeFlag);
};
$dswork.ztree.expandAll = function()
{
	return $dswork.ztree.tree.expandAll(true);
};
$dswork.ztree.getCheckedNodes = function(checked)
{
	return $dswork.ztree.tree.getCheckedNodes(checked);
};
$dswork.ztree.getNodeByParam = function(key, value, parentNode)
{
	return $dswork.ztree.tree.getNodeByParam(key, value, parentNode);
};
$dswork.ztree.getNodesByParam = function(key, value, parentNode)
{
	return $dswork.ztree.tree.getNodesByParam(key, value, parentNode);
};
