package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import dswork.wx.model.message.MessageNews;
import dswork.wx.model.message.MessageNews.MessageNewsArticle;
import dswork.wx.model.msg.Msg;
import dswork.wx.session.DefaultSession;
import dswork.wx.session.HandleMessageAdapter;
import dswork.wx.session.HandleMessageListener;

/**
 * Demo
 * 这个是最新版的例子程序，需要结合旧版里的Servlet使用。
 * @author marker
 */
public class Demo
{
	public static void main(String[] args) throws FileNotFoundException, InterruptedException
	{
		while(true)
		{
			// 这里要说明下下：a.txt是微信的xml消息格式文件
			InputStream is = new FileInputStream("e://a.txt");
			OutputStream os = new FileOutputStream("e://c.txt");
			// 创建Session，默认没有任何监听器的。要使用功能必须添加监听器
			final DefaultSession session = DefaultSession.newInstance();
			// 1. 使用HandleMessageListener接口实现添加
			session.addOnHandleMessageListener(new HandleMessageListener()
			{
				public void onText(Msg msg)
				{
					// TODO Auto-generated method stub
				}

				public void onImage(Msg msg)
				{
					// TODO Auto-generated method stub
				}

				public void onEvent(Msg msg)
				{
					// TODO Auto-generated method stub
				}

				public void onLink(Msg msg)
				{
					// TODO Auto-generated method stub
				}

				public void onLocation(Msg msg)
				{
					// TODO Auto-generated method stub
				}

				public void onError(int errorCode)
				{
					// TODO Auto-generated method stub
				}

				public void onVoice(Msg msg)
				{
					// TODO Auto-generated method stub
				}

				public void onVideo(Msg msg)
				{
					// TODO Auto-generated method stub
				}
			});
			// 2. 使用HandleMessageAdapter适配器添加监听器（推荐使用此方案）
			session.addOnHandleMessageListener(new HandleMessageAdapter()
			{
				@Override
				public void onText(Msg msg)
				{
					System.out.println("收到消息：" + msg.getContent());
					// //回复一条消息
					// Msg4Text reMsg = new Msg4Text();
					// reMsg.setFromUserName(msg.getToUserName());
					// reMsg.setToUserName(msg.getFromUserName());
					// reMsg.setCreateTime(msg.getCreateTime());
					// reMsg.setMsgType(Msg.MSG_TYPE_TEXT);//设置消息类型
					// reMsg.setContent("呵呵,谢谢您给我发消息");
					// reMsg.setFuncFlag("0");
					
					
					MessageNewsArticle d1 = new MessageNewsArticle("测试标题", "测试描述", "http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg", " www.baidu.com");
					MessageNewsArticle d2 = new MessageNewsArticle("测试标题", "测试描述", "http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg", " www.baidu.com");
					List<MessageNewsArticle> list = new ArrayList<MessageNewsArticle>();
					list.add(d1);
					list.add(d2);
					MessageNews mit = new MessageNews(msg.getToUserName(), msg.getFromUserName(), list);
					session.callback(mit);
				}
			});
			// 处理消息（必须调用此方法才能处理微信消息）
			session.process(is, os);
			Thread.sleep(5000);
		}
	}
}
