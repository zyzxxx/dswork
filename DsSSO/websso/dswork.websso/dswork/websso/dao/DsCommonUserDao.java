/**
 * 用户Dao
 */
package dswork.websso.dao;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;
import dswork.websso.model.DsCommonUser;

@Repository
@SuppressWarnings("all")
public class DsCommonUserDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return DsCommonUser.class;
	}

	public int save(DsCommonUser po)
	{
		return executeInsert("insert", po);
	}

	public DsCommonUser get(Long id)
	{
		return (DsCommonUser) executeSelect("select", id);
	}

	public DsCommonUser getByAccount(String account)
	{
		return (DsCommonUser) executeSelect("selectByAccount", account);
	}
}
