package dswork.wx.model.message;

import java.util.List;

public class MessageNews extends Message
{
	private List<MessageNewsArticle> articles;

	/**
	 * @param toUserName
	 * @param fromUserName
	 * @param articles
	 */
	public MessageNews(String toUserName, String fromUserName, List<MessageNewsArticle> articles)
	{
		super(toUserName, fromUserName, "news");
		this.articles = articles;
	}

	@Override
	public String subXML()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<ArticleCount>" + articles.size() + "</ArticleCount>");
		sb.append("<Articles>");
		for(MessageNewsArticle a : articles)
		{
			sb.append("<item>");
			sb.append("<Title><![CDATA[").append((a.title == null ? "" : a.title)).append("]]></Title>");
			sb.append("<Description><![CDATA[").append((a.description == null ? "" : a.description)).append("]]></Description>");
			sb.append("<PicUrl><![CDATA[").append((a.picurl == null ? "" : a.picurl)).append("]]></PicUrl>");
			sb.append("<Url><![CDATA[").append((a.url == null ? "" : a.url)).append("]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		return sb.toString();
	}

	public static class MessageNewsArticle
	{
		private String title;
		private String description;
		private String picurl;
		private String url;

		public MessageNewsArticle()
		{
		}

		public MessageNewsArticle(String title, String description, String picurl, String url)
		{
			this.title = title;
			this.description = description;
			this.picurl = picurl;
			this.url = url;
		}

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getDescription()
		{
			return description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public String getPicurl()
		{
			return picurl;
		}

		public void setPicurl(String picurl)
		{
			this.picurl = picurl;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}
	}
}
