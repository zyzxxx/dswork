package dswork.common;

import java.util.List;

import dswork.common.dao.DsCommonDao;
import dswork.common.model.IDict;
import dswork.spring.BeanFactory;

public class DsCommonFactory
{
	private static DsCommonDao getDao(){return (DsCommonDao) BeanFactory.getBean("dsCommonDao");}
	private static DsCommonDao dao = getDao();

	public static String getSelect(String name, String selectName)
	{
		return getSelect(name, selectName, "");
	}
	public static String getSelect(String name, String selectName, String parentAlias)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"").append(selectName).append("\" name=\"").append(selectName).append("\">");
		sb.append(getOption(name, parentAlias));
		sb.append("</select>");
		return sb.toString();
	}
	public static String getOption(String name, String parentAlias)
	{
		List<IDict> list = dao.queryListDict(name, parentAlias);
		StringBuilder sb = new StringBuilder();
		for(IDict dict : list)
		{
			sb.append("<option value=\"").append(dict.getAlias()).append("\"").append(">").append(dict.getLabel()).append("</option>");
		}
		return sb.toString();
	}
	public static String getCheckbox(String name, String checkboxName)
	{
		List<IDict> list = dao.queryListDict(name, "");
		StringBuilder sb = new StringBuilder();
		IDict dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<label><input name=\"").append(checkboxName).append("\" type=\"checkbox\" value=\"").append(dict.getAlias()).append("\"/>").append(dict.getLabel()).append("</label>");
		}
		sb.append("<input name=\"").append(checkboxName).append("\" type=\"checkbox\" dataType=\"Group\" msg=\"必选\" style=\"display:none;\" />");
		return sb.toString();
	}
	public static String getRadio(String name, String radioName)
	{
		List<IDict> list = dao.queryListDict(name, "");
		StringBuilder sb = new StringBuilder();
		IDict dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<label><input name=\"").append(radioName).append("\" type=\"radio\" value=\"").append(dict.getAlias()).append("\"/>").append(dict.getLabel()).append("</label>");
		}
		sb.append("<input name=\"").append(radioName).append("\" type=\"radio\" dataType=\"Group\" msg=\"必选\" style=\"display:none;\" />");
		return sb.toString();
	}
	
	/**
	 * 获取指定节点的列表数据
	 * @param name 字典分类名
	 * @param parentAlias 上级标识，当alias为null时获取全部节点数据，当alias为""时获取根节点数据
	 * @return String
	 */
	public static String getDictJson(String name, String parentAlias)
	{
		return dao.queryListDict(name, parentAlias).toString();
	}
	
	/**
	 * 根据上级组织机构主键取得列表数据
	 * @param pid 上级组织机构主键
	 * @param status 0-2为指定分类（2单位，1部门，0岗位），超出0-2范围则不过滤
	 * @return String
	 */
	public static String getOrgJson(Long pid, Integer status)
	{
		return dao.queryListOrg(pid, status).toString();
	}
}
