package dswork.android.demo.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import dswork.android.R;

public class HtmlTabActivity extends Activity {
	private WebView webView;

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_html_tab);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键
		
		webView = (WebView)this.findViewById(R.id.webView);
		webView.loadUrl("file:///android_asset/tab.html");
		webView.getSettings().setJavaScriptEnabled(true);//允许执行js代码
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.alphabet, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId())
		{
			case android.R.id.home://返回
				this.finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
