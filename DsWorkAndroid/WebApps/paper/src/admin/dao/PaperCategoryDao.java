package admin.dao;

import gwen.devwork.BaseDao;

import org.springframework.stereotype.Repository;

import admin.entity.PaperCategory;

@Repository("paperCategoryDao")
public class PaperCategoryDao extends BaseDao<PaperCategory,Long>
{
	@Override
	public Class getEntityClass()
	{
		return PaperCategory.class;
	}
}
