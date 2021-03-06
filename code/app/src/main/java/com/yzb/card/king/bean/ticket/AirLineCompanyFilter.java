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
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：航空公司
 * 作  者：Li Yubing
 * 日  期：2017/9/21
 * 描  述：
 */
public class AirLineCompanyFilter extends AbsFilter {

    private boolean ifShow = false;

    private AirLineCompanyPP invoiceContentPp = null;

    int selectedCInvoicIndex = 0;


    public AirLineCompanyFilter() {
        super("航空公司", R.drawable.selector_air_line_company, UiUtils.getColorStateList(R.drawable.selector_filter_text));

    }

    @Override
    public void clickAction(View view) {

        if (invoiceContentPp == null) {

            String[] nameArray = GlobalApp.getInstance().getResources().getStringArray(R.array.ticket_air_line_company_name_array);

            invoiceContentPp = new AirLineCompanyPP(GlobalApp.getInstance().getPublicActivity(), -1, BaseFullPP.ViewPostion.VIEW_BOTTOM);

            invoiceContentPp.setLogicData(nameArray, null);



            invoiceContentPp.setCallBack(getInvoiceCCallBack());

            invoiceContentPp.getBaseBottomFullPP().setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    if (ifShow) {
                        ifShow = false;
                    }

                }
            });
        }
        String singleline_filter_copany = SharePrefUtil.getValueFromSp(GlobalApp.getInstance(),"singleline"+"-filter-company"," ");
        singleline_filter_copany = singleline_filter_copany.trim().equals("")?"0":singleline_filter_copany;
//        ToastUtil.i(GlobalApp.getInstance().getPublicActivity(),""+singleline_filter_copany);
        invoiceContentPp.setSelectIndex(Integer.parseInt(singleline_filter_copany));
        if (ifShow) {
            invoiceContentPp.getBaseBottomFullPP().dismiss();
        } else {

            invoiceContentPp.showToByView(view);


        }


        ifShow = invoiceContentPp.getBaseBottomFullPP().isShowing();

    }

    private AirLineCompanyPP.BottomDataListCallBack invoiceCCallBack = new AirLineCompanyPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex) {


        }
    };

    public AirLineCompanyPP.BottomDataListCallBack getInvoiceCCallBack() {
        return invoiceCCallBack;
    }

    public AirLineCompanyFilter setInvoiceCCallBack(AirLineCompanyPP.BottomDataListCallBack invoiceCCallBack) {
        this.invoiceCCallBack = invoiceCCallBack;
        return this;
    }
}
