var showNavigation = function(title)
{
	try
	{
		var url = window.location.href;
		top.frames['middleFrame'].navigation.addUrl(title, url);
		top.frames['middleFrame'].navigation.show();
	}
	catch(ex)
	{
		//window.alert(ex.name);   
		//window.alert(ex.message);   
	}
};