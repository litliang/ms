package com.yzb.card.king.ui.bonus.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

/**
 * Created by gengqiyun on 2017/2/13.
 * 领取红包成功的对话框；
 */
public class RecvBounsSucDialog extends BaseDialogFragment implements View.OnClickListener
{
    private String bounsAmount; //红包金额；
    private View.OnClickListener onClickListener;

    public RecvBounsSucDialog setBounsAmount(String bounsAmount)
    {
        this.bounsAmount = bounsAmount;
        return this;
    }

    public static RecvBounsSucDialog getInstance()
    {
        return new RecvBounsSucDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_recv_bouns_suc_dialog;
    }

    @Override
    protected void initView(View view)
    {
        view.findViewById(R.id.tvScanDetail).setOnClickListener(this);
        view.findViewById(R.id.tvContinue).setOnClickListener(this);

        TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);
        SpannableString ss = new SpannableString(Utils.subZeroAndDot(bounsAmount) + "元");
        ss.setSpan(new AbsoluteSizeSpan(CommonUtil.sp2px(getContext(), 48)), 0, ss.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvAmount.setText(ss);
    }

    public RecvBounsSucDialog setCallBack(View.OnClickListener onClickListener)
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
