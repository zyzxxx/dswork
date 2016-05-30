package common.wx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dswork.core.util.EncryptUtil;
import dswork.wx.model.msg.Msg;
import dswork.wx.model.message.*;
import dswork.wx.model.message.MessageNews.MessageNewsArticle;
import dswork.wx.session.DefaultSession;
import dswork.wx.session.HandleMessageAdapter;

/**
 * 处理微信服务器请求的响应地址
 * 注意：官方文档限制使用80端口
 */
public class WinXinServlet extends HttpServlet
{
	private static final long serialVersionUID = 10086L;

	/**
	 * 处理微信服务器验证
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		String[] arr =
		{
				nonce, timestamp, common.wx.WxFactory.TOKEN
		};
		Arrays.sort(arr);
		String k = "";
		for(int i = 0; i < arr.length; i++)
		{
			k += arr[i];
		}
		String c = EncryptUtil.encryptSha1(k);
		Writer out = response.getWriter();
		if(signature.toUpperCase().equals(c))
		{
			out.write(echostr);// 请求验证成功，返回随机码
		}
		else
		{
			out.write("");
		}
		out.flush();
		out.close();
	}

	/**
	 * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		InputStream is = request.getInputStream();
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
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "【菜单】\n" + "1. 功能菜单\n" + "2. 图文测试\n" + "3. 图片测试\n");
					sess.callback(reMsg);
				}
				else if(Msg.EVENT_UNSUBSCRIBE.equals(eventType))
				{// 取消订阅
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "取消关注：" + msg.getFromUserName());
					sess.callback(reMsg);
				}
				else if(Msg.EVENT_CLICK.equals(eventType))
				{// 点击事件
					MessageText reMsg = new MessageText(msg.getFromUserName(), msg.getToUserName(), "用户：" + msg.getFromUserName() + ",点击Key：" + msg.getEventKey());
					sess.callback(reMsg);
				}
			}
		});
		// 处理地理位置
		sess.addOnHandleMessageListener(new HandleMessageAdapter()
		{
			public void onLocation(Msg msg)
			{
				MessageText m = new MessageText(msg.getFromUserName(), msg.getToUserName(), "收到地理位置消息：" + "X:" + msg.getLocation_X() + ",Y:" + msg.getLocation_Y() + ",Scale:" + msg.getScale());
				sess.callback(m);
			}
		});
		sess.process(is, os);// 处理微信消息
		sess.close();// 关闭Session
		try
		{
			os.close();
		}
		catch(Exception ex)
		{
		}
	}
}
