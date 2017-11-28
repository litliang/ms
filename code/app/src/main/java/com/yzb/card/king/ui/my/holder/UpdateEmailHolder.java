package com.yzb.card.king.ui.my.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.activity.BindEmailActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.common.util.MD5;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/16 10:26
 */
public class UpdateEmailHolder implements IHolder
{
    private View view;
    private BindEmailActivity bindEmailActivity;
    private EditText etOldEmail;
    private Button btUpdate;
    private EditText etPassword;
    private EditText etNewEmail;

    public UpdateEmailHolder(BindEmailActivity bindEmailActivity)
    {
        this.bindEmailActivity = bindEmailActivity;
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        view = UiUtils.inflate(R.layout.holder_update_email);
        etOldEmail = (EditText) view.findViewById(R.id.etOldEmail);
        etNewEmail = (EditText) view.findViewById(R.id.etNewEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btUpdate = (Button) view.findViewById(R.id.btUpdate);

    }

    private void initListener()
    {
        btUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (RegexUtil.validEmail(getEmail()))
                {
                    ValidPassword();
                }
            }
        });
    }

    private void ValidPassword()
    {
        SimpleRequest<String> request = new SimpleRequest<String>(CardConstant.setting_provingcustomerinfo)
        {
            @Override
            protected String parseData(String data)
            {
                return data;
            }
        };
        final Map<String, Object> param = new HashMap<>();
        param.put("type", "1");
        param.put("passwd", MD5.md5(etPassword.getText().toString()));
        param.put("provingMobile", UserManager.getInstance().getUserBean().getAccount());
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<String>()
        {
            @Override
            public void onSuccess(String data)
            {
                bindEmailActivity.updateEmail(getEmail());
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    @NonNull
    private String getEmail()
    {
        return etNewEmail.getText().toString();
    }

    private void initData()
    {
        etOldEmail.setText(UserManager.getInstance().getUserBean().getEmail());
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
