package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.FavorPayee;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 功能：礼品卡-常用收礼人；
 *
 * @author:gengqiyun
 * @date: 2017/2/17
 */
public class FavorPayeeAdapter extends BaseListAdapter<FavorPayee>
{
    public static final int WHAT_ITEM = 0x001;
    private ImageOptions imageOptions;
    private Handler dataHandler;

    public FavorPayeeAdapter(Context context)
    {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(4), ImageView.ScaleType.FIT_XY);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_favor_payee, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        FavorPayee favorPayee = getItem(position);
        x.image().bind(holder.ivPic, ServiceDispatcher.getImageUrl(favorPayee.getPhotoUrl()), imageOptions);
        holder.tvName.setText(favorPayee.getCreditName());
        holder.tvPhone.setText(RegexUtil.hide4PhoneNum(favorPayee.getTradeAccount()));


        holder.root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null)
                {
                    Message msg= dataHandler.obtainMessage(WHAT_ITEM);
                    msg.arg1=position;
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


    public class ViewHolder
    {
        @ViewInject(R.id.ivPic)
        public ImageView ivPic;
        @ViewInject(R.id.tvName)
        public TextView tvName;
        @ViewInject(R.id.tvPhone)
        public TextView tvPhone;
        public View root;

        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}
