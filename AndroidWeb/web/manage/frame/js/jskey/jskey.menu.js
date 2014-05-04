if(typeof ($jskey) != "object")
{
	$jskey = {};
}
$jskey.menu =
{
list:[],
data: {},// 此处data是数组
hidden:true,//是否允许将所有菜单隐藏起来
root:"",
path:"",
imgPath:"",
$:function(id)
{
	return document.getElementById(id);
},
//初始化操作
reset:function()
{
	this.list = [];
},
//添加一级菜单下的一组子菜单的菜单项
put:function(title, menuId, html)
{
	this.list[this.list.length] = {"title":title, "menuId":menuId, "html":html};//OutLook菜单单元对象
	// menuId没用,可在show的时候循环list判断menuId打开指定的group
},
//创建一层菜单内容
createCell:function(obj, i)
{
	var html = '<tr>';
	html += '<td id="JskeyMenuT_' + i + '" class="menuClose" onclick="$jskey.menu.clickBar(' + i + ')"' + ((i == this.list.length)?' style="display:none;"':'') + '>';
	html += '<img id="JskeyImg' + 'JskeyMenuT_' + i + '" align="absmiddle" style="border:0;" src="' + this.imgPath + 'right.gif" /> ';
	html += obj.title + '</td>';
	html += '</tr>';
	html += '<tr>';
	html += '<td id="JskeyMenuC_' + i + '" valign="top" class="cellClose" style="' + ((i == this.list.length)?'display:;height:100%':'display:none;height:') + '">';
	html += '<div class="cellContent">'
	html += ((obj.html != "")?obj.html:'&nbsp;');
	html += '</div></td></tr>';
	return html;
},
//创建一层菜单内容
create:function()
{
	if(this.list.length < 1)
	{
		return;
	}
	var len = this.list.length;
	var html = '<table id="JskeyMenuG" class="jskeymenu" cellspacing="0" cellpadding="0">';
	for(var i = 0;i < len;i++)
	{
		html += this.createCell(this.list[i], i);
	}
	html += this.createCell({"title":"", "menuId":"", "html":"&nbsp;"}, len);// 在最后补一个看不见的，用于收缩
	html += '</table>';
	try
	{
		if(this.$("JskeyMenuG") != null)
		{
			document.body.removeChild(this.$("JskeyMenuG"));// 清空
		}
	}
	catch(e)
	{
	}
	document.body.innerHTML += html;
},


//滑动菜单
smoothMenu:function(itemOpen, itemClose, stat)
{
	var timeDelay = 10;
	var moveHight = 20;
	if(stat == 0)
	{
		this.$("JskeyMenuC_" + itemOpen).style.display = "";
		setTimeout("$jskey.menu.smoothMenu(" + itemOpen + "," + itemClose + "," + moveHight + ")", timeDelay);
	}
	else
	{
		if(stat > 100)
		{
			stat = 100;
		}
		this.$("JskeyMenuC_" + itemOpen).style.height = stat + "%";
		this.$("JskeyMenuC_" + itemClose).style.height = (100 - stat) + "%";
		if(stat < 100)
		{
			stat += moveHight;
			setTimeout("$jskey.menu.smoothMenu(" + itemOpen + "," + itemClose + "," + stat + ")", timeDelay);
		}
		else
		{
			this.$("JskeyMenuC_" + itemClose).style.display = "none";
		}
	}
},


//判断点了对应的菜单块
clickBar:function(item)
{
	if(item >= 0)
	{
		var hasAnyOpen = false;
		var isClose = this.$("JskeyMenuC_" + item).style.display == "";
		if(isClose)// 关掉
		{
			this.$("JskeyMenuT_" + item).className = "menuClose";
			this.$("JskeyMenuC_" + item).className = "cellClose";
			this.$("JskeyImg" + "JskeyMenuT_" + item).src = this.imgPath + "right.gif";
			if(this.hidden)
			{
				this.$("JskeyMenuC_" + this.list.length).style.display = "";
				this.smoothMenu(this.list.length, item, 0);// 需要将最后一个展开
			}
			else
			{
				this.$("JskeyMenuC_" + item).style.display = "none";
			}
		}
		else// 打开
		{
			this.$("JskeyMenuT_" + item).className = "menuOpen";
			this.$("JskeyMenuC_" + item).className = "cellOpen";
			this.$("JskeyImg" + "JskeyMenuT_" + item).src = this.imgPath + "down.gif";
			if(this.hidden)
			{
				for(var i = 0;i < this.list.length;i++)
				{
					if(i != item && this.$("JskeyMenuC_" + i).style.display == "")// 找到有其他被打开的就直接关掉并返回
					{
						this.$("JskeyMenuT_" + i).className = "menuClose";
						this.$("JskeyMenuC_" + i).className = "cellClose";
						this.$("JskeyImg" + "JskeyMenuT_" + i).src = this.imgPath + "right.gif";
						this.smoothMenu(item, i, 0);
						return;
					}
				}
				this.smoothMenu(item, this.list.length, 0);// 找不到被打开的，就关掉最后一个
			}
			else
			{
				this.$("JskeyMenuC_" + item).style.display = "";
			}
		}
	}
},
//鼠标移过
imgMouse:function(obj, id, imgpath, over)
{
	if(over)
	{
		obj.className = "imgdiv overfont";
		this.$("JskeyImg" + id).src = imgpath;
		this.$("JskeyImg" + id).className = "imgover";
	}
	else
	{
		obj.className = "imgdiv outfont";
		this.$("JskeyImg" + id).src = imgpath;
		this.$("JskeyImg" + id).className = "imgout";
	}
},
//树父级节点
expandNode:function(imgid, objid, img, imgOpen)
{
	if(this.$(objid).style.display == 'none')
	{
		this.$(objid).style.display = '';
		this.$(imgid).src = this.imgPath + "jian.gif";
		this.$(imgid + "_node").src = imgOpen;
	}
	else
	{
		this.$(objid).style.display = 'none';
		this.$(imgid).src = this.imgPath + "jia.gif";
		this.$(imgid + "_node").src = img;
	}
},
//取得一个菜单单元HTML代码
getCellHTML:function(obj, pnodeName)
{
	var html = "";
	if(obj.items.length == 0)
	{
		var _img = this.path + "/" + ((obj.img == null || obj.img == "") ? "setting.gif" : obj.img);
		var _imgOpen = this.path + "/" + ((obj.imgOpen == null || obj.imgOpen == "") ? "setting.gif" : obj.imgOpen);
		html += "<div style=\"text-align:center;margin:auto;\">";
		html += "<div class=\"imgdiv outfont\" onmouseover=\"$jskey.menu.imgMouse(this,'" + obj.id + "','" + _imgOpen + "',true)\"";
		html += "\tonmouseout=\"$jskey.menu.imgMouse(this,'" + obj.id + "', '" + _img + "',false)\"";
		html += " onclick=\"$jskey.menu.changeURL('" + pnodeName + "','" + obj.name + "','" + obj.url + "')\">";
		html += "<img id=\"JskeyImg" + obj.id + "\"  class=\"imgOut\" src=\"" + _img + "\"/>";
		html += obj.name + "</div></div>";//"<br/>" + 
	}
	return html;
},
//icoString:0和1的字符串
getTreeHtml:function(obj, pnodeName, icoString)
{
	var html = "";
	var len = icoString.length;
	var items = obj.items;
	if(icoString.length == 1 && items.length == 0)
	{
		var _timg = this.path + "/" + ((obj.img == null || obj.img == "") ? "default.gif" : obj.img);
		html += "<div class='treenode' onclick=\"$jskey.menu.changeURL('" + pnodeName + "','" + obj.name + "','" + obj.url + "')\">";
		html += "<img style='border:0px;' align='absmiddle' src='" + this.imgPath + ((icoString.charAt(len - 1) == '1') ? "L" : "T") + ".gif' />";
		html += "<img class='img' align='absmiddle' src='" + _timg + "' />";
		html += "<div class='treenodeout' onmouseover='this.className = \"treenodeover\"' onmouseout='this.className = \"treenodeout\"'>" + obj.name + "</div>";
		html += "</div>";
	}
	else
	{
		var tempHTML = "";//保存子节点HTML
		// 父节点的图标是否由json来决定
		var _img = ((obj.img == null || obj.img == "")?(this.imgPath + "close.gif"):(this.path + obj.img));
		var _imgOpen = ((obj.imgOpen == null || obj.imgOpen == "")?(this.imgPath + "open.gif"):(this.path + obj.imgOpen));
		html += "<div class='treenode'" + ">";
		// 处理上级节点
		for(var c = 0;c < len - 1;c++)
		{
			html += "<img style='border:0px;' align='absmiddle' src='" + this.imgPath + ((icoString.charAt(c) == '0') ? "I" : "N") + ".gif' />";
		}
		html += "<img id='JskeyImg" + obj.id + "' align='absmiddle' src='" + this.imgPath + "jia.gif' onclick='$jskey.menu.expandNode(\"JskeyImg" + obj.id + "\", \"DIV" + obj.id + "\", \"" + _img + "\", \"" + _imgOpen + "\");' />";
		html += "<img id='JskeyImg" + obj.id + "_node' class='img' align='absmiddle' src='" + _img + "' onclick='$jskey.menu.expandNode(\"JskeyImg" + obj.id + "\", \"DIV" + obj.id + "\", \"" + _img + "\", \"" + _imgOpen + "\");'/>";
		html += "<div class='treedivout' onmouseover='this.className = \"treedivover\"' onmouseout='this.className = \"treedivout\"' onclick='$jskey.menu.expandNode(\"JskeyImg" + obj.id + "\", \"DIV" + obj.id + "\", \"" + _img + "\", \"" + _imgOpen + "\");'>" + obj.name + "</div>";
		html += "</div>";
		html += "<div class='treenodes' id='DIV" + obj.id + "' style='display:none;'>";
		var lastIndex = items.length - 1;
		var item;
		for(var i = 0;i < items.length;i++)
		{
			item = items[i];
			if(item.items.length == 0)
			{
				var _timg = this.path + "/" + ((item.img == null || item.img == "") ? "default.gif" : item.img);
				tempHTML += "<div class='treenode' onclick=\"$jskey.menu.changeURL('" + pnodeName + "','" + item.name + "','" + item.url + "')\">";
				for(var c = 0;c < len;c++)
				{
					tempHTML += "<img style='border:0px;' align='absmiddle' src='" + this.imgPath + ((icoString.charAt(c) == '0') ? "I" : "N") + ".gif' />";
				}
				tempHTML += "<img style='border:0px;' align='absmiddle' src='" + this.imgPath + ((i == lastIndex) ? "L" : "T") + ".gif' />";
				tempHTML += "<img id='JskeyImg" + item.id + "_node' class='img' align='absmiddle' src='" + _timg + "'/>";
				tempHTML += "<div class='treenodeout' onmouseover='this.className = \"treenodeover\"' onmouseout='this.className = \"treenodeout\"'>" + item.name + "</div>";
				tempHTML += "</div>";
			}
			else
			{
				var icoStr = icoString + ((i == lastIndex) ? "1" : "0");
				tempHTML += this.getTreeHtml(item, pnodeName, icoStr);//这里的pnodeName不变成name
			}
		}
		html += tempHTML;
		html += "</div>";
	}
	return html;
},
//获得OutLook菜单子菜单中的html代码
getNodeHtml:function(obj)
{
	var html = "";
	var items = obj.items;
	var childrenHTML = "";//保存功能节点HTML
	var item;
	var isTree = false;
	for(var i = 0;i < items.length;i++)
	{
		//子节点，无下级节点
		if(items[i].items.length > 0)
		{
			isTree = true;
			break;
		}
	}
	if(isTree)
	{
		for(var i = 0;i < items.length;i++)
		{
			item = items[i];
			childrenHTML += this.getTreeHtml(item, obj.name, ((i == items.length-1) ? "1" : "0"));
			// 增加此功能树节点
			html += childrenHTML;
			childrenHTML = "";// 清空
		}
	}
	else
	{
		for(var i = 0;i < items.length;i++)
		{
			item = items[i];
			childrenHTML += this.getCellHTML(item, obj.name);
			//增加此功能节点
			html += childrenHTML;
			childrenHTML = "";
		}
	}
	return html;
}

};

$jskey.menu.Map = function()
{
	this.data = [];
};
$jskey.menu.Map.prototype =
{
	//把对象放进map中
	put:function(key, value)
	{
		if(key.length > 0)
		{
			for(var i = 0; i < this.data.length; i++)
			{
				//存在
				if(key == this.data[i][0])
				{
					this.data[i][1] = value;//更新
					return;//找到
				}
			}
			//经过for循环后没找到
			this.data[this.data.length] = [key, value];//新增
		}
	},
	//从map中取得指定的对象
	get:function(key)
	{
		if(key.length > 0)
		{
			for(var i = 0; i < this.data.length; i++)
			{
				//存在
				if(key == this.data[i][0])
				{
					return this.data[i][1];
				}
			}
		}
		return null;
	}
};

$jskey.menu.format = function(data)
{
	try
	{
		var m = new $jskey.menu.Map();
		var root = [];
		for(var i = 0; i < data.length; i++)
		{
			data[i].img = (data[i].img == null)?"":data[i].img;
			data[i].imgOpen = (data[i].imgOpen == null)?"":data[i].imgOpen;
			data[i].items = [];
			m.put(data[i].id + "", data[i]);// 把obj放入map
		}
		var po;
		for(var i = 0; i < data.length; i++)
		{
			if(data[i].pid != "0")
			{
				po = m.get(data[i].pid + "");
				if(po != null)
				{
					var len = po.items.length;
					po.items[len] = data[i];// 把子节点放入父节点的items数组中
				}
			}
			else
			{
				root[root.length] = data[i];
			}
		}
		return root;
	}
	catch(e)
	{
		alert(e.message);
		return [];
	}
};

$jskey.menu.jsSrc = "" + document.getElementsByTagName("script")[document.getElementsByTagName("script").length - 1].src;
//当前js的引用路径
$jskey.menu.jsPath = $jskey.menu.jsSrc.substring(0, $jskey.menu.jsSrc.lastIndexOf("/"));

$jskey.menu.show = function(items, isHidden)
{
	$jskey.menu.data = items;
	$jskey.menu.hidden = (isHidden) ? true : false;
	if($jskey.menu.path == "") $jskey.menu.path = $jskey.menu.jsPath + "/themes/menu/ico/";
	if($jskey.menu.imgPath == "") $jskey.menu.imgPath = $jskey.menu.jsPath + "/themes/menu/img/";
	$jskey.menu.reset();
	for( var i = 0;i < items.length;i++)
	{
		var item = items[i];
		var html = $jskey.menu.getNodeHtml(item);
		//alert(html);
		$jskey.menu.put(item.name, item.id, html);
	}
	$jskey.menu.create();
};
//改变URL，可以覆盖此方法，用于自定义不同的方式
$jskey.menu.changeURL = function(parentname, nodename, url)
{
	if(url == null || url == "" || url == "null")
	{
		url = "";
	}
	if($jskey.menu.root != "")
	{
		if(url.indexOf("http:") != -1 || url.indexOf($jskey.menu.root) != 0)
		{
			url = $jskey.menu.root + url;
		}
	}
	if(url != "")
	{
		try
		{
			var mainFrame = parent.frames["rightFrame"];
			parent.navigation.reset(parentname);
			parent.document.getElementById("rightFrame").src = url;
		}
		catch(e)
		{
		}
	}
};
