/**
 * 栏目Service
 */
package dswork.ep.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.page.PageRequest;
import dswork.ep.model.DsCmsCategory;
import dswork.ep.model.DsCmsSite;
import dswork.ep.dao.DsCmsCategoryDao;
import dswork.ep.dao.DsCmsSiteDao;

@Service
@SuppressWarnings("all")
public class DsCmsCategoryService extends BaseService<DsCmsCategory, Long>
{
	@Autowired
	private DsCmsCategoryDao catDao;
	@Autowired
	private DsCmsSiteDao siteDao;

	@Override
	protected EntityDao getEntityDao()
	{
		return catDao;
	}

	@Override
	@Deprecated
	public void deleteBatch(Long[] primaryKeys)
	{
	}

	/**
	 * 更新排序
	 * @param ids 主键数组
	 */
	public void updateSeq(long[] idArr, int[] seqArr)
	{
		for(int i = 0; i < idArr.length && i < seqArr.length; i++)
		{
			catDao.updateSeq(idArr[i], seqArr[i]);
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
		return catDao.queryCount(new PageRequest(map));
	}

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public List<DsCmsSite> queryListSite(PageRequest rq)
	{
		return siteDao.queryList(rq);
	}
}
