package dswork.wx.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import dswork.wx.model.message.Message;
import dswork.wx.model.msg.Msg;

/**
 * 抽象会话
 * 此会话声明周期在一个请求响应内。
 * 通过继承类实现各种消息的处理方法
 */
public abstract class Session
{
	/** 时间格式化 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	// 输入流
	private InputStream is;
	// 输出流
	private OutputStream os;
	/** Document构建类 */
	private static DocumentBuilderFactory factory;
	private static DocumentBuilder builder;
	//private static TransformerFactory tffactory;
	static
	{
		factory = DocumentBuilderFactory.newInstance();
		try
		{
			builder = factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		// 格式化工厂对象
		//tffactory = TransformerFactory.newInstance();
	}

	/**
	 * Session
	 */
	public Session()
	{
	}

	/**
	 * 解析微信消息，并传递给对应方法
	 * @param is 输入流
	 * @param os 输出流
	 */
	public void process(InputStream is, OutputStream os)
	{
		this.os = os;
		try
		{
			Document document = builder.parse(is);
			String type = Msg.readMsgType(document);
			Msg msg = new Msg(document);
			if(Msg.MSG_TYPE_TEXT.equals(type))
			{// 文本消息
				this.onText(msg);
			}
			else if(Msg.MSG_TYPE_IMAGE.equals(type))
			{// 图片消息
				this.onImage(msg);
			}
			else if(Msg.MSG_TYPE_EVENT.equals(type))
			{// 事件推送
				this.onEvent(msg);
			}
			else if(Msg.MSG_TYPE_LINK.equals(type))
			{// 链接消息
				this.onLink(msg);
			}
			else if(Msg.MSG_TYPE_LOCATION.equals(type))
			{// 地理位置消息
				this.onLocation(msg);
			}
			else if(Msg.MSG_TYPE_VOICE.equals(type))
			{
				this.onVoice(msg);
			}
			else if(Msg.MSG_TYPE_VIDEO.equals(type))
			{
				this.onVideo(msg);
			}
			else
			{
				this.onError(-1);// 这里暂时这样处理的
			}
		}
		catch(SAXException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 回传消息给微信服务器
	 * 只能再接收到微信服务器消息后，才能调用此方法
	 * @param msg 消息对象（支持：文本、音乐、图文）
	 */
	public void callback(Message msg)
	{
		try
		{
			msg.outputStreamWrite(os);
			//os.write(b);
			//Document document = builder.newDocument();
			//msg.write(document);
			//Transformer t = tffactory.newTransformer();
			//t.transform(new DOMSource(document), new StreamResult(new OutputStreamWriter(os, "utf-8")));
		}
		catch(Exception e)
		{
			e.printStackTrace();// 保存dom至输出流
		}
	}

	/**
	 * 关闭
	 */
	public void close()
	{
		try
		{
			if(is != null)
			{
				is.close();
			}
			if(os != null)
			{
				os.flush();
				os.close();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 收到文本消息
	 * @param msg
	 */
	public abstract void onText(Msg msg);

	/**
	 * 收到图片消息
	 * @param msg
	 */
	public abstract void onImage(Msg msg);

	/**
	 * 收到事件推送消息
	 * @param msg
	 */
	public abstract void onEvent(Msg msg);

	/**
	 * 收到链接消息
	 * @param msg
	 */
	public abstract void onLink(Msg msg);

	/**
	 * 收到地理位置消息
	 * @param msg
	 */
	public abstract void onLocation(Msg msg);

	/**
	 * 收到语音识别消息
	 * @param msg
	 */
	public abstract void onVoice(Msg msg);

	/**
	 * 收到视频消息
	 * @param msg
	 */
	public abstract void onVideo(Msg msg);

	/**
	 * 错误消息
	 * @param msg
	 */
	public abstract void onError(int errorCode);
}
