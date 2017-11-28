package com.yzb.card.king.bean.ticket;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.SortTopListPop;
import com.yzb.card.king.ui.appwidget.TicketFilterView;
import com.yzb.card.king.ui.appwidget.popup.SimpleListPop;
import com.yzb.card.king.ui.ticket.interfaces.OnItemSelectLis;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：筛选：从早到晚
 * 作者：殷曙光
 * 日期：2016/9/28 18:16
 */
public class MorningNight extends AbsFilter implements OnItemSelectLis
{
    private List<IFilterPopItem> list = new ArrayList<>();
    private int position = -1;
    private boolean ifShow  = false;

    private  SortTopListPop  sortTopListPop;

    public MorningNight()
    {
        super("时间",R.drawable.selector_ticket_filter_time, UiUtils.getColorStateList(R.drawable.selector_filter_text));
        list.add(new SortType("1","从早到晚",0));
        list.add(new SortType("2","从晚到早",1));
    }

    @Override
    public void clickAction(View view)
    {
        if (sortTopListPop == null)
        {

            AppBaseDataBean bean = GlobalApp.getInstance().getAppBaseDataBean();

            View menuView = LayoutInflater.from(GlobalApp.getInstance()).inflate(R.layout.popup_ticket_filter, null);

            int h = (int) GlobalApp.getInstance().getResources().getDimension(R.dimen.tab_bottom_h);;

            sortTopListPop = new SortTopListPop(menuView, ViewGroup.LayoutParams.MATCH_PARENT, bean.getScreenHeight() -h - bean.getStatusBarHeight());

            sortTopListPop.setOnItemSelectLis(this);

            sortTopListPop.setSelectedItem(0);

            sortTopListPop.addDataList(list);

            sortTopListPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
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
            sortTopListPop.dismiss();
        }else{

            sortTopListPop.showPositionTopByOnclickView(view);
            sortTopListPop.setSelectedItem(position);
        }
        ifShow=sortTopListPop.isShowing();
    }


    @Override
    public void onItemSelect(IFilterPopItem item)
    {
        setName(item.getName());
        SortType sortType = (SortType) item;
        position = sortType.getSortIndex();
        TicketFilterView.filterData.setTime(sortType);
    }
}
