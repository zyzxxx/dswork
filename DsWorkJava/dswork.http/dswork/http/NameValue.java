package dswork.http;

public class NameValue
{
	private String name;
	private String value;

	public NameValue(String name, String value)
	{
		this.name = String.valueOf(name);
		this.value = value;
	}
	
	public void setName(String name)
	{
		this.name = String.valueOf(name);
	}

	public String getName()
	{
		return this.name;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return this.value;
	}
}
