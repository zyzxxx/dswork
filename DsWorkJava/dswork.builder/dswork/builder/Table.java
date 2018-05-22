package dswork.builder;

import java.util.ArrayList;
import java.util.List;

public class Table
{
	public class Column
	{
		private String name;// 列名
		private String type;// 类型
		private String comment;// 列注释
		private String defaults;// 默认值
		private int length;// 长度
		private boolean key = false;// 是否主键
		private boolean auto = false;// 是否自增
		private int precision = 0;// 整数位
		private int digit = 0;// 小数位
		
		public String getNameLowerCamel()
		{
			return Builder.lowerCamel(name);
		}// 小驼峰命名

		public String getNameUpperCamel()
		{
			return Builder.upperCamel(name);
		}// 大驼峰命名

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getType()
		{
			return type;
		}

		public void setType(String type)
		{
			this.type = type;
		}

		public String getDefaults()
		{
			return defaults;
		}

		public void setDefaults(String defaults)
		{
			this.defaults = defaults;
		}

		public String getComment()
		{
			return comment;
		}

		public void setComment(String comment)
		{
			this.comment = comment;
		}

		public int getLength()
		{
			return length;
		}

		public void setLength(int length)
		{
			this.length = length;
		}

		public boolean iskey()
		{
			return key;
		}

		public void setKey(boolean key)
		{
			this.key = key;
		}

		public boolean isAuto()
		{
			return auto;
		}

		public void setAuto(boolean auto)
		{
			this.auto = auto;
		}

		public int getPrecision()
		{
			return precision;
		}

		public void setPrecision(int precision)
		{
			this.precision = precision;
		}

		public int getDigit()
		{
			return digit;
		}

		public void setDigit(int digit)
		{
			this.digit = digit;
		}
	}
	private String name;// 表名
	private String comment;// 表注释
	public List<Column> column = new ArrayList<Column>();// 表列list
	public Column addColumn()
	{
		Column c = new Column();
		column.add(c);
		return c;
	}

	public String getNameLowerCamel()
	{
		return Builder.lowerCamel(name);
	}// 小驼峰命名

	public String getNameUpperCamel()
	{
		return Builder.upperCamel(name);
	}// 大驼峰命名

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
