package com.yzb.card.king.ui.discount.activity.msf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.NumberKeypadView;
import com.yzb.card.king.util.ToastUtil;

import java.util.regex.Pattern;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/2/9
 * 描  述：
 */
public class DiscountQsfMoneyActivty extends MsfBaseActivity implements View.OnClickListener {


    private EditText getAmount;

    private View viewNumberKeypay;

    private NumberKeypadView numberKeypadView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.discount_msf_pay);

        init();
        // 设置标题
        setHeader(R.mipmap.icon_back_white, "牵手付");

    }

    private void init()
    {
        showGet = (TextView) findViewById(R.id.showGet);

        showPay = (TextView) findViewById(R.id.showPay);

        headerLayout = (RelativeLayout) findViewById(R.id.headerLayout);

        findViewById(R.id.headerLeft).setOnClickListener(this);

        userStatusUI();
        showGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                payType = AppConstant.CREDIT;


            }
        });

        showPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(flagPayType){
                    return;
                }
                payType = AppConstant.DEBIT;

                Intent intent1 = new Intent();

                intent1.putExtra("payType",payType);

                intent1.setClass(DiscountQsfMoneyActivty.this, DiscountQsfNfcLinkedActivity.class);

                startActivity(intent1);

             //   uiHandler.sendEmptyMessageDelayed(0,500);


            }
        });

        // 遮盖层,不弹出系统自带键盘
        ((EditText) findViewById(R.id.shadowText)).clearFocus();

        getAmount = (EditText) findViewById(R.id.getAmount);

        getAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

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

        //数据键盘
        viewNumberKeypay = findViewById(R.id.viewNumberKeypay);

        numberKeypadView = new NumberKeypadView(viewNumberKeypay, this);

        numberKeypadView.setAmount(getAmount);

        numberKeypadView.setKeyHandler(keyHandler);

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        getAmount.setText("");
    }

    private  Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            finish();
        }
    };

    private Handler keyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            String amount = getAmount.getText().toString();

            if (!TextUtils.isEmpty(amount)) {

                Intent it = new Intent(DiscountQsfMoneyActivty.this, DiscountQsfNfcLinkedActivity.class);

                it.putExtra("payType", AppConstant.CREDIT);

                it.putExtra("money", amount);

                startActivity(it);

                uiHandler.sendEmptyMessageDelayed(0,500);

            } else {

                ToastUtil.i(DiscountQsfMoneyActivty.this, "请输入金额");
            }
        }
    };


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;

            default:
                break;

        }
    }
}
