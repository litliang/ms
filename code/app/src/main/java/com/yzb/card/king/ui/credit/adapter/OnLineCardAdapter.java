package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.List;

/**
 *
 */
public class OnLineCardAdapter extends BaseAdapter
{

    private LayoutInflater in;
    private Context context;
    private List<Bank> list;

    public OnLineCardAdapter(Context context, List<Bank> list)
    {
        this.context = context;
        this.list = list;
        in = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.size() > 8 ? 8 : list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder vh;
        if (convertView == null)
        {
            //实例化一个布局文件
            convertView = in.inflate(R.layout.gridview_on_line_card_bank, null);
            vh = new ViewHolder();
            vh.imageView = (ImageView) convertView.findViewById(R.id.on_line_card_imageView);
            vh.bank = (TextView) convertView.findViewById(R.id.on_line_card_textView);
            vh.id = (TextView) convertView.findViewById(R.id.on_line_card_id_textView);
            vh.tvActive = (TextView) convertView.findViewById(R.id.tvActive);
            convertView.setTag(vh);
        } else
        {
            vh = (ViewHolder) convertView.getTag();
        }
        Bank map = list.get(position);

        if (position == getCount() - 1)
        {
            TextView textView = new TextView(UiUtils.getContext());
            textView.setBackgroundColor(Color.WHITE);
            textView.setTextColor(UiUtils.getColor(R.color.bgBlack));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            textView.setText("查看更多");
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dp2px(86)));
            return textView;
        }
        x.image().bind(vh.imageView, ServiceDispatcher.getImageUrl(map.getBankLogo()));
        vh.bank.setText(map.getBankName());
        vh.id.setText(String.valueOf(map.getBankId()));
        vh.tvActive.setText(map.getBankDescript());
        return convertView;
    }

    //用于保存第一次查找的组件，避免下次重复查找,不封装比封装性能要快，效率高
    class ViewHolder
    {
        ImageView imageView;//银行图片
        TextView bank;//银行名称
        TextView id;//id
        TextView tvActive;//id
    }
}
