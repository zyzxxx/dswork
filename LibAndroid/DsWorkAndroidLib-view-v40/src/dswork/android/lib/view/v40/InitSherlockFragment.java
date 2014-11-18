package dswork.android.lib.view.v40;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface InitSherlockFragment 
{
	/**
	 * 初始化主布局
	 */
	public View initMainView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	/**
	 * 初始化菜单布�?
	 * @param menu
	 */
	public void initMenu(Menu menu, MenuInflater inflater);
}
