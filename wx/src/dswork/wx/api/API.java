package dswork.wx.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import dswork.http.HttpUtil;

/**
 * 依赖于dswork.http
 * @author skey
 */
public class API
{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	public static final String URI_API = "https://api.weixin.qq.com";
	public static final String URI_API_FILE = "http://file.api.weixin.qq.com";
	public static final String URI_MP = "https://mp.weixin.qq.com";
	public static final String URI_OPEN = "https://open.weixin.qq.com";
	public static final String URI_API_MCH = "https://api.mch.weixin.qq.com";
	public static final String P_ACCESS_TOKEN = "access_token";
	public static final String P_ACCESS_TOKEN_AUTHORIZER = "authorizer_access_token";
	
	public static final <T> T parseObject(String json, Class<T> clazz)
	{
		return JSON.parseObject(json, clazz);
	}

	public static final String toJSONString(Object object)
	{
		return JSON.toJSONString(object);
	}
	
	protected static final HttpUtil createHttp()
	{
		return new HttpUtil();
	}
}
