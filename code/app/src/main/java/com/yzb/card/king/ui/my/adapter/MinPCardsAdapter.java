package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.gift.bean.ECardBean;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 类  名：功能：礼品卡-选购心意e卡；
 * 作  者：Li Yubing
 * 日  期：2017/10/17
 * 描  述：
 */
public class MinPCardsAdapter  extends BaseListAdapter<ECardBean>
{
    public static final int WHAT_ITEM = 0x001;
    private ImageOptions imageOptions;
    private Handler dataHandler;

    public MinPCardsAdapter(Context context)
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
            convertView = mInflater.inflate(R.layout.item_list_mind_pcards, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        ECardBean bean = getItem(position);

        x.image().bind(holder.ivCardLogo, ServiceDispatcher.getImageUrl(bean.getImageCode()), imageOptions);

        holder.tvCardIntro.setText(bean.getBlessWord());

        holder.root.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null)
                {
                    Message msg = dataHandler.obtainMessage(WHAT_ITEM);
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

    public class ViewHolder
    {
        @ViewInject(R.id.ivCardLogo)
        public ImageView ivCardLogo;

        @ViewInject(R.id.tvCardIntro)
        public TextView tvCardIntro;

        public View root;



        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}

