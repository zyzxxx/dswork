package dswork.android.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dswork.android.R;

public class FragmentTabHostFragment extends  Fragment
{
	private FragmentTabHost mTabHost;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabhost);

        mTabHost.addTab(mTabHost.newTabSpec("f1").setIndicator("Fragment1"),
                Fragment1.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("f2").setIndicator("Fragment2"),
        		Fragment2.class, null);

        return mTabHost;
	}
	
	
}
