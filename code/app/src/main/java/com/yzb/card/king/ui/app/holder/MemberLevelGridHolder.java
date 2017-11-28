package com.yzb.card.king.ui.app.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.app.bean.LevelGridItem;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 9:44
 * 描述：
 */
public class MemberLevelGridHolder extends Holder<LevelGridItem>
{
    private ImageView image;
    private TextView tvName;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.item_level_grid);
        image = (ImageView) view.findViewById(R.id.image);
        tvName = (TextView) view.findViewById(R.id.tvName);
        return view;
    }

    @Override
    public void refreshView(LevelGridItem data)
    {
        x.image().bind(image, ServiceDispatcher.getImageUrl(data.getPrivilegeLogo()));
        tvName.setText(data.getPrivilegeName());
    }
}
