package admin.service;

import gwen.devwork.BaseDao;
import gwen.devwork.BaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.PaperImageDao;
import admin.entity.PaperImage;

@Service("paperImageService")
public class PaperImageService extends BaseService<PaperImage,Long>
{
	@Autowired
	private PaperImageDao dao;

	@Override
	public BaseDao<PaperImage,Long> getEntityDao() 
	{
		return dao;
	}
	
	public void addBatch(List<PaperImage> poList)
	{
		for(PaperImage po : poList)
		{
			add(po);
		}
	}
	
	public void updSortById(PaperImage po)
	{
		dao.updSortById(po);
	}
	
	public void updSortBatch(Long[] ids, Integer[] sorts)
	{
		for(int i=0; i<ids.length; i++)
		{
			PaperImage po = new PaperImage();
			po.setId(ids[i]);
			po.setSort(sorts[i]);
			dao.updSortById(po);
		}
	}
}
