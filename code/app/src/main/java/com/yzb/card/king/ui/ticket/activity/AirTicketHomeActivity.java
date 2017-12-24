package com.yzb.card.king.ui.ticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.flyco.tablayout.SegmentTabLayout;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.AppFactory;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.ui.appwidget.DefindTabView;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.ChannelTypePopup;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelHomeActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.order.OrderManageActivity;
import com.yzb.card.king.ui.ticket.fragment.LowPriceTicketFragment;
import com.yzb.card.king.ui.ticket.fragment.TicketHomeFragment;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：机票首页
 * 作  者：Li Yubing
 * 日  期：2017/9/19
 * 描  述：
 */
@ContentView(R.layout.activity_air_ticket_home)
public class AirTicketHomeActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.titleTab)
    private SegmentTabLayout titleTab;

    //底部控件
    @ViewInject(R.id.llBottomTab)
    private LinearLayout llBottomTab;

    private String[] mTitleTab = {"商务机票", "低价机票"};

    private ArrayList<Fragment> pagerList = new ArrayList<>();

    private ChannelTypePopup channelTypePopup;

    private int selectedIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalVariable.industryId = Integer.parseInt(AppConstant.ticket_id);
        initView();
    }

    private void initView() {

        initTopTitleView();

        initBoyView();

        initBottom();
    }

    private void initBoyView() {
        pagerList.clear();
        //商务机票
        pagerList.add(TicketHomeFragment.newInstance());
        //低价机票
        pagerList.add(LowPriceTicketFragment.newInstance());

        titleTab.setTabData(mTitleTab, this, R.id.fl_change, pagerList);

    }

    private void initTopTitleView() {

        titleTab.setTabData(mTitleTab);

        findViewById(R.id.llRight).setOnClickListener(this);
    }


    public TicketHomeFragment getTicketHomeFragment() {
        return (TicketHomeFragment) pagerList.get(0);
    }

    public LowPriceTicketFragment getLowPriceTicketFragment() {
        return (LowPriceTicketFragment) pagerList.get(1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePrefUtil.saveToSp(this, "singleline"+ "-filter-company", "");
    }

    private List<DefindTabView> defindTabViewList = new ArrayList<DefindTabView>();

    //初始化底部
    private void initBottom() {

        for (int i = 0; i < 4; i++) {

            DefindTabView defindTabView = new DefindTabView(this, tabOnClick);

            int[] textColor = new int[]{R.color.color_b5b5b5, R.color.color_d7bf7f};

            defindTabView.setTextColor(textColor);

            if (i == 0) {//

                int[] drawable0 = new int[]{R.mipmap.icon_tab_home_ticket_bottom_one, R.mipmap.icon_tab_home_ticket_bottom_one_yellow};
                defindTabView.setViewData(R.string.home_ticket_tab_one, drawable0);

            } else if (i == 1) {
                defindTabView.setEnable(false);
                int[] drawable1 = new int[]{R.mipmap.icon_tab_home_ticket_bottom_two, R.mipmap.icon_tab_home_ticket_bottom_two};//icon_tab_home_ticket_bottom_two_yellow
                defindTabView.setViewData(R.string.home_ticket_tab_two, drawable1);

            } else if (i == 2) {//出发时间
                int[] drawable2 = new int[]{R.mipmap.icon_tab_home_ticket_bottom_three, R.mipmap.icon_tab_home_ticket_bottom_three};//icon_tab_home_ticket_bottom_three_yellow
                defindTabView.setViewData(R.string.home_ticket_tab_three, drawable2);
                defindTabView.setEnable(false);
            } else if (i == 3) {//排序
                int[] drawable3 = new int[]{R.mipmap.icon_tab_home_ticket_bottom_four, R.mipmap.icon_tab_home_ticket_bottom_four_yellow};
                defindTabView.setViewData(R.string.home_ticket_tab_four, drawable3);
            }
            defindTabView.addTabToLL(llBottomTab, i);
            defindTabViewList.add(defindTabView);
        }
    }

    /**
     * 底部tab监听事件
     */
    private DefindTabView.OnClickAction tabOnClick = new DefindTabView.OnClickAction() {
        @Override
        public void onTabClickItem(int index, TextView textView, boolean selectedStatus) {

            if (index == 0) {//航班动态

                Intent iPlane = new Intent(AirTicketHomeActivity.this, PlaneSeachActivity.class);
                startActivity(iPlane);

            } else if (index == 1) {//值机选座

                ToastUtil.i(AirTicketHomeActivity.this, "敬请期待");

            } else if (index == 2) {//常旅客卡

                ToastUtil.i(AirTicketHomeActivity.this, "敬请期待");

            } else if (index == 3) {//订单管理

                //检测
                if (!UserManager.getInstance().isLogin()) {
                    new GoLoginDailog(AirTicketHomeActivity.this).show();
                    return;
                }
                Intent intent1 = new Intent(AirTicketHomeActivity.this, OrderManageActivity.class);
                intent1.putExtra("orderType", OrderBean.ORDER_TYPE_AIRCRAFT);
                startActivity(intent1);
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llRight:

                if (channelTypePopup == null) {

                    channelTypePopup = new ChannelTypePopup(this, -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

                    channelTypePopup.setSelectIndex(selectedIndex);

                    channelTypePopup.setCallBack(dataCallBackChannelType);

                }

                View rootView = getWindow().getDecorView();

                channelTypePopup.showBottomByViewPP(rootView);

                break;

            default:
                break;

        }
    }

    /**
     * app频道选择回调数据
     */
    private ChannelTypePopup.BottomDataListCallBack dataCallBackChannelType = new ChannelTypePopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {

            if (selectIndex == 0) {//酒店

                readyGo(AirTicketHomeActivity.this, HotelHomeActivity.class);

                finish();

            } else if (selectIndex == 2) {//旅游

                String childTypeJson = SharePrefUtil.getValueFromSp(AirTicketHomeActivity.this,
                        AppConstant.sp_childtypelist_home, "[]");

                List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);

                ChildTypeBean selectedBean = null;

                String typeId = AppConstant.travel_id;

                for (ChildTypeBean bean : childTypeBeans) {

                    if (bean.id.equals(typeId)) {

                        selectedBean = bean;

                        break;
                    }

                }


                if (selectedBean != null) {

                    int index = AppFactory.channelIdToFragmentIndex(typeId);

                    GlobalVariable.industryId = Integer.parseInt(typeId);

                    Intent intent = new Intent();

                    intent.setClass(AirTicketHomeActivity.this, ChannelMainActivity.class);

                    intent.putExtra("pagetype", index);

                    intent.putExtra("data", selectedBean);

                    startActivity(intent);

                    finish();
                }

            }

        }
    };

}
