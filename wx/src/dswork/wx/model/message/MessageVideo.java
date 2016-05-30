package dswork.wx.model.message;

public class MessageVideo extends Message
{
	private String mediaId;
	private String title;
	private String description;

	public MessageVideo(String toUserName, String fromUserName, String mediaId, String title, String description)
	{
		super(toUserName, fromUserName, "video");
		this.mediaId = mediaId;
		this.title = title;
		this.description = description;
	}

	@Override
	public String subXML()
	{
		return "<Video><MediaId><![CDATA[" + mediaId + "]]></MediaId><Title><![CDATA[" + (title == null ? "" : title) + "]]></Title><Description><![CDATA[" + (description == null ? "" : description) + "]]></Description></Video>";
	}
}
