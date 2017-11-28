package com.yzb.card.king.ui.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;

/**
 * 拨打电话；
 *
 * @author gengqiyun
 * @date 2016/10/27
 */
public class PhoneDialogFragment extends BaseDialogFragment implements View.OnClickListener
{
    private static PhoneDialogFragment dialogFragment;
    private String phoneNum;
    private TextView tvDial;

    public PhoneDialogFragment setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
        return this;
    }

    public static PhoneDialogFragment getInstance()
    {
        if (dialogFragment == null)
        {
            dialogFragment = new PhoneDialogFragment();
        }
        return dialogFragment;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_dial_phone;
    }

    protected void initView(View root)
    {
        tvDial = (TextView) root.findViewById(R.id.tv_dial);
        tvDial.setText(phoneNum);
        tvDial.setOnClickListener(this);
        root.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_dial:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(getString(R.string.tel) + phoneNum));
                startActivity(intent);
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

}
