package com.yzb.card.king.ui.appwidget.popup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.ui.hotel.adapter.DistrictAdapterDataCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/21
 * 描  述：
 */
public class TicketDiscountTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CatalogueTypeBean> list = new ArrayList<CatalogueTypeBean>();

    private int currentIndex = 0;

    private Activity activity;

    private DistrictAdapterDataCallBack dataCallBack;

    public TicketDiscountTypeAdapter(Activity activity){

        this.activity = activity;

    }

    /**
     * 重新添加新的集合數據
     * @param list
     */
    public  void addNewDataList(List<CatalogueTypeBean> list){

        this.list.clear();

        this.list.addAll(list);

        notifyDataSetChanged();
    }


    public void setDataCallBack(DistrictAdapterDataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_item_ticket_discount,null);

        TicketDiscountViewHold oneViewHold = new TicketDiscountViewHold(view);

        return oneViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if( holder instanceof  TicketDiscountViewHold){

            TicketDiscountViewHold ticketDiscountViewHold = (TicketDiscountViewHold) holder;

            ticketDiscountViewHold.initData(position);
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    class TicketDiscountViewHold extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private LinearLayout llParent;


        public TicketDiscountViewHold(View itemView)
        {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

            llParent = (LinearLayout) itemView.findViewById(R.id.llParent);


        }

        public void initData(int position)
        {

            CatalogueTypeBean bean =  list.get(position);

            if(position==currentIndex){

                bean.setIfDefault(true);
            }else{

                bean.setIfDefault(false);
            }

            tvItemName.setEnabled(bean.isIfDefault());

            tvItemName.setText(bean.getTypeName());


            llParent.setTag(position);

            llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int index = (int) v.getTag();
                    CatalogueTypeBean bean =  list.get(index);
                    boolean flag = bean.isIfDefault();

                    if(flag){

                    }else {

                        currentIndex = index;

                        notifyDataSetChanged();

                        if( dataCallBack!= null){

                            dataCallBack.onClickItemData(bean);
                        }
                    }

                }
            });

        }
    }

}
