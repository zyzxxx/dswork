/**
 * DS_WEBSSO_USERDao
 */
package dswork.websso.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import dswork.core.db.BaseDao;
import dswork.websso.model.DsWebssoUser;

@Repository
@SuppressWarnings("all")
public class DsWebssoUserDao extends BaseDao<DsWebssoUser, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsWebssoUser.class;
	}

	public DsWebssoUser getByUseraccount(String useraccount)
	{
		return (DsWebssoUser) executeSelect("selectByUseraccount", useraccount);
	}

	public DsWebssoUser getByOpenid(DsWebssoUser po)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openidqq", po.getOpenidqq());
		map.put("openidalipay", po.getOpenidalipay());
		map.put("openidwechat", po.getOpenidwechat());
		return (DsWebssoUser) executeSelect("selectByOpenid", map);
	}

	public DsWebssoUser getByOpenidqq(String openid)
	{
		return (DsWebssoUser) executeSelect("selectByOpenidqq", openid);
	}

	public DsWebssoUser getByOpenidalipay(String openid)
	{
		return (DsWebssoUser) executeSelect("selectByOpenidalipay", openid);
	}

	public DsWebssoUser getByOpenidwechat(String openid)
	{
		return (DsWebssoUser) executeSelect("selectByOpenidwechat", openid);
	}
}