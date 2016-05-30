package dswork.wx.model.message;

public class MessageVoice extends Message
{
	private String mediaId;

	public MessageVoice(String toUserName, String fromUserName, String mediaId)
	{
		super(toUserName, fromUserName, "voice");
		this.mediaId = mediaId;
	}

	@Override
	public String subXML()
	{
		return "<Voice><MediaId><![CDATA[" + mediaId + "]]></MediaId></Voice>";
	}
}
