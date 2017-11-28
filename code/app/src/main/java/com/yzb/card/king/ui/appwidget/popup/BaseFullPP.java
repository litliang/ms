package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.sys.GlobalApp;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/13
 * 描  述：从当前屏幕底部或者顶部全屏显示视图
 */
public class BaseFullPP extends PopupWindow {

    public enum ViewPostion {
        VIEW_TOP,VIEW_BOTTOM
    }

    private Activity activity;

    /**
     * 试图容器
     */
    private LinearLayout llViewContainer;

    private PpOndismisssListener listener;

    public BaseFullPP(Activity activity, ViewPostion postion)
    {

        this.activity = activity;
        initView(postion);
    }

    public BaseFullPP(Activity activity, ViewPostion postion,int h)
    {

        this.activity = activity;

        initView(postion);

        if(postion == ViewPostion.VIEW_BOTTOM){
            setHeight(h);
        }

    }


    private void initView( ViewPostion postion)
    {

        View view = null;

        if(ViewPostion.VIEW_TOP == postion){
            view = LayoutInflater.from(activity).inflate(R.layout.pp_base_top_view,null);
        }else{
            view = LayoutInflater.from(activity).inflate(R.layout.pp_base_bottom_view,null);
        }


        setContentView(view);

        view.findViewById(R.id.llTopEmpty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        llViewContainer = (LinearLayout) view.findViewById(R.id.llViewContainer);

        initPParmas();
    }


    public void setListener(PpOndismisssListener listener)
    {
        this.listener = listener;
    }

    /**
     * 添加子视图
     * @param childView
     */
    public void addChildView(View childView){

        llViewContainer.removeAllViews();

        llViewContainer.addView(childView);

    }

    /**
     * 初始化pp参数
     */
    private void initPParmas()
    {

        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);// 点击空白处时，隐藏掉pop窗口
        setOnDismissListener(currentPPdismissListener);
        setOutsideTouchable(true);

    }

    /**
     * 全屏显示pp
     *
     * @param rootView 当前页面
     */
    public void show(View rootView)
    {
        showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
                0);
    }

    /**
     * 居某个视图下面显示
     * @param rootView
     */
    public void showBottomAsView(View rootView){

        showAsDropDown(rootView);

    }

    public void showTopByView(View rootView){
        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

       int statusBarHeight = bean.getStatusBarHeight();
        showAtLocation(rootView, Gravity.TOP, 0,
                statusBarHeight);
    }


    private OnDismissListener currentPPdismissListener = new OnDismissListener() {
        @Override
        public void onDismiss()
        {

            if(listener != null){

                listener.onClickListenerDismiss();
            }

        }
    };

    interface  PpOndismisssListener{

        public void onClickListenerDismiss();

    }
}
