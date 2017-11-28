package com.yzb.card.king.ui.discount.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.sys.ServiceDispatcher;

import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by Timmy on 16/5/26.
 */
public class FilmGalleryAdapter extends BaseAdapter
{
    private List<Map> mDatas;
    private Activity mContext;

    public FilmGalleryAdapter(Activity context, List<Map> images)
    {
        this.mDatas = images;
        this.mContext = context;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Map getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder mHolder;
        if (convertView == null)
        {
            convertView = mContext.getLayoutInflater().inflate(R.layout.item_film_gallery, null, false);
            mHolder = new ViewHolder();
            mHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(mHolder);
        } else
        {
            mHolder = (ViewHolder) convertView.getTag();
        }

        int r = position % mDatas.size();
        Map<String, Object> map = mDatas.get(r);
        String imageUri = ServiceDispatcher.getImageUrl(String.valueOf(map.get("imageCode")));//url_image + "getImg/" + String.valueOf(map.get("imageCode")) + "/0";
        x.image().bind(mHolder.imageView, imageUri,GlobalApp.getInstance().getImageOptionsFitXY());
        return convertView;
    }

    private static class ViewHolder
    {
        ImageView imageView;
    }
}
