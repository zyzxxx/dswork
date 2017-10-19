package dswork.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dswork.cms.model.DsCmsCategory;

public class DsCmsUtil
{
	private static void categorySettingList(DsCmsCategory m, List<DsCmsCategory> list)
	{
		int size = m.getList().size();
		for(int i = 0; i < size; i++)
		{
			DsCmsCategory n = m.getList().get(i);
			n.setLevel(m.getLevel() + 1);
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
			m.setLevel(0);
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
	public static List<DsCmsCategory> categoryAccess(List<DsCmsCategory> list, String ids)
	{
		Map<Long, DsCmsCategory> map = new HashMap<Long, DsCmsCategory>();
		List<DsCmsCategory> _list = new ArrayList<DsCmsCategory>();
		for(DsCmsCategory c : list)
		{
			if(ids.indexOf("," + c.getId() + ",") != -1)
			{
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
}
