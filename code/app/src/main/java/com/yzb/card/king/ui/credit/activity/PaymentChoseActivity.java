package com.yzb.card.king.ui.credit.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.AutomaticPaymentPop;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.adapter.ChosePayMentAdapter;
import com.yzb.card.king.ui.credit.bean.CreditCard;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 选择还款方式
 */
public class PaymentChoseActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_huankuanMoney;

    private TextView money_huank;

    private int defaultPos;

    private LinearLayout panel_back;
    private Button sure;

    private CreditCard data;

    private String[] list = new String[]{"全额", "最低还款金额"};

    private String hkMoney;

    private ImageView bankLogo;

    private TextView bankName, peopleName, latNum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_chose);
        initView();
        reciveData();
    }

    /**
     * 设置信息
     */
    private void setData()
    {
        if (data != null)
        {
            if (!TextUtils.isEmpty(data.getLogo()))
            {
                x.image().bind(bankLogo, ServiceDispatcher
                        .getImageUrl(data.getLogo()));
            }
            bankName.setText(data.getBankName());
            peopleName.setText(data.getUserName());
            latNum.setText("尾号" + data.getSortNo());
        }
    }

    /**
     * 接收信用卡信息
     */
    private void reciveData()
    {
        Intent i = getIntent();
        if (i != null)
        {
            data = (CreditCard) i.getSerializableExtra("data");
            if ("1".equals(data.getAutoType()))
            {
                defaultPos = 0;
                money_huank.setText(list[0]);
            } else if ("2".equals(data.getAutoType()))
            {
                defaultPos = 1;
                money_huank.setText(list[1]);
            }
            setData();
        }
    }

    private void initView()
    {
        rl_huankuanMoney = (RelativeLayout) findViewById(R.id.rl_huankuanMoney);
        rl_huankuanMoney.setOnClickListener(this);
        sure = (Button) findViewById(R.id.sure);
        bankLogo = (ImageView) findViewById(R.id.img_logo);
        bankName = (TextView) findViewById(R.id.bankName);
        peopleName = (TextView) findViewById(R.id.peopleName);
        latNum = (TextView) findViewById(R.id.lastNum);
        sure.setOnClickListener(this);
        panel_back = (LinearLayout) findViewById(R.id.panel_back);
        panel_back.setOnClickListener(this);
        money_huank = (TextView) findViewById(R.id.money_huank);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.panel_back:
                finish();
                break;
            case R.id.rl_huankuanMoney:
                showDialog();
                break;
            case R.id.sure:  //确定
                sureSet(view);
                break;
        }
    }

    private void sureSet(final View view)
    {
        Map<String, Object> param = new HashMap<>();
        if (defaultPos == 0)
        {
            hkMoney = "1";
        } else if (defaultPos == 1)
        {
            hkMoney = "2";
        }
        param.put("autoType", hkMoney);
        param.put("creditId", data.getId());
        new SimpleRequest(CardConstant.CREDIT_UPDATE, param).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                data.setAutoType(hkMoney);
                AutomaticPaymentPop pop = new AutomaticPaymentPop(PaymentChoseActivity.this);
                pop.openPop();
                RepaymentActivity.setData(data);
                CardManageActivity.setData(data);
            }

            @Override
            public void onFailed(Object o)
            {

            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });

    }

    private void showDialog()
    {
        LinearLayout linearLayoutMain = new LinearLayout(this);//自定义一个布局文件
        linearLayoutMain.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ListView listView = new ListView(this);//this为获取当前的上下文
        listView.setFadingEdgeLength(0);
        listView.setDivider(null);
        final ChosePayMentAdapter a = new ChosePayMentAdapter(this, list, defaultPos);
        listView.setAdapter(a);
        linearLayoutMain.addView(listView);//往这个布局中加入listview
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
                .create();
        a.setOnItemClickListener(new ChosePayMentAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int postion)
            {
                defaultPos = postion;
                money_huank.setText(list[postion]);
                dialog.cancel();
            }
        });
        dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
        dialog.show();
    }
}
