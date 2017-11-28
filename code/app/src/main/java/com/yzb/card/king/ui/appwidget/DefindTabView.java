package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.TravelScreenPopup;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

/**
 * 自定义tab试图
 * Created by 玉兵 on 2016/11/27.
 */

public class DefindTabView implements View.OnClickListener {

    private View tabView;
    /**
     * 底部图片控件
     */
    private ImageView ivBottom;
    /**
     * 底部文字控件
     */
    private TextView tvBottom;

    private LinearLayout llParent;

    private boolean defaultStatusFlag = true;

    /**
     * 文字选中状态文字,0未选中撞他，1选中状态
     */
    private int[] textColor = new int[]{R.color.text_color_14, R.color.color_d4b877};
    /**
     * 图片选中不选中,0未选中撞他，1选中状态
     */
    private int[] drawable = new int[]{R.mipmap.filter_default, R.mipmap.filter_selected};

    private Context context;

    private OnClickAction clickAction;
    /**
     * 打击打开PP
     */
    private boolean ppflag = false;

    public DefindTabView(Context context, OnClickAction clickAction)
    {

        this.context = context;

        this.clickAction = clickAction;

        View tabView = LayoutInflater.from(context).inflate(R.layout.common_bottom_tab, null);

        ivBottom = (ImageView) tabView.findViewById(R.id.ivBottom);

        tvBottom = (TextView) tabView.findViewById(R.id.tvBottom);

        llParent = (LinearLayout) tabView.findViewById(R.id.llParent);

        this.tabView = tabView;

        llParent.setOnClickListener(this);

    }

    public void setTextColor(int[] textColor){
        this.textColor = textColor;
    }

    public void setPpflag(boolean ppflag)
    {
        this.ppflag = ppflag;
    }

    public void setDrawable(int[] drawable)
    {
        this.drawable = drawable;
    }

    /**
     * 添加tab到底部
     *
     * @param ll
     * @param index
     */
    public void addTabToLL(LinearLayout ll, int index)
    {

        ivBottom.setBackgroundResource(drawable[0]);

        tvBottom.setTextColor(context.getResources().getColor(textColor[0]));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        lp.weight = 1;

        ll.addView(tabView, index, lp);

        llParent.setTag(index);

    }

    public View getTabView()
    {

        return tabView;
    }

    /**
     * 设置试图数据
     *
     * @param stringName
     * @param drawable
     */
    public void setViewData(int stringName, int[] drawable)
    {

        setDrawable(drawable);

        ivBottom.setBackgroundResource(drawable[0]);

        tvBottom.setText(stringName);
    }

    /**
     * 设置选中状态
     */
    public  void setSelectedTabStatus(boolean flag){


        if(flag){
            uiHandler.sendEmptyMessage(1);
        }else{
            uiHandler.sendEmptyMessage(0);
        }


    }

    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            ivBottom.setBackgroundResource(drawable[msg.what]);

            tvBottom.setTextColor(context.getResources().getColor(textColor[msg.what]));
        }
    };

    @Override
    public void onClick(View v)
    {
        if (ppflag) {

            if(defaultStatusFlag){

                defaultStatusFlag = false;
            }else{
                defaultStatusFlag = true;
            }

            clickAction.onTabClickItem((int) v.getTag(), tvBottom,defaultStatusFlag);


        } else {


            if (defaultStatusFlag) {

                defaultStatusFlag = false;

                ivBottom.setBackgroundResource(drawable[1]);

                tvBottom.setTextColor(context.getResources().getColor(textColor[1]));



            } else {

                defaultStatusFlag = true;

                ivBottom.setBackgroundResource(drawable[0]);

                tvBottom.setTextColor(context.getResources().getColor(textColor[0]));
            }

            clickAction.onTabClickItem((int) v.getTag(), tvBottom,defaultStatusFlag);
        }
    }

    public interface OnClickAction {

        void onTabClickItem(int index, TextView textView,boolean selectedStatus);

    }
}
