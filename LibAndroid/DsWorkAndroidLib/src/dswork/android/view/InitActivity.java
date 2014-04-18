package dswork.android.view;

import android.view.Menu;

public interface InitActivity 
{
	/**
	 * 初始化主布局
	 */
	public void initMainView();
	/**
	 * 初始化菜单布局
	 * @param menu
	 */
	public void initMenu(Menu menu);
}
