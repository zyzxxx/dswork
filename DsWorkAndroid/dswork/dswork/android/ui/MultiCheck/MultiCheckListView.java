package dswork.android.ui.MultiCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import dswork.android.R;
import dswork.android.db.BaseModel;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MultiCheckListView extends ListView 
{
	private Context ctx;
	private List<BaseModel> dataList;//数据集合
	private List<Long> idList = new ArrayList<Long>();//主键集合
	private ListView listView;
	private CheckBox chkAll;//全选CheckBox
	private Intent intent;//明细页意图
	private MultiCheckAdapter adapter;//自定义适配器
	private ActionMode mMode;
    private int checkNum;//多选模式下，选中个数
    private boolean isMultiChoose = false;//判断是否多选模式 （默认false）
	private ActionModeListener listener;
    
    public MultiCheckListView(Context context, AttributeSet attrs) 
    {
    	super(context, attrs);
    	this.ctx = ctx;
    }
    
    /**
     * 初始化多选模式
     * @param _dataList 数据集合
     * @param _adapter 自定义适配器（必须继承自MultiCheckAdapter）
     * @param _listView 列表视图对象（_listView和_chkAll必须在同一个xml布局下）
     * @param _chkAll 全选框对象（_listView和_chkAll必须在同一个xml布局下）
     * @param _intent 明细页意图
     */
	public void getMultiCheck(List _dataList, MultiCheckAdapter _adapter, ListView _listView, CheckBox _chkAll, Intent _intent)
	{
		dataList = _dataList;
		adapter = _adapter;
		listView = _listView;
		chkAll = _chkAll;
		intent = _intent;
		//初始化全选CheckBox监听时间
		chkAll.setOnCheckedChangeListener(new ChkAllListener());
		//初始化列表数据和监听事件
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());//单击事件
		listView.setOnItemLongClickListener(new MyOnItemLongClickListener());//长按事件
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
			chkAll.setVisibility(CheckBox.GONE);
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
	 * 刷新列表
	 * @param _dataList 数据集合
	 */
	public void refreshListView(List _dataList)
	{
		if(mMode!=null)noCheckAll();
		dataList.clear();
		dataList.addAll(_dataList);
		listView.setAdapter(adapter);//刷新adapter
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		//键盘返回键
		if (keyCode == KeyEvent.KEYCODE_BACK )  
        {
			if(isMultiChoose)
			{
				chkAll.setVisibility(CheckBox.GONE);
				isMultiMode(false);
			}
        }
		return super.onKeyDown(keyCode, event);
	}
	
	//全选框CheckBox监听类
	private class ChkAllListener implements OnCheckedChangeListener
	{
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
		{
			idList.clear();
			if(isChecked)
			{//全选
				HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();      
		        for (int i = 0; i < dataList.size(); i++)
		        {
		        	isSelected.put(i, true);
		        	idList.add(dataList.get(i).getId());
		        }
		        MultiCheckAdapter.setIsSelected(isSelected);
		        checkNum = dataList.size();
		        mMode.setSubtitle(checkNum+" selected");
			}
			else
			{//反选
				noCheckAll();
			}
			adapter.notifyDataSetChanged();
		    Toast.makeText(getContext(), "You choose "+checkNum+"items.", Toast.LENGTH_SHORT).show();  
		}
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
                Toast.makeText(getContext(), "You choose "+checkNum+"items.", Toast.LENGTH_SHORT).show();  
            }  
            else  
            {//非多选模式，跳转到明细页  
            	TextView idText = (TextView)v.findViewById(R.id.id);
            	long id = Long.parseLong(idText.getText().toString());
                getContext().startActivity(intent.putExtra("id", id));
            } 
		}
	}
	//listView长按item监听类
	private class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener
	{
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View v, int pos, long arg3) 
		{
			mMode = startActionMode(listener.getActionModeCallback());
			chkAll.setVisibility(CheckBox.VISIBLE);
			isMultiMode(true);
			checkOne(v, pos);
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
	
	//全不选
	private void noCheckAll()
	{
		chkAll.setChecked(false);
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
	}
	
	/**
	 * ActionMode监听器
	 * @param listener
	 */
	public void setActionModeListener(ActionModeListener listener)
	{
		this.listener = listener;
	}
	
	public interface ActionModeListener
	{
		public ActionMode.Callback getActionModeCallback();
	}
}
