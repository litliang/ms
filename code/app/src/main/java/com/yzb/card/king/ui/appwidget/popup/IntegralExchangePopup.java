package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.integral.model.IIntegralExchange;
import com.yzb.card.king.ui.integral.model.impl.IntegralExchangeImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/24
 * 描  述：
 */
public class IntegralExchangePopup implements DataCallBack{


    private Activity activity;
    private View parentView;
    private ListView listView;
    private PopupWindow popupWindow;
    private PriorityListener listener;

    private String planId;

    private MyAdapter myAdapter;

    private LinearLayout llParent;

    private IntegralExchangeImpl exchange;

    public IntegralExchangePopup(View parentView, Activity activity, PriorityListener listener) {
        this.parentView = parentView;
        this.activity = activity;
        this.listener = listener;

        exchange = new IntegralExchangeImpl(this);
    }



    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(int index);
    }


    /**
     * 关闭dialog
     */
    public void dismiss() {
        popupWindow.dismiss();
    }

    /**
     * 创建飞行计划的pop
     *
     * @param list
     */
    public void create(List<ExchangeIntegralBean> list, int layoutId) {

        View layout = activity.getLayoutInflater().inflate(layoutId, null);

        llParent = (LinearLayout) layout.findViewById(R.id.llParent);


        listView = (ListView) layout.findViewById(R.id.llTravellerPlanInfo);

        myAdapter = new MyAdapter();

        listView.setAdapter(myAdapter);

        myAdapter.addExchangeIntegralBeanList(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ExchangeIntegralBean temp = (ExchangeIntegralBean) myAdapter.getItem(position);

                listener.refreshPriorityUI(position);

                dismiss();
            }
        });

        int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                .MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.discount_popupwindow_animation);
        popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(parentView, 0, 0);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                //  listener.refreshPriorityUI(activity.getString(R.string.credit_card_all));
//            }
//        });

        layout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                popupWindow.dismiss();
                return false;
            }
        });

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
    }

    /**
     * 创建积分兑换Pop
     *
     * @param type
     * @param airlineId
     */
    public void create(int type, String airlineId) {

        View layout = activity.getLayoutInflater().inflate(R.layout.view_popup_integral_exchange, null);

        llParent = (LinearLayout) layout.findViewById(R.id.llParent);

        exchange.pointExchangePlanRequest(type,airlineId);

//        PointExchangePlanRequest request = new PointExchangePlanRequest(type, airlineId, new RequestCallBack() {
//            @Override
//            public void onSuccess(Object o) {
//
//                Map<String, String> resultMap = (Map<String, String>) o;
//
//                String data = resultMap.get("data");
//
//                String sessionId = resultMap.get("sessionId");
//
//                AppConstant.handleSessionId(sessionId);
//
//                List<ExchangeIntegralListBean> userBean = JSON.parseArray(data, ExchangeIntegralListBean.class);
//
//                List<ExchangeIntegralBean> list = userBean.get(0).getRuleList();
//
//                if (list.size() > 0) {
//                    planId = userBean.get(0).getPlanId();
//
//                    myAdapter.addExchangeIntegralBeanList(list);
//                }
//            }
//
//            @Override
//            public void onFailed(Object o) {
//
//            }
//        });
//
//        request.execute(request.getParam(), null, null);

        listView = (ListView) layout.findViewById(R.id.llTravellerPlanInfo);

        myAdapter = new MyAdapter();

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ExchangeIntegralBean temp = (ExchangeIntegralBean) myAdapter.getItem(position);

                listener.refreshPriorityUI(position);

                dismiss();
            }
        });

        int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                .MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.discount_popupwindow_animation);
        popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAsDropDown(parentView, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //  listener.refreshPriorityUI(activity.getString(R.string.credit_card_all));
            }
        });

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                popupWindow.dismiss();
                return false;
            }
        });

    }

    /**
     * 创建计划列表的pop
     *
     * @param list
     */
    public void createPlan(List<ExchangeIntegralListBean> list) {

        View layout = activity.getLayoutInflater().inflate(R.layout.view_popup_integral_exchange, null);

        llParent = (LinearLayout) layout.findViewById(R.id.llParent);


        listView = (ListView) layout.findViewById(R.id.llTravellerPlanInfo);

        myAdapter = new MyAdapter();

        listView.setAdapter(myAdapter);

        myAdapter.addExchangeIntegralListBean(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ExchangeIntegralBean temp = (ExchangeIntegralBean) myAdapter.getItem(position);

                listener.refreshPriorityUI(position);

                dismiss();
            }
        });

        int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams
                .MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.discount_popupwindow_animation);
        popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAsDropDown(parentView, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //  listener.refreshPriorityUI(activity.getString(R.string.credit_card_all));
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);


    }


    public ExchangeIntegralBean getSelectOjbect(int index) {

        return (ExchangeIntegralBean) myAdapter.getItem(index);

    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    private class MyAdapter extends BaseAdapter {

        List<Object> userBean = new ArrayList<Object>();

        public void addNewData(List<Object> userBean) {

            this.userBean.clear();

            this.userBean.addAll(userBean);

            notifyDataSetChanged();

        }

        /**
         * 添加兑换积分集合
         *
         * @param list
         */
        public void addExchangeIntegralBeanList(List<ExchangeIntegralBean> list) {
            userBean.clear();

            for (ExchangeIntegralBean bean : list) {

                userBean.add(bean);
            }
            notifyDataSetChanged();
        }

        /**
         * 添加飞行计划集合
         *
         * @param list
         */
        public void addExchangeIntegralListBean(List<ExchangeIntegralListBean> list) {

            userBean.clear();

            for (ExchangeIntegralListBean bean : list) {

                userBean.add(bean);
            }
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return userBean.size();
        }

        @Override
        public Object getItem(int position) {
            return userBean.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHold viewHold = null;

            if (convertView == null) {

                viewHold = new ViewHold();

                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_integral_exchange, null);

                viewHold.tvName = (TextView) convertView.findViewById(R.id.tvName);


                convertView.setTag(viewHold);
            } else {

                viewHold = (ViewHold) convertView.getTag();
            }

            Object o = getItem(position);

            if (o instanceof ExchangeIntegralBean) {


                ExchangeIntegralBean bean = (ExchangeIntegralBean) o;

                StringBuffer sb = new StringBuffer();

                sb.append(bean.getRuleName() + "  ");

                sb.append(bean.getPoint() + ":" + bean.getExchangeResult());

                viewHold.tvName.setText(sb.toString());

            } else if (o instanceof ExchangeIntegralListBean) {

                ExchangeIntegralListBean bean = (ExchangeIntegralListBean) o;

                StringBuffer sb = new StringBuffer();

                sb.append(bean.getPlanName());

                viewHold.tvName.setText(sb.toString());

            }
            return convertView;
        }


        class ViewHold {

            TextView tvName;

        }
    }

    @Override
    public void requestSuccess(Object o, int type)
    {

        if( type == IIntegralExchange.POINTEXCHANGEPLAN_CODE)
        {
            List<ExchangeIntegralListBean> userBean = (List<ExchangeIntegralListBean>) o;

            List<ExchangeIntegralBean> list = userBean.get(0).getRuleList();

            if (list.size() > 0) {

                planId = userBean.get(0).getPlanId();

                myAdapter.addExchangeIntegralBeanList(list);
            }
        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {

    }

}
