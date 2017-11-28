package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.ui.hotel.holder.HotelCardEquityHolder;
import com.yzb.card.king.ui.hotel.holder.HotelFlashSaleHolder;
import com.yzb.card.king.util.LogUtil;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述： 卡权益 限时抢购
 */
public class ProductGiftComboAdatper extends RecyclerAdapter<GiftComboBean> {

    private boolean productType = true;//true:限时抢购;false:卡权益

    public void setProductType(boolean productType){

        this.productType = productType;

    }


    public ProductGiftComboAdatper(Context context) {
        super(context);
    }


    @Override
    public int getItemViewType(int position)
    {

        if(productType){

            return 10002;
        }else{
            return 10001;
        }

    }

    @Override
    public BaseViewHolder<GiftComboBean> onCreateBaseViewHolder(ViewGroup parent, int viewType) {

        LogUtil.e("ABCDEF","-------------------productType="+productType);
        if(viewType==10002){

            return new HotelFlashSaleHolder(parent);
        }else{
            return new HotelCardEquityHolder(parent);
        }


    }
}
