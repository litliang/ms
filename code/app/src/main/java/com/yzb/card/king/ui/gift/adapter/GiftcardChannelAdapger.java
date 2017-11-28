package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.my.CouponChannelBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

/**
 * 功能：礼品卡首页频道列表；
 *
 * @author:gengqiyun
 * @date: 2016/12/14
 */
public class GiftcardChannelAdapger extends BaseListAdapter<CouponChannelBean>
{
    public static final int WHAT_CHANNEL_CLICK = 0x001;

    private Handler dataHandler;

    public GiftcardChannelAdapger(Context context)
    {
        super(context);
    }

    /**
     * 设置未领取红包数量
     * @param numberValue
     */
    public void setRedEnvelopeNumber(int numberValue){

        CouponChannelBean bean = getItem(1);

        bean.setShowNumber(true);

        bean.setNumberValue(numberValue);

        notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.item_giftcard_channel, parent, false);

        ImageView ivThumb = (ImageView) convertView.findViewById(R.id.ivThumb);

        TextView tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);

        CouponChannelBean bean = getItem(position);

        if(bean.isShowNumber() && bean.getNumberValue()>0){
            tvNumber.setVisibility(View.VISIBLE);
            int value = bean.getNumberValue();

            if(value <= 99){

                tvNumber.setText(value+"");
                tvNumber.setTextSize(10f);

            }else{

                tvNumber.setText("99+");

                tvNumber.setTextSize(8f);

            }

        }else{
            tvNumber.setVisibility(View.INVISIBLE);
        }

        ivThumb.setImageResource(bean.getImgResId());

        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);

        tvContent.setText(bean.getText());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null)
                {
                    Message msg = dataHandler.obtainMessage(WHAT_CHANNEL_CLICK);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });
        return convertView;
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    public class ViewHolder
    {
        public final ImageView ivThumb;
        public final TextView tvContent;
        public final View root;

        public ViewHolder(View root)
        {
            ivThumb = (ImageView) root.findViewById(R.id.ivThumb);
            tvContent = (TextView) root.findViewById(R.id.tvContent);
            this.root = root;
        }
    }
}
