package com.yzb.card.king.ui.bonus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.bean.BounsThemeParam;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.adapter.RedBagDetailAdapter;
import com.yzb.card.king.ui.bonus.bean.RedBagRecOrSendBean;
import com.yzb.card.king.ui.gift.activity.GiveCardActivity;
import com.yzb.card.king.ui.gift.fragment.SendGiftCardDialog;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.bonus.presenter.RedBagRecOrSendPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： 红包明细详情
 * 作 者： gaolei
 * 日 期：2016年12月26日
 * 描 述：红包明细详情
 */

public class RedBagDetailSendActivity extends BaseActivity implements RedBagDetailAdapter.OnMyItemClickListener, View.OnClickListener, BaseViewLayerInterface, CommandView {
    private RecyclerView recycler;
    private RedBagDetailAdapter adapter;
    private RedBagRecOrSendPresenter presenter;
    private RedBagRecOrSendBean listBean;
    private List<RedBagRecOrSendBean.ReceiveListBean> receiveList;
    private ImageView thumbImg; //顶部的封面图；
    private ImageView userImg;
    private TextView userName;
    private TextView hopeWord;
    private TextView allMessage;
    private TextView amouttype;
    private TextView recAcount;
    private TextView red_bag_titleName;
    private TextView tvPromte;
    private View goOn;
    private BounsThemeParam themeParam;
    private String orderId;
    private String orderNo; //订单号；
    private ImageOptions imageOptions;
    private String themeName;
    private CommandPresenter commandPresenter;
    private int surplusNum;//剩余数量
    private String isequal = "true";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_redbag_send_detail);

        recvIntentData();

        initView();

        commandPresenter = new CommandPresenter(this);

        presenter = new RedBagRecOrSendPresenter(this);
        //预览；
        if (themeParam != null) {
            initViewContent();
        } else {
            initPresent();
        }
    }

    String sum;
    String num;

    /**
     * gengqiyun 2017.2.6
     */
    private void initViewContent() {
        setTitleNmae(themeParam.getBlessWord());
        x.image().bind(userImg, getUserBean().getPic(),
                XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(30), ImageView.ScaleType.FIT_CENTER));//发送方图片
        if(themeParam.getOpenImageCode()!=null) {
            x.image().bind(thumbImg, ServiceDispatcher.getImageUrl(themeParam.getOpenImageCode()));
        }
        userName.setText(themeParam.getBounsSender() + "的红包");
        red_bag_titleName.setText(themeParam.getThemeName());
        hopeWord.setText(themeParam.getBlessWord());
        recAcount.setVisibility(View.GONE);
            num = (TextUtils.isEmpty(themeParam.getBounsNum()) ? "0" : themeParam.getBounsNum());
            sum = (TextUtils.isEmpty(themeParam.getBounsAmount()) ? "0" : themeParam.getBounsAmount());
        amouttype.setText(isequal.equals("true")?"等值金额":"随机金额");
        if (themeParam.isRandom()) {
            allMessage.setText(
                    "红包总额：" + (sum = (TextUtils.isEmpty(themeParam.getBounsAmount()) ? "0" : themeParam.getBounsAmount())) + "元"
                            + "       " + "发送人数：" + num + "人");
        } else {
            allMessage.setText(
                    "红包总额：" + (sum = (TextUtils.isEmpty(themeParam.getBounsAmount()) ? "0" : themeParam.getBounsAmount())) + "元"
                            + "       " + "发送人数：" + num + "人");
        }
    }

    private void recvIntentData() {
        Intent intent = getIntent();
        orderId = intent.getExtras().getString("orderId");
        themeName = intent.getExtras().getString("themeName");

        Serializable ser = intent.getSerializableExtra("themeParam");
        if (ser != null) {
            themeParam = (BounsThemeParam) ser;
        }

    }

    private void initData(boolean flag) {

        if (receiveList != null) {
            adapter = new RedBagDetailAdapter(this, receiveList);
            adapter.setStatuFlag(flag);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(manager);
            recycler.addItemDecoration(new DecorationUtil(2));
            recycler.setAdapter(adapter);
            adapter.setOnMyItemClickListener(this);
            adapter.notifyDataSetChanged();
        }

    }

    private void initView() {
        recycler = (RecyclerView) findViewById(R.id.red_bag_detail_recyclerview);
        userImg = (ImageView) findViewById(R.id.redbag_detail_usercicle_img);
        thumbImg = (ImageView) findViewById(R.id.thumbImg);

        userName = (TextView) findViewById(R.id.redbag_detail_user_txt);
        hopeWord = (TextView) findViewById(R.id.redbag_detail_dreamword_txt);
        allMessage = (TextView) findViewById(R.id.redbag_detail_allmessage_txt);
        recAcount = (TextView) findViewById(R.id.redbag_detail_totalmoney_txt);
        tvPromte = (TextView) findViewById(R.id.tvPromte);
        red_bag_titleName = (TextView) findViewById(R.id.red_bag_titleName);
        red_bag_titleName.setText(themeName);
        amouttype= (TextView) findViewById(R.id.amouttype);
        findViewById(R.id.tvContinueSend).setOnClickListener(this);

        goOn = findViewById(R.id.panelContinueSend);
        goOn.setVisibility(View.GONE);

        recycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                recycler.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        imageOptions = new ImageOptions.Builder()
                //  .setSize(org.xutils.common.util.DensityUtil.dip2px(30), org.xutils.common.util.DensityUtil.dip2px(30))
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(360))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                .setUseMemCache(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.icon_nav_user)
                .setFailureDrawableId(R.mipmap.icon_nav_user)
                .setCircular(false)
                .build();
    }

    private void initPresent() {
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        presenter.getRedBagDetailTotalData(param, CardConstant.REC_OR_SEND_DETAIL, 1);
    }

    @Override
    public void OnMyItemClick(RecyclerView parent, View view, int position, List<RedBagRecOrSendBean.ReceiveListBean> data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContinueSend: //继续发送；
                generateCommand();
                break;
        }
    }

    /**
     * 生成口令；
     */
    private void generateCommand() {
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType", AppConstant.command_type_bouns);
        args.put("code", orderNo); //订单号；
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        args.put("activityData", JSON.toJSONString(map));
        commandPresenter.loadData(args);
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SendGiftCardDialog.WHAT_SHARE_PLATFORM: //分享-嗨生活；
                    Intent intent = new Intent(RedBagDetailSendActivity.this, GiveCardActivity.class);
                    intent.putExtra("recordIds", orderId);
                    intent.putExtra("pageType", GiveCardActivity.TYPE_BOUNS);
                    intent.putExtra("totalNum", surplusNum);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    });

    @Override
    public void onGetCommandSuc(String commandAndUrl) {
        closePDialog();
        SendGiftCardDialog.getInstance()
                .setHandler(dataHandler).setFragmentManager(getSupportFragmentManager()).setCommandAndUrl(
                CommonUtil.getGiftcardShareContent("红包", commandAndUrl)).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onGetCommandFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        if (type == 1) {
            listBean = JSON.parseObject(String.valueOf(o), RedBagRecOrSendBean.class);
            orderNo = listBean.getOrderNo();
            red_bag_titleName.setText(listBean.getBlessWord());
            userName.setText(listBean.getIssuePerson() + "的红包");
            //String path = listBean.getIssueImageCode() +"";//图片加载 userImg
            x.image().bind(userImg, ServiceDispatcher.getImageUrl(listBean.getIssuePersonPhoto() + ""), imageOptions);//获取背景图片
            x.image().bind(thumbImg, ServiceDispatcher.getImageUrl(listBean.getOpenImageCode() + ""));//获取背景图片
            hopeWord.setText(listBean.getBlessWord() + "");
            String orderAcount = listBean.getOrderAmount();//订单金额
            int totalQuantity = listBean.getTotalQuantity();//发放数量
            int receiveQuantity = listBean.getReceiveQuantity();//领取数量

            surplusNum = totalQuantity - receiveQuantity;

            StringBuilder builder = new StringBuilder();

            num = listBean.getTotalQuantity()+"";
            sum = listBean.getOrderAmount()+"";
            themeParam = (BounsThemeParam) getIntent().getSerializableExtra("typedThemeParam");
            if(themeParam!=null) {
                amouttype.setText(!themeParam.isRandom() ? "等值金额" : "随机金额");
            }
            builder.append("红包总额：" + (orderAcount ) + "元"
                    + "       " + "发送人数：" + listBean.getReceiveList().size() + "人");
            allMessage.setText(builder.toString());
            receiveList = listBean.getReceiveList();

            for (int i = 0; i < receiveList.size(); i++) {
                if (receiveList.get(i).getReceivePerson().equals(listBean.getIssuePerson())) {
                    recAcount.setText(receiveList.get(i).getReceiveAmount() + "");
                    break;
                }
            }
            boolean orderStatus = (OrderBean.ORDER_STATUS_COMPLETE + "").equals(listBean.getOrderStatus());
            boolean flag = totalQuantity == receiveQuantity || !orderStatus;
            //已领取==总数量 或非已支付状态；
            goOn.setVisibility(flag ? View.GONE : View.VISIBLE);

            //
            if (flag) {
                tvPromte.setVisibility(View.GONE);
            } else {
                tvPromte.setVisibility(View.VISIBLE);
            }

            initData(flag);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {

    }
}