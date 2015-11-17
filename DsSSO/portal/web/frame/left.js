$jskey.menu.load = function(index, id){
	var obj, _o = {};
	for(var i = 0; i < sys.length; i++){
		_o = obj = sys[i];
		if(i == index){// || obj.id == id
			if(obj.data == null || obj.data.length == 0){
				menuload(_o);
				return;
			}
			break;
		}
	}
};
$jskey.menu.click = function(index, id){
	if(sys.length > index){
		$jskey.menu.root = sys[index].domainurl + sys[index].rooturl;
	}
};
function init(){
	var treedata = [];
	for(var i=0; i<sys.length; i++){
		treedata[i] = {index:i, id:sys[i].id, name:sys[i].name, img:"", imgOpen:"", url:"", items:[]};
	}
	//打开多个的话$jskey.menu.root会出错，除非将菜单中的url补全
	$jskey.menu.show(treedata, true);// 只能打开一个，删掉<!DOCTYPE html>则会其余最会置底
	$jskey.menu.clickBar(0);
}
function showSystem(){
	alert("please load leftone.js");
}