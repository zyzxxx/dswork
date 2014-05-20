var Navigation = function(showObjName)
{
	this.objName = showObjName;
	this.currentIndex = 0;
	this.urlList = [];
};
Navigation.prototype =
{
	//清除
	reset:function(name)
	{
		//alert(name);
		this.urlList = [];
		this.urlList[0] = {name:name,url:null};
		this.currentIndex = 1;
	},
	//加入一个链接
	addUrl:function(name, url)
	{
		if(name == null || name == "")
		{
			return;
		}
		if(!this.isExistUrl(name, url))
		{
			var link = {name:name,url:url};
			this.urlList[this.currentIndex] = link;
			this.currentIndex++;
		}
	},
	//判断导航名是否已经存在
	isExistUrl:function(name, url)
	{
		//从1开始,都不判断首节点
		//已经存在相同的url时，重设该点的名称
		//for(var i = 1; i < this.currentIndex; i++)
		//{
		//	if(url == this.urlList[i].url && name != this.urlList[i].name)
		//	{
		//		this.urlList[i].name = name;
		//		return true;//已经存在
		//	}
		//}
		//不存在相同的url时才查找导航名
		for(var i = 1; i < this.currentIndex; i++)
		{
			if(name == this.urlList[i].name)
			{
				this.currentIndex = i + 1;
				return true;
			}
		}
		return false;
	},
	//显示导航
	show:function()
	{
		var html = '';
		for(var i = 0; i < this.currentIndex - 1; i++)
		{
			//var mylink = this.urlList[i];if(mylink.url == null) html += mylink.name + '>';else html += '<a href="#" onclick="goHistory(\'' + mylink.url + '\')">' + mylink.name + '</a>>';
			html += this.urlList[i].name + '&gt;';
		}
		html += this.urlList[this.currentIndex - 1].name;
		document.getElementById(this.objName).innerHTML = html;
	}
};
function goHistory(url){document.getElementById("rightFrame").src = url;}
