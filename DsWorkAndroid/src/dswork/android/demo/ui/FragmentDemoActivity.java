package dswork.android.demo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dswork.android.R;

public class FragmentDemoActivity extends FragmentActivity 
{
	Button b1, b2, b3;
	
	private void initView()
	{
		b1 = (Button) findViewById(R.id.btn_f1);
		b2 = (Button) findViewById(R.id.btn_f2);
		b3 = (Button) findViewById(R.id.btn_f3);
		
		b1.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)//跳转动画
				.replace(R.id.fragment_contrainer, new Fragment1())
				.commit();
			}
		});
		b2.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)//跳转动画
				.replace(R.id.fragment_contrainer, new Fragment2())
				.commit();
			}
		});
		b3.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)//跳转动画
				.replace(R.id.fragment_contrainer, new Fragment3())
				.commit();
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_demo);
	
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contrainer, new Fragment1()).commit();
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fragment_demo, menu);
		return true;
	}

}
