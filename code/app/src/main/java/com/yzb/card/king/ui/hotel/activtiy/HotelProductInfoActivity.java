package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.Hotel;
import com.yzb.card.king.bean.hotel.HotelDetailServiceBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.WrapContentHeightViewPager;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.fragment.HotelDiningRoomFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelGiftCombmFragment;
import com.yzb.card.king.ui.hotel.fragment.HotelRoomFragment;
import com.yzb.card.king.ui.hotel.model.IHotelDetail;
import com.yzb.card.king.ui.hotel.persenter.HotelDetailPersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.PjActivity;
import com.yzb.card.king.ui.other.activity.RoutePlanActivity;
import com.yzb.card.king.util.AppBarStateChangeListener;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：酒店产品详情
 * 作  者：Li Yubing
 * 日  期：2017/7/22
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_product_info)
public class HotelProductInfoActivity extends BaseActivity implements BaseViewLayerInterface, View.OnClickListener {

    @ViewInject(R.id.backdrop)
    private ImageView backdrop;

    //服务内容
    private SlidingTabLayout tabLayout_8;
    private WrapContentHeightViewPager vp;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String[] mTitles;


    @ViewInject(R.id.tvHotelName)
    private TextView tvHotelName;

    @ViewInject(R.id.tvHotelLevelDate)
    private TextView tvHotelLevelDate;

    @ViewInject(R.id.tvHotelImageNumber)
    private TextView tvHotelImageNumber;

    @ViewInject(R.id.tvHotelAddress)
    private TextView tvHotelAddress;

    @ViewInject(R.id.tvHotelCityDis)
    private TextView tvHotelCityDis;

    @ViewInject(R.id.tvHotelVote)
    private TextView tvHotelVote;

    @ViewInject(R.id.tvHoteVoteMessage)
    private TextView tvHoteVoteMessage;

    @ViewInject(R.id.tvHotelVoteMessageOne)
    private TextView tvHotelVoteMessageOne;

    @ViewInject(R.id.llPingjia)
    private LinearLayout llPingjia;

    private ImageView ivFav;

    private Hotel hotel = null;

    private ShareDialogFragment shareDialogFragment;

    private HotelDetailPersenter hotelServerGoodsView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        colorStatusResId = android.R.color.transparent;
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("hotelId")) {

            hotelId = Long.parseLong(getIntent().getStringExtra("hotelId"));
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);

        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.black));

        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state)
            {
                if (state == State.EXPANDED) {

                    //展开状态
                    Log.e("ABCE", "---------展开状态----");
                    collapsingToolbar.setTitle(" ");

                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                } else if (state == State.COLLAPSED) {

                    //折叠状态
                    Log.e("ABCE", "---------折叠状态----");
                    collapsingToolbar.setTitle(hotel.getHotelName());
                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.white));
                } else {

                    //中间状态
                    Log.e("ABCE", "---------中间状态----");
                    collapsingToolbar.setTitle(" ");

                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            }
        });

        initView();

        initRequest();

    }

    private long hotelId = 1L;


    private void initRequest()
    {
        showPDialog("正在请求数据……");

        hotelServerGoodsView = new HotelDetailPersenter(this);

        hotelServerGoodsView.sendSelectHotelServiceInfoRequest(hotelId + "");

    }


    private void initView()
    {
        findViewById(R.id.llQueryHotelImage).setOnClickListener(this);

        findViewById(R.id.ivShare).setOnClickListener(this);

        ivFav = (ImageView) findViewById(R.id.ivFav);
        ivFav.setOnClickListener(this);

        findViewById(R.id.llHotelMap).setOnClickListener(this);

        llPingjia.setOnClickListener(this);

    }



    private void hotelServerGoodsView(List<Hotel.GoodsType> goodsTypeList)
    {

        mFragments.clear();
        //分类条目
        int size = goodsTypeList.size();

        mTitles = new String[size];

        for (int i = 0; i < size; i++) {

            Hotel.GoodsType temp = goodsTypeList.get(i);

            mTitles[i] = temp.getGoodsTypeName();

            String typeCode = temp.getGoodsTypeCode();

            String hotelName = tvHotelName.getText().toString();

            if (HoteUtil.HOTEL_ROOM_CODE.equals(typeCode)) {//房间碎片

                mFragments.add(HotelRoomFragment.getInstance(temp, hotelName, hotelId));

            } else if (HoteUtil.HOTEL_DINING_ROOM_CODE.equals(typeCode) || HoteUtil.HOTEL_BAR_CODE.equals(typeCode)
                    || HoteUtil.HOTEL_SPA_CODE.equals(typeCode) || HoteUtil.HOTEL_LOBBY_BAR_CODE.equals(typeCode)
                    || HoteUtil.HOTEL_GYM_CODE.equals(typeCode) || HoteUtil.HOTEL_SWMMING_POOL_CODE.equals(typeCode)) {

                mFragments.add(HotelDiningRoomFragment.getInstance(temp, hotelName, hotelId));

            } else {

                mFragments.add(HotelGiftCombmFragment.getInstance(temp, hotelName, hotelId));
            }
        }

        View decorView = getWindow().getDecorView();

        if (vp == null) {
            vp = UiUtils.find(decorView, R.id.vp);
        }

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        vp.setAdapter(mAdapter);


        if (tabLayout_8 == null) {

            tabLayout_8 = UiUtils.find(decorView, R.id.tl_8);

        } else {

        }

        tabLayout_8.setViewPager(vp, mTitles, this, mFragments);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llHotelMap:

                if (hotel != null) {
                    Intent i = new Intent(this, RoutePlanActivity.class);
                    i.putExtra(RoutePlanActivity.ARG_LAT, hotel.getAddrMap().getLat());
                    i.putExtra(RoutePlanActivity.ARG_LNG, hotel.getAddrMap().getLng());
                    i.putExtra(RoutePlanActivity.ARG_CITY_ID, hotel.getAddrMap().getCityId());
                    startActivity(i);
                }

                break;

            case R.id.llQueryHotelImage:

                if (hotel != null) {
                    Intent hotelImageIntent = new Intent();
                    hotelImageIntent.setClass(this, HotelPicActivity.class);
                    hotelImageIntent.putExtra("hotelId", hotelId);
                    hotelImageIntent.putExtra("videoUrl", hotel.getVideoUrl());
                    hotelImageIntent.putExtra("defaultImgUrl", hotel.getDefaultImgUrl());
                    startActivity(hotelImageIntent);
                }

                break;


            case R.id.ivFav://收藏
                if (hotel == null) {

                    return;
                }

                if (!UserManager.getInstance().isLogin()) {

                    new GoLoginDailog(HotelProductInfoActivity.this).show();

                } else {

                    hotelServerGoodsView.collectHotel(hotel.getHotelId() + "",
                            !hotel.isCollectionStatus() ? "1" : "0", AppConstant.collect_shop_type,
                            AppConstant.collect_hotel_category);
                }

                break;
            case R.id.ivShare://分享

                if (hotel == null) {
                    return;
                }

                if (shareDialogFragment == null) {
                    showPDialog(R.string.loading);
                    shareDialogFragment = ShareDialogFragment.getInstance("", "");
                    shareDialogFragment.setContent(HotelProductInfoActivity.this);
                    shareDialogFragment.setTitle(hotel.getHotelName());
                    shareDialogFragment.setImage(ServiceDispatcher.getImageUrl(hotel.getDefaultImgUrl()));
                    shareDialogFragment.setContent("快来点开看看吧~");
                    shareDialogFragment.initShareContent(AppConstant.command_type_shop, hotelId, AppConstant.hotel_id);

                } else {
                    shareDialogFragment.show(getSupportFragmentManager(), "");
                }


                break;
            case R.id.llPingjia://评价

                if (hotel == null) {

                    return;
                }

                Intent pjIntent = new Intent(this, PjActivity.class);
                pjIntent.putExtra("industryId", 8);
                pjIntent.putExtra("storeId", hotelId);
                pjIntent.putExtra("pjTitle", hotel.getHotelName());
                pjIntent.putExtra("vote", hotel.getVote());
                pjIntent.putExtra("voteDesc", hotel.getVoteDesc());
                startActivity(pjIntent);

                break;
            default:
                break;
        }
    }



    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (type == IHotelDetail.HOTEL_COLLECT) //收藏酒店
        {

            hotel.setCollectionStatus(!hotel.isCollectionStatus());

            if (hotel.isCollectionStatus()) {
                ivFav.setBackgroundResource(R.mipmap.icon_hotel_faved);
                ToastUtil.i(HotelProductInfoActivity.this, getResources().getString(R.string.my_collect));
            } else {
                ToastUtil.i(HotelProductInfoActivity.this, getResources().getString(R.string.qxcoll));
                ivFav.setBackgroundResource(R.mipmap.icon_hotel_fav);
            }

        } else if(type == IHotelDetail.HOTEL_SERVER_CODE){

            if (o instanceof HotelDetailServiceBean) {

                HotelDetailServiceBean   hotelDetailServiceBean = (HotelDetailServiceBean) o;

                HotelLogicManager.getInstance().setHotelDetailServiceBean(hotelDetailServiceBean);

                showPDialog("正在请求数据……");
                //发送酒店详情请求
                HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                hotelServerGoodsView.getHotelDetail(hotelId + "", productListParam.getArrDate());
            }


        } else {

            if (o instanceof Hotel) {

                hotel = (Hotel) o;

                HotelLogicManager.getInstance().setHotel(hotel);

                //酒店收藏状态
                if (hotel.isCollectionStatus()) {

                    ivFav.setBackgroundResource(R.mipmap.icon_hotel_faved);

                } else {

                    ivFav.setBackgroundResource(R.mipmap.icon_hotel_fav);

                }

                tvHotelName.setText(hotel.getHotelName());

                if( hotel.getGoodsTypeList() != null && hotel.getGoodsTypeList().size() > 0){
                    hotelServerGoodsView(hotel.getGoodsTypeList());
                }

                String reDate = hotel.getRenovationDate();

                if (!TextUtils.isEmpty(reDate)) {

                    reDate =  reDate + "装修";

                }else {

                    if(!TextUtils.isEmpty(hotel.getOpeningDate())){

                        reDate =  hotel.getOpeningDate() + "开业";
                    }else {

                        reDate="";
                    }

                }

                if(TextUtils.isEmpty(hotel.getLevelDesc())){

                }else {

                    reDate = hotel.getLevelDesc() +"  "+ reDate;
                }
                LogUtil.e("AAAAA","reDate===>"+reDate);

                tvHotelLevelDate.setText( reDate);

                tvHotelImageNumber.setText(hotel.getPhotoQuantity() + "张");

                tvHotelImageNumber.setVisibility(View.VISIBLE);

                tvHotelCityDis.setText(hotel.getAddrMap().getDistrictName());

                tvHotelAddress.setText(hotel.getAddrMap().getAddress());

                tvHotelVote.setText(hotel.getVote() + "");

                HoteUtil.hotelVoteMessage(hotel.getVote(), tvHoteVoteMessage);

                tvHotelVoteMessageOne.setText(hotel.getVoteDesc());


                Glide.with(this).load(ServiceDispatcher.getImageUrl(hotel.getDefaultImgUrl())).into(backdrop);
            }

        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        dimissPdialog();
    }

    /**
     * 分类碎片适配器
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

    }
}
