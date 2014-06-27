package dswork.android.lib.ui.MultiCheck;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import dswork.android.lib.R;
import dswork.android.lib.db.BaseModel;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MultiCheckListView extends ListView 
{
	private Context ctx;
	private List<BaseModel> dataList;//数据集
	/*MultiCheck属性**********/
	private MultiCheckAdapter adapter;//自定义适配器
	private List<Long> idList = new ArrayList<Long>();//主键集合
    private int checkNum;//多选模式下，被选中个数
    private boolean isMultiChoose = false;//判断是否多选模式 （默认false）
    private android.view.ActionMode mMode;//ActionMode
	private MultiCheckActionModeListener listener;//MultiCheckActionMode监听器
	private OnItemClickNotMultiListener itemClickNotMultiListener;//item项单击监听器（非多选模式下）
	/*PullRefresh属性**********/
	private LinearLayout headerView;//顶部布局
	private LinearLayout inHeaderView;//headerView内的布局
	private TextView RefreshTips;//刷新更多...
	private LinearLayout footerView;//底部布局
	private LinearLayout inFooterView;//footerView内的布局
	private TextView MoreTips;//更多...
	private ProgressBar downRefreshPb;//下拉刷新进度条
	private ProgressBar upRefreshPb;//上拉刷新进度条
	private int MaxDataNum;//最大数据条数
	private int AvgDataNum = 10;//每次获取数据数
	private int PerDataNum = 20;//每秒获取数据数
	private int CurDataNum;//已取数据条数
	private int LastDataNum;//剩余数据条数
    private int lastVisibleIndex;// 最后可见条目的索引
    private int firstVisibleIndex;// 第一可见条目的索引
    private Handler handler;
    private PullUpToRefreshListener uListener;//上拉刷新监听器
    private PullDownToRefreshListener dListener;//下拉刷新监听器
    
    public MultiCheckListView(Context ctx, AttributeSet attrs) 
    {
    	super(ctx, attrs);
    	this.ctx = ctx;
    	initView();
    }
    
    /*MultiCheck方法**********/
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
	 * @return Long[]
	 */
	public Long[] getIdArray()
	{
		Long[] ids = new Long[this.idList.size()];
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
		pos = pos-1;
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
	
	/*PullRefresh方法**********/
	/**
	 * 获取最大数据条数
	 * @return
	 */
	public int getMaxDataNum() {
		return MaxDataNum;
	}
	/**
	 * 设置最大数据条数
	 * @return
	 */
	public void setMaxDataNum(int maxDataNum) {
		MaxDataNum = maxDataNum;
	}
	/**
	 * 获取每秒读取数据数
	 * @return
	 */
	public int getPerDataNum() {
		return PerDataNum;
	}
	/**
	 * 设置每秒读取数据数
	 * @return
	 */
	public void setPerDataNum(int perDataNum) {
		PerDataNum = perDataNum;
	}
	/**
	 * 获取每次读取数据条数
	 * @return
	 */
	public int getAvgDataNum() {
		return AvgDataNum;
	}
	/**
	 * 设置每次读取数据条数
	 * @return
	 */
	public void setAvgDataNum(int avgDataNum) {
		AvgDataNum = avgDataNum;
	}
	/**
	 * 获取当前显示数据条数
	 * @return
	 */
	public int getCurDataNum() {
		return CurDataNum = adapter.getCount();
	}
	/**
	 * 获取剩余数据条数
	 * @return
	 */
	public int getLastDataNum() {
		return LastDataNum = MaxDataNum - getCurDataNum();
	}
	/**
	 * 获取下次加载数据条数
	 * @return
	 */
	public int getLoadDataNum()
	{
		if(getLastDataNum() > 0)
		{
	    	if(LastDataNum > AvgDataNum)
	    		return AvgDataNum;
	    	else
	    		return LastDataNum;
		}
		return 0;
	}
	/**
	 * 添加item
	 * @param item
	 */
	public void addDataItem(BaseModel item)
	{
		this.dataList.add(item);
	}
	/**
	 * 初始化上拉下拉视图
	 */
	private void initView()
	{
		handler = new Handler();
		dataList = new ArrayList<BaseModel>();//初始化data
		initHeaderView();//画header布局
		initFooterView();//画footer布局
		//滑动加载数据
		super.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				firstVisibleIndex = firstVisibleItem;// 计算第一可见条目的索引
		        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;// 计算最后可见条目的索引
		        // 所有的条目已经和最大条数相等，则移除底部的View
		        System.out.println("[totalItemCount]"+totalItemCount+"|[MaxDataNum]"+(MaxDataNum+1));
		        if (totalItemCount == MaxDataNum + 2) 
		        {
//		        	MultiCheckListView.this.removeFooterView(footerView);
		        	footerView.setVisibility(View.GONE);
//		            Toast.makeText(ctx, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
		        }
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) 
			{
		        if (lastVisibleIndex==(adapter.getCount()+1) && MaxDataNum>adapter.getCount() && scrollState==OnScrollListener.SCROLL_STATE_IDLE) 
		        {//上拉(滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目)
		        	pullUpDelalyLoad();
		        }
		        else if(firstVisibleIndex==0 && scrollState==OnScrollListener.SCROLL_STATE_IDLE)
		        {//下拉
		        	pullDownDelalyLoad();
		        }
			}
		});
//    	pullDownDelalyLoad();
	}
	private void initHeaderView()
	{
		//创建顶部布局
		headerView = new LinearLayout(ctx);
		headerView.setOrientation(LinearLayout.VERTICAL);
		headerView.setGravity(Gravity.CENTER_HORIZONTAL);
		//创建"刷新更多..."提示
		RefreshTips = new TextView(ctx);
		RefreshTips.setText("↓ 释放更多...");
		inHeaderView = new LinearLayout(ctx);
		inHeaderView.setGravity(Gravity.CENTER);
		ViewGroup.LayoutParams RefreshTipsParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		inHeaderView.addView(RefreshTips, RefreshTipsParams);
		ViewGroup.LayoutParams headerViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
		headerView.addView(inHeaderView, headerViewParams);
		//创建ProgressBar
		downRefreshPb = new ProgressBar(ctx);
		downRefreshPb.setVisibility(GONE);
		headerView.addView(downRefreshPb, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		//添加顶部布局
		addHeaderView(headerView);
	}
	private void initFooterView()
	{
		//创建底部布局
		footerView = new LinearLayout(ctx);
		footerView.setOrientation(LinearLayout.VERTICAL);
		footerView.setGravity(Gravity.CENTER_HORIZONTAL);
		//创建"更多..."提示
		MoreTips = new TextView(ctx);
		MoreTips.setText("↑ 更多...");
		inFooterView = new LinearLayout(ctx);
		inFooterView.setGravity(Gravity.CENTER);
		ViewGroup.LayoutParams MoreTipsParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		inFooterView.addView(MoreTips, MoreTipsParams);
		ViewGroup.LayoutParams bottomViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
		footerView.addView(inFooterView, bottomViewParams);
		//创建ProgressBar
		upRefreshPb = new ProgressBar(ctx);
		upRefreshPb.setVisibility(GONE);
		footerView.addView(upRefreshPb, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		//添加底部布局
		addFooterView(footerView);
	}
	//上拉加载数据
	private void pullUpDelalyLoad()
	{
		inFooterView.setVisibility(View.GONE);
		upRefreshPb.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				uListener.pullUpToRefresh();
				inFooterView.setVisibility(View.VISIBLE);
				upRefreshPb.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
			}
		},1000*(AvgDataNum/PerDataNum));
	}
	//下拉加载数据
	private void pullDownDelalyLoad()
	{
		inHeaderView.setVisibility(View.GONE);
		downRefreshPb.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				dataList.clear();
				dListener.pullDownToRefresh();
				inHeaderView.setVisibility(View.VISIBLE);
				downRefreshPb.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
				MultiCheckListView.this.setSelection(1);
				footerView.setVisibility(View.VISIBLE);
			}
		},1000*(AvgDataNum/PerDataNum));
	}
	
	/**
	 * 上拉刷新监听器
	 * @param listener
	 */
	public void setPullUpToRefreshListener(PullUpToRefreshListener listener)
	{
		this.uListener = listener;
	}
	
	public interface PullUpToRefreshListener
	{
		public void pullUpToRefresh();
	}
	
	/**
	 * 下拉刷新监听器
	 * @param listener
	 */
	public void setPullDownToRefreshListener(PullDownToRefreshListener listener)
	{
		this.dListener = listener;
	}
	
	public interface PullDownToRefreshListener
	{
		public void pullDownToRefresh();
	}
}
