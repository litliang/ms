package com.yzb.card.king.ui.gift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.presenter.AddressListPresenter;
import com.yzb.card.king.ui.app.view.AddressListView;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.gift.adapter.GiftcardLimitAdapter;
import com.yzb.card.king.ui.gift.adapter.GiftcardSelectLimitAdapter;
import com.yzb.card.king.ui.gift.bean.ECardBean;
import com.yzb.card.king.ui.gift.bean.GiftCardProductBean;
import com.yzb.card.king.ui.gift.bean.GiftcardLimitBean;
import com.yzb.card.king.ui.gift.presenter.BuyCardPresenter;
import com.yzb.card.king.ui.gift.presenter.GiftCardStoreProductPresenter;
import com.yzb.card.king.ui.gift.view.BuyCardView;
import com.yzb.card.king.ui.gift.view.GiftCardProductView;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.model.UpdatePayDetailModel;
import com.yzb.card.king.ui.my.pop.BuySucesWithOkDialog;
import com.yzb.card.king.ui.ticket.presenter.GetPostFeePresenter;
import com.yzb.card.king.ui.ticket.view.GetPostFeeView;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.XImageOptionUtil;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：礼品卡--购买心意实体卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/16
 */
@ContentView(R.layout.activity_buy_mind_physcard)
public class BuyMindPhysCardActivity extends BaseActivity implements View.OnClickListener, AddressListView,
        BuyCardView, GetPostFeeView {
    private static final int REQ_ADD_BANK_CARD = 0x002;
    @ViewInject(R.id.amoutGv)
    private SpecHeightGridView amoutGv; //面额；

    @ViewInject(R.id.tvGiftName)
    private TextView tvGiftName;

    @ViewInject(R.id.lv)
    private WholeListView lv; //已选；

    @ViewInject(R.id.ivCryptonym)
    private ImageView ivCryptonym; //匿名；

    @ViewInject(R.id.etInputAmount)
    private EditText etInputAmount;

    @ViewInject(R.id.tvPostFee)
    private TextView tvPostFee; //邮费；

    @ViewInject(R.id.addressName)
    private TextView addressName; //收货人；

    @ViewInject(R.id.addressMobile)
    private TextView addressMobile; //收货人电话；

    @ViewInject(R.id.addressAddress)
    private TextView addressAddress; //收货人地址；

    @ViewInject(R.id.tvTotalAmount)
    private TextView tvTotalAmount; //总金额；

    @ViewInject(R.id.ivEntityCardImage)
    private ImageView ivEntityCardImage;//实体卡图像

    @ViewInject(R.id.panelAddress)
    private RelativeLayout panelAddress;

    @ViewInject(R.id.lineAddress)
    private View lineAddress;

    @ViewInject(R.id.llEmailMoney)
    private LinearLayout llEmailMoney;

    private static final int REQ_GET_ADDRESS = 0x001;

    private GoodsAddressBean addressBean;

    private GiftcardLimitAdapter limitAdapter;

    private GiftcardSelectLimitAdapter selectLimitAdapter;

    private PostFeeBean postFeeBean;

    private AddressListPresenter addressListPresenter; //收货地址；

    private BuyCardPresenter buyCardPresenter;

    private GetPostFeePresenter postFeePresenter;

    private UpdatePayDetailModel recordModel;

    private OrderOutBean outBean;

    private PayRequestLogic payHandle;

    private String payType = "4";

    private String payDetailId;

    private ECardBean eCardBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        recordModel = new UpdatePayDetailModel();
        addressListPresenter = new AddressListPresenter(this);
        buyCardPresenter = new BuyCardPresenter(this);
        postFeePresenter = new GetPostFeePresenter(this);
        initView();
        getDefaultAddress();
        initData();
    }

    /**
     * 发送获取实体卡信息
     */
    private void initData() {

        eCardBean = (ECardBean) getIntent().getSerializableExtra("ECardBean");
        ImageOptions imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(4), ImageView.ScaleType.FIT_XY);
        x.image().bind(ivEntityCardImage, ServiceDispatcher.getImageUrl(eCardBean.getImageCode()), imageOptions);

        tvGiftName.setText(eCardBean.getBlessWord());

    }

    private void initView() {
        setTitleNmae("购买实体卡");

        findViewById(R.id.ivPlusAmount).setOnClickListener(this);
        findViewById(R.id.panelSelectAmount).setOnClickListener(this);
        findViewById(R.id.ivAddAddress).setOnClickListener(this);
        findViewById(R.id.tvPay).setOnClickListener(this);

        limitAdapter = new GiftcardLimitAdapter(this, getLimitData());
        limitAdapter.setHandler(dataHandler);
        amoutGv.setAdapter(limitAdapter);

        selectLimitAdapter = new GiftcardSelectLimitAdapter(this);
        selectLimitAdapter.setHandler(dataHandler);
        lv.setAdapter(selectLimitAdapter);
    }

    /**
     * 获取面额数据；
     */
    private List<GiftcardLimitBean> getLimitData() {
        List<GiftcardLimitBean> dataList = new ArrayList<>();
        int[] limits = getResources().getIntArray(R.array.giftcard_limit_array);
        for (int i = 0; i < limits.length; i++) {
            GiftcardLimitBean bean = new GiftcardLimitBean();
            bean.setLimit(limits[i]);
            dataList.add(bean);
        }
        return dataList;
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BuySucesWithOkDialog.WHAT_OK:
                    finish();
                    break;

                case GiftcardLimitAdapter.WHAT_LIMIT_ITEM: //礼品卡面额item点击；
                    GiftcardLimitBean limitBean = limitAdapter.getItem(msg.arg1);
                    try {
                        if (!selectLimitAdapter.hasEqualLimit(limitBean)) {
                            //克隆对象；
                            GiftcardLimitBean cloneLimitBean = limitBean.clone();
                            cloneLimitBean.setSheetNum(1);
                            selectLimitAdapter.add(cloneLimitBean);
                            calcuTotalAmount();
                        }
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    break;
                case GiftcardSelectLimitAdapter.WHAT_AMOUNT_CHANGE: //总金额变动；
                    calcuTotalAmount();
                    break;
                case GiftcardSelectLimitAdapter.WHAT_AMOUNT_ZERO: //面额数量归0；
                    calcuTotalAmount();
                    limitAdapter.unSelectItemByAmount(msg.arg1);
                    break;
            }
            return false;
        }
    });


    /**
     * 购买成功；
     */
    private void showBuySucesdDialog() {
        BuySucesWithOkDialog.getInstance().setDataHandler(dataHandler).show(getSupportFragmentManager(), "");
        updatePayDetail();
    }

    /**
     * 更新付款详情；
     */
    private void updatePayDetail() {
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("account", getUserBean().getAccount());
        argsMap.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        argsMap.put("payType", payType);
        argsMap.put("payDetailId", payDetailId);
        argsMap.put("orderId", outBean.getOrderId());
        argsMap.put("orderTime", outBean.getOrderTime());

        String amount = tvTotalAmount.getText().toString().trim();
        amount = amount.substring(1, amount.length());
        argsMap.put("orderAmount", amount); //订单金额；
        argsMap.put("activityName", "实体卡"); //活动名称；
        argsMap.put("orderType", OrderBean.ORDER_TYPE_LIPING); //订单类型；
        recordModel.loadData(true, argsMap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panelSelectAmount: //匿名；
                ivCryptonym.setSelected(!ivCryptonym.isSelected());
                break;

            case R.id.ivPlusAmount: //添加金额；
                addAmount();
                break;
            case R.id.tvPay: //立即激活；
                if (isInputRight()) {
                    exeCreateOrder();
                }
                break;
            case R.id.ivAddAddress: //收获地址；
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);
                startActivityForResult(intent, REQ_GET_ADDRESS);
                break;
        }
    }

    /**
     * 计算总金额；
     */
    private void calcuTotalAmount() {
        float totalAmount = selectLimitAdapter.getTotalAmount();
        if (postFeeBean != null) {
            totalAmount += postFeeBean.getFee();
        }

        SpannableString ss = new SpannableString("¥" + String.format("%.2f", totalAmount));
        ss.setSpan(new AbsoluteSizeSpan(15, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTotalAmount.setText(ss);
    }

    /**
     * 添加自定义礼品卡金额；
     * 500<=面值<=90000
     */
    private void addAmount() {
        String amount = etInputAmount.getText().toString().trim();
        if (TextUtils.isEmpty(amount)) {
            toastCustom("金额不能为空");
            return;
        }
        int amountInt = Integer.parseInt(amount);
        if (amountInt < 500 || amountInt > 9000) {
            toastCustom("面额只能在500和9000之间");
            return;
        }
        if (!selectLimitAdapter.hasEqualLimit(amountInt)) {
            GiftcardLimitBean limitBean = new GiftcardLimitBean();
            limitBean.setLimit(amountInt);
            limitBean.setSheetNum(1);
            limitBean.setIsUserDefined(true);
            selectLimitAdapter.add(limitBean);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1003) {
            addressBean = null;
            postFeeBean = null;
            panelAddress.setVisibility(View.GONE);

            lineAddress.setVisibility(View.GONE);
            llEmailMoney.setVisibility(View.GONE);
            getDefaultAddress();
            return;
        }
        if (data == null) {
            return;
        }
        switch (requestCode) {

            case REQ_GET_ADDRESS:  //选择收货地址
                Serializable addressObj = data.getSerializableExtra("addressBeanTemp");
                if (addressObj != null) {
                    addressBean = (GoodsAddressBean) addressObj;
                    initAddressView();
                    loadPostageFee(addressBean.getCityId());

                    panelAddress.setVisibility(View.VISIBLE);

                    lineAddress.setVisibility(View.VISIBLE);
                } else {

                    panelAddress.setVisibility(View.GONE);

                    lineAddress.setVisibility(View.GONE);
                }
                break;
            case REQ_ADD_BANK_CARD:
                if (payHandle != null) {
                    payHandle.showAddCardSuccess();
                }
                break;
        }
    }

    /**
     * 提交订单；
     */
    private void exeCreateOrder() {
        showPDialog(R.string.setting_committing);

        String amount = tvTotalAmount.getText().toString().trim();
        String totalAmount = amount.substring(1, amount.length());
        //配送费用
        buyCardPresenter.setEntityCardInterfaceParameters(String.valueOf(eCardBean.getProductId()), selectLimitAdapter.getQuantitys(), selectLimitAdapter.getAmounts(), ivCryptonym.isSelected(), false, eCardBean.getBlessWord(), JSON.toJSONString(getExpress()), totalAmount);
    }

    /**
     * 获取配送信息
     */
    private Map<String, String> getExpress() {
        Map<String, String> express = new HashMap<>();
        express.put("contacts", addressBean.getContacts());
        express.put("phone", addressBean.getPhone());
        express.put("districtId", addressBean.getDistrictId());
        express.put("address", addressAddress.getText().toString().trim());
        express.put("fee", String.format("%.2f", postFeeBean.getFee()) + "");//邮费
        return express;
    }

    private boolean isInputRight() {
        if (eCardBean == null) {

            toastCustom("暂无实体可供购买");

            return false;
        }

        if (selectLimitAdapter.getData().size() == 0) {
            toastCustom("请选择或自定义礼品卡面额");
            return false;
        }
        if (addressBean == null) {
            toastCustom("请选择送货地址");
            return false;
        }
        if (postFeeBean == null) {
            toastCustom("请选择邮费");
            return false;
        }

        if (!isLogin()) {
            readyGo(this, LoginActivity.class);
            return false;
        }

        return true;
    }

    /**
     * 获取默认收获地址；
     */
    private void getDefaultAddress() {
        Map<String, Object> param = new HashMap<>();
        addressListPresenter.loadData(true, param);
    }

    @Override
    public void onLoadAddressListSucess(boolean event_flag, Object data) {
        GoodsAddressBean addressBean = addressListPresenter.getDefaultAddress(data);

        if (addressBean != null) {

            this.addressBean = addressBean;

            panelAddress.setVisibility(View.VISIBLE);

            lineAddress.setVisibility(View.VISIBLE);

            initAddressView();

            loadPostageFee(addressBean.getCityId());


        }


    }

    /**
     * 初始化收获地址View内容；
     */
    private void initAddressView() {
        if (addressBean != null) {
            addressName.setText(addressBean.contacts);
            addressMobile.setText(addressBean.phone);

            StringBuilder sb = new StringBuilder();
            sb.append(isEmpty(addressBean.getProvinceName()) ? "" : addressBean.getProvinceName());
            sb.append(isEmpty(addressBean.getCityName()) ? "" : addressBean.getCityName());
            sb.append(isEmpty(addressBean.getDistrictName()) ? "" : addressBean.getDistrictName());
            sb.append(isEmpty(addressBean.getAddress()) ? "" : addressBean.getAddress());

            addressAddress.setText(sb.toString());
        }
    }

    @Override
    public void onLoadAddressListFail(String failMsg) {
        LogUtil.i("获取收获地址失败-failMsg=" + failMsg);
    }

    @Override
    public void onBuyMindECardSuc(OrderOutBean outBean) {
        this.outBean = outBean;
        closePDialog();
        startBuy();
    }

    /**
     * 付款；
     */
    private void startBuy() {
        payHandle = new PayRequestLogic(this);
        // 显示/隐藏 红包账户
        payHandle.showEnvelopPay(false);
        // 显示/隐藏 礼品卡账户
        payHandle.showGiftPay(true);
        // 显示/隐藏 现金账户
        payHandle.showBalancePay(false);
        // 显示/隐藏 信用卡 默认隐藏
        payHandle.showCreditCard(false);
        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener() {
            @Override
            public void callBack() {
                startActivityForResult(new Intent(BuyMindPhysCardActivity.this, AddBankCardActivity.class), REQ_ADD_BANK_CARD);
            }
        });
        payHandle.payMethodCallBack(new PayMethodListener() {
            @Override
            public void callBack(Map<String, String> map) {
                LogUtil.e("选择付款方式返回数据=" + JSON.toJSONString(map));
                payType = map.get("payType");
                payDetailId = map.get("payDetailId");
            }
        });
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE) {
                onPaySucess();
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {

                if (RESULT_CODE.equals(com.yzb.wallet.util.WalletConstant.PAY_TYPE_OFF)) {// 支付卡信息不全

                    String str = JSON.toJSONString(resultMap);

                    PayMethod accountInfo = JSON.parseObject(str, PayMethod.class);

                    int cardType = accountInfo.getCardType();

                    Class claz = null;

                    if (cardType == 1) {// 储蓄卡

                        claz = AddBankCardActivity.class;

                    } else if (cardType == 2) {//信用卡

                        claz = AddCanPayCardActivity.class;

                    }
                    Intent intent = new Intent(BuyMindPhysCardActivity.this, claz);
                    intent.putExtra("cardNo", accountInfo.getCardNo());
                    intent.putExtra("name", accountInfo.getName());
                    startActivity(intent);

                } else {

                    onPaySucess();

                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG) {
                LogUtil.i("付款失败；RESULT_CODE=" + RESULT_CODE + ",ERROR_MSG=" + ERROR_MSG);
                onPayFail(ERROR_MSG);
            }
        });
        payHandle.pay(getInputMap(), false);
    }

    private Map<String, String> getInputMap() {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", getUserBean().getAmountAccount());
        params.put("orderNo", outBean.getOrderNo());
        params.put("orderTime", DateUtil.formatOrderTime(outBean.getOrderTime()));


        String amount = tvTotalAmount.getText().toString().trim();
        amount = amount.substring(1, amount.length());
        params.put("amount", amount); //订单金额；

        params.put("leftTime", AppConstant.leftTime); //超时时间
        params.put("goodsName", "实体卡"); //商品名称
        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        params.put("notifyUrl", outBean.getNotifyUrl());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号
        params.put("sign", AppConstant.sign);//签名
        return params;
    }

    @Override
    public void onBuyMindECardFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 获取邮费信息；
     *
     * @cityId 目的地的cityId；
     */
    private void loadPostageFee(String cityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", cityId);
        params.put("pageStart", "0");
        params.put("pageSize", "1");
        postFeePresenter.loadData(params);
    }

    @Override
    public void onGetPostFeeSucess(Object data) {
        if (data != null) {
            List<PostFeeBean> postFeeBeans = (List<PostFeeBean>) data;
            if (postFeeBeans != null && postFeeBeans.size() > 0) {
                this.postFeeBean = postFeeBeans.get(0);
                tvPostFee.setText(Utils.subZeroAndDot(postFeeBean.getFee() + "") + "元");

                llEmailMoney.setVisibility(View.VISIBLE);
                calcuTotalAmount();
            }
        }
    }

    @Override
    public void onGetPostFeeFail(String failMsg) {
        llEmailMoney.setVisibility(View.GONE);
    }

    public void onPaySucess() {
        showBuySucesdDialog();
    }

    public void onPayFail(String failMsg) {
        toastCustom(failMsg);
    }

}
