package common.gov;

import common.automatic.AutomaticExecuteXzsp;

import dswork.core.util.TimeUtil;
import MQAPI.AcceptOB;
import MQAPI.ApplicationOB;
import MQAPI.BlockOB;
import MQAPI.CompleteOB;
import MQAPI.PreAcceptOB;
import MQAPI.ReceiveRegOB;
import MQAPI.ResumeOB;
import MQAPI.SubmitOB;
import MQAPI.SupplyAcceptOB;
import MQAPI.SupplyOB;

public class DsXzsp
{
	// 主键
	private Long id = 0L;
	// 申办流水号
	private String sblsh = "";
	// 对象类型0ShenBan，1YuShouLi，2ShouLi，3ShenPi，4BanJie，5TeBieChengXuQiDong，6TeBieChengXuBanJie，7BuJiaoGaoZhi，8BuJiaoShouLi，9LingQuDengJi
	private Integer sptype = 0;
	// 发送状态0待发1已发3信息格式不正确
	private Integer fszt = 0;
	// 发送次数
	private Integer fscs = 0;
	// 发送时间
	private String fssj = "";
	// 序列化对象jdk7或以上
	private String spobject = "";
	// 备注，一般存放发送失败信息
	private String memo = "";

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSblsh()
	{
		return sblsh;
	}

	public void setSblsh(String sblsh)
	{
		this.sblsh = sblsh;
	}

	public Integer getSptype()
	{
		return sptype;
	}

	public void setSptype(Integer sptype)
	{
		this.sptype = sptype;
	}

	public Integer getFszt()
	{
		return fszt;
	}

	public void setFszt(Integer fszt)
	{
		this.fszt = fszt;
	}

	public Integer getFscs()
	{
		return fscs;
	}

	public void setFscs(Integer fscs)
	{
		this.fscs = fscs;
	}

	public String getFssj()
	{
		return fssj;
	}

	public void setFssj(String fssj)
	{
		this.fssj = fssj;
	}

	public String getSpobject()
	{
		return spobject;
	}

	public void setSpobject(String spobject)
	{
		this.spobject = spobject;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}
	
	public Object getInfo()
	{
		try
		{
			Object o = AutomaticExecuteXzsp.outputObject(getSpobject());
			if(o != null)
			{
				return o;
			}
		}
		catch(Exception mex)
		{
			mex.printStackTrace();
		}
		return new Object();
		
//		if(sptype == 1obj instanceof ApplicationOB){i = 0;}// ShenBan
//		else if(obj instanceof PreAcceptOB){i = 1;}// YuShouLi
//		else if(obj instanceof AcceptOB){i = 2;}// ShouLi
//		else if(obj instanceof SubmitOB){i = 3;}// ShenPi
//		else if(obj instanceof CompleteOB){i = 4;}// BanJie
//		else if(obj instanceof BlockOB){i = 5;}// TeBieChengXuQiDong
//		else if(obj instanceof ResumeOB){i = 6;}// TeBieChengXuBanJie
//		else if(obj instanceof SupplyOB){i = 7;}// BuJiaoGaoZhi
//		else if(obj instanceof SupplyAcceptOB){i = 8;}// BuJiaoShouLi
//		else if(obj instanceof ReceiveRegOB){i = 9;}// LingQuDengJi
	}
}
