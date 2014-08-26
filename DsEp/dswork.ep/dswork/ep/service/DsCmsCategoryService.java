/**
 * 栏目Service
 */
package dswork.ep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.ep.model.DsCmsCategory;
import dswork.ep.dao.DsCmsCategoryDao;

@Service
@SuppressWarnings("all")
public class DsCmsCategoryService extends BaseService<DsCmsCategory, Long>
{
	@Autowired
	private DsCmsCategoryDao dao;

	@Override
	protected EntityDao getEntityDao()
	{
		return dao;
	}

	/**
	 * 更新排序
	 * @param ids 主键数组
	 */
	public void updateSeq(long[] idArr, int[] seqArr)
	{
		for (int i = 0; i < idArr.length && i < seqArr.length; i++)
		{
			dao.updateSeq(idArr[i], seqArr[i]);
		}
	}
}
