package dswork.android.model;

import net.sourceforge.pinyin4j.PinyinHelper;
import dswork.android.db.BaseModel;

public class Person extends BaseModel
{
	private String name;
	private String phone;
	private String amount;
	private String sortkey;
	
	public Person() {}

	public Person(String name, String phone, String amount) 
	{
		this.name = name;
		this.phone = phone;
		this.amount = amount;
		String first = PinyinHelper.toHanyuPinyinStringArray(name.charAt(0))[0];//获取拼音首字母
		this.sortkey = (first!=null?(String.valueOf(first.charAt(0))):name);//首字母为null时,不作转换
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSortkey() {
		return sortkey;
	}
	public void setSortkey(String sortkey) {
		this.sortkey = sortkey;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", phone=" + phone + ", amount="
				+ amount + ", sortkey=" + sortkey + "]";
	}
	
}
