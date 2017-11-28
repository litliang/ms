package com.yzb.card.king.ui.bonus.fragment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.activity.BounsListActivity;

/**
 * 功能： 收到红包消息；
 *
 * @author:gengqiyun
 * @date: 2017/1/9
 */
public class BonusRecvMsgDialog extends Dialog implements View.OnClickListener
{
    private final Context context;
    private String codeNo;

    public BonusRecvMsgDialog(Context context)
    {
        super(context);
        this.context = context;
        initView();
    }

    public static BonusRecvMsgDialog getInstance(Context context)
    {
        return new BonusRecvMsgDialog(context);
    }

    protected void initView()
    {
        View view = getLayoutInflater().inflate(R.layout.dialog_recv_bouns_msg, null);
        setContentView(view);

        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        window.setAttributes(lp);

        view.findViewById(R.id.tvCancel).setOnClickListener(this);
        view.findViewById(R.id.tvSure).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText("command", ""));
        switch (v.getId())
        {
            case R.id.tvCancel: //取消；
                break;
            case R.id.tvSure://立即领取；
                Intent intent = new Intent(context, BounsListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", codeNo);
                context.startActivity(intent);
                break;
        }
        dismiss();
    }

    public BonusRecvMsgDialog setOrderNo(String codeNo)
    {
        this.codeNo = codeNo;
        return this;
    }
}
