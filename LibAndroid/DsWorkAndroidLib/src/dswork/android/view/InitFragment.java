package dswork.android.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public interface InitFragment 
{
	/**
	 * 初始化主布局
	 */
	public View initMainView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	/**
	 * 初始化菜单布局
	 * @param menu
	 */
	public void initMenu(Menu menu, MenuInflater inflater);
}
