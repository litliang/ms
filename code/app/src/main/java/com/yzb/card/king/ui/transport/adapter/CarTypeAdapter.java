package com.yzb.card.king.ui.transport.adapter;

import android.content.Context;
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
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.List;

/**
 * 类名： CarTypeAdapter
 * 作者： Lei Chao.
 * 日期： 2016-09-05
 * 描述： 接送机 和 接送火车的适配器
 */
public class CarTypeAdapter extends RecyclerView.Adapter
{

    private List<SpecialCar> mSpecialCars;

    private LayoutInflater inflater;

    private Context mContext;

    private int currentIndex = -1;
    private SpecialCar sepcail;

    public CarTypeAdapter(Context context, List<SpecialCar> specialCars)
    {
        this.inflater = LayoutInflater.from(context);
        this.mSpecialCars = specialCars;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHodler(inflater.inflate(R.layout.view_adapter_motorcycle_type, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHodler)
        {
            MyViewHodler viewHold = (MyViewHodler) holder;

            viewHold.ivArrow.setTag(position);

            viewHold.llParent.setTag(position);

            SpecialCar busTypeBean = this.mSpecialCars.get(position);
            //图片加载

            x.image().bind(viewHold.ivBusImage, ServiceDispatcher.getImageUrl(busTypeBean.imageCode), GlobalApp.getInstance().getImageOptionsLogo());

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

            // 进入订单页面
            viewHold.ivArrow.setOnClickListener(adapterListener);

            // 进入附近门店
            //viewHold.llParent.setOnClickListener(adapterListener);

            if (currentIndex == position)
            {

                viewHold.ivArrow.setBackgroundResource(R.mipmap.icon_show_more_top);

                viewHold.llSameLevel.setVisibility(View.VISIBLE);

                //展开数据
                WholeAdapter wholeListViewApdate = new WholeAdapter(busTypeBean, mContext);
                wholeListViewApdate.setSpecialCard(getGloabeSepcail());
                viewHold.wvAll.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                viewHold.wvAll.setAdapter(wholeListViewApdate);

            } else
            {

                viewHold.ivArrow.setBackgroundResource(R.mipmap.icon_show_more_down);

                viewHold.llSameLevel.setVisibility(View.GONE);
            }


        }

    }


    public void setGloabeSepcail(SpecialCar sepcailCar)
    {
        this.sepcail = sepcailCar;
    }

    public SpecialCar getGloabeSepcail()
    {
        return this.sepcail;
    }


    @Override
    public int getItemCount()
    {
        return this.mSpecialCars.size() == 0 ? 0 : this.mSpecialCars.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder
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

        public MyViewHodler(View convertView)
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

            wvAll = (WholeRecyclerView) convertView.findViewById(R.id.wvAll);
        }
    }

    protected View.OnClickListener adapterListener = new View.OnClickListener()
    {


        @Override
        public void onClick(View v)
        {

            int temp = (int) v.getTag();

            switch (v.getId())
            {

                case R.id.ivArrow:


                    LogUtil.i("点击了展开图标 -------- " + currentIndex);

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

                    intentShop.putExtra("stepIndex", getGloabeSepcail().source);

                    mContext.startActivity(intentShop);

                    Object busTypeBean = mSpecialCars.get(temp);

                    RentCarLogicManager.getInstance().setBusTypeBeanObject(busTypeBean);

                    break;

                default:
                    break;
            }

        }
    };

    /**
     * 打开车辆详情的回调
     */
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