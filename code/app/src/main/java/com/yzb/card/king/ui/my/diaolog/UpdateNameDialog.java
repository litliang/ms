package com.yzb.card.king.ui.my.diaolog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.listeners.SimpleWatcher;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;

import java.io.UnsupportedEncodingException;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/19 16:33
 */
public class UpdateNameDialog extends DialogFragment
{
    private TextView tvEnsure;
    private TextView tvCancel;
    private EditText etNickName;
    private OnItemClickListener<String> listener;
    private String nickName;

    public void setListener(OnItemClickListener<String> listener)
    {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return initDialog();
    }

    private Dialog initDialog()
    {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        dialog.setContentView(initView());
        initListener();
        initData();
        setWindow(dialog);
        return dialog;
    }

    private View initView()
    {
        View view = UiUtils.inflate(R.layout.pop_update_name);
        etNickName = (EditText) view.findViewById(R.id.etNickName);
        tvEnsure = (TextView) view.findViewById(R.id.tvEnsure);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        return view;
    }

    private void initListener()
    {

        etNickName.addTextChangedListener(new SimpleWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                try
                {
                    int len = s.toString().getBytes("GBK").length;
                    if (len > 16)
                    {
                        s = s.delete(s.length()-1,s.length());
                    }
                    LogUtil.e("length:" + s.toString().getBytes("GBK").length);
                } catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        tvEnsure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onItemClick(etNickName.getText().toString());
                }
                dismiss();
            }
        });
    }

    private void initData()
    {
        etNickName.setText(nickName);
    }

    private void setWindow(Dialog dialog)
    {
        Window window = dialog.getWindow();
        //底部弹出动画;
        window.setWindowAnimations(R.style.dialog_bottom_animation_style);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
}
