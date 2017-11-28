package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.WholeListView;
import com.yzb.card.king.ui.travel.adapter.TravellerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：旅游报名   旅客信息，联系人view；
 *
 * @author:gengqiyun
 * @date: 2016/11/25
 */
public class TravelSignGuestContactView extends BaseViewGroup implements View.OnClickListener
{
    private WholeListView passengerListView;
    private List<PassengerInfoBean> passengerList;
    private TravellerAdapter passengerAdapter;
    private Connector connector; //联系人；
    private TextView contactsName;
    private TextView contactsMobile;
    private OnClickListener listener;

    public TravelSignGuestContactView(Context context)
    {
        super(context);
    }

    public TravelSignGuestContactView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        // 添加旅客信息
        rootView.findViewById(R.id.travellerAdd).setOnClickListener(this);
        // 添加联系人信息
        rootView.findViewById(R.id.contactsAdd).setOnClickListener(this);
        // 旅客信息
        passengerListView = (WholeListView) rootView.findViewById(R.id.passengerListView);

        // 联系人信息
        contactsName = (TextView) findViewById(R.id.contactsName);
        contactsMobile = (TextView) findViewById(R.id.contactsMobile);
    }

    public List<PassengerInfoBean> getPassengers()
    {
        return passengerList;
    }

    public Connector getConnector()
    {
        return connector;
    }

    public void setConnector(Connector connector)
    {
        this.connector = connector;
        if (connector != null)
        {
            contactsName.setText(connector.getNickName());
            contactsMobile.setText(connector.getMobile());
        }
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_signup_guest_contact;
    }

    @Override
    public void onClick(View v)
    {
        if (listener != null)
        {
            listener.onClick(v);
        }
    }

    public void addGuest(PassengerInfoBean passenger)
    {
        if (passenger != null)
        {
            if (passengerList == null)
            {
                passengerList = new ArrayList<>();
            }
            if (!hasExist(passenger))
            {
                passengerList.add(passenger);
                passengerAdapter = new TravellerAdapter(mContext, passengerList);
                passengerListView.setAdapter(passengerAdapter);
            }
        }
    }

    /**
     * 判断是否存在；
     *
     * @param passenger
     * @return true:存在；false：不存在；
     */
    private boolean hasExist(PassengerInfoBean passenger)
    {
        if (passengerList != null && passenger != null)
        {
            String id = passenger.getId();
            for (int i = 0; i < passengerList.size(); i++)
            {
                if (!TextUtils.isEmpty(id) && id.equals(passengerList.get(i).getId()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void setViewClickCall(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    /**
     * 判断旅客是否已存在；
     *
     * @param pib
     * @return
     */
    public boolean judgeSame(PassengerInfoBean pib)
    {
        if (passengerList != null && pib != null && !isEmpty(pib.getCertType()) && !isEmpty(pib.getCertNo()))
        {
            PassengerInfoBean pibLocal;
            for (int i = 0; i < passengerList.size(); i++)
            {
                pibLocal = passengerList.get(i);
                if (pib.getCertType().equals(pibLocal.getCertType()) && pib.getCertNo().equals(pibLocal.getCertNo()))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
