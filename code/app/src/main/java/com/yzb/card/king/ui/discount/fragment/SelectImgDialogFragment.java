package com.yzb.card.king.ui.discount.fragment;

import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.ui.base.BaseDialogFragment;

/**
 * Created by gengqiyun on 2016/4/20.
 * 图片选择对话框；
 */
public class SelectImgDialogFragment extends BaseDialogFragment implements View.OnClickListener
{
    private static SelectImgDialogFragment dialogFragment;
    private IDialogCallBack iDialogCallBack;

    public static SelectImgDialogFragment getInstance(String arg1, String arg2)
    {

        synchronized (SelectImgDialogFragment.class)
        {
            if (dialogFragment == null)
            {
                dialogFragment = new SelectImgDialogFragment();
            }
        }
        return dialogFragment;
    }

    public SelectImgDialogFragment setCallBack(IDialogCallBack iDialogCallBack)
    {
        this.iDialogCallBack = iDialogCallBack;
        return  this;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_getimg_dialog;
    }

    @Override
    protected void initView(View view)
    {
        if (view != null)
        {
            view.findViewById(R.id.tv_pz).setOnClickListener(this);
            view.findViewById(R.id.tv_xc).setOnClickListener(this);
            view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v)
    {
        dismiss();
        switch (v.getId())
        {
            case R.id.tv_pz:
                if (iDialogCallBack != null)
                {
                    iDialogCallBack.dialogCallBack(0);
                }
                break;
            case R.id.tv_xc:
                if (iDialogCallBack != null)
                {
                    iDialogCallBack.dialogCallBack(1);
                }
                break;
            case R.id.tv_cancel:
                break;
        }

    }
}
