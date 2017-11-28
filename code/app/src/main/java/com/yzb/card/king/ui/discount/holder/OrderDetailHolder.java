package com.yzb.card.king.ui.discount.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.appwidget.popup.GoodsCarPopup;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.bean.Goods;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/14 18:27
 * 描述：
 */
public class OrderDetailHolder extends Holder implements View.OnClickListener,
        GoodsCarPopup.OnTotalAmountChangeListener, PopupWindow.OnDismissListener {
    private ImageView ivArrow;
    private TextView tvNum;
    private View orderDetailsLayout;
    private TextView tvTotalAmount;
    private GoodsCarPopup goodsCarPopup;
    private View view;
    private Activity activity;
    private TextView tvSubmit;
    private String amount;

    public OrderDetailHolder(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public View initView() {
        view = UiUtils.inflate(R.layout.layout_order_detail);
        tvTotalAmount = (TextView) view.findViewById(R.id.tvTotalAmount);
        tvNum = (TextView) view.findViewById(R.id.tvNum);
        ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        orderDetailsLayout = view.findViewById(R.id.orderDetailsLayout);
        tvSubmit = (TextView) view.findViewById(R.id.tvSubmit);

        orderDetailsLayout.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

        goodsCarPopup = GoodsCarPopup.getGoodsCarPopup();
        goodsCarPopup.setOnTotalAmountChangeListener(this);
        goodsCarPopup.setOnDismissListener(this);
        onAmountChange(goodsCarPopup.getAmount()+"");
        onGoodsNumChange(goodsCarPopup.getNum());
        return view;
    }

    @Override
    public void refreshView(Object data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderDetailsLayout://显示购物车
                if (goodsCarPopup.isShowing()) {
                    goodsCarPopup.dismiss();
                    ivArrow.setEnabled(true);
                } else {
                    goodsCarPopup.setHeight(Utils.getDisplayHeight(activity) - orderDetailsLayout.getHeight()-1);
                    goodsCarPopup.showAtLocation(view, Gravity.TOP, 0, 0);
                    ivArrow.setEnabled(false);
                }
                break;
            case R.id.tvSubmit:
                submit();
                break;
        }
    }

    @Override
    public void onAmountChange(String amount) {
        this.amount = amount;
        tvTotalAmount.setText(amount);
    }

    @Override
    public void onGoodsNumChange(int num) {
        if (num == 0) {
            tvNum.setVisibility(View.GONE);
        } else {
            tvNum.setVisibility(View.VISIBLE);
        }
        tvNum.setText(num + "");
    }

    @Override
    public void onDismiss() {
        ivArrow.setEnabled(true);
    }

    public void addGoods(Goods goods) {
        goodsCarPopup.addGoods(goods);
    }

    /**
     * 提交订单
     *
     * @author ysg
     * created at 2016/7/12 15:10
     */
    private void submit() {
        if (UserManager.getInstance().isLogin()) {
            if(goodsCarPopup.getNum()>0) {
                Intent intent = new Intent(activity, DiscountPayActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("storeName", goodsCarPopup.getStoreName());
                UiUtils.startActivity(intent);
            }else {
                UiUtils.shortToast("请选择商品");
            }
        } else {
            new GoLoginDailog(activity).show();
        }
    }
    /**
     *
     *
     *@author ysg
     *created at 2016/7/18 16:58
     */
    public void onActivityDestroy(){
        goodsCarPopup.unsubscribeListener(this);
        goodsCarPopup.saveCar();
    }
}
