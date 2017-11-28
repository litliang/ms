package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
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

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */
public class AddRosterPopup {


    private Activity activity;
    private View parentView;
    private ListView listView;
    private LinearLayout credit_index_allCustomer_hide2_layout;
    private LinearLayout credit_index_allCustomer_hide_layout;
    private PopupWindow popupWindow;
    private View.OnClickListener positiveBtnListener;
    private PriorityListener listener;

    List<IntegralShareLinkman> userBean = new ArrayList<IntegralShareLinkman>();
    private MyAdapter myAdapter;

    private LinearLayout llParent;

    public AddRosterPopup(View parentView, Activity activity, PriorityListener listener) {
        this.parentView = parentView;
        this.activity = activity;
        this.listener = listener;

        IntegralShareLinkman  temp1 = new IntegralShareLinkman();
        temp1.setRelationship("1");
        temp1.setRelationName("父母");
        userBean.add(temp1);

        IntegralShareLinkman  temp2 = new IntegralShareLinkman();
        temp2.setRelationship("2");
        temp2.setRelationName("夫妻");
        userBean.add(temp2);

        IntegralShareLinkman  temp3 = new IntegralShareLinkman();
        temp3.setRelationship("3");
        temp3.setRelationName("子女");
        userBean.add(temp3);

        IntegralShareLinkman  temp4 = new IntegralShareLinkman();
        temp4.setRelationship("4");
        temp4.setRelationName("亲戚");
        userBean.add(temp4);

        IntegralShareLinkman  temp5= new IntegralShareLinkman();
        temp5.setRelationship("5");
        temp5.setRelationName("朋友");
        userBean.add(temp5);

        IntegralShareLinkman  temp6= new IntegralShareLinkman();
        temp6.setRelationship("6");
        temp6.setRelationName("同事");
        userBean.add(temp6);
    }


    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(Object o);
    }


    /**
     * 关闭dialog
     */
    public void dismiss() {
        popupWindow.dismiss();
    }

    public void create() {

        View layout = activity.getLayoutInflater().inflate(R.layout.view_popup_add_roster, null);

        llParent = (LinearLayout) layout.findViewById(R.id.llParent);



        listView = (ListView) layout.findViewById(R.id.llTravellerPlanInfo);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IntegralShareLinkman temp =    userBean.get(position);

                listener.refreshPriorityUI(temp);

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
                listener.refreshPriorityUI(null);
            }
        });
    }


    private class MyAdapter extends BaseAdapter {





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

                viewHold.ivLogo.setVisibility(View.GONE);

                viewHold.tvName.setTextColor(activity.getResources().getColor(R.color.black2));

                convertView.setTag(viewHold);
            } else {

                viewHold = (ViewHold) convertView.getTag();
            }

            IntegralShareLinkman bean = (IntegralShareLinkman) getItem(position);

            viewHold.tvName.setText(bean.getRelationName());



            return convertView;
        }

        class ViewHold {

            TextView tvName;

            ImageView ivLogo;
        }
    }


}
