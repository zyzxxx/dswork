package dswork.android.ui;

import java.util.HashMap;
import dswork.android.R;
import dswork.android.controller.BaseController;
import dswork.android.ui.MultiCheck.MultiCheckListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class OleActionMode implements ActionMode.Callback 
{
	private Activity thisAct;
	private BaseController controller;
	private int menuRes;
	private int menuUpdRes;
	private int menuDelConfirmRes;
	private MultiCheckListView lv;
	private String packageName;
	private String updClassName;

	/**
	 * 
	 * @param thisAct CallingActivity
	 * @param BaseController 继承自BaseController的控制器对象
	 * @param menuRes ActionMode菜单资源
	 * @param menuUpdRes menuItem修改资源
	 * @param menuDelConfirmRes menuItem删除确认资源
	 * @param lv MultiCheckListView对象
	 * @param updListener 修改操作类
	 * @param delListener 删除操作类
	 * @param packageName 项目报名
	 * @param updClassName 修改Activity全类名
	 */
	public OleActionMode(Activity thisAct, BaseController controller,
			int menuRes, int menuUpdRes, int menuDelConfirmRes,
			MultiCheckListView lv, String packageName, String updClassName) {
		super();
		this.thisAct = thisAct;
		this.controller = controller;
		this.menuRes = menuRes;
		this.menuUpdRes = menuUpdRes;
		this.menuDelConfirmRes = menuDelConfirmRes;
		this.lv = lv;
		this.packageName = packageName;
		this.updClassName = updClassName;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) 
	{
		//填充ActionMode的menu
		MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(menuRes, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) 
	{
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item)
	{
		boolean result = false;
		//修改
		if(menuUpdRes == item.getItemId())
		{
        	if(lv.getIdList().size() > 0)
        	{
        		if(lv.getIdList().size() == 1)
        		{//一条	
        			Bundle b = new Bundle();
        			b.putString("ids", lv.getIds());
        			b.putLongArray("idsArr", lv.getIdArray());
        			thisAct.startActivity(new Intent().setClassName(packageName, updClassName).putExtras(b));
        		}
        		else
        		{//多条
	        		new AlertDialog.Builder(thisAct)
	        		.setTitle(R.string.confirm_upd)
	        		.setIcon(android.R.drawable.ic_dialog_info)
	        		.setNegativeButton(R.string.no, null)
	        		.setPositiveButton(R.string.yes, new updListener())
	        		.show();
        		}
        		result = true;
        	}
        	else
        	{
        		Toast.makeText(thisAct.getApplicationContext(), "未选中 ！", Toast.LENGTH_SHORT).show();  
                result = false;
        	}
		}
		//删除
		else if(menuDelConfirmRes == item.getItemId())
		{
        	if(lv.getIdList().size()>0)
        	{
        		new AlertDialog.Builder(thisAct)
        		.setTitle(R.string.confirm_del)
        		.setIcon(android.R.drawable.ic_delete)
        		.setNegativeButton(R.string.no, null)
        		.setPositiveButton(R.string.yes, new delListener())
        		.show();
        		result = true;
        	}
        	else
        	{
        		Toast.makeText(thisAct.getApplicationContext(), "未选中 ！", Toast.LENGTH_SHORT).show();  
                result = false;
        	}
		}
		return result;
	}
	
	@Override
	public void onDestroyActionMode(ActionMode mode) 
	{
		lv.isMultiMode(false);//关闭多选模式
	}

	//删除操作监听类
	private class delListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
    		String result = controller.deleteBatch(lv.getIds());//执行删除
    		lv.refreshListView(controller.get(new HashMap()));//刷新列表
    		Toast.makeText(thisAct.getApplicationContext(), result, Toast.LENGTH_SHORT).show(); 
		}
	}
	
	//修改操作监听类
	private class updListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			Bundle b = new Bundle();
			b.putString("ids", lv.getIds());
			b.putLongArray("idsArr", lv.getIdArray());
			thisAct.startActivity(new Intent().setClassName(packageName, updClassName).putExtras(b));
		}
	}
}
