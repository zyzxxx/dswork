package common.gov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.BaseService;
import dswork.core.db.EntityDao;

@Service
public class DsXzspService extends BaseService<DsXzsp, Long>
{
	@Autowired
	private DsXzspDao dao;

	@Override
	protected EntityDao<DsXzsp, Long> getEntityDao()
	{
		return dao;
	}

	public int updateData(DsXzsp po)
	{
		return dao.updateData(po);
	}
}
