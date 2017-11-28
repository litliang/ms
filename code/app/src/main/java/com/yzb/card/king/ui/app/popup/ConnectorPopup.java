package com.yzb.card.king.ui.app.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.interfaces.ConnectorListener;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：添加、修改联系人弹窗
 * 作者：殷曙光
 * 日期：2016/6/27 14:40
 * 描述：
 */
public class ConnectorPopup extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private EditText etName;
    private EditText etMobile;
    private TextView tvSubmit;
    private TextView tvTitle;
    private ConnectorListener listener;
    private Connector connector;
    private RelativeLayout rlClose;
    private Activity context;

    public ConnectorPopup(Activity context,Connector connector,ConnectorListener listener) {
        this.connector = connector;
        this.listener = listener;
        this.context = context;
        initView();
        initData();
        init();
    }

    private void initView() {
        rootView = UiUtils.inflate(R.layout.popwindow_contactor);
        etName = (EditText) rootView.findViewById(R.id.etName);
        etMobile = (EditText) rootView.findViewById(R.id.etMobile);
        tvSubmit = (TextView) rootView.findViewById(R.id.tvSubmit);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        rlClose = (RelativeLayout) rootView.findViewById(R.id.rlClose);


        tvSubmit.setOnClickListener(this);
        rlClose.setOnClickListener(this);
        rootView.setOnClickListener(this);
    }

    private void initData() {
        if(connector == null){
            tvTitle.setText("添加联系人");
            etMobile.setText("");
            etName.setText("");
        }else {
            tvTitle.setText("修改联系人");
            etMobile.setText(connector.mobile);
            etName.setText(connector.nickName);
        }
    }

    private void init() {
        this.setSoftInputMode(INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(rootView);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        backgroundAlpha(context,0.5f);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlClose:
                dismiss();
                break;
            case R.id.tvSubmit:
                submit();
                break;
        }
    }

    private void submit() {
        String name = String.valueOf(etName.getText());
        String mobile = String.valueOf(etMobile.getText());
        if(TextUtils.isEmpty(name)){
            UiUtils.shortToast("昵称不能为空");
            return;
        }
        if(!RegexUtil.validPhoneNum(mobile)){
            return;
        }

        if(connector == null) {
            connector = new Connector();
        }
        connector.mobile = mobile;
        connector.nickName = name;
        connector.type = "2";
        if(listener != null){
            listener.onUpdate(connector);
            dismiss();
        }
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        backgroundAlpha(context,1f);
    }
}
