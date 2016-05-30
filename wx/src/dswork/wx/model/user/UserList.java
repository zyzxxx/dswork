package dswork.wx.model.user;

import java.util.List;

import dswork.wx.model.WxCode;

public class UserList extends WxCode
{
	private List<SnsUser> user_info_list;

	public List<SnsUser> getUser_info_list()
	{
		return user_info_list;
	}

	public void setUser_info_list(List<SnsUser> user_info_list)
	{
		this.user_info_list = user_info_list;
	}
}
