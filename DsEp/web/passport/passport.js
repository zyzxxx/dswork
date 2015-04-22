if(top.location != this.location){top.location = this.location;}
function $(id){return document.getElementById(id);}
function init(){for(var i=0;i<m.length;i++){
	var o=$(m[i].id);
	o.d=m[i];
	o.s=$(o.d.s);
	o.onfocus=function(){this.s.innerHTML="&nbsp;";};
	o.onblur=function(){docheck(this);};
}}
function docheck(o){
	var r=false;
	if(o.d.f!=null){
		eval("r="+o.d.f+"()");
	}
	else{
		r=o.d.c.test(o.value);
	}
	if(!r){
		o.s.innerHTML=o.d.v;
	}
	return r;
}
function doclick(){var r=true;
	for(var i=0;i<m.length;i++){
		r = r&docheck($(m[i].id));
	}
	if(r){$('v').submit();}
}