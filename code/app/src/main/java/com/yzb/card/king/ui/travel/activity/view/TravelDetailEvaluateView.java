package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.other.activity.WypjActivity;
import com.yzb.card.king.ui.travel.adapter.EvaluateListAdapter;
import com.yzb.card.king.ui.user.LoginActivity;

import java.util.List;

/**
 * 功能：旅游详情-->评价view；
 *
 * @author:gengqiyun
 * @date: 2016/11/12
 */
public class TravelDetailEvaluateView extends BaseViewGroup implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private OnClickListener clickListener;
    private ListView evaluateListView;
    private View panelHasEvaluate; //有评价层；
    private View emptyView; //无评价层；
    private EvaluateListAdapter adapter;

    public TravelDetailEvaluateView(Context context)
    {
        super(context);
    }

    public TravelDetailEvaluateView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        evaluateListView = (ListView) rootView.findViewById(R.id.evaluateListView);
        evaluateListView.setFocusable(false);

        adapter = new EvaluateListAdapter(mContext);
        evaluateListView.setAdapter(adapter);
        evaluateListView.setOnItemClickListener(this);

        panelHasEvaluate = rootView.findViewById(R.id.panelHasEvaluate);
        panelHasEvaluate.setVisibility(GONE);
        emptyView = rootView.findViewById(R.id.emptyView);
        emptyView.setOnClickListener(this);

        emptyView.setVisibility(VISIBLE);
        rootView.findViewById(R.id.tvLookMore).setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_evaluate;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvLookMore: //查看更多；
                if (clickListener != null)
                {
                    clickListener.onClick(v);
                }
                break;
            case R.id.emptyView: //跳转发布评论页；
                if (!isLogin())
                {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                if (provider != null && provider.getProduDetailBean() != null)
                {
                    Intent intent2 = new Intent(mContext, WypjActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("detailId", provider.getProduDetailBean().getProductId());
                    bundle2.putString("type", "2");
                    intent2.putExtra(AppConstant.INTENT_BUNDLE, bundle2);
                    mContext.startActivity(intent2);
                }
                break;
        }
    }

    @Override
    public void fillViewEvaluates(List<PjBean> pjBeanList)
    {
        List<PjBean> evaluateBeans = pjBeanList;
        boolean isNull = evaluateBeans == null || evaluateBeans.size() == 0;
        emptyView.setVisibility(isNull ? VISIBLE : GONE);
        panelHasEvaluate.setVisibility(isNull ? GONE : VISIBLE);

        if (!isNull)
        {
            evaluateBeans = evaluateBeans.size() > 2 ? evaluateBeans.subList(0, 2) : evaluateBeans;
            adapter.setList(evaluateBeans);
        }
    }

    public void setClickListener(OnClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        PjBean evaluateBean = adapter.getItem(position);

//        //跳转评价详情；
//        Intent intent = new Intent(mContext, PjDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("pjId", evaluateBean.getVoteId());
//        bundle.putString("argIdKey", "productId");
//        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
//        mContext.startActivity(intent);
//        LogUtil.i("点击的position==" + position);
    }
}
