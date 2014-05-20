/**
 * 功能Model
 */
package dswork.common.model;

public class DsCommonFunc
{
	// 主键
	private Long id = 0L;
	// 上级ID(本表)
	private Long pid = 0L;
	// 所属系统ID
	private Long systemid = 0L;
	// 名称
	private String name = "";
	// 标识
	private String alias = "";
	// 对应的URI
	private String uri = "";
	// 显示图标
	private String img = "";
	// 状态(0不是菜单,1菜单,不是菜单不能添加下级)
	private Integer status = 2;
	// 排序
	private Integer seq = 0;
	// 扩展信息
	private String memo = "";
	// 对Role设置Func时使用
	boolean checked = false;
	
	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setPid(Long pid)
	{
		this.pid = (pid == null || pid <= 0) ? 0 : pid;
	}

	public Long getPid()
	{
		return this.pid;
	}

	public void setSystemid(Long systemid)
	{
		this.systemid = (systemid == null || systemid <= 0) ? 0 : systemid;
	}

	public Long getSystemid()
	{
		return this.systemid;
	}

	public void setName(String name)
	{
		this.name = String.valueOf(name).trim();
	}

	public String getName()
	{
		return this.name;
	}

	public void setAlias(String alias)
	{
		this.alias = String.valueOf(alias).trim();
	}

	public String getAlias()
	{
		return this.alias;
	}

	public void setUri(String uri)
	{
		this.uri = String.valueOf(uri).trim();
	}

	public String getUri()
	{
		return this.uri;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public String getImg()
	{
		return this.img;
	}

	public void setSeq(Integer seq)
	{
		this.seq = seq;
	}

	public Integer getSeq()
	{
		return this.seq;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getMemo()
	{
		return this.memo;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = (status == null || status.intValue() != 1) ? 0 : 1;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	@Override
	public String toString()
	{
		try
		{
			return new StringBuilder().append("{id:").append(id).append(",pid:").append(pid).append(",status:").append(status).append(",isParent:").append((status == 0)?"false":"true").append(",checked:").append(isChecked()).append(",name:\"").append(name.trim().replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\\\"")).append("\",uri:\"").append(uri.trim().replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\\\"")).append("\"}").toString();
		}
		catch (Exception e)
		{
			return "{id:0,pid:-1,status:0,isParent:true,checked:false,name:\"\",uri:\"\"}";
		}
	}
}
