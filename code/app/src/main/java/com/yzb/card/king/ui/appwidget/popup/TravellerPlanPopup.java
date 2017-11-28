package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.http.integral.FrequentPassengerPlanRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.manage.IntegralDataManager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/22
 * 描  述：
 */
public class TravellerPlanPopup {


    private Activity activity;
    private View parentView;
    private ListView listView;
    private LinearLayout credit_index_allCustomer_hide2_layout;
    private LinearLayout credit_index_allCustomer_hide_layout;
    private PopupWindow popupWindow;
    private View.OnClickListener positiveBtnListener;
    private PriorityListener listener;


    private MyAdapter myAdapter;

    private LinearLayout llParent;

    public TravellerPlanPopup(View parentView, Activity activity, PriorityListener listener) {
        this.parentView = parentView;
        this.activity = activity;
        this.listener = listener;
    }


    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(ExchangeIntegralListBean string);
    }


    /**
     * 关闭dialog
     */
    public void dismiss() {
        popupWindow.dismiss();
    }

    public void create(String airlineId) {

        View layout = activity.getLayoutInflater().inflate(R.layout.view_popup_traveller_plan, null);

        llParent = (LinearLayout) layout.findViewById(R.id.llParent);

        List<ExchangeIntegralListBean> list = IntegralDataManager.getInstance().getTravellerPlanBeanList();

        listView = (ListView) layout.findViewById(R.id.llTravellerPlanInfo);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ExchangeIntegralListBean bean = (ExchangeIntegralListBean) myAdapter.getItem(position);

                listener.refreshPriorityUI(bean);

                dismiss();
            }
        });

        myAdapter = new MyAdapter();

        listView.setAdapter(myAdapter);

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

        if (list == null) {


            FrequentPassengerPlanRequest request = new FrequentPassengerPlanRequest(airlineId,new DataCallBack() {

                @Override
                public void requestSuccess(Object o, int type)
                {
                    String data = o+"";

                    List<ExchangeIntegralListBean> userBean = JSON.parseArray(data, ExchangeIntegralListBean.class);

                    if (userBean.size() > 10) {

                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llParent.getLayoutParams();

                        layoutParams.width = 120;

                        llParent.setLayoutParams(layoutParams);
                    }


                    myAdapter.addNewData(userBean);
                }

                @Override
                public void requestFailedDataCall(Object o, int type)
                {

                }
            });

            request.execute();

        } else {

            if (list.size() > 10) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llParent.getLayoutParams();

                layoutParams.width = 120;

                llParent.setLayoutParams(layoutParams);


            }

            myAdapter.addNewData(list);

        }


    }


    private class MyAdapter extends BaseAdapter {

        List<ExchangeIntegralListBean> userBean = new ArrayList<ExchangeIntegralListBean>();

        public void addNewData(List<ExchangeIntegralListBean> userBean) {

            this.userBean.clear();

            this.userBean.addAll(userBean);

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

                convertView = LayoutInflater.from(activity).inflate(R.layout.view_item_traveller_plan, null);

                viewHold.tvName = (TextView) convertView.findViewById(R.id.tvName);

                viewHold.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);

                convertView.setTag(viewHold);
            } else {

                viewHold = (ViewHold) convertView.getTag();
            }

            ExchangeIntegralListBean bean = (ExchangeIntegralListBean) getItem(position);

            viewHold.tvName.setText(bean.getPlanName());

            if (!TextUtils.isEmpty(bean.getImageCode())) {

                x.image().bind(viewHold.ivLogo,ServiceDispatcher.getImageUrl(bean.getImageCode()),
                        GlobalApp.getInstance().getImageOptionsLogo() );
            }

            return convertView;
        }

        class ViewHold {

            TextView tvName;

            ImageView ivLogo;
        }
    }


}
