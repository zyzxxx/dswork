package dswork.wx.api;

import dswork.wx.model.token.Token;

public class TokenApi extends API
{
	private TokenApi()
	{
	}

	/**
	 * 获取调用接口凭证access_token
	 * @param appid
	 * @param secret
	 * @return Token
	 */
	public static Token token(String appid, String secret)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/cgi-bin/token?grant_type=client_credential&appid=").append(appid).append("&secret=").append(secret);
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), Token.class);
	}
}
