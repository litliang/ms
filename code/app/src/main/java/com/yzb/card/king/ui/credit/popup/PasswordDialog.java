package com.yzb.card.king.ui.credit.popup;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jungly.gridpasswordview.GridPasswordView;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.WalletConstant;
import com.yzb.card.king.ui.app.activity.VerifyIdentificationNeedBankActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.logic.comm.PaypswdValidateLogic;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/6 12:12
 */
public class PasswordDialog extends DialogFragment
{
    private GridLayout gridLayout;
    private GridPasswordView inputPwd;
    private StringBuilder password = new StringBuilder();
    private OnPwdCheckListener listener;
    private View waiting;
    private ProgressBar progressBar;
    private TextView payTitle;
    private View pwdCancel;
    private View payPwdForget;

    public void setListener(OnPwdCheckListener listener)
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
        View view = UiUtils.inflate(R.layout.pop_password);
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        inputPwd = (GridPasswordView) view.findViewById(R.id.inputPwd);
        waiting = view.findViewById(R.id.waiting);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        payTitle = (TextView) view.findViewById(R.id.payTitle);
        pwdCancel = view.findViewById(R.id.pwdCancel);
        payPwdForget = view.findViewById(R.id.payPwdForget);
        return view;
    }

    private void initListener()
    {
        int count = gridLayout.getChildCount();
        for (int i = 0; i < count; i++)
        {
            gridLayout.getChildAt(i).setOnClickListener(new OnKeyDownListener());
        }

        pwdCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        payPwdForget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UiUtils.startActivity(VerifyIdentificationNeedBankActivity.class);
            }
        });
    }

    private class OnKeyDownListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            TextView textView = (TextView) v;
            String text = textView.getText().toString();
            if (TextUtils.isEmpty(text))
            {
                if (password.length() > 0)
                {
                    password.delete(password.length() - 1, password.length());
                }
            } else
            {
                if (password.length() < 6)
                {
                    password.append(text);
                }
            }
            inputPwd.setPassword(password.toString());
            if (password.length() == 6)
            {
                waiting.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.GONE);
                setActionText("正在校验密码...");
                checkPassword(password.toString());
            }
        }
    }

    public void setActionText(String actionText)
    {
        payTitle.setText(actionText);
    }

    private void checkPassword(String payPwd)
    {
        // 刑琰接口---- 支付密码效验
        PaypswdValidateLogic payHandle = new PaypswdValidateLogic(getActivity());
        payHandle.setCallBack(new WalletBackListener()
        {
            @Override
            public void setSuccess(String RESULT_CODE)
            {
                if (listener != null)
                {
                    // 验证密码成功
                    if (RESULT_CODE.equals(AppConstant.CODE_OK))
                    {
                        listener.onPwdRight();
                    } else
                    {
                        listener.onPwdWrong();
                    }
                }
            }

            @Override
            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE)
            {
                if (listener != null)
                {
                    listener.onPwdRight();
                }
            }

            @Override
            public void setError(String RESULT_CODE, String ERROR_MSG)
            {
                UiUtils.shortToast("密码错误");
                clearPassword();
                gridLayout.setVisibility(View.VISIBLE);
                waiting.setVisibility(View.GONE);

                if (listener != null)
                {
                    listener.onPwdWrong();
                }
            }
        });

        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", UserManager.getInstance().getUserBean().getAccount());
        params.put("payPasswd", payPwd);
        params.put("merchantNo", WalletConstant.MERCHANT_NO);
        params.put("sign", WalletConstant.SIGN);
        LogUtil.e("密码校验参数：" + JSON.toJSONString(params));
        payHandle.validate(params);
    }


    private void initData()
    {
        waiting.setVisibility(View.GONE);
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


    public interface OnPwdCheckListener
    {
        void onPwdRight();

        void onPwdWrong();
    }

    @Override
    public void dismiss()
    {
        clearPassword();
        super.dismiss();
    }

    private void clearPassword()
    {
        inputPwd.clearPassword();
        password = new StringBuilder();
    }
}
