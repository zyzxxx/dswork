<%@page language="java" pageEncoding="UTF-8"%><%
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/plain;charset=UTF-8");
	String rootpath = "http://192.168.1.10";
	String xpath = rootpath + "/www-visitgz/m/cms/category.jsp?siteid=7&pagesize=5";
%>{<%
	%>"siteid":"7"<%
	%>,"siteurl":"<%=rootpath%>"<%
	%>,"dataurl":"<%=xpath%>"<%
	%>,"imgArray":["/www-visitgz/m/cms/visitgz/banner/banner1.jpg","/www-visitgz/m/cms/visitgz/banner/banner2.jpg","/www-visitgz/m/cms/visitgz/banner/banner3.jpg","/www-visitgz/m/cms/visitgz/banner/banner4.jpg","/www-visitgz/m/cms/visitgz/banner/banner5.jpg"]<%
	%>,"icoArray":[<%
			%> {"name":"机器人"  ,"type":"url", "img":"/www-visitgz/m/cms/visitgz/ico/jqr.png"    ,"url":"http://120.25.152.16/lyj-robotwwwa/app/appIndex.html"}<%
			%>,{"name":"广州通"  ,"type":"app", "img":"/www-visitgz/m/cms/visitgz/ico/gzt.png"    ,"url":"http://gdown.baidu.com/data/wisegame/c504507db77a1ae1/guangzhoutong_256.apk","pkgname":"com.xtownmobile.NZHGD.GZ","appname":""}<%
			%>,{"name":"旅程推荐","type":"func","img":"/www-visitgz/m/cms/visitgz/ico/zl.png"     ,"url":"<%=xpath%>&categoryids=48,48&categoryids=49,49&categoryids=50,50&categoryids=127,127&categoryids=190,190"}<%
			%>,{"name":"资讯网"  ,"type":"url", "img":"/www-visitgz/m/cms/visitgz/ico/visitgz.png","url":"http://192.168.1.10"}<%
			%>,{"name":"旅游服务","type":"func","img":"/www-visitgz/m/cms/visitgz/ico/tx.png"     ,"url":"<%=xpath%>&categoryids=55,55&categoryids=118,118"}<%
	%>]<%
	%>,"url":"<%=xpath%>&categoryids=13,14,15,16&categoryids=22,23,24,25,26&categoryids=28&categoryids=17,18,19,20,21&categoryids=33,34,35,36&categoryids=37,38,39,40"<%
%>}
