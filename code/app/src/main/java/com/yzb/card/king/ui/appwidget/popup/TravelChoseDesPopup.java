package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.FromPlaceBean;
import com.yzb.card.king.ui.travel.adapter.TravelBgnPopAdapter;

import java.util.List;

/**
 * 类  名：旅游详情设置出发地
 * 作  者：Li JianQiang
 * 日  期：2016/11/17
 * 描  述：旅游详情-出发地；
 */
public class TravelChoseDesPopup extends PopupWindow
{
    private Activity activity;
    private List<FromPlaceBean> list;
    private FromPlaceBean fromPlaceBean;
    public OnItemClick setOnItemClick;

    public TravelChoseDesPopup(Activity activity, List<FromPlaceBean> list)
    {
        this.list = list;
        this.activity = activity;
    }

    public void setSelectBean(FromPlaceBean fpBean)
    {
        this.fromPlaceBean = fpBean;
    }

    public void openPop(View view)
    {
        initPopWindow(R.layout.travel_dis_pop);
        initPopData();
        showAtLocation(view, Gravity.TOP, 0, 0);
    }

    private void initPopData()
    {
        View contentView = getContentView();
        if (contentView != null)
        {
            GridView grid = (GridView) contentView.findViewById(R.id.gridView);
            contentView.findViewById(R.id.dis_close).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    dismiss();
                }
            });
            final TravelBgnPopAdapter adapter = new TravelBgnPopAdapter(activity, list, fromPlaceBean);
            adapter.setOnItemClickListener(new TravelBgnPopAdapter.onClickeListener()
            {
                @Override
                public void onClickListener(int position)
                {
                    setOnItemClick.setOnClick(position);
                    dismiss();
                }
            });
            grid.setAdapter(adapter);
        }
    }

    public interface OnItemClick
    {
        void setOnClick(int pos);
    }

    public void setSetOnItemClick(OnItemClick itemClick)
    {
        this.setOnItemClick = itemClick;
    }

    private void initPopWindow(int contentViewId)
    {
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.popupwindow_animation_style);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(activity.getLayoutInflater().inflate(contentViewId, null));
    }
}
