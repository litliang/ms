package com.yzb.card.king.ui.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.CatalogueTypeBean;
import com.yzb.card.king.bean.SubItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 玉兵 on 2017/7/29.
 */

public class DistrictOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CatalogueTypeBean> list = new ArrayList<CatalogueTypeBean>();

    private Activity activity;

    private DistrictAdapterDataCallBack dataCallBack;

    private int currentIndex = 0;

    public DistrictOneAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setDataCallBack(DistrictAdapterDataCallBack dataCallBack) {
        this.dataCallBack = dataCallBack;
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

    /**
     * 獲取當前選項子集合
     * @return
     */
    public  List<SubItemBean> getCurrentObjectChildList(){

        CatalogueTypeBean catalogueTypeBean = list.get(currentIndex);

        List<SubItemBean>  childList =   catalogueTypeBean.getChildList();

        return  childList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_item_district,null);

        OneViewHold oneViewHold = new OneViewHold(view);

        return oneViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof OneViewHold){//一级

            OneViewHold oneViewHold = (OneViewHold) holder;

            oneViewHold.initData(position);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void callTwoChildrenData() {

        if( dataCallBack!= null){

            CatalogueTypeBean bean =  list.get(currentIndex);
            dataCallBack.onClickItemData(bean);
        }
    }

    class OneViewHold extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private LinearLayout llParent;


        public OneViewHold(View itemView)
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
