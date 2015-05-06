package com.paper.model;

public class PaperModel 
{
	private long id;
	private long pid;
	private String name;
	private int sort;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public long getPid()
	{
		return pid;
	}
	public void setPid(long pid)
	{
		this.pid = pid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getSort()
	{
		return sort;
	}
	public void setSort(int sort)
	{
		this.sort = sort;
	}
}
