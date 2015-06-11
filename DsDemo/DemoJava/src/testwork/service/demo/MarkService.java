/**
 * MyBatis样例Service
 */
package testwork.service.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dswork.core.db.EntityDao;
import dswork.core.db.BaseService;
import dswork.core.page.Page;
import dswork.core.page.PageRequest;
import testwork.model.Demo;
import testwork.model.Mark;
import testwork.dao.DemoDao;
import testwork.dao.MarkDao;

@Service
@SuppressWarnings("all")
public class MarkService
{
	@Autowired
	private DemoDao demoDao;
	@Autowired
	private MarkDao markDao;

	public Page<Demo> queryPage(PageRequest pageRequest)
	{
		return demoDao.queryPage(pageRequest);
	}

	public Demo get(Long primaryKey)
	{
		return (Demo) demoDao.get(primaryKey);
	}
	
	public List<Mark> queryListMark(Long demoid)
	{
		PageRequest rq = new PageRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("demoid", demoid);
		rq.setFilters(map);
		return markDao.queryList(rq);
	}
	public int save(List<Mark> list)
	{
		for(Mark m : list)
		{
			markDao.save(m);
		}
		return 1;
	}
}
