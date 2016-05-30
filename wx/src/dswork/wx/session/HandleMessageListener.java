package dswork.wx.session;

import dswork.wx.model.msg.Msg;

/**
 * 主要用于接收微信服务器消息的接口
 */
public interface HandleMessageListener
{
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
	 * 语音识别消息
	 * @param msg
	 */
	public abstract void onVoice(Msg msg);

	/**
	 * 错误消息
	 * @param msg
	 */
	public abstract void onError(int errorCode);

	/**
	 * 视频消息
	 * @param msg
	 */
	public abstract void onVideo(Msg msg);
}
