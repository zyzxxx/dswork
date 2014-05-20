package common.dict;

import java.util.HashMap;
import java.util.List;

import dswork.common.dao.DsCommonDictDataDao;
import dswork.common.model.DsCommonDictData;
import dswork.spring.BeanFactory;

public class DictFactory
{
	public static DsCommonDictDataDao getDsDictDataDao(){return (DsCommonDictDataDao) BeanFactory.getBean("dsDictDataDao");}
	
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
		List<DsCommonDictData> list = getDsDictDataDao().queryList(null, name, new HashMap<String, Object>());
		StringBuilder sb = new StringBuilder();
		for(DsCommonDictData dict : list)
		{
			sb.append("<option value=\"").append(dict.getAlias()).append("\"").append(">").append(dict.getLabel()).append("</option>");
		}
		return sb.toString();
	}
	
	public static String Checkbox(String name, String checkboxName)
	{
		List<DsCommonDictData> list = getDsDictDataDao().queryList(null, name, new HashMap<String, Object>());
		StringBuilder sb = new StringBuilder();
		DsCommonDictData dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<input name=\"").append(checkboxName).append("\"");
			sb.append(" id=\"chk_").append(dict.getAlias()).append("\"");
			sb.append(" type=\"checkbox\" value=\"").append(dict.getAlias()).append("\"");
			if(i == list.size() - 1)
			{
				sb.append(" dataType=\"Group\" msg=\"必选\" alertMsg=\"" + name + ",必选\"");
			}
			sb.append("/>").append("<label for=\"chk_").append(dict.getAlias()).append("\">").append(dict.getLabel()).append("</label>");
		}
		return sb.toString();
	}
	
	public static String Radio(String name, String radioName)
	{
		List<DsCommonDictData> list = getDsDictDataDao().queryList(null, name, new HashMap<String, Object>());
		StringBuilder sb = new StringBuilder();
		DsCommonDictData dict;
		for(int i = 0; i < list.size(); i++)
		{
			dict = list.get(i);
			sb.append("<input name=\"").append(radioName).append("\"");
			sb.append(" id=\"rdo_").append(dict.getAlias()).append("\"");
			sb.append("\" type=\"radio\" value=\"").append(dict.getAlias()).append("\"");
			if(i == list.size() - 1)
			{
				sb.append(" dataType=\"Group\" msg=\"必选\" alertMsg=\"" + name + ",必选\"");
			}
			sb.append("/>").append("<label for=\"rdo_").append(dict.getAlias()).append("\">").append(dict.getLabel()).append("</label>");
		}
		return sb.toString();
	}
}
