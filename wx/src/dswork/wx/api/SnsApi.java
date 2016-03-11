package dswork.wx.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import dswork.wx.model.user.User;
import dswork.http.HttpUtil;
import dswork.wx.model.token.SnsToken;
import dswork.wx.model.token.Token;

/**
 * 微信端用户网页授权
 * @author skey
 */
public class SnsApi extends API
{
	/**
	 * 通过code换取网页授权access_token
	 * @param appid
	 * @param secret
	 * @param code
	 * @return SnsToken
	 */
	public static SnsToken accessToken(String appid, String secret, String code)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/sns/oauth2/access_token?appid=").append(appid)
			.append("&secret=").append(secret)
			.append("&code=").append(code)
			.append("&grant_type=authorization_code");
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), SnsToken.class);
	}

	/**
	 * 生成网页授权 URL
	 * @param appid
	 * @param redirect_uri 自动URLEncoder
	 * @param snsapi_userinfo 是否需要拉取用户信息
	 * @param state 可以为空
	 * @param component_appid 第三方平台开发，可以为空。
	 * @return String
	 */
	public static String connectCodeURL(String appid, String redirect_uri, boolean snsapi_userinfo, String state, String component_appid)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append(API.URI_OPEN).append("/connect/oauth2/authorize?appid=").append(appid)
				.append("&redirect_uri=").append(URLEncoder.encode(redirect_uri, "UTF-8"))
				.append("&response_type=").append("code")
				.append("&scope=").append(snsapi_userinfo ? "snsapi_userinfo" : "snsapi_base")
				.append("&state=").append(state == null ? "" : state);
			if(component_appid != null)
			{
				sb.append("&component_appid=").append(component_appid);
			}
			sb.append("#wechat_redirect");
		}
		catch(Exception e)
		{
		}
		return sb.toString();
	}


	/**
	 * 生成网页授权 URL二维码
	 * @param appid
	 * @param redirect_uri 自动URLEncoder
	 * @param snsapi_userinfo 是否需要拉取用户信息
	 * @param state 可以为空
	 * @param component_appid 第三方平台开发，可以为空。
	 * @return String
	 */
	public static String connectCodeQrURL(String appid, String redirect_uri, boolean snsapi_userinfo, String state, String component_appid)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append(API.URI_OPEN).append("/connect/qrconnect?appid=").append(appid)
				.append("&redirect_uri=").append(URLEncoder.encode(redirect_uri, "UTF-8"))
				.append("&response_type=").append("code")
				.append("&scope=").append("snsapi_login")
				.append("&state=").append(state == null ? "" : state);
			if(component_appid != null)
			{
				sb.append("&component_appid=").append(component_appid);
			}
			sb.append("#wechat_redirect");
		}
		catch(Exception e)
		{
		}
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 通过code换取网页授权access_token (第三方平台开发)
	 * @param appid
	 * @param code
	 * @param component_appid 服务开发方的appid
	 * @param component_access_token 服务开发方的access_token
	 * @return
	 */
	public static SnsToken oauth2ComponentAccessToken(String appid, String code, String component_appid, String component_access_token)
	{
		HttpUtil http = new HttpUtil();
		http.create(API.URI_API + "/cgi-bin/token").addForm("grant_type", "client_credential").addForm("appid", appid).addForm("secret", secret);
		return API.parseObject(http.connect(), Token.class);
		HttpUriRequest httpUriRequest = RequestBuilder.post().setUri(BASE_URI + "/sns/oauth2/component/access_token").addParameter("appid", appid).addParameter("code", code).addParameter("grant_type", "authorization_code").addParameter("component_appid", component_appid)
				.addParameter("component_access_token", component_access_token).build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, SnsToken.class);
	}

	/**
	 * 刷新access_token
	 * @param appid
	 * @param refresh_token
	 * @return
	 */
	public static SnsToken oauth2RefreshToken(String appid, String refresh_token)
	{
		HttpUtil http = new HttpUtil();
		http.create(API.URI_API + "/cgi-bin/token").addForm("grant_type", "client_credential").addForm("appid", appid).addForm("secret", secret);
		return API.parseObject(http.connect(), Token.class);
		HttpUriRequest httpUriRequest = RequestBuilder.post().setUri(BASE_URI + "/sns/oauth2/refresh_token").addParameter("appid", appid).addParameter("refresh_token", refresh_token).addParameter("grant_type", "refresh_token").build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, SnsToken.class);
	}

	/**
	 * 刷新access_token (第三方平台开发)
	 * @param appid
	 * @param refresh_token
	 * @param component_appid 服务开发商的appid
	 * @param component_access_token 服务开发方的access_token
	 * @return
	 */
	public static SnsToken oauth2ComponentRefreshToken(String appid, String refresh_token, String component_appid, String component_access_token)
	{
		HttpUtil http = new HttpUtil();
		http.create(API.URI_API + "/cgi-bin/token").addForm("grant_type", "client_credential").addForm("appid", appid).addForm("secret", secret);
		return API.parseObject(http.connect(), Token.class);
		HttpUriRequest httpUriRequest = RequestBuilder.post().setUri(BASE_URI + "/sns/oauth2/component/refresh_token").addParameter("appid", appid).addParameter("refresh_token", refresh_token).addParameter("grant_type", "refresh_token").addParameter("component_appid", component_appid)
				.addParameter("component_access_token", component_access_token).build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, SnsToken.class);
	}

	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * @param access_token
	 * @param openid
	 * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return
	 */
	public static User userinfo(String access_token, String openid, String lang)
	{
		HttpUtil http = new HttpUtil();
		http.create(API.URI_API + "/cgi-bin/token").addForm("grant_type", "client_credential").addForm("appid", appid).addForm("secret", secret);
		return API.parseObject(http.connect(), Token.class);
		HttpUriRequest httpUriRequest = RequestBuilder.post().setUri(BASE_URI + "/sns/userinfo").addParameter(PARAM_ACCESS_TOKEN, access_token).addParameter("openid", openid).addParameter("lang", lang).build();
		return LocalHttpClient.executeJsonResult(httpUriRequest, User.class);
	}

}
