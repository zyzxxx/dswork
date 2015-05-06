package com.paper.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.paper.R;
import com.paper.model.PaperImage;
import com.paper.view.listeners.ImageGridViewOnItemClickListener;

import dswork.android.lib.core.util.bmputil.BitmapLoader;

public class ImageGridViewAdapter extends BaseAdapter implements WrapperListAdapter
{
	private static Context ctx;
	private List dataList;
	private static int mCount = 0;

	public ImageGridViewAdapter(Context ctx, List<PaperImage> dataList)
	{
		this.ctx = ctx;
		this.dataList = dataList;
		BitmapLoader.createBitmapCache(ctx, 8);
	}
	
	public List getDataList()
	{
		return this.dataList;
	}

	@Override
	public int getCount()
	{
		return dataList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
        if (convertView == null) 
        {  
        	convertView = LayoutInflater.from(ctx).inflate(R.layout.image_gridview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } 
        else 
        {  
            viewHolder = (ViewHolder) convertView.getTag();
        }
    	PaperImage o = (PaperImage)dataList.get(pos);
    	viewHolder.mImageId.setText(o.getId()+"");
    	viewHolder.mImagePid.setText(o.getPid()+"");
    	//异步加载图片
//		viewHolder.mImageView.setImageResource(R.drawable.empty_photo);
    	String url = ctx.getString(R.string.app_path)+"client/paperimage/getPaperImageById.action?id="+o.getId();
    	BitmapLoader.loadBitmaps(ctx, null, viewHolder.mImageView, url, null, null);
    	//图片单击事件
    	convertView.setOnClickListener(new ImageGridViewOnItemClickListener(ctx, o.getId(), o.getPid(), dataList));
    	
//---------------------------诡异，采用以下方式反而会有重复项出现？！----------------------------------------------//
//        if(parent.getChildCount() == pos)
//        {// 正常情况下应该执行的代码
//        	PaperImage o = (PaperImage)dataList.get(pos);
//        	viewHolder.mImageId.setText(o.getId()+"");
//        	viewHolder.mImagePid.setText(o.getPid()+"");
//        	//异步加载图片
////			viewHolder.mImageView.setImageResource(R.drawable.empty_photo);
//        	String url = ctx.getString(R.string.app_path)+"client/paperimage/getPaperImageById.action?id="+o.getId();
//        	BitmapLoader.loadBitmaps(ctx, null, viewHolder.mImageView, url, null, null);
//        	//图片单击事件
//        	convertView.setOnClickListener(new ImageGridViewOnItemClickListener(ctx, o.getId(), o.getPid(), dataList));
//        }
//        else
//        {// 这里就是多次加载的问题，可以不用理这里面的 代码，
//        }
		return convertView;
	}
	
	// 重写的自定义ViewHolder
	public static class ViewHolder
	{
		public View v;
		public ImageView mImageView;
		public TextView mImageId;
		public TextView mImagePid;

		public ViewHolder(View v)
		{
			this.v = v;
			this.mImageView = (ImageView) v.findViewById(R.id.img);
			this.mImageId = (TextView) v.findViewById(R.id.img_id);
			this.mImagePid = (TextView) v.findViewById(R.id.img_pid);
		}
	}

	@Override
	public ListAdapter getWrappedAdapter()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
