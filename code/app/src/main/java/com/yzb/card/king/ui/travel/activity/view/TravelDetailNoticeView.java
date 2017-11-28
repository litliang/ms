package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.travel.adapter.TravelNoticeAdapter;

/**
 * 功能：旅游详情-->须知view；
 *
 * @author:gengqiyun
 * @date: 2016/11/12
 */
public class TravelDetailNoticeView extends BaseViewGroup
{
    private LinearLayout panelFeeForm;
    private WholeListView feeFormListView;
    private LinearLayout panelSelfCareFee;
    private WholeListView selfCareListView;

    public TravelDetailNoticeView(Context context)
    {
        super(context);
    }

    public TravelDetailNoticeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        panelFeeForm = (LinearLayout) rootView.findViewById(R.id.panelFeeForm);
        feeFormListView = (WholeListView) rootView.findViewById(R.id.feeFormListView);
        feeFormListView.setFocusable(false);

        feeFormListView.setDivider(null);
        feeFormListView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        panelSelfCareFee = (LinearLayout) rootView.findViewById(R.id.panelSelfCareFee);
        selfCareListView = (WholeListView) rootView.findViewById(R.id.selfCareListView);
        selfCareListView.setFocusable(false);

        selfCareListView.setDivider(null);
        selfCareListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_notice;
    }

    @Override
    protected void fillViewData(TravelProduDetailBean detailBean)
    {
        feeFormListView.setAdapter(new TravelNoticeAdapter(mContext, detailBean.getFeeIncludeList()));
        selfCareListView.setAdapter(new TravelNoticeAdapter(mContext, detailBean.getOneselfFeeList()));
    }
}
