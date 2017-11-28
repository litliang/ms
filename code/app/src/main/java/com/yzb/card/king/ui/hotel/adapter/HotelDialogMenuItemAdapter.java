package com.yzb.card.king.ui.hotel.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelMenuBean;

import java.util.List;

/**
 * 类  名：酒店菜单菜单适配器
 * 作  者：Li Yubing
 * 日  期：2017/8/10
 * 描  述：
 */
public class HotelDialogMenuItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    List<HotelMenuBean.HotelChildMenuBean> value;

    public HotelDialogMenuItemAdapter(LayoutInflater inflater, List<HotelMenuBean.HotelChildMenuBean> value){

        this.inflater = inflater;

        this.value = value;
    }

    public void addNewList(List<HotelMenuBean.HotelChildMenuBean> value){

        this.value.clear();

        this.value.addAll(value);

         notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return value.size();
    }

    @Override
    public Object getItem(int position)
    {
        return value.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHold viewHold = null;

        if(convertView == null){

            viewHold = new ViewHold();

            convertView = inflater.inflate(R.layout.view_hotel_menu_item,null);

            viewHold.initView(convertView);

            convertView.setTag(viewHold);
        }else {

            viewHold = (ViewHold) convertView.getTag();
        }

        HotelMenuBean.HotelChildMenuBean bean = (HotelMenuBean.HotelChildMenuBean) getItem(position);

        StringBuffer sb = new StringBuffer();

        sb.append(bean.getGoodsName());

        if(!TextUtils.isEmpty(bean.getGoodsUnit())){

            sb.append("(").append(bean.getGoodsUnit()).append(")");

        }

        viewHold.tvMenuName.setText(sb.toString());


        if(bean.getGoodsPrice() !=0){
            viewHold.tvPice.setText("¥"+bean.getGoodsPrice());
        }else {
            viewHold.tvPice.setText("");
        }


        return convertView;
    }

    class  ViewHold{

        TextView tvMenuName;

        TextView tvPice;

        public void initView(View view){

            tvMenuName = (TextView) view.findViewById(R.id.tvMenuName);

            tvPice = (TextView) view.findViewById(R.id.tvPice);
        }

    }

}