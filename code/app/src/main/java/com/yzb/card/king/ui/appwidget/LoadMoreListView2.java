package com.yzb.card.king.ui.appwidget;

import android.widget.ListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.yzb.card.king.R;
/**
 * 功能：
 *
 * @author:gengqiyun
 * @date: 2017/2/20
 */
public class LoadMoreListView2 extends ListView implements OnScrollListener
{
    private View footer;

    private int totalItem;
    private int lastItem;

    private boolean isLoading;

    private OnLoadMoreListener onLoadMore;

    private LayoutInflater inflater;

    public LoadMoreListView2(Context context)
    {
        super(context);
        init(context);
    }

    public LoadMoreListView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView2(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.item_footer, null, false);
        footer.setVisibility(View.GONE);
        addFooterView(footer);
        setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        this.lastItem = firstVisibleItem + visibleItemCount;
        this.totalItem = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if (this.totalItem == lastItem && scrollState == SCROLL_STATE_IDLE)
        {
            if (!isLoading)
            {
                isLoading = true;
                footer.setVisibility(View.VISIBLE);
                onLoadMore.onLoadMore();
            }
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMore)
    {
        this.onLoadMore = onLoadMore;
    }

    /**
     * 加载完成调用此方法
     */
    public void onLoadMoreComplete()
    {
        footer.setVisibility(View.GONE);
        isLoading = false;
    }

    public interface OnLoadMoreListener
    {
        void onLoadMore();
    }

}
