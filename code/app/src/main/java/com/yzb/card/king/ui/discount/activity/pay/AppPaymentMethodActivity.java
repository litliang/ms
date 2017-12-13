package com.yzb.card.king.ui.discount.activity.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.common.ActivityCouponParam;
import com.yzb.card.king.bean.common.ActivityDeductionStyleParam;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.LifeStageDetailBean;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.bean.common.PayOrderBean;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.bean.ticket.OrderIdBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.base.BasePresenter;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.activity.PayMethodActivity;
import com.yzb.card.king.ui.credit.adapter.AppBankPaymentAdapter;
import com.yzb.card.king.ui.hotel.persenter.GetCouponPersenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.ui.my.bean.WalletHomeInfo;
import com.yzb.card.king.ui.my.presenter.SelectCardPresenter;
import com.yzb.card.king.ui.ticket.presenter.RePayPresenter;
import com.yzb.card.king.ui.travel.presenter.impl.FlightBookPImpl;
import com.yzb.card.king.ui.travel.view.FlightBookView;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：付款方式
 * 作  者：Li Yubing
 * 日  期：2017/8/11
 * 描  述：
 */
@ContentView(R.layout.activity_app_payment_method)
public class AppPaymentMethodActivity extends BaseActivity implements View.OnClickListener, DataCallBack, BaseViewLayerInterface, FlightBookView {

    private static final int REQ_ADD_BANK_CARD = 0x101;

    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;

    private SelectCardPresenter selectCardPresenter;

    private AppBankPaymentAdapter mAdapter;

    private PaymethodAndBankPreStageBean selectPayMethod;

    private String goodName;

    private PayOrderBean bean;

    //实际支付金额（特惠付款扣除优惠数据后得到的实付金额）
    private double paymentMoney;

    private double amountActuallyMoney = 0;

    private GoldTicketParam goldTicketParam;

    private ActivityCouponParam activityCouponParam;

    private AccountInfoBean walletHomeInfo;

    private GetCouponPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        GlobalApp.getInstance().setPublicActivity(this);

        selectCardPresenter = new SelectCardPresenter(this);

        persenter = new GetCouponPersenter(this);

        initData();

        initView();

    }

    private void initData()
    {

        if (getIntent().hasExtra("goodName") && getIntent().hasExtra("orderData") && getIntent().hasExtra("goldTicketParam") && getIntent().hasExtra("activityCouponParam")) {

            goodName = getIntent().getStringExtra("goodName");

            bean = (PayOrderBean) getIntent().getSerializableExtra("orderData");

            paymentMoney = getIntent().getDoubleExtra("paymentMoney", 0);

            tv_commit.setText("需支付" + Utils.subZeroAndDot(paymentMoney + "") + "元");

            goldTicketParam = (GoldTicketParam) getIntent().getSerializableExtra("goldTicketParam");

            activityCouponParam = (ActivityCouponParam) getIntent().getSerializableExtra("activityCouponParam");

            amountActuallyMoney = paymentMoney;

        }

    }


    private void initView()
    {
        setTitleNmae("付款方式");

        findViewById(R.id.tv_commit).setOnClickListener(this);
        //付款方式
        RecyclerView wvLvData = (RecyclerView) findViewById(R.id.wvBankLvData);

        mAdapter = new AppBankPaymentAdapter(this, adapterDataCallBack);

        wvLvData.setLayoutManager(new LinearLayoutManager(this));

        wvLvData.setAdapter(mAdapter);

        wvLvData.post(new Runnable() {
            @Override
            public void run()
            {
                /**
                 */
                showPDialog("正在请求数据……");

                loadWalletInfo();
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        loadWalletInfo();

    }

    /**
     * 查询用户账户余额
     */
    private void loadWalletInfo()
    {
        SimpleRequest<AccountInfoBean> request = new SimpleRequest<AccountInfoBean>(CardConstant.SelectAccountInfo) {
            @Override
            protected AccountInfoBean parseData(String data)
            {
                return JSON.parseObject(data, AccountInfoBean.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("amountAccount", UserManager.getInstance().getUserBean().getAmountAccount());
        param.put("balanceStatus", "1");
        param.put("preProfitStatus", "1");
        param.put("annualRateStaus", "1");
        param.put("totalProfitStatus", "1");
        param.put("personBonusStatus", "1"); //查询个人红包余额
        param.put("giftcardStatus", "1"); //查询礼品卡余额
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<AccountInfoBean>() {
            @Override
            public void onSuccess(AccountInfoBean data)
            {

                walletHomeInfo = data;
                selectCardPresenter.sendUserPaymethodRequest(-1, 1, 0, bean.getOrderId(), goldTicketParam, paymentMoney);
            }

            @Override
            public void onFail(Error error)
            {
                selectCardPresenter.sendUserPaymethodRequest(-1, 1, 0, bean.getOrderId(), goldTicketParam, paymentMoney);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        UserManager.getInstance().setFlightType(null);
        UserManager.getInstance().setOrderIdBeans(null);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.tv_commit://付款方式

                if (selectPayMethod != null) {

                    if ("9999".equals(selectPayMethod.getAccountType())){

                        ToastUtil.i(AppPaymentMethodActivity.this,"暂不支持微信付款");

                        return;

                    }

                    if ("9998".equals(selectPayMethod.getAccountType())){

                        ToastUtil.i(AppPaymentMethodActivity.this,"暂不支持信用支付");

                        return;

                    }

                    if ("9997".equals(selectPayMethod.getAccountType())){

                        ToastUtil.i(AppPaymentMethodActivity.this,"暂不支持支付宝支付");

                        return;

                    }

                    //检测是否存在分期付款方式
                    LifeStageDetailBean.StageBean stageBean = selectPayMethod.getSelectedBean();

                    if (stageBean == null) {

                        exePay();

                    } else {
                        sendDeductionActionRequest();
                    }

                } else {

                    ToastUtil.i(this, "请选择个付款方式");
                }

                break;
            default:
                break;
        }
    }

    private static final int REQ_CODE_ADD_BANK = 0x001; // 添加银行卡；
    /**
     * 特惠付款
     * 说明： 除现金账户和银行卡快捷支付，此接口不允许其他方式付款
     */
    private void exePay()
    {
        PayRequestLogic payHandle = new PayRequestLogic(this);
        // 显示/隐藏 红包账户
        payHandle.showEnvelopPay(true);
        // 显示/隐藏 礼品卡账户
        payHandle.showGiftPay(true);
        // 显示/隐藏 现金账户
        payHandle.showBalancePay(true);
        // 显示/隐藏 信用卡 默认隐藏
        payHandle.showCreditCard(true);

        payHandle.payMethodCallBack(new PayMethodListener() {
            @Override
            public void callBack(Map<String, String> map)
            {

            }
        });
        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener() {
            @Override
            public void callBack()
            {

                Intent intent = new Intent(AppPaymentMethodActivity.this, AddCardAllActivity.class);
                intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.ALL_BUSINESS_VALUE);
                startActivityForResult(intent, REQ_CODE_ADD_BANK);
            }
        });

        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                sendDeductionActionRequest();
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {

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
                    Intent intent = new Intent(AppPaymentMethodActivity.this, claz);
                    intent.putExtra("cardNo", accountInfo.getCardNo());
                    intent.putExtra("name", accountInfo.getName());
                    startActivity(intent);

                } else {

                    sendDeductionActionRequest();

                }

            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                onPayFail(ERROR_MSG);
            }
        });
        payHandle.pay(getInputMap(), false);
    }


    /**
     * 发送抵扣优惠信息请求
     */
    private void sendDeductionActionRequest()
    {

        showPDialog("正在请求数据……");

        //机票改签
        ArrayList<OrderIdBean.OrderIds> orderIdsArrayList = UserManager.getInstance().getOrderIdBeans();

        if (orderIdsArrayList != null && orderIdsArrayList.size() > 0) {

            RePayPresenter repayPresenter = new RePayPresenter();

            Map<String, Object> argMap = new HashMap<>();
            argMap.put("orderIdList", JSON.toJSONString(orderIdsArrayList));
            argMap.put("orderIds", bean.getOrderId() + "");  //大订单id；
            repayPresenter.loadData(true, argMap);

        }

        //机票购票
        String flightType = UserManager.getInstance().getFlightType();

        if (flightType != null) {

            BasePresenter flightBookPresenter = new FlightBookPImpl(this);

            Map<String, Object> argMap = new HashMap<>();
            argMap.put("orderId", bean.getOrderId() + "");
            argMap.put("flightType", flightType); // 航班类型（单程：OW；往返：RT；多段：MT）
            // argMap.put("targetActivity", "");  //消息推送需求不确定，待定中......
            flightBookPresenter.loadData(true, argMap);
        }


        ActivityDeductionStyleParam activityDeductionStyleParam = new ActivityDeductionStyleParam();
        /**
         * 检测选择支付方式是否选择了分期付款方式
         */
        if (selectPayMethod.getSelectedBean() != null) {

            activityDeductionStyleParam.setPayType("4");
            //银行分期id
            activityDeductionStyleParam.setBankStageId(selectPayMethod.getSelectedBean().getActId());

        } else {

            String accountType = selectPayMethod.getAccountType();
             //
            if ("1".equals(accountType)) {//钱包支付

                activityDeductionStyleParam.setPayType("1");

            } else {//储蓄卡和借记卡

                String cardType = selectPayMethod.getCardType();

                if ("1".equals(cardType)) {//储蓄卡

                    activityDeductionStyleParam.setPayType("3");

                } else if ("2".equals(cardType)) {//信用卡

                    activityDeductionStyleParam.setPayType("2");
                }
            }

            activityDeductionStyleParam.setPayDetailId(selectPayMethod.getBankId() + "");

            BankActivityInfoBean infoBean = selectPayMethod.getBankActInfo();

            //银行优惠id
            if (infoBean != null) {

                activityDeductionStyleParam.setBankActId(Long.parseLong(infoBean.getActId()));
            }

        }

        /**
         *  检查是否有抵销优惠信息,如果有则发送吊销请求，主要检测优惠券、红包、礼品卡、银行支付
         */
        long coupinId = activityCouponParam.getCouponId();

        long couponItemId = activityCouponParam.getCouponItemId();

        if (coupinId != -1 && couponItemId != -1 && activityCouponParam.getCouponAmount() != 0) {

            sendActivityDedutionRequest(activityDeductionStyleParam);

        }  else if (activityDeductionStyleParam.getPayDetailId() != null && activityDeductionStyleParam.getPayDetailId().length() > 0) {

            sendActivityDedutionRequest(activityDeductionStyleParam);

        } else {
            onPaySucess();
        }

    }

    private void sendActivityDedutionRequest(ActivityDeductionStyleParam activityDeductionStyleParam)
    {
        persenter.sendActivityDeductionAmountRequest(amountActuallyMoney, activityCouponParam, activityDeductionStyleParam);
    }

    private void onPaySucess()
    {
        if (goldTicketParam == null) {

            return;
        }

        //支付成功
        Intent succeedIntent = new Intent(AppPaymentMethodActivity.this, AppPaySucceedActivity.class);

        long orderId = bean.getOrderId();

        succeedIntent.putExtra("orderId", orderId);

        succeedIntent.putExtra("payMoney", amountActuallyMoney);

        succeedIntent.putExtra("goldTicketParam", goldTicketParam);

        startActivity(succeedIntent);

        setResult(7777);

        finish();
    }


    private Map<String, String> getInputMap()
    {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", getUserBean().getAmountAccount());
        params.put("accountType", selectPayMethod.getAccountType());

        params.put("payMethodName", !isEmpty(selectPayMethod.getBankName()) ? selectPayMethod.getBankName() :
                selectPayMethod.getTypeName());//支付方式名称

        params.put("sortCode", selectPayMethod.getSortCode());//银行卡关联码（快捷支付） N
        params.put("orderNo", bean.getOrderNo());
        params.put("orderTime", bean.getOrderTime());
        params.put("leftTime", AppConstant.leftTime); //超时时间
        params.put("goodsName", goodName); //商品名称

        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP

        params.put("notifyUrl", bean.getNotifyUrl());

        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号

        params.put("sign", AppConstant.sign);//签名

        params.put("amount", String.format("%.2f", amountActuallyMoney)); //订单金额；  必须要是0.00格式的数据；

        if (activityCouponParam.getBonusAmount() == 0d) {

            params.put("amountEnvelop", ""); //个人红包金额

        } else {

            params.put("amountEnvelop", String.format("%.2f", activityCouponParam.getBonusAmount())); //个人红包金额

        }

        params.put("amountEnvelopPt", ""); //平台红包金额

        if (activityCouponParam.getGiftcardAmount() == 0d) {

            params.put("amountGift", ""); //礼品卡

        } else {

            params.put("amountGift", String.format("%.2f", activityCouponParam.getGiftcardAmount())); //礼品卡

        }

        params.put("amountIntegral", ""); //平台积分金额

        LogUtil.e("Pay","params===>"+params.toString());

        return params;
    }

    public void onPayFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
        //嗨支付账户
        List<PaymethodAndBankPreStageBean> payMethodList = JSON.parseArray(o + "", PaymethodAndBankPreStageBean.class);

        payMethodList(payMethodList);
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        payMethodList(null);

    }
    /*
       银行付款方式数据
     */
    private List<PaymethodAndBankPreStageBean>  bankPaymentodBeanList =new ArrayList<>();

    /*
       所有付款方式数据
    */
    private List<PaymethodAndBankPreStageBean>  allPaymentBeanList = new ArrayList<>();

    private int allPaymentCount  = 0;

    /**
     * 付款方式数据
     * @param bankPaymentDataList
     */
    private void payMethodList(List<PaymethodAndBankPreStageBean> bankPaymentDataList)
    {

        bankPaymentodBeanList.clear();

        allPaymentBeanList.clear();

        if (bankPaymentDataList == null) {


        } else {

            for (PaymethodAndBankPreStageBean bean : bankPaymentDataList) {

                bean.setPaymentMoney(paymentMoney);

            }

            bankPaymentodBeanList.addAll(bankPaymentDataList);
        }



        /**
         * 添加新卡支付
         */
        PaymethodAndBankPreStageBean addPayMethod = new PaymethodAndBankPreStageBean();

        addPayMethod.setTypeName("添加银行卡");

        addPayMethod.setAccountType("10000");

        addPayMethod.setPayMsg("信用卡");

        addPayMethod.setAccountLogo(R.mipmap.icon_add_new_bank);

        bankPaymentodBeanList.add(addPayMethod);

        allPaymentBeanList.addAll(bankPaymentodBeanList);

        /**
         * 其它支付方式数据
         */
        //添加钱包账户
        if (walletHomeInfo != null) {

            PaymethodAndBankPreStageBean userAccountPaymethod = new PaymethodAndBankPreStageBean();
            userAccountPaymethod.setTypeName("Hi钱包支付");
            userAccountPaymethod.setAccountType("1");
            userAccountPaymethod.setPayMsg("余额 ¥" + Utils.subZeroAndDot(walletHomeInfo.getBalance()+""));
            userAccountPaymethod.setAccountLogo(R.mipmap.icon_user_account);
            allPaymentBeanList.add(userAccountPaymethod);

            PaymethodAndBankPreStageBean giftAccountPaymethod = new PaymethodAndBankPreStageBean();
            giftAccountPaymethod.setTypeName("礼品卡支付");
            giftAccountPaymethod.setAccountType("4");
            giftAccountPaymethod.setPayMsg("余额 ¥" + Utils.subZeroAndDot(walletHomeInfo.getGiftcardBalance()+""));
            giftAccountPaymethod.setAccountLogo(R.mipmap.icon_gift_yellow_money);
            allPaymentBeanList.add(giftAccountPaymethod);


            PaymethodAndBankPreStageBean redPackageAccountPaymethod = new PaymethodAndBankPreStageBean();
            redPackageAccountPaymethod.setTypeName("红包支付");
            redPackageAccountPaymethod.setAccountType("5");
            redPackageAccountPaymethod.setPayMsg("余额 ¥" + Utils.subZeroAndDot(walletHomeInfo.getPersonBonusBalance()+""));
            redPackageAccountPaymethod.setAccountLogo(R.mipmap.icon_ref_package_moeny);
            allPaymentBeanList.add(redPackageAccountPaymethod);

        }

        //其它支付账户
        //添加支付保支付方式
        PaymethodAndBankPreStageBean useCreditPayMethod = new PaymethodAndBankPreStageBean();
        useCreditPayMethod.setTypeName("信用支付");
        useCreditPayMethod.setAccountType("9998");
        useCreditPayMethod.setPayMsg("信用安全支付");
        useCreditPayMethod.setAccountLogo(R.mipmap.icon_xinyong_pay);
        allPaymentBeanList.add(useCreditPayMethod);

        PaymethodAndBankPreStageBean weixinzhifuPayMethod = new PaymethodAndBankPreStageBean();
        weixinzhifuPayMethod.setTypeName("微信支付");
        weixinzhifuPayMethod.setAccountType("9999");
        weixinzhifuPayMethod.setPayMsg("微信安全支付");
        weixinzhifuPayMethod.setAccountLogo(R.mipmap.icon_weixinchat_pay);
        allPaymentBeanList.add(weixinzhifuPayMethod);

        PaymethodAndBankPreStageBean zhifubaoPayMethod = new PaymethodAndBankPreStageBean();
        zhifubaoPayMethod.setTypeName("支付宝支付");
        zhifubaoPayMethod.setAccountType("9997");
        zhifubaoPayMethod.setPayMsg("支付宝安全支付");
        zhifubaoPayMethod.setAccountLogo(R.mipmap.icon_qqweixinchat_pay);
        allPaymentBeanList.add(zhifubaoPayMethod);

        mAdapter.clear();

        mAdapter.addAll(bankPaymentodBeanList);

        allPaymentCount = allPaymentBeanList.size();

    }

    private AppBankPaymentAdapter.AdapterDataCallBack adapterDataCallBack = new AppBankPaymentAdapter.AdapterDataCallBack() {
        @Override
        public void selectedPayMethod(PaymethodAndBankPreStageBean payMethod)
        {

            if ("10000".equals(payMethod.getAccountType())) {//添加信用卡

                Intent intent = new Intent(AppPaymentMethodActivity.this, AddCardAllActivity.class);
                intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.ALL_BUSINESS_VALUE);
                startActivityForResult(intent, REQ_CODE_ADD_BANK);

            } else {

                selectPayMethod = payMethod;

                LogUtil.e("AAAA="+payMethod.getAccountType());
                /**
                 * 计算出选择支付方式的满额减、随机立减
                 */
                BankActivityInfoBean infoBean = payMethod.getBankActInfo();

                double favMoney = 0;

                if (infoBean != null) {

                  if( payMethod.ifSelectedBankStage()){ //信用卡支付时，银行优惠和分期活动同时存在，检测是否选择了银行的分期活动

                      favMoney = 0;

                  }else {

                      int actCls = infoBean.getActCls();

                      if (actCls == 1) {//满减

                          if (payMethod.getPaymentMoney() >= infoBean.getFuuAmount()) {

                              favMoney = infoBean.getDisContent();
                          }

                      } else if (actCls == 2) {//折扣

                          double discountRate = Double.valueOf("0." + infoBean.getDisContent());

                          favMoney = discountRate * payMethod.getPaymentMoney();

                      } else if (actCls == 3) {//随机立减

                          favMoney = 0;
                      }
                  }



                } else {

                    favMoney = 0;
                }

                amountActuallyMoney = paymentMoney - favMoney;

                LifeStageDetailBean.StageBean stageBean = selectPayMethod.getSelectedBean();

                if (stageBean == null) {

                    tv_commit.setText("需支付" + Utils.subZeroAndDot(amountActuallyMoney + "") + "元");

                } else {

                    tv_commit.setText("生活分期支付");
                }

                tv_commit.setBackgroundResource(R.color.color_980100);
            }

        }

        @Override
        public void hideOrShowOtherPayData(boolean ifShow) {

          int currentAdapterCount =  mAdapter.getItemCount()-1;


            if(currentAdapterCount == allPaymentCount){//  展示银行付款方式

                mAdapter.clear();

                mAdapter.addAll(bankPaymentodBeanList);

            }else {//展示所有的付款方式

                mAdapter.clear();

                mAdapter.addAll(allPaymentBeanList);

            }

        }
    };

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();
        onPaySucess();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        dimissPdialog();

        onPaySucess();
    }

    @Override
    public void onFlightBookSucess()
    {
        LogUtil.i("成功");
    }

    @Override
    public void onFlightBookFail(String failMsg)
    {
        LogUtil.i(failMsg);
    }
}

