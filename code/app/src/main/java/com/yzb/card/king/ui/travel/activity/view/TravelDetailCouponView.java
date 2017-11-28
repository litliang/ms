package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.travel.adapter.CouponBankAdapter;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：旅游详情-->优惠详情view；
 *
 * @author:gengqiyun
 * @date: 2016/11/12
 */
public class TravelDetailCouponView extends BaseViewGroup implements View.OnClickListener
{
    private SpecHeightGridView gridCouponBanks; //优惠银行；
    private ImageView couponBanksArrow;
    private CouponBankAdapter adapter;

    private View panelOutFullReduction;
    private View panelPlatformCoupon;
    private View dividerPlatShop;//商家，平台间隔线；
    private View panelShopCoupon;
    private TextView tvPlatformCouponInfo;
    private TextView tvShopCouponInfo;

    private View panelOutCoupon;
    private View panelGetCoupon;
    private View panelGetRedPacket;
    private View dividerCouponRedPacket; //红包，优惠券间隔线；

    private List<GoodActivityBean> couponBanks; //优惠银行列表；
    private OnClickListener clickListener;
    private View emptycouponBankView;

    public TravelDetailCouponView(Context context)
    {
        super(context);
    }

    public TravelDetailCouponView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        gridCouponBanks = (SpecHeightGridView) rootView.findViewById(R.id.gridCouponBanks);
        gridCouponBanks.setFocusable(false);

        emptycouponBankView = rootView.findViewById(R.id.emptyView);
        emptycouponBankView.setVisibility(GONE);
        gridCouponBanks.setTag(0);

        adapter = new CouponBankAdapter(getContext());
        gridCouponBanks.setAdapter(adapter);

        couponBanksArrow = (ImageView) rootView.findViewById(R.id.couponBanksArrow);
        couponBanksArrow.setVisibility(GONE);
        couponBanksArrow.setOnClickListener(this);

        panelOutFullReduction = rootView.findViewById(R.id.panelOutFullReduction);
        panelOutFullReduction.setVisibility(GONE);
        //平台和商家优惠活动；
        panelPlatformCoupon = rootView.findViewById(R.id.panelPlatformCoupon);
        dividerPlatShop = rootView.findViewById(R.id.dividerPlatShop);
        panelShopCoupon = rootView.findViewById(R.id.panelShopCoupon);

        tvPlatformCouponInfo = (TextView) rootView.findViewById(R.id.tvPlatformCouponInfo);
        tvShopCouponInfo = (TextView) rootView.findViewById(R.id.tvShopCouponInfo);


        panelOutCoupon = rootView.findViewById(R.id.panelOutCoupon);
        panelOutCoupon.setVisibility(GONE);
        dividerCouponRedPacket = rootView.findViewById(R.id.dividerCouponRedPacket);
        dividerCouponRedPacket.setVisibility(VISIBLE);
        panelGetCoupon = rootView.findViewById(R.id.panelGetCoupon);
        panelGetCoupon.setOnClickListener(this);

        panelGetRedPacket = rootView.findViewById(R.id.panelGetRedPacket);
        panelGetRedPacket.setOnClickListener(this);

        //初始化状态；
        emptycouponBankView.setVisibility(VISIBLE);
        couponBanksArrow.setVisibility(GONE);
        gridCouponBanks.setVisibility(GONE);
    }

    @Override
    protected void fillViewData(TravelProduDetailBean detailBean)
    {
        //银行优惠状态   1有；0无
        String bankStatus = detailBean.getBankStatus();
        gridCouponBanks.setVisibility("1".equals(bankStatus) ? VISIBLE : GONE);

        //平台商家优惠；
        String platformPointsDesc = detailBean.getPlatformPointsDesc();
        String shopPointsDesc = detailBean.getShopPointsDesc();
        boolean platformEmpty = isEmpty(platformPointsDesc);
        boolean shopEmpty = isEmpty(shopPointsDesc);
        panelOutFullReduction.setVisibility(platformEmpty && shopEmpty ? GONE : VISIBLE);
        panelPlatformCoupon.setVisibility(platformEmpty ? GONE : VISIBLE);
        panelShopCoupon.setVisibility(platformEmpty ? GONE : VISIBLE);
        //分割线；
        dividerPlatShop.setVisibility(!platformEmpty && !shopEmpty ? VISIBLE : GONE);
        tvPlatformCouponInfo.setText(platformPointsDesc);
        tvShopCouponInfo.setText(shopPointsDesc);

        //优惠券,红包；
        boolean hasCoupon = "1".equals(detailBean.getCouponStatus());
        boolean hasBonus = "1".equals(detailBean.getBonusStatus());

        panelOutCoupon.setVisibility(!hasCoupon && !hasBonus ? GONE : VISIBLE);
        panelGetCoupon.setVisibility(hasCoupon ? VISIBLE : GONE);
        dividerCouponRedPacket.setVisibility(hasBonus ? VISIBLE : GONE);
        //分割线；
        dividerCouponRedPacket.setVisibility(hasCoupon && hasBonus ? VISIBLE : GONE);
        panelGetRedPacket.setVisibility(hasBonus ? VISIBLE : GONE);
    }

    protected int getLayoutResId()
    {
        return R.layout.travel_detail_coupon;
    }

    @Override
    protected void fillViewCouponBanks(List<GoodActivityBean> couponBanks)
    {
        boolean isNull = couponBanks == null || couponBanks.size() == 0;

        emptycouponBankView.setVisibility(isNull ? VISIBLE : GONE);
        couponBanksArrow.setVisibility(isNull || couponBanks.size() <= 3 ? GONE : VISIBLE);
        gridCouponBanks.setVisibility(isNull ? GONE : VISIBLE);
        if (!isNull)
        {
            this.couponBanks = couponBanks;
            //默认最多显示3个；
            adapter.setList(couponBanks.size() > 3 ? couponBanks.subList(0, 3) : couponBanks);
        }
    }

    /**
     * 初始化银行优惠列表；
     */
    private void refreshCouponBanksData()
    {
        List<GoodActivityBean> targetList = new ArrayList<>();
        int tag = Integer.parseInt(String.valueOf(gridCouponBanks.getTag()));

        //展开状态或数据小；
        if (couponBanks.size() < 4)
        {
            targetList = couponBanks;
        } else if (tag == 0) //闭合状态；
        {
            couponBanksArrow.setImageResource(R.mipmap.icon_travel_up);
            targetList = couponBanks;
            gridCouponBanks.setTag(1);
        } else if (tag == 1) //展开状态；
        {
            couponBanksArrow.setImageResource(R.mipmap.icon_travel_down);
            targetList = couponBanks.subList(0, 3);
            gridCouponBanks.setTag(0);
        }
        LogUtil.i("targetList大小=" + targetList.size());
        adapter.setList(targetList);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.couponBanksArrow: //银行优惠伸展；
                refreshCouponBanksData();
                break;
            case R.id.panelGetCoupon: //领取优惠券；
            case R.id.panelGetRedPacket: //领取红包；
                if (clickListener != null)
                {
                    clickListener.onClick(v);
                }
                break;
        }
    }

    public void setClickListener(OnClickListener clickListener)
    {
        this.clickListener = clickListener;
    }
}
