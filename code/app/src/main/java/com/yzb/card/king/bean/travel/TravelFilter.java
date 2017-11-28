package com.yzb.card.king.bean.travel;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.ui.appwidget.popup.HotelFilterPop;
import com.yzb.card.king.ui.appwidget.popup.TravelScreenPopup;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/22
 * 描  述：
 */
public class TravelFilter extends AbsFilter {
    private TravelScreenPopup travelFilterPop;
    private Activity activity;

    public TravelFilter(Activity activity)
    {
        super("筛选", R.drawable.selector_filter, UiUtils.getColorStateList(R.drawable.selector_filter_text));
        this.activity = activity;
    }

    @Override
    public void clickAction(View view)
    {
//        if (travelFilterPop == null)
//        {
//            travelFilterPop = new TravelScreenPopup(activity);
//        }
//        travelFilterPop.showAtLocation(view, Gravity.TOP, 0, 0);
    }

}
