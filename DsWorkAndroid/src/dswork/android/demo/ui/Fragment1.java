package dswork.android.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dswork.android.R;

public class Fragment1  extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View convertView = inflater.inflate(R.layout.fragment_1, container, false);
//		getChildFragmentManager().beginTransaction().replace(R.id.fragment1_contrainer, new TabFragment()).commit();
		return convertView;
	}
}
