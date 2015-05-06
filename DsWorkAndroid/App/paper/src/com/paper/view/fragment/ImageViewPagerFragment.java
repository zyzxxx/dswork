package com.paper.view.fragment;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paper.R;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import dswork.android.lib.core.util.bmputil.BitmapLoader;

public class ImageViewPagerFragment extends Fragment
{
	//标记要注入的控件
	@InjectView(id=R.id.img) ImageView mImageView;
	//变量
	private long imgId;
	public ImageViewPagerFragment(Long imgId)
	{
		super();
		this.imgId = imgId;
	}
	public Long getImgId()
	{
		return imgId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.image_viewpager_fragment, container, false);
		InjectUtil.injectView(this, rootView);//注入控件
		String url = getActivity().getString(R.string.app_path)+"client/paperimage/getPaperImageById.action?id="+this.getImgId();
		BitmapLoader.loadBitmaps(getActivity(), mImageView.getRootView(), mImageView, url, "600", "400");
		
        // The MAGIC happens here!
		PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImageView);

		return rootView;
	}
}