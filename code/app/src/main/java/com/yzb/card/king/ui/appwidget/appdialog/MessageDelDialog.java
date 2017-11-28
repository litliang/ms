package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yzb.card.king.R;

/**
 * 类 名： 自定义消息删除的弹出框
 * 作 者： gaolei
 * 日 期：2016年12月9日
 * 描 述：消息删除的弹出框
 */

public class MessageDelDialog extends Dialog {

    public MessageDelDialog(Context context) {
        super(context,R.style.Message_Del_Dialog);
    }

    private Button noBtn;
    private Button yesBtn;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message_del);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick("确定");
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick("取消");
                }
            }
        });
    }

    private void initView() {
        yesBtn = (Button) findViewById(R.id.message_yesdel_btn);
        noBtn = (Button) findViewById(R.id.message_nodel_btn);
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick(String str);
    }

    public interface onNoOnclickListener {
        void onNoClick(String dtr);
    }

}
