package com.yzb.card.king.ui.my.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.ui.my.holder.AmountLimitHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.comm.PayMethodLimitLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：限额说明
 * 作者：殷曙光
 * 日期：2016/12/27 19:57
 */
@ContentView(R.layout.activity_limit_instruction)
public class LimitInstructionActivity extends BaseActivity
{

    @ViewInject(R.id.listView)
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<PayMethod> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        listView.addFooterView(UiUtils.inflate(R.layout.footer_limit_amount));
    }

    private void initData()
    {
        setDefaultHeader("限额说明");
        setRightImage(R.mipmap.limit_question, null);
        setAdapter();
        loadData();
    }


    private void loadData()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);

        PayMethodLimitLogic payHandle = new PayMethodLimitLogic(this);

        // 显示/隐藏 现金账户
        payHandle.showBalancePay(true);
        // 显示/隐藏 红包账户
        payHandle.showEnvelopPay(false);
        // 显示/隐藏 礼品卡账户
        payHandle.showGiftPay(false);
        // 显示/隐藏 积分账户
        payHandle.showIntegralPay(false);
        // 显示/隐藏 借记卡
        payHandle.showDebitCard(true);
        // 显示/隐藏 信用卡
        payHandle.showCreditCard(false);

        payHandle.payMethod(params);

        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                System.out.println("=返回结果=>code" + RESULT_CODE);
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                LogUtil.e("=返回结果=>code" + RESULT_CODE + "返回数据=>" + resultMap);
                dataList.clear();
                dataList.addAll(JSON.parseArray(resultMap.get("data"),PayMethod.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                LogUtil.e("=返回结果=>code" + RESULT_CODE + "错误提示：" + ERROR_MSG);
                UiUtils.shortToast(ERROR_MSG);
                dataList.clear();
                adapter.notifyDataSetChanged(); }
        });
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<PayMethod>(dataList)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor(getBackgroundColor(position));
                return view;
            }

            @Override
            protected Holder getHolder(int position)
            {
                AmountLimitHolder holder = new AmountLimitHolder();
                return holder;
            }

            private int getBackgroundColor(int position)
            {
                if (position == 0)
                {
                    return UiUtils.getColor(R.color.white);
                } else
                {
                    return UiUtils.getColor(R.color.transparent);
                }
            }
        };
        listView.setAdapter(adapter);
    }
}
