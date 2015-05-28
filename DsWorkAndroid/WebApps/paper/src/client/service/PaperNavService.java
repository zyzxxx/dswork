package client.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gwen.devwork.BaseDao;
import gwen.devwork.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import client.dao.PaperCategoryDao;
import client.dao.PaperModelDao;
import client.entity.PaperCategory;
import client.entity.PaperModel;

@Service("paperNavService")
public class PaperNavService extends BaseService<PaperCategory,Long>
{
	@Autowired
	private PaperCategoryDao paperCategoryDao;
	@Autowired
	private PaperModelDao paperModelDao;

	@Override
	public BaseDao<PaperCategory,Long> getEntityDao() {
		return paperCategoryDao;
	}
	
	public String getNavJson()
	{
		Map m = new HashMap();
		List<PaperCategory> pcList = paperCategoryDao.get(m);
		List<PaperModel> pmList = paperModelDao.get(m);
		for(PaperCategory pc : pcList)
		{
			for(PaperModel pm : pmList)
			{
				if(pc.getId() == pm.getPid())
				{
					pc.addPaperModel(pm);
				}
			}
		}
		Gson gson = new Gson();  
		return gson.toJson(pcList);
	}
}
