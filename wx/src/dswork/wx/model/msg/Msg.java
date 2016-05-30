package dswork.wx.model.msg;

import org.w3c.dom.Document;
//import org.w3c.dom.Element;

/**
 * 抽象消息类 提供各种消息类型字段、头部消息对象以及写入和读取抽象方法
 */
public class Msg
{
	public static final String MSG_TYPE_TEXT = "text";//文本消息
	public static final String MSG_TYPE_IMAGE = "image";//图片消息
	public static final String MSG_TYPE_MUSIC = "music";//音乐消息
	public static final String MSG_TYPE_LOCATION = "location";//地理位置消息
	public static final String MSG_TYPE_LINK = "link";//链接消息
	public static final String MSG_TYPE_IMAGE_TEXT = "news";//图文消息 
	public static final String MSG_TYPE_EVENT = "event";//事件消息
	public static final String MSG_TYPE_VOICE = "voice";//语音识别消息
	public static final String MSG_TYPE_VIDEO = "video";//视频消息

	/**
	 * 获取节点文本内容
	 * @param document 文档
	 * @param element 节点名称
	 * @return 内容
	 */
	private static String getEtext(Document document, String element)
	{
		return document.getElementsByTagName(element).item(0).getTextContent();
	}

	
	public Msg(Document document)
	{
		readHead(document);
		if(Msg.MSG_TYPE_TEXT.equals(this.msgType))
		{// 文本消息
			this.readText(document);
		}
		else if(Msg.MSG_TYPE_IMAGE.equals(this.msgType))
		{// 图片消息
			this.readImage(document);
		}
		else if(Msg.MSG_TYPE_EVENT.equals(this.msgType))
		{// 事件推送
			this.readEvent(document);
		}
		else if(Msg.MSG_TYPE_LINK.equals(this.msgType))
		{// 链接消息
			this.readLink(document);
		}
		else if(Msg.MSG_TYPE_LOCATION.equals(this.msgType))
		{// 地理位置消息
			this.readLocation(document);
		}
		else if(Msg.MSG_TYPE_VOICE.equals(this.msgType))
		{
			this.readVoice(document);
		}
		else if(Msg.MSG_TYPE_VIDEO.equals(this.msgType))
		{
			this.readVideo(document);
		}
	}
	
	/**
	 * 开发者微信号
	 */
	private String toUserName;// 开发者微信号
	/**
	 * 接收方帐号（收到的OpenID）
	 */
	private String fromUserName;// 发送方帐号（一个OpenID）
	private String createTime;// 消息创建时间 （整型）
	private String msgType;// 消息类型：text\image\
	public void readHead(Document document)
	{
		this.toUserName = getEtext(document, "ToUserName");//<![CDATA[]]>
		this.fromUserName = getEtext(document, "FromUserName");//<![CDATA[]]>
		this.createTime = getEtext(document, "CreateTime");
		this.msgType = getEtext(document, "MsgType");//<![CDATA[]]>
	}
	public static String readMsgType(Document document)
	{
		return getEtext(document, "MsgType");
	}
	
	
	private String content;// 文本消息内容
	private String msgId;// 消息id，64位整型
	private String picUrl;// 图片链接
	private String mediaId;// 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String format;// 语音格式：amr
	private String recognition;// 语音识别结果，UTF8编码
	private String thumbMediaId;// 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。


	//text=文本消息
	private void readText(Document document)
	{
		this.content = getEtext(document, "Content");//<![CDATA[]]>
		this.msgId = getEtext(document, "MsgId");
	}
	//image=图片消息
	private void readImage(Document document)
	{
		this.picUrl = getEtext(document, "PicUrl");//<![CDATA[]]>
		this.mediaId = getEtext(document, "MediaId");//<![CDATA[]]>
		this.msgId = getEtext(document, "MsgId");
	}
	//voice=语音消息
	private void readVoice(Document document)
	{
		this.mediaId = getEtext(document, "MediaId");//<![CDATA[]]>
		this.format = getEtext(document, "Format");//<![CDATA[]]>
		this.msgId = getEtext(document, "MsgId");
		this.recognition = getEtext(document, "Recognition");//开通语音识别后，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个Recongnition字段
	}
	//video=视频消息
	//shortvideo=小视频消息
	private void readVideo(Document document)
	{
		this.mediaId = getEtext(document, "MediaId");//<![CDATA[]]>
		this.thumbMediaId = getEtext(document, "ThumbMediaId");//<![CDATA[]]>
		this.msgId = getEtext(document, "MsgId");
	}
	
	//location=地理位置消息
	private String location_X;// 地理位置纬度
	private String location_Y;// 地理位置经度
	private String scale;// 地图缩放大小
	private String label;// 地理位置信息
	private void readLocation(Document document)
	{
		this.location_X = getEtext(document, "Location_X");
		this.location_Y = getEtext(document, "Location_Y");
		this.scale = getEtext(document, "Scale");
		this.label = getEtext(document, "Label");
		this.msgId = getEtext(document, "MsgId");
	}
	//link=链接消息
	private String title;// 消息标题
	private String description;// 消息描述
	private String url;// 消息链接
	private void readLink(Document document)
	{
		this.title = getEtext(document, "Title");
		this.description = getEtext(document, "Description");
		this.url = getEtext(document, "Url");
		this.msgId = getEtext(document, "MsgId");
	}
	
	
	
	
	

	

	public static final String EVENT_SUBSCRIBE = "subscribe";//订阅
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";//取消订阅
	public static final String EVENT_SCAN = "SCAN";// 用户已关注时的事件推送
	public static final String EVENT_LOCATION = "LOCATION";// 上报地理位置事件
	
	public static final String EVENT_CLICK = "CLICK";// 自定义菜单点击事件-点击菜单拉取消息时的事件推送
	public static final String EVENT_CLICK_VIEW = "VIEW";// 自定义菜单事件-点击菜单跳转链接时的事件推送
	public static final String EVENT_CLICK_SCANCODE_PUSH = "scancode_push";// 自定义菜单事件-扫码推事件的事件推送
	public static final String EVENT_CLICK_SCANCODE_WAITMSG = "scancode_waitmsg";// 自定义菜单事件-扫码推事件且弹出“消息接收中”提示框的事件推送
	public static final String EVENT_CLICK_PIC_SYSPHOTO = "pic_sysphoto";// 自定义菜单事件-弹出系统拍照发图的事件推送
	public static final String EVENT_CLICK_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";// 自定义菜单事件-弹出拍照或者相册发图的事件推送
	public static final String EVENT_CLICK_PIC_WEIXIN = "pic_weixin";// 自定义菜单事件-弹出微信相册发图器的事件推送
	public static final String EVENT_CLICK_LOCATION_SELECT = "location_select";// 自定义菜单事件-弹出地理位置选择器的事件推送
	
	
	private String event;// 事件类型subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件)
	private String eventKey;// 事件KEY值，与自定义菜单接口中KEY值对应
	private String ticket;// 二维码的ticket，可用来换取二维码图片
	private String latitude;// 地理位置纬度
	private String longitude;// 地理位置经度
	private String precision;// 地理位置精度


	//private String scanCodeInfo;// 包括scanType和scanResult
	private String scanType;// qrcode
	private String scanResult;// 二维码内容
	private String menuId;//指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。
	
	
	private void readEvent(Document document)
	{
		this.event = getEtext(document, "Event");
		// 用户未关注时，进行关注后的事件推送
		// EVENT_SCAN用户已关注时的事件推送
		if(EVENT_SUBSCRIBE.equals(this.event) || EVENT_UNSUBSCRIBE.equals(this.event) || EVENT_SCAN.equals(this.event))
		{
			this.eventKey = getEtext(document, "Event");
			this.ticket = getEtext(document, "Ticket");// 用户扫描带场景值二维码时才会带这个参数
		}
		// 上报地理位置事件
		else if(EVENT_LOCATION.equals(this.event))
		{
			this.latitude = getEtext(document, "Latitude");//地理位置纬度
			this.longitude = getEtext(document, "Longitude");//地理位置经度
			this.precision = getEtext(document, "Precision");//地理位置精度
		}
		else if(EVENT_CLICK.equals(this.event))
		{
			this.eventKey = getEtext(document, "EventKey");//KEY值
		}
		else if(EVENT_CLICK_VIEW.equals(this.event))
		{
			this.eventKey = getEtext(document, "EventKey");//URL值
			this.menuId = getEtext(document, "MenuId");//URL值
		}
		else if(EVENT_CLICK_SCANCODE_PUSH.equals(this.event) || EVENT_CLICK_SCANCODE_WAITMSG.equals(this.event))
		{
			//<ScanCodeInfo>//扫描信息
			//	<ScanType><![CDATA[qrcode]]></ScanType>//扫描类型，一般是qrcode
			//	<ScanResult><![CDATA[http://weixin.qq.com/r/GERcROPE5b6aracn9xEK]]></ScanResult>//扫描结果，即二维码对应的字符串信息
			//</ScanCodeInfo>

			this.eventKey = getEtext(document, "EventKey");
			org.w3c.dom.NodeList list = document.getElementsByTagName("ScanCodeInfo").item(0).getChildNodes();
			this.scanType = list.item(0).getTextContent();
			this.scanResult = list.item(1).getTextContent();
		}
		else if(EVENT_CLICK_PIC_SYSPHOTO.equals(this.event) || EVENT_CLICK_PIC_PHOTO_OR_ALBUM.equals(this.event) || EVENT_CLICK_PIC_WEIXIN.equals(this.event))
		{
			//<SendPicsInfo>//发送的图片信息
			//	<Count>1</Count>//发送的图片数量
			//	<PicList>//图片列表
			//		<item>
			//			<PicMd5Sum><![CDATA[1b5f7c23b5bf75682a53e7b6d163e185]]></PicMd5Sum>//图片的MD5值，开发者若需要，可用于验证接收到图片
			//		</item>
			//	</PicList>
			//</SendPicsInfo>
			this.eventKey = getEtext(document, "EventKey");
		}
		else if(EVENT_CLICK_LOCATION_SELECT.equals(this.event))
		{
			//<SendLocationInfo>//发送的位置信息
			//	<Location_X><![CDATA[23]]></Location_X>//X坐标信息
			//	<Location_Y><![CDATA[113]]></Location_Y>//	Y坐标信息
			//	<Scale><![CDATA[15]]></Scale>//精度，可理解为精度或者比例尺、越精细的话 scale越高
			//	<Label><![CDATA[ 广州市海珠区客村艺苑路 106号]]></Label>//地理位置的字符串信息
			//	<Poiname><![CDATA[]]></Poiname>//朋友圈POI的名字，可能为空
			//</SendLocationInfo>
			this.eventKey = getEtext(document, "EventKey");
		}
	}


	public String getToUserName()
	{
		return toUserName;
	}

	public void setToUserName(String toUserName)
	{
		this.toUserName = toUserName;
	}

	public String getFromUserName()
	{
		return fromUserName;
	}

	public void setFromUserName(String fromUserName)
	{
		this.fromUserName = fromUserName;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getMsgType()
	{
		return msgType;
	}

	public void setMsgType(String msgType)
	{
		this.msgType = msgType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getMsgId()
	{
		return msgId;
	}

	public void setMsgId(String msgId)
	{
		this.msgId = msgId;
	}

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public String getMediaId()
	{
		return mediaId;
	}

	public void setMediaId(String mediaId)
	{
		this.mediaId = mediaId;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public String getRecognition()
	{
		return recognition;
	}

	public void setRecognition(String recognition)
	{
		this.recognition = recognition;
	}

	public String getThumbMediaId()
	{
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId)
	{
		this.thumbMediaId = thumbMediaId;
	}

	public String getLocation_X()
	{
		return location_X;
	}

	public void setLocation_X(String location_X)
	{
		this.location_X = location_X;
	}

	public String getLocation_Y()
	{
		return location_Y;
	}

	public void setLocation_Y(String location_Y)
	{
		this.location_Y = location_Y;
	}

	public String getScale()
	{
		return scale;
	}

	public void setScale(String scale)
	{
		this.scale = scale;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
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

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getEvent()
	{
		return event;
	}

	public void setEvent(String event)
	{
		this.event = event;
	}

	public String getEventKey()
	{
		return eventKey;
	}

	public void setEventKey(String eventKey)
	{
		this.eventKey = eventKey;
	}

	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getPrecision()
	{
		return precision;
	}

	public void setPrecision(String precision)
	{
		this.precision = precision;
	}

	public String getScanType()
	{
		return scanType;
	}

	public void setScanType(String scanType)
	{
		this.scanType = scanType;
	}

	public String getScanResult()
	{
		return scanResult;
	}

	public void setScanResult(String scanResult)
	{
		this.scanResult = scanResult;
	}

	public String getMenuId()
	{
		return menuId;
	}

	public void setMenuId(String menuId)
	{
		this.menuId = menuId;
	}
	
	
}

