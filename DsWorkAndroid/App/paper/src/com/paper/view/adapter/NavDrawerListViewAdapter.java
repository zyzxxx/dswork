package com.paper.view.adapter;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paper.R;
import com.paper.model.PaperCategory;
import com.paper.model.PaperModel;
import dswork.android.lib.core.util.InjectUtil;
import dswork.android.lib.core.util.InjectUtil.InjectView;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerListViewAdapter extends BaseExpandableListAdapter
{
	//标记要注入的控件
	@InjectView(id=R.id.section_label) TextView mGroupSectionLabel;
	@InjectView(id=R.id.section_arrow) ImageView mGroupSectionArrow;
	@InjectView(id=R.id.section_label) TextView mChildSectionLabel;
	@InjectView(id=R.id.item_id) TextView mChildSectionId;
	
	private Context ctx;
	private LayoutInflater inflater;
	private PaperCategory[] mGroups = null;
	private PaperModel[][] mChilds = null;
	
	@SuppressWarnings("unchecked")
	public NavDrawerListViewAdapter(Context ctx, String menuJson)
	{
		this.ctx = ctx;
		this.inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Gson gson = new Gson();
		List<PaperCategory> groupList = (List<PaperCategory>)gson.fromJson(menuJson,  new TypeToken<List<PaperCategory>>(){}.getType());
		mGroups = new PaperCategory[groupList.size()];
		mChilds = new PaperModel[groupList.size()][];
		for(int i=0; i<mGroups.length; i++)
		{
			mGroups[i] = groupList.get(i);
			mChilds[i] = new PaperModel[mGroups[i].getPaperModelList().size()];
			System.out.println(mGroups[i].getName());
			for(int j=0; j<mGroups[i].getPaperModelList().size(); j++)
			{
				mChilds[i][j] = mGroups[i].getPaperModelList().get(j);
				System.out.println("-"+mGroups[i].getPaperModelList().get(j).getName());
			}
		}
		//保存导航菜单的首项section_id、section_name至SharedPreferences
		saveFirstSectionId(mChilds[0][0].getId(), mChilds[0][0].getName());
	}
	
	//保存导航菜单的首项section_id、section_name至SharedPreferences
	private void saveFirstSectionId(long section_id, String section_name)
	{
		SharedPreferences prefsNavSetting = ctx.getSharedPreferences(ctx.getString(R.string.prefs_nav_setting), 0);
		SharedPreferences.Editor editor = prefsNavSetting.edit();
		editor.putLong("first_section_id", section_id);
		editor.putString("first_section_name", section_name);
		editor.commit();
	}

	@Override
	public int getGroupCount()
	{
		return mGroups.length;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return mChilds[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return null;
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		convertView = inflater.inflate(R.layout.nav_drawer_group, null);
		InjectUtil.injectView(this, convertView);//注入控件
		mGroupSectionLabel.setText(mGroups[groupPosition].getName());
		//判断isExpanded就可以控制是按下还是关闭，同时更换箭头图片 
        if(isExpanded){
        	mGroupSectionArrow.setImageResource(R.drawable.arrow_down);
        }else{
        	mGroupSectionArrow.setImageResource(R.drawable.arrow_right);
        }
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		convertView = inflater.inflate(R.layout.nav_drawer_child, null);
		InjectUtil.injectView(this, convertView);//注入控件
		mChildSectionLabel.setText(mChilds[groupPosition][childPosition].getName());
		mChildSectionId.setText(String.valueOf(mChilds[groupPosition][childPosition].getId()));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}
}
