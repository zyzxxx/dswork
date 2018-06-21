function show(url, args, width, height){
	url += ((url.indexOf("?") == -1) ? "?jskey=" : "&jskey=") + (new Date().getTime());//防止缓存
	return window.showModalDialog(url, args, "dialogWidth:" + width + "px;dialogHeight:" + height
			+ "px;help:no;center:yes;status:no;scroll:auto;resizable:yes"
			+ ";dialogTop:" + ((window.screen.availHeight - height) / 3)
			+ ";dialogLeft:" + ((window.screen.availWidth - width) / 2)
	);
}
var myindex = 0;
function showSystem(){
	try{
		var i = show("./system.html", {sys:sys,index:myindex}, 300, 200);
		if(i != null){
			var obj = sys[i]; 
			if(obj.data == null || obj.data.length == 0){
				menuload(obj);
			}
			else{
				$jskey.menu.showNode(obj.index, obj.data, obj.domainurl + obj.rooturl);
			}
		}
	}
	catch(e){
	}
}
$jskey.menu.showNode = function(i, data){
	if(sys.length > i){
		myindex = i;
		// true置底仅打开一个，false不置底可打开多个，null不置底仅打开一个
		$jskey.menu.show(data, true, sys[i].domainurl + sys[i].rooturl);
		if(data.length > 0)
		{
			$jskey.menu.clickBar(0);
		}
	}
};
function init(){
	menuload(sys[0]);
}
