package dswork.android.lib.ui.MultiCheck;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import dswork.android.lib.R;
import dswork.android.lib.db.BaseModel;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MultiCheckListView extends ListView 
{
	private Context ctx;
	private List<BaseModel> dataList;//数据集合
	private MultiCheckAdapter adapter;//自定义适配器
	private List<Long> idList = new ArrayList<Long>();//主键集合
    private int checkNum;//多选模式下，选中个数
    private boolean isMultiChoose = false;//判断是否多选模式 （默认false）
    private android.view.ActionMode mMode;
	private MultiCheckActionModeListener listener;
	private OnItemClickNotMultiListener itemClickNotMultiListener;
    
    public MultiCheckListView(Context ctx, AttributeSet attrs) 
    {
    	super(ctx, attrs);
    	this.ctx = ctx;
    }
    
    /**
     * 初始化多选模式
     * @param _dataList 数据集合
     * @param _adapter 自定义适配器（必须继承自MultiCheckAdapter）
     */
	public void initMultiCheck(List _dataList, MultiCheckAdapter _adapter)
	{
		this.dataList = _dataList;
		this.adapter = _adapter;
		this.setAdapter(adapter);
		//初始化列表数据和监听事件
		this.setOnItemClickListener(new MyOnItemClickListener());//单击事件
		this.setOnItemLongClickListener(new MyOnItemLongClickListener());//长按事件
	}
	
	/**
	 * 切换多选模式
	 * @param b (true:启用；false:关闭)
	 */
	public void isMultiMode(boolean b)
	{
		isMultiChoose = b;
		//若非多选模式，隐藏多选CheckBox，勾掉所有列表项的CheckBox
		if(!isMultiChoose)
		{
			noCheckAll();
		}
    	adapter.setIsMultiChoose(isMultiChoose);
    	adapter.notifyDataSetChanged();
	}
	
	/**
	 * 获取主键值集合
	 * @return List<Long>
	 */
	public List<Long> getIdList()
	{
		return this.idList;
	}
	/**
	 * 获取主键值数组
	 * @return long[]
	 */
	public long[] getIdArray()
	{
		long[] ids = new long[this.idList.size()];
		for(int i=0;i<this.idList.size();i++)
		{
			ids[i] = this.idList.get(i);
			System.out.println("ids["+i+"]:"+ids[i]);
		}
		return ids;
	}
	/**
	 * 获取主键值集合字符串，以逗号隔开
	 * @return String
	 */
	public String getIds()
	{
		String ids = "";
		for(int i=0; i<this.idList.size(); i++)
		{
			ids += String.valueOf(idList.get(i)) + (i+1<idList.size()?",":"");
		}
		Log.i("ids is :", ids);
		return ids;
	}
	
	/**
	 * 刷新列表
	 * @param _dataList 数据集合
	 */
	public void refreshListView(List _dataList)
	{
		if(mMode!=null)noCheckAll();
		dataList.clear();
		dataList.addAll(_dataList);
		this.setAdapter(adapter);//刷新adapter
	}
	
	/**
	 * 切换全选/全不选（用于MenuItem）
	 * @param item
	 */
	public void toggleChecked(MenuItem item)
	{
		idList.clear();
		if(item.isChecked())
		{
			item.setChecked(false);
			item.setIcon(R.drawable.btn_check_off_holo_light);
			noCheckAll();
		}
		else 
		{
			item.setChecked(true);
			item.setIcon(R.drawable.btn_check_on_holo_light);
			CheckAll();
		}
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 设置快速滚动条图片
	 * @param _drawable
	 */
	public void setFastScrollDrawable(int _drawable)
	{
		try { 
			Field f = AbsListView.class.getDeclaredField("mFastScroller"); 
			f.setAccessible(true); 
			Object o=f.get(this); 
			f=f.getType().getDeclaredField("mThumbDrawable"); 
			f.setAccessible(true); 
			Drawable drawable=(Drawable) f.get(o); 
			drawable=getResources().getDrawable(_drawable); 
			f.set(o,drawable); 
//			Toast.makeText(this, f.getType().getName(), 1000).show(); 
		} catch (Exception e) { 
			throw new RuntimeException(e); 
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		//键盘返回键
		if (keyCode == KeyEvent.KEYCODE_BACK )  
        {
			if(isMultiChoose)
			{
				isMultiMode(false);
			}
        }
		return super.onKeyDown(keyCode, event);
	}
	
	//listView单击item监听类
	private class MyOnItemClickListener implements AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) 
		{
            if(isMultiChoose)  
            {//多选模式，单击选中
            	checkOne(v, pos);
            }  
            else  
            {//非多选模式，由用户实现接口
            	itemClickNotMultiListener.onClick(v);
            } 
		}
	}
	//listView长按item监听类
	private class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener
	{
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View v, int pos, long arg3) 
		{
			mMode = startActionMode(listener.getActionModeCallback());//启动ActionMode
			isMultiMode(true);//设为多选模式
			checkOne(v, pos);//选中一项
	        return true;
		}
	}
	
	//单选
	private void checkOne(View v, int pos)
	{
		// 取得ViewCache对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤    
    	ViewCache holder = (ViewCache) v.getTag();    
        // 改变CheckBox的状态    
        holder.chk.toggle();    
        // 将CheckBox的选中状况记录下来    
        adapter.getIsSelected().put(pos, holder.chk.isChecked());     
        // 调整选定条目    
        if (holder.chk.isChecked() == true) 
        {
        	v.setSelected(true);
        	checkNum++;
        	idList.add(Long.parseLong(String.valueOf(holder.idView.getText())));
        }
        else 
    	{
        	v.setSelected(false);
        	checkNum--;
        	idList.remove(Long.parseLong(String.valueOf(holder.idView.getText())));
    	}
        mMode.setSubtitle(checkNum+" selected");		
	}
	
	//全选
	private void CheckAll()
	{
		HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();      
        for (int i = 0; i < dataList.size(); i++)
        {
        	isSelected.put(i, true);
        	idList.add(dataList.get(i).getId());
        }
        adapter.setIsSelected(isSelected);
        checkNum = dataList.size();
        mMode.setSubtitle(checkNum+" selected");
	}
	
	//全不选
	private void noCheckAll()
	{
		HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();      
        for (int i = 0; i < dataList.size(); i++) isSelected.put(i, false);  
        adapter.setIsSelected(isSelected);
        idList.clear();
        checkNum = 0;
        mMode.setSubtitle(checkNum+" selected");
	}
	
	//视图缓存类
	public static class ViewCache
	{
		public CheckBox chk;
		public TextView idView;
		public ImageButton itemMenu;
	}
	
	/**
	 * ListView项单击监听接口（非多选模式）
	 * @param listener OnItemClickNotMultiListener对象
	 */
	public void setOnItemClickNotMultiListener(OnItemClickNotMultiListener listener)
	{
		this.itemClickNotMultiListener = listener;
	}
	public interface OnItemClickNotMultiListener
	{
		public void onClick(View v);
	}
	
	/**
	 * ActionMode监听器
	 * @param listener MultiCheckActionModeListener对象
	 */
	public void setMultiCheckActionModeListener(MultiCheckActionModeListener listener)
	{
		this.listener = listener;
	}
	public interface MultiCheckActionModeListener
	{
		/**
		 * 实列化MultiCheckActionMode
		 * @return MultiCheckActionMode对象
		 */
		public Callback getActionModeCallback();
		/**
		 * ActionItem点击事件
		 * @param mode ActionMode对象
		 * @param item MenuItem对象
		 * @return
		 */
		public boolean onActionItemClicked(ActionMode mode, MenuItem item);
	}
}
