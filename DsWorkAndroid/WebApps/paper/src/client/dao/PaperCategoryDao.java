package client.dao;

import gwen.devwork.BaseDao;

import org.springframework.stereotype.Repository;

import client.entity.PaperCategory;

@Repository("clientPaperCategoryDao")
public class PaperCategoryDao extends BaseDao<PaperCategory,Long>
{
	@Override
	public Class getEntityClass()
	{
		return PaperCategory.class;
	}
}
