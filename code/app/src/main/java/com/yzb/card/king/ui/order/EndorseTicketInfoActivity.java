package com.yzb.card.king.ui.order;

import android.os.Bundle;
import android.widget.ListView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.TicketOrderDetBean;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.order.adapter.EndorseTicketInfoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 机票改签详情
 * Created by 玉兵 on 2017/10/27.
 */

public class EndorseTicketInfoActivity extends BaseActivity{

    private ListView listView;

    private EndorseTicketInfoAdapter endorseTicketInfoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colorStatusResId = R.color.color_436a8e;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_tickets_endorse_ticket_info);

        initView();

        initData();
    }

    private void initData() {


        TicketOrderDetBean  ticketOrderDetBean = (TicketOrderDetBean) getIntent().getSerializableExtra("ticketOrderDetBean");

        List<TicketOrderDetBean.OrderInfoBean>  list = ticketOrderDetBean.getOrderInfo();

        endorseTicketInfoAdapter.appendALL(list);

    }

    private void initView() {

        endorseTicketInfoAdapter = new EndorseTicketInfoAdapter(this);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(endorseTicketInfoAdapter);


    }
}
