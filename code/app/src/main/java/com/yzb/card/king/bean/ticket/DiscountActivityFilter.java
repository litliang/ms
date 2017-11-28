package com.yzb.card.king.bean.ticket;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SortTopListPop;
import com.yzb.card.king.ui.appwidget.popup.AirLineCompanyPP;
import com.yzb.card.king.ui.appwidget.popup.BaseFullPP;
import com.yzb.card.king.ui.appwidget.popup.TicketDiscountActivityPopup;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：优惠活动
 * 作  者：Li Yubing
 * 日  期：2017/9/21
 * 描  述：
 */
public class DiscountActivityFilter extends AbsFilter{



    private boolean ifShow  = false;

    private TicketDiscountActivityPopup invoiceContentPp = null;

    public DiscountActivityFilter()
    {
        super("优惠活动", R.drawable.selector_discount_activity_time, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {



        if (invoiceContentPp == null) {


            invoiceContentPp = new TicketDiscountActivityPopup(GlobalApp.getInstance().getPublicActivity(), -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

            invoiceContentPp.setCallBack(invoiceCCallBack);

            invoiceContentPp.getBaseBottomFullPP().setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss()
                {

                    if(ifShow ){
                        ifShow = false;
                    }

                }
            });
        }

        if(ifShow){
            invoiceContentPp.getBaseBottomFullPP().dismiss();
        }else{

            invoiceContentPp.showToByView(view);

        }
        ifShow= invoiceContentPp.getBaseBottomFullPP().isShowing();


    }


    private TicketDiscountActivityPopup.BottomDataListCallBack invoiceCCallBack = new TicketDiscountActivityPopup.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {


        }
    };
}
