package com.yzb.logic_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.R;
import com.yzb.wallet.logic.pay.TransferHandle;
import com.yzb.wallet.openInterface.AddCardBackListener;
import com.yzb.wallet.openInterface.ForgetPwdBackListener;
import com.yzb.wallet.openInterface.PayFailBackListener;
import com.yzb.wallet.openInterface.PayMethodListener;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class TransferActivity extends AppCompatActivity {

    private TextView code;
    private TextView error;
    private TextView orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        code = (TextView) findViewById(R.id.code);
        error = (TextView) findViewById(R.id.error);

        orderNo = (TextView) findViewById(R.id.orderNo);
        orderNo.setText(genOrderNo());

        final EditText amount = (EditText) findViewById(R.id.amount);

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String amountStr = amount.getText().toString();

                String temp = s.toString();
                int len = temp.length();

                // 如果第一位是0则第二位不允许输入数字
                if (len == 2 && "0".equals(temp.substring(0, 1))) {
                    if (Pattern.matches("(\\d)", temp.substring(1, 2)))
                        s.delete(1, 2);
                }

                // 保留2位小数
                int posDot = temp.indexOf(".");
                if (posDot == 0) s.clear();
                if (posDot < 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });

        ((Button) findViewById(R.id.rechargeBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView amount = (TextView) findViewById(R.id.amount);
                TextView goodsName = (TextView) findViewById(R.id.goodsName);
                TextView transIp = (TextView) findViewById(R.id.transIp);

                TextView debitMobile = (TextView) findViewById(R.id.debitMobile);
                TextView debitType = (TextView) findViewById(R.id.debitType);
                TextView debitSortCode = (TextView) findViewById(R.id.debitSortCode);

                TextView creditMobile = (TextView) findViewById(R.id.creditMobile);
                TextView creditType = (TextView) findViewById(R.id.creditType);
                TextView creditCardName = (TextView) findViewById(R.id.creditCardName);
                TextView creditCardNo = (TextView) findViewById(R.id.creditCardNo);


                TextView merchantNo = (TextView) findViewById(R.id.merchantNo);
                TextView sign = (TextView) findViewById(R.id.sign);

                Map<String, String> params = new HashMap<String, String>();
                params.put("amount", amount.getText().toString());
                //params.put("fee", "0.02");
                params.put("orderNo", orderNo.getText().toString());
                params.put("goodsName", goodsName.getText().toString());
                params.put("transIp", transIp.getText().toString());

                params.put("debitMobile", debitMobile.getText().toString());
                params.put("debitCustomerType", "P");// 用户类型 P个人 C商户
                //params.put("debitType", debitType.getText().toString());
                //params.put("debitSortCode", debitSortCode.getText().toString());

                params.put("creditMobile", creditMobile.getText().toString());
                params.put("creditCustomerType", "P");// 用户类型 P个人 C商户
                params.put("creditType", creditType.getText().toString());
                params.put("creditCardName", creditCardName.getText().toString());
                params.put("creditCardNo", creditCardNo.getText().toString());

                params.put("notifyUrl", ":http://116.228.184.115:8082/card/api/walletUpdateOrders/1178");

                params.put("merchantNo", merchantNo.getText().toString());
                params.put("sign", sign.getText().toString());

                // 初始化
                final TransferHandle payHandle = new TransferHandle(TransferActivity.this);

                // 转账操作 显示"忘记密码"：是true 否false或null
                payHandle.pay(params, true);

                /*以下方法 非必调用 start*/
                // 显示/隐藏 现金账户 默认显示
                payHandle.showBalancePay(true);
                // 显示/隐藏 储蓄卡 默认显示
                payHandle.showDebitCard(true);
                // 显示/隐藏 信用卡 默认隐藏
                payHandle.showCreditCard(true);
                // 隐藏该银行卡 默认显示
                //payHandle.hiddenCard("6230580000035630883");
                /*以下方法 非必调用 end*/

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

                // 支付失败
                payHandle.payFailCallBack(new PayFailBackListener() {
                    @Override
                    public void callBack() {

                        // TODO 支付失败
                        System.out.println("==支付失败==>");
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
