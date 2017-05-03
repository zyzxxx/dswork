;!function(){
	$dswork.flow.check = function(flow){
		var r = true;
		var map = {};
		var msg = "";
		for(var i = 0; i < flow.tasks.length; i++){
			var t = flow.tasks[i];
			t.canBegin = false;// 还原上次找的结果
			t.canEnd = false;// 还原上次找的结果
			
			if(!/^[0-9a-zA-Z]*$/g.test(t.alias)){
				msg += "[" + t.alias + "]标识格式不符合要求\n";
				r = false;
			}
		}
		if(!r){return false;}
		
		for(var i = 0; i < flow.tasks.length; i++){
			var t = flow.tasks[i];
			if(map[t.alias+""] !=  null){
				msg += "[" + t.alias + "]标识重复\n";
				r = false;
			}
			map[t.alias+""] = t;
		}
		if(!r){alert(msg);}
		

		function traverseASC(node){
			if(node == null){return;}
			node.canBegin = true;
			for(var i = 0; i < flow.lines.length; i++){
				var line = flow.lines[i];
				if(line.from == node && !line.to.canBegin){traverseASC(line.to);}
			}
		}
		function traverseDESC(node){
			if(node == null){return;}
			node.canEnd = true;
			for(var i = 0; i < flow.lines.length; i++){
				var line = flow.lines[i];
				if(line.to == node && !line.from.canEnd){traverseDESC(line.from);}
			}
		}
		traverseASC(map["start"]);
		traverseDESC(map["end"]);
		for(var i = 0; i < flow.tasks.length; i++){
			var t = flow.tasks[i];
			if(!t.canBegin){
				r = false;
				msg += "[" + t.alias + "]无法开始任务\n";
			}
		}
		for(var i = 0; i < flow.tasks.length; i++){
			var t = flow.tasks[i];
			if(!t.canEnd){
				r = false;
				msg += "[" + t.alias + "]无法结束任务\n";
			}
		}
		if(!r){alert(msg);}
		
		return r;
	};
}();
