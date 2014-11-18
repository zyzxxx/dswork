package dswork.android.lib.view.v40;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class OleActivity extends Activity implements InitActivity
{
	/**
	 * 重载此方法重新定义MenuItem点击事件
	 * @param item
	 */
	protected void initMenuItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
		}
	}
	///////////////////////官方Activity重载方法////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMainView();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		initMenu(menu);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		initMenuItemSelected(item);
		return super.onOptionsItemSelected(item);
	}
}
