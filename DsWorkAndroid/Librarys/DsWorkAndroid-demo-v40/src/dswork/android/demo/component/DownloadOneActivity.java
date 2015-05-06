package dswork.android.demo.component;

import dswork.android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class DownloadOneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_one);
		getActionBar().setHomeButtonEnabled(true);//actionbar主按键可以被点击
		getActionBar().setDisplayHomeAsUpEnabled(true);//显示向左的图标
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.download_one, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
			case R.id.action_settings:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
