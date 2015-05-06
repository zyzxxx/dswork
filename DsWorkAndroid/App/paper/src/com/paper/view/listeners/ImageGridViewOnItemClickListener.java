package com.paper.view.listeners;

import java.io.Serializable;
import java.util.List;
import com.paper.model.PaperImage;
import com.paper.view.ImageViewPagerActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.appcompat.R;
import android.view.View;

public class ImageGridViewOnItemClickListener implements View.OnClickListener
{
	private Context ctx;
	private long id;//被点击的图片id
	private long pid;//对应菜单项id
	private List<PaperImage> dataList;
	
	public ImageGridViewOnItemClickListener(Context ctx, long id, long pid, List<PaperImage> dataList)
	{
		super();
		this.ctx = ctx;
		this.id = id;
		this.pid = pid;
		this.dataList = dataList;
	}

	@Override
	public void onClick(View v)
	{
		Bundle b = new Bundle();
		b.putLong("id", id);
		b.putLong("pid", pid);
		b.putSerializable("dataList", (Serializable) dataList);
		//跳转到图片分页Activity
		ctx.startActivity(new Intent().setClass(ctx, ImageViewPagerActivity.class).putExtra("info", b));
//		ctx.overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
//		ActivityOptionsCompat.makeCustomAnimation(ctx, R.anim.abc_fade_in,R.anim.abc_fade_out);
	}
}
