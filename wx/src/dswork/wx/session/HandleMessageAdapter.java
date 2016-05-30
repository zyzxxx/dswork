package dswork.wx.session;

import dswork.wx.model.msg.Msg;

/**
 * 处理消息适配器(适配器模式)
 */
public class HandleMessageAdapter implements HandleMessageListener
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
}
