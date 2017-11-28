package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/7/7 14:27
 * 描述：
 */
public class YhkGvAdapter2 extends BaseAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<BankBean> bankBeans;
    private String curBankId; // 当前的bankId；

    public YhkGvAdapter2(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return bankBeans == null ? 0 : bankBeans.size();
    }

    @Override
    public BankBean getItem(int position)
    {
        return bankBeans == null ? null : bankBeans.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.gv_item_yhk, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(R.id.tag_first, viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag(R.id.tag_first);
        }
        convertView.setTag(R.id.tag_second, position);
        BankBean bankBean = getItem(position);
        //viewHolder.ivyhk.setBackgroundResource(ImageUtils.getBankImage(bankBean.id));
        viewHolder.ivyhk.setImageBitmap(AppUtils.getImageFromAssets(context, ImageUtils.getBankName(bankBean.id)));
        String bankName = TextUtils.isEmpty(bankBean.bankName) ? "" : bankBean.bankName;
        viewHolder.tvname.setText(bankName);

        if (!TextUtils.isEmpty(bankBean.id + "") && (bankBean.id + "").equals(curBankId))
        {
            viewHolder.tvname.setTextColor(context.getResources().getColor(R.color.color_status));
            viewHolder.red_line.setVisibility(View.VISIBLE);
        } else
        {
            viewHolder.tvname.setTextColor(context.getResources().getColor(R.color.text_color_33));
            viewHolder.red_line.setVisibility(View.INVISIBLE);
        }

        convertView.setBackgroundResource(R.drawable.selector_bg_blue_white);
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = (int) v.getTag(R.id.tag_second);
                if (callBack != null)
                {
                    callBack.callBack(position);
                }
            }
        });
        return convertView;
    }

    public void appendDataList(List<BankBean> bankBeans)
    {
        if (bankBeans == null || bankBeans.size() == 0) return;
        if (this.bankBeans == null)
        {
            this.bankBeans = new ArrayList<>();
        }
        this.bankBeans.addAll(bankBeans);
        notifyDataSetChanged();
    }

    public void setCurBankId(String bankId)
    {
        this.curBankId = bankId;
    }

    public class ViewHolder
    {
        public final ImageView ivyhk;
        public final TextView tvname;
        public final View red_line;

        public ViewHolder(View root)
        {
            ivyhk = (ImageView) root.findViewById(R.id.iv_yhk);
            tvname = (TextView) root.findViewById(R.id.tv_name);
            red_line = root.findViewById(R.id.red_line);
        }
    }

    private IDataCallBack callBack;

    /**
     * 设置点击的回调；
     *
     * @param callBack
     */
    public void setCallBack(IDataCallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface IDataCallBack
    {
        void callBack(int position);
    }
}
