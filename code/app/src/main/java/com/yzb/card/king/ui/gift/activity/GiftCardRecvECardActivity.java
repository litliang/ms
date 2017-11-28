package com.yzb.card.king.ui.gift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.NoRecvCardBean;
import com.yzb.card.king.ui.appwidget.LoadMoreListView;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.gift.adapter.ECardsAdapter;
import com.yzb.card.king.ui.gift.dialog.GiftcardRecvSucesDialog;
import com.yzb.card.king.ui.gift.presenter.GiftcardNoRecvPresenter;
import com.yzb.card.king.ui.gift.presenter.RecvCardPresenter;
import com.yzb.card.king.ui.gift.view.GiftcardNoRecvView;
import com.yzb.card.king.ui.gift.view.RecvCardView;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SwipeRefreshSettings;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 我的-->礼品卡-未领取礼品卡列表；
 *
 * @author gengqiyun
 * @date 2016.12.15
 */
@ContentView(R.layout.activity_giftcard_recv_ecard)
public class GiftCardRecvECardActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, GiftcardNoRecvView, LoadMoreListView.OnLoadMoreListener, RecvCardView {
    @ViewInject(R.id.scrollView)
    private ScrollView scrollView;
    @ViewInject(R.id.swipeRefresh)
    private SwipeRefreshLayout swipeRefresh;
    @ViewInject(R.id.tvEmptyView)
    private TextView tvEmptyView;
    @ViewInject(R.id.listView)
    private LoadMoreListView listView;

    private ECardsAdapter adapter;
    private GiftcardNoRecvPresenter listPresenter;
    private RecvCardPresenter recvCardPresenter;
    private String pushCodeNo;//推送下来的recordId
    private String selectOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        listPresenter = new GiftcardNoRecvPresenter(this);
        recvCardPresenter = new RecvCardPresenter(this);
        recvInentData();
        initView();

        if (isEmpty(pushCodeNo)) {
            loadData(true);
        } else {
            //来自于推送；
            exeRecvGiftcard(pushCodeNo);
        }
    }

    private void initView() {
        setHeader(R.mipmap.icon_back_white, getString(R.string.giftcard_recv));
        findViewById(R.id.headerLeft).setOnClickListener(this);
        SwipeRefreshSettings.setAttrbutes(this, swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        scrollView.smoothScrollTo(0, 0);
        listView.setFocusable(false);
        listView.setCanLoadMore(true);
        listView.setOnLoadMoreListener(this);

        adapter = new ECardsAdapter(this);
        adapter.setDataHandler(dataHandler);
        listView.setAdapter(adapter);

        tvEmptyView.setVisibility(View.GONE);
    }

    private Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case ECardsAdapter.WHAT_COLLECT: //领取操作；
                    exeRecvGiftcard(adapter.getItem(msg.arg1).getOrderId());
                    break;
            }
            return false;
        }
    });

    /**
     * 領取礼品卡；
     *
     * @param orderId
     */
    private void exeRecvGiftcard(String orderId) {
        this.selectOrderId = orderId;
        showNoCancelPDialog(R.string.loading);
        recvCardPresenter.setInterfaceParameters( getUserBean().getAccount(),orderId);
    }

    public void recvInentData() {
        pushCodeNo = getIntent().getStringExtra("id");
        LogUtil.i("pushCodeNo=" + pushCodeNo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeft:  //左侧点击；
                finish();
                break;
            case R.id.panelPhysicalCard: //心意实体卡；
                break;
            case R.id.panelWishCard: //心意e卡；
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    private void loadData(final boolean isRefresh) {
        dataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadListData(isRefresh);
            }
        }, 100);
    }

    private void loadListData(boolean isRefresh) {
        adapter.clearAll();
        listPresenter.setInterfaceParameters(isRefresh,isRefresh ? "0" : adapter.getCount() + "");
    }

    @Override
    public void onGetECardListSuc(boolean event_tag, List<NoRecvCardBean> list) {
        swipeRefresh.setRefreshing(false);
        listView.onLoadMoreComplete();
        if (event_tag) {
            adapter.clearAll();
        }
        adapter.appendALL(list);

        tvEmptyView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        listView.setVisibility(adapter.getCount() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onGetECardListFail(String failMsg) {
        swipeRefresh.setRefreshing(false);
        listView.onLoadMoreComplete();

        tvEmptyView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        listView.setVisibility(adapter.getCount() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onLoadMore() {
        loadListData(false);
    }

    @Override
    public void onRecvCardSuc(String data) {
        closePDialog();
        String orderAmount = null;
        if (data.contains(",")) {
            String[] array = data.split(",");
            String orderId = array[0];
            orderAmount = array[1];
            adapter.removeBeanByOrderId(orderId);
        }
        loadListData(true);

        final GiftcardRecvSucesDialog dialog = GiftcardRecvSucesDialog.getInstance();
        dialog.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiftCardRecvECardActivity.this, GiftCardUseDetailActivity.class);
                intent.putExtra("orderId", selectOrderId);
                startActivity(intent);
            }
        }).setAmount(orderAmount).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onRecvCardFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }
}
