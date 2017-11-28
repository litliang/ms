package com.yzb.card.king.ui.appwidget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.appwidget.headfootrecyclerview.EndlessRecyclerOnScrollListener;
import com.yzb.card.king.ui.appwidget.headfootrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.yzb.card.king.ui.appwidget.headfootrecyclerview.LoadingFooter;
import com.yzb.card.king.ui.appwidget.headfootrecyclerview.RecyclerViewStateUtils;
import com.yzb.card.king.ui.appwidget.headfootrecyclerview.RecyclerViewUtils;
import com.yzb.card.king.util.LogUtil;

/**
 * Created by 玉兵 on 2016/11/13.
 */

public class HeadFootRecyclerView extends android.support.v7.widget.RecyclerView {


    /**
     * 每一页展示多少条数据
     */
    private int REQUEST_COUNT = AppConstant.MAX_PAGE_NUM;


    private Activity context;

    private OnLoadMoreListener loadMoreListener;

    private boolean mandatory = false;//强制关闭标志

    public int getREQUEST_COUNT()
    {
        return REQUEST_COUNT;
    }

    /**
     * 设置最大页数
     *
     * @param REQUEST_COUNT
     */
    public void setREQUEST_COUNT(int REQUEST_COUNT)
    {
        this.REQUEST_COUNT = REQUEST_COUNT;
    }

    /**
     * 根据新加载数量计算是否支持加载新数据
     *
     * @param newSize
     */
    public void calByNewNum(int newSize)
    {

        if (newSize < REQUEST_COUNT) {

            loadMoreEnable = false;

        } else {
            loadMoreEnable = true;
        }

        notifyData();
    }

    /**
     * 是否加载
     */
    private boolean loadMoreEnable = false;

    public HeadFootRecyclerView(Context context)
    {
        super(context);

        this.context = (Activity) context;


    }

    public HeadFootRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context = (Activity) context;
    }

    public HeadFootRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = (Activity) context;
    }

    /**
     * 设置头部
     *
     * @param headView
     */
    public void setHeadView(View headView)
    {
        RecyclerViewUtils.setHeaderView(HeadFootRecyclerView.this, headView);
    }

    /**
     * 设置底部
     *
     * @param footView
     */
    public void setFootView(View footView)
    {
        RecyclerViewUtils.setHeaderView(HeadFootRecyclerView.this, footView);
    }

    private void init()
    {
        if (loadMoreEnable) {
            addOnScrollListener(mOnScrollListener);
        }

    }

    /**
     * 滚动监听
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view)
        {
            super.onLoadNextPage(view);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(HeadFootRecyclerView.this);
            if (state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }

            /**
             * 当新加载数据数量小于，则loadMoreEnable=true
             */
            if (loadMoreEnable) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(context, HeadFootRecyclerView.this, REQUEST_COUNT, LoadingFooter.State.Loading, null);

                if (loadMoreListener != null) {

                    loadMoreListener.loadMoreListener();
                }

            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(context, HeadFootRecyclerView.this, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };
    /**
     * 设置是否允许加载更多
     *
     * @param isEnable
     */
    public void setIsEnale(boolean isEnable)
    {
        this.loadMoreEnable = isEnable;

        init();
    }


    /**
     * 设置是否允许加载更多
     *
     * @param isEnable
     */
    public void setLoadMoreEnable(boolean isEnable)
    {
        this.mandatory = isEnable;

        if(isEnable){
            //关闭加载更多
            RecyclerViewStateUtils.setFooterViewState(HeadFootRecyclerView.this, LoadingFooter.State.Normal);
        }

    }


    @Override
    public void setAdapter(Adapter adapter)
    {
        init();
        // RecyclerView的上拉时候的浏览动画  从小变大
        //ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(adapter);
        // 渐变动画 透明到清晰
        //AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
        // 类似位移的动画
        //SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter);

        // 如果需要动画 请将 xxxxxxAnimationAdapter的对象作为AutoLoadAdapter的构造参数传入进去
        // PS: 如果需要动画 项目需要引入 compile 'jp.wasabeef:recyclerview-animators:1.3.0'

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        super.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }


    /**
     * 加载更多数据回调
     *
     * @param listener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener listener)
    {
        this.loadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void loadMoreListener();//上拉刷新
    }
    /**
     * 刷新数据
     */
    public void notifyData()
    {
        setLoadMoreEnable(true);
    }

    /**
     * 设置分割线；
     */
    public void setNeedDivider(boolean isNeedDivider)
    {
        if (isNeedDivider)
        {
            //添加分割线；
            addItemDecoration(new DividerDecoration(getContext(), DividerDecoration.VERTICAL_LIST));
        }
    }

}
