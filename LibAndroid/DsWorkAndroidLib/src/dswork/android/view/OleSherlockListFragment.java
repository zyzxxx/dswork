package dswork.android.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class OleSherlockListFragment extends SherlockListFragment implements InitSherlockFragment
{
	/**
	 * 重载此方法重新定义MenuItem点击事件
	 * @param item
	 */
	protected boolean initMenuItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				getActivity().finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	///////////////////////官方Fragment重载方法////////////////////////
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initMainView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		initMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return initMenuItemSelected(item);
	}
	
}
