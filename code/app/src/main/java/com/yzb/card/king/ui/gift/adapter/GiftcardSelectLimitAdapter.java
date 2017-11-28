package com.yzb.card.king.ui.gift.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.gift.bean.GiftcardLimitBean;
import com.yzb.card.king.ui.base.BaseListAdapter;

/**
 * 功能：礼品卡 选中的面额；
 *
 * @author:gengqiyun
 * @date: 2016/12/18
 */
public class GiftcardSelectLimitAdapter extends BaseListAdapter<GiftcardLimitBean>
{

    public static final int WHAT_AMOUNT_CHANGE = 0x006;
    public static final int WHAT_AMOUNT_ZERO = 0x008;
    private Handler dataHandler;

    public GiftcardSelectLimitAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.list_item_giftcard_select_limit, parent, false);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);

        ImageView ivCut = (ImageView) convertView.findViewById(R.id.ivCut);
        TextView tvNum = (TextView) convertView.findViewById(R.id.tvNum);
        TextView tvDefine = (TextView) convertView.findViewById(R.id.tvDefine);
        ImageView ivAdd = (ImageView) convertView.findViewById(R.id.ivAdd);

        final GiftcardLimitBean bean = getItem(position);

        tvAmount.setText("¥" + bean.getLimit());
        tvDefine.setVisibility(bean.isUserDefined() ? View.VISIBLE : View.INVISIBLE);
        tvNum.setText(bean.getSheetNum() + "");

        //减;
        ivCut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int orignal = bean.getSheetNum();
                bean.setSheetNum(orignal <= 0 ? 0 : --orignal);
                //数量为0时删除；
                if (bean.getSheetNum() == 0)
                {
                    mList.remove(position);
                    if (dataHandler != null)
                    {
                        Message msg = dataHandler.obtainMessage(WHAT_AMOUNT_ZERO);
                        msg.arg1 = bean.getLimit();
                        dataHandler.sendMessage(msg);
                    }
                }
                notifyDataSetChanged();

                sendMsg(WHAT_AMOUNT_CHANGE);
            }
        });

        //加;
        ivAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int orignal = bean.getSheetNum();
                bean.setSheetNum(++orignal);
                notifyDataSetChanged();
                sendMsg(WHAT_AMOUNT_CHANGE);
            }
        });
        return convertView;
    }

    public void sendMsg(int what)
    {
        if (dataHandler != null)
        {
            dataHandler.sendEmptyMessage(what);
        }
    }

    @Override
    public void add(GiftcardLimitBean item)
    {
        super.add(item);
        sendMsg(WHAT_AMOUNT_CHANGE);
    }

    /**
     * 判断是否有等值面额的礼品卡；
     *
     * @param limitBean
     * @return
     */
    public boolean hasEqualLimit(GiftcardLimitBean limitBean)
    {
        if (limitBean != null)
        {
            int limit = limitBean.getLimit();
            for (int i = 0; i < mList.size(); i++)
            {
                if (limit == mList.get(i).getLimit())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有等值面额的礼品卡；
     *
     * @param limit
     * @return
     */
    public boolean hasEqualLimit(int limit)
    {
        for (int i = 0; i < mList.size(); i++)
        {
            if (limit == mList.get(i).getLimit())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取礼品卡总金额；
     */
    public float getTotalAmount()
    {
        float totalAmount = 0f;
        GiftcardLimitBean bean;
        for (int i = 0; i < mList.size(); i++)
        {
            bean = mList.get(i);
            totalAmount += bean.getSheetNum() * bean.getLimit();
        }
        return totalAmount;
    }

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    /**
     * 金额  多个使用,分割，与数量一一对应
     */
    public String getAmounts()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mList.size(); i++)
        {
            sb.append(i == mList.size() - 1 ? (mList.get(i).getLimit() + "") : (mList.get(i).getLimit() + ","));
        }
        return sb.toString();
    }

    /**
     * 數量  多个使用,分割，与数量一一对应
     */
    public String getQuantitys()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mList.size(); i++)
        {
            sb.append(i == mList.size() - 1 ? (mList.get(i).getSheetNum() + "") : (mList.get(i).getSheetNum() + ","));
        }
        return sb.toString();
    }
}
