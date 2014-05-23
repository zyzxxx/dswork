package dswork.android.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dswork.android.R;

public class Fragment3  extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_3, container, false);
		//嵌套其他Fragment(如：TabFragment)
		getChildFragmentManager().beginTransaction().replace(R.id.fragment3_contrainer, new FragmentTabHostFragment()).commit();
		return convertView;
	}
}
