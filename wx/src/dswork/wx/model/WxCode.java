package dswork.wx.model;

/**
 * 微信请求状态数据
 * @author skey
 */
public class WxCode
{
	private static final String SuccessCode = "0";
	private String errcode;
	private String errmsg;

	public String getErrcode()
	{
		return errcode;
	}

	public void setErrcode(String errcode)
	{
		this.errcode = errcode;
	}

	public String getErrmsg()
	{
		return errmsg;
	}

	public void setErrmsg(String errmsg)
	{
		this.errmsg = errmsg;
	}

	public boolean isSuccess()
	{
		return errcode == null || errcode.isEmpty() || errcode.equals(SuccessCode);
	}
}
