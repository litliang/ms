package com.yzb.card.king.ui.bonus.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.bean.BounsBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 红包列表；
 *
 * @author:gengqiyun
 * @date: 2016/12/21
 */
public class BounsListAdapter extends BaseListAdapter<BounsBean>
{
    public static final int WHAT_RECV = 0x001;
    private ImageOptions imageOptions;
    private Handler dataHandler;

    public BounsListAdapter(Context context)
    {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(5), ImageView.ScaleType.FIT_XY);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_bouns_list_new, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        BounsBean bean = getItem(position);
        x.image().bind(holder.iv, ServiceDispatcher.getImageUrl(bean.getCloseImageCode()), imageOptions);
        holder.tvThemeName.setText(bean.getThemeName());

        holder.tvGiverIntro.setText(mContext.getString(R.string.person_bouns, bean.getIssuePerson()));

        holder.tvClickGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null)
                {
                    Message msg = dataHandler.obtainMessage(WHAT_RECV);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });

        return holder.root;
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }


    /**
     * 删除item；
     *
     * @param orderId
     */
    public void delItemById(String orderId)
    {
        if (!isEmpty(orderId))
        {
            for (int i = 0; i < mList.size(); i++)
            {
                if (orderId.equals(mList.get(i).getOrderId()))
                {
                    mList.remove(i);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    public class ViewHolder
    {
        @ViewInject(R.id.iv)
        public ImageView iv;
        @ViewInject(R.id.tvThemeName)
        public TextView tvThemeName;
        @ViewInject(R.id.tvGiverIntro)
        public TextView tvGiverIntro;
        @ViewInject(R.id.tvClickGet)
        public TextView tvClickGet;
        public View root;

        public ViewHolder(View root)
        {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}
