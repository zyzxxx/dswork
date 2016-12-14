package dswork.html.nodes;

public class AttributeBoolean extends Attribute
{
	public AttributeBoolean(String key)
	{
		super(key, "");
	}

	@Override
	protected boolean isBooleanAttribute()
	{
		return true;
	}
}
