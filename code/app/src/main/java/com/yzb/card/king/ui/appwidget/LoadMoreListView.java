package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;

/**
 * 加载更多的ListView；
 *
 * @author gengyiqun
 */
public class LoadMoreListView extends ListView implements OnScrollListener
{
    private OnScrollListener mOnScrollListener;
    private LayoutInflater mInflater;

    // footer view
    private View mFooterView;

    // Listener to process load more items when user reaches the end of the list
    private OnLoadMoreListener mOnLoadMoreListener;
    // To know if the list is loading more items
    private boolean mIsLoadingMore = false;

    private boolean mCanLoadMore = true;
    private int mCurrentScrollState;

    public LoadMoreListView(Context context)
    {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // footer
        mFooterView = mInflater.inflate(R.layout.item_footer, this, false);

        addFooterView(mFooterView);

        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        super.setAdapter(adapter);
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {

        if (mOnScrollListener != null)
        {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }

        if (mOnLoadMoreListener != null)
        {

            if (visibleItemCount == totalItemCount)
            {
                mFooterView.setVisibility(View.GONE);
                return;
            }


            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

            if (!mIsLoadingMore && loadMore
                    && mCurrentScrollState != SCROLL_STATE_IDLE)
            {
                if (!mCanLoadMore)
                {
                    mFooterView.setVisibility(View.GONE);
                    return;
                }
                mFooterView.setVisibility(View.VISIBLE);
                mIsLoadingMore = true;
                onLoadMore();
            }


        }

    }

    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        mCurrentScrollState = scrollState;

        if (mOnScrollListener != null)
        {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    /**
     * Set the listener that will receive notifications every time the list
     * scrolls.
     *
     * @param listener The scroll listener.
     */
    @Override
    public void setOnScrollListener(OnScrollListener listener)
    {
        mOnScrollListener = listener;
    }

    /**
     * Register a callback to be invoked when this list reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener The callback to run.
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener)
    {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setCanLoadMore(boolean canLoadMore)
    {
        mCanLoadMore = canLoadMore;
        mFooterView.setVisibility(View.GONE);
    }

    public void onLoadMore()
    {
        if (mOnLoadMoreListener != null)
        {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * Notify the loading more operation has finished
     */
    public void onLoadMoreComplete()
    {
        mIsLoadingMore = false;
        mFooterView.setVisibility(View.GONE);
    }

    public boolean isLoading()
    {
        return mIsLoadingMore;
    }



    /**
     * Interface definition for a callback to be invoked when list reaches the
     * last item (the user load more items in the list)
     */
    public interface OnLoadMoreListener
    {
        /**
         * Called when the list reaches the last item (the last item is visible
         * to the user)
         */
        void onLoadMore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int heightMs = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMs);
    }
}