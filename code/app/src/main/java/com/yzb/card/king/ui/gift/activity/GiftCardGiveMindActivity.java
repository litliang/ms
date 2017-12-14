package com.yzb.card.king.ui.gift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.gift.fragment.SendGiftCardDialog;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.model.UpdatePayDetailModel;
import com.yzb.card.king.ui.gift.dialog.GiftcardBuySucesDialog;
import com.yzb.card.king.ui.gift.presenter.BuyCardPresenter;
import com.yzb.card.king.ui.my.presenter.AccountInfoPresenter;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.gift.view.BuyCardView;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能：礼品卡--送心意；
 *
 * @author:gengqiyun
 * @date: 2016/12/14
 */
@ContentView(R.layout.activity_giftcard_give_mind)
public class GiftCardGiveMindActivity extends BaseActivity implements View.OnClickListener, BuyCardView, CommandView, BaseViewLayerInterface {
    private static final int REQ_ADD_BANK_CARD = 0x001;
    public static final int WHAT_CLOSE = 0x007;

    @ViewInject(R.id.giftcardSumAmount)
    private TextView giftcardSumAmount;

    @ViewInject(R.id.ivThumb)
    private ImageView ivThumb;

    @ViewInject(R.id.etCardMsg)
    private EditText etCardMsg;

    @ViewInject(R.id.ivCryptonym)
    private ImageView ivCryptonym;

    @ViewInject(R.id.etSumAmount)
    private EditText etSumAmount;

    @ViewInject(R.id.ivProtocalSelect)
    private ImageView ivProtocalSelect;

    @ViewInject(R.id.tvTotalAmount)
    private TextView tvTotalAmount;

    private BuyCardPresenter buyCardPresenter;
    private String productId; //产品id；
    private String blessWord; //祝福语；
    private String imageCode; //图片；
    private CommandPresenter commandPresenter;
    private UpdatePayDetailModel recordModel;
    private OrderOutBean outBean;
    private PayRequestLogic payHandle;
    private String payType = "1";
    private String payDetailId;

    private AccountInfoPresenter accountInfoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        buyCardPresenter = new BuyCardPresenter(this);
        recordModel = new UpdatePayDetailModel();
        accountInfoP = new AccountInfoPresenter(this);
        commandPresenter = new CommandPresenter(this);
        recvIntentData();
        initView();
        initData();
    }

    private void recvIntentData() {
        productId = getIntent().getStringExtra("productId");
        blessWord = getIntent().getStringExtra("blessWord");
        imageCode = getIntent().getStringExtra("imageCode");
    }

    private void initView() {
        if (getIntent().hasExtra("titleName")) {
            setTitleNmae(getIntent().getStringExtra("titleName"));
        } else {
            setTitleNmae("送礼品e卡");
        }

        updateTotalAmount(0);
        etCardMsg.setEnabled(false);
        ivProtocalSelect.setSelected(true);

        findViewById(R.id.ivEdit).setOnClickListener(this);
        findViewById(R.id.panelCryptonym).setOnClickListener(this);
        findViewById(R.id.tvBuy).setOnClickListener(this);
        findViewById(R.id.panelRecvProtocal).setOnClickListener(this);
        findViewById(R.id.tvServiceProtocal).setOnClickListener(this);

        etSumAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    tvTotalAmount.setText("0.00");
                } else if(s.toString().endsWith(".")){
                    tvTotalAmount.setText(s.toString().replaceAll(".",""));
                } else {

                    Double amountTxt = Double.parseDouble(s.toString());
                    tvTotalAmount.setText("¥" + new BigDecimal(amountTxt).setScale(2,BigDecimal.ROUND_UP).doubleValue());
                    ;
                }
            }
        });

        findViewById(R.id.panelScanDetail).

                setOnClickListener(this);
    }


    private void initData() {
        x.image().bind(ivThumb, ServiceDispatcher.getImageUrl(imageCode));
        etCardMsg.setText(blessWord);

        loadAccountInfo();
    }

    /**
     * 获取账户信息；
     */
    private void loadAccountInfo() {
        Map<String, Object> args = new HashMap<>();
        args.put("amountAccount", getUserBean().getAmountAccount());
        args.put("giftcardStatus", "1"); //查询礼品卡余额
        accountInfoP.loadData(args);
    }

    /**
     * 更新礼品卡总金额；
     */
    private void updateTotalAmount(float amount) {
        SpannableString ss = new SpannableString("¥" + String.format("%.2f", amount));
        //  ss.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(10)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        giftcardSumAmount.setText(ss);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEdit: //编辑；
                etCardMsg.setEnabled(true);
                break;

            case R.id.panelCryptonym:  //匿名；
                ivCryptonym.setSelected(!ivCryptonym.isSelected());
                break;
            case R.id.panelRecvProtocal:  //接受服务协议；
                ivProtocalSelect.setSelected(!ivProtocalSelect.isSelected());
                break;
            case R.id.tvServiceProtocal:  //服务协议；
                Bundle bundle = new Bundle();
                bundle.putString(WebViewClientActivity.CATEGORY, AppConstant.h5_buy_giftcard_service);
                bundle.putString(WebViewClientActivity.TITLE_NAME, "服务协议");
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
            case R.id.tvBuy:  //购买；
                if (isInputRight()) {
                    checkUserValid(new OnIDValid() {
                        @Override
                        public void onValid() {
                            exeBuy();
                        }
                    });
                }
                break;
            case R.id.panelScanDetail:  //查看明细；
                Intent intent = new Intent(this, GiftCardBillDetailActivity.class);
                intent.putExtra("billDetail", "礼品卡明细");
                startActivity(intent);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainEventThread(Message msg) {
        LogUtil.i("收到消息了");
        if (msg.what == WHAT_CLOSE) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAccountInfo();
    }

    /**
     * 购买心意卡；
     */
    private void exeBuy() {
        showPDialog(R.string.setting_committing);

        buyCardPresenter.setEcardOrderInterfaceParameters(productId, etSumAmount.getText().toString().trim(), ivCryptonym.isSelected(), etCardMsg.getText().toString().trim());
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GiftcardBuySucesDialog.WHAT_SEND: //立即发送；
                    generateCommand();
                    break;
                case GiftcardBuySucesDialog.WHAT_LOOK://立即查看；
                    Intent detailIntent = new Intent(GiftCardGiveMindActivity.this, GiftCardUseDetailActivity.class);
                    detailIntent.putExtra("orderId", outBean.getOrderId());
                    startActivity(detailIntent);
                    finish();
                    break;
                case SendGiftCardDialog.WHAT_SHARE_PLATFORM: //分享-嗨生活；
                    Intent intent = new Intent(GiftCardGiveMindActivity.this, GiveCardActivity.class);
                    intent.putExtra("recordIds", outBean.getOrderId());
                    intent.putExtra("pageType", GiveCardActivity.TYPE_GIFTCARD);
                    String numTxt = "1";
                    intent.putExtra("totalNum", Integer.parseInt(numTxt));
                    startActivity(intent);
                    finish();
                    break;
            }
            return false;
        }
    });

    /**
     * 生成口令；
     */
    private void generateCommand() {
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType", AppConstant.command_type_giftcard);
        args.put("code", outBean.getOrderId()); //编码
        commandPresenter.loadData(args);
    }

    /**
     * 购买成功；
     */
    private void showBuySucesdDialog() {
        updatePayDetail();
        GiftcardBuySucesDialog.getInstance().setDataHandler(dataHandler).show(getSupportFragmentManager(), "");
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
        argsMap.put("orderAmount", amount.substring(1, amount.length())); //订单金额；
        argsMap.put("activityName", "心意卡"); //活动名称；
        argsMap.put("orderType", OrderBean.ORDER_TYPE_LIPING); //订单类型；
        recordModel.loadData(true, argsMap);
    }

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
                startActivityForResult(new Intent(GiftCardGiveMindActivity.this, AddBankCardActivity.class), REQ_ADD_BANK_CARD);
            }
        });

        payHandle.payMethodCallBack(new PayMethodListener() {
            @Override
            public void callBack(Map<String, String> map) {
                LogUtil.i("选择付款方式返回数据=" + JSON.toJSONString(map));
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
                LogUtil.i("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);

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
                    Intent intent = new Intent(GiftCardGiveMindActivity.this, claz);
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
        params.put("mobile", UserManager.getInstance().getUserBean().getAmountAccount());
        params.put("orderNo", outBean.getOrderNo());
        params.put("orderTime", DateUtil.formatOrderTime(outBean.getOrderTime()));
        params.put("goodsName", "心意卡"); //商品名称

        String amount = tvTotalAmount.getText().toString().trim();
        params.put("amount", amount.substring(1, amount.length())); //订单金额；

        params.put("leftTime", AppConstant.leftTime); //超时时间
        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        params.put("notifyUrl", outBean.getNotifyUrl());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号
        params.put("sign", AppConstant.sign);//签名
        return params;
    }

    private boolean isInputRight() {
        if (isEmpty(etCardMsg.getText().toString().trim())) {
            toastCustom("请输入祝福语");
            return false;
        }
        if (isEmpty(etSumAmount.getText().toString().trim())) {
            toastCustom("请输入总金额");
            return false;
        }
        if (Float.parseFloat(etSumAmount.getText().toString().trim()) <= 0) {
            toastCustom("总金额须大于0");
            return false;
        }

        if (!ivProtocalSelect.isSelected()) {
            toastCustom("请同意服务协议");
            return false;
        }

        if (!isLogin()) {
            readyGo(this, LoginActivity.class);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case REQ_ADD_BANK_CARD:
                if (payHandle != null) {
                    payHandle.showAddCardSuccess();
                }
                break;
        }
    }

    @Override
    public void onBuyMindECardSuc(OrderOutBean outBean) {
        this.outBean = outBean;
        closePDialog();
        startBuy();
    }

    @Override
    public void onBuyMindECardFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    public void onPaySucess() {
        LogUtil.i("======特惠付款成功");
        showBuySucesdDialog();
    }

    public void onPayFail(String failMsg) {
        toastCustom(failMsg);
    }

    @Override
    public void onGetCommandSuc(String commandAndUrl) {
        closePDialog();
        SendGiftCardDialog.getInstance()
                .setHandler(dataHandler).setFragmentManager(getSupportFragmentManager()).setCommandAndUrl(
                CommonUtil.getGiftcardShareContent("心意卡", commandAndUrl)).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onGetCommandFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type) {
        AccountInfoBean accountInfoBean = (AccountInfoBean) o;

        updateTotalAmount(accountInfoBean.getGiftcardBalance());
    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        ToastUtil.i(this, "" + o);
    }
}
