package dswork.cms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dswork.cms.dao.DsCmsUtilDao;
import dswork.cms.model.DsCmsCategory;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.spring.BeanFactory;

public class DsCmsUtil
{
	DsCmsUtil()
	{
	}

	static
	{
		refresh();
	}

	private static Map<Long, Map<Long, Map<String, Set<String>>>> siteMap = new HashMap<Long, Map<Long, Map<String, Set<String>>>>();

	public static void refresh()
	{
		Map<Long, Map<Long, Map<String, Set<String>>>> map1 = new HashMap<Long, Map<Long, Map<String, Set<String>>>>();
		DsCmsUtilDao dao = (DsCmsUtilDao) BeanFactory.getBean(DsCmsUtilDao.class);
		List<DsCmsSite> siteList = dao.queryListSite();
		for(DsCmsSite site : siteList)
		{
//			List<DsCmsCategory> categoryList = dao.queryListCategory(site.getId());
			List<DsCmsPermission> permissionList = dao.queryListPermission(site.getId());
			Map<Long, Map<String, Set<String>>> map2 = new HashMap<Long, Map<String, Set<String>>>();
			for(DsCmsPermission permission : permissionList)
			{
				refreshMap3(map2, Arrays.asList(permission.getEditall().split(",")), "editall", permission);
				refreshMap3(map2, Arrays.asList(permission.getEditown().split(",")), "editown", permission);
				refreshMap3(map2, Arrays.asList(permission.getPublish().split(",")), "publish", permission);
				refreshMap3(map2, Arrays.asList(permission.getAudit().split(",")), "audit", permission);
			}
			if(!map2.isEmpty())
			{
				map1.put(site.getId(), map2);
			}
		}
		siteMap = map1;
	}

	private static void refreshMap3(Map<Long, Map<String, Set<String>>> map2, List<String> idList, String key, DsCmsPermission permission)
	{
		for(String id : idList)
		{
			try
			{
				Long _id = Long.parseLong(id);
				Map<String, Set<String>> map3 = map2.get(_id);
				if(map3 == null)
				{
					map3 = new HashMap<String, Set<String>>();
					map2.put(_id, map3);
				}
				Set<String> set = map3.get(key);
				if(set == null)
				{
					set = new HashSet<String>();
					map3.put(key, set);
				}
				set.add(permission.getAccount());
			}
			catch(NumberFormatException e)
			{
			}
		}
	}

	public static boolean checkCategory(long siteid, long categoryid)
	{
		return siteMap.get(siteid) == null
			|| siteMap.get(siteid).get(categoryid) == null;
	}

	public static boolean checkEditall(long siteid, long categoryid, String account)
	{
		if(checkCategory(siteid, categoryid))
		{
			return true;
		}
		return siteMap.get(siteid).get(categoryid).get("editall") != null
			&& siteMap.get(siteid).get(categoryid).get("editall").contains(account);
	}

	public static boolean checkEditown(long siteid, long categoryid, String account)
	{
		if(checkCategory(siteid, categoryid))
		{
			return true;
		}
		return siteMap.get(siteid).get(categoryid).get("editown") != null
			&& siteMap.get(siteid).get(categoryid).get("editown").contains(account);
	}

	public static boolean checkEdit(long siteid, long categoryid, String account)
	{
		return checkEditown(siteid, categoryid, account)
			|| checkEditall(siteid, categoryid, account);
	}

	public static boolean checkAudit(long siteid, long categoryid, String account)
	{
		if(checkCategory(siteid, categoryid))
		{
			return false;
		}
		return siteMap.get(siteid).get(categoryid).get("audit") != null
			&& siteMap.get(siteid).get(categoryid).get("audit").contains(account);
	}

	public static boolean checkPublish(long siteid, long categoryid, String account)
	{
		if(checkCategory(siteid, categoryid))
		{
			return true;
		}
		return siteMap.get(siteid).get(categoryid).get("publish") != null
			&& siteMap.get(siteid).get(categoryid).get("publish").contains(account);
	}

	private static void categorySettingList(DsCmsCategory m, List<DsCmsCategory> list)
	{
		int size = m.getList().size();
		for(int i = 0; i < size; i++)
		{
			DsCmsCategory n = m.getList().get(i);
			n.setLabel(m.getLabel());
			if(m.getLabel().endsWith("├"))
			{
				n.setLabel(m.getLabel().substring(0, m.getLabel().length() - 2) + "│");
			}
			else if(m.getLabel().endsWith("└"))
			{
				n.setLabel(m.getLabel().substring(0, m.getLabel().length() - 2) + "　");
			}
			n.setLabel(n.getLabel() + "&nbsp;&nbsp;");
			n.setLabel(n.getLabel() + (i == size - 1 ? "└" : "├"));
			list.add(n);
			categorySettingList(n, list);
		}
	}

	/**
	 *  接受整理成树形的栏目list
	 * @param list
	 * @return
	 */
	public static List<DsCmsCategory> categorySettingList(List<DsCmsCategory> list)
	{
		List<DsCmsCategory> _list = new ArrayList<DsCmsCategory>();// 按顺序放入
		for(int i = 0; i < list.size(); i++)
		{
			DsCmsCategory m = list.get(i);
			m.setLabel("");
			_list.add(m);
			categorySettingList(m, _list);
		}
		return _list;
	}

	/**
	 *  接受关系完整的栏目list
	 * @param list
	 * @return
	 */
	public static List<DsCmsCategory> categorySetting(List<DsCmsCategory> list)
	{
		Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
		for(DsCmsCategory m : list)
		{
			map.put(m.getId(), m);
		}
		List<DsCmsCategory> _list = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory m : list)
		{
			if(m.getPid() > 0)
			{
				if(map.get(m.getPid()) != null)
				{
					map.get(m.getPid()).add(m);// 放入节点对应的父节点
				}
			}
			else if(m.getPid() == 0)
			{
				_list.add(m);// 只把根节点放入list
			}
		}
		return categorySettingList(_list);
	}

	/**
	 *  接受关系完整的栏目list和权限字符串
	 * @param list
	 * @param ids
	 * @return
	 */
	public static List<DsCmsCategory> categoryAccess(List<DsCmsCategory> list, String account, DsCmsBaseController controller)
	{
		Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
		List<DsCmsCategory> _list = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory c : list)
		{
			if(controller.checkCategory(c, account))
			{
				c.setEnable(true);
				_list.add(c);
			}
			else
			{
				map.put(c.getId(), c);
			}
		}
		List<DsCmsCategory> __list = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory c : _list)
		{
			__list.add(c);
			DsCmsCategory p = null;
			while(c.getPid() != 0 && (p = map.get(c.getPid())) != null)
			{
				__list.add(p);
				map.remove(p.getId());
				c = p;
			}
		}
		return categorySetting(__list);
	}

	public static List<DsCmsCategory> queryCategory(List<DsCmsCategory> clist, boolean exclude, long excludeId)
	{
		Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
		for(DsCmsCategory m : clist)
		{
			map.put(m.getId(), m);
		}
		List<DsCmsCategory> tlist = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory m : clist)
		{
			if(m.getId() != excludeId)
			{
				if(m.getPid() > 0)
				{
					try
					{
						if(m.getScope() == 0 || exclude)
						{
							map.get(m.getPid()).add(m);// 放入其余节点对应的父节点
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();// 找不到对应的父栏目
					}
				}
				else if(m.getPid() == 0)
				{
					if(m.getScope() == 0 || exclude)
					{
						tlist.add(m);// 只把根节点放入list
					}
				}
			}
		}
		if(excludeId > 0)
		{
			try
			{
				map.get(excludeId).clearList();
			}
			catch(Exception e)
			{
				e.printStackTrace();// 找不到对应的栏目
			}
		}
		List<DsCmsCategory> list = DsCmsUtil.categorySettingList(tlist);
		return list;
	}
}
