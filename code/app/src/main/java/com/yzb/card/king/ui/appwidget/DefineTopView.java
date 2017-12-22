package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.CommonUtil;

/**
 * Created by 玉兵 on 2017/7/16.
 */

public class DefineTopView implements View.OnClickListener{


    private View tabView;

    private  Context context;

    private TextView tvTabName;

    private ImageView ivRight;

    private  OnClickAction clickAction;

    private boolean currentCheckStatus = false;


    public DefineTopView(Context context,OnClickAction clickAction)
    {
        this.context = context;

        this.clickAction = clickAction;

        View tabView = LayoutInflater.from(context).inflate(R.layout.common_top_tab, null);

        this.tabView = tabView;

        tvTabName = (TextView) tabView.findViewById(R.id.tvTabName);

        ivRight = (ImageView) tabView.findViewById(R.id.ivRight);

        tvTabName.setEnabled(currentCheckStatus);

        tabView.setOnClickListener(this);

    }

    private  boolean noClickTab = true;

    /**
     * 设置颜色和背景色
     */
    public void setUiColor(){

        tvTabName.setBackgroundResource(R.drawable.shap_corner_gray_gray);

        tvTabName.setTextColor(Color.parseColor("#999999"));

        ivRight.setVisibility(View.INVISIBLE);

        noClickTab = false;

    }

    public void setTabName(String tabNameStr){
        tvTabName.setText(tabNameStr);
    }


    public void setTabCheckStatus(boolean checkStatus){

        tvTabName.setEnabled(checkStatus);

        if(checkStatus){
            ivRight.setVisibility(View.VISIBLE);
        }else{
            ivRight.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 添加tab到底部
     *
     * @param ll
     * @param index
     */
    public void addTabToLL(LinearLayout ll, int index)
    {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        lp.weight = 1;

        if(index != 0){

            lp.leftMargin = CommonUtil.dip2px(context,7);
        }

        ll.addView(tabView, index, lp);

        tabView.setTag(index);

    }

    @Override
    public void onClick(View v) {

        if(noClickTab){

            setTabCheckStatus(true);

            if(clickAction != null){

                clickAction.onTabClickItem((int)v.getTag(),null,true);
            }

        }

    }

    public interface OnClickAction {

        void onTabClickItem(int index, TextView textView,boolean selectedStatus);

    }
}
