package com.yzb.card.king.ui.hotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.hotel.ExtraGoodsComboBean;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelMenuBean;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.adapter.HotelDialogMenuItemAdapter;
import com.yzb.card.king.ui.hotel.adapter.HotelInfoFacitityAdapter;
import com.yzb.card.king.ui.hotel.persenter.HotelMenuPresenter;
import com.yzb.card.king.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：酒店其它产品详情简介
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelOtherProductInfoIntroFragment extends Fragment implements View.OnClickListener, BaseViewLayerInterface {

    private TextView tvName;

    private TextView tvPersonTime;

    private TextView tvAddress;

    private TextView tvCancelStatus;

    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    private List<HotelMenuBean.HotelChildMenuBean> value;

    private HotelMenuPresenter hotelMenuPresenter;

    private Hotel.GoodsType goodsType;

    private ExtraGoodsComboBean item;

    private HotelDialogMenuItemAdapter hotelFacitityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_hotel_other_product_info, null);

        Bundle bundle = getArguments();

        item = (ExtraGoodsComboBean) bundle.get("ExtraGoodsComboBean");

        goodsType = (Hotel.GoodsType) bundle.get("goodsType");

        String comboType = bundle.getString("roomType");

        //加载页面数据

        tvName = (TextView) view.findViewById(R.id.tvName);

        tvName.setText(item.getPolicysName());

        tvPersonTime = (TextView) view.findViewById(R.id.tvPersonTime);

        String code = goodsType.getGoodsTypeCode();
        //餐厅
        if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(code)) {
            StringBuffer sb = new StringBuffer();
            //人数处理
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sb, policysLimits);

            sb.append("     ");

            //餐信息
            String mealTypes = item.getMealTypes();
            HoteUtil.handleMealTypes(sb, mealTypes);

            // 人数 早餐
            tvPersonTime.setText(sb.toString());


            //酒吧 、大堂吧 、酒吧
        } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(code) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(code) || HoteUtil.HOTEL_BAR_CODE.equals(code)) {

            StringBuffer sb = new StringBuffer();
            //人数处理
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sb, policysLimits);
            // 人数
            tvPersonTime.setText(sb.toString());

            //SPA
        } else if (HoteUtil.HOTEL_SPA_CODE.equals(code)) {
            StringBuffer sb = new StringBuffer();
            //人数处理
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sb, policysLimits);

            sb.append("     ");

            //分钟信息
            String mealTypes = item.getTimeLegnth()+"分钟";
            sb.append(mealTypes);

            // 人数 SPA分钟
            tvPersonTime.setText(sb.toString());
         //健身房 、游泳池
        }else if (HoteUtil.HOTEL_GYM_CODE.equals(code)||HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(code)) {

            StringBuffer sb = new StringBuffer();
            //人数处理
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sb, policysLimits);

            sb.append(item.getPolicysLimits()+"次");
            tvPersonTime.setText(sb.toString());

        }

        tvAddress = (TextView) view.findViewById(R.id.tvAddress);

        String floorDesc = bundle.getString("floorDesc");

        tvAddress.setText(item.getPolicysIntro());

        tvCancelStatus = (TextView) view.findViewById(R.id.tvCancelStatus);

        String cancelStatusStr = item.getCancelStatus();

        HoteUtil.handlerRefundMessage(tvCancelStatus, cancelStatusStr);

        //套餐内容
        LinearLayout llOne = (LinearLayout) view.findViewById(R.id.llOne);
        LinearLayout llTwo = (LinearLayout) view.findViewById(R.id.llTwo);

        if ("自助餐厅".equals(comboType) && HoteUtil.HOTEL_DINING_ROOM_CODE.equals(code)) {
            llOne.setVisibility(View.GONE);

            llTwo.setVisibility(View.VISIBLE);

            TextView tvBuffetContent = (TextView) view.findViewById(R.id.tvBuffetContent);

            List<ExtraGoodsComboBean.ServiceItem> list = item.getBuffetItems();

            StringBuffer sb = new StringBuffer();

            int size = list.size();

            for (int i = 0; i < size; i++) {

                ExtraGoodsComboBean.ServiceItem serviceItem = list.get(i);

                sb.append(serviceItem.getItemName());

                if (i == size - 1) {

                } else {

                    sb.append(",");
                }


            }

            tvBuffetContent.setText(sb.toString());


            TextView tvBuffetPrice = (TextView) view.findViewById(R.id.tvBuffetPrice);

            tvBuffetPrice.setText("¥" + item.getStorePrice());

         //SPA
        } else if (HoteUtil.HOTEL_SPA_CODE.equals(code)) {

            llOne.setVisibility(View.GONE);

            llTwo.setVisibility(View.VISIBLE);


            TextView tvBuffetContent = (TextView) view.findViewById(R.id.tvBuffetContent);

            List<ExtraGoodsComboBean.ServiceItem> list = item.getSpaItems();

            StringBuffer sb = new StringBuffer();

            int size = list.size();

            for (int i = 0; i < size; i++) {

                ExtraGoodsComboBean.ServiceItem serviceItem = list.get(i);

                sb.append(serviceItem.getItemName());

                if (i == size - 1) {

                } else {

                    sb.append("\n");
                }


            }

            tvBuffetContent.setText(sb.toString());


            TextView tvBuffetPrice = (TextView) view.findViewById(R.id.tvBuffetPrice);

            tvBuffetPrice.setText("");

            //健身房 游泳池
        } else if(HoteUtil.HOTEL_GYM_CODE.equals(code) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(code)){

            view.findViewById(R.id.llTaocanContent).setVisibility(View.GONE);

            view.findViewById(R.id.vTaocanLine).setVisibility(View.GONE);

            llTwo.setVisibility(View.GONE);

            llOne.setVisibility(View.GONE);
            //
        }   else {

            llTwo.setVisibility(View.GONE);

            llOne.setVisibility(View.VISIBLE);

            SpecHeightGridView gvFactivity = (SpecHeightGridView) view.findViewById(R.id.gvFactivity);

            value = new ArrayList<>();

            hotelFacitityAdapter = new HotelDialogMenuItemAdapter(inflater, value);

            gvFactivity.setAdapter(hotelFacitityAdapter);

            view.findViewById(R.id.ivArrow).setOnClickListener(this);

            initData();

            gvFactivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    if (dataCall != null) {
                        dataCall.productMenuAction();
                    }
                }
            });
        }
        //购买须知
        TextView tvBuyNotice = (TextView) view.findViewById(R.id.tvBuyNotice);

        StringBuffer buySb = new StringBuffer();

        String effDateDesc = item.getEffDateDesc();

        if (!TextUtils.isEmpty(effDateDesc)) {

            buySb.append("有效期：").append("\n").append(effDateDesc).append("\n");
        }


        String useDateDesc = item.getUseDateDesc();

        String useTimeDesc = item.getUseTimeDesc();

        if (!TextUtils.isEmpty(useDateDesc) || !TextUtils.isEmpty(useTimeDesc)) {

            buySb.append("使用时间：").append("\n").append(useDateDesc).append("  ").append(useTimeDesc).append("\n");
        }

        String unsuitedIntro = item.getUnsuitedIntro();

        if (!TextUtils.isEmpty(unsuitedIntro)) {

            buySb.append("不适应人群：").append("\n").append(unsuitedIntro).append("\n");
        }


        String orderInfo = item.getOrderInfo();

        if (!TextUtils.isEmpty(orderInfo)) {

            buySb.append("预约信息：").append("\n").append(orderInfo).append("\n");
        }

        String policysRule = item.getPolicysRule();

        if (!TextUtils.isEmpty(policysRule)) {

            buySb.append("使用规则：").append("\n").append(policysRule).append("\n");
        }


        tvBuyNotice.setText(buySb.toString());

        view.findViewById(R.id.llGetCoupon).setOnClickListener(this);

        view.findViewById(R.id.llLifeStage).setOnClickListener(this);

        view.findViewById(R.id.llBankFavorable).setOnClickListener(this);

        FavInfoBean hotelProductPre = item.getDisMap();


        LinearLayout llLifeStagen = (LinearLayout) view.findViewById(R.id.llLifeStage);
        llLifeStagen.setOnClickListener(this);

        String stageStatus = hotelProductPre.getStageStatus();

        if ("1".equals(stageStatus)) {

            llLifeStagen.setVisibility(View.VISIBLE);

            TextView tvBankStageDesc = (TextView) view.findViewById(R.id.tvBankStageDesc);

            tvBankStageDesc.setText(hotelProductPre.getStageDesc());

        } else {

            llLifeStagen.setVisibility(View.GONE);
        }

        LinearLayout llSuperGive = (LinearLayout) view.findViewById(R.id.llSuperGive);

        llSuperGive.setVisibility(View.GONE);

        LinearLayout llBankFavorable = (LinearLayout) view.findViewById(R.id.llBankFavorable);

        llBankFavorable.setOnClickListener(this);

        if ("1".equals(hotelProductPre.getBankStatus())) {

            llBankFavorable.setVisibility(View.VISIBLE);

            TextView tvBankFavDesc = (TextView) view.findViewById(R.id.tvBankFavDesc);

            tvBankFavDesc.setText(hotelProductPre.getBankDesc());

        } else {

            llBankFavorable.setVisibility(View.GONE);
        }

        LinearLayout llReturnMoney = (LinearLayout) view.findViewById(R.id.llReturnMoney);

        if ("1".equals(hotelProductPre.getCashbackStatus())) {

            llReturnMoney.setVisibility(View.VISIBLE);
        } else {

            llReturnMoney.setVisibility(View.GONE);
        }

        LinearLayout llGetCoupon = (LinearLayout) view.findViewById(R.id.llGetCoupon);

        llGetCoupon.setOnClickListener(this);

        if ("1".equals(hotelProductPre.getTicketStatus())) {

            llGetCoupon.setVisibility(View.VISIBLE);
        } else {

            llGetCoupon.setVisibility(View.GONE);
        }

        LinearLayout llMerchantIntegral = (LinearLayout) view.findViewById(R.id.llMerchantIntegral);

        llMerchantIntegral.setVisibility(View.GONE);


        return view;
    }

    private void initData()
    {

        hotelMenuPresenter = new HotelMenuPresenter(this);

        if (goodsType != null) {
            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), true);

            hotelMenuPresenter.sendHotelGoodsPolicysMenuRequest(goodsType.getGoodsTypeCode(), item.getPolicysId() + "");
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.ivArrow:
                if (dataCall != null)
                    dataCall.productMenuAction();

                break;

            case R.id.llGetCoupon:
                if (dataCall != null)
                    dataCall.getCouponAction();

                break;
            case R.id.llLifeStage:
                if (dataCall != null)
                    dataCall.hotelLifeStageAction();

                break;

            case R.id.llBankFavorable:
                if (dataCall != null)
                    dataCall.bankFavorableAction();

                break;
            default:
                break;
        }
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall)
    {
        this.dataCall = dataCall;
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        List<HotelMenuBean> list = JSON.parseArray(o + "", HotelMenuBean.class);

        if (list == null) {

            return;

        }

        List<HotelMenuBean.HotelChildMenuBean> value = new ArrayList<HotelMenuBean.HotelChildMenuBean>();

        //只需要添加3个
        int number = 3;
        int size = list.size();
        for (int i = 0; i < size; i++) {

            HotelMenuBean hotelMeanuBean = list.get(i);

            int childSize = hotelMeanuBean.getValue().size();

            if (childSize >= number) {

                List<HotelMenuBean.HotelChildMenuBean> subList = hotelMeanuBean.getValue().subList(0, 3);

                value.addAll(subList);

                break;
            } else if (number == 0) {

                break;

            } else {

                value.addAll(hotelMeanuBean.getValue());

                number = number - childSize;
            }


        }


        hotelFacitityAdapter.addNewList(value);

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }
}
