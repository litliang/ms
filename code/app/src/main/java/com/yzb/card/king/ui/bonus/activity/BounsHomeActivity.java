package com.yzb.card.king.ui.bonus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.AccountInfoBean;
import com.yzb.card.king.ui.bonus.bean.BounsBean;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.bean.my.CouponChannelBean;
import com.yzb.card.king.ui.appwidget.SpecHeightGridView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.bonus.adapter.BounsHomeThemesAdapter;
import com.yzb.card.king.ui.bonus.presenter.BounsListPresenter;
import com.yzb.card.king.ui.bonus.view.BounsListView;
import com.yzb.card.king.ui.gift.adapter.GiftcardChannelAdapger;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.presenter.AccountInfoPresenter;
import com.yzb.card.king.ui.bonus.presenter.BounsThemePresenter;
import com.yzb.card.king.ui.bonus.view.BounsThemeView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的-->发红包首页；
 *
 * @author gengqiyun
 * @date 2016.12.12
 */
@ContentView(R.layout.activity_bouns_home)
public class BounsHomeActivity extends BaseActivity implements  BounsThemeView, BaseViewLayerInterface,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, BounsListView {

    @ViewInject(R.id.tvRestAmount)
    private TextView tvRestAmount;

    @ViewInject(R.id.tvYestodayIncome)
    private TextView tvYestodayIncome;

    @ViewInject(R.id.tvYearRate)
    private TextView tvYearRate;

    @ViewInject(R.id.tvTotalIncome)
    private TextView tvTotalIncome;

    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;

    @ViewInject(R.id.gvRedPackets)
    private SpecHeightGridView gvRedPackets;

    @ViewInject(R.id.gvChannels)
    private SpecHeightGridView gvChannels;

    private BounsHomeThemesAdapter homeAdapter;

    private BounsThemePresenter themePresenter;

    private AccountInfoPresenter accountInfoP;

    private AccountInfoBean accountInfoBean; //红包信息；

    private GiftcardChannelAdapger channelAdapger;

    private BounsListPresenter listPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        themePresenter = new BounsThemePresenter(this);
        accountInfoP = new AccountInfoPresenter(this);
        listPresenter = new BounsListPresenter(this);
        initView();
        loadData(true);
    }

    private void initView()
    {

        setTitleNmae("发红包");

        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);

        swipeRefresh.setOnRefreshListener(this);

        homeAdapter = new BounsHomeThemesAdapter(this);

        gvRedPackets.setOnItemClickListener(this);

        gvRedPackets.setAdapter(homeAdapter);

        channelAdapger = new GiftcardChannelAdapger(this);

        channelAdapger.setHandler(dataHandler);

        gvChannels.setAdapter(channelAdapger);

        channelAdapger.appendALL(getBounsHomeChannels());

        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BounsThemeActivity.class);

                intent.putExtra(BounsThemeActivity.INTENT_FLAG, getClass().getName());

//                intent.putExtra("bounsParam", getBounsThemeParam());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        loadData(true);
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what) {
                case GiftcardChannelAdapger.WHAT_CHANNEL_CLICK:
                    targetChannel(msg.arg1);
                    break;
                case BounsHomeThemesAdapter.WHAT_ITEM_CLICK:
                    homeAdapter.getData().get(msg.arg1);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onResume()
    {
        super.onResume();

        loadAccountInfo();

        listPresenter.sendRedEnvelopeRequest();
    }

    @Override
    public void onRefresh()
    {
        loadData(true);
    }

    private List<CouponChannelBean> getBounsHomeChannels()
    {
        int[] imageResIds = {R.mipmap.icon_send_redpacket, R.mipmap.icon_recv_redpacket,
                R.mipmap.icon_withdraw_redpacket, R.mipmap.icon_record_redpacket};
        String[] labelResIds = getResources().getStringArray(R.array.coupons_home_channels);
        List<CouponChannelBean> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CouponChannelBean bean = new CouponChannelBean();
            bean.setImgResId(imageResIds[i]);
            bean.setText(labelResIds[i]);
            bean.setShowNumber(false);
            data.add(bean);
        }
        return data;
    }

    /**
     * 频道跳转；
     */
    private void targetChannel(int pos)
    {
        switch (pos) {

            case 0: //发红包

                readyGo(this, BounsCreateActivity.class);

                break;
            case 1://领取红包

                readyGoForResult(this, BounsListActivity.class,1000);

                break;
            case 2://提现
                if (accountInfoBean != null) {
                    Intent intent = new Intent(this, BounsCashActivity.class);
                    intent.putExtra("amount", String.format("%.2f", accountInfoBean.getPersonBonusBalance()));
                    startActivity(intent);
                } else {
                    toastCustom("获取红包余额失败，刷新试试");
                }
                break;
            case 3:   //红包记录；
                readyGo(this, RedSendAndRecActivity.class);
                break;
        }
    }


    private void loadData(final boolean isRefresh)
    {
        dataHandler.postDelayed(new Runnable() {
            @Override
            public void run()
            {

                loadAccountInfo();

                loadListData();

                //请求未领取红包数量

                listPresenter.sendRedEnvelopeRequest();

            }
        }, 150);
    }


    private void loadListData()
    {
        Map<String, Object> args = new HashMap<>();

        args.put("sort", "1"); //1、随机   、2最新（创建时间倒序）

        args.put("pageStart", "0");

        args.put("pageSize", "4");

        themePresenter.loadData(args);
    }

    /**
     * 获取红包信息；
     */
    private void loadAccountInfo()
    {
        Map<String, Object> args = new HashMap<>();
        args.put("amountAccount", getUserBean().getAmountAccount());
        args.put("personBonusStatus", "1"); //查询个人红包余额
        args.put("bonusPreProfitStatus", "1"); //查询红包余额昨日收益
        args.put("bonusAnnualRateStaus", "1"); //查询红包余额年化率
        args.put("bonusTotalProfitStatus", "1"); //查询红包余额累计收益
        accountInfoP.loadData(args);
    }

    @Override
    public void onGetBounsThemeSuc(boolean event_tag, List<BounsThemeBean> list)
    {
        swipeRefresh.setRefreshing(false);

        if (event_tag) {

            homeAdapter.clearAll();
        }
        homeAdapter.appendALL(list);
    }

    @Override
    public void onGetBounsThemeFail(String failMsg)
    {
        swipeRefresh.setRefreshing(false);

        toastCustom(failMsg);
    }

    private void initAccountInfo(AccountInfoBean accountInfoBean)
    {
        this.accountInfoBean = accountInfoBean;

        tvRestAmount.setText(String.format("%.2f", accountInfoBean.getPersonBonusBalance()));

        tvYestodayIncome.setText("+" + String.format("%.2f", accountInfoBean.getBonusPreProfit()));

        tvYearRate.setText(String.format("%.1f", accountInfoBean.getBonusAnnualRateBalance()) + "%");

        tvTotalIncome.setText("+" + String.format("%.2f", accountInfoBean.getBonusTotalProfit()));
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, BounsCreateActivity.class);

        BounsThemeBean themeBean = homeAdapter.getItem(position);

        intent.putExtra("themeBean", themeBean);

        startActivity(intent);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        AccountInfoBean accountInfoBean = (AccountInfoBean) o;

        initAccountInfo(accountInfoBean);
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ToastUtil.i(this, "" + o);
    }


    @Override
    public void onGetBounsListSuc(boolean event_tag, List<BounsBean> list)
    {
        List<BounsBean> redEnvelepoList = list;

        int size = redEnvelepoList.size();

        channelAdapger.setRedEnvelopeNumber(size);

        UserManager.getInstance().setRedEnvelepoList(redEnvelepoList);
    }

    @Override
    public void onGetBounsListFail(String failMsg)
    {
        channelAdapger.setRedEnvelopeNumber(0);
        UserManager.getInstance().setRedEnvelepoList(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        LogUtil.e("ABCD","resultCode="+resultCode+"   requestCode="+requestCode);
        if(requestCode== 1000 && resultCode == Activity.RESULT_OK){
            //请求未领取红包数量
            listPresenter.sendRedEnvelopeRequest();
        }

    }
}
