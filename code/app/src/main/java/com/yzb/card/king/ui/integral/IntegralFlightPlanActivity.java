package com.yzb.card.king.ui.integral;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.IntegralExchangePopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.integral.presenter.IntegralExchangePresenter;
import com.yzb.card.king.ui.user.ProtocolActivity;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */

@ContentView(R.layout.activity_integral_flight_plan)
public class IntegralFlightPlanActivity extends BaseActivity implements BaseViewLayerInterface {

    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    @ViewInject(R.id.lv)
    private ListView lv;

    @ViewInject(R.id.llParent)
    private LinearLayout llParent;

    @ViewInject(R.id.tvScale)
    private TextView tvScale;

    private MyAdapter adapter;

    private String airlineId;

    private IntegralExchangePopup allCustomerPopup;

    private List<ExchangeIntegralBean> list = null;

    private IntegralExchangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new IntegralExchangePresenter(this);
        initView();

        initData();
    }

    @Event(R.id.rlArrow)
    private void rlArrow(View v) {
        
        if (adapter.getCount() > 0) {

            if (list != null && list.size() > 0) {

                allCustomerPopup = new IntegralExchangePopup(llParent, IntegralFlightPlanActivity.this, new
                        IntegralExchangePopup.PriorityListener() {


                    @Override
                    public void refreshPriorityUI(int index) {

                        ExchangeIntegralBean currentExchangeIntegralBean = allCustomerPopup.getSelectOjbect(index);

                        if (currentExchangeIntegralBean != null) {

                            tvScale.setText(currentExchangeIntegralBean.getPoint() + "积分 = " +
                                    currentExchangeIntegralBean.getExchangeResult()+getString(R.string.integral_gongli));

                        }

                    }
                });

                allCustomerPopup.create(list,R.layout.view_popup_integral_exchange_fly);

            } else {
                ToastUtil.i(this, R.string.integral_no_rate_information);
            }
        } else {

            ToastUtil.i(this, R.string.integral_current_plan);
        }

    }

    private void initData() {

        presenter.sendExchangePointPlanRequest(1,airlineId,this);

    }

    private void initView() {

        titleName.setText(R.string.integral_flight_plan_table);

        adapter = new MyAdapter();
        lv.setAdapter(adapter);

    }

    /**
     * 查看详情
     * @param v
     */
    public void checkDetail(View v) {

        int index = adapter.getCurrentIndex();

        if (index == -1) {

            ToastUtil.i(this, R.string.integral_toast_please_one_plan);

        } else {
            startActivity(new Intent(this, ProtocolActivity.class));
        }


    }


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if( o != null && o instanceof  List){

            List<ExchangeIntegralListBean> userBean = (List<ExchangeIntegralListBean>) o;

            adapter.addNewData(userBean);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }

    private class MyAdapter extends BaseAdapter {

        private int currentIndex = -1;

        public int getCurrentIndex() {
            return currentIndex;
        }

        public void setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
        }

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
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHold viewHold = null;

            if (convertView == null) {

                viewHold = new ViewHold();

                convertView = LayoutInflater.from(IntegralFlightPlanActivity.this).inflate(R.layout
                        .view_item_flight_plan, null);

                viewHold.tvName = (TextView) convertView.findViewById(R.id.tvName);

                viewHold.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);

                viewHold.tvName.setTextColor(getResources().getColor(R.color.integral_gray_898989));

                viewHold.ibSelect = (ImageButton) convertView.findViewById(R.id.ibSelect);

                convertView.setTag(viewHold);
            } else {

                viewHold = (ViewHold) convertView.getTag();
            }

            ExchangeIntegralListBean bean = (ExchangeIntegralListBean) getItem(position);


            viewHold.tvName.setText(bean.getPlanName());

            if (currentIndex == position) {

                viewHold.ibSelect.setBackgroundResource(R.mipmap.icon_check_red_active);

                viewHold.tvName.setTextColor(getResources().getColor(R.color.transparent_red));
            } else {

                viewHold.ibSelect.setBackgroundResource(R.mipmap.icon_check_grey_default);

                viewHold.tvName.setTextColor(getResources().getColor(R.color.gray_898989));
            }

            if (!TextUtils.isEmpty(bean.getImageCode())) {
                x.image().bind(viewHold.ivLogo,ServiceDispatcher.getImageUrl(bean.getImageCode()));
            }



            viewHold.ibSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tvScale.setText("");
                    if (position == currentIndex) {

                        currentIndex = -1;

                        list = null;

                    } else {

                        currentIndex = position;

                        list =  ((ExchangeIntegralListBean) getItem(position)).getRuleList();
                    }

                    notifyDataSetChanged();
                }
            });

            return convertView;
        }

        class ViewHold {

            TextView tvName;

            ImageView ivLogo;

            ImageButton ibSelect;
        }
    }

}
