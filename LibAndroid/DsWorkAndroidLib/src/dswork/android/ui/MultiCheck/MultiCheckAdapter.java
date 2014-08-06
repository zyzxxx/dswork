package dswork.android.ui.MultiCheck;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.R;
import dswork.android.controller.BaseController;
import dswork.android.db.BaseModel;
import dswork.android.ui.MultiCheck.MultiCheckListView.ViewCache;

public class MultiCheckAdapter extends BaseAdapter 
{
	protected Context ctx;//上下文
	private BaseController controller;//控制器
	protected List dataList;//在绑定的数据
	private MultiCheckListView lv;
	protected int resource;//绑定的条目界面(如:R.layout.item)
	private int itemIdRes;//每条item项的主键TextView
	private int itemChkRes;//每条item项的CheckBox
	private int ctrlMenuRes;//每条item项的Menu
	private int ctrlMenuItemsRes;//每条item项的Menu的操作项（如:修改、删除）
	protected LayoutInflater inflater;//通过XML生成view对象，属于系统服务
	protected static HashMap<Integer,Boolean> isSelected;// 用来控制CheckBox的选中状况  
	protected boolean isMultiChoose; //是否让listView变成多选模式
	private String[] from;
	private int[] to;
	private ViewCache vc;
	private String packageName;
	private String updClassName;
	private ExpandCtrlMenu ecMenu;
	private Boolean isRewriteCtrlMenu = false;
	
	/**
	 * 构造器
	 * @param context 上下文
	 * @param controller 控制器
	 * @param dataList 数据集合
	 * @param lv MultiCheckListView对象
	 * @param resource 绑定的条目界面(如:R.layout.item)
	 * @param itemIdRes 每条item项的主键TextView资源
	 * @param itemChkRes 每条item项的CheckBox资源
	 * @param ctrlMenuRes 每条item项的ImageButton资源
	 * @param ctrlMenuItemsRes 每条item项的Menu的操作项R.array资源（采用默认值：修改、删除，则传0）
	 * @param from 需显示的model属性名,字符串数组String[]
	 * @param to 属性名绑定的View控件id,整形数组int[]
	 * @param packageName 项目包名
	 * @param updClassName 修改页Activity全类名
	 * @param ExpandCtrlMenu 扩展ctrl_menu
	 * @param isRewriteCtrlMenu 是否重写CtrlMenu,默认为false
	 */
	public MultiCheckAdapter(Context ctx, BaseController controller, List dataList, MultiCheckListView lv, int resource, int itemIdRes, int itemChkRes, int ctrlMenuRes, int ctrlMenuItemsRes, String[] from, int[] to, ViewCache vc, String packageName, String updClassName, ExpandCtrlMenu ecMenu, Boolean isRewriteCtrlMenu) 
	{
		this.ctx = ctx;
		this.controller = controller;
		this.dataList = dataList;
		this.lv = lv;
		this.resource = resource;
		this.itemIdRes = itemIdRes;
		this.itemChkRes = itemChkRes;
		this.ctrlMenuRes = ctrlMenuRes;
		//this.ctrlMenuItemsRes = (ctrlMenuItemsRes == 0 ? R.array.ctrl_menu_items : ctrlMenuItemsRes);
		this.ctrlMenuItemsRes = ctrlMenuItemsRes;
		this.inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.isMultiChoose = false;
		this.from = from;
		this.to = to;
		this.vc = vc;
		this.packageName = packageName;
		this.updClassName = updClassName;
		this.ecMenu = ecMenu;
		this.isRewriteCtrlMenu = isRewriteCtrlMenu;
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
		cache.chk = (CheckBox)convertView.findViewById(itemChkRes);
		cache.idView = (TextView)convertView.findViewById(itemIdRes);
		cache.ctrlMenu = (ImageButton) convertView.findViewById(ctrlMenuRes);
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
		//判断是否显示checkbox和ctrlMenu
		if(isMultiChoose) 
		{
			cache.chk.setVisibility(CheckBox.VISIBLE);
			cache.ctrlMenu.setVisibility(ImageButton.GONE);
		}
		else 
		{
			cache.chk.setVisibility(CheckBox.GONE);
			cache.ctrlMenu.setVisibility(ImageButton.VISIBLE);
		}
		//id赋值，根据isSelected来设置checkbox的选中状况 
		if(null!=getIsSelected().get(position))
		{
			cache.chk.setChecked(getIsSelected().get(position));
			cache.idView.setText(o.getId().toString());
			cache.ctrlMenu.setTag(cache.idView);//保存记录id到Tag中方便后续操作
		}
		//单击每项的ctrlMenu弹出菜单
		cache.ctrlMenu.setOnClickListener(new CtrlMenuItemOnClickListener());
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
    
    //CtrlMenu项点击监听类
    private class CtrlMenuItemOnClickListener implements OnClickListener
    {
		@Override
		public void onClick(final View v) 
		{
//			Log.i("itemMenuClick", String.valueOf(((TextView)v.getTag()).getText()));
//			Toast.makeText(context, ((TextView)v.getTag()).getText(), Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(ctx)
			.setTitle("")
			//.setTitle(R.string.ctrl_menu)
			.setItems(ctrlMenuItemsRes, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					final String id_s = String.valueOf(((TextView)v.getTag()).getText());
					long[] id_l = {Long.valueOf(id_s).longValue()};
					if(isRewriteCtrlMenu)
					{//若覆盖CtrlMenu
						ecMenu.onItemSelected(id_s, id_l[0], which);
					}
					else
					{//若扩展CtrlMenu
						onCtrlMenuItemSelectedDefault(id_s, id_l, which);
						ecMenu.onItemSelected(id_s, id_l[0], which);
					}
				}
			})
			.show();
		}
    }
    
    //CtrlMenu默认ItemSelected方法
    private void onCtrlMenuItemSelectedDefault(final String id_s, long[] id_l, int which)
    {
		switch (which) 
		{
			case 0://修改
				Bundle b = new Bundle();
				b.putString("ids", id_s);
				b.putLongArray("idsArr", id_l);
				ctx.startActivity(new Intent().setClassName(packageName, updClassName).putExtras(b));
				break;
			case 1://删除
        		new AlertDialog.Builder(ctx)
        		.setTitle(R.string.confirm_del)
        		.setIcon(android.R.drawable.ic_delete)
        		.setNegativeButton(R.string.no, null)
        		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        		{
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						String result = controller.deleteBatch(id_s);//执行删除
						if(result.equals("1"))
						{
							lv.refreshListView(controller.get(new HashMap()));//刷新列表
							Toast.makeText(ctx.getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show(); 
						}
						else
						{
							Toast.makeText(ctx.getApplicationContext(), "操作失败，网络异常", Toast.LENGTH_LONG).show();
						}
					}
				})
        		.show();
	    		break;
		}
    }
    
    //扩展Ctrl_Menu接口
	public interface ExpandCtrlMenu
	{
		/**
		 * 选择Item时调用的方法
		 * @param id_s String类型的id
		 * @param id_l long类型的id
		 * @param which
		 */
		public void onItemSelected(String id_s, long id_l, int which);
	}
}
