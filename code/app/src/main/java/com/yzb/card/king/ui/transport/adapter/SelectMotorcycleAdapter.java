package com.yzb.card.king.ui.transport.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.discount.bean.BusTypeBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;

import org.xutils.x;

import java.util.List;

/**
 * 类名： SelectMotorcycleAdapter
 * 作者： Lei Chao.
 * 日期： 2016-09-07
 * 描述：
 */
public class SelectMotorcycleAdapter extends RecyclerView.Adapter
{

    private int logicFlag = 0;

    private int currentIndex = -1;

    private List<BusTypeBean> busTypeList;

    private Activity mContext;

    private LayoutInflater inflater;

    public SelectMotorcycleAdapter(List<BusTypeBean> busTypeLists, Activity context , int flag)
    {
        this.busTypeList = busTypeLists;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.logicFlag = flag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.view_adapter_motorcycle_type, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MyViewHolder viewHold = (MyViewHolder) holder;
        BusTypeBean busTypeBean = this.busTypeList.get(position);
        //图片加载

        viewHold.ivArrow.setTag(position);

        viewHold.llParent.setTag(position);

        x.image().bind(viewHold.ivBusImage, ServiceDispatcher.getImageUrl(busTypeBean.imageCode));

        //车型
        viewHold.tvCarType.setText(busTypeBean.carTypeName);

        //租金
        viewHold.tvCarMoney.setText(busTypeBean.price);

        //车座
        viewHold.tvCarPort.setText("×" + busTypeBean.personNumber);
        //行李数
        viewHold.tvLuggageNumber.setText("×" + busTypeBean.luggageNumber);
        //
        viewHold.tvSameLevelCar.setText(busTypeBean.sameLevelCar);

        viewHold.ivArrow.setOnClickListener(adapterListener);

        if (currentIndex == position)
        {

            viewHold.ivArrow.setBackgroundResource(R.mipmap.icon_show_more_top);

            viewHold.llSameLevel.setVisibility(View.VISIBLE);


            //展开数据
            WholeSelectAdapter wholeListViewApdate = new WholeSelectAdapter(busTypeBean, mContext,logicFlag);
            viewHold.wvAll.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            viewHold.wvAll.setAdapter(wholeListViewApdate);

        } else
        {

            viewHold.ivArrow.setBackgroundResource(R.mipmap.icon_show_more_down);

            viewHold.llSameLevel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return this.busTypeList.size() == 0 ? 0 : this.busTypeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivBusImage;

        TextView tvCarType;

        TextView tvCarMoney;

        TextView tvCarPort;

        TextView tvLuggageNumber;

        TextView tvSameLevelCar;

        ImageView ivArrow;

        LinearLayout llSameLevel, llParent;

        WholeRecyclerView wvAll;

        public MyViewHolder(View convertView)
        {
            super(convertView);
            ivBusImage = (ImageView) convertView.findViewById(R.id.ivBusImage);

            tvCarType = (TextView) convertView.findViewById(R.id.tvCarType);

            tvCarMoney = (TextView) convertView.findViewById(R.id.tvCarMoney);

            tvCarPort = (TextView) convertView.findViewById(R.id.tvCarPort);

            tvLuggageNumber = (TextView) convertView.findViewById(R.id.tvLuggageNumber);

            tvSameLevelCar = (TextView) convertView.findViewById(R.id.tvSameLevelCar);

            ivArrow = (ImageView) convertView.findViewById(R.id.ivArrow);

            llSameLevel = (LinearLayout) convertView.findViewById(R.id.llSameLevel);

            llParent = (LinearLayout) convertView.findViewById(R.id.llParent);


            // viewHold.llParent.setOnClickListener(adapterListener);

            wvAll = (WholeRecyclerView) convertView.findViewById(R.id.wvAll);
        }
    }

    private View.OnClickListener adapterListener = new View.OnClickListener()
    {


        @Override
        public void onClick(View v)
        {

            int temp = (int) v.getTag();

            switch (v.getId())
            {

                case R.id.ivArrow:

                    if (currentIndex == temp)
                    {

                        currentIndex = -1;

                    } else
                    {

                        currentIndex = temp;
                    }

                    if (mOnOpenClickListener != null)
                    {
                        mOnOpenClickListener.onOpen();
                    }

                    break;
                case R.id.llParent:

                    Intent intentShop = new Intent(mContext, SelfDriveLogicActivity.class);

                    intentShop.putExtra("stepIndex", 4);

                    mContext.startActivity(intentShop);

                    BusTypeBean busTypeBean = busTypeList.get(temp);

                    mContext.finish();

                    RentCarLogicManager.getInstance().setBusTypeBean(busTypeBean);

                    break;

                default:
                    break;
            }

        }
    };

    public interface OnOpenClickListener
    {
        void onOpen();
    }

    private OnOpenClickListener mOnOpenClickListener;

    public void setOnOpenListener(OnOpenClickListener onOpenListener)
    {
        this.mOnOpenClickListener = onOpenListener;
    }


}