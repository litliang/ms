package com.yzb.card.king.ui.integral.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserCollectBean;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.integral.presenter.MyCollectPresenter;
import com.yzb.card.king.util.LogUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li JianQiang
 * 日  期：2016/8/16
 * 描  述：
 */
public class MyCollectAdapter extends ItemMenuAdapter {

    public List<UserCollectBean> shopInfos;
    private LayoutInflater inflater;
    private Context context;
    private int searchType;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_EMPTY = 1;
    private MyCollectPresenter presenter;


    public MyCollectAdapter(Context context, List<UserCollectBean> shopInfos, MyCollectPresenter presenter)
    {
        this.presenter = presenter;
        this.shopInfos = shopInfos;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    public void setSearchType(int searchType)
    {
        this.searchType = searchType;
    }

    @Override
    public int getCount()
    {
        return shopInfos.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (shopInfos.size() == 0)
        {
            return TYPE_EMPTY;
        } else
        {
            return TYPE_ITEM;
        }
    }

    public void notifyData(UserCollectBean userCollectBean)
    {
        this.shopInfos.remove(userCollectBean);
    }

    @Override
    public void onDeleItem(int pos)
    {
        shopInfos.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent)
    {
        MyViewHolder myViewHolder;
        if (convertView == null)
        {
            convertView = (ItemMenuView) LayoutInflater.from(context).inflate
                    (R.layout.view_adapter_item_collect, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.imgShopStop = (ImageView) convertView.findViewById(R.id.collecet_list_image);
            myViewHolder.txtTitle = (TextView) convertView.findViewById(R.id.shop_title);
            myViewHolder.txtPeople = (TextView) convertView.findViewById(R.id.shop_people);
            myViewHolder.contentinfo = (TextView) convertView.findViewById(R.id.contentinfo);
            myViewHolder.item_view = (LinearLayout) convertView.findViewById(R.id.item_view);
            myViewHolder.message_detail_delete_img = (ImageView) convertView.findViewById(R.id.message_detail_delete_img);
            myViewHolder.shop_money = (TextView) convertView.findViewById(R.id.shop_money);
            convertView.setTag(myViewHolder);
        } else
        {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        final UserCollectBean map = shopInfos.get(position);
        myViewHolder.txtTitle.setText(map.getStoreName());
     //   myViewHolder.txtPeople.setText(String.valueOf(map.getAvgPrice()));
        myViewHolder.contentinfo.setText(map.getIntroduction() +"");
        String a = map.getShopLogo();
        x.image().bind(myViewHolder.imgShopStop, a);
        myViewHolder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onItemClickListener.setOnItemClickListener(position);
            }
        });
        final ItemMenuView itemMenuView = convertView;
        myViewHolder.message_detail_delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //deleteItem(position, itemMenuView);
                if (searchType == 0) {
                    if (map.getCategory().equals("1")) {
                        presenter.delete(map, "1");
                    } else if (map.getCategory().equals("2")) {
                        presenter.delete(map, "2");
                    }
                } else {
                    presenter.delete(map, searchType + "");
                }
                itemMenuView.hideMenu();
                onDeleItem(position);
            }
        });
        LogUtil.e("AAAAA","----------map.getAvgPrice()---"+map.getAvgPrice());
        switch (searchType)
        {
            case 1:
                myViewHolder.txtPeople.setText(String.valueOf(map.getAvgPrice()) + "/人");
                myViewHolder.txtPeople.setVisibility(View.VISIBLE);//商铺不显示价格
                break;
            case 2:
                myViewHolder.txtPeople.setText( String.valueOf(map.getAvgPrice())+"起");
                myViewHolder.txtPeople.setVisibility(View.VISIBLE);
                break;
            case 0:
                if (map.getCategory().equals("1")) {
                    myViewHolder.txtPeople.setText(String.valueOf(map.getAvgPrice()) + "/人");
                    myViewHolder.txtPeople.setVisibility(View.VISIBLE);//商铺不显示价格
                } else if (map.getCategory().equals("2")) {
                    myViewHolder.txtPeople.setText(String.valueOf(map.getAvgPrice())+"起");
                    myViewHolder.txtPeople.setVisibility(View.VISIBLE);
                }
                break;
        }
        return convertView;
    }


    class MyViewHolder {
        ImageView imgShopStop;
        TextView txtTitle;
        Button btnDel;
        TextView txtPeople;
        TextView contentinfo;
        LinearLayout item_view;
        ImageView message_detail_delete_img;
        TextView shop_money;

    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void deleteItem(int pos, ItemMenuView view) {
        super.deleteItem(pos, view);
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(int postion);
    }
}
