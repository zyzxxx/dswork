package dswork.android.ui.MultiCheck;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import dswork.android.R;
import dswork.android.db.BaseModel;
import dswork.android.ui.MultiCheck.MultiCheckListView.ViewCache;

public class MultiCheckAdapter extends BaseAdapter 
{
	protected Context context;//上下文
	protected List dataList;//在绑定的数据
	protected int resource;//绑定的条目界面(如:R.layout.item)
	protected LayoutInflater inflater;//通过XML生成view对象，属于系统服务
	protected static HashMap<Integer,Boolean> isSelected;// 用来控制CheckBox的选中状况  
	protected boolean isMultiChoose; //是否让listView变成多选模式
	private String[] from;
	private int[] to;
	private ViewCache vc;
	
    
	/**
	 * 构造器
	 * @param context 上下文
	 * @param dataList 数据集合
	 * @param resource 绑定的条目界面(如:R.layout.item)
	 * @param from 需显示的model属性名,字符串数组String[]
	 * @param to 属性名绑定的View控件id,整形数组int[]
	 */
	public MultiCheckAdapter(Context context, List dataList, int resource, String[] from, int[] to, ViewCache vc) 
	{
		this.context = context;
		this.dataList = dataList;
		this.resource = resource;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.isMultiChoose = false;
		this.from = from;
		this.to = to;
		this.vc = vc;
		initData();
	}
	
	// 初始化isSelected的数据  
    private void initData()
    {  
        //这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。         
        isSelected = new HashMap<Integer, Boolean>();      
        for (int i = 0; i < dataList.size(); i++) isSelected.put(i, false);    
    }  
    
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int pos) {
		return dataList.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		BaseModel o = (BaseModel) dataList.get(position);
		convertView = inflater.inflate(resource, null);
		//找到显示控件
		ViewCache cache = new ViewCache();
		cache.chk = (CheckBox)convertView.findViewById(R.id.chk);
		cache.idView = (TextView)convertView.findViewById(R.id.id);
		try
		{
			for(int i=0;i<to.length;i++)
			{
				Method m = o.getClass().getMethod("get"+from[i].substring(0,1).toUpperCase()+from[i].substring(1));
				((TextView)convertView.findViewById(to[i])).setText(String.valueOf(m.invoke(o)));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//将控件对象保存到缓存中
		convertView.setTag(cache);//保存视图缓存对象至View的Tag属性中
		//判断是否显示checkbox
		if(isMultiChoose) cache.chk.setVisibility(CheckBox.VISIBLE);
		else cache.chk.setVisibility(CheckBox.GONE);
		//id赋值，根据isSelected来设置checkbox的选中状况 
		if(null!=getIsSelected().get(position))
		{
			cache.chk.setChecked(getIsSelected().get(position));
			cache.idView.setText(o.getId().toString());
		}
		return convertView;
		
//以下写法，ListView会出现超过10条时，数据重复的bug		
//		BaseModel o = (BaseModel) dataList.get(position);
//		ViewCache cache = null;
//		if(convertView==null)
//		{//如果为空，即第一页，需创建view对象，即生成条目界面对象
//			convertView = inflater.inflate(resource, null);
//			//找到显示控件
//			cache = new ViewCache();
//			cache.chk = (CheckBox)convertView.findViewById(R.id.chk);
//			cache.idView = (TextView)convertView.findViewById(R.id.id);
//			try
//			{
//				for(int i=0;i<to.length;i++)
//				{
//					Method m = o.getClass().getMethod("get"+from[i].substring(0,1).toUpperCase()+from[i].substring(1));
//					((TextView)convertView.findViewById(to[i])).setText(String.valueOf(m.invoke(o)));
//				}
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//			//判断是否显示checkbox
//			if(isMultiChoose) cache.chk.setVisibility(CheckBox.VISIBLE);
//			else cache.chk.setVisibility(CheckBox.GONE);
//			System.out.println("isMultiChoose:"+isMultiChoose);
//			//将控件对象保存到缓存中
//			convertView.setTag(cache);//保存视图缓存对象至View的Tag属性中
//		}
//		else
//		{//从缓存获取显示控件
//			cache = (ViewCache)convertView.getTag();
//		}
//		//id赋值，根据isSelected来设置checkbox的选中状况 
//		cache.chk.setChecked(getIsSelected().get(position));
//		cache.idView.setText(o.getId().toString());
//		System.out.println("getView:"+"inGetView");
//		return convertView;
	}

	/**
	 * 获取已选Map
	 * @return
	 */
	public static HashMap<Integer,Boolean> getIsSelected() {  
        return isSelected;  
    }  
  
	/**
	 * 修改已选Map
	 * @param isSelected
	 */
    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {  
    	MultiCheckAdapter.isSelected = isSelected;  
    }  
      
    /**
     * 修改多选模式
     * @param b
     */
    public void setIsMultiChoose(Boolean b){  
        isMultiChoose = b;  
    }	
    
//	//视图缓存类
//	public static class ViewCache
//	{
//		public CheckBox chk;
//		public TextView idView;
//	}
}
