package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.GiftcardLimitBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

import java.util.List;

/**
 * 功能：礼品卡面额；
 *
 * @author:gengqiyun
 * @date: 2016/12/18
 */
public class GiftcardLimitAdapter extends BaseListAdapter<GiftcardLimitBean>
{
    public static final int WHAT_LIMIT_ITEM = 0x007;

    private Handler dataHandler;

    public GiftcardLimitAdapter(Context context, List<GiftcardLimitBean> list)
    {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.list_item_giftcard_limit, parent, false);

        TextView tv = (TextView) convertView.findViewById(R.id.tvLimit);

        final GiftcardLimitBean bean = getItem(position);
        tv.setSelected(bean.isSelect());
        tv.setText("¥" + bean.getLimit());

        tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectItem(bean);
                if (dataHandler != null)
                {
                    Message msg = dataHandler.obtainMessage(WHAT_LIMIT_ITEM);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });
        return convertView;
    }

    /**
     * 选中；
     */
    private void selectItem(GiftcardLimitBean bean)
    {
        if (!bean.isSelect())
        {
            bean.setIsSelect(true);
            notifyDataSetChanged();
        }
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    /**
     * 取消选中item；
     *
     * @param amount 面额；
     */
    public void unSelectItemByAmount(int amount)
    {
        for (int i = 0, j = mList.size(); i < j; i++)
        {
            if (amount == mList.get(i).getLimit())
            {
                mList.get(i).setIsSelect(false);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
