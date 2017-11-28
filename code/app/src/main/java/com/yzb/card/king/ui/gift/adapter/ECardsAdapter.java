package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.NoRecvCardBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 功能：礼品卡-e卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/15
 */
public class ECardsAdapter extends BaseListAdapter<NoRecvCardBean>
{
    public static final int WHAT_COLLECT = 0x001; //领取；

    private ImageOptions imageOptions;

    private Handler dataHandler;

    public ECardsAdapter(Context context)
    {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(2), ImageView.ScaleType.FIT_XY);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_giftcard_ecards, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        NoRecvCardBean bean = getItem(position);

        x.image().bind(holder.ivCardLogo, ServiceDispatcher.getImageUrl(bean.getImageCode()), imageOptions);
        holder.tvCardName.setText(bean.getTypeName());
        holder.tvCardIntro.setText(bean.getBlessWord());

        holder.tvGiverIntro.setText(bean.getIssuePerson() + "的心意");

        holder.tvCollect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null)
                {
                    Message msg = dataHandler.obtainMessage(WHAT_COLLECT);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });
        return holder.root;
    }

    public void setDataHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }


    /**
     * 根据订单id找出bean；
     *
     * @param orderId
     */
    public NoRecvCardBean getItemById(String orderId)
    {
        if (!isEmpty(orderId))
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (orderId.equals(mList.get(i).getOrderId()))
                {
                    return mList.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 通过codeNo删除bean；
     *
     * @param orderId
     */
    public void removeBeanByOrderId(String orderId)
    {
        if (!isEmpty(orderId))
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (orderId.equals(mList.get(i).getOrderId()))
                {
                    removeBean(mList.get(i));
                    break;
                }
            }
        }
    }



    public class ViewHolder
    {
        @ViewInject(R.id.ivCardLogo)
        public ImageView ivCardLogo;
        @ViewInject(R.id.tvCardName)
        public TextView tvCardName;

        @ViewInject(R.id.tvCardIntro)
        public TextView tvCardIntro;
        @ViewInject(R.id.tvGiverIntro)
        public TextView tvGiverIntro;

        @ViewInject(R.id.tvGiftCardAmount)
        public TextView tvGiftCardAmount;
        @ViewInject(R.id.tvCollect)
        public TextView tvCollect;
        public View root;

        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}
