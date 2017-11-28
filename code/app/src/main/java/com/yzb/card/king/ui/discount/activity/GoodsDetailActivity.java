package com.yzb.card.king.ui.discount.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.bean.Active;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.bean.Specification;
import com.yzb.card.king.ui.discount.fragment.ShareDialogFragment;
import com.yzb.card.king.ui.discount.holder.OrderDetailHolder;
import com.yzb.card.king.ui.discount.holder.SpecificationsHolder;
import com.yzb.card.king.ui.appwidget.NoScrollListView;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.List;

/**
 * 商品详情；
 */
public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener,
        SpecificationsHolder.OnSpecificationSelectedListener
{
    private ImageView ivBack, ivShare, ivAdd;
    private TextView tvName, tvPrice, tvDescription, tvSaleNum;
    private LinearLayout llSize;
    private FrameLayout flOrder;
    private OrderDetailHolder orderDetailHolder;
    private Goods goods;
    private View tvSaleOut;
    private FrameLayout flSpecifications;
    private SpecificationsHolder specificationsHolder;
    private NoScrollListView lvActive;
    private ActiveAdapterAbs adapter;
    private ImageView ivGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_goods_detail);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivShare = (ImageView) findViewById(R.id.ivShare);
        ivGoods = (ImageView) findViewById(R.id.ivGoods);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvSaleNum = (TextView) findViewById(R.id.tvSaleNum);
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        llSize = (LinearLayout) findViewById(R.id.llSize);
        flSpecifications = (FrameLayout) findViewById(R.id.flSpecifications);
        flOrder = (FrameLayout) findViewById(R.id.flOrder);
        tvSaleOut = findViewById(R.id.tvSaleOut);
        lvActive = (NoScrollListView) findViewById(R.id.lvActive);

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    private void initData()
    {
        goods = (Goods) getIntent().getSerializableExtra("goods");
        //图片
        x.image().bind(ivGoods, ServiceDispatcher.getImageUrl(goods.goodsPhoto), GlobalApp.getInstance().getImageOptionsLogo());
        //规格
        flSpecifications.removeAllViews();
        specificationsHolder = new SpecificationsHolder();
        specificationsHolder.refreshView(goods);

        flSpecifications.addView(specificationsHolder.getConvertView());
        specificationsHolder.setOnSpecificationSelectedListener(this);
        //购物车
        flOrder.removeAllViews();
        orderDetailHolder = new OrderDetailHolder(this);
        flOrder.addView(orderDetailHolder.getConvertView());
        //活动
        adapter = new ActiveAdapterAbs(goods.actList);
        lvActive.setAdapter(adapter);

        tvName.setText(goods.goodsName);
        if (goods.specification != null)
        {
            tvPrice.setText(goods.specification.specPrice + "");
        }
        tvDescription.setText(goods.goodsDesc);
        tvSaleNum.setText(goods.monthSales + "");

        if (goods.stockNum <= 0)
        {
            tvSaleOut.setVisibility(View.VISIBLE);
            ivAdd.setVisibility(View.GONE);
        } else
        {
            tvSaleOut.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivShare:
                ShareDialogFragment.getInstance("", "").show(getSupportFragmentManager(), "");
                break;
            case R.id.ivAdd:
                if (goods.specification != null)
                {
                    orderDetailHolder.addGoods(goods);
                } else
                {
                    toastCustom(R.string.text_select_standard);
                }
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        orderDetailHolder.onActivityDestroy();
    }

    @Override
    public void onSpecificationSelected(Specification specification)
    {
        tvPrice.setText(goods.goodsPrice + "");
    }

    class ActiveAdapterAbs extends AbsBaseListAdapter<Active>
    {

        public ActiveAdapterAbs(List<Active> list)
        {
            super(list);
        }

        @Override
        protected Holder getHolder(int position)
        {
            return new ActiveHolder();
        }
    }

    class ActiveHolder extends Holder<Active>
    {
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
            setDividerVisibility(View.GONE);
            return view;
        }

        @Override
        public void refreshView(Active data)
        {

            x.image().bind(image, ServiceDispatcher.getImageUrl(data.actPhoto), GlobalApp.getInstance().getImageOptionsLogo());
            tvTitle.setText(data.actTitle);
        }

        public void setDividerVisibility(int visibility)
        {
            divider.setVisibility(visibility);
        }
    }
}
