package dswork.builder;

import java.util.ArrayList;
import java.util.List;

public class Table
{
	public class Column
	{
		private String name;// 列名
		private String datatype;// 类型
		private int length;// 长度
		private boolean nullable = true;
		private int precision = 0;// 整数位
		private int digit = 0;// 小数位
		private String comment;// 列注释
		private boolean auto = false;// 是否自增
		private String defaultvalue = "";// 类型
		private boolean key = false;// 是否主键

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

		public String getDatatype()
		{
			return datatype;
		}

		public void setDatatype(String datatype)
		{
			this.datatype = datatype;
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

		public boolean isKey()
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

		public boolean isNullable()
		{
			return nullable;
		}

		public void setNullable(boolean nullable)
		{
			this.nullable = nullable;
		}

		public String getDefaultvalue()
		{
			return defaultvalue;
		}

		public void setDefaultvalue(String defaultvalue)
		{
			this.defaultvalue = defaultvalue;
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
