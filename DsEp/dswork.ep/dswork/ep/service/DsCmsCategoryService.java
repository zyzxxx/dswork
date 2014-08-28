/**
 * 栏目Service
 */
package dswork.ep.service;

import java.util.List;

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

	public DsCmsSite getSite(Long siteid)
	{
		return (DsCmsSite) siteDao.get(siteid);
	}

	public List<DsCmsSite> queryListSite(PageRequest rq)
	{
		return siteDao.queryList(rq);
	}
}
