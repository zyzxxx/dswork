package dswork.android.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MoreListView extends ListView 
{
	private Context ctx;
	private List<HashMap<String,Object>> data;
	private BaseAdapter adapter;
	private LinearLayout footerView;
	private Button btn;
	private ProgressBar pb;
	private OnLoadMoreListener listener;
	private Handler handler;
	private int MaxDataNum;//最大数据条数
	private int AvgDataNum = 10;//每次获取数据数
	private int PerDataNum = 20;//每秒获取数据数
	private int CurDataNum;//已取数据条数
	private int LastDataNum;//剩余数据条数
    private int lastVisibleIndex;// 最后可见条目的索引
    private boolean isLoadScroll = false;//是否需要下拉刷新效果，默认false
	
	//采用findById方式实例化控件，需用以下方式定义控件类构造函数
	public MoreListView(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		this.ctx = ctx;
		initView();
	}
	//获取与设置定义适配器(SimpleAdapter)时所需要的List<HashMap<String, Object>>集合
	public List<HashMap<String, Object>> getData() {
		return data;
	}
	public void setData(List<HashMap<String, Object>> data) {
		this.data = data;
	}
	public void addDataItem(HashMap<String, Object> item)
	{
		this.data.add(item);
	}
	//设置适配器
	public void setAdapter(BaseAdapter adapter)
	{
		this.adapter = adapter;
		super.setAdapter(adapter);
	}
	//获取与设置最大数据条数
	public int getMaxDataNum() {
		return MaxDataNum;
	}
	public void setMaxDataNum(int maxDataNum) {
		MaxDataNum = maxDataNum;
	}
	//获取与设置每秒获取数据数
	public int getPerDataNum() {
		return PerDataNum;
	}
	public void setPerDataNum(int perDataNum) {
		PerDataNum = perDataNum;
	}
	//获取与设置每次获取数据条数
	public int getAvgDataNum() {
		return AvgDataNum;
	}
	public void setAvgDataNum(int avgDataNum) {
		AvgDataNum = avgDataNum;
	}
	//获取当前显示数据条数
	public int getCurDataNum() {
		return CurDataNum = adapter.getCount();
	}
	//获取剩余数据条数
	public int getLastDataNum() {
		return LastDataNum = MaxDataNum - getCurDataNum();
	}
	//获取下次加载数据条数
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
	//设置是否需要下拉刷新
	public void setLoadScroll(boolean isLoadScroll) {
		this.isLoadScroll = isLoadScroll;
	}
	
	//初始化视图
	private void initView()
	{
		//初始化data
		data = new ArrayList<HashMap<String,Object>>();
		//创建底部布局
		footerView = new LinearLayout(ctx);
		footerView.setOrientation(LinearLayout.VERTICAL);
		footerView.setGravity(Gravity.CENTER_HORIZONTAL);
		//创建"加载更多"按钮
		btn = new Button(ctx);
		btn.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		btn.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		btn.setText("点击加载更多");
		ViewGroup.LayoutParams btnViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		footerView.addView(btn,btnViewParams);
		//创建ProgressBar
		pb = new ProgressBar(ctx);
		pb.setVisibility(GONE);
		ViewGroup.LayoutParams pbViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		footerView.addView(pb,pbViewParams);
		//添加底部布局
		addFooterView(footerView);
		
		handler = new Handler();
		//单击按钮加载数据
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				loadDelalyed();
			}
		});
		//滑动加载数据
		super.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
		        // 计算最后可见条目的索引
		        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		        // 所有的条目已经和最大条数相等，则移除底部的View
		        if (totalItemCount == MaxDataNum + 1) 
		        {
		        	MoreListView.this.removeFooterView(footerView);
		            Toast.makeText(ctx, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
		        }
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) 
			{
				if(isLoadScroll)
				{
			        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
			        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastVisibleIndex == adapter.getCount()) 
			        {
			        	loadDelalyed();
			        }
				}
			}
		});
	}
	//加载数据
	private void loadDelalyed()
	{
		System.out.println("速度："+AvgDataNum/PerDataNum);
		pb.setVisibility(View.VISIBLE);
		btn.setVisibility(View.GONE);
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run() 
			{
				listener.loadMoreData();
				btn.setVisibility(View.VISIBLE);
				pb.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
			}
		},1000*(AvgDataNum/PerDataNum));
	}
	
	/**
	 * 加载更多监听器
	 * @param listener
	 */
	public void setOnLoadMoreListener(OnLoadMoreListener listener)
	{
		this.listener = listener;
	}
	
	public interface OnLoadMoreListener
	{
		public void loadMoreData();
	}
	
}
