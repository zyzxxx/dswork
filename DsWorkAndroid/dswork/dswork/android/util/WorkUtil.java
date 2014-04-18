package dswork.android.util;

import dswork.android.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class WorkUtil
{
	/**
	 * 添加九宫格快捷方式
	 * @param ctx
	 * @param rootView 根视图
	 * @param includeId include快捷方式视图id
	 * @param icRes 图标资源
	 * @param name 快捷方式名
	 * @param intent 意图
	 */
	public static void addShortcut(final Context ctx, View rootView, int includeId, int icRes, CharSequence name, final Intent intent)
	{
		View v = rootView.findViewById(includeId);
		ImageView ic_app = (ImageView)v.findViewById(R.id.ic_app_dswork);
		ic_app.setImageResource(icRes);
		TextView name_app = (TextView)v.findViewById(R.id.name_app_dswork);
		name_app.setText(name);
		v.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				ctx.startActivity(intent);
			}
		});
	}
	
	/**
	 * 获取应用意图
	 * @param packageName 包名
	 * @param className 类名
	 * @param isOtherApp 是否跨应用
	 * @return
	 */
	public static Intent getAppIntent(String packageName, String className, boolean isOtherApp, String action, String category, Integer flags)
	{
		Intent intent=new Intent();
		//用法一
		if(isOtherApp) intent.setAction(action);
		intent.setClassName(packageName, className);
		//用法二（跨应用激活）
//		ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
//		intent.setAction(Intent.ACTION_MAIN);
//		intent.addCategory(Intent.CATEGORY_LAUNCHER);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setComponent(cmp);
		return intent;
	}
	
//	/***
//	 * 界面布局框架
//	 * @author ole
//	 *
//	 */
//	public static abstract class WorkActivity extends FragmentActivity
//	{
//		///////////////////////////////////////////////////////////////////
//		///////////////////////布局重载方法/////////////////////////////
//		/**
//		 * 初始化主布局
//		 */
//		protected abstract void initMainView();
//		/**
//		 * 初始化菜单布局
//		 * @param menu
//		 */
//		protected abstract void initMenu(Menu menu);
//		/**
//		 * 初始化菜单按钮点击事件
//		 * @param item
//		 */
//		protected void initMenuItemSelected(MenuItem item)
//		{
//			switch(item.getItemId())
//			{
//				case android.R.id.home://返回
//					this.finish();
//					break;
//			}
//		}
//		
//		///////////////////////////////////////////////////////////////////
//		///////////////////////官方Activity重载方法////////////////////////
//		@Override
//		protected void onCreate(Bundle savedInstanceState) {
//			super.onCreate(savedInstanceState);
//			initMainView();
//		}
//
//		@Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			initMenu(menu);
//			return true;
//		}
//		
//		@Override
//		public boolean onMenuItemSelected(int featureId, MenuItem item) {
//			initMenuItemSelected(item);
//			return super.onOptionsItemSelected(item);
//		}
//	}
//	//列表页
//	public static abstract class GetActivity extends WorkActivity
//	{
//	}
//	//添加页
//	public static abstract class AddActivity extends WorkActivity
//	{
//	}
//	//修改页
//	public static abstract class UpdActivity extends WorkActivity
//	{
//	}
//	//明细页
//	public static abstract class GetByIdActivity extends WorkActivity
//	{
//	}
//	
//	public @interface Inject
//	{
//		int id();
//	}
}
