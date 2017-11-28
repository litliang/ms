package com.yzb.card.king.ui.integral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.AirlinesBean;
import com.yzb.card.king.bean.integral.ExchangeMileageBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.appwidget.vhtable.CHTableScrollView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.integral.presenter.IntegralExchangePresenter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类  名：兑换里程表
 * 作  者：Li Yubing
 * 日  期：2016/6/23
 * 描  述：
 */
@ContentView(R.layout.activity_integral_exchange_mileage_table)
public class ExchangeMileageTableActivity extends BaseActivity implements BaseViewLayerInterface {

    //控件
    @ViewInject(R.id.titleName)
    private TextView titleName;

    public HorizontalScrollView mTouchView;

    protected List<CHTableScrollView> mHScrollViews = new ArrayList<CHTableScrollView>();

    @ViewInject(R.id.item_scroll_title)
    private   CHTableScrollView headerScroll;

    @ViewInject(R.id.scroll_list)
    private ListView mListView;

    private ExchangeMileageBean userBean;

    private ScrollAdapter2 adapter;

    private IntegralExchangePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new IntegralExchangePresenter(this);

        initView();

        initData();
    }

    private void initData() {

        presenter.sendAirlineCompanyListRequest(this);

    }

    private void initView() {

        titleName.setText(R.string.integral_dui_li_cheng);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        UserBean userBean = UserManager.getInstance().getUserBean();

        if (userBean != null && userBean.getNickName() != null) {

            tvWelcome.setText(getString(R.string.integral_welcome_exchange_mileage).replace("###", userBean
                    .getNickName()));

        } else {
            tvWelcome.setText(getString(R.string.integral_welcome_exchange_mileage));
        }


    }

    /**
     * 右按键
     * @param v
     */
    public void rightAciton(View v) {

        Intent it = new Intent(this, IntegralFlightPlanActivity.class);

        startActivity(it);

    }


    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        for (CHTableScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView) scrollView.smoothScrollTo(l, t);
        }
    }

    /**
     * 处理里程信息
     *
     * @param bean
     */
    private void initViews(ExchangeMileageBean bean) {

        List<BankBean> bankList = bean.getBankList();

        List<AirlinesBean> airlinesList = bean.getAirlineList();

        //Table Title
        LinearLayout titleLinearLayout = (LinearLayout) this.findViewById(R.id.scrollLinearLayout);

        titleLinearLayout.removeAllViews();

        for (int i = 0; i < bankList.size(); i++) {

            BankBean temp = bankList.get(i);

            View linearLay = newView(R.layout.row_title_edit_view, "" + temp.bankId);

            TextView et = (TextView) linearLay.findViewById(R.id.tevEditView);

            et.setBackgroundResource(R.mipmap.icon_kuai_row_first);

            et.setText(temp.bankName);

            titleLinearLayout.addView(linearLay);
        }

        //添加头滑动事件
        mHScrollViews.add(headerScroll);

        int sizeI = airlinesList.size();

        int sizeJ = bankList.size();

        for (int i = 0; i < sizeI; i++) {

            AirlinesBean airlinesBean = airlinesList.get(i);

            airlinesBean.setSize(sizeJ);
        }

        adapter = new ScrollAdapter2(this, airlinesList, R.layout.row_item_edit);

        mListView.setAdapter(adapter);
    }

    public void addHViews(final CHTableScrollView hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CHTableScrollView scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {

        if( o != null && o instanceof  ExchangeMileageBean ){

            userBean = (ExchangeMileageBean) o;

            if (userBean != null && userBean.checkDataValue()) {

                initViews(userBean);

            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {

    }


    class ScrollAdapter2 extends BaseAdapter {

        private List<AirlinesBean> airlinesList;
        private int res;
        private Context context;

        private int currentIndex = -1;

        private int x = -1;

        private int size;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getCurrentIndex() {
            return currentIndex;
        }

        public void setCurrentIndex(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        public ScrollAdapter2(Context context, List<AirlinesBean> airlinesList, int resource) {
            this.context = context;
            this.airlinesList = airlinesList;
            this.res = resource;

            size = airlinesList.size();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return airlinesList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return airlinesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            View v = convertView;
            AirlinesBean airlinesBean = (AirlinesBean) getItem(position);

            int len = airlinesBean.getSize();

            ViewHold viewHold = null;
            if (v == null) {

                viewHold = new ViewHold();
                v = LayoutInflater.from(context).inflate(res, null);
                //第一次初始化的时候装进来
                viewHold.item_title = (TextView) v.findViewById(R.id.item_title);


                viewHold.tvExchange = (TextView) v.findViewById(R.id.tvExchange);


                viewHold.item_scroll = (CHTableScrollView) v.findViewById(R.id.item_scroll);

                viewHold.item_scroll_layout = (LinearLayout) v.findViewById(R.id.item_scroll_layout);

                viewHold.tvExchange.setOnClickListener(myClickListener);

                for (int c = 0; c < len; c++) {

                    View linearLay = newView(R.layout.row_item_edit_view, airlinesBean.getCompanyId());

                    TextView td = (TextView) linearLay.findViewById(R.id.ievEditView);

                    td.setOnClickListener(clickListener);

                    td.setTag(position + "#" + c);

                    if (position + 1 == size) {

                        td.setBackgroundResource(R.mipmap.icon_kuai_content_footer);
                    } else {

                        td.setBackgroundResource(R.mipmap.icon_kuai_content);
                    }

                    if (currentIndex == position) {

                        if (x != -1) {

                            if (x == c) {
                                if (position + 1 == size) {
                                    td.setBackgroundResource(R.mipmap.icon_kuai_content_footer_check);

                                } else {

                                    td.setBackgroundResource(R.mipmap.icon_kuai_content_check);
                                }

                            }

                        }

                    }

                    viewHold.item_scroll_layout.addView(linearLay);
                }

                v.setTag(viewHold);

                addHViews(viewHold.item_scroll);

            } else {

                viewHold = (ViewHold) v.getTag();

            }

            viewHold.item_title.setText(airlinesBean.getCompanyName());

            viewHold.tvExchange.setTag(airlinesBean.getCompanyId());

            int childCount = viewHold.item_scroll_layout.getChildCount();

            for (int c = 0; c < childCount; c++) {

                View linearLay = viewHold.item_scroll_layout.getChildAt(c);

                TextView td = (TextView) linearLay.findViewById(R.id.ievEditView);

                td.setOnClickListener(clickListener);

                td.setTag(position + "#" + c);

                if (position + 1 == size) {

                    td.setBackgroundResource(R.mipmap.icon_kuai_content_footer);
                } else {

                    td.setBackgroundResource(R.mipmap.icon_kuai_content);
                }

                if (currentIndex == position) {

                    if (x != -1) {

                        if (x == c) {
                            if (position + 1 == size) {
                                td.setBackgroundResource(R.mipmap.icon_kuai_content_footer_check);

                            } else {

                                td.setBackgroundResource(R.mipmap.icon_kuai_content_check);
                            }

                        }

                    }

                }

            }

            if (position + 1 == size) {

                viewHold.item_title.setBackgroundResource(R.mipmap.icon_kuai_name_footer);

            } else {
                viewHold.item_title.setBackgroundResource(R.mipmap.icon_kuai_name);

            }

            viewHold.item_title.setText(airlinesBean.getCompanyName());

            if (currentIndex == position) {

                viewHold.tvExchange.setBackgroundResource(R.mipmap.icon_kuai_duihuan_red);

                viewHold.tvExchange.setTextColor(getResources().getColor(R.color.white));

            } else {

                viewHold.tvExchange.setTextColor(getResources().getColor(R.color.integral_gray_afafaf));
                if (position + 1 == size) {


                    viewHold.tvExchange.setBackgroundResource(R.mipmap.icon_kuai_duihuan_footer);

                } else {

                    viewHold.tvExchange.setBackgroundResource(R.mipmap.icon_kuai_duihuan);

                }

            }

            return v;
        }

        class ViewHold {

            LinearLayout item_scroll_layout;

            TextView tvExchange, item_title;

            CHTableScrollView item_scroll;

        }
    }

    private View newView(int res_id, String tag_name) {

        View itemView = LayoutInflater.from(ExchangeMileageTableActivity.this).inflate(res_id, null);

        itemView.setTag(tag_name);

        return itemView;
    }

    //点击事件
    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String index = (String) v.getTag();

            String[] str = index.split("#");

            int position = Integer.parseInt(str[0]);

            int x = Integer.parseInt(str[1]);

            boolean flag = false;

            Map<Long, List<Long>> map = userBean.getRelationshipMap();

            Long tempLX = Long.parseLong(str[0]);
            Long tempLY = Long.parseLong(str[1]);

            //检测是否是平台
            if (tempLY == 0) {

                if (position == adapter.getCurrentIndex() && x == adapter.getX()) {

                    adapter.setCurrentIndex(-1);

                    adapter.setX(-1);

                } else {

                    adapter.setCurrentIndex(position);

                    adapter.setX(x);
                }
                adapter.notifyDataSetChanged();

            } else {

                //检查航空公司与银行的关系
                if (map.containsKey(tempLX)) {

                    List<Long> list = map.get(tempLX);

                    if (list.contains(tempLY)) {
                        flag = true;

                    }
                }

                if (flag) {

                    if (position == adapter.getCurrentIndex() && x == adapter.getX()) {

                        adapter.setCurrentIndex(-1);

                        adapter.setX(-1);

                    } else {

                        adapter.setCurrentIndex(position);

                        adapter.setX(x);
                    }
                    adapter.notifyDataSetChanged();
                    ;
                } else {

                    ToastUtil.i(ExchangeMileageTableActivity.this, getString(R.string.jbf_without_plan));
                }
            }
        }
    };

    /**
     * 兑换积分监听
     */
    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (adapter.getCurrentIndex() != -1) {

                String airlineId = (String) v.getTag();
                Intent oneIt = new Intent(ExchangeMileageTableActivity.this, ExchangeMileageActivity.class);

                oneIt.putExtra("airlineId", airlineId);

                startActivity(oneIt);
            }else{

                ToastUtil.i(ExchangeMileageTableActivity.this,getString(R.string.jfb_select_exchange_mileage));
            }

        }
    };
}
