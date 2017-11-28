package com.yzb.card.king.ui.appwidget.popup.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.SubItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/21
 * 描  述：
 */
public class SubItemadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SubItemBean> brandBeanList;

    private List<SubItemBean> allBrandBeanList;

    private boolean flag = false;

    private Activity activity;

    private boolean mutualList = false;



    public SubItemadapter(Activity activity, List<SubItemBean> brandBeanList, boolean flag)
    {
        this.activity = activity;

        this.brandBeanList = brandBeanList;
    }

    public SubItemadapter(Activity activity, List<SubItemBean> brandBeanList)
    {

        this.activity = activity;

        int size = brandBeanList.size();

        if (size > 4 && !flag) {

            allBrandBeanList = brandBeanList;

            List<SubItemBean> brandBeanListTepm = new ArrayList<>();

            for (int i = 0; i < 4; i++) {

                brandBeanListTepm.add(brandBeanList.get(i));
            }

            this.brandBeanList = brandBeanListTepm;

        } else {
            this.brandBeanList = brandBeanList;
        }

    }

    public void setMutualList(boolean mutualList)
    {

        this.mutualList = mutualList;
    }


    /**
     * 展开数据
     *
     * @param flag
     */
    public void spreadData(boolean flag)
    {

        this.flag = flag;

        this.brandBeanList = allBrandBeanList;

        notifyDataSetChanged();
    }

    /**
     * 收缩数据
     *
     * @param flag
     */
    public void shrinkData(boolean flag)
    {
        this.flag = flag;

        List<SubItemBean> brandBeanListTepm = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            brandBeanListTepm.add(allBrandBeanList.get(i));
        }

        this.brandBeanList = brandBeanListTepm;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(activity).inflate(R.layout.view_activity_month_item, null);


        SubItemadapter.CurrentViewHolder viewHolder = new SubItemadapter.CurrentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof SubItemadapter.CurrentViewHolder) {

            SubItemadapter.CurrentViewHolder holderTemp = (SubItemadapter.CurrentViewHolder) holder;

            holderTemp.initData(position);

        }

    }

    @Override
    public int getItemCount()
    {
        return brandBeanList.size();
    }

    public void addOneData(SubItemBean brandBean) {

        brandBeanList.add(brandBean);

        notifyDataSetChanged();
    }

    public void clearData() {

        brandBeanList.clear();

        notifyDataSetChanged();
    }

    class CurrentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTabName;

        private View itemView;

        public CurrentViewHolder(View itemView)
        {
            super(itemView);

            this.itemView = itemView;

            tvTabName = (TextView) itemView.findViewById(R.id.tvTabName);

        }

        public void initData(int position)
        {
            SubItemBean bean = brandBeanList.get(position);

            tvTabName.setText(bean.getFilterName());

            tvTabName.setEnabled(bean.isDefault());

            itemView.setTag(position);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    int index = (int) v.getTag();

                    if (mutualList) {

                        int lenghtTemp = brandBeanList.size();

                        for( int a = 0 ; a < lenghtTemp ; a++){

                            SubItemBean beanTemp = brandBeanList.get(a);

                            if(a == index){

                                beanTemp.setDefault(true);

                            }else {

                                beanTemp.setDefault(false);
                            }

                        }

                        notifyDataSetChanged();

                    } else {

                        SubItemBean beanTemp = brandBeanList.get(index);

                        boolean flag = !beanTemp.isDefault();

                        tvTabName.setEnabled(flag);

                        beanTemp.setDefault(flag);

                        if (itemDataCallBack != null) {

                            itemDataCallBack.onSelectorItem(beanTemp);

                        }
                    }

                }
            });
        }
    }

    private ItemDataCallBack itemDataCallBack;

    public void setItemDataCallBack(ItemDataCallBack itemDataCallBack)
    {

        this.itemDataCallBack = itemDataCallBack;
    }




    public interface ItemDataCallBack {

        public void onSelectorItem(SubItemBean subItemBean);

    }
}
