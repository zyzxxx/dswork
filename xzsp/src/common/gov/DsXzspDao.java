package common.gov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dswork.core.db.BaseDao;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;

@Repository
@SuppressWarnings("all")
public class DsXzspDao extends BaseDao<DsXzsp, Long>
{
	@Override
	public Class getEntityClass()
	{
		return DsXzsp.class;
	}

	@Deprecated
	public int save(DsXzsp entity)
	{
		return 0;
	}
	
	@Deprecated
	public int delete(Long primaryKey)
	{
		return 0;
	}
}