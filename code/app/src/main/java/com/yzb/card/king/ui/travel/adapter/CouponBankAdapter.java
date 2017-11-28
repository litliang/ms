package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.CommonUtil;

/**
 * 功能：优惠银行列表；
 *
 * @author:gengqiyun
 * @date: 2016/11/14
 */
public class CouponBankAdapter extends BaseListAdapter<GoodActivityBean>
{
    public CouponBankAdapter(Context context)
    {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_coupon_bank, parent, false);
            viewHolder = new ViewHolder((LinearLayout) convertView);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GoodActivityBean itemBean = getItem(position);

        //银行logo；
        CommonUtil.downloadImageForView(ServiceDispatcher.getImageUrl(itemBean.getBankLogoCode()), viewHolder.ivBankLogo, 13);
        viewHolder.tvBankName.setText(itemBean.getBankName());

        //利用余数控制2个item的不同对齐方式；
        int reste = position % 3;
        if (reste == 0)
        {
            viewHolder.root.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else if (reste == 1)
        {
            viewHolder.root.setGravity(Gravity.CENTER);
        } else if (reste == 2)
        {
            viewHolder.root.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
        return viewHolder.root;
    }

    public class ViewHolder
    {
        public final ImageView ivBankLogo;
        public final TextView tvBankName;
        public final LinearLayout root;

        public ViewHolder(LinearLayout root)
        {
            ivBankLogo = (ImageView) root.findViewById(R.id.ivBankLogo);
            tvBankName = (TextView) root.findViewById(R.id.tvBankName);
            this.root = root;
        }
    }
}
