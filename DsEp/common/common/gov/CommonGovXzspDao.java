package common.gov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.MyBatisDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;

@Repository
@SuppressWarnings("all")
public class CommonGovXzspDao extends MyBatisDao
{
	@Override
	public Class getEntityClass()
	{
		return CommonGovXzspDao.class;
	}
	
	public int executeInsert(Map map)
	{
		return getSqlSessionTemplate().insert(getStatementName("insert"), map);
	}

}