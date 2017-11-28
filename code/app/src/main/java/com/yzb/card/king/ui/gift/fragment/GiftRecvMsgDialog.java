package com.yzb.card.king.ui.gift.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.KeyInfoBean;
import com.yzb.card.king.ui.gift.activity.GiftCardRecvECardActivity;
import com.yzb.card.king.ui.my.view.KeyInfoView;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.UiUtils;

/**
 * 功能： 收到礼品卡消息；
 *
 * @author:gengqiyun
 * @date: 2017/1/5
 */
public class GiftRecvMsgDialog extends Dialog implements View.OnClickListener, KeyInfoView
{
    private final Context context;
    private TextView tvSender;
    private String sender; //发送人；
    private String codeNo;//订单号；

    public GiftRecvMsgDialog(Context context)
    {
        super(context);
        this.context = context;
        initView();
    }

    public GiftRecvMsgDialog setSender(String sender)
    {
        this.sender = sender;
        tvSender.setText(sender);
        return this;
    }

    public static GiftRecvMsgDialog getInstance(Context context)
    {
        return new GiftRecvMsgDialog(context);
    }

    protected void initView()
    {
        View view = getLayoutInflater().inflate(R.layout.dialog_recv_gift_msg, null);
        setContentView(view);

        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);

        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        lp.width = CommonUtil.getScreenWidth(context) - CommonUtil.dip2px(context, 58);
        window.setAttributes(lp);

        tvSender = (TextView) view.findViewById(R.id.tvSender);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);
        view.findViewById(R.id.tvSure).setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvCancel: //取消；
                break;
            case R.id.tvSure://立即领取；
                Intent intent = new Intent(context, GiftCardRecvECardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", codeNo);
                context.startActivity(intent);
                break;
        }
        dismiss();
    }

    @Override
    public void onGetKeyInfoSuc(KeyInfoBean keyInfoBean)
    {
        tvSender.setText(keyInfoBean.getUserName());
    }

    @Override
    public void onGetKeyInfoFail(String failMsg)
    {
        UiUtils.shortToast(failMsg);
    }

    public GiftRecvMsgDialog setOrderNo(String codeNo)
    {
        this.codeNo = codeNo;
        return this;
    }
}
