package common.cms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dswork.cms.model.DsCmsPermission;
import dswork.cms.model.DsCmsSite;
import dswork.spring.BeanFactory;

public class CmsPermission
{
	private CmsPermission()
	{
	}

	private static Map<Long, Map<Long, Map<String, Map<String, String>>>> siteMap = new HashMap<Long, Map<Long, Map<String, Map<String, String>>>>();

	public static void refresh()
	{
		Map<Long, Map<Long, Map<String, Map<String, String>>>> map1 = new HashMap<Long, Map<Long, Map<String, Map<String, String>>>>();
		CmsPermissionDao dao = (CmsPermissionDao) BeanFactory.getBean(CmsPermissionDao.class);
		List<DsCmsSite> siteList = dao.queryListSite();
		for(DsCmsSite site : siteList)
		{
//			List<DsCmsCategory> categoryList = dao.queryListCategory(site.getId());
			List<DsCmsPermission> permissionList = dao.queryListPermission(site.getId());
			Map<Long, Map<String, Map<String, String>>> map2 = new HashMap<Long, Map<String, Map<String, String>>>();
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

	private static void refreshMap3(Map<Long, Map<String, Map<String, String>>> map2, List<String> idList, String key, DsCmsPermission permission)
	{
		for(String id : idList)
		{
			try
			{
				Long _id = Long.parseLong(id);
				Map<String, Map<String, String>> map3 = map2.get(_id);
				if(map3 == null)
				{
					map3 = new HashMap<String, Map<String, String>>();
					map2.put(_id, map3);
				}
				Map<String, String> map4 = map3.get(key);
				if(map4 == null)
				{
					map4 = new HashMap<String, String>();
					map3.put(key, map4);
				}
				map4.put(permission.getAccount(), "true");
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
			&& siteMap.get(siteid).get(categoryid).get("editall").get(account) != null;
	}

	public static boolean checkEditown(long siteid, long categoryid, String account)
	{
		if(checkCategory(siteid, categoryid))
		{
			return true;
		}
		return siteMap.get(siteid).get(categoryid).get("editown") != null
			&& siteMap.get(siteid).get(categoryid).get("editown").get(account) != null;
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
			return true;
		}
		return siteMap.get(siteid).get(categoryid).get("audit") != null
			&& siteMap.get(siteid).get(categoryid).get("audit").get(account) != null;
	}

	public static boolean checkPublish(long siteid, long categoryid, String account)
	{
		if(checkCategory(siteid, categoryid))
		{
			return true;
		}
		return siteMap.get(siteid).get(categoryid).get("publish") != null
			&& siteMap.get(siteid).get(categoryid).get("publish").get(account) != null;
	}
}
