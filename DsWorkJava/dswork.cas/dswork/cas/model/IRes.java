/**
 * @描述：系统资源
 */
package dswork.cas.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class IRes implements Serializable
{
	private static final long serialVersionUID = 1L;
	// 主键
	private Long id = 0L;
	// 所属功能ID
	private Long funcid = 0L;
	// 所属系统ID
	private Long systemid = 0L;
	// 资源对应的URL
	private String url = "";
	// 排序
	private Integer seq = 0;
	// 参数(格式:name1=value2,name2=value2...)
	private String param = "";
	// 对param进行key=value切分后的集合
	private Map<String, String> paramMap = new HashMap<String, String>();

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setFuncid(Long funcid)
	{
		this.funcid = funcid;
	}

	public Long getFuncid()
	{
		return this.funcid;
	}

	public void setSystemid(Long systemid)
	{
		this.systemid = systemid;
	}

	public Long getSystemid()
	{
		return this.systemid;
	}

	public void setUrl(String url)
	{
		this.url = String.valueOf(url).trim();
	}

	public String getUrl()
	{
		return this.url;
	}

	public void setSeq(Integer seq)
	{
		this.seq = seq;
	}

	public Integer getSeq()
	{
		return this.seq;
	}

	public String getParam()
	{
		return this.param;
	}

	public String getParamByKey(String key)
	{
		return paramMap != null ? paramMap.get(key) : null;
	}

	public String[] getParamKeys()
	{
		return paramMap.keySet().toArray(new String[paramMap.size()]);
	}

	/**
	 * @param param k=v,k2=v2
	 */
	public void setParam(String param)
	{
		this.param = String.valueOf(param).trim();
		paramMap.clear();
		if(this.param.length() > 0)
		{
			String[] params = this.param.split(",");
			for (String str : params)
			{
				if (str.indexOf("=") > 0)
				{
					String[] keyValue = str.split("=");
					paramMap.put(keyValue[0].trim(), keyValue[1].trim());
				}
			}
		}
	}

	public Map<String, String> getParamMap()
	{
		return paramMap;
	}
}
