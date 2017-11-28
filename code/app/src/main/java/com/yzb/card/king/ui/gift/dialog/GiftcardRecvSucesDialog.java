package com.yzb.card.king.ui.gift.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.util.Utils;

import org.xutils.common.util.DensityUtil;

/**
 * 功能：礼品卡领取成功dialog；
 *
 * @author:gengqiyun
 * @date: 2016/12/15
 */
public class GiftcardRecvSucesDialog extends BaseDialogFragment implements View.OnClickListener
{
    private String amount; //领取的金额；
    private View.OnClickListener onClickListener;

    public GiftcardRecvSucesDialog setAmount(String amount)
    {
        this.amount = amount;
        return this;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_recv_giftcard_suces;
    }

    public static GiftcardRecvSucesDialog getInstance()
    {
        return new GiftcardRecvSucesDialog();
    }

    @Override
    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    protected int getDialogWidth()
    {
        return DensityUtil.getScreenWidth() - DensityUtil.dip2px(30);
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected void initView(View view)
    {

        view.findViewById(R.id.tvScanDetail).setOnClickListener(this);
        view.findViewById(R.id.tvContinue).setOnClickListener(this);

        TextView giftcardAmount = (TextView) view.findViewById(R.id.giftcardAmount);

        SpannableString ss = new SpannableString(Utils.subZeroAndDot(amount) + "元");
        int yuanLen = "元".length();
        ss.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(25)), 0, ss.length() - yuanLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        giftcardAmount.setText(ss);
    }

    @Override
    protected int getDialogStyle()
    {
        return 0;
    }

    public GiftcardRecvSucesDialog setCallBack(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
        return this;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvScanDetail:
                if (onClickListener != null)
                {
                    onClickListener.onClick(v);
                }
                break;
            case R.id.tvContinue:
                break;
        }
        dismiss();
    }
}
