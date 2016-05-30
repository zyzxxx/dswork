package dswork.wx.model.message;

public class MessageImage extends Message
{
	private String mediaId;

	public MessageImage(String toUserName, String fromUserName, String mediaId)
	{
		super(toUserName, fromUserName, "image");
		this.mediaId = mediaId;
	}

	@Override
	public String subXML()
	{
		return "<Image><MediaId><![CDATA[" + mediaId + "]]></MediaId></Image>";
	}
}
