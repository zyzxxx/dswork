package dswork.cms.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;

@Repository
@SuppressWarnings("all")
public class DsCmsUserDao extends MyBatisDao
{
	@Override
	protected Class getEntityClass()
	{
		return DsCmsUserDao.class;
	}

	public Page<Map<String, Object>> queryPageCommonUser(PageRequest pr)
	{
		return queryPage("queryCommonUser", pr, "queryCommonUserCount", pr);
	}

	public Page<Map<String, Object>> queryPageEpUser(PageRequest pr)
	{
		return queryPage("queryEpUser", pr, "queryEpUserCount", pr);
	}

	public Page<Map<String, Object>> queryPagePersonUser(PageRequest pr)
	{
		return queryPage("queryPersonUser", pr, "queryPersonUserCount", pr);
	}
}
