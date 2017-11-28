package com.yzb.card.king.ui.discount.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.discount.activity.GoodsDetailActivity;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.bean.Specification;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.util.UiUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GoodsHolder extends Holder<Goods> {

    private ImageView ivPhoto;
    private ImageView ivAdd;
    private TextView tvName, tvSize, tvPrice, tvSaleNum;
    private Goods goods;
    private List<View> views;
    private TextView ivAdd1, ivAdd2;
    private View view;
    private OrderDetailHolder orderDetailHolder;

    public GoodsHolder(OrderDetailHolder orderDetailHolder) {
        this.orderDetailHolder = orderDetailHolder;
    }

    @Override
    public View initView() {
        view = UiUtils.inflate(R.layout.item_shop_goods);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        ivAdd = (ImageView) view.findViewById(R.id.ivAdd);
        ivAdd1 = (TextView) view.findViewById(R.id.ivAdd1);
        ivAdd2 = (TextView) view.findViewById(R.id.ivAdd2);
        List<View> views = new ArrayList<>();
        views.add(ivAdd);
        views.add(ivAdd1);
        views.add(ivAdd2);
        this.views = views;
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvSize = (TextView) view.findViewById(R.id.tvSize);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvSaleNum = (TextView) view.findViewById(R.id.tvSaleNum);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderDetailHolder.addGoods(goods);
            }
        });
        ivAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    UiUtils.shortToast("选规格");
                Intent intent = new Intent(UiUtils.getContext(), GoodsDetailActivity.class);
                intent.putExtra("goods", goods);
                UiUtils.startActivity(intent);
            }
        });
        ivAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.shortToast("售罄");
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UiUtils.getContext(), GoodsDetailActivity.class);
                intent.putExtra("goods", goods);
                UiUtils.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void refreshView(Goods data) {
        DecimalFormat df = new DecimalFormat("###.00");
        goods = data;

        ImageOptions imageOptions = new ImageOptions.Builder()
                //.setSize(imageWith, imageHeight)
                .setRadius(org.xutils.common.util.DensityUtil.dip2px(ivPhoto.getWidth() / 2))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .build();
        x.image().bind(ivPhoto, ServiceDispatcher.getImageUrl(data.goodsPhoto), imageOptions);

        tvName.setText(data.goodsName);
        if (data.specList.size() > 0)
            data.specification = data.specList.get(0);
        if (data.specification != null) {
            tvPrice.setText(df.format(data.specification.specPrice));
            tvSize.setText(data.specification.specName);
        } else {
            tvPrice.setText("");
            tvSize.setText("");
        }
        tvSaleNum.setText(data.monthSales + "");
        setVisibility(data.stockNum <= 0, data.specList);
    }

    private void setVisibility(boolean isSaleOut, List<Specification> specifications) {

        if (isSaleOut) {
            setVisibleView(ivAdd2);//售罄
        } else {
            if (specifications.size() > 1) {//选规格
                setVisibleView(ivAdd1);
            } else if (specifications.size() == 1) {
                setVisibleView(ivAdd);//添加
            } else {
                setVisibleView(null);
            }
        }
    }

    private void setVisibleView(View visibleView) {
        for (View view : views) {
            if (view == visibleView) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

}