package dswork.wx.api;

import dswork.wx.model.token.SnsToken;
import dswork.wx.model.user.SnsUser;

/**
 * 微信端用户网页授权
 * @author skey
 */
public class SnsApi extends API
{
	/**
	 * 生成网页授权 URL，用户允许授权后，将会重定向到redirect_uri的网址上，并且带上code, state
	 * @param appid
	 * @param redirect_uri 自动URLEncoder
	 * @param snsapi_userinfo 是否需要拉取用户信息
	 * @param state 可以为空
	 * @return String
	 */
	public static String connectOauth2Code(String appid, String redirect_uri, boolean snsapi_userinfo, String state)
	{
		return connectOauth2Code(appid, redirect_uri, snsapi_userinfo, state, null);
	}
	/**
	 * 生成网页授权 URL，用户允许授权后，将会重定向到redirect_uri的网址上，并且带上code, state以及appid，若用户禁止授权，则仅会带上state参数
	 * @param appid
	 * @param redirect_uri 自动URLEncoder
	 * @param snsapi_userinfo 是否需要拉取用户信息
	 * @param state 可以为空
	 * @param component_appid 服务方的appid，在申请创建公众号服务成功后，可在公众号服务详情页找到，没有为null
	 * @return String
	 */
	public static String connectOauth2Code(String appid, String redirect_uri, boolean snsapi_userinfo, String state, String component_appid)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append(API.URI_OPEN).append("/connect/oauth2/authorize?appid=").append(appid)
				.append("&redirect_uri=").append(java.net.URLEncoder.encode(redirect_uri, "UTF-8"))
				.append("&response_type=").append("code")
				.append("&scope=").append(snsapi_userinfo ? "snsapi_userinfo" : "snsapi_base");
			if(state != null)
			{
				sb.append("&state=").append(state == null ? "" : state);
			}
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
	 * @param component_appid 服务方的appid，在申请创建公众号服务成功后，可在公众号服务详情页找到，没有为null
	 * @return String
	 */
	public static String connectQrCode(String appid, String redirect_uri, boolean snsapi_userinfo, String state, String component_appid)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			sb.append(API.URI_OPEN).append("/connect/qrconnect?appid=").append(appid)
				.append("&redirect_uri=").append(java.net.URLEncoder.encode(redirect_uri, "UTF-8"))
				.append("&response_type=").append("code")
				.append("&scope=").append("snsapi_login");
			if(state != null)
			{
				sb.append("&state=").append(state == null ? "" : state);
			}
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
	 * 通过code换取网页授权access_token
	 * @param appid
	 * @param secret
	 * @param code
	 * @return SnsToken
	 */
	public static SnsToken oauth2AccessToken(String appid, String secret, String code)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/sns/oauth2/access_token?appid=").append(appid)
			.append("&secret=").append(secret)
			.append("&code=").append(code)
			.append("&grant_type=authorization_code");
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), SnsToken.class);
	}

	/**
	 * 刷新access_token
	 * @param appid 公众号的唯一标识
	 * @param refresh_token 填写通过access_token获取到的refresh_token参数
	 * @return SnsToken
	 */
	public static SnsToken oauth2RefreshToken(String appid, String refresh_token)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/sns/oauth2/refresh_token?appid=").append(appid)
			.append("&grant_type=refresh_token")
			.append("&refresh_token=").append(refresh_token);
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), SnsToken.class);
	}
	
	/**
	 * 代公众号发起网页授权，通过code换取网页授权access_token (第三方平台开发)
	 * 需要注意的是，由于安全方面的考虑，对访问该链接的客户端有IP白名单的要求。
	 * @param appid
	 * @param code
	 * @param component_appid 服务开发方的appid
	 * @param component_access_token 服务开发方的access_token
	 * @return
	 */
	public static SnsToken oauth2ComponentAccessToken(String appid, String code, String component_appid, String component_access_token)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/sns/oauth2/component/access_token?appid=").append(appid)
			.append("&code=").append(code)
			.append("&grant_type=authorization_code")
			.append("&component_appid=").append(component_appid)
			.append("&component_access_token=").append(component_access_token);
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), SnsToken.class);
	}

	/**
	 * 刷新access_token (第三方平台开发)
	 * @param appid 公众号的appid
	 * @param refresh_token 填写通过access_token获取到的refresh_token参数
	 * @param component_appid 服务开发商的appid
	 * @param component_access_token 服务开发方的access_token
	 * @return
	 */
	public static SnsToken oauth2ComponentRefreshToken(String appid, String refresh_token, String component_appid, String component_access_token)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/sns/oauth2/component/refresh_token?appid=").append(appid)
			.append("&grant_type=refresh_token")
			.append("&refresh_token=").append(refresh_token)
			.append("&component_appid=").append(component_appid)
			.append("&component_access_token=").append(component_access_token);
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), SnsToken.class);
	}

	/**
	 * 通过网页授权access_token获取用户基本信息（需授权作用域为snsapi_userinfo）
	 * @param access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openid 用户的唯一标识
	 * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return User
	 */
	public static SnsUser userinfo(String access_token, String openid, String lang)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/sns/userinfo?access_token=").append(access_token)
			.append("&openid=").append(openid)
			.append("&lang=").append(lang);
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), SnsUser.class);
	}
}
