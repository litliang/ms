package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.giftcard.CardRightsInfoBean;
import com.yzb.card.king.bean.giftcard.GiftsIncrementBean;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.hotel.activtiy.CardRightsOrderActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.Utils;

/**
 * 类  名：卡权益 --- 商品明细
 * 作  者：Li Yubing
 * 日  期：2017/9/12
 * 描  述：
 */
public class GiftCombonGoodsDetailDialog extends DialogFragment implements View.OnClickListener{

    private CardRightsInfoBean item;

    private GiftsIncrementBean giftsIncrementBean;

    private String fuzengquanyi;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_fragment_dialog_giftcombn_goods_detail_dialog, container, false);
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle bundle = getArguments();

        item = (CardRightsInfoBean) bundle.get("dataDetail");

        giftsIncrementBean = (GiftsIncrementBean) bundle.get("GiftsIncrementBean");

        fuzengquanyi =  bundle.getString("fuzengquanyi");

        initView(view);

        return view;
    }

    private void initView(View view)
    {

        TextView tvGiftCardName = (TextView) view.findViewById(R.id.tvGiftCardName);

        tvGiftCardName.setText(giftsIncrementBean.getItemName());

        TextView tvNumber = (TextView) view.findViewById(R.id.tvNumber);

        tvNumber.setText(giftsIncrementBean.getItemQuantity()+"份");

        TextView tvCarQuanyiDesc = (TextView) view.findViewById(R.id.tvCarQuanyiDesc);

        String userPlace = giftsIncrementBean.getUserPlace();

        tvCarQuanyiDesc.setText(userPlace);

        TextView tvShuoming = (TextView) view.findViewById(R.id.tvShuoming);

        tvShuoming.setText(giftsIncrementBean.getUseCondition());

        view.findViewById(R.id.btOne).setOnClickListener(this);

        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        view.findViewById(R.id.llTemp).setOnClickListener(this);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("商品明细");

        TextView tvPayTypeMsg = (TextView) view.findViewById(R.id.tvPayTypeMsg);

        tvPayTypeMsg.setText("特惠价");

        TextView tvRoomComboPrice = (TextView) view.findViewById(R.id.tvRoomComboPrice);

        tvRoomComboPrice.setText(Utils.subZeroAndDot(item.getOnlinePrice()+""));

        TextView tvRturnMoney = (TextView) view.findViewById(R.id.tvRturnMoney);

        String storePrice = Utils.subZeroAndDot(item.getStorePrice()+"");

        String tempStr = "原价       ¥";

        SpannableString ss = new SpannableString(tempStr+storePrice);

        ss.setSpan(new StrikethroughSpan(), ss.length()-storePrice.length(),ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvRturnMoney.setText(ss);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvConfirm:
                //检查是否登录
                if (UserManager.getInstance().isLogin()) {

                    Intent intent = new Intent(getActivity(), CardRightsOrderActivity.class);

                    intent.putExtra("dataDetail",item);

                    intent.putExtra("fuzengquanyi", fuzengquanyi);

                    getActivity().startActivity(intent);

                    dismiss();

                }else {

                    new GoLoginDailog(getActivity()).create().show();
                }

                break;
            case R.id.llTemp:

                dismiss();

                break;

            default:
                break;
        }
    }
}

