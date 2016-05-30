package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import dswork.wx.model.message.MessageText;
import dswork.wx.model.msg.Msg;
import dswork.wx.session.Session;

public class mTest
{
	public static void main(String[] args) throws FileNotFoundException
	{
		InputStream inputStream = new FileInputStream("e://a.txt");
		OutputStream os = new FileOutputStream("e://b.txt");
		Session session = new Session()
		{
			@Override
			public void onText(Msg msg)
			{
				System.out.println(msg.getToUserName());
				System.out.println("收到消息" + msg.getContent());
				MessageText reMsg = new MessageText(msg.getToUserName(), msg.getFromUserName(), "呵呵,谢谢您给我发消息");
				callback(reMsg);
			}

			@Override
			public void onImage(Msg msg)
			{
			}

			@Override
			public void onEvent(Msg msg)
			{
				System.out.println("接收到事件消息");
			}

			@Override
			public void onLink(Msg msg)
			{
			}

			@Override
			public void onLocation(Msg msg)
			{
			}

			@Override
			public void onError(int errorCode)
			{
			}

			@Override
			public void onVoice(Msg msg)
			{
				System.out.println(msg.getRecognition());
			}

			@Override
			public void onVideo(Msg msg)
			{
				// TODO Auto-generated method stub
			}
		};
		session.process(inputStream, os);
	}
}
