package dswork.wx.api;

import java.util.List;

import dswork.wx.model.WxCode; 
import dswork.wx.model.user.User;
import dswork.wx.model.user.UserGet;
import dswork.wx.model.user.UserList;

public class UserApi extends API
{
	private UserApi()
	{
	}

	/**
	 * 获取用户基本信息
	 * @param access_token 调用接口凭证Token中的access_token
	 * @param openid 普通用户的标识，对当前公众号唯一
	 * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return User
	 */
	public static User info(String access_token, String openid, String lang)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/cgi-bin/user/info?access_token=").append(access_token)
			.append("&openid=").append(openid)
			.append("&lang=").append(lang);
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), User.class);
	}

	/**
	 * 设置备注名
	 * @param access_token 调用接口凭证Token中的access_token
	 * @param openid 普通用户的标识，对当前公众号唯一
	 * @param remark 新的备注名，长度必须小于30字符
	 * @return WxCode
	 */
	public static WxCode infoUpdateremark(String access_token, String openid, String remark)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/cgi-bin/user/info/updateremark?access_token=").append(access_token);
		return API.parseObject(API.createHttp().create(sb.toString()).addForm("openid", openid).addForm("remark", remark).connect(), WxCode.class);
	}

	/**
	 * 获取用户列表
	 * @param access_token 调用接口凭证Token中的access_token
	 * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return UserGet
	 */
	public static UserGet get(String access_token, String next_openid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/cgi-bin/user/get?access_token=").append(access_token);
		if(next_openid != null)
		{
			sb.append("&next_openid=").append(next_openid);
		}
		return API.parseObject(API.createHttp().create(sb.toString()).connect(), UserGet.class);
	}

	/**
	 * 批量获取用户基本信息
	 * @param access_token
	 * @param lang	zh-CN
	 * @param openids 最多支持一次拉取100条
	 * @return UserList
	 */
	public static UserList infoBatchget(String access_token, String lang, List<String> openids){
		StringBuilder s = new StringBuilder();
		s.append("{\"user_list\": [");
		for(int i = 0;i < openids.size();i++){
			s.append("{")
			  .append("\"openid\": \"").append(openids.get(i)).append("\",")
			  .append("\"lang\": \"").append(lang).append("\"")
			  .append("}").append(i==openids.size()-1?"":",");
		}
		s.append("]}");

		StringBuilder sb = new StringBuilder();
		sb.append(API.URI_API).append("/cgi-bin/user/info/batchget?access_token=").append(access_token);
		byte[] arr = null;
		try
		{
			arr = s.toString().getBytes("UTF-8");
		}
		catch(Exception e)
		{
			arr = new byte[0];
		}
		return API.parseObject(API.createHttp().create(sb.toString()).setDataBody(arr).connect(), UserList.class);
	}
}
