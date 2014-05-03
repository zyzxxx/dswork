package dswork.android.view;

import com.actionbarsherlock.view.Menu;

public interface InitSherlockActivity 
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
