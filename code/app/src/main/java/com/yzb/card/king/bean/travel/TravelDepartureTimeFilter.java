package com.yzb.card.king.bean.travel;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.ui.appwidget.popup.TravelDepartrueTimePop;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * Created by sushuiku on 2016/11/22.
 */

public class TravelDepartureTimeFilter extends AbsFilter{

    private TravelDepartrueTimePop travelDepartrueTimePop;
    private Activity activity;

    public TravelDepartureTimeFilter(Activity activity) {
        super("筛选", R.drawable.selector_filter, UiUtils.getColorStateList(R.drawable.selector_filter_text));
        this.activity = activity;
    }

    @Override
    public void clickAction(View view)
    {
//        if (travelDepartrueTimePop == null)
//        {
//            travelDepartrueTimePop = new TravelDepartrueTimePop(activity);
//        }
//        travelDepartrueTimePop.showAtLocation(view, Gravity.TOP, 0, 0);
    }

    public void getDate(){
        if (travelDepartrueTimePop != null){
            LogUtil.i("travel=="+1111);
            travelDepartrueTimePop.onStart();

        }
    }
}

