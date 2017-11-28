package com.yzb.card.king.ui.my.pop;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.ui.my.holder.PayeePopHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/27 17:38
 */
public class AccountListPop extends PopupWindow
{
    private View mRoot;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<Payee> payeeList = new ArrayList<>();
    private String searchStr = "";

    public AccountListPop()
    {
        initView();
        initListener();
        initData();
        init();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.pop_account_list);
        listView = (ListView) mRoot.findViewById(R.id.listView);
    }

    private void initListener()
    {

    }

    private void initData()
    {
        setAdapter();
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<Payee>(payeeList)
        {
            @Override
            protected Holder getHolder(final int position)
            {
                PayeePopHolder holder = new PayeePopHolder(AccountListPop.this);
                holder.setListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (onOneResult != null)
                        {
                            onOneResult.onSelected(payeeList.get(position));
                            dismiss();
                        }
                    }
                });
                return holder;
            }
        };
        listView.setAdapter(adapter);
    }

    private void init()
    {
        setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setTouchable(true);
        setFocusable(false);
        setOutsideTouchable(true);
    }

    public void setData(String s)
    {
        this.searchStr = s;
        payeeList.clear();
        adapter.notifyDataSetChanged();
        sendRequest();
    }

    private void sendRequest()
    {
        SimpleRequest<List<Payee>> simpleRequest
                = new SimpleRequest<List<Payee>>(CardConstant.USER_QUERYPLATFORMACCOUNT)
        {
            @Override
            protected List<Payee> parseData(String data)
            {
                return JSON.parseArray(data, Payee.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("searchName", searchStr);
        param.put("customerType", "P");
        param.put("customerMod", "2");
        param.put("pageStart", 0);
        param.put("pageSize", 20);
        simpleRequest.setParam(param);
        simpleRequest.sendRequestNew(new BaseCallBack<List<Payee>>()
        {
            @Override
            public void onSuccess(List<Payee> data)
            {
                payeeList.clear();
                payeeList.addAll(data);
                adapter.notifyDataSetChanged();
                if(onOneResult!=null)
                if(payeeList.size()==1){
                    onOneResult.onOneResult(payeeList.get(0));
                }else {
                    onOneResult.onClear();
                }
            }

            @Override
            public void onFail(Error error)
            {
                payeeList.clear();
                adapter.notifyDataSetChanged();
                if(onOneResult!=null)onOneResult.onClear();
            }
        });
    }

    public int getSearchLength()
    {
      return  searchStr.length();
    }

    private OnOneResultListener onOneResult;

    public void setListener(OnOneResultListener listener) {
        this.onOneResult = listener;
    }

    public interface OnOneResultListener{
        void onSelected(Payee payee);
        void onOneResult(Payee payee);
        void onClear();
    }
}
