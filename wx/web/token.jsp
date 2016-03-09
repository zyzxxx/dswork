<%@page language="java" import="java.util.*, common.wx.*, dswork.core.util.FileUtil, org.dom4j.*" pageEncoding="UTF-8"%><%
// 此文件用于校验和处理微信消息”
System.out.println(request.getQueryString());
String signature = request.getParameter("signature");
String timestamp = request.getParameter("timestamp");
String nonce = request.getParameter("nonce");
boolean rs = WxExecute.checkSign(signature, timestamp, nonce);
if(rs){
	String echostr = request.getParameter("echostr");
	if(echostr != null){
		// 这种情况是第一次接入设置时的处理代码
		System.out.println("验证通过" + echostr);
		%><%=echostr%><%
	}
	else{
		int i = request.getContentLength();
		byte[] byteArray = new byte[i];
		int j = 0;
		while(j < i)// 获取表单的上传文件
		{
			int k = request.getInputStream().read(byteArray, j, i - j);
			j += k;
		}
		String body = FileUtil.getToString(FileUtil.getToInputStream(byteArray), "UTF-8");
		System.out.println("微信提交过来的信息是：" + body);
		Document document = DocumentHelper.parseText(body);
		
	}
}
%>