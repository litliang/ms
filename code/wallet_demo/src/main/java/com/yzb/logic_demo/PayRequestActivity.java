package com.yzb.logic_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.R;
import com.yzb.wallet.logic.pay.PayRequestLogic;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.BackListener;
import com.yzb.wallet.openInterface.ForgetPwdBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.sys.ServiceDispatcher;
import com.yzb.wallet.util.DateUtil;
import com.yzb.wallet.util.WalletConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayRequestActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;
    private TextView data;
    private TextView orderNo;
    private TextView orderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pay_request);

        orderNo = (TextView) findViewById(R.id.orderNo);
        orderNo.setText(genOrderNo());
        orderTime = (TextView) findViewById(R.id.orderTime);
        orderTime.setText(DateUtil.date2String(new Date(), DateUtil.DEFAULT_DATE_FORMAT));

        ((Button) findViewById(R.id.payBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code = (TextView) findViewById(R.id.code);
                error = (TextView) findViewById(R.id.error);
                data = (TextView) findViewById(R.id.data);

                TextView mobile = (TextView) findViewById(R.id.mobile);

                TextView accountType = (TextView) findViewById(R.id.accountType);
                TextView sortCode = (TextView) findViewById(R.id.sortCode);
                TextView payMethodName = (TextView) findViewById(R.id.payMethodName);

                TextView orderNo = (TextView) findViewById(R.id.orderNo);
                TextView orderTime = (TextView) findViewById(R.id.orderTime);
                TextView amount = (TextView) findViewById(R.id.amount);
                TextView leftTime = (TextView) findViewById(R.id.leftTime);
                TextView goodsName = (TextView) findViewById(R.id.goodsName);
                TextView transIp = (TextView) findViewById(R.id.transIp);
                TextView notifyUrl = (TextView) findViewById(R.id.notifyUrl);

                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", "18856066230");
                params.put("customerType", "P");// 用户类型，可为空 P个人 C公司

                params.put("accountType", "5");
                params.put("sortCode", "5f6ecbe7a61ea634f4f293fa4701eb24");
                params.put("payMethodName", "红包");

                params.put("orderNo", "9912320264569139");
                params.put("orderTime", "20171123202618");

                params.put("amount", "100");
                params.put("amountEnvelop", "");
                params.put("amountGift",  "");
                params.put("amountIntegral",  "");
                params.put("amountEnvelopPt",  "");

                params.put("leftTime", "20");
                params.put("goodsName", "正海宾馆(经济型单间)");
                params.put("transIp", "192.168.0.106");
                params.put("notifyUrl", "http://116.228.184.115:8082/card/api/walletUpdateOrders/1460");

                params.put("merchantNo", "141023000000000290");
                params.put("sign", "18856066230");

                ServiceDispatcher.app_url_api  = "http://116.228.184.117:8082/app/api/api/";

                // 初始化
                final PayRequestLogic payHandle = new PayRequestLogic(PayRequestActivity.this);

                // 充值操作 显示"忘记密码"：是true 否false或null
                payHandle.pay(params, true);

                // 显示/隐藏付款详情
                //payHandle.showPayInfo(false);

                // 显示/隐藏 现金账户 默认显示
                payHandle.showBalancePay(false);

                // 显示/隐藏 红包账户 默认隐藏
                payHandle.showEnvelopPay(true);

                // 显示/隐藏 礼品卡账户 默认隐藏
                payHandle.showGiftPay(true);

                // 显示/隐藏 信用账户 默认隐藏
                payHandle.showCreditPay(false);

                // 显示/隐藏 储蓄卡 默认显示
                payHandle.showDebitCard(true);

                // 显示/隐藏 信用卡 默认隐藏
                payHandle.showCreditCard(true);

                // 操作结果
                payHandle.setCallBack(new WalletBackListener() {
                    @Override
                    public void setSuccess(String RESULT_CODE) {
                        System.out.println("=返回结果=>code" + RESULT_CODE);
                        code.setText(RESULT_CODE);
                    }

                    @Override
                    public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                        System.out.println("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                        code.setText(RESULT_CODE);
                        data.setText(JSON.toJSONString(resultMap));
                    }

                    @Override
                    public void setError(String RESULT_CODE, String ERROR_MSG) {
                        System.out.println("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                        code.setText(RESULT_CODE);
                        error.setText(ERROR_MSG);
                    }
                });

                // 忘记密码回掉
                payHandle.setForgetPwdCallBack(new ForgetPwdBackListener() {
                    @Override
                    public void callBack() {

                        // TODO 跳转忘记密码页面
                        System.out.println("==跳转忘记密码页面==>");

                    }
                });

                // 添加银行卡回掉
                payHandle.setAddCardCallBack(new AddCardBackListener() {
                    @Override
                    public void callBack() {

                        // TODO 跳转添加银行卡页面
                        System.out.println("==跳转添加银行卡页面==>");

                        // TODO 绑卡成功，显示页面
                        payHandle.showAddCardSuccess();
                    }
                });

                // 选择付款方式回掉
                payHandle.payMethodCallBack(new PayMethodListener() {
                    @Override
                    public void callBack(Map<String, String> data) {

                        // TODO 选择付款方式返回数据
                        System.out.println("选择付款方式返回数据=="+data);
                    }
                });

                //返回
                payHandle.setBack(new BackListener() {
                    @Override
                    public void callBack(Map<String, String> data) {

                        // TODO 返回map，2个关键字，code 0001 和data文字说明
                        System.out.println("返回=="+data);
                    }
                });
            }
        });

        ((Button) findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(0x0);
                finish();
            }
        });
    }

    private String genOrderNo() {
        //订单的生成
//        String time = DateUtil.date2String(new Date(), "yyyyMMddHHmmss"); //订单 v创建时间
        String time = DateUtil.date2String(new Date(), "yyyy"); //订单 v创建时间
        String random1 = String.valueOf(Math.round(Math.random() * 100));
        String random2 = String.valueOf(Math.round(Math.random() * 100));
        String random3 = String.valueOf(Math.round(Math.random() * 100));

        return "T" + time + random1 + random2 + random3;
    }
}
