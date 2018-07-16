package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.dao.DsCmsSpecialDao;
import dswork.cms.model.DsCmsSite;
import dswork.cms.model.DsCmsSpecial;

@Service
public class DsCmsSpecialService
{
	@Autowired
	private DsCmsSpecialDao dao;
	@Autowired
	private DsCmsSiteDao siteDao;

	public List<DsCmsSpecial> querySpecialList(Long siteid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		return dao.queryList(map);
	}
	
	public void saveUpdateDelete(List<DsCmsSpecial> addList, List<DsCmsSpecial> updList, List<Long> delList)
	{
		for(DsCmsSpecial po : addList)
		{
			dao.save(po);
		}
		for(DsCmsSpecial po : updList)
		{
			dao.update(po);
		}
		for(Long id : delList)
		{
			dao.delete(id);
		}
	}

	@SuppressWarnings("unchecked")
	public List<DsCmsSite> querySiteList(Map<String, Object> map)
	{
		return siteDao.queryList(map);
	}

	public DsCmsSite getSite(long id)
	{
		return (DsCmsSite) siteDao.get(id);
	}
}
