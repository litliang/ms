package com.yzb.card.king.ui.ticket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FlightDetailBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.WebViewSettings;

/**
 * 类  名：退改详情
 * 作  者：Li Yubing
 * 日  期：2017/9/22
 * 描  述：
 */
public class TicketBackChangeFragment extends Fragment implements View.OnClickListener{


    private TicketDetailFragmentDialog.TicketDialogInterface parentDataCallAction;

    private  String priceId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.view_air_ticket_back_change, null);

        view.findViewById(R.id.llLeft).setOnClickListener(this);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("退改签须知详情");


        Bundle bundle =   getArguments();

        if(bundle.containsKey("data")){

            FlightDetailBean.TicketPriceInfoBean bean = (FlightDetailBean.TicketPriceInfoBean) bundle.getSerializable("data");

            this.priceId = bean.getTicketPriceId();
        }

        WebView  webView = (WebView) view.findViewById(R.id.webView);
        WebViewSettings.setting(webView, null);

        String url = getRefundRuleUrl(priceId);

        webView.loadUrl(url);
        return  view;

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
    @Override
    public void onClick(View v)
    {
        parentDataCallAction.backAction(0);
    }

    public void setTickeDetaiDataCall(TicketDetailFragmentDialog.TicketDialogInterface tickeDetaiDataCall)
    {
        this.parentDataCallAction = tickeDetaiDataCall;
    }
}
