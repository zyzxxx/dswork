package dswork.wx.session;

import java.util.ArrayList;
import java.util.List;

import dswork.wx.model.msg.Msg;

/**
 * 默认的Session实现
 * 使用默认Session实现类需要通过添加HandleMessageListener监听器来对微信消息进行监听
 * 可以添加多个监听器来处理不同的消息内容。
 * 注意：此类并非线程安全，建议每次使用newInstance调用
 */
public class DefaultSession extends Session
{
	/** 监听器集合 */
	private List<HandleMessageListener> listeners = new ArrayList<HandleMessageListener>(3);

	/**
	 * 私有构造方法
	 */
	private DefaultSession()
	{
	}

	/**
	 * 创建一个Session实例
	 */
	public static DefaultSession newInstance()
	{
		return new DefaultSession();
	}

	/**
	 * 添加监听器
	 * @param handleMassge
	 */
	public void addOnHandleMessageListener(HandleMessageListener handleMassge)
	{
		listeners.add(handleMassge);
	}

	/**
	 * 移除监听器
	 */
	public void removeOnHandleMessageListener(HandleMessageListener handleMassge)
	{
		listeners.remove(handleMassge);
	}

	@Override
	public void onText(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onText(msg);
		}
	}

	@Override
	public void onImage(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onImage(msg);
		}
	}

	@Override
	public void onEvent(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onEvent(msg);
		}
	}

	@Override
	public void onLink(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onLink(msg);
		}
	}

	@Override
	public void onLocation(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onLocation(msg);
		}
	}

	@Override
	public void onError(int errorCode)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onError(errorCode);
		}
	}

	@Override
	public void onVoice(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onVoice(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see dswork.wx.session.Session#onVideoMsg(dswork.wx.msg.Msg4Video)
	 */
	@Override
	public void onVideo(Msg msg)
	{
		for(HandleMessageListener currentListener : listeners)
		{
			currentListener.onVideo(msg);
		}
	}
}
