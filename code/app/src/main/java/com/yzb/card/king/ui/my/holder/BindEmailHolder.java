package com.yzb.card.king.ui.my.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.activity.BindEmailActivity;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/16 10:15
 */
public class BindEmailHolder implements IHolder
{

    private View view;
    private EditText etEmail;
    private Button btBind;
    private BindEmailActivity bindEmailActivity;

    public BindEmailHolder(BindEmailActivity bindEmailActivity)
    {
        this.bindEmailActivity = bindEmailActivity;
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        view = UiUtils.inflate(R.layout.holder_bind_email);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        btBind = (Button) view.findViewById(R.id.btBind);
    }

    private void initListener()
    {
        btBind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (RegexUtil.validEmail(getEmail()))
                {
                    bindEmailActivity.updateEmail(getEmail());
                }
            }
        });
    }

    @NonNull
    private String getEmail()
    {
        return etEmail.getText().toString();
    }

    private void initData()
    {

    }

    public View getView()
    {
        return view;
    }

    @Override
    public void setData(Object data)
    {

    }
}
