package com.yzb.card.king.ui.transport.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.discount.bean.BusTypeBean;
import com.yzb.card.king.ui.discount.bean.BusTypeGradeBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;
import com.yzb.card.king.ui.user.LoginActivity;


import java.util.List;

/**
 * 类名： WholeSelectAdapter
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public class WholeSelectAdapter extends RecyclerView.Adapter
{

    private List<BusTypeGradeBean> list;

    private BusTypeBean busTypeBean;

    private LayoutInflater inflater;

    private Activity mActivity;

    private int logicFlag = 0;

    public WholeSelectAdapter(BusTypeBean busTypeBean, Activity activity, int flag)
    {

        this.list = busTypeBean.supplierList;
        this.mActivity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.busTypeBean = busTypeBean;
        this.logicFlag = flag;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new BaseViewHolder(inflater.inflate(R.layout.view_adapter_samecar, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        BaseViewHolder viewHold = (BaseViewHolder) holder;

        BusTypeGradeBean temp = this.list.get(position);

        viewHold.llSelectedItem.setTag(position);

        viewHold.tvCarTypeAdapter.setText(temp.supplierName);

        viewHold.tvCarMoneyAdapter.setText(temp.price);

        viewHold.tvSuccessRate.setText(temp.successRatio + "%");

        viewHold.tvFen.setText(temp.vote + mActivity.getString(R.string.tv_selfdrive_score));

        viewHold.llSelectedItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (UserManager.getInstance().getUserBean() == null)
                {

                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    mActivity.startActivity(intent);
                    return;
                }

                int index = (int) v.getTag();

                BusTypeGradeBean busTypeGradeBeanTemp = list.get(index);

                RentCarLogicManager.getInstance().setBusTypeGradeBean(busTypeGradeBeanTemp);

                RentCarLogicManager.getInstance().setBusTypeBean(busTypeBean);

                if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG)
                {

                    Intent intentShop1 = new Intent(mActivity,
                            SelfDriveLogicActivity.class);

                    intentShop1.putExtra("stepIndex", 4);

                    mActivity.startActivity(intentShop1);


                } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG)
                {

                    Intent intentShop1 = new Intent(mActivity,
                            SelfDriveLogicActivity.class);

                    intentShop1.putExtra("stepIndex", 4);

                    intentShop1.putExtra("logic_flag", logicFlag);

                    mActivity.startActivity(intentShop1);

                }

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return this.list.size() == 0 ? 0 : this.list.size();
    }
}