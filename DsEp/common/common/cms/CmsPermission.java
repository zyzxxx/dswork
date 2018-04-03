package common.cms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.spring.BeanFactory;

public class CmsPermission
{
	private CmsPermission()
	{
	}

	private static Map<Long, Map<Long, Map<String, Set<String>>>> siteMap = new HashMap<Long, Map<Long, Map<String, Set<String>>>>();

	public static void refresh()
	{
		Map<Long, Map<Long, Map<String, Set<String>>>> map1 = new HashMap<Long, Map<Long, Map<String, Set<String>>>>();
		CmsPermissionDao dao = (CmsPermissionDao) BeanFactory.getBean(CmsPermissionDao.class);
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
}
