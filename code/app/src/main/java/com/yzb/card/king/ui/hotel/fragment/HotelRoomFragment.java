package com.yzb.card.king.ui.hotel.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.yzb.card.king.bean.SubItemBean;
import com.yzb.card.king.bean.common.FavInfoBean;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.bean.hotel.HotelProductObjectBean;
import com.yzb.card.king.bean.hotel.HotelRoomComboInfoBean;
import com.yzb.card.king.bean.hotel.HotelRoomInfoBean;
import com.yzb.card.king.bean.hotel.HotelRoomParam;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.HttpUtil;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.CollectionView;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.async.AsyncExpandableListView;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.async.AsyncExpandableListViewCallbacks;
import com.yzb.card.king.ui.appwidget.asyncexpandablelist.async.AsyncHeaderViewHolder;
import com.yzb.card.king.ui.appwidget.popup.AppCalendarPopup;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.appwidget.popup.HotelInfoScreenPopup;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelImageExpositionActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelRoomOrderActivity;
import com.yzb.card.king.ui.hotel.persenter.HotelServicePersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.ticket.holder.BaseNullHolder;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类  名：酒店房间
 * 作  者：Li Yubing
 * 日  期：2017/8/2
 * 描  述：
 */
@SuppressLint("ValidFragment")
@ContentView(R.layout.fragment_hotel_room)
public class HotelRoomFragment extends BaseFragment implements AsyncExpandableListViewCallbacks<HotelRoomInfoBean, HotelRoomComboInfoBean>, BaseViewLayerInterface {

    private final String tag = getClass().getSimpleName();

    private LinearLayout llHotelInfoScreen;

    private View viewNoData;
    //筛选
    private View viewScreenHotel;

    private TextView tabNameHotelScreen;

    private TextView tvStartDate, tvStartDateWeek, tvEndDate, tvEndDateWeek, tvDuration;

    private HotelInfoScreenPopup hotelInfoScreenPopup;

    private AppCalendarPopup appCalendarPopup;

    private Hotel.GoodsType goodsType;
    /**
     * 酒店名称
     */
    private String hotelName;

    private long hotelId = 1l;

    private AsyncExpandableListView<HotelRoomInfoBean, HotelRoomComboInfoBean> mAsyncExpandableListView;

    private Context context;

    private HotelServicePersenter persenter;

    /**
     * 酒店房间请求参数
     */
    /**
     * 是否直销   1是；
     */
    private int directStatus = -1;
    /**
     * 付款方式 1到店付、2在线支付、3担保支付
     */
    private int paymentType = -1;

    /**
     * 用餐类型 0不含早、1单早、2双早
     */
    private int mealType = -1;
    /**
     * 1单床、2双床、3大床、4圆床、5水床、6单床/双床、7大床/单床
     */
    private int roomsType = -1;


    public static HotelRoomFragment getInstance(Hotel.GoodsType bean, String hotelName, long hotelId)
    {

        HotelRoomFragment sf = new HotelRoomFragment();

        sf.goodsType = bean;

        sf.hotelName = hotelName;

        sf.hotelId = hotelId;

        return sf;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        persenter = new HotelServicePersenter(this);

        context = getContext();

        initView(view);

        /**
         * 请求该酒店的房间信息
         */
        initRequest(true);
        initData();
    }

    private void initData()
    {
        //设置日期
        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        Date startDate = DateUtil.string2Date(productListParam.getArrDate(), DateUtil.DATE_FORMAT_DATE);

        Date endDate = DateUtil.string2Date(productListParam.getDepDate(), DateUtil.DATE_FORMAT_DATE);

        setHeadData(startDate, endDate);

    }

    private void setHeadData(Date startDate, Date endDate)
    {
        tvDuration.setText("共" + AppUtils.ToCH(DateUtil.naturalDaysBetween(startDate, endDate)) + getString(R.string.hotel_toast_night));
        tvStartDate.setText(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_MONTH_DAY));
        tvEndDate.setText(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_MONTH_DAY));
        tvStartDateWeek.setText(DateUtil.getDateExplain(startDate));
        tvEndDateWeek.setText(DateUtil.getDateExplain(endDate));
    }

    /**
     * 发送酒店房间信息请求
     */
    private void initRequest(boolean flag)
    {
        if (flag) {
            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);
        }

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        HotelRoomParam param = new HotelRoomParam(hotelId, productListParam.getArrDate(), productListParam.getDepDate());
        param.setDirectStatus(directStatus);
        param.setPaymentType(paymentType);
        param.setMealType(mealType);
        param.setRoomsType(roomsType);

        persenter.sendRoomProductRequest(param);
    }

    private void initView(View view)
    {
        llHotelInfoScreen = (LinearLayout) view.findViewById(R.id.llHotelInfoScreen);

        llHotelInfoScreen.removeAllViews();

        viewNoData = view.findViewById(R.id.viewNoData);

        String[] screenNameArray = getResources().getStringArray(R.array.hotel_screen_name_array);

        int[] screenValueInt = getResources().getIntArray(R.array.hotel_screen_value_array);

        int length = screenNameArray.length;

        for (int i = 0; i < length; i++) {

            SubItemBean tempBean = new SubItemBean();

            tempBean.setDefault(false);

            if (i == 0) {

                tempBean.setLocalDataHotelRoomCode(2);

            } else if (i == 1) {

                tempBean.setLocalDataHotelRoomCode(3);

            } else if (i == 2) {

                tempBean.setLocalDataHotelRoomCode(1);

            } else if (i == 3) {

                tempBean.setLocalDataHotelRoomCode(0);
            }

            tempBean.setFilterName(screenNameArray[i]);

            tempBean.setFilterId(String.valueOf(screenValueInt[i]));

            final View screenView = LayoutInflater.from(context).inflate(R.layout.view_activity_screen_info_item, null);

            final TextView tvScreenName = (TextView) screenView.findViewById(R.id.tvTabName);

            tvScreenName.setText(screenNameArray[i]);

            tvScreenName.setEnabled(false);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            lp.rightMargin = CommonUtil.dip2px(context, 8);

            llHotelInfoScreen.addView(screenView, i, lp);

            screenView.setTag(tempBean);

            screenView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    SubItemBean temp = (SubItemBean) v.getTag();

                    boolean flag = !(boolean) temp.isDefault();

                    tvScreenName.setEnabled(flag);

                    temp.setDefault(flag);

                    screenView.setTag(temp);

                    int localDataCode = temp.getLocalDataHotelRoomCode();

                    int filtedId = Integer.parseInt(temp.getFilterId());

                    if (localDataCode == 0) {

                        if (flag) {

                            directStatus = filtedId;

                        } else {

                            directStatus = -1;

                        }

                    } else if (localDataCode == 1) {

                        if (flag) {

                            paymentType = filtedId;

                        } else {

                            paymentType = -1;

                        }

                    } else if (localDataCode == 2) {

                        if (flag) {

                            mealType = filtedId;

                        } else {

                            mealType = -1;

                        }

                    } else if (localDataCode == 3) {

                        if (flag) {

                            roomsType = filtedId;

                        } else {

                            roomsType = -1;

                        }
                    }

                    initRequest(false);

                }
            });
        }

        view.findViewById(R.id.llSetDatePP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (appCalendarPopup == null) {

                    //设置日期
                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    Date startDate = DateUtil.string2Date(productListParam.getArrDate(), DateUtil.DATE_FORMAT_DATE);

                    Date endDate = DateUtil.string2Date(productListParam.getDepDate(), DateUtil.DATE_FORMAT_DATE);

                    appCalendarPopup = new AppCalendarPopup(getActivity(), -1, startDate, endDate, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    appCalendarPopup.setCalendarCallBack(calendarDataCallBack);

                }

                appCalendarPopup.showBottomByViewPP(getView());
            }
        });

        viewScreenHotel = view.findViewById(R.id.viewScreen);

        tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);

        tvStartDateWeek = (TextView) view.findViewById(R.id.tvStartDateWeek);

        tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);

        tvEndDateWeek = (TextView) view.findViewById(R.id.tvEndDateWeek);

        tvDuration = (TextView) view.findViewById(R.id.tvDuration);

        tabNameHotelScreen = (TextView) viewScreenHotel.findViewById(R.id.tvTabName);

        tabNameHotelScreen.setText(R.string.text_sx);

        tabNameHotelScreen.setEnabled(false);

        viewScreenHotel.setTag(R.id.tvTabName, false);

        viewScreenHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean flag = !(boolean) v.getTag(R.id.tvTabName);

                tabNameHotelScreen.setEnabled(flag);

                viewScreenHotel.setTag(R.id.tvTabName, flag);

                if (hotelInfoScreenPopup == null) {

                    hotelInfoScreenPopup = new HotelInfoScreenPopup(getActivity(), CommonUtil.dip2px(context, 330), BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    hotelInfoScreenPopup.setCallBack(ppViewDataCallBack);

                }

                hotelInfoScreenPopup.show(getView());
            }
        });


        initRoomInfoView(view);

    }

    private AppCalendarPopup.DataCalendarCallBack calendarDataCallBack = new AppCalendarPopup.DataCalendarCallBack() {
        @Override
        public void onSelectorStartEndDate(Date startDate, Date endDate)
        {

            setHeadData(startDate, endDate);
            //设置日期
            HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

            productListParam.setArrDate(DateUtil.date2String(startDate, DateUtil.DATE_FORMAT_DATE));

            productListParam.setDepDate(DateUtil.date2String(endDate, DateUtil.DATE_FORMAT_DATE));

            initRequest(false);
        }
    };

    private void initRoomInfoView(View view)
    {

        mAsyncExpandableListView = (AsyncExpandableListView) view.findViewById(R.id.asyncExpandableCollectionView);
        mAsyncExpandableListView.setCallbacks(this);

    }

    private HotelInfoScreenPopup.ViewDataCallBack ppViewDataCallBack = new HotelInfoScreenPopup.ViewDataCallBack() {
        @Override
        public void onConfirm(List<SubItemBean> selectedBean)
        {
            int size = selectedBean.size();
            //设置支付方式、餐种信息、床型数据
            if (size > 0) {

                for (SubItemBean bean : selectedBean) {

                    int code = bean.getLocalDataHotelRoomCode();

                    int filterIdInt = Integer.parseInt(bean.getFilterId());

                    if (code == 0) {

                        directStatus = filterIdInt;

                    } else if (code == 1) {

                        paymentType = filterIdInt;

                    } else if (code == 2) {

                        mealType = filterIdInt;

                    } else if (code == 3) {

                        roomsType = filterIdInt;
                    }

                }

                //检查下 单早、大床 到店付 直销 选择状态
                int count = llHotelInfoScreen.getChildCount();

                for (int a = 0; a < count; a++) {

                    View view = llHotelInfoScreen.getChildAt(a);

                    SubItemBean subItemBean = (SubItemBean) view.getTag();

                    int code = subItemBean.getLocalDataHotelRoomCode();

                    boolean ifDefault = subItemBean.isDefault();

                    if (ifDefault) {

                        int filedId = Integer.parseInt(subItemBean.getFilterId());

                        final TextView tvScreenName = (TextView) view.findViewById(R.id.tvTabName);

                        if (code == 0) {//直销

                                if( directStatus != filedId ){

                                    tvScreenName.setEnabled(false);

                                    subItemBean.setDefault(false);
                                }

                        } else if (code == 1) {//到店付

                            if( paymentType != filedId ){

                                tvScreenName.setEnabled(false);

                                subItemBean.setDefault(false);
                            }

                        } else if (code == 2) {//单早

                            if( mealType != filedId ){

                                tvScreenName.setEnabled(false);

                                subItemBean.setDefault(false);
                            }

                        } else if (code == 3) {//大床

                            if( roomsType != filedId ){

                                tvScreenName.setEnabled(false);

                                subItemBean.setDefault(false);
                            }
                        }
                    }
                }

            } else {

                directStatus = -1;

                paymentType = -1;

                mealType = -1;

                roomsType = -1;
                //清理当前页面的 单早、大床、到店付和直销的选择状态
                int count = llHotelInfoScreen.getChildCount();

                for (int a = 0; a < count; a++) {

                    View view = llHotelInfoScreen.getChildAt(a);

                    final TextView tvScreenName = (TextView) view.findViewById(R.id.tvTabName);

                    tvScreenName.setEnabled(false);

                }

            }

            initRequest(false);

        }
    };

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == HotelServicePersenter.HOTEL_ROOM_PRODUCT_CODE) {

            List<HotelRoomInfoBean> list = JSONArray.parseArray(o + "", HotelRoomInfoBean.class);

            CollectionView.Inventory<HotelRoomInfoBean, HotelRoomComboInfoBean> inventory = new CollectionView.Inventory<>();

            int size = list.size();

            for (int i = 0; i < size; i++) {

                CollectionView.InventoryGroup<HotelRoomInfoBean, HotelRoomComboInfoBean> group = inventory.newGroup(i);

                group.setHeaderItem(list.get(i));
            }
            mAsyncExpandableListView.updateInventory(inventory);

           int v = mAsyncExpandableListView.getVisibility();

            if(v ==View.GONE){

                mAsyncExpandableListView.setVisibility(View.VISIBLE);

                viewNoData.setVisibility(View.GONE);
            }

        } else if (type == HotelServicePersenter.HOTEL_ROOM_COMBO_CODE) {

            WeakReference<AsyncExpandableListView<HotelRoomInfoBean, HotelRoomComboInfoBean>> listviewRef = new WeakReference<>(mAsyncExpandableListView);

            List<HotelRoomComboInfoBean> list = JSONArray.parseArray(o + "", HotelRoomComboInfoBean.class);

            if (listviewRef.get() != null) {
                listviewRef.get().onFinishLoadingGroup(mGroupOrdinal, list);
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == HotelServicePersenter.HOTEL_ROOM_PRODUCT_CODE) {

            if(o == null || TextUtils.isEmpty(o+"")){
                return;
            }

            com.yzb.card.king.bean.common.Error error = JSONObject.parseObject(JSON.toJSONString(o), com.yzb.card.king.bean.common.Error.class);

            if (HttpConstant.NOINFO.equals(error.getCode())) {

                int v = viewNoData.getVisibility();

                if(v ==View.GONE){

                    mAsyncExpandableListView.setVisibility(View.GONE);

                    viewNoData.setVisibility(View.VISIBLE);
                }
            }else {

                HttpUtil.responseCallBackMessage(error.getCode());
            }

        } else if (type == HotelServicePersenter.HOTEL_ROOM_COMBO_CODE) {

            WeakReference<AsyncExpandableListView<HotelRoomInfoBean, HotelRoomComboInfoBean>> listviewRef = new WeakReference<>(mAsyncExpandableListView);

            if (listviewRef.get() != null) {

                listviewRef.get().onGroupClicked(mGroupOrdinal);
            }
        }

    }

    private int mGroupOrdinal;

    @Override
    public void onStartLoadingGroup(int groupOrdinal)
    {
        this.mGroupOrdinal = groupOrdinal;

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        HotelRoomInfoBean hotelRoomInfoBean = mAsyncExpandableListView.getHeader(mGroupOrdinal);

        HotelRoomParam param = new HotelRoomParam(hotelRoomInfoBean.getRoomsId(), productListParam.getArrDate());
        param.setDirectStatus(directStatus);
        param.setPaymentType(paymentType);
        param.setMealType(mealType);
        param.setRoomsType(roomsType);

        persenter.sendRoomComboRequest(param);
    }

    @Override
    public AsyncHeaderViewHolder newCollectionHeaderView(Context context, int groupOrdinal, ViewGroup parent)
    {

            View v = LayoutInflater.from(context)
                    .inflate(R.layout.view_item_hotel_room_info, parent, false);//

            return new MyHeaderViewHolder(v, groupOrdinal, mAsyncExpandableListView);

    }

    @Override
    public RecyclerView.ViewHolder newCollectionItemView(Context context, int groupOrdinal, ViewGroup parent)
    {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.view_item_hotel_room_combo_info, parent, false);

        return new NewsItemHolder(v);
    }

    @Override
    public void bindCollectionHeaderView(Context context, AsyncHeaderViewHolder holder, int groupOrdinal, HotelRoomInfoBean headerItem)
    {
        MyHeaderViewHolder myHeaderViewHolder = (MyHeaderViewHolder) holder;

        myHeaderViewHolder.tvRoomComboName.setText(headerItem.getRoomsName());

        StringBuffer sb = new StringBuffer();

        String bedTypeDesc = headerItem.getBedTypeDesc();

        if (!TextUtils.isEmpty(bedTypeDesc)) {
            sb.append(bedTypeDesc + " ");
        }
        String wifiDesc = headerItem.getWifiDesc();
        if (!TextUtils.isEmpty(wifiDesc)) {
            sb.append(wifiDesc + " ");
        }
        String areaDesc = headerItem.getAreaDesc();
        if (!TextUtils.isEmpty(areaDesc)) {
            sb.append(areaDesc + " ");
        }
//        String floorDesc = headerItem.getFloorDesc();
//        if (!TextUtils.isEmpty(floorDesc)) {
//            sb.append(floorDesc + " ");
//        }

//        String smokeDesc = headerItem.getSmokeDesc();
//        if (!TextUtils.isEmpty(smokeDesc)) {
//            sb.append(smokeDesc + " ");
//        }

//        String windowDesc = headerItem.getWindowDesc();
//        if (!TextUtils.isEmpty(windowDesc)) {
//            sb.append(windowDesc + " ");
//        }
        myHeaderViewHolder.tvRoomFacDesc.setText(sb.toString());

        myHeaderViewHolder.tvHotelRoomComboPrice.setText(Utils.subZeroAndDot(headerItem.getMinPrice() + ""));

        String photoUrls = headerItem.getPhotoUrls();

        if (photoUrls != null) {

            myHeaderViewHolder.rlRoomImage.setTag(R.id.rlRoomImage, photoUrls);

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
    public void bindCollectionItemView(Context context, RecyclerView.ViewHolder holder, int groupOrdinal, HotelRoomComboInfoBean item)
    {
        NewsItemHolder newsItemHolder = (NewsItemHolder) holder;

        newsItemHolder.tvTitle.setText(item.getMealTypeDesc());

        HotelRoomInfoBean hotelRoomInfoBean = mAsyncExpandableListView.getHeader(mGroupOrdinal);

        item.setHotelName(hotelName);

        item.setHotelId(hotelId);

        item.setGoodsType(goodsType.getGoodsTypeCode());

        HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

        item.setArrDate(productListParam.getArrDate());

        item.setDepDate(productListParam.getDepDate());

        item.setRoomInfo(hotelRoomInfoBean);

        newsItemHolder.itemView.setTag(item);

        newsItemHolder.llPayCombo.setTag(item);

        int directStatus = item.getDirectStatus();

        if (directStatus == 1) {//直销
            newsItemHolder.tvDescription.setText("酒店直销");
        } else {
            newsItemHolder.tvDescription.setText(item.getShopName());

        }

        FavInfoBean pre = item.getDisMap();

        if ("1".equals(pre.getCashbackStatus()) || "1".equals(pre.getMinusStatus())) {

            newsItemHolder.tvBackMoney.setVisibility(View.VISIBLE);

            if ("1".equals(pre.getCashbackStatus())) {

                newsItemHolder.tvBackMoney.setText("返");

            } else if ("1".equals(pre.getMinusStatus())) {

                newsItemHolder.tvBackMoney.setText("减");
            }

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


        if ("1".equals(pre.getTicketStatus())) {

            newsItemHolder.tvQuan.setVisibility(View.VISIBLE);
        } else {

            newsItemHolder.tvQuan.setVisibility(View.GONE);
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

        if ("1".equals(pre.getLeftStatus())) {//显示甩房价格

            newsItemHolder.tvLeftRoom.setTextColor(getResources().getColor(R.color.color_d6c07f));

            newsItemHolder.tvRoomComboPrice.setText(Utils.subZeroAndDot(item.getLeftPrice() + ""));

        } else {//显示套餐价格


            newsItemHolder.tvRoomComboPrice.setText(Utils.subZeroAndDot(item.getPolicysPrice() + ""));

            String chshbackStatus = item.getDisMap().getCashbackStatus();

            if ("1".equals(chshbackStatus)) {

                newsItemHolder.tvLeftRoom.setTextColor(getResources().getColor(R.color.color_999999));

                newsItemHolder.tvLeftRoom.setText("¥" + Utils.subZeroAndDot(item.getPolicysPrice()+"") + "返" + Utils.subZeroAndDot(item.getBackPrice()+""));


            } else if ("1".equals(item.getDisMap().getMinusStatus())) {

                newsItemHolder.tvLeftRoom.setTextColor(getResources().getColor(R.color.color_999999));

                newsItemHolder.tvLeftRoom.setText("立减¥" + Utils.subZeroAndDot(item.getMinusPrice()+""));
            } else {
                newsItemHolder.tvLeftRoom.setText("");
            }

        }

        String giveContent = item.getGiveContent();

        if (TextUtils.isEmpty(giveContent)) {

            newsItemHolder.lineYellow.setVisibility(View.INVISIBLE);

            newsItemHolder.llGiveContent.setVisibility(View.GONE);

        } else {

            newsItemHolder.lineYellow.setVisibility(View.VISIBLE);

            newsItemHolder.llGiveContent.setVisibility(View.VISIBLE);

            newsItemHolder.tvGiveContent.setText(giveContent);
        }

        int payType = item.getPaymentType();

        HoteUtil.payTypeSetMessage(payType, newsItemHolder.tvPayType);

    }


    public class MyHeaderViewHolder extends AsyncHeaderViewHolder implements AsyncExpandableListView.OnGroupStateChangeListener {

        public final TextView tvRoomComboName;
        public final TextView tvRoomFacDesc;
        public final TextView tvHotelRoomComboPrice;
        public final ImageView ivRoomImage;
        public final TextView tvRoomImageNumber;
        private final ProgressBar mProgressBar;
        private ImageView ivExpansionIndicator;
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

            rlRoomImage = (RelativeLayout) v.findViewById(R.id.rlRoomImage);

            rlRoomImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    if (v.getTag(R.id.rlRoomImage) != null) {

                        String photoUrls = (String) v.getTag(R.id.rlRoomImage);

                        Intent intent = new Intent(getContext(), HotelImageExpositionActivity.class);

                        intent.putExtra("imageTitleName", "房间图片");

                        intent.putExtra("photoUrls", photoUrls);

                        startActivity(intent);

                    }
                }
            });
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


        public final TextView tvTitle;

        public final TextView tvDescription;

        public final TextView tvBackMoney, tvBankPre, tvQuan, tvQiang, tvKaquanyi, tvLeftRoom;

        public final ImageView ivLifeStages;

        public final View lineYellow;

        public final LinearLayout llGiveContent;

        public final LinearLayout llPayCombo;

        public final TextView tvPayType;

        public final TextView tvGiveContent;

        public final TextView tvRoomComboPrice;
        ;

        public NewsItemHolder(View v)
        {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    HotelRoomComboInfoBean bean = (HotelRoomComboInfoBean) v.getTag();

                    HotelProductRoomInfoFragmentDialog hotelProductFragmentDialog = new HotelProductRoomInfoFragmentDialog();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("data", bean);

                    hotelProductFragmentDialog.setArguments(bundle);

                    hotelProductFragmentDialog.show(getFragmentManager().beginTransaction(), "roomFt");

                }
            });
            tvTitle = (TextView) v.findViewById(R.id.tvMealTypeDesc);

            tvDescription = (TextView) v.findViewById(R.id.tvShopName);

            tvBackMoney = (TextView) v.findViewById(R.id.tvBackMoney);

            ivLifeStages = (ImageView) v.findViewById(R.id.ivLifeStages);

            tvBankPre = (TextView) v.findViewById(R.id.tvBankPre);

            tvQuan = (TextView) v.findViewById(R.id.tvQuan);

            tvQiang = (TextView) v.findViewById(R.id.tvQiang);

            tvKaquanyi = (TextView) v.findViewById(R.id.tvKaquanyi);

            tvLeftRoom = (TextView) v.findViewById(R.id.tvLeftRoom);

            lineYellow = v.findViewById(R.id.lineYellow);

            tvPayType = (TextView) v.findViewById(R.id.tvPayType);

            tvGiveContent = (TextView) v.findViewById(R.id.tvGiveContent);

            tvRoomComboPrice = (TextView) v.findViewById(R.id.tvRoomComboPrice);

            llGiveContent = (LinearLayout) v.findViewById(R.id.llGiveContent);

            llPayCombo = (LinearLayout) v.findViewById(R.id.llPayCombo);

            llPayCombo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    //检查是否登录
                    if (UserManager.getInstance().isLogin()) {
                        HotelRoomComboInfoBean bean = (HotelRoomComboInfoBean) v.getTag();
                        Intent intent = new Intent(context, HotelRoomOrderActivity.class);
                        intent.putExtra("HotelRoomComboInfoBean", bean);
                        context.startActivity(intent);
                    } else {

                        new GoLoginDailog(HotelRoomFragment.this.getContext()).create().show();
                    }
                }
            });
        }

    }

}
