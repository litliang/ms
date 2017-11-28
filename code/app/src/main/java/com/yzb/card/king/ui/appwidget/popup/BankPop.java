package com.yzb.card.king.ui.appwidget.popup;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.adapter.VpBaseAdapter;
import com.yzb.card.king.ui.discount.adapter.YhkGvAdapter;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.luxury.presenter.BankMenuPresenter;
import com.yzb.card.king.ui.luxury.presenter.impl.BankMenuPresenterImpl;
import com.yzb.card.king.ui.luxury.view.BankMenuView;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：银行卡pop；
 *
 * @author:gengqiyun
 * @date: 2016/7/15
 */
public class BankPop implements BankMenuView
{
    private static BankPop bankPop;
    private static PopupWindow yhkPopWindow;
    private Context context;
    private VpBaseAdapter vpBaseAdapter;

    private static List<BankBean> myBanks; //我的银行；
    private static List<BankBean> allBanks;//所有银行；
    private String bankId;
    private IBankPop callBack;
    private BankMenuPresenter bankMenuPresenter; //获取我的银行和所有银行；

    public static void clear()
    {
        myBanks = null;
        allBanks = null;
    }

    public static void reset()
    {
        bankPop = null;
        clear();
    }

    public BankPop setIBankCallBack(IBankPop callBack)
    {
        this.callBack = callBack;
        return this;
    }

    public static PopupWindow getYhkPopWindow()
    {
        return yhkPopWindow;
    }

    public interface IBankPop
    {
        void popDismiss();

        void bankItemClick(BankBean bankBean);

        void onAllBankLoadFinished();
    }

    public static BankPop getInstance(Context context)
    {

        synchronized (BankPop.class)
        {
            if (bankPop == null)
            {
                bankPop = new BankPop(context);
            }
        }
        return bankPop;
    }

    public BankPop()
    {

    }

    public BankPop(Context context)
    {
        this.context = context;
        bankMenuPresenter = new BankMenuPresenterImpl(this);
    }


    public static List<BankBean> getAllBanks()
    {
        return allBanks;
    }

    /**
     * 显示popwindow；
     *
     * @param anchor
     */
    public void showYhkPopWindow(Context context, View anchor)
    {
        if (anchor == null || context == null)
        {
            return;
        }
        this.context = context;

        if (yhkPopWindow == null)
        {
            yhkPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            initPopWindow(yhkPopWindow, R.layout.popwindow_content_yhk);
        }
        initYhkViewData();
        yhkPopWindow.showAsDropDown(anchor, 0, 0);
        yhkPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                if (callBack != null)
                {
                    callBack.popDismiss();
                }
            }
        });
    }

    /**
     * 初始化银行卡数据；
     * ViewPager+PageAdapter不能再PopupWindow中使用；
     */
    private void initYhkViewData()
    {
        View yhkContentView = yhkPopWindow.getContentView();
        if (yhkContentView == null) return;

        TabLayout tabLayout = (TabLayout) yhkContentView.findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) yhkContentView.findViewById(R.id.viewPager);
        vpBaseAdapter = new VpBaseAdapter();

        List<View> viewList = new ArrayList<>();
        viewList.add(getVpItemView(0));
        viewList.add(getVpItemView(1));
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
     */
    public View getVpItemView(final int pageIndex)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_popup_yhk, null);
        final GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setBackgroundColor(context.getResources().getColor(R.color.white));
        final YhkGvAdapter yhkGvAdapter = new YhkGvAdapter(context);
        if (pageIndex == 0)
        {
            yhkGvAdapter.appendDataList(myBanks);
        } else if (pageIndex == 1)
        {
            yhkGvAdapter.appendDataList(allBanks);
        }
        yhkGvAdapter.setCurBankId(bankId);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                BankBean bankBean = yhkGvAdapter.getItem(position);
                bankId = bankBean.id + "";
                if (callBack != null)
                {
                    callBack.bankItemClick(bankBean);
                }
            }
        });
        gridView.setAdapter(yhkGvAdapter);
        return view;
    }

    public void dismissPopWindow()
    {
        if (yhkPopWindow != null && yhkPopWindow.isShowing() && context != null)
        {
            yhkPopWindow.dismiss();
        }
    }

    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        if (window != null)
        {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popupwindow_animation_style);
        }

        if (window != null && contentViewId != 0)
        {
            window.setContentView(LayoutInflater.from(context).inflate(contentViewId, null));
        }
    }

    /**
     * 获取我的银行卡，所有银行卡数据；
     */
    public void getYhkData()
    {
        if (allBanks == null || allBanks.size() == 0)
        {
            loadMyAndAllBanks();
        }
    }

    @Override
    public void onLoadBankMenuSucess(List<BankBean> myBanks, List<BankBean> allBanks)
    {
        this.myBanks = myBanks;
        this.allBanks = allBanks;
        if (callBack != null)
        {
            callBack.onAllBankLoadFinished();
        }
    }

    @Override
    public void onLoadBankMenuFail(String failMsg)
    {
        ToastUtil.i(context, context.getResources().getString(R.string.app_load_data_error));
    }

    /**
     * 获取我的银行和所有银行；
     */
    private void loadMyAndAllBanks()
    {
        Map<String, Object> param = new HashMap<>();
        bankMenuPresenter.loadData(param);
    }

}
