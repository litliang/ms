package com.yzb.card.king.ui.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelLabelBean;
import com.yzb.card.king.bean.ticket.BankInfo;
import com.yzb.card.king.bean.travel.TravelLineProductBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.DiscBankDialog;
import com.yzb.card.king.ui.appwidget.popup.TravelDetailTripPop;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.ui.travel.fragment.TravelLineFragment;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 玉兵 on 2016/11/23.
 */

public class TravelLineListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private LayoutInflater inflater;

    private List<TravelLineProductBean> list;

    private Context context;

    private Handler dataHandler;

    private TravelLineFragment fragment;

    public TravelLineListAdapter(TravelLineFragment fragment)
    {

        this.fragment = fragment;

        context =  fragment.getContext();

        inflater = LayoutInflater.from(context);

        list = new ArrayList<TravelLineProductBean>();

    }

    public void setDataHandler(Handler dataHandler)
    {

        this.dataHandler = dataHandler;

    }

    /**
     * 添加无数据提示
     */
    public void addNoDataPrompt(String message)
    {
        list.clear();
        TravelLineProductBean bean = new TravelLineProductBean();
        bean.setViewType(1);
        bean.setDataPrompt(message);
        list.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 添加刷新数据
     *
     * @param newList
     */
    public void addRefresh(List<TravelLineProductBean> newList)
    {

        list.clear();

        list.addAll(newList);

        notifyDataSetChanged();
    }

    /**
     * 添加更多银行提示试图
     *
     * @param newList
     */
    public void addMoreBankView(List<TravelLineProductBean> newList)
    {

        list.clear();
        list.addAll(newList);
        TravelLineProductBean bean = new TravelLineProductBean();
        bean.setViewType(2);
        list.add(bean);
        notifyDataSetChanged();

    }

    /**
     * 用户无银行卡信息或者银行卡无优惠信息
     *
     * @param message
     */
    public void addNoCardDataView(String message)
    {

        list.clear();
        TravelLineProductBean bean = new TravelLineProductBean();
        bean.setDataPrompt(message);
        bean.setViewType(3);
        list.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 更多银行---刷新添加数据
     *
     * @param list
     */
    public void addMoreBankRefresh(List<TravelLineProductBean> list)
    {

        this.list.remove(getItemCount() - 1);

        this.list.addAll(list);

        notifyDataSetChanged();
    }

    /**
     * 更多银行 --- 加载更多数据
     *
     * @param list
     */
    public void addMoreBankMoreInfo(List<TravelLineProductBean> list)
    {

        this.list.addAll(list);

        notifyDataSetChanged();
    }


    /**
     * 添加更多数据
     *
     * @param newList
     */
    public void addLoadMoreData(List<TravelLineProductBean> newList)
    {

        list.addAll(newList);

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position)
    {
        TravelLineProductBean travelLineProductBean = list.get(position);

        int viewType = travelLineProductBean.getViewType();

        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        if (viewType == 1)
        {//无数据信息显示视图

            return new NoViewHolder(inflater.inflate(R.layout.view_no_data, parent, false));

        } else if (viewType == 2)
        {//更多银行提示试图

            return new OtherMoreBankView(inflater.inflate(R.layout.view_bank_other_more, parent, false));

        } else if (viewType == 3)
        {//表示无卡信息和有卡无优惠信息试图

            return new BankCardInfoViewHolder(inflater.inflate(R.layout.view_bank_discount_other, parent, false));

        } else
        {//正常数据显示视图

            return new MyViewHolder(inflater.inflate(R.layout.listview_travel_list, parent, false));
        }

    }

    private int b;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyViewHolder)
        {

            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.llParent.setTag(position);
            myViewHolder.llParent.setOnClickListener(this);
            //线路对象
            TravelLineProductBean travelLineProductBean = list.get(position);

            myViewHolder.fromPlace.setText(travelLineProductBean.getFromPlace() + context.getString(R.string.travel_from_city));

            myViewHolder.typeName.setText(travelLineProductBean.getProductType());

            myViewHolder.travelName.setText(travelLineProductBean.getProductName());

            myViewHolder.travelAgency.setText(travelLineProductBean.getAgentName());

            String depDateDesc =  travelLineProductBean.getDepDateDesc();
            if(depDateDesc.length()<=15){

            }else{
                depDateDesc = depDateDesc.substring(0,15)+"…";
            }
            myViewHolder.travelDate.setText("班期：" +depDateDesc );

            myViewHolder.bgnPrice.setText(Utils.subZeroAndDot(travelLineProductBean.getBgnPrice() + ""));

            myViewHolder.tvTrip.setTag(position);

            myViewHolder.tvTrip.setOnClickListener(this);
            //特惠信息
            String status = travelLineProductBean.getBankStatus();

            int a = 0;
            myViewHolder.llLabel.removeAllViews();
            if ("1".equals(status))
            {//有

                View tehuiV = inflater.inflate(R.layout.view_travel_tehui, null);

                myViewHolder.llLabel.addView(tehuiV);
                a = 1;

            } else if ("0".equals(status))
            {//无

            }
            //标签信息
            List<HotelLabelBean> labelList = travelLineProductBean.getLabelList();

            for (HotelLabelBean temp : labelList)
            {

                View labelV = inflater.inflate(R.layout.view_travel_labe, null);

                TextView tvLabelName = (TextView) labelV.findViewById(R.id.tvLabelName);

                String labelName = temp.getLabelName();

                int length = labelName.length();

                if (length > 5)
                {
                    tvLabelName.setText(labelName.substring(0, 6) + "…");
                } else
                {
                    tvLabelName.setText(labelName);
                }

                if (a == 0)
                {

                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvLabelName.getLayoutParams();
                    lp.leftMargin = 0;
                    tvLabelName.setLayoutParams(lp);
                    a = 1;
                }

                myViewHolder.llLabel.addView(labelV);
            }

            //银行，平台，商家优惠标签

            myViewHolder.llPre.removeAllViews();
            b = 0;
            //平台
            String platformStatus = travelLineProductBean.getPlatformStatus();

            if ("1".equals(platformStatus))
            {//有
                myViewHolder.llPre.addView(getPreView("平台", b));

            } else if ("0".equals(platformStatus))
            {//无

            }

            //检查是否多家银行
            List<BankInfo> bankList = travelLineProductBean.getBankList();
            int size = bankList.size();
            if (size == 0)
            {

                myViewHolder.ivBankLogo.setVisibility(View.INVISIBLE);

                myViewHolder.tvBankName.setVisibility(View.INVISIBLE);
            } else
            {

                myViewHolder.llPre.addView(getPreView("银行", b));

                if (size == 1)
                {

                    BankInfo bean = bankList.get(0);
                    myViewHolder.ivBankLogo.setVisibility(View.VISIBLE);

                    myViewHolder.tvBankName.setVisibility(View.VISIBLE);

                    myViewHolder.tvBankName.setText(bean.getBankName());

                    if (!TextUtils.isEmpty(bean.getBankLogoCode()))
                    {

                        x.image().bind(myViewHolder.ivBankLogo, ServiceDispatcher
                                .getImageUrl(bean.getBankLogoCode()));

                    }

                } else
                {

                    myViewHolder.ivBankLogo.setVisibility(View.GONE);

                    myViewHolder.tvBankName.setText(R.string.travel_more_bank);

                    myViewHolder.tvBankName.setTag(position);

                    myViewHolder.tvBankName.setOnClickListener(this);
                }
            }
            //商家
            String shopStatus = travelLineProductBean.getShopStatus();

            if ("1".equals(shopStatus))
            {//有

                myViewHolder.llPre.addView(getPreView("商家", b));

            } else if ("0".equals(shopStatus))
            {//无

            }

            if (!TextUtils.isEmpty(travelLineProductBean.getProductImageUrl()))
            {
                if (ValidatorUtil.isUrl(travelLineProductBean.getProductImageUrl()))
                {
                    x.image().bind(myViewHolder.travelImage, travelLineProductBean.getProductImageUrl());
                } else
                {
                    x.image().bind(myViewHolder.travelImage, ServiceDispatcher
                            .getImageUrl(travelLineProductBean.getProductImageUrl()));
                }

            }

        } else if (holder instanceof NoViewHolder)
        {

            NoViewHolder viewHolder = (NoViewHolder) holder;

            TravelLineProductBean travelLineProductBean = list.get(position);

            viewHolder.tvMessage.setText(travelLineProductBean.getDataPrompt());

        } else if (holder instanceof OtherMoreBankView)
        {//更多银行

            OtherMoreBankView viewHolder = (OtherMoreBankView) holder;


            viewHolder.llOtherMore.setOnClickListener(this);

        } else if (holder instanceof BankCardInfoViewHolder)
        {

            BankCardInfoViewHolder viewHolder = (BankCardInfoViewHolder) holder;

            viewHolder.llOtherMore.setOnClickListener(this);

            viewHolder.tvBandCard.setOnClickListener(this);

            viewHolder.tvManageCard.setOnClickListener(this);

            TravelLineProductBean travelLineProductBean = list.get(position);


            viewHolder.tvMessageBank.setText(travelLineProductBean.getDataPrompt());

        }

    }

    /**
     * 旅游优惠信息试图
     *
     * @param name
     * @param b
     * @return
     */
    private View getPreView(String name, int b)
    {
        View v = inflater.inflate(R.layout.view_travel_pre, null);

        TextView tv = (TextView) v.findViewById(R.id.tv);

        tv.setText(name);
        if (b == 0)
        {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
            lp.leftMargin = 0;
            this.b = 1;
            tv.setLayoutParams(lp);
        }

        return v;
    }

    @Override
    public int getItemCount()
    {
        int size = list.size();

        return size;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.tvBankName:
                int position = (int) v.getTag();
                TravelLineProductBean travelLineProductBean = list.get(position);

                DiscBankDialog discBankDialog = new DiscBankDialog(context, travelLineProductBean.getBankList());

                discBankDialog.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tvTrip://行程概要
                int position1 = (int) v.getTag();
                TravelLineProductBean travelLineProductBean1 = list.get(position1);
                TravelDetailTripPop pop = new TravelDetailTripPop((Activity) context, travelLineProductBean1.getLineId(), 0, travelLineProductBean1.getProductId() + "");
                pop.setTitle(travelLineProductBean1.getProductName());
                pop.setPrice(travelLineProductBean1.getBgnPrice());
                pop.openPop();
                break;
            case R.id.llParent://查看旅游产品详情
                int position2 = (int) v.getTag();
                TravelLineProductBean travelLineProductBean2 = list.get(position2);
                Intent it = new Intent(context, TravelProductDetailActivity.class);
                it.putExtra("id", travelLineProductBean2.getProductId() + "");
                context.startActivity(it);
                break;
            case R.id.llOtherMore://更多银行

                //重新发送请求
                if (dataHandler != null)
                {

                    dataHandler.sendEmptyMessage(0);
                }
                break;
            case R.id.tvBandCard:

                //ToastUtil.i(context, "进入绑卡流程");

                bindCard();

                break;
            case R.id.tvManageCard:

               // ToastUtil.i(context, "进入办卡流程");
                applyCard();
                break;
            default:
                break;
        }
    }

    private void bindCard()
    {
        if (UserManager.getInstance().isLogin())
        {
           // context.startActivity(new Intent(context, AddNoPayCardActivity.class));
            Intent intent = new Intent(fragment.getActivity(), AddCardAllActivity.class);
            intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.PART_BUSINESS_VALUE);
          //  context.startActivity(intent);
           fragment.getActivity().startActivityForResult(intent,1000);
        } else
        {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    private void applyCard()
    {
        if (UserManager.getInstance().isLogin())
        {
            context.startActivity(new Intent(context, CreditOnlineCardActivity.class));
        } else
        {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * 出发地名
         */
        public TextView fromPlace;
        /**
         * 旅游图片
         */
        private ImageView travelImage;

        /**
         * 产品类型
         */
        private TextView typeName;

        /**
         * 旅游线路名称
         */
        private TextView travelName;

        /**
         * 标签
         */
        private LinearLayout llLabel;
        /**
         * 代理商
         */
        private TextView travelAgency;

        /**
         * 日期
         */
        private TextView travelDate;
        /**
         * 优惠信息
         */
        private LinearLayout llPre;
        /**
         * 价格
         */
        private TextView bgnPrice;

        private ImageView ivBankLogo;

        private TextView tvBankName;

        private LinearLayout tvTrip;

        private LinearLayout llParent;

        public MyViewHolder(View itemView)
        {
            super(itemView);

            fromPlace = (TextView) itemView.findViewById(R.id.fromPlace);

            typeName = (TextView) itemView.findViewById(R.id.typeName);

            bgnPrice = (TextView) itemView.findViewById(R.id.bgnPrice);

            travelName = (TextView) itemView.findViewById(R.id.travelName);

            tvBankName = (TextView) itemView.findViewById(R.id.tvBankName);

            travelDate = (TextView) itemView.findViewById(R.id.travelDate);

            travelAgency = (TextView) itemView.findViewById(R.id.travelAgency);

            tvTrip = (LinearLayout) itemView.findViewById(R.id.tvTrip);

            ivBankLogo = (ImageView) itemView.findViewById(R.id.ivBankLogo);

            travelImage = (ImageView) itemView.findViewById(R.id.travelImage);

            llLabel = (LinearLayout) itemView.findViewById(R.id.llLabel);

            llPre = (LinearLayout) itemView.findViewById(R.id.llPre);

            llParent = (LinearLayout) itemView.findViewById(R.id.llParent);

        }
    }

    //无数据视图句柄
    class NoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessage;

        public NoViewHolder(View itemView)
        {
            super(itemView);

            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }
    }

    //更多银行
    class OtherMoreBankView extends RecyclerView.ViewHolder {

        private LinearLayout llOtherMore;

        public OtherMoreBankView(View itemView)
        {
            super(itemView);

            llOtherMore = (LinearLayout) itemView.findViewById(R.id.llOtherMore);
        }
    }

    //无卡试图或有卡无优惠信息试图
    class BankCardInfoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBandCard;

        private TextView tvManageCard;

        private TextView tvMessageBank;

        private LinearLayout llOtherMore;

        public BankCardInfoViewHolder(View itemView)
        {
            super(itemView);

            tvBandCard = (TextView) itemView.findViewById(R.id.tvBandCard);

            tvManageCard = (TextView) itemView.findViewById(R.id.tvManageCard);

            tvMessageBank = (TextView) itemView.findViewById(R.id.tvMessage);

            llOtherMore = (LinearLayout) itemView.findViewById(R.id.llOtherMore);
        }
    }
}
