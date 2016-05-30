<%@page language="java" import="
java.util.*,
common.wx.*,
dswork.wx.*,
dswork.wx.model.msg.*,
dswork.wx.model.message.*,
dswork.wx.model.message.MessageNews.MessageNewsArticle,
dswork.wx.session.*,
dswork.core.util.*,
java.io.*" pageEncoding="UTF-8"%><%
//此文件用于校验和处理微信消息”


String signature = request.getParameter("signature");
String timestamp = request.getParameter("timestamp");
String nonce = request.getParameter("nonce");


String[] arr =
{
		nonce, timestamp, common.wx.WxFactory.TOKEN
};
Arrays.sort(arr);
String _k = "";
for(int i = 0; i < arr.length; i++)
{
	_k += arr[i];
}
String c = EncryptUtil.encryptSha1(_k);
if(signature.toUpperCase().equals(c)){
	System.out.println("");
	System.out.println(request.getMethod());
	System.out.println(request.getQueryString());
	if( request.getMethod().equals("GET") ){
		String echostr = request.getParameter("echostr");
		if(echostr != null){
			// 这种情况是第一次接入设置时的处理代码
			System.out.println("验证通过" + echostr);
			out.print(echostr);
		}
	}
	else if( request.getMethod().equals("POST") ){
		
		/*
		ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try
		{
			while((i = request.getInputStream().read(buffer)) > -1 )
			{
				byteArr.write(buffer, 0, len);
			}
			byteArr.flush();
		}
		catch(IOException e)
		{
		}
		*/
		int i = request.getContentLength();
		byte[] byteArray = new byte[i];
		int j = 0;
		while(j < i)// 获取表单的上传文件
		{
			int k = request.getInputStream().read(byteArray, j, i - j);
			j += k;
		}
		String body = new String(byteArray, "UTF-8");
		System.out.println("微信提交过来的信息是：");
		System.out.println(body);
		
		
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		InputStream is = new ByteArrayInputStream(byteArray);//request.getInputStream();
		OutputStream os = response.getOutputStream();
		final DefaultSession sess = DefaultSession.newInstance();
		sess.addOnHandleMessageListener(new HandleMessageAdapter()
		{
			@Override
			public void onText(Msg msg)
			{
				if("1".equals(msg.getContent()))
				{// 菜单选项1
					MessageText m = new MessageText(msg.getFromUserName(), msg.getToUserName(), "【菜单】\n" + "1. 功能菜单\n" + "2. 图文测试\n" + "3. 图片测试\n");
					sess.callback(m);// 回传消息
				}
				else if("2".equals(msg.getContent()))
				{
					MessageNewsArticle d1 = new MessageNewsArticle("itellyou", "与世界分享你的知识、经验和见解", "http://msdn.itellyou.cn/images/itellyou.cn.png", "msdn.itellyou.cn");
					MessageNewsArticle d2 = new MessageNewsArticle("百度", "百度一下，你就知道", "https://www.baidu.com/img/bdlogo.png", "www.baidu.com");
					List<MessageNewsArticle> list = new ArrayList<MessageNewsArticle>();
					list.add(d1);
					list.add(d2);
					MessageNews mit = new MessageNews(msg.getFromUserName(), msg.getToUserName(), list);
					sess.callback(mit);
				}
				else if("3".equals(msg.getContent()))
				{
					MessageImage m = new MessageImage(msg.getFromUserName(), msg.getToUserName(), "http://msdn.itellyou.cn/images/itellyou.cn.png");
					sess.callback(m);
				}
				else
				{
					MessageText m = new MessageText(msg.getFromUserName(), msg.getToUserName(), "消息命令错误，请重试！");
					sess.callback(m);
				}
			}
		});
		// 语音识别消息
		sess.addOnHandleMessageListener(new HandleMessageAdapter()
		{
			public void onVoice(Msg msg)
			{
				MessageText m = new MessageText(msg.getFromUserName(), msg.getToUserName(), "识别结果: " + msg.getRecognition());
				sess.callback(m);
			}
		});
		// 处理事件
		sess.addOnHandleMessageListener(new HandleMessageAdapter()
		{
			public void onEvent(Msg msg)
			{
				String eventType = msg.getEvent();
				if(Msg.EVENT_SUBSCRIBE.equals(eventType))
				{
					// 订阅
					System.out.println("关注：" + msg.getFromUserName());
				}
				else if(Msg.EVENT_UNSUBSCRIBE.equals(eventType))
				{// 取消订阅
					System.out.println("取消关注：" + msg.getFromUserName());
				}
				else if(Msg.EVENT_CLICK.equals(eventType))
				{// 点击事件
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "事件类型：" + eventType + ",事件类型：" + eventType + ",用户：" + msg.getFromUserName()+",点击Key：" + msg.getEventKey());
					sess.callback(reMsg);
				}
				else if(Msg.EVENT_CLICK_VIEW.equals(eventType))
				{
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "事件类型：" + eventType + ",用户：" + msg.getFromUserName()+",点击Key：" + msg.getEventKey()+",点击menuid：" + msg.getMenuId());
					sess.callback(reMsg);
				}
				else if(Msg.EVENT_CLICK_SCANCODE_PUSH.equals(eventType) || Msg.EVENT_CLICK_SCANCODE_WAITMSG.equals(eventType))
				{
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "事件类型：" + eventType + ",用户：" + msg.getFromUserName()+",扫描类型：" + msg.getScanType()+",扫描结果：" + msg.getScanResult());
					sess.callback(reMsg);
				}
				else if(Msg.EVENT_CLICK_PIC_SYSPHOTO.equals(eventType) || Msg.EVENT_CLICK_PIC_PHOTO_OR_ALBUM.equals(eventType) || Msg.EVENT_CLICK_PIC_WEIXIN.equals(eventType))
				{
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "事件类型：" + eventType + ",用户：" + msg.getFromUserName()+",点击Key：" + msg.getEventKey());
					sess.callback(reMsg);
					
				}
				else if(Msg.EVENT_CLICK_LOCATION_SELECT.equals(eventType))
				{
					MessageText m = new MessageText(msg.getFromUserName(), msg.getToUserName(), "收到地理位置消息："+"X:" + msg.getLocation_X()+",Y:" + msg.getLocation_Y()+",Scale:" + msg.getScale());
					sess.callback(m);
					//<SendLocationInfo>//发送的位置信息
					//	<Location_X><![CDATA[23]]></Location_X>//X坐标信息
					//	<Location_Y><![CDATA[113]]></Location_Y>//	Y坐标信息
					//	<Scale><![CDATA[15]]></Scale>//精度，可理解为精度或者比例尺、越精细的话 scale越高
					//	<Label><![CDATA[ 广州市海珠区客村]]></Label>//地理位置的字符串信息
					//	<Poiname><![CDATA[]]></Poiname>//朋友圈POI的名字，可能为空
					//</SendLocationInfo>
				}
			}
		});
		// 处理地理位置
		sess.addOnHandleMessageListener(new HandleMessageAdapter()
		{
			public void onLocation(Msg msg)
			{
				MessageText m = new MessageText(msg.getFromUserName(), msg.getToUserName(), "收到地理位置消息："+"X:" + msg.getLocation_X()+",Y:" + msg.getLocation_Y()+",Scale:" + msg.getScale());
				sess.callback(m);
			}
		});
		sess.process(is, os);// 处理微信消息
		sess.close();// 关闭Session
		try{
			os.close();
		}catch(Exception ex){}
	}
}
%>