package com.yzb.card.king.ui.gift.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.my.fragment.CommandDialog;

/**
 * 功能：我的-->发送礼品卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/20
 */
public class SendGiftCardDialog extends BaseDialogFragment implements View.OnClickListener
{
    private static SendGiftCardDialog dialogFragment;

    private LayoutInflater inflater;

    private Handler dataHandler;

    public static final int WHAT_SHARE_PLATFORM = 0x002;

    private FragmentManager fm;

    private String shareContent; //分析内容；

    private String[] share_platforms_packages;

    private String orderId = null;

    public static SendGiftCardDialog getInstance()
    {
        if (dialogFragment == null)
        {
            dialogFragment = new SendGiftCardDialog();
        }
        return dialogFragment;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_share_giftcard_dialog;
    }

    protected void initView(View view)
    {
        share_platforms_packages = getResources().getStringArray(R.array.share_platforms_packages1);
        inflater = LayoutInflater.from(getActivity());
        view.findViewById(R.id.ivClose).setOnClickListener(this);
        GridView gv = (GridView) view.findViewById(R.id.gv);
        gv.setAdapter(new ShareGridAdapter());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivClose:
                dismiss();
                break;
        }
    }

    public SendGiftCardDialog setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
        return this;
    }

    public void sendMsg(int what)
    {
        if (dataHandler != null)
        {
            Message msg = dataHandler.obtainMessage(what);

            if(orderId != null){

                msg.obj = orderId;
            }

            dataHandler.sendMessage(msg);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public SendGiftCardDialog setFragmentManager(FragmentManager fm)
    {
        this.fm = fm;
        return this;
    }

    public SendGiftCardDialog setCommandAndUrl(String shareContent)
    {
        this.shareContent = shareContent;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public class ShareGridAdapter extends BaseAdapter
    {
        public String[] sharePlatforms = getActivity().getResources().getStringArray(R.array.share_giftcard_platforms);
        // , R.mipmap.icon_share_giftcard_sina
        public int[] resIds = {
                R.mipmap.icon_share_giftcard_platform
                , R.mipmap.icon_share_giftcard_weixin
                , R.mipmap.icon_share_giftcard_qq
                , R.mipmap.icon_share_giftcard_circle
                , R.mipmap.icon_share_more
        };

        @Override
        public int getCount()
        {
            return sharePlatforms.length;
        }

        @Override
        public String getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            convertView = inflater.inflate(R.layout.item_share_giftcard, parent, false);

            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);

            TextView tv = (TextView) convertView.findViewById(R.id.tv);

            iv.setBackgroundResource(resIds[position]);

            tv.setText(sharePlatforms[position]);

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (position == 0) //嗨生活；
                    {
                        sendMsg(WHAT_SHARE_PLATFORM);
                    } else
                    {
                        CommandDialog.getInstance().setHandler(dataHandler).setSharePlatform(share_platforms_packages[position-1]).
                                setShareContent(shareContent).show(fm, "");
                    }
                    dismiss();
                }
            });
            return convertView;
        }
    }
}
