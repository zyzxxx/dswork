package dswork.android.demo.component.downloadlist;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ole on 15/5/31.
 */
public class ViewHolder
{
    private View mConvertView;
    private int mPos;
    private SparseArray<View> mViews;

    public ViewHolder(Context ctx, ViewGroup parent, int layoutId, int pos)
    {
        this.mPos = pos;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(ctx).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context ctx, View convertView, ViewGroup parent, int layoutId, int pos)
    {
        if(convertView == null)
        {
            return new ViewHolder(ctx, parent, layoutId, pos);
        }
        else
        {
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.mPos = pos;
            return holder;
        }
    }

    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if(view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }
}
