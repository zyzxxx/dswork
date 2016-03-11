<%@page language="java" import="java.util.*, common.wx.*, org.dom4j.*" pageEncoding="UTF-8"%><%
// 此文件用于校验和处理微信消息”
System.out.println(request.getQueryString());
String signature = request.getParameter("signature");
String timestamp = request.getParameter("timestamp");
String nonce = request.getParameter("nonce");
boolean rs = WxFactory.checkSign(signature, timestamp, nonce);
if(rs){
	String echostr = request.getParameter("echostr");
	if(echostr != null){
		// 这种情况是第一次接入设置时的处理代码
		System.out.println("验证通过" + echostr);
		out.print(echostr);
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
		String body = new String(byteArray, "UTF-8");
		/*
		<xml>
			<ToUserName><![CDATA[公众号]]></ToUserName>
			<FromUserName><![CDATA[扫码的微信号Openid]]></FromUserName>
			<CreateTime>1457513276</CreateTime>
			<MsgType><![CDATA[event]]></MsgType>
			......
		</xml>
		*/
		System.out.println("微信提交过来的信息是：" + body);
		Document document = DocumentHelper.parseText(body);
		Element node = document.getRootElement();
		
		String _ToUserName = String.valueOf(node.element("ToUserName").getText());
		String _FromUserName = String.valueOf(node.element("FromUserName").getText());
		
		String _MsgType = String.valueOf(node.element("MsgType").getText());
		
		System.out.println("MsgType：" + _MsgType);
		if(_MsgType.equals("event")){
			String _Event = String.valueOf(node.element("Event").getText());
			String _EventKey = String.valueOf(node.element("EventKey").getText());
			//<Event><![CDATA[scancode_waitmsg、scancode_push、VIEW]]></Event>
			//<EventKey><![CDATA[自定义的key值或URL值]]></EventKey>
			if(_Event.equals("VIEW")){
				System.out.println(":::VIEW" + _EventKey);// EventKey是URL值
			}
			else if(_Event.equals("CLICK")){
				System.out.println(":::CLICK" + _EventKey);// EventKey是KEY值
			}
			else if(_Event.equals("scancode_waitmsg") || _Event.equals("scancode_push")){
				//<ScanCodeInfo>
				//	<ScanType><![CDATA[qrcode]]></ScanType>
				//	<ScanResult><![CDATA[http://weixin.qq.com/r/GERcROPE5b6aracn9xEK]]></ScanResult>
				//</ScanCodeInfo>
				Element _ScanCodeInfo = node.element("ScanCodeInfo");
				String _ScanType = String.valueOf(_ScanCodeInfo.element("ScanType").getText());
				String _ScanResult = String.valueOf(_ScanCodeInfo.element("ScanResult").getText());
				if(_ScanType.equals("qrcode")){
					System.out.println(":::qrcode:::" + _ScanType + ":::" + _ScanResult);
				}
			}
		}
		else if(_MsgType.equals("text")){
			//<Content><![CDATA[二]]></Content>
			//<MsgId>6259982269603693944</MsgId>
			String _Content = String.valueOf(node.element("Content").getText());
			String _MsgId = String.valueOf(node.element("MsgId").getText());
			System.out.println(":::text:::" + _Content + ":::" + _MsgId);
		}
		
		else if(_MsgType.equals("image")){
			//<PicUrl><![CDATA[http://mmbiz.qpic.cn/mmbiz/zK9prSe7I3s77t8PEMYNBalwvMwuxxKktqFkuGP4EEMrTHwicBhj9MSWFicicjGwXtDjpTXPTTX8LLqWpibTxrvlBg/0]]></PicUrl>
			//<MsgId>6259991301919917838</MsgId>
			//<MediaId><![CDATA[Wtu7qBA4kQr5rWxK7YiRKJCmHo08r87Vu1MTXh7YUTvDH5nu3RTlP-TTGo7fBQpD]]></MediaId>
			String _PicUrl = String.valueOf(node.element("PicUrl").getText());
			String _MsgId = String.valueOf(node.element("MsgId").getText());
			String _MediaId = String.valueOf(node.element("MediaId").getText());
			System.out.println(":::voice:::" + _PicUrl + ":::" + _MsgId + ":::" + _MediaId);
		}
		else if(_MsgType.equals("voice")){
			//<MediaId><![CDATA[rjqf7-Tp8EehOu_elo2M5R_J-Ibu7dSi6LZm5KgM55iLmhxYH4lDI7jhKeflRK3q]]></MediaId>
			//<Format><![CDATA[amr]]></Format>
			//<MsgId>6259983850151658941</MsgId>
			//<Recognition><![CDATA[]]></Recognition>
			String _MediaId = String.valueOf(node.element("MediaId").getText());
			String _Format = String.valueOf(node.element("Format").getText());//amr，speex等
			String _MsgId = String.valueOf(node.element("MsgId").getText());
			String _Recognition = String.valueOf(node.element("Recognition").getText());//Recognition为语音识别结果
			System.out.println(":::voice:::" + _MediaId + ":::" + _Format + ":::" + _MsgId + ":::" + _Recognition);
		}
		else if(_MsgType.equals("shortvideo")){
			//<MediaId><![CDATA[olMUZACJNCjqkOY-FNjoEpNkuqxuy5iO9d-WpdhNE9EpBwIlA5RbLkVNq2cTYCxe]]></MediaId>
			//<ThumbMediaId><![CDATA[6EZYQP3O08iHrPgOXodDtbIwl6_AbpqPXi7C4YryPBXqpTRkIzThydLLI-kGAtgO]]></ThumbMediaId>
			//<MsgId>6260308790197416612</MsgId>
			String _MediaId = String.valueOf(node.element("MediaId").getText());
			String _ThumbMediaId = String.valueOf(node.element("ThumbMediaId").getText());//amr，speex等
			String _MsgId = String.valueOf(node.element("MsgId").getText());
			System.out.println(":::voice:::" + _MediaId + ":::" + _ThumbMediaId + ":::" + _MsgId);
		}
	}
}
%>