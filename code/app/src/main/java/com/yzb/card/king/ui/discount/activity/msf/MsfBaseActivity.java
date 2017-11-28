package com.yzb.card.king.ui.discount.activity.msf;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.LogUtil;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/18
 * 描  述：
 */
public class MsfBaseActivity extends BaseActivity {

    protected TextView showGet;
    protected TextView showPay;
    protected TextView btGetCode;
    protected RelativeLayout headerLayout;

    protected boolean flagPayType;//true:付款方；false:收款方。

    protected String payType;


    protected void userStatusUI()
    {

        payType = getIntent().getStringExtra("payType");

       // LogUtil.e("XYYYYYY","payType="+payType);

        initUI();

    }

    protected void initUI()
    {

        flagPayType = payType.equals(AppConstant.DEBIT);

        showGet.setTextColor(flagPayType ? getResources().getColor(R.color.wordGrey) : getResources().getColor(R.color.wordBlue));

        showPay.setTextColor(flagPayType ? getResources().getColor(R.color.wordRed) : getResources().getColor(R.color.wordGrey));

        if (flagPayType) {

            if (btGetCode != null) {
                btGetCode.setBackgroundResource(R.drawable.style_btn_red_small);
            }

            headerLayout.setBackgroundColor(getResources().getColor(R.color.titleRed));

        } else {


            if (btGetCode != null) {
                btGetCode.setBackgroundResource(R.drawable.style_btn_blue_small);
            }

            headerLayout.setBackgroundColor(getResources().getColor(R.color.titleBlue));
        }
    }
}
