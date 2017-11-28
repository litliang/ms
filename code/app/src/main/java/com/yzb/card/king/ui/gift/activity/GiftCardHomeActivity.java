package com.yzb.card.king.ui.gift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.FragmentMessageEvent;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.bean.my.CouponChannelBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.activity.ActivatePhysCardActivity;
import com.yzb.card.king.ui.gift.adapter.GiftcardChannelAdapger;
import com.yzb.card.king.ui.gift.adapter.ShopGiftCardAdapter;
import com.yzb.card.king.ui.gift.bean.GiftCardProductBean;
import com.yzb.card.king.ui.gift.fragment.SendGiftCardDialog;
import com.yzb.card.king.ui.my.presenter.AccountInfoPresenter;
import com.yzb.card.king.ui.gift.presenter.GiftCardStoreProductPresenter;
import com.yzb.card.king.ui.gift.view.GiftCardProductView;
import com.yzb.card.king.ui.order.OrderManageActivity;
import com.yzb.card.king.ui.travel.adapter.SpaceItemDecoration;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我的-->礼品卡首页；
 *
 * @author gengqiyun
 * @date 2016.12.14
 */
@ContentView(R.layout.activity_giftcard_home)
public class GiftCardHomeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        BaseViewLayerInterface, GiftCardProductView {
    @ViewInject(R.id.giftcardSumAmount)
    private TextView giftcardSumAmount;
    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.gvChannels)
    private SpecHeightGridView gvChannels;

    @ViewInject(R.id.giftcard_shop_recyclerview)
    private WholeRecyclerView giftcard_shop_recyclerview;

    @ViewInject(R.id.giftcard_egiftcard_shop_recyclerview)
    private WholeRecyclerView giftcard_egiftcard_shop_recyclerview;

    @ViewInject(R.id.discount_card_giftcard_more)
    private LinearLayout discount_card_giftcard_more;

    @ViewInject(R.id.discount_card_e_giftcard_more)
    private LinearLayout discount_card_e_giftcard_more;

    private GiftcardChannelAdapger channelAdapger;

    private AccountInfoPresenter accountInfoP;

    private GiftCardStoreProductPresenter productPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accountInfoP = new AccountInfoPresenter(this);
        productPresenter = new GiftCardStoreProductPresenter(this);
        initView();
        initAdapter();
        initData();
    }

    private void initView()
    {

        setTitleNmae("送礼品卡");

        updateTotalAmount(0);
        channelAdapger = new GiftcardChannelAdapger(this);
        channelAdapger.setHandler(dataHandler);

        gvChannels.setAdapter(channelAdapger);
        channelAdapger.appendALL(getGiftCardHomeChannels());

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        findViewById(R.id.panelScanDetail).setOnClickListener(this);

        discount_card_giftcard_more.setOnClickListener(this);

        discount_card_e_giftcard_more.setOnClickListener(this);

    }

    private List<GiftCardProductBean> listProdtct = new ArrayList<GiftCardProductBean>();

    private ShopGiftCardAdapter adapter;

    private List<GiftCardProductBean> listEProdtct = new ArrayList<GiftCardProductBean>();

    private ShopGiftCardAdapter eAdapter;

    private void initAdapter()
    {
        adapter = new ShopGiftCardAdapter(this, listProdtct);
        adapter.setViewType(1);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        giftcard_shop_recyclerview.setLayoutManager(manager);
        SpaceItemDecoration dec = new SpaceItemDecoration(CommonUtil.dip2px(this, 20));
        dec.setHInt(3);
        giftcard_shop_recyclerview.addItemDecoration(new SpaceItemDecoration(CommonUtil.dip2px(this, 20)));
        giftcard_shop_recyclerview.setAdapter(adapter);
        adapter.setOnNotItemClickListener(new ShopGiftCardAdapter.OnNotItemClickListener() {
            @Override
            public void OnNotItemClick(RecyclerView parent, View view, int position, List<GiftCardProductBean> data)
            {
                Intent intent = new Intent(GiftCardHomeActivity.this, GiftCardGiveMindActivity.class);
                intent.putExtra("productId", data.get(position).getProductId() + "");
                intent.putExtra("blessWord", data.get(position).getBlessWord());
                intent.putExtra("imageCode", data.get(position).getImageCode());
                intent.putExtra("titleName",  data.get(position).getTypeName());
                startActivity(intent);
            }
        });


        eAdapter = new ShopGiftCardAdapter(this, listEProdtct);
        eAdapter.setViewType(1);
        GridLayoutManager managere = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        giftcard_egiftcard_shop_recyclerview.setLayoutManager(managere);
        SpaceItemDecoration dece = new SpaceItemDecoration(CommonUtil.dip2px(this, 20));
        dece.setHInt(3);
        giftcard_egiftcard_shop_recyclerview.addItemDecoration(new SpaceItemDecoration(CommonUtil.dip2px(this, 20)));
        giftcard_egiftcard_shop_recyclerview.setAdapter(eAdapter);
        eAdapter.setOnNotItemClickListener(new ShopGiftCardAdapter.OnNotItemClickListener() {
            @Override
            public void OnNotItemClick(RecyclerView parent, View view, int position, List<GiftCardProductBean> data)
            {
                Intent intent = new Intent(GiftCardHomeActivity.this, GiftCardGiveMindActivity.class);
                intent.putExtra("productId", data.get(position).getProductId() + "");
                intent.putExtra("blessWord", data.get(position).getBlessWord());
                intent.putExtra("imageCode", data.get(position).getImageCode());
                intent.putExtra("titleName",  data.get(position).getTypeName());
                startActivity(intent);
            }
        });
    }

    private void initData()
    {

        productPresenter.sendHomeGiftXinYiRecommandRequest();

        productPresenter.sendHomeGiftECardRecommandRequest();
    }

    /**
     * 获取账户信息；
     */
    private void loadAccountInfo()
    {
        Map<String, Object> args = new HashMap<>();
        args.put("amountAccount", getUserBean().getAmountAccount());
        args.put("giftcardStatus", "1"); //查询礼品卡余额
        accountInfoP.loadData(args);
    }


    private List<CouponChannelBean> getGiftCardHomeChannels()
    {
        int[] imageResIds = {R.mipmap.icon_home_gift_bottom_elect, R.mipmap.icon_home_gift_bottom_shitika,
                R.mipmap.icon_home_gift_bottom_exchangecard, R.mipmap.icon_home_gift_bottom_order};

        String[] labelResIds = getResources().getStringArray(R.array.giftcard_home_bottom_channels);

        List<CouponChannelBean> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CouponChannelBean bean = new CouponChannelBean();
            bean.setImgResId(imageResIds[i]);
            bean.setText(labelResIds[i]);
            data.add(bean);
        }
        return data;
    }

    /**
     * 更新礼品卡总金额；
     */
    private void updateTotalAmount(float amount)
    {
        SpannableString ss = new SpannableString("¥" + String.format("%.2f", amount));
        //  ss.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(10)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        giftcardSumAmount.setText(ss);
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what) {
                case GiftcardChannelAdapger.WHAT_CHANNEL_CLICK:
                    targetChannel(msg.arg1);
                    break;
                case SendGiftCardDialog.WHAT_SHARE_PLATFORM: //分享-嗨生活；
//                    Intent intent = new Intent(GiftCardHomeActivity.this, GiveCardActivity.class);
//                    intent.putExtra("recordIds", adapter.getSelectRecordIds());
//                    intent.putExtra("pageType", GiveCardActivity.TYPE_GIFTCARD);
//                    startActivity(intent);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onResume()
    {
        super.onResume();
        loadAccountInfo();
    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            goBack();
//            return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }
//
//    public void backAction(View v)
//    {
//
//        goBack();
//    }

    /**
     * 频道跳转；
     */
    private void targetChannel(int pos)
    {
        switch (pos) {
            case 0: //电子卡
//                GlobalApp.backFlag = true;
//                FragmentMessageEvent event = new FragmentMessageEvent();
//                event.setFragmentIndex(4);
//                EventBus.getDefault().post(event);
//                finish();
               // readyGo(GiftCardHomeActivity.this, BuyMindECardActivity.class);
               // ToastUtil.i(GiftCardHomeActivity.this, "敬请期待");
                initData();
                break;
            case 1://实体卡
                // readyGo(this, ActivatePhysCardActivity.class);
                readyGo(GiftCardHomeActivity.this, BuyPhysicalCardActivity.class);
                break;
            case 2://卡兑换
                //readyGo(this, GiftCardRecvECardActivity.class);
                ToastUtil.i(GiftCardHomeActivity.this, "敬请期待");
                break;
            case 3://卡订单
                Intent intent = new Intent(this, OrderManageActivity.class);
                intent.putExtra("orderType", OrderBean.ORDER_TYPE_LIPING);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh()
    {
        loadAccountInfo();
        initData();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.panelScanDetail:  //查看明细；
                Intent intent = new Intent(this, GiftCardBillDetailActivity.class);
                intent.putExtra("billDetail", "礼品卡明细");
                startActivity(intent);
                break;
            case R.id.discount_card_giftcard_more:

                Intent intent1One = new Intent(GiftCardHomeActivity.this, BuyMindECardActivity.class);

                intent1One.putExtra("ecardType",1);

                intent1One.putExtra("titleName","选购心意卡");

                startActivity(intent1One);

                break;
            case R.id.discount_card_e_giftcard_more:

                Intent intent1Two = new Intent(GiftCardHomeActivity.this, BuyMindECardActivity.class);

                intent1Two.putExtra("ecardType",2);

                intent1Two.putExtra("titleName","选购e卡");

                startActivity(intent1Two);

                break;

        }
    }

//    private void goBack()
//    {
//        GlobalApp.backFlag = true;
//        FragmentMessageEvent backEvent = new FragmentMessageEvent();
//        backEvent.setFragmentIndex(3);
//        EventBus.getDefault().post(backEvent);
//        finish();
//    }



    @Override
    public void onLoadProductSuccess(Object o, int type)
    {
        if (type == 888) {//礼品卡  心意卡
            List<GiftCardProductBean> listProdtct = JSON.parseArray(String.valueOf(o), GiftCardProductBean.class);
            this.listProdtct.clear();

            this.listProdtct.addAll(listProdtct);

            adapter.notifyDataSetChanged();
        } else if (type == 889) {//礼品卡  e卡
            List<GiftCardProductBean> listProdtct = JSON.parseArray(String.valueOf(o), GiftCardProductBean.class);
            this.listEProdtct.clear();

            this.listEProdtct.addAll(listProdtct);

            eAdapter.notifyDataSetChanged();
        }

        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadProductFail(Object o, int type)
    {
        if (type == 1) {
            toastCustom("无返回数据");
        }

        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        AccountInfoBean accountInfoBean = (AccountInfoBean) o;

        updateTotalAmount(accountInfoBean.getGiftcardBalance());
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

        ToastUtil.i(this, "" + o);
    }
}
