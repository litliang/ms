package com.yzb.card.king.ui.appwidget.popup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.ui.hotel.adapter.DistrictAdapterDataCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：优惠内容
 * 作  者：Li Yubing
 * 日  期：2017/9/21
 * 描  述：
 */
public class TicketDiscountContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubItemBean> list = new ArrayList<SubItemBean>();

    private Activity activity;

    private int twoCurrentIndex = 0;

    public TicketDiscountContentAdapter(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_item_discount_content, null);

        TwoViewHold twoViewHold = new TwoViewHold(view);

        return twoViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if(holder instanceof TwoViewHold ){

            TwoViewHold twoViewHold = (TwoViewHold) holder;

            twoViewHold.initData(position);

        }
    }

    /**
     * 重新加載集合數據
     * @param childList
     */
    public void addNewChildList(List<SubItemBean> childList) {


        this.list.clear();
        this.list.addAll(childList);
        twoCurrentIndex = 0;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class TwoViewHold extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private LinearLayout llParent;

        private ImageView ivChecked;


        private TextView vLine;

        public TwoViewHold(View itemView)
        {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

            llParent = (LinearLayout) itemView.findViewById(R.id.llParent);

            ivChecked = (ImageView) itemView.findViewById(R.id.ivChecked);

            vLine = (TextView) itemView.findViewById(R.id.vLine);
        }

        public void initData(int position)
        {

            SubItemBean bean = list.get(position);

            if (position == twoCurrentIndex) {

                bean.setDefault(true);
            } else {

                bean.setDefault(false);
            }

            tvItemName.setEnabled(bean.isDefault());
            ivChecked.setEnabled(bean.isDefault());

            tvItemName.setText(bean.getFilterName());

            llParent.setTag(position);

            llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int index = (int) v.getTag();
                    SubItemBean bean = list.get(index);
                    boolean flag = bean.isDefault();

                    if (flag) {

                    } else {

                        twoCurrentIndex = index;

                        notifyDataSetChanged();

                        if (dataCallBack != null) {

                            dataCallBack.onClickItemData(bean);
                        }
                    }


                }
            });

        }
    }

    private DistrictAdapterDataCallBack dataCallBack;


    public void setDataCallBack(DistrictAdapterDataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }
}
