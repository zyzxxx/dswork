package dswork.android.lib.core.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import dswork.android.lib.core.R;

public class WorkUtil
{
	/**
	 * 获取应用程序版本号
	 * @param context
	 * @return
	 */
	public static int getAppVersion(Context context) 
	{
		try 
		{
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} 
		catch (NameNotFoundException e) 
		{
			e.printStackTrace();
		}
		return 1;
	}
	
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
	
}
