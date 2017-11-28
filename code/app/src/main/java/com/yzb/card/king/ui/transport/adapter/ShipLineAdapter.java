package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.bean.ShipRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：航线；
 *
 * @author:gengqiyun
 * @date: 2016/9/8
 */
public class ShipLineAdapter extends BaseExpandableListAdapter
{
    private final Context context;
    private final ExpandableListView listView;
    private List<ShipRoute> dataList;
    private LayoutInflater inflater;

    public ShipLineAdapter(Context context, List<ShipRoute> dataList, ExpandableListView listView)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.listView = listView;
    }
    public List<ShipRoute> getDataList()
    {
        return dataList;
    }

    public void appendData(List<ShipRoute> dataList)
    {
        if (dataList == null)
        {
            return;
        }
        if (this.dataList == null)
        {
            this.dataList = new ArrayList<>();
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear()
    {
        if (this.dataList != null)
        {
            this.dataList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return 1;
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
        if (!isExpanded)
        {
            //展开组
            listView.expandGroup(groupPosition);
        }

        GroupViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.ship_route_parent_item, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (GroupViewHolder) convertView.getTag();
        }
        ShipRoute shipRoute = dataList.get(groupPosition);
        holder.tViewGroupName.setText(shipRoute.title);

        return holder.parentRootView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        HotViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.transport_ship_route_child_item, parent, false);
            holder = new HotViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (HotViewHolder) convertView.getTag();
        }

        List<ShipRoute.Route> routes = dataList.get(groupPosition).lineList;
        holder.glRoute.removeAllViews();

        if (routes != null)
        {
            for (final ShipRoute.Route route : routes)
            {
                View view = inflater.inflate(R.layout.ship_route_grid_item, null);
                TextView textView = (TextView) view.findViewById(R.id.tvRoute);
                textView.setText(route.startCityName + "—" + route.endCityName);
                if (groupPosition != 0)
                    textView.setBackgroundResource(R.drawable.bg_round_corner_blue);
                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (childClickCallBack != null)
                        {
                            childClickCallBack.callBack(route);
                        }
                    }
                });
                holder.glRoute.addView(view);
            }
        }
        return holder.childRootView;
    }

    private ICallBack childClickCallBack;

    public interface ICallBack
    {
        void callBack(ShipRoute.Route route);
    }

    public void setChildClickCallBack(ICallBack childClickCallBack)
    {
        this.childClickCallBack = childClickCallBack;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

    class GroupViewHolder
    {
        TextView tViewGroupName;
        View parentRootView;

        public GroupViewHolder(View parent)
        {
            this.parentRootView = parent;
            tViewGroupName = (TextView) parent.findViewById(R.id.tView_group_name);
        }
    }

    class HotViewHolder
    {
        GridLayout glRoute;
        View childRootView;

        public HotViewHolder(View parent)
        {
            this.childRootView = parent;
            glRoute = (GridLayout) parent.findViewById(R.id.glRoute);
        }
    }

}
