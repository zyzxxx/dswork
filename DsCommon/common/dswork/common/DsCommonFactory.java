package dswork.common;

import java.util.List;

import dswork.common.dao.DsCommonDao;
import dswork.common.imodel.IDsDict;
import dswork.spring.BeanFactory;

public class DsCommonFactory
{
	public static DsCommonDao getDsDictDataDao(){return (DsCommonDao) BeanFactory.getBean("dsCommonDao");}
	
	public static String Select(String name, String selectName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"").append(selectName).append("\" name=\"").append(selectName).append("\">");
		sb.append(Option(name));
		sb.append("</select>");
		return sb.toString();
	}
	
	public static String Option(String name)
	{
		List<IDsDict> list = getDsDictDataDao().queryList(name, "");
		StringBuilder sb = new StringBuilder();
		for(IDsDict dict : list)
		{
			sb.append("<option value=\"").append(dict.getAlias()).append("\"").append(">").append(dict.getLabel()).append("</option>");
		}
		return sb.toString();
	}
	
	public static String Checkbox(String name, String checkboxName)
	{
		List<IDsDict> list = getDsDictDataDao().queryList(name, "");
		StringBuilder sb = new StringBuilder();
		IDsDict dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<input name=\"").append(checkboxName).append("\"");
			sb.append(" id=\"chk_").append(dict.getAlias()).append("\"");
			sb.append(" type=\"checkbox\" value=\"").append(dict.getAlias()).append("\"");
			sb.append("/>").append("<label for=\"chk_").append(dict.getAlias()).append("\">").append(dict.getLabel()).append("</label>");
		}
		sb.append("<input name=\"").append(checkboxName).append("\" type=\"checkbox\" dataType=\"Group\" msg=\"必选\" style=\"display:none;\" />");
		return sb.toString();
	}
	
	public static String Radio(String name, String radioName)
	{
		List<IDsDict> list = getDsDictDataDao().queryList(name, "");
		StringBuilder sb = new StringBuilder();
		IDsDict dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<input name=\"").append(radioName).append("\"");
			sb.append(" id=\"rdo_").append(dict.getAlias()).append("\"");
			sb.append(" type=\"radio\" value=\"").append(dict.getAlias()).append("\"");
			sb.append("/>").append("<label for=\"rdo_").append(dict.getAlias()).append("\">").append(dict.getLabel()).append("</label>");
		}
		sb.append("<input name=\"").append(radioName).append("\" type=\"radio\" dataType=\"Group\" msg=\"必选\" style=\"display:none;\" />");
		return sb.toString();
	}
	
	public static String Json(String name, String parentAlias)
	{
		return getDsDictDataDao().queryList(name, parentAlias).toString();
	}
}
