package com.yzb.card.king.ui.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.activity.PaySettingActivity;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.msf.DiscountMsfMoneyPGActivity;
import com.yzb.card.king.ui.discount.activity.msf.DiscountQsfMoneyActivty;
import com.yzb.card.king.ui.discount.activity.msf.DiscountQsfNfcLinkedActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.CardInfo;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.bean.WalletHomeInfo;
import com.yzb.card.king.ui.my.holder.PayeeHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.zxing.activity.MipcaActivityCapture;
import com.yzb.wallet.logic.bank.bankListLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_wallet)
public class WalletActivity extends BaseActivity {

    private static final int REQ_CHARGE = 2;
    private static final int REQ_CASH = 3;
    private static final int REQ_TRANSFER = 4;
    private static final int REQ_CARD_LIST = 5;

    @ViewInject(R.id.listView)
    private WholeListView listView;

    @ViewInject(R.id.tvMoney)
    private TextView tvMoney;

    @ViewInject(R.id.tvGainSum)
    private TextView tvGainSum;

    @ViewInject(R.id.tvRate)
    private TextView tvRate;

    @ViewInject(R.id.tvGain)
    private TextView tvGain;

    @ViewInject(R.id.tvCardNum)
    private TextView tvCardNum;

    @ViewInject(R.id.ivArrowGray)
    private ImageView ivArrowGray;

    @ViewInject(R.id.llChongzhiMoney)
    private LinearLayout llChongzhiMoney;

    @ViewInject(R.id.tvBankMsg)
    private TextView tvBankMsg;

    @ViewInject(R.id.llBankNum)
    private LinearLayout llBankNum;

    private NfcManager nfcManager = null;
    private NfcAdapter nfcAdapter = null;
    private AbsBaseListAdapter<Payee> adapter;
    private List<Payee> payeeList = new ArrayList<>();
    private int pageStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();

    }

    private void initData()
    {
        initTitle();
        initListView();
        initNFC();
        loadWalletInfo();
        loadCardNum();
        loadPayee();
    }

    private void loadWalletInfo()
    {
        SimpleRequest<WalletHomeInfo> request = new SimpleRequest<WalletHomeInfo>(CardConstant.SelectAccountInfo) {
            @Override
            protected WalletHomeInfo parseData(String data)
            {
                return JSON.parseObject(data, WalletHomeInfo.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("amountAccount", UserManager.getInstance().getUserBean().getAmountAccount());
        param.put("balanceStatus", "1");
        param.put("preProfitStatus", "1");
        param.put("annualRateStaus", "1");
        param.put("totalProfitStatus", "1");
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<WalletHomeInfo>() {
            @Override
            public void onSuccess(WalletHomeInfo data)
            {
                setWalletInfo(data);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
                setWalletInfo(null);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        loadWalletInfo();
    }


    private void setWalletInfo(WalletHomeInfo data)
    {
        if (data == null) data = new WalletHomeInfo("0.00", "0.00", 0, "0.00");
        tvMoney.setText(data.getBalance());
        tvGain.setText(data.getPreProfit());
        tvGainSum.setText(data.getTotalProfit());
        tvRate.setText(data.getAnnualRateBalance() + "%");
    }

    private void loadPayee()
    {
        SimpleRequest<List<Payee>> request = new SimpleRequest<List<Payee>>(CardConstant.card_querytradeobject) {
            @Override
            protected List<Payee> parseData(String data)
            {
                return JSON.parseArray(data, Payee.class);
            }
        };
        final Map<String, Object> params = new HashMap<>();
        params.put("type", "0");
        params.put("pageStart", pageStart);
        params.put("pageSize", AppConstant.MAX_PAGE_NUM);
        request.setParam(params);
        request.sendRequestNew(new BaseCallBack<List<Payee>>() {
            @Override
            public void onSuccess(List<Payee> data)
            {
                payeeList.clear();
                payeeList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                payeeList.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initNFC()
    {
        // 判断是否有NFC功能
        nfcManager = (NfcManager) getSystemService(NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();
    }


    private void loadCardNum()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);

        bankListLogic payHandle = new bankListLogic(this);
        payHandle.getData(params);
        payHandle.setCallBack(new WalletBackListener() {
            @Override
            public void setSuccess(String RESULT_CODE)
            {

            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                setCardNum(JSON.parseArray(resultMap.get("data"), CardInfo.class).size());
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                setCardNum(0);
                UiUtils.shortToast(ERROR_MSG);
            }
        });
    }


    private void setCardNum(int num)
    {
        tvCardNum.setText(num + "");

        if(num == 0){
            tvBankMsg.setVisibility(View.VISIBLE);

            llBankNum.setVisibility(View.GONE);
        }else {

            tvBankMsg.setVisibility(View.GONE);

            llBankNum.setVisibility(View.VISIBLE);
        }
    }

    private void initListView()
    {
        adapter = new AbsBaseListAdapter<Payee>(payeeList) {
            @Override
            protected Holder getHolder(int position)
            {
                return new PayeeHolder();
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Payee payee = payeeList.get(position);

                if ("1".equals(payee.getTradeType())) {//平台账户
                    goNext(HiLifeTransferActivity.class, payee);
                } else if ("2".equals(payee.getTradeType())) {//储蓄卡账户
                    goNext(BankCardTransActivity.class, payee);
                }
            }
        });
    }

    private void goNext(Class clazz, Payee payee)
    {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("payee", payee);
        startActivityForResult(intent, REQ_TRANSFER);
    }

    private void initTitle()
    {

        findViewById(R.id.llRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(WalletActivity.this, PaySettingActivity.class));
            }
        });

    }

    @Event(R.id.llChongzhiControl)
    private void llChongzhiControl(View view){

        int vi = llChongzhiMoney.getVisibility();

        if(vi == View.GONE){

            llChongzhiMoney.setVisibility(View.VISIBLE);
            Utils.changeBackground(this, ivArrowGray);

        }else {

            llChongzhiMoney.setVisibility(View.GONE);

            Utils.backBackground(this, ivArrowGray);
        }

    }

    @Event(R.id.llBankCard)
    private void goCardList(View view)
    {
        startActivityForResult(new Intent(this, CardListActivity.class), REQ_CARD_LIST);
    }

    @Event(R.id.llQianShouFu)
    private void qianShouFu(View view)
    {
        GlobalApp.orderType = "18";
        // 显示牵手付
        // 判断手机是否支持NFC
        if (nfcManager == null || nfcAdapter == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.merchant_offers_title);
            builder.setMessage(R.string.merchant_offers_your_phone_no_nfc);
            builder.setPositiveButton(R.string.merchant_offers_confirm, null);
            builder.create().show();
            return;
        } else {

            Intent intent1 = new Intent();
            // 是否启用NFC
            if (!nfcAdapter.isEnabled()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.merchant_offers_title);
                builder.setMessage(R.string.merchant_offers_if_open_nfc);
                builder.setNegativeButton(R.string.merchant_offers_close, null);
                builder.setPositiveButton(R.string.merchant_offers_open,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                                startActivity(intent);
                                return;
                            }
                        });
                builder.create().show();
            } else {
                if (ServiceDispatcher.isNetworkConnected(this)) {

                    intent1.putExtra("payType", AppConstant.CREDIT);

                    intent1.setClass(this, DiscountQsfMoneyActivty.class);//DiscountQsfPayActivity  DiscountQsfMoneyActivty

                    startActivity(intent1);

                } else {

                    intent1.putExtra("payType", AppConstant.DEBIT);

                    intent1.setClass(this, DiscountQsfNfcLinkedActivity.class);

                    startActivity(intent1);
                }
            }
        }
    }

    @Event(R.id.llPayScanner)
    private void llPayScanner(View view){
        GlobalApp.orderType = "17";
//        Intent intent2 = new Intent();
//        intent2.putExtra("isSaoYiSao", true);
//        intent2.putExtra("payType", AppConstant.CREDIT);
//        intent2.setClass(this, MipcaActivityCapture.class);
//        startActivityForResult(intent2, AppConstant.REQ_MSF_PAY);

        Intent intent = new Intent();
        intent.putExtra("isSaoYiSao", false);
        intent.putExtra("payType",AppConstant.DEBIT);
        intent.setClass(this, MipcaActivityCapture.class);
        startActivity(intent);
    }

    @Event(R.id.llMaShangFu)
    private void maShangFu(View view)
    {
        GlobalApp.orderType = "17";
        Intent intent = new Intent();
        // 判断是否有网络状态 如果有网 进入扫描二维码  没有则进入输入金 额页面
        if (ServiceDispatcher.isNetworkConnected(this)) {


            if (UserManager.getInstance().isLogin()) {
                //检查是否普通用户和商户
//                String customerType = UserManager.getInstance().getUserBean().getCustomerType();
//
//                if (UserBean.C.equals(customerType)) {//商户
//
                intent.putExtra("payType", AppConstant.CREDIT);

                intent.setClass(this, DiscountMsfMoneyPGActivity.class);

                startActivity(intent);
//
//                } else {//普通用户
//
//                intent.putExtra("isSaoYiSao", false);
//                intent.putExtra("payType", AppConstant.DEBIT);
//                intent.setClass(this, MipcaActivityCapture.class);
//                startActivity(intent);
//                }

            } else {

                new GoLoginDailog(this).create().show();
            }

        } else {
            // 进入输入金额页面
            intent.putExtra("payType", AppConstant.DEBIT);
            intent.setClass(this, DiscountMsfMoneyPGActivity.class);
            startActivity(intent);
        }
    }

    @Event(R.id.llTransfer)
    private void transfer(View view)
    {
        startActivityForResult(new Intent(this, TransferAcountsActivity.class), REQ_TRANSFER);
    }

    @Event( {R.id.llChargeOne, R.id.llChargeTwo,R.id.llChargeThree})
    private void charge(View view)
    {
        int id = view.getId();

        String titleName = "充值";

        int accountType = 0;

        if(id == R.id.llChargeOne){

            titleName = "礼品卡充值";

            accountType = 3;

        }else if(id == R.id.llChargeTwo){

            titleName = "Hi钱包充值";

            accountType = 1;

        }else if(id == R.id.llChargeThree){

            titleName = "红包充值";

            accountType = 2;
        }

        Intent intent = new Intent(this, ChargeActivity.class);

        intent.putExtra("titleName",titleName);

        intent.putExtra("accountType",accountType);

        startActivityForResult(intent, REQ_CHARGE);
    }


    @Event(R.id.llCash)
    private void cash(View view)
    {
        Intent intent = new Intent(this, CashActivity.class);
        intent.putExtra("amount", tvMoney.getText());
        startActivityForResult(intent, REQ_CASH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CASH:
                case REQ_CHARGE:
                    loadWalletInfo();
                    break;
                case REQ_TRANSFER:
                    loadPayee();
                    break;
                case REQ_CARD_LIST:
                    loadCardNum();
                    break;
            }
        }
    }
}
