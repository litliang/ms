package com.yzb.card.king.ui.appwidget;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;

/**
 * 类  名：数字软键盘
 * 作  者：Li Yubing
 * 日  期：2017/1/17
 * 描  述：
 */
public class NumberKeypadView {

    private View view;

    private Activity context;

    private EditText getAmount;

    private Handler keyHandler;

    private ImageView key0, key00, key01, key02, key03, key04, key05, key06,
            key07, key08, key09, keyQx, keyQc, keyQr, keyPoint;

    public NumberKeypadView(View view, Activity context)
    {

        this.view = view;

        this.context = context;

        keyBoard(textKeyboardClicker);
    }

    public void setAmount(EditText getAmount)
    {

        this.getAmount = getAmount;

    }

    public void setKeyHandler(Handler keyHandler)
    {

        this.keyHandler = keyHandler;
    }

    private void keyBoard(View.OnClickListener listener)
    {

        key0 = (ImageView) view.findViewById(R.id.key0);
        key00 = (ImageView) view.findViewById(R.id.key00);
        key01 = (ImageView) view.findViewById(R.id.key01);
        key02 = (ImageView) view.findViewById(R.id.key02);
        key03 = (ImageView) view.findViewById(R.id.key03);
        key04 = (ImageView) view.findViewById(R.id.key04);
        key05 = (ImageView) view.findViewById(R.id.key05);
        key06 = (ImageView) view.findViewById(R.id.key06);
        key07 = (ImageView) view.findViewById(R.id.key07);
        key08 = (ImageView) view.findViewById(R.id.key08);
        key09 = (ImageView) view.findViewById(R.id.key09);
        keyQx = (ImageView) view.findViewById(R.id.keyQx);
        keyQc = (ImageView) view.findViewById(R.id.keyQc);
        keyQr = (ImageView) view.findViewById(R.id.keyQr);
        keyPoint = (ImageView) view.findViewById(R.id.keyPoint);

        key0.setOnClickListener(listener);
        key00.setOnClickListener(listener);
        key01.setOnClickListener(listener);
        key02.setOnClickListener(listener);
        key03.setOnClickListener(listener);
        key04.setOnClickListener(listener);
        key05.setOnClickListener(listener);
        key06.setOnClickListener(listener);
        key07.setOnClickListener(listener);
        key08.setOnClickListener(listener);
        key09.setOnClickListener(listener);
        keyQx.setOnClickListener(listener);
        keyQc.setOnClickListener(listener);
        keyQr.setOnClickListener(listener);
        keyPoint.setOnClickListener(listener);

        key0.setTag(0);
        key00.setTag(0);
        key01.setTag(1);
        key02.setTag(2);
        key03.setTag(3);
        key04.setTag(4);
        key05.setTag(5);
        key06.setTag(6);
        key07.setTag(7);
        key08.setTag(8);
        key09.setTag(9);
        keyPoint.setTag(".");

    }

    /**
     * 金额输入
     */
    private View.OnClickListener textKeyboardClicker = new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {

            if (getAmount == null) {

                if (R.id.keyPoint != v.getId()) {

                    Message message = keyHandler.obtainMessage();

                    message.what = v.getId();

                    message.obj = v.getTag() == null ? null: v.getTag().toString();

                    keyHandler.sendMessage(message);
                }

            } else {


                String input = getAmount.getText().toString();

                if (v.getId() == R.id.keyQc) {//清除
                    getAmount.setText("");
                } else if (v.getId() == R.id.keyQr) {//确认，传数据并跳转到二维码页面

                    if (StringUtils.isEmpty(input)) {
                        ToastUtil.i(context, "请输入金额");
                        return;
                    }

                    keyHandler.sendEmptyMessage(0);
                } else if (v.getId() == R.id.keyQx) {//取消
                    context.finish();
                } else {//输入
                    String text = input + v.getTag().toString();

                    if(v.getId() == R.id.key00&& StringUtils.isEmpty(input)){
                        getAmount.setText("");
                        return;
                    }

                    if (v.getId() == R.id.key0  && text.length()==2 && "00".equals(text)) {
                        return;
                    }

                    if(text.length()==3 && "000".equals(text)){
                        return;
                    }

                    //输入00
                    if (v.getId() == R.id.key00)
                        text += "0";
                    //控制小数点输入
                    if (v.getId() == R.id.keyPoint) {
                        int dos = text.indexOf(".");
                        int dosLast = text.lastIndexOf(".");
                        if (dos != dosLast)
                            return;
                    }
                    getAmount.setText(text);
                }
            }
        }
    };
}
