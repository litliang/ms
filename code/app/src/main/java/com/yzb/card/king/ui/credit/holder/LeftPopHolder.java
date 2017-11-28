package com.yzb.card.king.ui.credit.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/23
 */
public class LeftPopHolder extends Holder<ChildTypeBean>
{

    private ImageView imageView;
    private TextView name;
    private ImageOptions imageOptionsLogo = new ImageOptions.Builder()
            .setCrop(false)
            .setUseMemCache(true)
            .setImageScaleType(ImageView.ScaleType.FIT_XY).build();

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.item_left_popwindow);
        imageView = (ImageView) view.findViewById(R.id.image);
        name = (TextView) view.findViewById(R.id.name);
        return view;
    }

    @Override
    public void refreshView(ChildTypeBean data)
    {

        if (!TextUtils.isEmpty(data.typeImage))
        {
            x.image().bind(imageView, data.typeImage, imageOptionsLogo);
        }
        name.setText(data.typeName);

    }
}
