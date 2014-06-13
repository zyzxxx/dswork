/**
 * DEMOService
 */
package testwork.service.demo;

import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import testwork.model.demo.Demo;
import testwork.dao.demo.DemoDao;

@Service
@SuppressWarnings("unchecked")
public class DemoService extends BaseService<Demo, Long>
{
	@Autowired
	private DemoDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}
	
	public void updBatch(Map m)
	{
		String[] ids_s = m.get("ids").toString().split(",");
		Long[] ids = (Long[])ConvertUtils.convert(ids_s, Long.class);
		for(Long id: ids)
		{
			Demo po = new Demo();
			po.setId(id);
			po.setTitle(m.get("title").toString());
			po.setContent(m.get("content").toString());
			po.setFoundtime(m.get("foundtime").toString());
			dao.update(po);
		}
	}
	
	
}
