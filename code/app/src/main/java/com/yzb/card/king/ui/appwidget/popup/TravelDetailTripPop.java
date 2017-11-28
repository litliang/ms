package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.travel.activity.TravelProductDetailActivity;
import com.yzb.card.king.ui.travel.adapter.TravelDetailTripAdapter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/14
 * 描  述：
 */
public class TravelDetailTripPop extends PopupWindow
{

    private PopupWindow popupWindow;
    private Activity activity;

    private List<Map> list = new ArrayList<>();
    ;

    private TextView price;

    private String title;

    public final static int DETAIL_CODE = 1; //不显示顶部和底部的信息

    private String lineId;
    private int type; //列表与详情标识
    private TextView go_detail;

    private String travelId = "";

    private TravelDetailTripAdapter adapter;

    private String travelPrice = "0";

    public TravelDetailTripPop(Activity activity, String lineId, int type, String travelId)
    {
        this.travelId = travelId;
        this.lineId = lineId;
        this.type = type;
        this.activity = activity;
    }

    /**
     * 旅游列表传入价格设置价格
     *
     * @param price
     */
    public void setPrice(String price)
    {
        this.travelPrice = price;
    }

    /**
     * 旅游列表传入标题
     *
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }


    public void openPop()
    {
        if (popupWindow == null)
        {
            popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(popupWindow, R.layout.popwindow_travel_trip);
        }
        initPopData();
        popupWindow.showAtLocation(popupWindow.getContentView(), Gravity.TOP, 0, 0);
    }

    /**
     * 获取行程概要信息
     */
    private void sendRequest()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("lineId", lineId);
        new SimpleRequest(CardConstant.TRAVEL_XINGCHEN, param).sendRequest(new HttpCallBackData()
        {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                list.clear();
                List<Map> map = JSON.parseArray(String.valueOf(o), Map.class);
                list.addAll(map);
                adapter.notifyDataSetChanged();
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


    private void initPopData()
    {
        View contentView = popupWindow.getContentView();
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recy_trip);
        adapter = new TravelDetailTripAdapter(activity, list);
        sendRequest();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        TextView txDetail = (TextView) contentView.findViewById(R.id.detail_info);
        LinearLayout llPrice = (LinearLayout) contentView.findViewById(R.id.ll_price_info);
        price = (TextView) contentView.findViewById(R.id.price);

        SpannableString ss = new SpannableString("¥" + Utils.subZeroAndDot(travelPrice) + "起");
        ss.setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(activity, 12)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(activity, 12)), ss.length() - 1, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setText(ss);

        if (DETAIL_CODE == type)
        {
            txDetail.setVisibility(View.GONE);
            llPrice.setVisibility(View.GONE);
        } else
        {
            txDetail.setVisibility(View.VISIBLE);
            txDetail.setText(title);
            llPrice.setVisibility(View.VISIBLE);
        }
        //查看详情
        go_detail = (TextView) contentView.findViewById(R.id.go_detail);
        if (!travelId.equals("") || travelId != null)
        {
            go_detail.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(activity, TravelProductDetailActivity.class);
                    intent.putExtra("id", travelId);
                    activity.startActivity(intent);
                }
            });
        }
        contentView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                popupWindow.dismiss();
            }
        });
    }

    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        if (window != null)
        {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popwindow_inright);
            window.setFocusable(true);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        if (window != null && contentViewId != 0)
        {
            window.setContentView(activity.getLayoutInflater().inflate(contentViewId, null));
        }
    }

}
