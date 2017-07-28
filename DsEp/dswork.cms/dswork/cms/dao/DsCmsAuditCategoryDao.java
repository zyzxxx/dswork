/**
 * 内容Dao
 */
package dswork.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import dswork.cms.model.DsCmsAuditCategory;
import dswork.cms.model.DsCmsCategory;
import dswork.core.db.BaseDao;

@Repository
public class DsCmsAuditCategoryDao extends BaseDao<DsCmsAuditCategory, Long>
{
	@Override
	public Class<?> getEntityClass()
	{
		return DsCmsAuditCategory.class;
	}
	
	public int saveFromCategoryList(List<DsCmsCategory> categoryList)
	{
		int r = 0;
		for(DsCmsCategory c : categoryList)
		{
			if(c.getScope() != 0) //非列表
			{
				DsCmsAuditCategory _c = (DsCmsAuditCategory) super.get(c.getId());
				if(_c == null)
				{
					r++;
					_c = new DsCmsAuditCategory();
					_c.setId(c.getId());
					_c.setSiteid(c.getSiteid());
					_c.setSummary(c.getSummary());
					_c.setMetakeywords(c.getMetakeywords());
					_c.setMetadescription(c.getMetadescription());
					_c.setReleasesource(c.getReleasesource());
					_c.setReleaseuser(c.getReleaseuser());
					_c.setImg(c.getImg());
					_c.setContent(c.getContent());
					_c.setReleasetime(c.getReleasetime());
					_c.setUrl(c.getUrl());
					_c.setStatus(0); //草稿
					super.save(_c);
				}
			}
		}
		return r;
	}
}
