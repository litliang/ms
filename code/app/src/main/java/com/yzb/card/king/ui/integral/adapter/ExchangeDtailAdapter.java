package com.yzb.card.king.ui.integral.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：兑换明细适配器
 * 作  者：Li Yubing
 * 日  期：2016/6/24
 * 描  述：
 */
public class ExchangeDtailAdapter extends BaseAdapter {

    List<ExchangeIntegralBean> temp = new ArrayList<ExchangeIntegralBean>();

    private Context context;

    private Handler adapterHandler;

    public ExchangeDtailAdapter(Context context, Handler adapterHandler) {

        this.context = context;

        this.adapterHandler = adapterHandler;
    }


    public void addNewData(List<ExchangeIntegralBean> list) {

        temp.clear();

        temp.addAll(list);

        notifyDataSetChanged();

    }

    public List<ExchangeIntegralBean> getTemp() {
        return temp;
    }

    public void setTemp(List<ExchangeIntegralBean> temp) {
        this.temp = temp;
    }

    /**
     * 添加新的兑换明细，flag:true表示添加成功；false表示添加失败
     * @param bean
     * @return
     */
    public boolean addOneNewData(ExchangeIntegralBean bean) {

        boolean flag = true;

        for (ExchangeIntegralBean eib : temp) {

            if (eib.getRuleId().equals(bean.getRuleId()) && eib.getBankId().equals(bean.getBankId())) {

                flag = false;
                break;
            }

        }

        if (flag) {

            temp.add(bean);

            notifyDataSetChanged();
        }

        return flag;
    }

    @Override
    public int getCount() {
        return temp.size();
    }

    @Override
    public Object getItem(int position) {
        return temp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHold viewHold;

        if (convertView == null) {

            viewHold = new ViewHold();

            convertView = LayoutInflater.from(context).inflate(R.layout.view_adapter_mingxi,
                    null);

            viewHold.ibDel = (ImageButton) convertView.findViewById(R.id.ibDel);

            viewHold.tvIntegralContent = (TextView) convertView.findViewById(R.id.tvIntegralContent);

            convertView.setTag(viewHold);

            viewHold.ibDel.setOnClickListener(myClickListener);

        } else {

            viewHold = (ViewHold) convertView.getTag();
        }

        viewHold.ibDel.setTag(position);

        ExchangeIntegralBean eib = (ExchangeIntegralBean) getItem(position);
        viewHold.tvIntegralContent.setText("+" + eib.getExchangeResult() + "元  抵扣" + eib.getRuleName() + "  " + eib.getPoint() + "积分");

        return convertView;
    }


    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int index = (int) v.getTag();

            temp.remove(index);

            notifyDataSetChanged();

            adapterHandler.sendEmptyMessage(0);
        }
    };

    public void clear() {

        temp.clear();


        notifyDataSetChanged();
    }

    class ViewHold {

        TextView tvIntegralContent;

        ImageButton ibDel;

    }
}
