﻿if(typeof($jskey)!="object"){$jskey={};}$jskey.$=function(id){return document.getElementById(id);};$jskey.$byName=function(name){return document.getElementsByName(name);};$jskey.$byTagName=function(name){return document.getElementsByTagName(name);};$jskey.$dateFormat=function(d,f){var t={"y+":d.getFullYear(),"M+":d.getMonth()+1,"d+":d.getDate(),"H+":d.getHours(),"m+":d.getMinutes(),"s+":d.getSeconds(),"S+":d.getMilliseconds()};var _t;for(var k in t){while(new RegExp("("+k+")").test(f)){_t=(RegExp.$1.length==1)? t[k]:("0000000000".substring(0,RegExp.$1.length)+t[k]).substr((""+t[k]).length);f=f.replace(RegExp.$1,_t+"");}}return f;};$jskey.$replace=function(str,t,u){str=str+"";var i=str.indexOf(t);var r="";while(i !=-1){r+=str.substring(0,i)+u;str=str.substring(i+t.length,str.length);i=str.indexOf(t);}r=r+str;return r;};$jskey.$src=$jskey.$replace($jskey.$byTagName("script")[$jskey.$byTagName("script").length-1].src+"","\\","/");$jskey.$show=function(msg){if((msg||"").length>0){alert(msg);}};$jskey.$path=$jskey.$src.substring(0,$jskey.$src.lastIndexOf("/")+1);$jskey.$url=function(){var _p=window.location.pathname;return _p.substring(0,_p.lastIndexOf("/"));};$jskey.$random=function(){return(new Date().getTime())+"";};
$jskey.checkbox={getSelected:function(name,msg){var _a=[];var _e=$jskey.$byName(name);for(var i=0;i<_e.length;i++){if(_e[i].checked){_a[_a.length]={"id":_e[i].getAttribute("id"),"value":_e[i].value};}}if(_a.length==0){$jskey.$show(msg);}return _a;},countSelected:function(name){return(this.getSelected(name)).length;},getSelectedBySeparator:function(name,sep){var _v="";var _a=this.getSelected(name);for(var i=0;i<_a.length;i++){_v+=((_v=="")? "":sep)+_a[i].value;}return _v;},getSelectedIdBySeparator:function(name,sep){var _v="";var _a=this.getSelected(name);for(var i=0;i<_a.length;i++){_v+=((_v=="")? "":sep)+_a[i].id;}return _v;},getSingleSelected:function(name,msg){var _o=null;var _e=$jskey.$byName(name);for(var i=0;i<_e.length;i++){if(_e[i].checked){if(_o==null){_o={"id":_e[i].getAttribute("id"),"value":_e[i].value};}else{$jskey.$show(msg);_o=null;break;}}}return _o;},isSelected:function(name){var _e=$jskey.$byName(name);for(var i=0;i<_e.length;i++){if(_e[i].checked){return true;}}return false;},reselect:function(name,chk){var _e=$jskey.$byName(name);if(chk==null){for(var i=0;i<_e.length;i++){_e[i].checked=!(_e[i].checked);}}else{chk=(chk)? true:false;for(var i=0;i<_e.length;i++){_e[i].checked=chk;}}}};
$jskey.radio={getSelected:function(name,msg){var _o=null;var _e=$jskey.$byName(name);for(var i=0;i<_e.length;i++){if(_e[i].checked){_o={"id":_e[i].getAttribute("id"),"value":_e[i].value};break;}}if(_o==null){$jskey.$show(msg);}return _o;},getId:function(name,defaultValue){var _a=this.getSelected(name);return(_a==null)?defaultValue:_a.id;},getValue:function(name,defaultValue){var _a=this.getSelected(name);return(_a==null)?defaultValue:_a.value;},reselect:function(name,value,isDisabled){var _c=false;var _e=$jskey.$byName(name);for(var i=0;i<_e.length;i++){if(_e[i].value==value){_e[i].disabled=false;_e[i].checked=true;_c=true;}else{_e[i].disabled=isDisabled||false;}}if(!_c){_e[0].disabled=false;_e[0].checked=true;}return _c;},reset:function(name,isDisabled){isDisabled=isDisabled ? true:false;var _e=$jskey.$byName(name);for(var i=0;i<_e.length;i++){_e[i].disabled=isDisabled;_e[i].checked=false;}}};
$jskey.select={$fnSwap:function(o1,o2){var _t=o1.value;o1.value=o2.value;o2.value=_t;_t=o1.text;o1.text=o2.text;o2.text=_t;_t=o1.selected;o1.selected=o2.selected;o2.selected=_t;},exist:function(id,v){var _o=$jskey.$(id);for(var i=0;i<_o.options.length;i++){if(v==_o.options[i].value){return true;}}return false;},add:function(id,t,v){var _o=$jskey.$(id);_o.options[_o.options.length]=new Option(t,v);},copy:function(fromID,toID,isSelected,isClear){var _o1=$jskey.$(fromID);var _o2=$jskey.$(toID);if(_o1.options.length>-1){if(isSelected){if(_o1.selectedIndex>-1){if(isClear){_o2.options.length=0;}for(var i=0;i<_o1.options.length;i++){if(_o1.options[i].selected&&!this.exist(toID,_o1.options[i].value)){_o2.options[_o2.options.length]=new Option(_o1.options[i].text,_o1.options[i].value);}}}}else{if(isClear){_o2.options.length=0;}for(var i=0;i<_o1.options.length;i++){_o2.options[_o2.options.length]=new Option(_o1.options[i].text,_o1.options[i].value);}}}},move:function(id,count,isWay){var _o=$jskey.$(id);var _a=_o.options;if(count==0){var _t=null;if(isWay){for(var i=0;i<_a.length;i++){if(_a[i].selected&&_t){_o.insertBefore(_a[i],_t);}else if(!_t&&!_a[i].selected){_t=_a[i];}}}else{for(var i=_a.length-1;i>-1;i--){if(_a[i].selected){if(_t){_t=_o.insertBefore(_a[i],_t);}else{_t=_o.appendChild(_a[i]);}}}}}else{if(isWay){for(var j=0;j<count;j++){for(var i=1;i<_a.length;i++){if(_a[i].selected&&!_a[i-1].selected){this.$fnSwap(_a[i],_a[i-1]);}}}}else{for(var j=0;j<count;j++){for(var i=_a.length-2;i>-1;i--){if(_a[i].selected&&!_a[i+1].selected){this.$fnSwap(_a[i],_a[i+1]);}}}}}},remove:function(id,isSelected){var _o=$jskey.$(id);if(isSelected){for(var i=_o.length-1;i>=0;i--){if(_o.options[i].selected){_o.remove(i);}}}else{_o.options.length=0;}},reselect:function(id,isSelected){var _o=$jskey.$(id);var _a=_o.options;if(isSelected==null){for(var i=0;i<_a.length;i++){_a[i].selected=!_a[i].selected;}}else{isSelected=(isSelected)? true:false;for(var i=0;i<_a.length;i++){_a[i].selected=isSelected;}}},swap:function(fromID,toID,isSelected){var _o1=$jskey.$(fromID);var _o2=$jskey.$(toID);if(_o1.options.length>-1){var _a=[];if(isSelected){if(_o1.selectedIndex>-1){for(var i=_o1.length-1;i>=0;i--){if(_o1.options[i].selected){_a[_a.length]=new Option(_o1.options[i].text,_o1.options[i].value);_o1.remove(i);}}for(var i=0;i<_a.length;i++){_o2.options[_o2.options.length]=_a[i];}}}else{for(var i=_o1.length-1;i>=0;i--){_a[_a.length]=new Option(_o1.options[i].text,_o1.options[i].value);_o1.remove(i);}for(var i=0;i<_a.length;i++){_o2.options[_o2.options.length]=_a[i];}}}}};
$jskey.dialog={returnValue:null,dialogArguments:null,close:function(){},callback:function(){},showDialog:function(){var o=arguments[0];if(typeof(o)!="object"){o={};}var defaults={"id":"dsworkDialog","title":"Window","url":"","width":400,"height":300,"args":{"data":{},"url":""},"iconCls":'icon_system',"maximizable":false,"minimizable":false,"collapsible":false,"resizable":true,"modal":true,"zIndex":9999};var opts=$.extend(defaults,o);if(opts.args&&opts.args.url){if(opts.args.url.indexOf("/")!=0&&opts.args.url.indexOf("http:")!=0&&opts.args.url.indexOf("https:")!=0&&o.url.indexOf("file:")!=0&&o.url.indexOf(":")!=1){opts.args.url=$jskey.$url()+"/"+opts.args.url;}}$jskey.dialog.dialogArguments={url:opts.url,args:opts.args};var $win=$('#'+opts.id);$jskey.dialog.returnValue=null;var url=opts.url+((opts.url.indexOf("?")==-1)? "?jskey=":"&jskey=")+$jskey.$random();if($win.length){$('iframe',$win)[0].contentWindow.location.href=url;$win.dialog('open');}else{$win=$('<div id="'+opts.id+'"></div>').appendTo('body');opts.onClose=function(){$jskey.dialog.dialogArguments=null;$win.dialog('destroy');$jskey.dialog.callback();};opts.content='<iframe scrolling="no" src="" width="100%" height="100%" style="width:100%;height:100%;border:0;" frameborder="0"></iframe>';$win.dialog(opts);$('iframe',$win)[0].contentWindow.location.href=url;}$jskey.dialog.close=function(){$win.dialog('close');};},showModalDialog:function(url,args,width,height,status,scroll,resizable){url+=((url.indexOf("?")==-1)? "?jskey=":"&jskey=")+$jskey.$random();return window.showModalDialog(url,args,("help:no;center:yes;dialogWidth:"+width+"px;dialogHeight:"+height+"px;status:"+status+";scroll:"+scroll+";resizable:"+resizable+";dialogTop:"+((window.screen.availHeight-height)/ 3)+";dialogLeft:"+((window.screen.availWidth-width)/ 2)));},show:function(){var o=arguments[0];if(typeof(o)!="object"){o={};}o.url=o.url||arguments[2]||"";if(o.url.indexOf("/")!=0&&o.url.indexOf("http:")!=0&&o.url.indexOf("https:")!=0&&o.url.indexOf("file:")!=0&&o.url.indexOf(":")!=1){o.url=$jskey.$url()+"/"+o.url;}o.height=o.height||arguments[5]||450;$jskey.dialog.returnValue=null;var reValue=this.showModalDialog($jskey.$path+"themes/dialog/jskey_dialog.html",{title:o.title||arguments[1]||"",url:o.url,args:o.args||arguments[3]||"",height:o.height},o.width||arguments[4]||600,o.height,o.status||arguments[7]||"no",o.scroll||arguments[8]||"auto",o.resizable||arguments[9]||"yes");if(o.reload||arguments[6]||false){window.location.reload();}else{var v=reValue;$jskey.dialog.returnValue=v;$jskey.dialog.callback();return v;}},openWindow:function(url,target,width,height,top,left,status,scroll,resizable,menubar,fullscreen,toolbar,location,directories,channelmode){url+=((url.indexOf("?")==-1)? "?jskey=":"&jskey=")+$jskey.$random();window.open(url,target,("width="+width+",height="+height+",top:"+top+",left:"+left+",status="+status+",scrollbars="+scroll+",resizable="+resizable+",menubar="+menubar+",fullscreen="+fullscreen+",toolbar="+toolbar+",location="+location+",directories="+directories+",channelmode="+channelmode));},open:function(){var o=arguments[0];if(typeof(o)!="object"){o={};}o.width=o.width||arguments[3]||600;o.height=o.height||arguments[4]||450;o.top=o.top||arguments[5]||((window.screen.availHeight-o.height)/ 3);o.left=o.left||arguments[6]||((window.screen.availWidth-o.width)/ 2);this.openWindow(o.url||arguments[1]||"",o.target||arguments[2]||"",o.width,o.height,o.top,o.left,o.status||arguments[7]||"no",o.scroll||arguments[8]||"yes",o.resizable||arguments[9]||"yes",o.menubar||arguments[10]||"no",o.fullscreen||arguments[11]||"no",o.toolbar||arguments[12]||"no",o.location||arguments[13]||"no",o.directories||arguments[14]||"no",o.channelmode||arguments[15]||"no");}};
$jskey.Map=function(){this.$v=[];};$jskey.Map.prototype={$fnChk:function(v){return(typeof(v)!="string")? "":v.replace(/(^\s*)|(\s*$)/g,"");},put:function(k,v){k=this.$fnChk(k);if(k.length>0){for(var i=0;i<this.$v.length;i++){if(k==this.$v[i][0]){this.$v[i][1]=v;return false;}}this.$v[this.$v.length]=[k,v];return true;}},putTry:function(k,v){k=this.$fnChk(k);if(k.length>0){for(var i=0;i<this.$v.length;i++){if(k==this.$v[i][0]){return false;}}this.$v[this.$v.length]=[k,v];return true;}return false;},remove:function(k){k=this.$fnChk(k);if(k.length>0){for(var i=0;i<this.$v.length;i++){if(k==this.$v[i][0]){this.$v.splice(i,1);break;}}}},get:function(k){k=this.$fnChk(k);if(k.length>0){for(var i=0;i<this.$v.length;i++){if(k==this.$v[i][0]){return this.$v[i][1];}}}return null;},getKeyArray:function(){var _a=[];for(var i=0;i<this.$v.length;i++){_a[_a.length]=this.$v[i][0];}return _a;},getValueArray:function(){var _a=[];for(var i=0;i<this.$v.length;i++){_a[_a.length]=this.$v[i][1];}return _a;},size:function(){return this.$v.length;},containsKey:function(k){k=this.$fnChk(k);if(k.length>0){for(var i=0;i<this.$v.length;i++){if(k==this.$v[i][0]){return true;}}}return false;}};
$jskey.validator={"Char":{"v":/^[A-Za-z0-9_]+$/,"msg":"允许英文字母、数字、下划线"},"Chinese":{"v":/^[\u4e00-\u9fa5]+$/,"msg":"只允许中文"},"Email":{"v":/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,"msg":"格式错误,如test@test.com"},"English":{"v":/^[A-Za-z]+$/,"msg":"只允许英文字母"},"Mobile":{"v":/^((0\d{2,3}\d{6,15})|(1[358]{1}\d{9}))$/,"msg":"请输入手机号码(纯数字)"},"Money":{"v":/^(([1-9](\d+)?)|0)(\.\d+)?$/,"msg":"请输入金额"},"Numeral":{"v":/^\d+$/,"msg":"请输入数字"},"Phone":{"v":/^((0\d{2,3})|(\(0\d{2,3}\)))?(-)?[1-9]\d{6,7}(([\-0-9]+)?[^\D]{1})?$/,"msg":"请输入电话号码"},"Require":{"v":/\S+/,"msg":"必填"},"RequireCompact":{"v":/^\S+$/,"msg":"必填(无空格)"},"RequireTrim":{"v":/(^[^\s]{1}(.+)?[^\s]{1}$)|(^[^\s]{1}$)/,"msg":"必填(无前后空格)"},"Url":{"v":/^http(s)?:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,"msg":"格式错误,如http:\/\/127.0.0.1:80"},"Zip":{"v":/^[1-9]\d{5}$/,"msg":"邮政编码不存在"},"Number":{"v":"this._Number(value)","msg":"请输入数值"},"NumberPlus":{"v":"this._NumberPlus(value)","msg":"请输入正数"},"NumberMinus":{"v":"this._NumberMinus(value)","msg":"请输入负数"},"Integer":{"v":"this._Integer(value)","msg":"请输入整数"},"IntegerPlus":{"v":"this._IntegerPlus(value)","msg":"请输入正整数"},"IntegerMinus":{"v":"this._IntegerMinus(value)","msg":"请输入负整数"},"Custom":{"v":"this._Custom(value,getAttribute('regexp'))","msg":""},"DateCheck":{"v":"this._DateCheck(value,getAttribute('operator'),$jskey.$(getAttribute('to')).value,getAttribute('require'))","msg":"日期不正确"},"Filename":{"v":"this._Filename(value)","msg":"文件名不能为空,且不能包含下列字符 \\ \/ \:\* \? \"<>"},"Filter":{"v":"this._Filter(value,getAttribute('accept'))","msg":""},"Function":{"v":"this._Function(value,getAttribute('fn'))","msg":""},"Group":{"v":"this._Group(getAttribute('name'),getAttribute('min'),getAttribute('max'))","msg":""},"IdCard":{"v":"this._IdCard(value)","msg":"只能输入18位的身份证号码"},"Limit":{"v":"this._Limit(value.length,getAttribute('min'),getAttribute('max'))","msg":""},"LimitB":{"v":"this._Limit(this._LenB(value),getAttribute('min'),getAttribute('max'))","msg":""},"Password":{"v":"this._Password(value)","msg":"密码不符合安全规则"},"Repeat":{"v":"value==$jskey.$(getAttribute('to')).value","msg":"重复输入不一致"},"Range":{"v":"this._Range(value,getAttribute('min'),getAttribute('max'))","msg":"请输入正确整数"},"UploadFile":{"v":"this._UploadFile(value)","msg":"请上传文件"},"UploadFileCheck":{"v":"this._UploadFileCheck(value)","msg":"有文件未上传,请上传或取消(清空上传内容)"},$ErrorObj:[],$ErrorMsg:["\u4ee5\u4e0b\u539f\u56e0\u5bfc\u81f4\u63d0\u4ea4\u5931\u8d25\uff1a\t\t\t\t"],$AlertMsg:["\u4ee5\u4e0b\u539f\u56e0\u5bfc\u81f4\u63d0\u4ea4\u5931\u8d25\uff1a\t\t\t\t"],$num:1,Validate:function(){var formID=arguments[0]||"";var mode=arguments[1]||"";var inputID=arguments[2]||"";var obj=null;if(formID==null||formID.length==0){obj={arr:[]};var _o=$jskey.$(inputID);var _dt=_o.getAttribute("dataType");if(typeof(_dt)=="object"||typeof(this[_dt])=="undefined"){}else{this.$Clear(_o);obj.arr.push(_o);}}else{obj=$jskey.$(formID)||event.srcElement;if(obj.arr !=null){for(var i=0;i<obj.arr.length;i++){with(obj.arr[i]){this.$Clear(obj.arr[i]);}}}obj.arr=[];var _t=obj.getElementsByTagName('*');var _dt;for(var i=0;i<_t.length;i++){if(_t[i].getAttribute){_dt=_t[i].getAttribute("dataType");if(typeof(_dt)=="object"||typeof(this[_dt])=="undefined"||this[_dt]==""){continue;}obj.arr.push(_t[i]);}}}var count=obj.arr.length;var errMsg="";var alertMsg="";this.$ErrorMsg.length=1;this.$AlertMsg.length=1;this.$ErrorObj.length=1;this.$ErrorObj[0]=obj;for(var i=0;i<count;i++){with(obj.arr[i]){var _dt=getAttribute("dataType");if(typeof(_dt)=="object"||typeof(this[_dt])=="undefined"){continue;}if(getAttribute("require")=="false"&&value==""){continue;}if(getAttribute("msg")==null){errMsg=this[_dt].msg;}else{errMsg=getAttribute("msg");}if(getAttribute("alertMsg")==null||typeof(getAttribute("alertMsg"))=="undefined"){alertMsg=errMsg;}else{alertMsg=getAttribute("alertMsg");}switch(_dt){case "Number":case "NumberPlus":case "NumberMinus":case "Integer":case "IntegerPlus":case "IntegerMinus":case "Custom":case "DateCheck":case "Filename":case "Filter":case "Function":case "Group":case "IdCard":case "Limit":case "LimitB":case "Password":case "Repeat":case "Range":case "UploadFile":case "UploadFileCheck":if(!eval(this[_dt].v)){this.$AddError(i,errMsg,alertMsg);}break;default:if(!this[_dt].v.test(value)){this.$AddError(i,errMsg,alertMsg);}break;}}}if(this.$ErrorMsg.length>1){mode=mode||1;var _c=this.$ErrorObj.length;switch(mode){case 2:for(var i=1;i<_c;i++){this.$ErrorObj[i].style.color="#ff0000";}case 1:alert(this.$AlertMsg.join("\n"));break;case 4:alert(this.$AlertMsg.join("\n"));case 3:for(var i=1;i<_c;i++){try{var _o=document.createElement("SPAN");this.$num++;_o.id="__ErrorMsgPanel"+this.$num;_o.style.color="#ff0000";this.$Clear(this.$ErrorObj[i]);this.$ErrorObj[i].JskeyValidator=_o.id;this.$ErrorObj[i].parentNode.appendChild(_o);_o.innerHTML=this.$ErrorMsg[i].replace(/\d+:/,"");}catch(e){alert(e.description);}}break;default:alert(this.$AlertMsg.join("\n"));break;}return false;}return true;},$Clear:function(o){try{with(o){if(style.color=="#ff0000"){style.color="";}parentNode.removeChild($jskey.$(JskeyValidator));JskeyValidator=null;}}catch(e){}},$AddError:function(index,emsg,amsg){this.$ErrorObj[this.$ErrorObj.length]=this.$ErrorObj[0].arr[index];this.$ErrorMsg[this.$ErrorMsg.length]=this.$ErrorMsg.length+":"+emsg;this.$AlertMsg[this.$AlertMsg.length]=this.$AlertMsg.length+":"+amsg;},_Number:function(v){if(!isNaN(v)){if(v.length==0||v.indexOf("+")!=-1){return false;}if(v.indexOf(".")==0||v.indexOf("-.")==0||v.indexOf("00")==0||v.indexOf("-00")==0||v.lastIndexOf(".")==v.length-1){return false;}return true;}return false;},_NumberPlus:function(v){if(this._Number(v)){if(v.indexOf("-")!=-1){return false;}return true;}return false;},_NumberMinus:function(v){if(this._Number(v)){if(v.indexOf("-")!=-1){return true;}}return false;},_Integer:function(v){if(this._Number(v)){if(v.indexOf(".")!=-1){return false;}return true;}return false;},_IntegerPlus:function(v){if(this._Integer(v)){if(v.indexOf("-")!=-1){return false;}return true;}return false;},_IntegerMinus:function(v){if(this._Integer(v)){if(v.indexOf("-")!=-1){return true;}}return false;},_Custom:function(op,reg){return new RegExp(reg,"g").test(op);},_DateCheck:function(op1,operator,op2,require){if(require=="false"&&op2.length==0){return true;}try{if(op1.length==0||op2.length==0){return false;}var d1=_$ToDate(op1);var d2=_$ToDate(op2);if(typeof(d1)!="object"||typeof(d2)!="object"){return false;}switch(operator){case "==":return(d1==d2);case "!=":return(d1 !=d2);case ">":return(d1>d2);case ">=":return(d1>=d2);case "<":return(d1<d2);case "<=":return(d1<=d2);default:return(d1>=d2);}}catch(e){}return false;function _$ToDate(op){try{var o,_y,_M,_d;o=op.match(new RegExp("^(\\d{4})([-./])(\\d{1,2})\\2(\\d{1,2})"));_d=o[4];_M=o[3]* 1;_y=o[1];if(!parseInt(_M)){return "";}_M=_M==0 ? 12:_M;return new Date(_y,_M-1,_d);}catch(ee){}return "";}},_Filename:function(v){if(v.length==0){return false;}if(v.indexOf("\\")==-1&&v.indexOf("\/")==-1&&v.indexOf("\:")==-1&&v.indexOf("\*")==-1&&v.indexOf("\?")==-1&&v.indexOf("\"")==-1&&v.indexOf("<")==-1&&v.indexOf(">")==-1&&v.indexOf(".")!=0&&v.lastIndexOf(".")!=(v.length-1)){return true;}return false;},_Filter:function(input,filter){return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g,filter.split(/\s*,\s*/).join("|")),"gi").test(input);},_Function:function(value,fn){var result=false;value=$jskey.$replace(value,"\"","\\\"");value=$jskey.$replace(value,"\r","");value=$jskey.$replace(value,"\n","");eval("result="+fn+"(\""+$jskey.$replace(value,"\"","\\\"")+"\")");return result;},_Group:function(name,min,max){var _g=document.getElementsByName(name);var chk=0;min=min||1;max=max||_g.length;for(var i=_g.length-1;i>=0;i--){if(_g[i].checked){chk++;}}return min<=chk&&chk<=max;},_IdCard:function(v){var _iSum=0;if(!(/^\d{17}([a-z\d\*]{1})$/i.test(v)||/^\d{15}$/i.test(v))){return false;}v=v.replace(/[a-z\*]{1}$/i,"a");if("11_12_13_14_15_21_22_23_31_32_33_34_35_36_37_41_42_43_44_45_46_50_51_52_53_54_61_62_63_64_65_71_81_82_91".indexOf(v.substr(0,2))==-1){return false;}if(v.length==15){v=v.substring(0,6)+"19"+v.substring(6,15);var _i=0;var _ti=[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];var _tc=['1','0','X','9','8','7','6','5','4','3','2'];for(var i=0;i<17;i++){_i+=v.substr(i,1)* _ti[i];}v+=_tc[_i % 11];}var _bd=v.substr(6,4)+"/"+v.substr(10,2)+"/"+v.substr(12,2);if(_bd !=$jskey.$dateFormat(new Date(_bd),"yyyy/MM/dd")){return false;}for(var i=17;i>=0;i--){_iSum+=(Math.pow(2,i)% 11)* parseInt(v.charAt(17-i),11);}if(_iSum%11 !=1){return false;}return true;},_Limit:function(len,min,max){min=min||0;max=max||Number.MAX_VALUE;return min<=len&&len<=max;},_LenB:function(v){return v.replace(/[^\x00-\xff]/g,"***").length;},_Password:function(v){return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(v));},_Range:function(v,min,max){min=min||(-1 * Number.MAX_VALUE);max=max||Number.MAX_VALUE;return this._Integer(v)&&parseInt(min)<=parseInt(v)&&parseInt(v)<=parseInt(max);},_UploadFile:function(v){if(v !=""){if(v=="0"){return false;}return true;}return false;},_UploadFileCheck:function(v){return v=="";},submit:function(containerid,submitid,type){if(this.Validate(containerid,3)){if(type==null||type !="select"){var _msg="是否确定提交?";if(type=="insert")_msg="是否确定提交?";else if(type=="update")_msg="是否确定保存?";if(!confirm(_msg)){return false;}}$jskey.$(submitid).click();return true;}return false;}};
if(typeof($jskey)!="object"){$jskey={};}$jskey.$=function(id){return document.getElementById(id);};$jskey.$byTagName=function(name){return document.getElementsByTagName(name);};$jskey.$replace=function(str,t,u){str=str+"";var i=str.indexOf(t);var r="";while(i !=-1){r+=str.substring(0,i)+u;str=str.substring(i+t.length,str.length);i=str.indexOf(t);}r=r+str;return r;};$jskey.$src=$jskey.$replace($jskey.$byTagName("script")[$jskey.$byTagName("script").length-1].src+"","\\","/");$jskey.$path=$jskey.$src.substring(0,$jskey.$src.lastIndexOf("/")+1);
if(true){
var _skin=[{name:"default"},{name:"gray"},{name:"lightGreen"}];
var _h=$jskey.$byTagName("head");for(var i=_skin.length-1;i>=0;i--){var _f=$jskey.$path+"themes/calendar/"+_skin[i].name+"/skin.css";var _k=document.createElement("link");_k.setAttribute("rel","stylesheet");_k.setAttribute("type","text/css");_k.setAttribute("skinType","JskeyCalendar");_k.setAttribute("disabled","true");if(i==0){_k.disabled=false;}_k.setAttribute("href",_f);_k.setAttribute("skin",_skin[i].name);_h[_h.length-1].appendChild(_k);}
}
$jskey.Calendar=function(){this.d=new Date();this.$p={"s":1,"m":2,"H":3,"d":4,"M":5,"y":6,"begin":1900,"end":this.d.getFullYear()+20,"cy":this.d.getFullYear(),"cM":this.d.getMonth(),"cd":this.d.getDate()};this.$s={};this.v="jskeyCalendar";this.$k={"div":this.v+"_div","panel":this.v+"_p","y":this.v+"_y","prevM":this.v+"Mp","M":this.v+"Mc","nextM":this.v+"Mn","table":this.v+"_T","H":this.v+"_H","m":this.v+"_m","s":this.v+"_s","bclear":this.v+"_r","btoday":this.v+"_d","bclose":this.v+"_e"};this.$c={"format":"yyyy-MM-dd","begin":this.$p.begin,"end":this.$p.end,"lang":0,"left":0,"top":0,"level":this.$p.d};this.$focus=false;this.$input=null;this.$div=null;this.$panel=null;this.$skin=[];this.$selTD=null;this.$lang={"y":["",""],"M":[["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"]],"w":[["日","一","二","三","四","五","六"],["SUN","MON","TUR","WED","THU","FRI","SAT"]],"t":[["时","分","秒"],["H","M","S"]],"b":[["今天","确定","清空","关闭"],["TODAY","OK","CLS","CLOSE"]]};};
$jskey.Calendar.prototype={$fnReset:function(d){this.$s.y=d.getFullYear();this.$s.M=d.getMonth();this.$s.d=d.getDate();this.$s.H=d.getHours();this.$s.m=d.getMinutes();this.$s.s=d.getSeconds();},$fnDate:function(){var o=this.$s;return new Date(o.y,o.M,o.d,o.H,o.m,o.s);},$fnGetWeek:function(d){var _co=new Date(d.getFullYear(),0,1);var cDay=parseInt(Math.abs(d-_co)/ 86400000)+1;var _w=(cDay+_co.getDay())/ 7;var _wInt=parseInt(_w);return((_w>_wInt)?(_wInt+1):_wInt);},$fnFormat:function(d,f){var t={"y+":d.getFullYear(),"M+":d.getMonth()+1,"d+":d.getDate(),"H+":d.getHours(),"m+":d.getMinutes(),"s+":d.getSeconds(),"S+":d.getMilliseconds(),"W+":this.$fnGetWeek(d),"w+":this.$lang.w[this.$c.lang][d.getDay()]};var _t;for(var k in t){while(new RegExp("("+k+")").test(f)){_t=(RegExp.$1.length==1)? t[k]:("0000000000".substring(0,RegExp.$1.length)+t[k]).substr((""+t[k]).length);f=f.replace(RegExp.$1,_t+"");}}return f;},$fnInit:function(){var cid=this.$k.div;if($jskey.$(cid)==null){var s='<div id="'+this.$k.panel+'" style="position:absolute;display:none;z-index:9999;" class="CalendarPanel"></div>';if(document.all){s+='<iframe style="position:absolute;z-index:8888;width:expression(this.previousSibling.offsetWidth);';s+='height:expression(this.previousSibling.offsetHeight);';s+='left:expression(this.previousSibling.offsetLeft);top:expression(this.previousSibling.offsetTop);';s+='display:expression(this.previousSibling.style.display);" scrolling="no" frameborder="no"></iframe>';}var o=document.createElement("div");o.innerHTML=s;o.id=cid;o.style.display="none";document.body.appendChild(o);this.$panel=$jskey.$(this.$k.panel);this.$div=$jskey.$(cid);}},$fnReturn:function(dt){if(this.$input !=null){this.$input.value=dt;}this.$hide();if(this.$input.onchange==null){if(typeof(this.$input.changeEvent)=='function'){this.$input.changeEvent();}return;}var ev=this.$input.onchange.toString();ev=ev.substring(((ev.indexOf("ValidatorOnChange();")>0)? ev.indexOf("ValidatorOnChange();")+20:ev.indexOf("{")+1),ev.lastIndexOf("}"));var fun=new Function(ev);this.$input.changeEvent=fun;this.$input.changeEvent();},$fnDraw:function(){var e=this;var a=[];a.push('<div style="margin:0px;">');a.push('<table width="100%" cellpadding="0" cellspacing="1" class="CalendarTop">');a.push('<tr class="title">');a.push('<th align="left" class="prevMonth"><input style="');if(e.$c.level>e.$p.M){a.push('display:none;');}a.push('" id="'+this.$k.prevM+'" type="button" value="&lt;" /></th>');a.push('<th align="center" width="98%" nowrap="nowrap" class="YearMonth">');a.push('<select id="'+this.$k.y+'" class="Year"></select>');a.push('<select id="'+this.$k.M+'" class="Month" style="');if(e.$c.level>e.$p.M){a.push('display:none;');}a.push('"></select></th>');a.push('<th align="right" class="nextMonth"><input style="');if(e.$c.level>e.$p.M){a.push('display:none;');}a.push('" id="'+this.$k.nextM+'" type="button" value="&gt;" /></th>');a.push('</tr>');a.push('</table>');a.push('<table id="'+this.$k.table+'" width="100%" class="CalendarDate" style="');if(e.$c.level>=e.$p.M){a.push('display:none;');}a.push('" cellpadding="0" cellspacing="1">');a.push('<tr class="title">');for(var i=0;i<7;i++){a.push('<th>'+e.$lang.w[e.$c.lang][i]+'</th>');}a.push('</tr>');for(var i=0;i<6;i++){a.push('<tr align="center" class="date" style="display:;">');for(var j=0;j<7;j++){if(j==0){a.push('<td class="sun" tdname="tdSun"></td>');}else if(j==6){a.push('<td class="sat" tdname="tdSat"></td>');}else{a.push('<td class="day" tdname="tdDay"></td>');}}a.push('</tr>');}a.push('</table>');a.push('<table width="100%" class="CalendarTime" style="');if(e.$c.level>e.$p.H){a.push('display:none;');}a.push('" cellpadding="0" cellspacing="1">');a.push('<tr><td align="center" colspan="7">');a.push('<select id="'+this.$k.H+'" class="Hour"></select>'+e.$lang.t[e.$c.lang][0]);a.push('<span style="');if(e.$c.level>e.$p.m){a.push('display:none;');}a.push('"><select id="'+this.$k.m+'" class="Minute"></select>'+e.$lang.t[e.$c.lang][1]+'</span>');a.push('<span style="');if(e.$c.level>e.$p.s){a.push('display:none;');}a.push('"><select id="'+this.$k.s+'" class="Second"></select>'+e.$lang.t[e.$c.lang][2]+'</span>');a.push('</td></tr>');a.push('</table>');a.push('<div align="center" class="CalendarButtonDiv">');a.push('<input id="'+this.$k.bclear+'" type="button" value="'+e.$lang.b[e.$c.lang][2]+'"/>');a.push('<input id="'+this.$k.btoday+'" type="button" value="');a.push((e.$c.level==e.$p.d)? e.$lang.b[e.$c.lang][0]:e.$lang.b[e.$c.lang][1]);a.push('" />');a.push('<input id="'+this.$k.bclose+'" type="button" value="'+e.$lang.b[e.$c.lang][3]+'" />');a.push('</div>');a.push('</div>');e.$panel.innerHTML=a.join("");var o=$jskey.$(this.$k.prevM);o.onclick=function(){e.goPrevMonth(e);};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.nextM);o.onclick=function(){e.goNextMonth(e);};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.bclear);o.onclick=function(){e.$fnReturn("");};o=$jskey.$(this.$k.bclose);o.onclick=function(){e.$hide();};o=$jskey.$(this.$k.y);o.onclick=function(){e.$s.y=parseInt(this.options[this.selectedIndex].value);e.$bindData();};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.M);o.onclick=function(){e.$s.M=parseInt(this.options[this.selectedIndex].value);e.$bindData();};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.H);o.onclick=function(){e.$s.H=parseInt(this.options[this.selectedIndex].value);};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.m);o.onclick=function(){e.$s.m=parseInt(this.options[this.selectedIndex].value);};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.s);o.onclick=function(){e.$s.s=parseInt(this.options[this.selectedIndex].value);};o.onblur=function(){e.$fnBlur();};o=$jskey.$(this.$k.btoday);o.onclick=function(){var _t=(e.$c.level !=e.$p.d)?(e.$fnDate()):(new Date());e.$fnReturn(e.$fnFormat(_t,e.$c.format));};},$bindSelect:function(){var o,_t,i;o=$jskey.$(this.$k.y);o.length=0;for(i=this.$c.begin;i<=this.$c.end;i++){o.options[o.length]=new Option(i+this.$lang.y[this.$c.lang],i);}o=$jskey.$(this.$k.M);o.length=0;for(i=0;i<12;i++){o.options[o.length]=new Option(this.$lang.M[this.$c.lang][i],i);}o=$jskey.$(this.$k.H);if(o.length==0){for(i=0;i<24;i++){_t=("00"+i+"").substr((""+i).length);o.options[o.length]=new Option(_t,_t);}}o=$jskey.$(this.$k.m);if(o.length==0){for(i=0;i<60;i++){_t=("00"+i+"").substr((""+i).length);o.options[o.length]=new Option(_t,_t);}}o=$jskey.$(this.$k.s);if(o.length==0){for(i=0;i<60;i++){_t=("00"+i+"").substr((""+i).length);o.options[o.length]=new Option(_t,_t);}}},goPrevMonth:function(e){if(e.$s.y==e.$c.begin&&e.$s.M==0){return;}e.$s.M--;if(e.$s.M==-1){e.$s.y--;e.$s.M=11;}e.$bindData();},goNextMonth:function(e){if(e.$s.y==e.$c.end&&e.$s.M==11){return;}e.$s.M++;if(e.$s.M==12){e.$s.y++;e.$s.M=0;}e.$bindData();},$fnGetViewArray:function(y,m){var a=[];var firstDay=new Date(y,m,1).getDay();var days=new Date(y,m+1,0).getDate();for(var i=0;i<42;i++){a[i]="&nbsp;";}for(var i=0;i<days;i++){a[i+firstDay]=i+1;}if(this.$s.d>days){this.$s.d=days;}return a;},$bindData:function(){var e=this;$jskey.$(e.$k.y)[(e.$s.y-e.$c.begin<0||e.$s.y-e.$c.begin>=$jskey.$(e.$k.y).length)?0:(e.$s.y-e.$c.begin)].selected=true;if(e.$c.level>=e.$p.y){return;}$jskey.$(e.$k.M)[e.$s.M].selected=true;$jskey.$(e.$k.H)[e.$s.H].selected=true;$jskey.$(e.$k.m)[e.$s.m].selected=true;$jskey.$(e.$k.s)[e.$s.s].selected=true;var dateArray=e.$fnGetViewArray(parseInt(e.$s.y),parseInt(e.$s.M));var tds=$jskey.$(this.$k.table).getElementsByTagName("td");for(var i=0;i<tds.length;i++){tds[i].onmouseover=function(){return;};tds[i].onmouseout=function(){return;};tds[i].innerHTML=dateArray[i];if(tds[i].getAttribute("tdname")=="tdSun"){tds[i].className="sun";}else if(tds[i].getAttribute("tdname")=="tdSat"){tds[i].className="sat";}else{tds[i].className="day";}if(dateArray[i]=="&nbsp;"){tds[i].onclick=function(){return;};}else{tds[i].isToday=false;if(e.$p.cy==e.$s.y&&e.$p.cM==e.$s.M&&e.$p.cd==dateArray[i]){tds[i].className="today";tds[i].isToday=true;}if(e.$s.d==dateArray[i]){tds[i].className="selDay";e.$selTD=tds[i];}if(e.$c.level==e.$p.d){tds[i].onclick=function(){e.$s.d=this.innerHTML;e.$fnReturn(e.$fnFormat(e.$fnDate(),e.$c.format));};}else{tds[i].onclick=function(){if(e.$selTD !=null){var t="day";if(e.$selTD.isToday){t="today";}else{var s=e.$selTD.getAttribute("tdname");if(s=="tdSun"){t="sun";}else if(s=="tdSat"){t="sat";}}e.$selTD.className=t;}this.className="selDay";e.$s.d=this.innerHTML;e.$selTD=this;};}tds[i].onmouseover=function(){this.className="dayOver";};tds[i].onmouseout=function(){var t="day";if(e.$selTD !=this){if(this.isToday){t="today";}else{var s=this.getAttribute("tdname");if(s=="tdSun"){t="sun";}else if(s=="tdSat"){t="sat";}}}else{t="selDay";}this.className=t;};tds[i].onblur=function(){e.$fnBlur();};}}},$fnPoint:function(e){var x=e.offsetLeft;var y=e.offsetTop;while(e=e.offsetParent){x+=e.offsetLeft;y+=e.offsetTop;}var sy=0;try{sy=event.clientY;}catch(ee){sy=0;}var a={"sx":x,"sy":sy,"x":x,"y":y,"w":$jskey.$(this.$k.panel).offsetWidth,"h":$jskey.$(this.$k.panel).offsetHeight};return a;},$fnSkin:function(p){var hasSkin=false;if(p.skin==null)p.skin="default";if(this.$skin.length==0){var linkList=$jskey.$byTagName("link");for(var i=0;i<linkList.length;i++){if(linkList[i].getAttribute("skinType")=="JskeyCalendar"){this.$skin[this.$skin.length]=linkList[i];}}}for(var i=0;i<this.$skin.length;i++){if(this.$skin[i].getAttribute("skin")==p.skin){this.$skin[i].disabled=false;hasSkin=true;}else{this.$skin[i].disabled=true;}}if(!hasSkin){for(var i=0;i<this.$skin.length;i++){if(this.$skin[i].getAttribute("skin")=="default"){this.$skin[i].disabled=false;break;}}}},$fnGetLevel:function(s){var t=this.$p;if(s.indexOf('ss')>-1){return t.s;}else if(s.indexOf('mm')>-1){return t.m;}else if(s.indexOf('HH')>-1){return t.H;}else if(s.indexOf('dd')>-1){return t.d;}else if(s.indexOf('MM')>-1){return t.M;}else if(s.indexOf('yyyy')>-1){return t.y;}return t.d;},$fnIsChange:function(p){var b=false,t=this.$c;if(t.begin !=p.beginYear||t.end !=p.endYear||t.lang !=p.lang||t.level !=p.level||t.format !=p.format){b=true;}this.$c.left=p.left;this.$c.top=p.top;if(b){t.begin=p.beginYear;t.end=p.endYear;t.lang=p.lang;t.format=p.format;t.level=p.level;}return b;},showCalendar:function(obj,p){var t;this.$fnInit();if(obj==null){throw new Error("arguments[0]is necessary");}if(p.beginYear==null){p.beginYear=this.$p.begin;}if(p.endYear==null){p.endYear=this.$p.end;}if(p.lang==null){p.lang=0;}if(p.left==null){p.left=0;}if(p.top==null){p.top=0;}if(p.format==null){p.format="yyyy-MM-dd";}p.level=this.$fnGetLevel(p.sample||p.show||"yyyy-MM-dd");t=new Date();try{var f=p.format,v=obj.value;if(v.length>=f.length){var y=1000,M=1,d=1,H=1,m=1,s=1,_iy=f.indexOf('yyyy'),_iM=f.indexOf('MM'),_id=f.indexOf('dd'),_iH=f.indexOf('HH'),_im=f.indexOf('mm'),_is=f.indexOf('ss');if(_iy !=-1){y=v.substring(_iy,_iy+4);};if(!isNaN(y)&&y>1000){if(_iM !=-1){M=v.substring(_iM,_iM+2);if(isNaN(M)){M=t.getMonth()+1;}}if(_id !=-1){d=v.substring(_id,_id+2);if(isNaN(d)){d=t.getDate();}}if(_iH !=-1){H=v.substring(_iH,_iH+2);if(isNaN(H)){H=t.getHours();}}if(_im !=-1){m=v.substring(_im,_im+2);if(isNaN(m)){m=t.getMinutes();}}if(_is !=-1){s=v.substring(_is,_is+2);if(isNaN(s)){s=t.getSeconds();}}eval("t=new Date('"+y+"','"+(M-1)+"','"+d+"','"+H+"','"+m+"','"+s+"')");}}}catch(e){t=new Date();}this.$fnReset(t);this.$fnSkin(p);var isChange=this.$fnIsChange(p);this.$input=obj;if(this.$panel.innerHTML==""||isChange){this.$fnDraw();this.$bindSelect();}this.$bindData();this.$panel.style.display="";this.$div.style.display="";t=this.$fnPoint(obj);this.$panel.style.left=(t.x+this.$c.left)+"px";this.$panel.style.top=((this.$c.top==0)?((t.sy>340)?(t.y-t.h):(t.y+obj.offsetHeight)):(t.y+obj.offsetHeight+this.$c.top))+"px";var e=this;if(!e.$input.isTransEvent){e.$input.isTransEvent=true;if(e.$input.onblur !=null){e.$input.blurEvent=e.$input.onblur;}e.$input.onblur=function(){e.$fnBlur();if(typeof(this.blurEvent)=='function'){this.blurEvent();}};}e.$div.onmouseover=function(){e.$focus=true;};e.$div.onmouseout=function(){e.$focus=false;};},$hide:function(){this.$panel.style.display="none";this.$div.style.display="none";this.$focus=false;},$fnBlur:function(){if(!(this.$focus)){this.$hide();}},show:function(a0,a1){if(a1.object !=null){this.showCalendar(a1.object,a1);return true;}if(a1.id !=null){a1.id=a1.id+"";var obj=$jskey.$(a1.id);if(obj !=null){this.showCalendar(obj,a1);return true;}}if(typeof(a0)=='object'){this.showCalendar(a0,a1);return true;}else if(typeof(a0)=='string'){var obj=$jskey.$(a0);if(obj==null){return false;}this.showCalendar(obj,a1);return true;}return false;}};$jskey.calendar=new $jskey.Calendar();
