package dswork.common;

import java.util.List;

import dswork.common.dao.DsCommonDao;
import dswork.common.model.IDsDict;
import dswork.spring.BeanFactory;

// demo
public class DsCommonFactory
{
	private static DsCommonDao getDao(){return (DsCommonDao) BeanFactory.getBean("dsCommonDao");}
	private static DsCommonDao dao = getDao();
	
	public static String getSelect(String name, String selectName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"").append(selectName).append("\" name=\"").append(selectName).append("\">");
		sb.append(getOption(name));
		sb.append("</select>");
		return sb.toString();
	}
	public static String getOption(String name)
	{
		List<IDsDict> list = dao.queryListDict(name, "");
		StringBuilder sb = new StringBuilder();
		for(IDsDict dict : list)
		{
			sb.append("<option value=\"").append(dict.getAlias()).append("\"").append(">").append(dict.getLabel()).append("</option>");
		}
		return sb.toString();
	}
	public static String getCheckbox(String name, String checkboxName)
	{
		List<IDsDict> list = dao.queryListDict(name, "");
		StringBuilder sb = new StringBuilder();
		IDsDict dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<label><input name=\"").append(checkboxName).append("\"");
			sb.append(" type=\"checkbox\" value=\"").append(dict.getAlias()).append("\"");
			sb.append("/>").append(dict.getLabel()).append("</label>");
		}
		sb.append("<input name=\"").append(checkboxName).append("\" type=\"checkbox\" dataType=\"Group\" msg=\"必选\" style=\"display:none;\" />");
		return sb.toString();
	}
	public static String getRadio(String name, String radioName)
	{
		List<IDsDict> list = dao.queryListDict(name, "");
		StringBuilder sb = new StringBuilder();
		IDsDict dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<label><input name=\"").append(radioName).append("\"");
			sb.append(" type=\"radio\" value=\"").append(dict.getAlias()).append("\"");
			sb.append("/>").append(dict.getLabel()).append("</label>");
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
	public static String getOrgJson(Long pid, Integer status)
	{
		return dao.queryListOrg(pid, status).toString();
	}
}
