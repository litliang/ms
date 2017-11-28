package com.yzb.card.king.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.ReasonForChangeBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.appwidget.DividerDecoration;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.order.adapter.ReasonForChanageAdapter;
import com.yzb.card.king.ui.order.presenter.ReasonForChangePresenter;
import com.yzb.card.king.ui.order.view.ReasonForChangeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_reason_for_change)
public class ReasonForChangeActivity extends BaseActivity implements ReasonForChangeView
{
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    private ReasonForChanageAdapter adapter;

    /**
     * 1 退 2 改 3 升
     */
    String reasonType = "";
    private List<ReasonForChangeBean> reasonList;
    private ReasonForChangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        reasonType = getIntent().getStringExtra("reasonType");

        this.reasonList = new ArrayList<>();
        presenter = new ReasonForChangePresenter(this);
        adapter = new ReasonForChanageAdapter(this.reasonList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST,
                R.drawable.divider_transparent_6dp));
        recyclerView.setAdapter(adapter);


        Map<String, Object> params = new HashMap<>();
        params.put("type", reasonType);
        presenter.sendReasonForChangeReqeust(params, CardConstant.card_app_queryOperateReason);
    }

    @Event(value = {R.id.back, R.id.commit})
    private void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.commit: //提交；
                if (adapter.getSelectItem() == null)
                {
                    toastCustom(R.string.select_change_reason);
                    return;
                }
                ReasonForChangeBean forChangeBean = adapter.getSelectItem();
                Intent result = new Intent();
                result.putExtra("reasonBean", forChangeBean);
                setResult(RESULT_OK, result);
                finish();
                break;
        }
    }

    @Override
    public void getReasonFroChange(List<ReasonForChangeBean> reasonForChangeViews)
    {
        reasonList.clear();
        reasonList.addAll(reasonForChangeViews);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        toastCustom(o+"");
    }
}
