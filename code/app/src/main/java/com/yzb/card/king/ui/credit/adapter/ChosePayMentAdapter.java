package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.RefundApplyReqFlightOrderBean;

import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/11/30
 * 描  述：
 */
public class ChosePayMentAdapter extends BaseAdapter {
    private Context context;

    private String[] beanList;

    private int chosePos;

    public ChosePayMentAdapter(Context context, String[] beanList, int pos)
    {
        this.chosePos = pos;
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount()
    {
        return beanList.length;
    }

    @Override
    public Object getItem(int i)
    {
        return beanList[i];
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        ViewHolder v;
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.chose_payment_item, null);
            v = new ViewHolder(view);
            view.setTag(v);
        } else
        {
            v = (ViewHolder) view.getTag();
        }
        v.txName.setText(beanList[i]);
        v.rl_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onItemClickListener.setOnItemClickListener(i);
                chosePos = i;
                notifyDataSetChanged();
            }
        });
        if (chosePos == i)
        {
            v.imageView.setImageResource(R.mipmap.icon_check_active);
        } else
        {
            v.imageView.setImageResource(R.mipmap.icon_check_default);
        }
        if (i == beanList.length - 1)
        {
            v.line_fg.setVisibility(View.GONE);
        } else
        {
            v.line_fg.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public class ViewHolder {
        TextView txName;
        ImageView imageView;
        RelativeLayout rl_view;
        View line_fg;

        public ViewHolder(View root)
        {
            txName = (TextView) root.findViewById(R.id.nameInfo);
            imageView = (ImageView) root.findViewById(R.id.nameLogo);
            rl_view = (RelativeLayout) root.findViewById(R.id.rl_view);
            line_fg = root.findViewById(R.id.line_fg);
        }
    }


    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(int postion);
    }
}
