package com.yzb.card.king.ui.hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

import com.yzb.card.king.bean.common.BaseCouponBean;
import com.yzb.card.king.bean.hotel.HotelImageBean;
import com.yzb.card.king.ui.hotel.fragment.PicFragment;
import com.yzb.card.king.ui.hotel.holder.HotelImagesHolder;
import com.yzb.card.king.ui.other.activity.FullScreenImgActivity;

import java.util.List;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/24
 * 描  述：
 */
public class HotelImagesHolderAdapter extends RecyclerAdapter<String> {

    private Context context;

    private Handler activityHandler;

   private   List<HotelImageBean.ImageBean> imageUrl;

    private   List<String>  totalList = null;

    private boolean ifSpread = false;

    private int spreadSize = -1;//-1:表示不可展开的编号

    public HotelImagesHolderAdapter(Context context)
    {
        super(context);

        this.context = context;
    }

    public HotelImagesHolderAdapter(Context context,Handler activityHandler)
    {
        super(context);

        this.context = context;

        this.activityHandler = activityHandler;
    }


    public List<HotelImageBean.ImageBean> getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(List<HotelImageBean.ImageBean> imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public  void setNewDataByNumber(List<String> newList, int number ){

        int size = newList.size();

        if(size > number){

            spreadSize = number;

            this.totalList = newList;

            ifSpread = true;

            List<String> subNumList = totalList.subList(0,number);

            clear();

            addAll(subNumList);

        }else {

            clear();

            addAll(newList);

        }

    }

    /**
     * 展开所有的数据
     */
    public void spreadAllData(){

        clear();

        addAll(totalList);

        ifSpread =false;

    }

    @Override
    public BaseViewHolder<String> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        if(activityHandler == null){

            return new HotelImagesHolder(viewGroup, hander);

        }else {


            return new HotelImagesHolder(viewGroup, activityHandler,ifSpread,spreadSize);
        }

    }

    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            List<String> list = getData();

            String[] url = (String[]) list.toArray(new String[list.size()]);

            Intent intent = new Intent(context, FullScreenImgActivity.class);
            intent.putExtra("currentPage", what);
            intent.putExtra("imgUrls", url);
            context.startActivity(intent);

        }
    };

}
