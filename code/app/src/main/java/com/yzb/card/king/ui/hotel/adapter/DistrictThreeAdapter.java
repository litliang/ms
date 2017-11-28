package com.yzb.card.king.ui.hotel.adapter;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 玉兵 on 2017/7/29.
 */

public class DistrictThreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubItemBean.ChildSubItemBean> list = new ArrayList<SubItemBean.ChildSubItemBean>();

    private int threeCurrentIndex = 0;

    private Activity activity;

    public DistrictThreeAdapter(Activity activity) {
        this.activity = activity;
    }

    /**
     * 獲取當前對象
     * @return
     */
    public  SubItemBean.ChildSubItemBean getCurrentObject(){

        SubItemBean.ChildSubItemBean bean = list.get(threeCurrentIndex);

        return bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_item_district_three,null);

        ThreeViewHold oneViewHold = new ThreeViewHold(view);

        return oneViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof ThreeViewHold){//三级

            ThreeViewHold threeViewHold = (ThreeViewHold) holder;

            threeViewHold.initData(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addNewChildList(List<SubItemBean.ChildSubItemBean> threeList) {

        this.list.clear();
        this.list.addAll(threeList);
        threeCurrentIndex = 0;
        notifyDataSetChanged();
    }

    public void clearDataList(){
        this.list.clear();
        notifyDataSetChanged();
    }

    class ThreeViewHold extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private LinearLayout llParent;

        private ImageView ivChecked;

        public ThreeViewHold(View itemView)
        {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

            llParent = (LinearLayout) itemView.findViewById(R.id.llParent);

            ivChecked = (ImageView) itemView.findViewById(R.id.ivChecked);
        }

        public void initData(int position)
        {

            SubItemBean.ChildSubItemBean bean =  list.get(position);

            if(position==threeCurrentIndex){

                bean.setIfDefault(true);
            }else{

                bean.setIfDefault(false);
            }

            tvItemName.setEnabled(bean.isIfDefault());

            if(bean.isIfDefault()){

                ivChecked.setVisibility(View.VISIBLE);

            }else{

                ivChecked.setVisibility(View.INVISIBLE);

            }

            tvItemName.setText(bean.getFilterName());

            llParent.setTag(position);

            llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    int index = (int) v.getTag();

                    SubItemBean.ChildSubItemBean  bean =  list.get(index);

                    boolean flag = bean.isIfDefault();

                    if(flag){

                    }else {

                        threeCurrentIndex = index;

                        notifyDataSetChanged();

                    }

                }
            });

        }
    }

}
