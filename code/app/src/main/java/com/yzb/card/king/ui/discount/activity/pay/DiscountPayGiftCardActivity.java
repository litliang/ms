package com.yzb.card.king.ui.discount.activity.pay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.AccountBalanceBean;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.appwidget.popup.GiftCardDialogFragment;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.ticket.presenter.AccountBalancePresenter;
import com.yzb.card.king.ui.ticket.view.AccountBalanceView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.Utils;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 特惠付款：使用礼品卡
 *
 * @author gengqiyun
 * @date 2016.8.26
 */
public class DiscountPayGiftCardActivity extends BaseActivity implements View.OnClickListener, AccountBalanceView
{
    private TextView tvTotalAmount; //礼品卡总额；
    private EditText etUseAmount;   //输入金额；

    private double mTotalBalance = 0; //礼品卡账户总额；  目前写死；
    private double mRecvUseAmount = 0;//接收的上次输入金额；
    private double orderAmount = 0;//接收的订单金额；
    private AccountBalancePresenter balancePresenter;
    private View tvUse;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_pay_gift_card);
        balancePresenter = new AccountBalancePresenter(this, this);
        recvIntentData();
        initView();
        getGiftCardBalance();
    }

    private void recvIntentData()
    {
        Intent intent = getIntent();
        mRecvUseAmount = intent.getFloatExtra("useAmount", 0f);
        orderAmount = intent.getFloatExtra("orderAmount", 0f);
        LogUtil.i("礼品卡余额=" + mTotalBalance + ",接受到的金额=" + mRecvUseAmount);
    }

    private void initView()
    {
        setHeader(R.mipmap.icon_back_white, getString(R.string.discount_use_giftcard), R.mipmap.icon_add_giftcard);
        tvTotalAmount = (TextView) findViewById(R.id.tvGiftCardAmount);
        tvTotalAmount.setText(Utils.subZeroAndDot(mTotalBalance + "") + "");

        // 使用金额
        etUseAmount = (EditText) findViewById(R.id.etAmount);
        etUseAmount.addTextChangedListener(etAmountTextChangeListener);
        // 添加礼品卡
        findViewById(R.id.headerRight).setOnClickListener(this);
        findViewById(R.id.headerLeft).setOnClickListener(this);
        // 使用金额
        tvUse = findViewById(R.id.tvUse);
        tvUse.setOnClickListener(this);
        tvUse.setEnabled(false);

        if (mRecvUseAmount > 0f)
        {
            etUseAmount.setText(mRecvUseAmount + "");
        }
    }

    //输入金额格式处理；
    private MyTextWatcher etAmountTextChangeListener = new MyTextWatcher()
    {
        @Override
        public void afterTextChange(String text)
        {
            //判断合法；
            if (!isEmpty(text) && !RegexUtil.verifyJeFormat2(text))
            {
                etUseAmount.setText(text.substring(0, text.length() - 1));
            }

            text = etUseAmount.getText().toString().trim();

            //当超过用户总额时，自动变显示为用户红包总额
            if (!isEmpty(text) && Double.parseDouble(text) > mTotalBalance)
            {
                etUseAmount.setText(mTotalBalance + "");
            }
            etUseAmount.setSelection(etUseAmount.getText().toString().length());
            tvUse.setEnabled(!isEmpty(text) ? true : false);
        }
    };

    /**
     * 获取礼品卡账户余额；
     */
    private void getGiftCardBalance()
    {
        showPDialog(R.string.loading_giftcard_amount);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("accountType", AppConstant.ACCOUNT_TYPE_GIFT_CARD);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", AppConstant.sign);
        balancePresenter.loadData(params);
    }

    @Override
    public void onGetAccountBalanceSucess(AccountBalanceBean balanceBean)
    {
        closePDialog();
        if (balanceBean != null)
        {
            //总余额；
            String balanceEmp = TextUtils.isEmpty(balanceBean.getBalance()) ? "0.00" : balanceBean.getBalance();
            mTotalBalance = Double.parseDouble(Utils.subZeroAndDot(balanceEmp));

            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            tvTotalAmount.setText(nf.format(mTotalBalance));
        }
    }

    @Override
    public void onGetAccountBalanceFail(String failMsg)
    {
        closePDialog();
        toastGiftCardAmountFail(failMsg);
    }

    /**
     * toast提示礼品卡总额失败；
     *
     * @param failMsg
     */
    public void toastGiftCardAmountFail(String failMsg)
    {
        toastCustom(failMsg);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvUse:
                if (isInputRight())
                {
                    Intent intent = new Intent();
                    String useAmount = etUseAmount.getText().toString();
                    intent.putExtra("giftcardBackAmount", Float.parseFloat(useAmount));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.headerRight:// 添加礼品卡
                addGiftCardDialog();
                break;
        }
    }

    /**
     * show 礼品卡dialog；
     */
    private void addGiftCardDialog()
    {
        GiftCardDialogFragment.getInstance()
                .setCardPayCallBack(new GiftCardDialogFragment.IGiftCardPayCallBack()
                {
                    @Override
                    public void addAcardSuccess()
                    {
                        //添加卡成功；开始执行转帐操作；
                        transferAccounts();
                    }
                }).show(getSupportFragmentManager(), "");
    }

    /**
     * 礼品卡充值成功后的转帐操作；
     */
    private void transferAccounts()
    {
        getGiftCardBalance();
    }

    private boolean isInputRight()
    {
        String inputAmount = etUseAmount.getText().toString().trim();
        if (TextUtils.isEmpty(inputAmount))
        {
            toastCustom("使用金额不能为空");
            return false;
        }
        //比较临界值；
        float inputAmountFloast = Float.parseFloat(inputAmount);
        if (inputAmountFloast < 0)
        {
            toastCustom("使用金额不能小于0");
            return false;
        }
        if (inputAmountFloast > orderAmount)
        {
            toastCustom("使用金额不得超出订单金额");
            return false;
        }
        return true;
    }
}
