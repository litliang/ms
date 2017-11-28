package com.yzb.card.king.bean.hotel;

import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.AbsFilter;
import com.yzb.card.king.ui.appwidget.popup.HotelLuxuryPop;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/25 16:36
 */
public class Luxury extends AbsFilter
{
    private HotelLuxuryPop hotelLuxuryPop;

    private boolean isShowing = false;

    public Luxury()
    {
        super("高端奢华", R.drawable.selector_luxury, UiUtils.getColorStateList(R.drawable.selector_filter_text));
    }

    @Override
    public void clickAction(View view)
    {
        if (hotelLuxuryPop == null)
        {
            hotelLuxuryPop = new HotelLuxuryPop();
        }

        if(isShowing){

            hotelLuxuryPop.dismiss();

        }else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            hotelLuxuryPop.setHeight(location[1] - Utils.getStatusBarHeight(UiUtils.getContext()));
            int y = location[1] - hotelLuxuryPop.getHeight();
            hotelLuxuryPop.showAtLocation(view, Gravity.NO_GRAVITY, 0, y);
        }


        isShowing = hotelLuxuryPop.isShowing();
    }
}
