package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.transport.bean.ShipAgent;
import com.yzb.card.king.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinsg on 2016/5/30.
 * 船票代理商
 */
public class ShipAgentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private LayoutInflater inflater;
    private List<ShipAgent> dataList;
    private OnAdvanceListener onAdvanceListener;
    private Context context;

    public List<ShipAgent> getDataList()
    {
        return dataList;
    }

    public void appendData(List<ShipAgent> dataList)
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

    public void setOnAdvanceListener(OnAdvanceListener listener)
    {
        this.onAdvanceListener = listener;
    }

    public interface OnAdvanceListener
    {
        void onAdvance(int parentPosition, int childPosition);
    }

    public ShipAgentAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.transport_ship_agent_parent_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof MyViewHolder)
        {
            final MyViewHolder viewHolder = (MyViewHolder) holder;
            final ShipAgent shipAgent = dataList.get(position);
            viewHolder.tvSeatTypeName.setText(shipAgent.seatName);
            viewHolder.tvPrice.setText(shipAgent.price + "");
            viewHolder.tvSeatNum.setText(shipAgent.allowance + "");

            viewHolder.tvArrow.setSelected(shipAgent.isExpand);
            viewHolder.lvChild.setVisibility(shipAgent.isExpand ? View.VISIBLE : View.GONE);

            //伸展点击；=====================
            viewHolder.llArrow.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    viewHolder.lvChild.setVisibility(shipAgent.isExpand ? View.GONE : View.VISIBLE);
                    shipAgent.isExpand = !shipAgent.isExpand;
                    viewHolder.tvArrow.setSelected(shipAgent.isExpand);
                }
            });
            ChildAdapter adapter = new ChildAdapter(shipAgent.supplierList, position);
            viewHolder.lvChild.setAdapter(adapter);
            ViewUtil.setListViewHeightBasedOnChildren(viewHolder.lvChild);
        }
    }

    @Override
    public int getItemCount()
    {
        return dataList == null ? 0 : dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public View root;
        public TextView tvSeatTypeName, tvPrice, tvSeatNum, tvArrow;
        public ListView lvChild;
        public LinearLayout llArrow;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvSeatTypeName = (TextView) itemView.findViewById(R.id.tvSeatTypeName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvSeatNum = (TextView) itemView.findViewById(R.id.tvSeatNum);
            tvArrow = (TextView) itemView.findViewById(R.id.tvArrow);
            lvChild = (ListView) itemView.findViewById(R.id.lvChild);
            llArrow = (LinearLayout) itemView.findViewById(R.id.llArrow);
            root = itemView;
        }
    }

    class ChildAdapter extends BaseAdapter
    {
        private List<ShipAgent.Supplier> list;
        private int parentPosition;

        public ChildAdapter(List<ShipAgent.Supplier> list, int parentPosition)
        {
            this.list = list;
            this.parentPosition = parentPosition;
        }

        @Override
        public int getCount()
        {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ChildViewHolder holder;
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.transport_ship_agent_child_item, parent, false);
                holder = new ChildViewHolder(convertView);
                convertView.setTag(holder);
            } else
            {
                holder = (ChildViewHolder) convertView.getTag();
            }
            ShipAgent.Supplier supplier = list.get(position);
            holder.tvPrice.setText(supplier.price + "");
            holder.tvSeatNum.setText(supplier.allowance + "");
            holder.tvSupplierName.setText(supplier.supplierName);

            holder.tvAdvance.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (onAdvanceListener != null)
                    {
                        onAdvanceListener.onAdvance(parentPosition, position);
                    }
                }
            });
            return holder.rootView;
        }

        class ChildViewHolder
        {
            public TextView tvSupplierName, tvInstruction, tvPrice, tvSeatNum, tvAdvance;
            public View rootView;

            public ChildViewHolder(View v)
            {
                rootView = v;
                tvSupplierName = (TextView) v.findViewById(R.id.tvSupplierName);
                tvInstruction = (TextView) v.findViewById(R.id.tvInstruction);
                tvPrice = (TextView) v.findViewById(R.id.tvPrice);
                tvSeatNum = (TextView) v.findViewById(R.id.tvSeatNum);
                tvAdvance = (TextView) v.findViewById(R.id.tvAdvance);
            }
        }
    }
}
