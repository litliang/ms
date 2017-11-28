package com.yzb.card.king.ui.ticket.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.AirLineCompanyPP;
import com.yzb.card.king.ui.appwidget.popup.CalendarPop;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.discount.fragment.BaseFragment;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.manage.CitySelectManager;
import com.yzb.card.king.ui.other.bean.CalendarDay;
import com.yzb.card.king.ui.other.bean.City;
import com.yzb.card.king.ui.ticket.activity.BaseTicketActivity;
import com.yzb.card.king.util.ToastUtil;

import java.util.Date;

/**
 * 类  名：低价机票
 * 作  者：Li Yubing
 * 日  期：2017/9/20
 * 描  述：
 */
public class LowPriceTicketFragment extends BaseFragment implements View.OnClickListener {

    public final int SINGLE_LINE = BaseTicketActivity.TYPE_SINGLE;
    public final int ROUND_LINE = BaseTicketActivity.TYPE_ROUND;
    private int currentLine = SINGLE_LINE;

    private ITicketFragment currentFragment;

    private LinearLayout ll_tab;

    private LinearLayout singleLine;

    private RoundLineFragment rf;
    private SingleLineFragment sf;

    private TextView tvMonth;

    private CalendarPop calendarPop;

    public static LowPriceTicketFragment newInstance()
    {
        LowPriceTicketFragment fragment = new LowPriceTicketFragment();


        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_low_price_ticket, container, false);

        initView(view);

        initViewData();

        return view;
    }

    private void initViewData()
    {
        if (calendarPop == null) {
            calendarPop = new CalendarPop();
            calendarPop.setListener(new OnItemClickListener<CalendarDay>() {
                @Override
                public void onItemClick(CalendarDay data)
                {
                    currentFragment.setDate(data.getDay());
                    calendarPop.dismiss();
                }
            });
        }
        selectTabFragment(0, singleLine);
    }

    private void initView(View view)
    {

        ll_tab = (LinearLayout) view.findViewById(R.id.ll_tab);

        singleLine = (LinearLayout) view.findViewById(R.id.singleLine);
        singleLine.setOnClickListener(this);

        view.findViewById(R.id.roundLine).setOnClickListener(this);

        view.findViewById(R.id.order).setOnClickListener(this);

        view.findViewById(R.id.find_low).setOnClickListener(this);

        view.findViewById(R.id.zj).setOnClickListener(this);

        view.findViewById(R.id.hbdt).setOnClickListener(this);

        view.findViewById(R.id.rlMonth).setOnClickListener(this);

        tvMonth = (TextView) view.findViewById(R.id.tvMonth);
    }
    private AirLineCompanyPP invoiceContentPp = null;

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.order://企业商旅
                ToastUtil.i(getContext(),"敬请期待");
                break;
            case R.id.find_low: // 多地比价

                ToastUtil.i(getContext(),"敬请期待");
                break;

            case R.id.hbdt:  //低价机票
                ToastUtil.i(getContext(),"敬请期待");
                break;
            case R.id.zj:   //机+酒

                ToastUtil.i(getContext(),"敬请期待");
                break;
            case R.id.singleLine:
                selectTabFragment(0, v);
                break;
            case R.id.roundLine:
                selectTabFragment(1, v);
                break;
            case R.id.rlMonth:

                if (invoiceContentPp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.month_air_line_name_array);

                    invoiceContentPp = new AirLineCompanyPP(getActivity(), -1);

                    invoiceContentPp.setLogicData(nameArray, null);

                    invoiceContentPp.setSelectIndex(0);

                    invoiceContentPp.setCallBack(invoiceCCallBack);
                }

                invoiceContentPp.showPP(getActivity().getWindow().getDecorView());

                break;

        }
    }
    private AirLineCompanyPP.BottomDataListCallBack invoiceCCallBack = new AirLineCompanyPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            tvMonth.setText(name);

        }
    };
    /**
     * fragment的切换
     *
     * @param index
     * @param view
     */
    private void selectTabFragment(int index, View view)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0: //单程
                currentLine = SINGLE_LINE;

                if (sf == null) {
                    sf = new SingleLineFragment();
                    transaction.add(R.id.fl_ticket_home_price, sf);
                } else {
                    transaction.show(sf);
                }
                currentFragment = sf;
                refresh(view);

                break;
            case 1:  //往返
                currentLine = ROUND_LINE;
                if (rf == null) {
                    rf = new RoundLineFragment();
                    transaction.add(R.id.fl_ticket_home_price, rf);
                } else {
                    transaction.show(rf);
                }
                currentFragment = rf;
                refresh(view);

                break;

        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction)
    {
        if (sf != null) {
            transaction.hide(sf);
        }
        if (rf != null) {
            transaction.hide(rf);
        }

    }

    private void refresh(View v)
    {
        int count = ll_tab.getChildCount();
        for (int i = 0; i < count; i++) {
            View vTemp = ll_tab.getChildAt(i);
            if (vTemp instanceof LinearLayout) {
                LinearLayout child = (LinearLayout) vTemp;
                child.getChildAt(0).setEnabled(child == v);
                if(child == v){

                    child.getChildAt(1).setBackgroundColor(Color.parseColor("#436a8e"));
                }else {
                    child.getChildAt(1).setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }

        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (sf != null) {
            transaction.remove(sf);
        }
        if (rf != null) {
            transaction.remove(rf);
        }

    }
    @Override
    public void onStart()
    {
        super.onStart();

        if (CalendarManager.getInstance().isDateLoadFlag()) {
            Date date = CalendarManager.getInstance().getDate();
            if (date != null) {
                currentFragment.setDate(date);
                CalendarManager.getInstance().clearData();
            }
        }

        if (CitySelectManager.getInstance().isLoadPlaceFlag()) {
            City city = (City) CitySelectManager.getInstance().getPlace();
            if (city != null) {
                currentFragment.setPlace(city);
                CitySelectManager.getInstance().clearData();
            }
        }

    }

}
