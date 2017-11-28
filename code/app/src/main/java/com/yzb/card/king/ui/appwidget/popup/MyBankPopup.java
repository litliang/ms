package com.yzb.card.king.ui.appwidget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.adapter.YhkGvAdapter2;
import com.yzb.card.king.ui.discount.adapter.VpBaseAdapter;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/6/7 20:05
 */
public class MyBankPopup extends PopupWindow implements View.OnClickListener
{
    private Context context;
    private View mRoot;
    private VpBaseAdapter vpBaseAdapter;
    protected List<BankBean> myBanks; //我的银行；
    protected List<BankBean> allBanks;//所有银行；
    protected String bankId = "";//银行id；
    protected Map<String, String> param = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View gray_part;
    private String bankName;

    public MyBankPopup(Context context)
    {
        super(context);
        this.context = context;
        initView();
        initData();
        init();
    }

    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }

    private void initView()
    {
        mRoot = View.inflate(context, R.layout.popwindow_content_yhk, null);
        tabLayout = (TabLayout) mRoot.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) mRoot.findViewById(R.id.viewPager);
        gray_part = mRoot.findViewById(R.id.gray_part);
        gray_part.setOnClickListener(this);
    }

    private void initData()
    {
        myBanks = new ArrayList<>();
        allBanks = new ArrayList<>();
        vpBaseAdapter = new VpBaseAdapter();

        if (allBanks != null && allBanks.size() > 0)
        {
            setAdapter();
        } else
        {
            getYhkData();
        }
    }

    private void setAdapter()
    {
        List<View> viewList = new ArrayList<>();
        viewList.add(getVpItemView(new YhkGvAdapter2(context), myBanks));
        viewList.add(getVpItemView(new YhkGvAdapter2(context), allBanks));
        vpBaseAdapter.setViewList(viewList);
        viewPager.setAdapter(vpBaseAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // 没有绑定银行卡，显示全部银行；
        if (myBanks == null || myBanks.size() == 0)
        {
            viewPager.setCurrentItem(1);
        } else
        {
            viewPager.setCurrentItem(0);
        }
    }

    /**
     * 获取ViewPager的每项布局；
     *
     * @param adapter
     * @return
     */
    public View getVpItemView(final YhkGvAdapter2 adapter, List<BankBean> list)
    {
        View view = View.inflate(context, R.layout.fragment_popup_yhk, null);
        final GridView gridView = (GridView) view.findViewById(R.id.gridview);

        adapter.appendDataList(list);
        adapter.setCurBankId(bankId);
        gridView.setAdapter(adapter);

        adapter.setCallBack(new YhkGvAdapter2.IDataCallBack()
        {
            @Override
            public void callBack(int position)
            {
                BankBean bankBean = adapter.getItem(position);
                bankId = bankBean.id + "";
                bankName = bankBean.bankName;
                if (listener != null)
                {
                    listener.onBankClick(bankId, bankName);
                } else
                {
                    LogUtil.e("没设置点击银行的监听事件");
                }
                dismiss();
            }
        });
        return view;
    }


    /**
     * 获取我的银行卡，所有银行卡数据；
     */
    private void getYhkData()
    {
        if (myBanks == null || myBanks.size() == 0)
        {
            getBank();
        }
    }

    private void getBank()
    {

//        SimpleRequest<List<Payee>> request = new SimpleRequest<List<Payee>>(CardConstant.card_app_banklist)
//        {
//            @Override
//            protected List<Payee> parseData(String data)
//            {
//                return JSON.parseArray(data, Payee.class);
//            }
//        };
//         Map<String, Object> params = new HashMap<>();
//
//        request.setParam(params);
//        request.sendRequestNew(new BaseCallBack<List<Payee>>()
//        {
//            @Override
//            public void onSuccess(List<Payee> data)
//            {
//            }
//
//            @Override
//            public void onFail(Error error)
//            {
//            }
//        });
//
//        new AsyncTask<String, Void, Map<String, String>>()
//        {
//
//            protected Map<String, String> doInBackground(String... params)
//            {
//                Map<String, String> param = new HashMap<>();
//                Map<String, String> map = new HashMap<>();
//                map.put("sessionId", AppConstant.sessionId);
//                map.put("identification", "1");
//                map.put("serviceName", CardConstant.card_app_banklist);
//                map.put("data", JSON.toJSONString(param));
//
//                return ServiceDispatcher.call(context, map);
//            }
//
//            @Override
//            protected void onPostExecute(Map<String, String> result)
//            {
//                if (null != result && AppConstant.CODE_OK.equals(result.get("code")))
//                {
//                    myBanks = JSON.parseArray(result.get("myBanks"), BankBean.class);
//                    allBanks = JSON.parseArray(result.get("allBanks"), BankBean.class);
//                    setAdapter();
//                } else
//                {
//                    ToastUtil.i(context, "获取失败，请重试");
//                }
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.gray_part:
                dismiss();
                break;
        }
    }

    private OnBankClickListener listener;

    public void setOnBankClickListener(OnBankClickListener listener)
    {
        this.listener = listener;
    }

    public interface OnBankClickListener
    {
        void onBankClick(String bankId, String bankName);
    }
}
