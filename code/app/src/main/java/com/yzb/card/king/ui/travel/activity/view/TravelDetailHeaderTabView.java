package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.util.LogUtil;

/**
 * 功能：旅游详情头部view；
 *
 * @author:gengqiyun
 * @date: 2016/11/11
 */
public class TravelDetailHeaderTabView extends BaseViewGroup
{
    private TabLayout tabs;
    private IItemSelect itemSelctListener;

    public TravelDetailHeaderTabView(Context context)
    {
        super(context);
    }

    public TravelDetailHeaderTabView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        String[] titles = {"优惠详情", "评价(0)", "0日行程", "须知"};

        for (int i = 0; i < titles.length; i++)
        {
            TextView tv = (TextView) inflater.inflate(R.layout.item_tablayout, null);
            tv.setText(titles[i]);
            //设置自定义布局；
            TabLayout.Tab tab = tabs.newTab().setCustomView(tv);

            //设置父view的点击事件，否则事件出发区域会不灵敏；
            View parentView = (View) tab.getCustomView().getParent();
            final int finalI = i;
            parentView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (itemSelctListener != null)
                    {
                        itemSelctListener.itemSelect(finalI);
                    }
                }
            });
            tabs.addTab(tab);
        }
    }

    /**
     * 更新tab内容； 评价数量 行程天数
     */
    @Override
    protected void fillViewData(TravelProduDetailBean detailBean)
    {
        String voteQuantity = detailBean.getVoteQuantity() > 99 ? "99+" : detailBean.getVoteQuantity() + "";
        LogUtil.i("评价数量====fillViewData=" + voteQuantity);

        TextView voteTv = (TextView) tabs.getTabAt(1).getCustomView();
        TextView dayTv = (TextView) tabs.getTabAt(2).getCustomView();

        voteTv.setText("评价(" + voteQuantity + ")");
        dayTv.setText(detailBean.getTravelDays() + "日行程");
    }

    /**
     * 选中相应的tab；
     *
     * @param index
     */
    public void selectTab(int index)
    {
        if (tabs != null)
        {
            tabs.getTabAt(index).select();
        }
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.travel_detail_tabs;
    }

    /**
     * tab切换的监听；
     *
     * @param itemClickListener
     */
    public void setTabListener(IItemSelect itemClickListener)
    {
        this.itemSelctListener = itemClickListener;
    }

    public interface IItemSelect
    {
        void itemSelect(int position);
    }

}
