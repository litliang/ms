package com.yzb.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.util.ImageUtils;
import com.yzb.wallet.util.WalletConstant;

import java.util.List;
import java.util.Map;

/**
 * 付款方式适配器
 */
public class PayMethodAdapter extends BaseAdapter {

    private List<Map> data;
    private int listViewItem;
    private LayoutInflater layoutInflater = null;

    public PayMethodAdapter(Context context, List<Map> data, int listViewItem) {
        this.data = data;
        this.listViewItem = listViewItem;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(listViewItem, null);

            viewHolder = new ViewHolder();
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
            viewHolder.typeName = (TextView) convertView.findViewById(R.id.typeName);
            viewHolder.accountType = (TextView) convertView.findViewById(R.id.accountType);
            viewHolder.sortCode = (TextView) convertView.findViewById(R.id.sortCode);
            viewHolder.cardType = (TextView) convertView.findViewById(R.id.cardType);
            viewHolder.payType = (TextView) convertView.findViewById(R.id.payType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, String> map = data.get(position);

        String typeName = map.get("typeName");
        String accountType = map.get("accountType");
        String sortCode = map.get("sortCode");
        String cardType = map.get("cardType");
        String payType = map.get("payType");
        String bankId = map.get("bankId");

        if (WalletConstant.PAY_BANK.equals(accountType)) {
            // 银行卡付款
            viewHolder.logo.setVisibility(View.VISIBLE);
            viewHolder.logo.setBackgroundResource(ImageUtils.getBankImage(Integer.parseInt(bankId)));
        } else if(WalletConstant.PAY_BALANCE.equals(accountType)){
            // 余额付款
            viewHolder.logo.setVisibility(View.VISIBLE);
            viewHolder.logo.setBackgroundResource(R.drawable.pay_icon_yue);
        } else if(WalletConstant.PAY_RED_ENVELOP.equals(accountType)){
            // 红包付款
            viewHolder.logo.setVisibility(View.VISIBLE);
            viewHolder.logo.setBackgroundResource(R.drawable.pay_icon_hongbao);
        } else if(WalletConstant.PAY_GIFT_CARD.equals(accountType)){
            // 礼品卡付款
            viewHolder.logo.setVisibility(View.VISIBLE);
            viewHolder.logo.setBackgroundResource(R.drawable.pay_icon_lipin);
        }
        viewHolder.typeName.setText(typeName);
        viewHolder.accountType.setText(accountType);
        viewHolder.sortCode.setText(sortCode);
        viewHolder.cardType.setText(cardType);
        viewHolder.payType.setText(payType);

        return convertView;
    }

    static class ViewHolder {
        ImageView logo;
        TextView typeName;
        TextView accountType;
        TextView sortCode;
        TextView cardType;
        TextView payType;
    }
}
