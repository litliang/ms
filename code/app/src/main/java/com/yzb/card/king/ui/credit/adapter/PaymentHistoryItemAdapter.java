package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;

import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/12/1
 * 描  述：
 */
public class PaymentHistoryItemAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater inflater;

    private List<Map> maps;

    public PaymentHistoryItemAdapter(Context context, List<Map> list)
    {
        this.maps = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return maps.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder;
        if (view == null)
        {
            view = inflater.inflate(R.layout.pay_history_item_info, null);

            viewHolder = new ViewHolder();
            viewHolder.viewLine = view.findViewById(R.id.viewline);
            viewHolder.creditHK = (TextView) view.findViewById(R.id.credit_hk);
            viewHolder.creditMoney = (TextView) view.findViewById(R.id.credit_money);
            viewHolder.creditDate = (TextView) view.findViewById(R.id.credit_date);
            viewHolder.creditSucc = (TextView) view.findViewById(R.id.credit_succ);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Map map = maps.get(i);
        viewHolder.creditHK.setText(String.valueOf(map.get("repayMethod")));
        viewHolder.creditMoney.setText(String.valueOf(map.get("amount")));
        viewHolder.creditDate.setText(String.valueOf(map.get("successTime")));
        viewHolder.creditSucc.setText(String.valueOf(map.get("repayStatus")));

        if (i == maps.size() - 1)
        {
            viewHolder.viewLine.setVisibility(View.GONE);
        } else
        {
            viewHolder.viewLine.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public class ViewHolder {
        View viewLine;
        TextView creditHK;
        TextView creditMoney;
        TextView creditDate;
        TextView creditSucc;
    }
}
