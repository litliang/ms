package com.yzb.card.king.ui.ticket.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.WebViewSettings;

import java.io.Serializable;

/**
 * 机票退改签规则；
 *
 * @author gengqiyun
 * @date 2016.11.20
 */
public class RefundTicketRuleActivity extends BaseActivity implements View.OnClickListener
{
    private WebView webView;
    private TextView tvPrice; //价格；
    private FlightDetailBean.TicketPriceInfoBean priceInfoBean;

    private LinearLayout llParent;

    private  String priceId;

    private  boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_ticket_rule);
        recvIntentData();
        assignViews();
        getData();
    }

    private void getData()
    {

        String url = getRefundRuleUrl(priceId);


        webView.loadUrl(url);
    }

    private void recvIntentData()
    {
       if( getIntent().hasExtra("data")){
           Serializable data = getIntent().getSerializableExtra("data");
           if (data != null)
           {
               priceInfoBean = (FlightDetailBean.TicketPriceInfoBean) data;

               this.priceId = priceInfoBean.getTicketPriceId();
           }
       }else if(getIntent().hasExtra("priceId")){

          String priceId = getIntent().getStringExtra("priceId");

          this.priceId = priceId;
           LogUtil.e("XYYY","priceId="+priceId);

           flag = true;

       }

    }

    /**
     * 获取机票退改签规则url；
     */
    public String getRefundRuleUrl(String  priceId)
    {
        if (priceId != null)
        {
            return ServiceDispatcher.url_refund_rule + "?ticketpriceId=" + priceId;
        }
        return "";
    }

    private void assignViews()
    {
        webView = (WebView) findViewById(R.id.webView);
        WebViewSettings.setting(webView, null);

        tvPrice = (TextView) findViewById(R.id.tvPrice);
        findViewById(R.id.tvOrder).setOnClickListener(this);

        llParent = (LinearLayout) findViewById(R.id.llParent);

        if(flag){
            llParent.setVisibility(View.GONE);
        }

        if (priceInfoBean != null)
        {
            tvPrice.setText(Utils.subZeroAndDot((priceInfoBean.getFareAdult() + priceInfoBean.getFueltaxAdult()) + ""));
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvOrder: //预定；
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    protected int getColorResId()
    {
        return R.color.color_1E2326;
    }
}
