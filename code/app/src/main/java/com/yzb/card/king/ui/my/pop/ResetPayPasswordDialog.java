package com.yzb.card.king.ui.my.pop;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.WalletRequestUtil;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.ToastUtil;

import org.xutils.common.util.DensityUtil;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/16
 * 描  述：
 */
public class ResetPayPasswordDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final int WHAT_LOOK = 0x005;
    private Handler dataHandler;

    private EditText etOne,etTwo;

    private ImageButton ibPasswordVisible;

    //数据
    private boolean isVisible = false;

    private WalletRequestUtil walletRequestUtil;
    @Override
    protected int getLayoutResId()
    {
        return R.layout.dialog_resetpay_password;
    }

    public static ResetPayPasswordDialog getInstance()
    {
        return new ResetPayPasswordDialog();
    }

    @Override
    protected int getGravity()
    {
        return Gravity.TOP;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.y = DensityUtil.dip2px(103); //距离顶部的距离；
        window.setAttributes(wl);
        dialog.setTitle("手机支付密码");
        return dialog;
    }

    @Override
    protected int getDialogWidth()
    {
        return DensityUtil.getScreenWidth() - DensityUtil.dip2px(90);
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected void initView(View view)
    {
        view.findViewById(R.id.tvConfirm).setOnClickListener(this);

        etOne = (EditText) view.findViewById(R.id.etOne);

        etTwo = (EditText) view.findViewById(R.id.etTwo);

        ibPasswordVisible = (ImageButton) view.findViewById(R.id.ibPasswordVisible );

        ibPasswordVisible.setOnClickListener(this);
        setPasswordVisible(isVisible);

        walletRequestUtil = new WalletRequestUtil(null);

    }

    @Override
    protected int getDialogStyle()
    {
        return 0;
    }

    public ResetPayPasswordDialog setDataHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
        return this;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.tvConfirm://查看；
                if(chechData()){
                    String str3 = etOne.getText().toString().trim();
                    UserBean bean = UserManager.getInstance().getUserBean();
                    walletRequestUtil.sendSetPayPassword(bean.getAmountAccount(), str3,activity,payHandler);
                }

                break;
            case R.id.ibPasswordVisible:

                if (isVisible)
                {

                    isVisible = false;
                } else
                {
                    isVisible = true;
                }

                setPasswordVisible(isVisible);
                break;
            default:
                break;
        }
    }

    public void sendMsg(int what)
    {
        if (dataHandler != null) {
            Message msg = dataHandler.obtainMessage(what);
            dataHandler.sendMessage(msg);
        }
    }

    /**
     * 设置密码显示状态
     *
     * @param isChecked
     */
    private void setPasswordVisible(boolean isChecked)
    {

        if (isChecked)
        {
            // 显示密码


            etOne
                    .setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            etOne.setInputType(InputType.TYPE_CLASS_NUMBER);

            etTwo
                    .setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            etTwo.setInputType(InputType.TYPE_CLASS_NUMBER);

            ibPasswordVisible.setBackgroundResource(R.mipmap.reset_eye_open);
        } else
        {
            // 隐藏密码
            etOne.setInputType(InputType.TYPE_CLASS_NUMBER);
            etOne.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());
            etTwo.setInputType(InputType.TYPE_CLASS_NUMBER);
            etTwo.setTransformationMethod(PasswordTransformationMethod
                    .getInstance());


            ibPasswordVisible.setBackgroundResource(R.mipmap.reset_eye_closed);
        }
    }

    private boolean chechData()
    {

        boolean flag = true;

        int str = 0;

        String str2 = etTwo.getText().toString().trim();

        String str3 = etOne.getText().toString().trim();


       if (TextUtils.isEmpty(str3) ||TextUtils.isEmpty(str2))
        {

            flag = false;

            str = R.string.hint_set_pay_password;

        }   else if (str2.length()!= 6)
        {
            flag = false;

            str = R.string.hint_set_pay_password_length_six;
        }else  if(!str2.equals(str3)){

           flag = false;
           str = R.string.hint_set_pay_password_no_same;

       }

        if (!flag)
        {
            Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
        }

        return flag;
    }

    private  Handler payHandler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            int  what = msg.what;
             if(what == 0){//设置成功

                 ToastUtil.i(activity,"支付密码设置成功");
                 dismiss();

             }else if(what == 1){//设置失败
                 ToastUtil.i(activity,"支付密码设置失败");
             }
        }
    };

}
