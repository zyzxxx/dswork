<%@page language="java" import="java.util.*, common.wx.*, dswork.http.HttpUtil" pageEncoding="UTF-8"%><%
HttpUtil http = new HttpUtil();
// 删除所有菜单
http.create("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + WxFactory.getAccessToken()).connect();
// 重新创建菜单
http.create("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + WxFactory.getAccessToken());
String json = "{'button':[" +
		"{'name':'用户管理','sub_button':[" +
       	"	{'type':'view', 'name':'我的微信',  'url':'http://183.232.32.145/wx/index.jsp'}," +
       	"	{'type':'view', 'name':'登录',      'url':'http://183.232.32.145/wx/login.jsp'}," +
       	"	{'type':'view', 'name':'注册',      'url':'http://183.232.32.145/wx/reg.jsp'}," +
       	"	{'type':'click','name':'点赞',      'key':'GO_1'}" +
       	"]}," +
       	"{'name':'扫码','sub_button':[" +
       	"	{'type':'scancode_waitmsg',    'name': '扫码带提示',    'key': 'GO_2_1','sub_button': []}, " +
       	"	{'type':'scancode_push',       'name': '扫码推事件',    'key': 'GO_2_2','sub_button': []}" +
       	"]}, " +
       	"{'name':'发图','sub_button':[" +
       	"	{'type':'pic_sysphoto',        'name': '拍照',  'key': 'GO_3_1','sub_button': []}, " +
       	"	{'type':'pic_photo_or_album',  'name': '拍照或相册',  'key': 'GO_3_2','sub_button': []}, " +
       	"	{'type':'pic_weixin',          'name': '相册',  'key': 'GO_3_3','sub_button': []}" +
       	"]}" +
	"]}";
json = json.replaceAll("'", "\"");
System.out.println(json);
String msg = http.setDataBody(json.getBytes("UTF-8")).connect();
%><%=msg%>
<br />
<%=json%>