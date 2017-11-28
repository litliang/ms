package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.discount.bean.BusTypeGradeBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.transport.activity.ShuttleOrderActivity;
import com.yzb.card.king.ui.transport.bean.SpecialCar;

import java.util.List;

/**
 * 类名： WholeAdapter
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述： 接送机 和 接送火车 item 点开时候的 listView
 */
public class WholeAdapter extends RecyclerView.Adapter
{

    private List<BusTypeGradeBean> list;

    private SpecialCar busTypeBean;

    private LayoutInflater inflater;

    private SpecialCar gloabSpecialCar;

    private Context mContext;

    public WholeAdapter(SpecialCar busTypeBean1, Context context)
    {
        this.list = busTypeBean1.supplierList;
        this.busTypeBean = busTypeBean1;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setSpecialCard(SpecialCar specialCard)
    {
        this.gloabSpecialCar = specialCard;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new BaseViewHolder(inflater.inflate(R.layout.view_adapter_samecar, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof BaseViewHolder)
        {
            BaseViewHolder viewHold = (BaseViewHolder) holder;

            BusTypeGradeBean temp = this.list.get(position);

            viewHold.llSelectedItem.setTag(position);

            viewHold.tvCarTypeAdapter.setText(temp.supplierName);

            viewHold.tvCarMoneyAdapter.setText(temp.price);

            viewHold.tvSuccessRate.setText(temp.successRatio + "%");

            viewHold.tvFen.setText(temp.vote + mContext.getResources().getString(R.string.tv_selfdrive_score));

            viewHold.llSelectedItem.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (UserManager.getInstance().isLogin())
                    {

                        int index = (int) v.getTag();

                        BusTypeGradeBean busTypeGradeBean = list.get(index);

                        Intent intentShop1 = new Intent(mContext, ShuttleOrderActivity.class);

                        busTypeBean.startPlace = gloabSpecialCar.startPlace;
                        busTypeBean.endPlace = gloabSpecialCar.endPlace;
                        busTypeBean.startDate = gloabSpecialCar.startDate;

                        intentShop1.putExtra("busTypeGradeBean", busTypeGradeBean);
                        intentShop1.putExtra("specialCar", busTypeBean);

                        mContext.startActivity(intentShop1);
                    } else
                    {
                        new GoLoginDailog(mContext).show();
                    }
                    // finish();
                }
            });

        }

    }

    @Override
    public int getItemCount()
    {
        return this.list.size() == 0 ? 0 : this.list.size();
    }



}