/**
 * 栏目Service
 */
package dswork.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.cms.dao.DsCmsCategoryDao;
import dswork.cms.dao.DsCmsPageDao;
import dswork.cms.dao.DsCmsSiteDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsSite;
import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.page.PageRequest;

@Service
@SuppressWarnings("all")
public class DsCmsCategoryService extends BaseService<DsCmsCategory, Long>
{
	@Autowired
	private DsCmsCategoryDao categoryDao;
	@Autowired
	private DsCmsSiteDao siteDao;
	@Autowired
	private DsCmsPageDao pageDao;

	@Override
	protected EntityDao getEntityDao()
	{
		return categoryDao;
	}
	
	public int save(DsCmsCategory po)
	{
		if(po.getScope() != 2)
		{
			categoryDao.save(po);
			po.setUrl("/a/" + po.getId() + "/index.html");
			return categoryDao.update(po);
		}
		else
		{
			return categoryDao.save(po);
		}
	}
	
	public int update(DsCmsCategory po)
	{
		if(po.getScope() != 2)
		{
			po.setUrl("/a/" + po.getId() + "/index.html");
		}
		categoryDao.update(po);
		categoryDao.updateScope(po);
		return 1;
	}

	@Override
	@Deprecated
	public void deleteBatch(Long[] primaryKeys)
	{
	}

	/**
	 * 更新排序
	 * @param ids 主键数组
	 * @param seqArr 排序位置数组
	 * @param siteid 站点ID
	 */
	public void updateSeq(long[] idArr, int[] seqArr, long siteid)
	{
		for(int i = 0; i < idArr.length && i < seqArr.length; i++)
		{
			categoryDao.updateSeq(idArr[i], seqArr[i], siteid);
		}
	}

	/**
	 * 获得节点的子节点个数
	 * @param pid 上级栏目主键
	 * @return int
	 */
	public int getCountByPid(long pid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		return categoryDao.queryCount(new PageRequest(map));
	}

	/**
	 * 获得栏目节点的内容数量
	 * @param siteid 站点主键
	 * @param categoryid 栏目主键
	 * @return int
	 */
	public int getCountByCategoryid(long siteid, long categoryid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteid", siteid);
		map.put("categoryid", categoryid);
		return pageDao.queryCount(new PageRequest(map));
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public List<DsCmsSite> queryListSite(Map<String, Object> map)
	{
		return siteDao.queryList(map);
	}
}
