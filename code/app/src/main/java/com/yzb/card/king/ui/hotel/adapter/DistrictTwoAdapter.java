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

public class DistrictTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubItemBean> list = new ArrayList<SubItemBean>();

    private Activity activity;

    private int twoCurrentIndex = 0;

    private  boolean ifThirdColumnFlag = false;

    public DistrictTwoAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.view_item_district_two,null);

        TwoViewHold oneViewHold = new TwoViewHold(view);

        return oneViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof TwoViewHold){//三级

            TwoViewHold twoViewHold = (TwoViewHold) holder;

            twoViewHold.initData(position);

        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    /**
     * 重新加載集合數據
     * @param childList
     */
    public void addNewChildList(List<SubItemBean> childList) {

        checkTIhreeColumnFlag(childList);

        this.list.clear();
        this.list.addAll(childList);
        twoCurrentIndex = 0;
        notifyDataSetChanged();
    }

    /**
     * 檢查集合可有子類集合信息
     * @param childList
     */
    private void checkTIhreeColumnFlag(List<SubItemBean> childList) {

        ifThirdColumnFlag = false;

        for(SubItemBean bean :childList){

            List<SubItemBean.ChildSubItemBean> tempList = bean.getChildList();

            if(tempList!=null && tempList.size()>0){

                ifThirdColumnFlag = true;
                break;
            }

        }

    }

    public boolean isIfThirdColumnFlag() {
        return ifThirdColumnFlag;
    }

    /**
     * 獲取當前對象的子集合
     * @return
     */
    public  List<SubItemBean.ChildSubItemBean> getCurrentObjectChildList(){

       SubItemBean subItemBean = list.get(twoCurrentIndex);

        List<SubItemBean.ChildSubItemBean> chidList = subItemBean.getChildList();

        return chidList;

    }

    public  SubItemBean getCurrentObject(){

        SubItemBean subItemBean = list.get(twoCurrentIndex);

        return  subItemBean;
    }

    class TwoViewHold extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        private LinearLayout llParent;

        private ImageView ivChecked;

        private TextView tvEndWaysLine;

        private  TextView vLine;

        public TwoViewHold(View itemView)
        {
            super(itemView);

            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

            llParent = (LinearLayout) itemView.findViewById(R.id.llParent);

            ivChecked = (ImageView) itemView.findViewById(R.id.ivChecked);

            tvEndWaysLine = (TextView) itemView.findViewById(R.id.tvEndWaysLine);

            vLine = (TextView) itemView.findViewById(R.id.vLine);
        }

        public void initData(int position)
        {

            SubItemBean bean =  list.get(position);

            if(position==twoCurrentIndex){

                bean.setDefault(true);
            }else{

                bean.setDefault(false);
            }

            tvItemName.setEnabled(bean.isDefault());

            if(ifThirdColumnFlag){

                tvEndWaysLine.setVisibility(View.VISIBLE);

                ivChecked.setVisibility(View.GONE);

                vLine.setVisibility(View.INVISIBLE);

            }else {

                tvEndWaysLine.setVisibility(View.INVISIBLE);

                vLine.setVisibility(View.VISIBLE);

                if(bean.isDefault()){

                    ivChecked.setVisibility(View.VISIBLE);

                }else{

                    ivChecked.setVisibility(View.INVISIBLE);

                }
            }


            tvItemName.setText(bean.getFilterName());

            llParent.setTag(position);

            llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int index = (int) v.getTag();
                    SubItemBean bean =  list.get(index);
                    boolean flag = bean.isDefault();

                    if(flag){

                    }else {

                        twoCurrentIndex = index;

                        notifyDataSetChanged();

                        if( dataCallBack!= null){

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
