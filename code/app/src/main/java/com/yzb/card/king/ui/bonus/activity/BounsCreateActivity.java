package com.yzb.card.king.ui.bonus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.bean.user.AuthenticationInfoBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.ui.bonus.bean.BounsThemeParam;
import com.yzb.card.king.ui.bonus.presenter.BonusOrderCreatePresenter;
import com.yzb.card.king.ui.bonus.presenter.BounsThemePresenter;
import com.yzb.card.king.ui.bonus.view.BounsThemeView;
import com.yzb.card.king.ui.credit.activity.AddBankCardActivity;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.gift.activity.GiveCardActivity;
import com.yzb.card.king.ui.gift.dialog.GiftcardBuySucesDialog;
import com.yzb.card.king.ui.gift.fragment.SendGiftCardDialog;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.fragment.CommandDialog;
import com.yzb.card.king.ui.my.model.UpdatePayDetailModel;
import com.yzb.card.king.ui.my.presenter.CommandPresenter;
import com.yzb.card.king.ui.my.view.CommandView;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：发红包；
 *
 * @author:gengqiyun
 * @date: 2016/12/22
 */
@ContentView(R.layout.activity_bouns_create)
public class BounsCreateActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface, CommandView, BounsThemeView
{
    private static final int REQ_GET_THEME = 0x001;
    private static final int REQ_ADD_BANK_CARD = 0x002;

    @ViewInject(R.id.ivRandom)
    private ImageView ivRandom;

    @ViewInject(R.id.ivEqual)
    private ImageView ivEqual;

    @ViewInject(R.id.tvAmountLabel)
    private TextView tvAmountLabel;

    @ViewInject(R.id.etPerAmount)
    private EditText etPerAmount;

    @ViewInject(R.id.etBounsNum)
    private EditText etBounsNum;

    @ViewInject(R.id.etBounsWishes)
    private EditText etBounsWishes;

    @ViewInject(R.id.tvBounsTheme)
    private TextView tvBounsTheme;

    @ViewInject(R.id.ivProtocalSelect)
    private ImageView ivProtocalSelect;

    @ViewInject(R.id.tvTotalAmount)
    private TextView tvTotalAmount;

    private BounsThemeBean themeBean;

    private BonusOrderCreatePresenter orderCreatePresenter;

    private BounsThemePresenter themePresenter;

    private CommandPresenter commandPresenter;

    private OrderOutBean outBean;

    private UpdatePayDetailModel recordModel;

    private PayRequestLogic payHandle;

    private String payType = "1";

    private String payDetailId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        recvIntentBean();
        recordModel = new UpdatePayDetailModel();
        orderCreatePresenter = new BonusOrderCreatePresenter(this);
        commandPresenter = new CommandPresenter(this);
        themePresenter = new BounsThemePresenter(this);
        initView();

        if (themeBean != null)
        {
            initThemeTv();
        } else
        {
            loadGeneralTheme();
        }

    }

    private void recvIntentBean()
    {
        Serializable recvThemeBean = getIntent().getSerializableExtra("themeBean");
        if (recvThemeBean != null)
        {
            this.themeBean = (BounsThemeBean) recvThemeBean;
        }
    }

    private void initView()
    {

        setTitleNmae( getString(R.string.my_red_envelope));

        etPerAmount.addTextChangedListener(watcher);
        etBounsNum.addTextChangedListener(watcher);

        ivProtocalSelect.setSelected(true);

        findViewById(R.id.panelRandom).setOnClickListener(this);
        findViewById(R.id.panelEqual).setOnClickListener(this);
        findViewById(R.id.panelBounsTheme).setOnClickListener(this);

        findViewById(R.id.tvBuy).setOnClickListener(this);
        findViewById(R.id.panelRecvProtocal).setOnClickListener(this);
        findViewById(R.id.tvServiceProtocal).setOnClickListener(this);

        // 默认等值金额；
        ivEqual.setSelected(true);

        if (themeBean != null)
        {
            etBounsWishes.setText(themeBean.getBlessWord());
            tvBounsTheme.setText(themeBean.getThemeName());
        }
        if(getIntent().getBooleanExtra("isFromCustomTheme",false)){
            recvTheme(getIntent());
        }
    }

    /**
     * 加载普通红包主题；
     */
    private void loadGeneralTheme()
    {
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("sort", "2"); //1、随机、2最新（创建时间倒序）
        args.put("pageStart", "0");
        args.put("pageSize", "100");
        themePresenter.loadData(args);
    }

    /**
     * 更新总金额；
     */
    private void updateTotalAmount()
    {
        float totalAmount;
        //随机金额；
        if (ivRandom.isSelected())
        {
            String amountTxt = etPerAmount.getText().toString().trim();
            totalAmount = isEmpty(amountTxt) ? 0 : Float.parseFloat(amountTxt);
        } else
        {
            String amountTxt = etPerAmount.getText().toString().trim();
            String numTxt = etBounsNum.getText().toString().trim();
            if (isEmpty(amountTxt) || isEmpty(numTxt))
            {
                totalAmount = 0;
            } else
            {
                totalAmount = Float.parseFloat(amountTxt) * Integer.parseInt(numTxt);
            }
        }
        tvTotalAmount.setText("¥" + String.format("%.2f", totalAmount));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelBounsTheme: //红包主题

                Intent intent = new Intent(this, BounsThemeActivity.class);

                intent.putExtra(BounsThemeActivity.INTENT_FLAG, getClass().getName());

                intent.putExtra("bounsParam", getBounsThemeParam());

                startActivityForResult(intent, REQ_GET_THEME);
                DefinethemeBounsActivity.bless =etBounsWishes.getText().toString();
                break;
            case R.id.panelRandom:  //随机金额

                if (!ivRandom.isSelected())
                {
                    ivRandom.setSelected(true);
                    ivEqual.setSelected(false);
                    tvAmountLabel.setText(getString(R.string.text_total_amount));
                    etPerAmount.setHint(getString(R.string.text_total_amount_hint));
                    updateTotalAmount();
                }
                break;
            case R.id.panelEqual:  //等值金额；
                if (!ivEqual.isSelected())
                {
                    ivEqual.setSelected(true);
                    ivRandom.setSelected(false);
                    tvAmountLabel.setText(getString(R.string.text_per_amount));
                    etPerAmount.setHint(getString(R.string.text_per_amount_hint));
                    updateTotalAmount();
                }
                break;
            case R.id.panelRecvProtocal:  //接受服务协议；
                ivProtocalSelect.setSelected(!ivProtocalSelect.isSelected());
                break;
            case R.id.tvServiceProtocal:  //服务协议；
                Bundle bundle = new Bundle();
                bundle.putString(WebViewClientActivity.CATEGORY, AppConstant.h5_bouns_create_service);
                bundle.putString(WebViewClientActivity.TITLE_NAME, "服务协议");
                readyGoWithBundle(this, WebViewClientActivity.class, bundle);
                break;
            case R.id.tvBuy:  //购买；
                if (isInputRight())
                {
                    //检查身份认证
                    checkUserValid(new OnIDValid()
                    {
                        @Override
                        public void onValid()
                        {
                            exeBuy();
                        }
                    });

                }
                break;
        }
    }

    private BounsThemeParam getBounsThemeParam()
    {
        BounsThemeParam param = new BounsThemeParam();
        param.setBlessWord(tvBounsTheme.getText().toString().trim());

        if (getUserBean() != null)
        {
            AuthenticationInfoBean infoBean = getUserBean().getAuthenticationInfo();
            param.setBounsSender(infoBean != null ? infoBean.getRealName() : getUserBean().getNickName());
        }
        if (themeBean != null)
        {
            param.setThemeId(themeBean.getThemeId());
        }
        param.setIsRandom(ivRandom.isSelected());
        String numTxt = etBounsNum.getText().toString().trim();
        param.setBounsNum(numTxt+"");
        param.setBounsAmount(etPerAmount.getText().toString().trim());
        return param;
    }


    private void exeBuy()
    {
        if (checkLogin())
        {
            showPDialog(R.string.setting_committing);
            Map<String, Object> args = new HashMap<>();
            args.put("issuePlatform", "3"); //1个人红包   红包类型 分个人红包、平台红包和商家红包
            args.put("amountType", ivRandom.isSelected() ? "2" : "1"); //1、等额；2、随机
            String numTxt = etBounsNum.getText().toString().trim();
            args.put("quantity", numTxt); //数量
            args.put("amount", etPerAmount.getText().toString().trim()); //金额
            args.put("blessWord", etBounsWishes.getText().toString().trim()); //祝福语
            args.put("themeName", themeBean.getThemeName());
            args.put("closeImageCode", themeBean.getCloseImageCode());
            args.put("openImageCode", themeBean.getOpenImageCode());
            args.put("createIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));
            orderCreatePresenter.loadData(args);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || resultCode != Activity.RESULT_OK) return;

        switch (requestCode)
        {
            case REQ_GET_THEME: //红包主题返回；
                recvTheme(data);
                break;
            case REQ_ADD_BANK_CARD:
                if (payHandle != null)
                {
                    payHandle.showAddCardSuccess();
                }
                break;

        }
    }

    private void recvTheme(Intent data) {
        Serializable bounsObj = data.getSerializableExtra("backThemeData");
        if (bounsObj != null && bounsObj instanceof BounsThemeBean)
        {
            BounsThemeBean themeBeanLocal = (BounsThemeBean) bounsObj;

            String oldWish = etBounsWishes.getText().toString().trim();
            //内容不等，为新输入内容；
            if (isEmpty(oldWish) || oldWish.equals(themeBean.getBlessWord()))
            {
                etBounsWishes.setText(themeBeanLocal.getBlessWord());
            }
            tvBounsTheme.setText(themeBeanLocal.getThemeName());
            themeBean = themeBeanLocal;
        }
    }

    private void initThemeTv()
    {
        if (themeBean != null)
        {
            tvBounsTheme.setText(themeBean.getThemeName());
            etBounsWishes.setText(themeBean.getBlessWord());
        }
    }

    /**
     * 付款；
     */
    private void startBuy()
    {
        payHandle = new PayRequestLogic(this);

        payHandle.showEnvelopPay(true);

        payHandle.showGiftPay(false);

        payHandle.showBalancePay(false);

        //添加卡；
        payHandle.setAddCardCallBack(new AddCardBackListener()
        {
            @Override
            public void callBack()
            {
                startActivityForResult(new Intent(BounsCreateActivity.this, AddBankCardActivity.class), REQ_ADD_BANK_CARD);
            }
        });
        payHandle.payMethodCallBack(new PayMethodListener()
        {
            @Override
            public void callBack(Map<String, String> map)
            {
                LogUtil.i("选择付款方式返回数据=" + JSON.toJSONString(map));
                payType = map.get("payType");
                payDetailId = map.get("payDetailId");
            }
        });

        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

                LogUtil.e("ABCD","-----setSuccess-----1------"+RESULT_CODE);
                onPaySucess();
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.e("ABCD","-----setSuccess-------2----"+RESULT_CODE);
                if(RESULT_CODE.equals( com.yzb.wallet.util.WalletConstant.PAY_TYPE_OFF)){// 支付卡信息不全

                    String str = JSON.toJSONString(resultMap);

                    PayMethod accountInfo = JSON.parseObject(str , PayMethod.class);

                    LogUtil.e("ABCD","---setSuccess----"+resultMap+"-------"+accountInfo.getCardType());
                    int cardType = accountInfo.getCardType();

                    Class claz = null;

                    if(cardType==1){// 储蓄卡

                        claz = AddBankCardActivity.class;

                    }else if(cardType ==2){//信用卡

                        claz = AddCanPayCardActivity.class;

                    }
                    Intent intent = new Intent(BounsCreateActivity.this, claz);
                    intent.putExtra("cardNo",accountInfo.getCardNo());
                    intent.putExtra("name", accountInfo.getName());
                    startActivity(intent);

                }else{

                    onPaySucess();

                }

            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.i("付款失败；RESULT_CODE=" + RESULT_CODE + ",ERROR_MSG=" + ERROR_MSG);
                onPayFail(ERROR_MSG);
            }
        });
        payHandle.pay(getInputMap(), false);
    }

    private Map<String, String> getInputMap()
    {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", getUserBean().getAmountAccount());
        params.put("orderNo", outBean.getOrderNo());
        params.put("orderTime", DateUtil.formatOrderTime(outBean.getOrderTime()));

        String totalAmount = tvTotalAmount.getText().toString().trim();
        params.put("amount", totalAmount.substring(1, totalAmount.length())); //订单金额；

        params.put("leftTime", AppConstant.leftTime); //超时时间
        params.put("goodsName", "红包"); //商品名称
        params.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        params.put("notifyUrl", outBean.getNotifyUrl());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);//商户号
        params.put("sign", AppConstant.sign);//签名
        return params;
    }


    public void onPaySucess()
    {
        GiftcardBuySucesDialog.getInstance().setDataHandler(dataHandler).show(getSupportFragmentManager(), "");
        updatePayDetail();
    }

    public void onPayFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    /**
     * 金额输入监听；
     */
    private MyTextWatcher watcher = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String s)
        {
            String amount = s.trim();
            //超过小数点后2位；
            if (RegexUtil.verifyJeFormat(amount))
            {
                amount = amount.substring(0, amount.indexOf(".") + 3);
                etPerAmount.setText(amount);
            }
            etBounsNum.setSelection(etBounsNum.getText().toString().trim().length());
            etPerAmount.setSelection(etPerAmount.getText().toString().trim().length());

            updateTotalAmount();
        }
    };

    private Handler dataHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case GiftcardBuySucesDialog.WHAT_SEND: //立即发送；
                    generateCommand();
                    break;
                case GiftcardBuySucesDialog.WHAT_LOOK://立即查看； 红包明细；
                    Intent detailIntent = new Intent(BounsCreateActivity.this, RedBagDetailSendActivity.class);
                    detailIntent.putExtra("orderId", outBean.getOrderId());
                    detailIntent.putExtra("typedThemeParam", getBounsThemeParam());
                    startActivity(detailIntent);
                    finish();
                    break;
                case SendGiftCardDialog.WHAT_SHARE_PLATFORM: //分享-嗨生活；
                    Intent intent = new Intent(BounsCreateActivity.this, GiveCardActivity.class);
                    intent.putExtra("recordIds", outBean.getOrderId());
                    intent.putExtra("pageType", GiveCardActivity.TYPE_BOUNS);
                    String numTxt = etBounsNum.getText().toString().trim();
                    intent.putExtra("totalNum", Integer.parseInt(numTxt));
                    startActivity(intent);
                    finish();
                    break;
                case CommandDialog.WHAT_SHARE_OTHER: //分享-其它平台；
                    readyGo(BounsCreateActivity.this, BounsHomeActivity.class);
                    finish();
                    break;
            }
            return false;
        }
    });

    /**
     * 生成口令；
     */
    private void generateCommand()
    {
        showPDialog(R.string.loading);
        Map<String, Object> args = new HashMap<>();
        args.put("codeType", AppConstant.command_type_bouns); //口令类型；1:商家，商品；2，第三方送红包；3，第三方送礼品卡；4：订单信息推送；5，平台推送信息；
        args.put("code", outBean.getOrderNo()); //编码

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", outBean.getOrderId());
        args.put("activityData", JSON.toJSONString(map));
        commandPresenter.loadData(args);
    }

    private boolean isInputRight()
    {
        if (isEmpty(etPerAmount.getText().toString().trim()))
        {
            toastCustom("请输入金额");
            return false;
        }
        if (Float.parseFloat(etPerAmount.getText().toString().trim()) <= 0)
        {
            toastCustom("金额需大于0");
            return false;
        }
        if (isEmpty(etBounsNum.getText().toString().trim()))
        {
            toastCustom("请输入红包份数");
            return false;
        }
        if (Integer.parseInt(etBounsNum.getText().toString().trim()) <= 0)
        {
            toastCustom("份数需大于0");
            return false;
        }

        if (isEmpty(etBounsWishes.getText().toString().trim()))
        {
            toastCustom("请输入红包寄语");
            return false;
        }
        if (themeBean == null)
        {
            toastCustom("请选择红包主题");
            return false;
        }
        if (!ivProtocalSelect.isSelected())
        {
            toastCustom("请同意服务协议");
            return false;
        }

        if (!isLogin())
        {
            readyGo(this, LoginActivity.class);
            return false;
        }
        return true;
    }
    /**
     * 更新付款详情；
     */
    private void updatePayDetail()
    {
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("account", getUserBean().getAccount());
        argsMap.put("transIp", AppUtils.getLocalIpAddress(GlobalApp.getInstance().getContext()));//交易IP
        argsMap.put("payType", payType);
        argsMap.put("payDetailId", payDetailId);
        argsMap.put("orderId", outBean.getOrderId());
        argsMap.put("orderTime", outBean.getOrderTime());

        String amount = tvTotalAmount.getText().toString().trim();
        argsMap.put("orderAmount", amount.substring(1, amount.length())); //订单金额；
        argsMap.put("activityName", "红包"); //活动名称；
        argsMap.put("orderType", OrderBean.ORDER_TYPE_REDPACGE); //订单类型；

        recordModel.loadData(true, argsMap);
    }

    @Override
    public void onGetCommandSuc(String commandAndUrl)
    {
        closePDialog();
        SendGiftCardDialog.getInstance()
                .setHandler(dataHandler).setFragmentManager(getSupportFragmentManager()).setCommandAndUrl(
                CommonUtil.getGiftcardShareContent("红包", commandAndUrl)).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onGetCommandFail(String failMsg)
    {
        closePDialog();
        toastCustom(failMsg);
    }

    @Override
    public void onGetBounsThemeSuc(boolean event_tag, List<BounsThemeBean> list)
    {
        closePDialog();
        if (list != null)
        {
            String theme;
            //筛选普通红包；
            for (int i = 0; i < list.size(); i++)
            {
                theme = list.get(i).getThemeName();
                if (!isEmpty(theme) && theme.contains("普通红包"))
                {
                    themeBean = list.get(i);
                    initThemeTv();
                    return;
                }
            }
        }
    }

    @Override
    public void onGetBounsThemeFail(String failMsg)
    {
        closePDialog();
        LogUtil.i(failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        this.outBean = (OrderOutBean)o;
        closePDialog();
        startBuy();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(o+"");
    }
}
