package com.yzb.card.king.ui.travel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;


/**
 * 功能：垂直只显示图片；图片垂直间距是10dp；
 *
 * @author:gengqiyun
 * @date: 2016/11/16
 */
public class ImageOnlyAdapter extends BaseListAdapter<String>
{
    private ImageOptions imageOptions;
    private int imageHeight; //图片的高度；
    private int imageWidth;

    public ImageOnlyAdapter(Context context)
    {
        super(context);
    }

    public ImageOnlyAdapter(Context context, List<String> list)
    {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(R.layout.item_listview_image, parent, false);
        ImageView ivPic = (ImageView) convertView.findViewById(R.id.ivPic);
        if (imageHeight > 0)
        {
            ivPic.getLayoutParams().height = imageHeight;
        }
        if (imageWidth > 0)
        {
            ivPic.getLayoutParams().width = imageWidth;
        }

        String url = getItem(position);
        if (imageOptions != null)
        {
            x.image().bind(ivPic, url, imageOptions);
        } else
        {
            x.image().bind(ivPic, url);
        }

        ivPic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (callBack != null)
                {
                    callBack.callBack(position, CommonUtil.convertListToStrArray(mList));
                }
            }
        });
        return convertView;
    }

    public void setImageOptions(ImageOptions imageOptions)
    {
        this.imageOptions = imageOptions;
    }

    public void setImageSize(int width, int height)
    {
        this.imageWidth = width;
        this.imageHeight = height;
    }


    public ICallBack callBack;

    public void setCallBack(ICallBack callBack)
    {
        this.callBack = callBack;
    }

    public interface ICallBack
    {
        /**
         * 点击的位置；
         *
         * @param clickPosition
         * @param images        图片列表；
         */
        void callBack(int clickPosition, String[] images);
    }

}
