package com.yzb.card.king.ui.discount.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.discount.adapter.GoodsAdapterAbs;
import com.yzb.card.king.ui.discount.adapter.TypeAdapterAbs;
import com.yzb.card.king.ui.discount.bean.Active;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.bean.GoodsType;
import com.yzb.card.king.ui.discount.bean.Position;
import com.yzb.card.king.ui.discount.bean.ShopDetail;
import com.yzb.card.king.ui.discount.holder.OrderDetailHolder;
import com.yzb.card.king.ui.discount.presenter.CollectionPresenter;
import com.yzb.card.king.ui.discount.presenter.StoreDetailPresenter;
import com.yzb.card.king.ui.discount.presenter.StoreGoodsPresenter;
import com.yzb.card.king.ui.discount.presenter.impl.CollectionPresenterImpl;
import com.yzb.card.king.ui.discount.presenter.impl.StoreDetailPresenterImpl;
import com.yzb.card.king.ui.discount.presenter.impl.StoreGoodsPresenterImpl;
import com.yzb.card.king.ui.discount.view.StoreGoodsView;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.ViewUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺页面；
 */
public class ShopHomeActivity extends BaseActivity implements View.OnClickListener,
        BaseViewLayerInterface, StoreGoodsView {
    private TextView headerTitle;
    private TextView tvArrow;
    private LinearLayout llArrow;
    private ListView lvTypeListView;
    private ListView lvGoodsListView;
    private TypeAdapterAbs typeAdapter;
    private List<GoodsType> typeList = new ArrayList<>();
    private GoodsAdapterAbs goodsAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    private Position selectPosition = new Position();
    private View rootView;
    private OrderDetailHolder orderDetailHolder;
    private FrameLayout flOrder;
    private View tvCollection;
    private ShopDetail shopDetail;
    private String goodsTypeId = "";
    private ListView lvActiveListView;
    private List<Active> activeList = new ArrayList<>();
    private ActiveAdapterAbs activeAdapter;
    private View backView;
    private View llActive;
    private String storeId;
    private boolean collectionStatus;
    private int twoItemHeight;
    private int realHeight;
    private ValueAnimator downAnimator, upAnimator;

    private StoreDetailPresenter storeDetailPresenter; //详情；
    private CollectionPresenter collectionPresenter; //收藏；
    private StoreGoodsPresenter storeGoodsPresenter; //商品列表；
    private int pageStart = 0; //页码；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        storeDetailPresenter = new StoreDetailPresenterImpl(this);
        collectionPresenter = new CollectionPresenterImpl(this);
        storeGoodsPresenter = new StoreGoodsPresenterImpl(this);
        initData();
    }

    private void initView()
    {
        rootView = UiUtils.inflate(R.layout.activity_shop_home);
        setContentView(rootView);
        headerTitle = (TextView) findViewById(R.id.headerTitle);
        tvArrow = (TextView) findViewById(R.id.icon_arrow_change);
        llArrow = (LinearLayout) findViewById(R.id.llArrow);
        lvActiveListView = (ListView) findViewById(R.id.lvActive);
        lvTypeListView = (ListView) findViewById(R.id.lvType);
        lvGoodsListView = (ListView) findViewById(R.id.lvGoods);
        flOrder = (FrameLayout) findViewById(R.id.flOrder);
        tvCollection = findViewById(R.id.tvCollection);
        backView = findViewById(R.id.backView);
        llActive = findViewById(R.id.llActive);

        findViewById(R.id.headerLeft).setOnClickListener(this);
        findViewById(R.id.llCollection).setOnClickListener(this);
        llArrow.setOnClickListener(this);

    }

    private void initData()
    {
        storeId = getIntent().getStringExtra("storeId");
        String storeName = getIntent().getStringExtra("storeName");

        headerTitle.setText(storeName);

        orderDetailHolder = new OrderDetailHolder(this);
        flOrder.removeAllViews();
        flOrder.addView(orderDetailHolder.getConvertView());

        typeAdapter = new TypeAdapterAbs(typeList, selectPosition);
        goodsAdapter = new GoodsAdapterAbs(goodsList, orderDetailHolder);
        activeAdapter = new ActiveAdapterAbs(activeList);
        lvTypeListView.setAdapter(typeAdapter);
        lvGoodsListView.setAdapter(goodsAdapter);
        lvActiveListView.setAdapter(activeAdapter);

        OnTypeClickListener onTypeClickListener = new OnTypeClickListener();
        lvTypeListView.setOnItemClickListener(onTypeClickListener);

        getStoreDetail();
        //CollectionTask collectionTask = new CollectionTask();
        //collectionTask.execute();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.llCollection://收藏
                if (!isLogin()) {
                    new GoLoginDailog(this).show();
                    return;
                }
                collectionStatus = tvCollection.isEnabled();
                exeCollect();
                break;
            case R.id.llSearch://搜索
                doSearch();
                break;
            case R.id.llArrow://显示隐藏活动信息
                changeDetailState();
                break;
        }
    }

    private void doSearch()
    {
        Intent intent = new Intent(this, SearchDiscountActivity.class);
        intent.putExtra("storeId", storeId);
        startActivity(intent);
    }

    private void changeDetailState()
    {
        if (downAnimator == null) {
            downAnimator = createAnimator(twoItemHeight, realHeight);
        }
        if (upAnimator == null) {
            upAnimator = createAnimator(realHeight, twoItemHeight);
        }
        if (tvArrow.isEnabled()) {
            tvArrow.setEnabled(false);
            downAnimator.start();
        } else {
            tvArrow.setEnabled(true);
            upAnimator.start();
        }
    }

    private ValueAnimator createAnimator(int start, int end)
    {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (int) animation.getAnimatedValue();
                ViewUtil.setListViewHeight(lvActiveListView, value);
            }
        });
        animator.setDuration(500);
        return animator;
    }

    @Override
    public void callSuccessViewLogic(Object data, int type)
    {
        if (type == 1) {
            tvCollection.setEnabled(!collectionStatus);
        } else if (type == 7) {
            if (data != null) {
                shopDetail = (ShopDetail) data;
                //活动列表数据
                activeList.clear();
                activeList.addAll(shopDetail.actList);
                activeAdapter.notifyDataSetChanged();
                setActiveListHeight();
                //分类数据
                typeList.clear();
                typeList.add(new GoodsType("", "全部"));
                typeList.addAll(shopDetail.typeList);
                typeAdapter.notifyDataSetChanged();
                //获取全部商品
                goodsTypeId = typeList.get(0).goodsTypeId;

                getStoreGoodsList(true);
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        if(type==1) {
            toastCustom(R.string.collsucc);
        }else if(type==7){
            toastCustom(o+"");
            setActiveListHeight();
        }
    }

    class ActiveAdapterAbs extends AbsBaseListAdapter<Active> {

        public ActiveAdapterAbs(List<Active> list)
        {
            super(list);
        }

        @Override
        protected Holder getHolder(int position)
        {
            return new ActiveHolder();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);
            ActiveHolder holder = (ActiveHolder) view.getTag();
            if (position == activeList.size() - 1) {
                holder.setDividerVisibility(View.INVISIBLE);

            } else {
                holder.setDividerVisibility(View.VISIBLE);
            }
            return view;
        }
    }

    class ActiveHolder extends Holder<Active> {
        private ImageView image;
        private TextView tvTitle;
        private View divider;

        @Override
        public View initView()
        {
            View view = UiUtils.inflate(R.layout.item_shop_active);
            image = (ImageView) view.findViewById(R.id.image);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            divider = view.findViewById(R.id.divider);
            return view;
        }

        @Override
        public void refreshView(Active data)
        {
            x.image().bind(image, ServiceDispatcher.getImageUrl(data.actPhoto));
            tvTitle.setText(data.actTitle);
        }

        public void setDividerVisibility(int visibility)
        {
            divider.setVisibility(visibility);
        }
    }

    @Override
    public void onLoadStoreGoodsSucess(boolean event_tag, Object data)
    {
        if (data != null) {
            pageStart++;
            List<Goods> list = (List<Goods>) data;
            if (event_tag) {
                goodsList.clear();
            }
            goodsList.addAll(list);
            for (Goods goods : goodsList) {
                goods.storeName = shopDetail.sotreName;
            }
            goodsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadStoreGoodsFail(String failMsg)
    {
        goodsList.clear();
        goodsAdapter.notifyDataSetChanged();
        toastCustom(R.string.text_no_goods);
    }

    /**
     * 获取店铺商品列表；
     *
     * @param event_tag true:下拉；false:上拉；
     */
    private void getStoreGoodsList(boolean event_tag)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", shopDetail.storeId);
        params.put("pageStart", pageStart);
        params.put("pageSize", "15");
        params.put("goodsTypeId", goodsTypeId);
        storeGoodsPresenter.loadData(event_tag, params);
    }

    class OnTypeClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectPosition.position = position;
            typeAdapter.notifyDataSetChanged();
            goodsTypeId = typeList.get(position).goodsTypeId;

            getStoreGoodsList(true);
        }
    }


    /**
     * 执行收藏；
     */
    private void exeCollect()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("type", AppConstant.COLLECTION_TYPE_STORE);//1,商家，2，商品
        params.put("category", AppConstant.COLLECTION_CATEGORY_STORE);//
        params.put("detailsId", storeId);
        params.put("status", collectionStatus ? "1" : "0");
        collectionPresenter.loadData(params);
    }



    /**
     * 获取店铺详情；
     */
    private void getStoreDetail()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        storeDetailPresenter.loadData(params);
    }

    /**
     * 设置活动列表的高度
     *
     * @author ysg
     * created at 2016/8/23 16:50
     */
    private void setActiveListHeight()
    {
        int llActiveHeight = 0;
        if (activeList.size() <= 2) {
            llArrow.setVisibility(View.GONE);
        } else {
            llArrow.setVisibility(View.VISIBLE);
            llActiveHeight = llActive.getMeasuredHeight();
        }

        twoItemHeight = ViewUtil.getListViewHeight(lvActiveListView, 2);
        ViewUtil.setListViewHeight(lvActiveListView, twoItemHeight);
        realHeight = ViewUtil.getListViewHeight(lvActiveListView, activeList.size());
        ViewGroup.LayoutParams params = backView.getLayoutParams();
        params.height = llActiveHeight + twoItemHeight;
        backView.setLayoutParams(params);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        orderDetailHolder.onActivityDestroy();
    }
}
