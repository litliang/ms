package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.bean.CreditRate;

import java.util.List;

/**
 * 分期利率适配器
 */
public class OnLineCreditInfoAdapter extends BaseAdapter{

    private LayoutInflater in;
    private Context context;
    private List<CreditRate> list;
    public OnLineCreditInfoAdapter(Context context,List<CreditRate> list){
        this.context=context;
        this.list = list;
        in = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         final ViewHolder vh;
        if(convertView == null) {
            //实例化一个布局文件
            convertView = in.inflate(R.layout.gridview_rate, null);
            vh = new ViewHolder();
            vh.gridView_staging_textView= (TextView) convertView.findViewById(R.id.gridView_staging_textView);
            vh.gridView_rate_textView= (TextView) convertView.findViewById(R.id.gridView_rate_textView);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        CreditRate map = list.get(position);

        vh.gridView_staging_textView.setText(map.getStages()+"期");
        vh.gridView_rate_textView.setText(map.getRate()+"%");


        return convertView;
    }

    //用于保存第一次查找的组件，避免下次重复查找,不封装比封装性能要快，效率高
    class ViewHolder {
        TextView gridView_staging_textView;//分期
        TextView gridView_rate_textView;//利率
    }

}
