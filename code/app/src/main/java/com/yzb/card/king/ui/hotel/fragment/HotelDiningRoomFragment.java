package com.yzb.card.king.ui.hotel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.hotel.ExtraGoodsComboBean;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelExtraProductBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.HttpUtil;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductInfoIntroFragmentDialog;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductIntroFragmentDialog;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.CollectionView;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.async.AsyncExpandableListView;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.async.AsyncExpandableListViewCallbacks;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.async.AsyncHeaderViewHolder;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelOtherProductOrderActivity;
import com.yzb.card.king.ui.hotel.persenter.HotelServicePersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 类  名：酒店餐厅
 * 作  者：Li Yubing
 * 日  期：2017/8/5
 * 描  述：2餐厅、3酒吧、5SPA、6大堂吧、9健身房、10游泳池
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_hotel_dining_room)
public class HotelDiningRoomFragment extends BaseFragment implements AsyncExpandableListViewCallbacks<HotelExtraProductBean, ExtraGoodsComboBean>, BaseViewLayerInterface {

    private Hotel.GoodsType goodsType;
    /**
     * 酒店名称
     */
    private String hotelName;

    private long hotelId = 1l;

    private AsyncExpandableListView<HotelExtraProductBean, ExtraGoodsComboBean> mAsyncExpandableListView;


    private final String tag = getClass().getSimpleName();

    private HotelServicePersenter persenter;

    public static HotelDiningRoomFragment getInstance(Hotel.GoodsType bean, String hotelName, long hotelId)
    {

        HotelDiningRoomFragment sf = new HotelDiningRoomFragment();
        sf.goodsType = bean;
        sf.hotelName = hotelName;
        sf.hotelId = hotelId;

        return sf;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        /**
         * 请求该酒店的房间信息
         */
        initRequest();

    }

    private void initView(View view)
    {

        final Context context = getContext();
        mAsyncExpandableListView = (AsyncExpandableListView) view.findViewById(R.id.asyncExpandableCollectionView);
        mAsyncExpandableListView.setCallbacks(this);

       View viewRim = (LinearLayout) view.findViewById(R.id.viewRim);

        HotelProductRimView hotelProductRimView = new HotelProductRimView(viewRim,getContext());

        hotelProductRimView.setHotelDetailServiceBean(HotelLogicManager.getInstance().getHotelDetailServiceBean());

    }

    private void initRequest()
    {
        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        persenter = new HotelServicePersenter(this);

        HotelRoomParam param = new HotelRoomParam(hotelId, productListParam.getArrDate(), Integer.parseInt(goodsType.getGoodsTypeCode()));

        persenter.sendHotelExtraProductRequest(param);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (type == HotelServicePersenter.HOTEL_OTHER_GOODS_CODE) {

            List<HotelExtraProductBean> list = JSONArray.parseArray(o + "", HotelExtraProductBean.class);

            CollectionView.Inventory<HotelExtraProductBean, ExtraGoodsComboBean> inventory = new CollectionView.Inventory<>();

            int size = list.size();

            for (int i = 0; i < size; i++) {

                CollectionView.InventoryGroup<HotelExtraProductBean, ExtraGoodsComboBean> group = inventory.newGroup(i);

                group.setHeaderItem(list.get(i));

            }

            mAsyncExpandableListView.updateInventory(inventory);

            int v = mAsyncExpandableListView.getVisibility();


                mAsyncExpandableListView.setVisibility(View.VISIBLE);


        } else if (type == HotelServicePersenter.HOTEL_OTHER_COMBO_CODE) {

            WeakReference<AsyncExpandableListView<HotelExtraProductBean, ExtraGoodsComboBean>> listviewRef = new WeakReference<>(mAsyncExpandableListView);

            List<ExtraGoodsComboBean> list = JSONArray.parseArray(o + "", ExtraGoodsComboBean.class);
            LogUtil.e(tag, "size=" + list.size());
            if (listviewRef.get() != null) {
                listviewRef.get().onFinishLoadingGroup(mGroupOrdinal, list);
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        LogUtil.e(tag, "o=" + o);
        if (type == HotelServicePersenter.HOTEL_ROOM_PRODUCT_CODE) {

            com.yzb.card.king.bean.common.Error error = JSONObject.parseObject(JSON.toJSONString(o), com.yzb.card.king.bean.common.Error.class);

            if (HttpConstant.NOINFO.equals(error.getCode())) {


                    mAsyncExpandableListView.setVisibility(View.GONE);

            }else {

                HttpUtil.responseCallBackMessage(error.getCode());
            }

        } else if (type == HotelServicePersenter.HOTEL_OTHER_COMBO_CODE) {
            WeakReference<AsyncExpandableListView<HotelExtraProductBean, ExtraGoodsComboBean>> listviewRef = new WeakReference<>(mAsyncExpandableListView);
            if (listviewRef.get() != null) {
                listviewRef.get().onGroupClicked(mGroupOrdinal);
            }
        }
    }

    private int mGroupOrdinal;

    private HotelExtraProductBean hotelRoomInfoBean;

    @Override
    public void onStartLoadingGroup(int groupOrdinal)
    {
        this.mGroupOrdinal = groupOrdinal;
        hotelRoomInfoBean = mAsyncExpandableListView.getHeader(groupOrdinal);

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        HotelRoomParam param = new HotelRoomParam(productListParam.getArrDate(), Integer.parseInt(goodsType.getGoodsTypeCode()), hotelRoomInfoBean.getGoodsId() + "");

        persenter.sendHotelExtraProductComeboRequest(param);
    }

    @Override
    public AsyncHeaderViewHolder newCollectionHeaderView(Context context, int groupOrdinal, ViewGroup parent)
    {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.view_item_hotel_other_goods_info, parent, false);

        return new MyHeaderViewHolder(v, groupOrdinal, mAsyncExpandableListView);
    }

    @Override
    public RecyclerView.ViewHolder newCollectionItemView(Context context, int groupOrdinal, ViewGroup parent)
    {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.view_item_hotel_other_goods_combo_info, parent, false);

        return new NewsItemHolder(v);
    }

    @Override
    public void bindCollectionHeaderView(Context context, AsyncHeaderViewHolder holder, int groupOrdinal, HotelExtraProductBean headerItem)
    {
        MyHeaderViewHolder myHeaderViewHolder = (MyHeaderViewHolder) holder;

        myHeaderViewHolder.llDiningRoomInfo.setTag(headerItem);

        myHeaderViewHolder.tvRoomComboName.setText(headerItem.getGoodsName());

        StringBuffer sb = new StringBuffer();

        sb.append("营业时间：");

        String bedTypeDesc = headerItem.getBusinessStartHours();

        if (!TextUtils.isEmpty(bedTypeDesc)) {
            sb.append(bedTypeDesc + "-");
        }
        String wifiDesc = headerItem.getBusinessEndHours();
        if (!TextUtils.isEmpty(wifiDesc)) {
            sb.append(wifiDesc);
        }

        myHeaderViewHolder.tvDoBusinessTime.setText(sb.toString());

        myHeaderViewHolder.tvRoomFacDesc.setText(headerItem.getFloorDesc());

        myHeaderViewHolder.tvHotelRoomComboPrice.setText(Utils.subZeroAndDot(headerItem.getMinPrice() + ""));

        String photoUrls = headerItem.getPhotoUrls();

        if (photoUrls != null) {

           myHeaderViewHolder.rlRoomImage.setTag(photoUrls);

            int index = photoUrls.indexOf(",");

            if (index == -1) {

                Glide.with(this).load(ServiceDispatcher.getImageUrl(photoUrls)).into(myHeaderViewHolder.ivRoomImage);

                myHeaderViewHolder.tvRoomImageNumber.setText("");

                myHeaderViewHolder.tvRoomImageNumber.setVisibility(View.INVISIBLE);
            } else {
                String[] photoUrlsArray = photoUrls.split(",");

                Glide.with(this).load(ServiceDispatcher.getImageUrl(photoUrlsArray[0])).into(myHeaderViewHolder.ivRoomImage);

                myHeaderViewHolder.tvRoomImageNumber.setText(photoUrlsArray.length + "张");

                myHeaderViewHolder.tvRoomImageNumber.setVisibility(View.VISIBLE);
            }

        } else {

            myHeaderViewHolder.tvRoomImageNumber.setText("");

            myHeaderViewHolder.tvRoomImageNumber.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void bindCollectionItemView(Context context, RecyclerView.ViewHolder holder, int groupOrdinal, ExtraGoodsComboBean item)
    {
        NewsItemHolder newsItemHolder = (NewsItemHolder) holder;

        item.setHotelExtraProductBean(hotelRoomInfoBean);

        item.setHotelId(hotelId);

        newsItemHolder.itemView.setTag(item);

        newsItemHolder.llPayCombo.setTag(item);

        StringBuffer sb = new StringBuffer();

        String policysName = item.getPolicysName();

        sb.append(policysName);

        String typeCode = goodsType.getGoodsTypeCode();

        if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(typeCode)) {//2餐厅、

            StringBuffer sbOne = new StringBuffer();

            sbOne.append(" (");

            //餐信息
            String mealTypesStr = item.getMealTypes();
            HoteUtil.handleMealTypes(sbOne, mealTypesStr);

            //人数信息
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sbOne, policysLimits);

            sbOne.append(")");

            sb.append(sbOne.toString());

        } else if (HoteUtil.HOTEL_BAR_CODE.equals(typeCode)) {//3酒吧、
            StringBuffer sbOne = new StringBuffer();

            sbOne.append(" (");

            //人数信息
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sbOne, policysLimits);

            sbOne.append(")");

            sb.append(sbOne.toString());


        } else if (HoteUtil.HOTEL_SPA_CODE.equals(typeCode)) {//5SPA、

            StringBuffer sbOne = new StringBuffer();

            sbOne.append(" (");

            //人数信息
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sbOne, policysLimits);

            sbOne.append(")");

            sb.append(sbOne.toString());

        } else if (HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(typeCode)) {//6大堂吧、
            StringBuffer sbOne = new StringBuffer();

            sbOne.append(" (");

            //人数信息
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sbOne, policysLimits);

            sbOne.append(")");

            sb.append(sbOne.toString());

        } else if (HoteUtil.HOTEL_GYM_CODE.equals(typeCode)) {//9健身房、

            StringBuffer sbOne = new StringBuffer();

            sbOne.append(" (");

            //人数信息
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sbOne, policysLimits);

            int policysQuantity = item.getPolicysQuantity();

            if (policysQuantity > 0) {
                sbOne.append(policysQuantity + "次");
            }

            sbOne.append(")");

            sb.append(sbOne.toString());

        } else if (HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)) {//10游泳池

            StringBuffer sbOne = new StringBuffer();

            sbOne.append(" (");
            //人数信息
            String policysLimits = item.getPolicysLimits();
            HoteUtil.handlerPolicysLimits(sbOne, policysLimits);
            int policysQuantity = item.getPolicysQuantity();
            if (policysQuantity > 0) {
                sbOne.append(policysQuantity + "次");
            }

            sbOne.append(")");

            sb.append(sbOne.toString());

        }

        newsItemHolder.tvMealTypeDesc.setText(sb.toString());

        newsItemHolder.tvDescription.setText(item.getShopName());

        FavInfoBean pre = item.getDisMap();

        if ("1".equals(pre.getCashbackStatus())) {

            newsItemHolder.tvBackMoney.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvBackMoney.setVisibility(View.INVISIBLE);
        }

        if ("1".equals(pre.getStageStatus())) {

            newsItemHolder.ivLifeStages.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.ivLifeStages.setVisibility(View.GONE);
        }

        if ("1".equals(pre.getBankStatus())) {

            newsItemHolder.tvBankPre.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvBankPre.setVisibility(View.GONE);
        }

        if ("1".equals(pre.getCouponStatus())) {//

            newsItemHolder.tvQuan.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvQuan.setVisibility(View.GONE);
        }

        if ("1".equals(pre.getCashCouponStatus())) {//tvJin

            newsItemHolder.tvJin.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvJin.setVisibility(View.GONE);
        }


        if ("1".equals(pre.getFlashsaleStatus())) {

            newsItemHolder.tvQiang.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvQiang.setVisibility(View.GONE);
        }

        if ("1".equals(pre.getGiftsStatus())) {

            newsItemHolder.tvKaquanyi.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvKaquanyi.setVisibility(View.GONE);
        }

        newsItemHolder.tvRoomComboPrice.setText(Utils.subZeroAndDot(item.getOnlinePrice() + ""));

        newsItemHolder.tvStorePrice.setText(Utils.subZeroAndDot(item.getStorePrice() + ""));

        String cancelStatusStr = item.getCancelStatus();

        newsItemHolder.tvCancelStatus.setVisibility(View.VISIBLE);

        if ("1".equals(cancelStatusStr)) {

            newsItemHolder.tvCancelStatus.setText("过期自动退");

        } else if ("2".equals(cancelStatusStr)) {

            newsItemHolder.tvCancelStatus.setText("随时退");

        } else if ("3".equals(cancelStatusStr)) {

            newsItemHolder.tvCancelStatus.setText("不可退");

        } else {
            newsItemHolder.tvCancelStatus.setVisibility(View.GONE);
        }
    }


    public class MyHeaderViewHolder extends AsyncHeaderViewHolder implements AsyncExpandableListView.OnGroupStateChangeListener {

        public final TextView tvRoomComboName;
        public final TextView tvRoomFacDesc;
        public final TextView tvHotelRoomComboPrice;
        public final ImageView ivRoomImage;
        public final TextView tvRoomImageNumber;
        private final ProgressBar mProgressBar;
        private ImageView ivExpansionIndicator;
        public final TextView tvDoBusinessTime;
        private LinearLayout llDiningRoomInfo;

        private RelativeLayout rlRoomImage;

        public MyHeaderViewHolder(View v, int groupOrdinal, AsyncExpandableListView asyncExpandableListView)
        {
            super(v, groupOrdinal, asyncExpandableListView);
            tvRoomComboName = (TextView) v.findViewById(R.id.tvRoomComboName);
            tvRoomFacDesc = (TextView) v.findViewById(R.id.tvRoomFacDesc);
            tvHotelRoomComboPrice = (TextView) v.findViewById(R.id.tvHotelRoomComboPrice);
            tvRoomImageNumber = (TextView) v.findViewById(R.id.tvRoomImageNumber);
            ivRoomImage = (ImageView) v.findViewById(R.id.ivRoomImage);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            mProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF,
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            ivExpansionIndicator = (ImageView) v.findViewById(R.id.ivExpansionIndicator);

            rlRoomImage = (RelativeLayout) v.findViewById(R.id.rlRoomImageOne);

            rlRoomImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    if (v.getTag() != null) {

                        String photoUrls = (String) v.getTag();

                        Intent intent = new Intent(getContext(), HotelImageExpositionActivity.class);

                        String typeCode = goodsType.getGoodsTypeCode();

                        if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(typeCode)) {

                            intent.putExtra("imageTitleName", "房间图片");

                        } else if (HoteUtil.HOTEL_BAR_CODE.equals(typeCode)) {

                            intent.putExtra("imageTitleName", "酒吧图片");

                        } else if (HoteUtil.HOTEL_SPA_CODE.equals(typeCode)) {

                            intent.putExtra("imageTitleName", "SPA图片");

                        } else if (HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(typeCode)) {

                            intent.putExtra("imageTitleName", "大堂吧图片");

                        } else if (HoteUtil.HOTEL_GYM_CODE.equals(typeCode)) {

                            intent.putExtra("imageTitleName", "健身房图片");

                        } else if (HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)) {

                            intent.putExtra("imageTitleName", "游泳池图片");

                        }

                        intent.putExtra("photoUrls", photoUrls);

                        startActivity(intent);

                    }
                }
            });

            llDiningRoomInfo = (LinearLayout) v.findViewById(R.id.llDiningRoomInfo);

            llDiningRoomInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    HotelExtraProductBean headerItem = (HotelExtraProductBean) v.getTag();

                    HotelOtherProductIntroFragmentDialog hotelOtherProductIntroFragmentDialog = new HotelOtherProductIntroFragmentDialog();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("HotelExtraProductBean", headerItem);

                    bundle.putSerializable("goodsType", goodsType);

                    hotelOtherProductIntroFragmentDialog.setArguments(bundle);

                    String tag = HotelOtherProductIntroFragmentDialog.HOTEL_PRODUCT_MENU;

                    if (HoteUtil.HOTEL_GYM_CODE.equals(goodsType.getGoodsTypeCode()) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(goodsType.getGoodsTypeCode())) {

                        tag = HotelOtherProductIntroFragmentDialog.HOTEL_PRODUCT_NO_MENU;
                    }

                    hotelOtherProductIntroFragmentDialog.show(getFragmentManager().beginTransaction(), tag);
                }
            });

            tvDoBusinessTime = (TextView) v.findViewById(R.id.tvDoBusinessTime);
        }

        @Override
        public void onGroupStartExpending()
        {
            mProgressBar.setVisibility(View.VISIBLE);
            ivExpansionIndicator.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onGroupExpanded()
        {
            mProgressBar.setVisibility(View.GONE);
            ivExpansionIndicator.setVisibility(View.VISIBLE);
            ivExpansionIndicator.setImageResource(R.mipmap.icon_gray_down);
        }

        @Override
        public void onGroupCollapsed()
        {
            mProgressBar.setVisibility(View.GONE);
            ivExpansionIndicator.setVisibility(View.VISIBLE);
            ivExpansionIndicator.setImageResource(R.mipmap.icon_gray_down);

        }
    }

    public class NewsItemHolder extends RecyclerView.ViewHolder {


        public final TextView tvMealTypeDesc;

        public final TextView tvDescription;

        public final TextView tvBackMoney, tvBankPre, tvQuan, tvQiang, tvKaquanyi, tvLeftRoom,tvJin;

        public final ImageView ivLifeStages;

        public final LinearLayout llPayCombo;

        public final TextView tvCancelStatus;

        public final TextView tvStorePrice;

        public final TextView tvRoomComboPrice;


        public NewsItemHolder(View v)
        {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    ExtraGoodsComboBean item = (ExtraGoodsComboBean) v.getTag();

                    HotelOtherProductInfoIntroFragmentDialog hotelProductFragmentDialog = new HotelOtherProductInfoIntroFragmentDialog();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("ExtraGoodsComboBean", item);

                    bundle.putSerializable("goodsType", goodsType);

                    String resTaurantTypeDesc = hotelRoomInfoBean.getRestaurantTypeDesc();

                    bundle.putString("roomType", resTaurantTypeDesc);

                    hotelProductFragmentDialog.setArguments(bundle);

                    hotelProductFragmentDialog.show(getFragmentManager().beginTransaction(), "roomFt");

                }
            });
            tvMealTypeDesc = (TextView) v.findViewById(R.id.tvMealTypeDesc);

            tvDescription = (TextView) v.findViewById(R.id.tvShopName);

            tvBackMoney = (TextView) v.findViewById(R.id.tvBackMoney);

            ivLifeStages = (ImageView) v.findViewById(R.id.ivLifeStages);

            tvBankPre = (TextView) v.findViewById(R.id.tvBankPre);

            tvQuan = (TextView) v.findViewById(R.id.tvQuan);

            tvJin = (TextView) v.findViewById(R.id.tvJin);

            tvQiang = (TextView) v.findViewById(R.id.tvQiang);

            tvKaquanyi = (TextView) v.findViewById(R.id.tvKaquanyi);

            tvLeftRoom = (TextView) v.findViewById(R.id.tvLeftRoom);

            tvCancelStatus = (TextView) v.findViewById(R.id.tvCancelStatus);

            tvStorePrice = (TextView) v.findViewById(R.id.tvStorePrice);

            tvStorePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

            tvRoomComboPrice = (TextView) v.findViewById(R.id.tvRoomComboPrice);


            llPayCombo = (LinearLayout) v.findViewById(R.id.llPayCombo);

            llPayCombo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    //检查是否登录
                    if (UserManager.getInstance().isLogin()) {

                        Intent intent = new Intent(getContext(), HotelOtherProductOrderActivity.class);

                        ExtraGoodsComboBean comboBean = (ExtraGoodsComboBean) v.getTag();

                        intent.putExtra("comboBean", comboBean);

                        intent.putExtra("goodsType", goodsType);

                        getContext().startActivity(intent);

                    } else {

                        new GoLoginDailog(HotelDiningRoomFragment.this.getContext()).create().show();
                    }
                }
            });
        }

    }

}
