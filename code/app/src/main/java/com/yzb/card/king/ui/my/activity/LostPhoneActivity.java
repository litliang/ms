package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.popup.IdentificationsPopup;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：手机丢失-身份认证
 * 作者：殷曙光
 * 日期：2016/12/30 14:13
 */
@ContentView(R.layout.activity_lost_phone)
public class LostPhoneActivity extends BaseActivity
{

    private static final int REQ_NEW_MOBILE = 2;

    @ViewInject(R.id.etName)
    private EditText etName;

    @ViewInject(R.id.etCertNo)
    private EditText etCertNo;

    @ViewInject(R.id.etPassword)
    private EditText etPassword;

    @ViewInject(R.id.tvCertType)
    private TextView tvCertType;

    @ViewInject(R.id.ivTriangle)
    private ImageView ivTriangle;

    private String password;
    private CertType certType;
    private String certNo;
    private String name;
    private IdentificationsPopup popup;

    public void setCertType(CertType certType)
    {
        this.certType = certType;
        tvCertType.setText(certType.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData()
    {
        setDefaultHeader("重置手机");
    }

    @Event(R.id.llCertType)
    private void certType(View v)
    {
        if (popup == null)
        {
            popup = new IdentificationsPopup(new OnItemClickListener<CertType>()
            {
                @Override
                public void onItemClick(CertType data)
                {
                    setCertType(data);
                }
            });
            popup.setWidth(v.getWidth());
        }
        popup.showAsDropDown(v, 0, 5);
    }

    @Event(R.id.btNext)
    private void next(View v)
    {
        if (validData())
        {
            sendRequest();
        }
    }

    private boolean validData()
    {
        return validName() && validCertType() && validCertNo() && validPassword();
    }

    private boolean validPassword()
    {
        password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            UiUtils.shortToast("请输入密码");
            return false;

        } else
        {
            return true;
        }
    }

    private boolean validCertNo()
    {
        certNo = etCertNo.getText().toString();
        if (TextUtils.isEmpty(certNo))
        {
            UiUtils.shortToast("请输入证件号");
            return false;

        } else
        {
            return true;
        }
    }

    private boolean validCertType()
    {
        if (certType == null)
        {
            UiUtils.shortToast("请选择证件类型");
            return false;
        } else
        {
            return true;
        }
    }

    private boolean validName()
    {
        name = etName.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            UiUtils.shortToast("请输入姓名");
            return false;

        } else
        {
            return true;
        }
    }

    private void sendRequest()
    {
        SimpleRequest<String> request = new SimpleRequest<String>(CardConstant.setting_provingcustomerinfo)
        {
            @Override
            protected String parseData(String data)
            {
                return data;
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("type", "2");//2,证件；1，密码
        param.put("name", name);
        param.put("passwd", password);
        param.put("certType", certType.getType());
        param.put("certNo", certNo);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<String>()
        {
            @Override
            public void onSuccess(String data)
            {
                if ("1".equals(data))
                {
                    startActivityForResult(new Intent(LostPhoneActivity.this, NewMobileActivity.class),
                            REQ_NEW_MOBILE);
                } else
                {
                    UiUtils.shortToast("验证不通过");
                }
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast("验证失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_NEW_MOBILE:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
