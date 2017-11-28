package com.yzb.card.king.ui.credit.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.http.HttpCallBackData;
import com.yzb.card.king.http.HttpConstant;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.appdialog.PayMentDialog;
import com.yzb.card.king.ui.appwidget.popup.LeftPopWindow;
import com.yzb.card.king.ui.base.BaseFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.activity.AdjustLimitActivity;
import com.yzb.card.king.ui.credit.activity.CardManageActivity;
import com.yzb.card.king.ui.credit.activity.ContactBankActivity;
import com.yzb.card.king.ui.credit.activity.CreditOnlineCardActivity;
import com.yzb.card.king.ui.credit.activity.RepaymentActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.holder.CardHolder;
import com.yzb.card.king.ui.credit.interfaces.ICardHolder;
import com.yzb.card.king.ui.credit.presenter.CreditIndexPresenter;
import com.yzb.card.king.ui.manage.CreditCardManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.activity.AddCardAllActivity;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.SwipeRefreshSettings;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 信用卡首页
 */
@ContentView(R.layout.fragment_credit_index)
public class CreditIndexFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , BaseViewLayerInterface, ICardHolder<CreditCard>
{
    private static final int REQ_LOGIN = 0;
    private static final int REQ_NEXT_PAGE = 1;
    private static final int ADD_CARD = 2;
    private static final int REQ_BIND_CARD = 3;

    private Boolean isSuccess;

    private AbsBaseListAdapter creditIndexAdapter;
    @ViewInject(R.id.listView_credit_index)
    private ListView listView;
    @ViewInject(R.id.sRL)
    private SwipeRefreshLayout sRL;
    @ViewInject(R.id.llNoCard)
    private LinearLayout llNoCard;

    @ViewInject(R.id.header)
    private View header;

    //数据
    private int startPage = 0;

    private LeftPopWindow letfWindow;

    private CreditIndexPresenter creditIndexPresenter;

    private List<CreditCard> dataList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setTranslucentStatus(true, R.color.stateWhite);

        creditIndexPresenter = new CreditIndexPresenter(this, this);

        //加载数据
        triggerEvent();

        initData();

    }

    private void initData()
    {
        startPage = 0;
        creditIndexPresenter.queryUserBindCard();
    }

    @Event(R.id.llFinanceLife)
    private void financeLife(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putString("url", ServiceDispatcher.URL_JINRONG_LIFE );
        bundle.putString(WebViewClientActivity.TITLE_NAME, "金融生活");
        readyGoWithBundle(getActivity(), WebViewClientActivity.class, bundle);
    }

    @Event(R.id.headerLeft)
    private void llLinkBankAction(View v)
    {
        if (UserManager.getInstance().getUserBean() == null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getContext(), ContactBankActivity.class);
        startActivity(intent);
    }

    @Event(R.id.llCallback)
    private void llCallbackAction(View v)
    {
        if (UserManager.getInstance().getUserBean() == null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getActivity(), RepaymentActivity.class));
    }


    @Event(R.id.llOnline)
    private void llOnlineAction(View v)
    {
        Intent intent = new Intent();
        intent.setClass(getContext(), CreditOnlineCardActivity.class);
        startActivity(intent);
    }

    @Event(R.id.llAddCard)
    private void llAddCardAction(View view)
    {
        if (UserManager.getInstance().getUserBean() == null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }

        Intent intent = new Intent(getContext(), AddCardAllActivity.class);
        intent.putExtra(AddCardAllActivity.BUSINESS_ADD_CARD,AddCardAllActivity.ALL_BUSINESS_VALUE);
        startActivityForResult(intent , ADD_CARD);

    }

    @Event(R.id.llEduTiaozheng)
    private void llEduTiaozhengAction(View view)
    {
        if (!UserManager.getInstance().isLogin())
        {

            startActivity(new Intent(getActivity(), LoginActivity.class));

            return;

        }
        Intent intent = new Intent();
        intent.setClass(getContext(), AdjustLimitActivity.class);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (resultCode == getActivity().RESULT_OK)
        {
            switch (requestCode)
            {
                case ADD_CARD:
                    onRefresh();
                    break;
                case REQ_BIND_CARD:
                    onRefresh();
                    break;
            }
        }
        if (data == null)
        {
            isSuccess = false;
        } else
        {
            isSuccess = data.getBooleanExtra("isSuccess", false);
        }

        if (resultCode == AppConstant.RES_HOME_PAGE)
        {

            if (requestCode == AppConstant.REQ_ADD_CREDIT_CARD && isSuccess == true)
            {

                ToastUtil.i(getActivity(),    getResources().getString(R.string.credit_add_succ));
                // 加載信息和触发事件  调用接口数据
                triggerEvent();
            }

            if (requestCode == AppConstant.REQ_REPAYMENT && isSuccess == true)
            {

                ToastUtil.i(getActivity(),    getResources().getString(R.string.credit_yet_hk));
                // 加載信息和触发事件  调用接口数据
                triggerEvent();
            }

            if (requestCode == AppConstant.REQ_ORDER_DETAIL)
            {
                startPage = 0;
                creditIndexPresenter.queryUserBindCard();
                if (isSuccess)

                ToastUtil.i(getActivity(),    getResources().getString(R.string.credit_hk_succ));
            }
        }

        if (requestCode == REQ_NEXT_PAGE)
        {
            onRefresh();
        }

    }


    @Event(R.id.headerRight)
    private void headerRightAction(View v)
    {
        if (UserManager.getInstance().getUserBean() == null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }

        if (letfWindow == null)
        {
            letfWindow = new LeftPopWindow(getActivity());
        }

        letfWindow.showAsDropDown(header);
    }

    //加载数据
    private void triggerEvent()
    {

        creditIndexAdapter = new AbsBaseListAdapter<CreditCard>(dataList)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new CardHolder(CreditIndexFragment.this);
            }
        };

        listView.setAdapter(creditIndexAdapter);

        SwipeRefreshSettings.setAttrbutes(getActivity(), sRL);

        sRL.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh()
    {
        startPage = 0;
        creditIndexPresenter.queryUserBindCard();
    }

    @Override
    public void onResume()
    {

        super.onResume();

        if (CreditCardManager.getInstance().isIfRefresh())
        {
            CreditCardManager.getInstance().setIfRefresh(false);

            startPage = 0;

            creditIndexPresenter.queryUserBindCard();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        creditIndexPresenter = null;
    }


    private void showNoDataView()
    {
        listView.setVisibility(View.GONE);
        llNoCard.setVisibility(View.VISIBLE);
    }

    private void showListView()
    {
        listView.setVisibility(View.VISIBLE);
        llNoCard.setVisibility(View.GONE);
    }

    @Override
    public void goNextPage(CreditCard data)
    {
        Intent intent = new Intent(getContext(), RepaymentActivity.class);
        intent.putExtra("data", data);
        startActivityForResult(intent, REQ_NEXT_PAGE);
    }

    @Override
    public void bindCard(CreditCard data) {

        Intent intent = new Intent(getContext(), AddCanPayCardActivity.class);
        intent.putExtra("cardNo", data.getCardNo());
        intent.putExtra("name", data.getUserName());
        startActivityForResult(intent, REQ_BIND_CARD);
    }

    @Override
    public void unBindCard(final CreditCard data) {
        final PayMentDialog.Builder builder = new PayMentDialog.Builder(getContext());

        builder.setTitle("您是否确定解绑？");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                unbundling(data);
                builder.dismiss();
            }
        });
        builder.create().show();
    }
    /**
     * 解绑
     */
    private void unbundling(CreditCard data)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("creditId", data.getId());
        new SimpleRequest(CardConstant.CREDIT_UNBUND, param).sendRequest(new HttpCallBackData() {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(Object o)
            {
                startPage = 0;
                creditIndexPresenter.queryUserBindCard();
            }

            @Override
            public void onFailed(Object o)
            {
                if (o != null && o instanceof Map)
                {
                    Map<String, String> onSuccessData = (Map<String, String>) o;

                    if (onSuccessData.get(HttpConstant.SERVER_CODE).equals("0101"))
                    {
                        ToastUtil.i(GlobalApp.getInstance().getContext(),
                                "解绑失败");
                    }

                }
            }

            @Override
            public void onCancelled(Object o)
            {

            }

            @Override
            public void onFinished()
            {

            }
        });
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        sRL.setRefreshing(false);
        List<CreditCard> list = (List<CreditCard>) o;
        if (list.size() > 0)
        {
            dataList.clear();
            dataList.addAll(list);
            creditIndexAdapter.notifyDataSetChanged();
            showListView();
        } else
        {
            showNoDataView();
        }
        if (startPage == 0)
        {
            listView.setSelection(0);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        sRL.setRefreshing(false);
        dataList.clear();
        creditIndexAdapter.notifyDataSetChanged();
        showNoDataView();
    }
}
