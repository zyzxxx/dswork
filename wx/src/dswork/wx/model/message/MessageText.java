package dswork.wx.model.message;

public class MessageText extends Message
{
	private String content;

	public MessageText(String toUserName, String fromUserName, String content)
	{
		super(toUserName, fromUserName, "text");
		this.content = content;
	}

	@Override
	public String subXML()
	{
		return "<Content><![CDATA[" + content + "]]></Content>";
	}
}
