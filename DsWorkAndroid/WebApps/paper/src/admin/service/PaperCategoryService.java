package admin.service;

import gwen.devwork.BaseDao;
import gwen.devwork.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.PaperCategoryDao;
import admin.entity.PaperCategory;

@Service("paperCategoryService")
public class PaperCategoryService extends BaseService<PaperCategory,Long>
{
	@Autowired
	private PaperCategoryDao paperCategoryDao;

	@Override
	public BaseDao<PaperCategory,Long> getEntityDao() {
		return paperCategoryDao;
	}
}
