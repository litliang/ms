package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.hotel.HotelLevelBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：酒店产品订单详情
 * 作  者：Li Yubing
 * 日  期：2017/8/21
 * 描  述：
 */
public class HotelProductOrderDetailsPopup {

    private BaseFullPP baseBottomFullPP;

    private Activity activity;


    private List<HotelProductOrderDetailPpBean> list = new ArrayList<HotelProductOrderDetailPpBean>();

    private ViewDataListCallBack callBack;

    private     LinearLayout llContent;

    private  LayoutInflater layoutInflater;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     */
    public HotelProductOrderDetailsPopup(Activity activity, int defineHeight, BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        layoutInflater = LayoutInflater.from(activity);

        AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

        int h = (int) activity.getResources().getDimension(R.dimen.tab_bottom_fifty);

        int deH = bean.getScreenHeight() - h - bean.getStatusBarHeight();

        baseBottomFullPP = new BaseFullPP(activity, postion, deH);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_hotel_order_details_pp, null);

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
            @Override
            public void onClickListenerDismiss()
            {
                if(callBack != null){

                    callBack.onConfirm();

                }

            }
        });


         llContent = (LinearLayout) view.findViewById(R.id.llContent);

        loadDataView(list);

    }

    public void loadDataView(List<HotelProductOrderDetailPpBean> list)
    {

        llContent.removeAllViews();

        int size = list.size();

        for( int i = 0 ; i < size; i++){

            HotelProductOrderDetailPpBean bean = list.get(i);

            if (bean.ifFather){
                View fatherView = layoutInflater.inflate(R.layout.view_hotel_product_detail_father,null);

                TextView tvFatherName = (TextView) fatherView.findViewById(R.id.tvFatherName);

                TextView tvFatherCash = (TextView) fatherView.findViewById(R.id.tvFatherCash);

                tvFatherName.setText(bean.productNameStr);

                tvFatherCash.setText(Utils.subZeroAndDot(bean.detailPrice+""));

                llContent.addView(fatherView);
            }else {
                View childView = layoutInflater.inflate(R.layout.view_hotel_product_detail_child,null);

                TextView tvChildName = (TextView) childView.findViewById(R.id.tvChildName);

                TextView tvChildCash = (TextView) childView.findViewById(R.id.tvChildCash);

                tvChildName.setText(bean.productNameStr);

                tvChildCash.setText(Utils.subZeroAndDot(bean.detailPrice+""));

                llContent.addView(childView);
            }
        }

    }


    public interface ViewDataListCallBack {

        void onConfirm();
    }

    public void showPP(View rootView)
    {

        baseBottomFullPP.showTopByView(rootView);

    }


    public void showBottomByViewPP(View rootView)
    {

        baseBottomFullPP.showAsDropDown(rootView);

    }

    public void setViewDataCallBack(ViewDataListCallBack callBack){

        this.callBack = callBack;
    }


   public static class  HotelProductOrderDetailPpBean{

        public  boolean ifFather = true;

        public String productNameStr;

        public float detailPrice ;

    }

}
