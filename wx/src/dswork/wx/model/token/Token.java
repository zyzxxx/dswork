package dswork.wx.model.token;

import dswork.wx.model.WxCode;

public class Token extends WxCode
{
	private String access_token;
	private int expires_in;

	public String getAccess_token()
	{
		return this.access_token;
	}

	public void setAccess_token(String access_token)
	{
		this.access_token = access_token;
	}

	public int getExpires_in()
	{
		return this.expires_in;
	}

	public void setExpires_in(int expires_in)
	{
		this.expires_in = expires_in;
	}
}
