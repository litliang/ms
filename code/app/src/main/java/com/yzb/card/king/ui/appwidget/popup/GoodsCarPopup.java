package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.discount.bean.GoodsCarItem;
import com.yzb.card.king.util.UiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/14 9:31
 * 描述：
 */
public class GoodsCarPopup extends PopupWindow implements View.OnClickListener {
    private static GoodsCarPopup goodsCarPopup;
    private View rootView;
    private TextView tvNum;
    private View llDelete;
    private ListView listView;
    private List<GoodsCarItem> dataList = new ArrayList<>();
    private GoodsCarAdapterAbs adapter;
    private View vBlank;
    private int goodsNum = 0;

    private GoodsCarPopup() {
        initView();
        initData();
        init();
    }

    public static GoodsCarPopup getGoodsCarPopup() {
        if (goodsCarPopup == null) {
            goodsCarPopup = new GoodsCarPopup();
        }
        return goodsCarPopup;
    }

    private void initView() {
        rootView = UiUtils.inflate(R.layout.popwindow_goods_car);
        tvNum = (TextView) rootView.findViewById(R.id.tvNum);
        llDelete = rootView.findViewById(R.id.llDelete);
        listView = (ListView) rootView.findViewById(R.id.listView);
        vBlank = rootView.findViewById(R.id.vBlank);

        adapter = new GoodsCarAdapterAbs(dataList);
        listView.setAdapter(adapter);

        llDelete.setOnClickListener(this);
        vBlank.setOnClickListener(this);
    }

    private void initData() {
//        initCar();
    }

    private void init() {
        this.setContentView(rootView);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        //
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popwin_anim_style);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llDelete://清空购物车
                clearGoods();
                break;
            case R.id.vBlank:
                dismiss();
                break;
        }
    }

    public void clearGoods() {
        dataList.clear();
        adapter.notifyDataSetChanged();
        goodsNum = 0;
        evaluatePrice();
        goodsNumChanged();
    }

    public int getNum() {
        return goodsNum;
    }

    public String getStoreName() {
        Map<String, String> map = new HashMap<>();
        String storeId;
        String storeName = "";
        for (GoodsCarItem item : dataList) {
            storeId = item.goods.storeId;
            storeName = item.goods.storeName;
            map.put(storeId, storeName);
        }
        if (map.size() > 1) {
            storeName = "多种商铺";
        }
        return storeName;
    }

    class GoodsCarAdapterAbs extends AbsBaseListAdapter<GoodsCarItem> {

        public GoodsCarAdapterAbs(List<GoodsCarItem> list) {
            super(list);
        }

        @Override
        protected Holder getHolder(int position) {
            return new GoodsCarHolder();
        }
    }

    class GoodsCarHolder extends Holder<GoodsCarItem> implements View.OnClickListener {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvGoodsNum;
        private ImageView ivReduce;
        private ImageView ivAdd;
        private GoodsCarItem item;

        @Override
        public View initView() {
            View view = UiUtils.inflate(R.layout.item_goods_car);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvGoodsNum = (TextView) view.findViewById(R.id.tvGoodsNum);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);

            ivReduce = (ImageView) view.findViewById(R.id.ivReduce);
            ivAdd = (ImageView) view.findViewById(R.id.ivAdd);

            ivReduce.setOnClickListener(this);
            ivAdd.setOnClickListener(this);
            return view;
        }

        @Override
        public void refreshView(GoodsCarItem data) {
            item = data;
            tvGoodsNum.setText(data.goodsNum + "");
            tvName.setText(data.goods.goodsName);
            tvPrice.setText(String.valueOf(data.goods.goodsPrice));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivReduce:
                    item.goodsNum--;
                    goodsNum--;
                    if (item.goodsNum == 0)
                        dataList.remove(item);
                    break;
                case R.id.ivAdd:
                    if (item.goodsNum + 1 > item.specification.stockNum) {
                        UiUtils.shortToast("存货就这么多了");
                        return;
                    } else {
                        item.goodsNum++;
                        goodsNum++;
                    }
                    break;
            }
            adapter.notifyDataSetChanged();
            evaluatePrice();
            goodsNumChanged();
        }
    }

    public void addGoods(Goods goods) {
        boolean contains = false;
        Goods newGoods = copyGoods(goods);
        for (GoodsCarItem item : dataList) {
            if (item.goods.goodsId.equals(newGoods.goodsId) && item.goods.specification.specId.equals(newGoods.specification.specId)) {
                if (item.goodsNum + 1 > goods.specification.stockNum) {
                    UiUtils.shortToast("存货就这么多了");
                    return;
                } else {
                    item.goodsNum++;
                }
                contains = true;
            }
        }
        if (!contains) {
            dataList.add(new GoodsCarItem(newGoods, 1, goods.specification));
        }
        goodsNum++;
        adapter.notifyDataSetChanged();
        evaluatePrice();
        goodsNumChanged();
    }

    private String amount = "0.00";

    private void evaluatePrice() {
        float price = 0;
        for (GoodsCarItem item : dataList) {
            price += item.goodsNum * item.goods.goodsPrice;
        }
        DecimalFormat df = new DecimalFormat("###.00");
        amount = df.format(price);
//        amount = Float.parseFloat(price);
        if (listeners.size() > 0) {
            for (OnTotalAmountChangeListener listener : listeners)
                listener.onAmountChange(amount);
        }
    }

    public String getAmount() {
        return this.amount;
    }

    private void goodsNumChanged() {

        if (goodsNum == 0) {
            tvNum.setVisibility(View.GONE);
            dismiss();
        } else {
            tvNum.setVisibility(View.VISIBLE);
        }
        tvNum.setText(goodsNum + "");
        if (listeners.size() > 0) {
            for (OnTotalAmountChangeListener listener : listeners)
                listener.onGoodsNumChange(goodsNum);
        }
    }

    private LinkedList<OnTotalAmountChangeListener> listeners = new LinkedList<>();


    public void setOnTotalAmountChangeListener(OnTotalAmountChangeListener listener) {
        listeners.add(listener);
    }

    public void unsubscribeListener(OnTotalAmountChangeListener listener) {
        listeners.remove(listener);
    }

    public interface OnTotalAmountChangeListener {
        void onAmountChange(String amount);

        void onGoodsNumChange(int num);
    }

    private Goods copyGoods(Goods goods) {
        Goods newGoods =  JSON.parseObject(JSON.toJSONString(goods),Goods.class);
        return newGoods;
    }

    public void saveCar() {
//        SaveCarTask saveCarTask  = new SaveCarTask();
//        saveCarTask.execute();
    }

    public void initCar() {
//        InitCarTask initCarTask = new InitCarTask();
//        initCarTask.execute();
    }


//
//    class InitCarTask extends CommentTask {
//
//
//        public InitCarTask() {
//            super("ShoppingCartQuery");
//        }
//
//        @Override
//        protected void parseJson(String data) {
//            List<GoodsCarItem> list = JSON.parseArray(data, GoodsCarItem.class);
//            dataList.clear();
//            dataList.addAll(list);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected void setParam(Map<String, String> param) {
//        }
//    }
}
