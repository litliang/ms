package com.yzb.card.king.bean.hotel;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.bean.ticket.IFilterPopItem;
import com.yzb.card.king.bean.ticket.SortType;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.appwidget.HotelFilterView;
import com.yzb.card.king.ui.appwidget.SortTopListPop;
import com.yzb.card.king.ui.appwidget.popup.SimpleListPop;
import com.yzb.card.king.ui.ticket.interfaces.OnItemSelectLis;
import com.yzb.card.king.ui.travel.activity.TravelLineListActivity;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/25 16:37
 */
public class LevelPrice extends AbsFilter implements OnItemSelectLis
{
    private List<IFilterPopItem> list = new ArrayList<>();

    private boolean ifShow  = false;

    private  SortTopListPop  sortTopListPop;

    /**
     * 当前排序编号
     */
    private int sortCurrentIndex = 0;
    public LevelPrice()
    {
        super("星级/价格", R.drawable.selector_level_price, UiUtils.getColorStateList(R.drawable.selector_filter_text));
        list.add(new SortType("1","价格从低到高",0));
        list.add(new SortType("2","价格从高到低",1));
        list.add(new SortType("3","星级从低到高",2));
        list.add(new SortType("4","星级从高到低",3));
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
            sortTopListPop.setSelectedItem(sortCurrentIndex);
        }
        ifShow=sortTopListPop.isShowing();
    }

    @Override
    public void onItemSelect(IFilterPopItem item)
    {
        setName(item.getName());

        HotelFilterView.data.setPrice((SortType) item);

        SortType type = (SortType) item;

        sortCurrentIndex = type.getSortIndex();
        ifShow = false;
    }
}
