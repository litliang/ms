package com.yzb.card.king.ui.appwidget.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.home.ChannelMainActivity;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.SharePrefUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 类  名：App模块频道
 * 作  者：Li Yubing
 * 日  期：2016/8/18
 * 描  述：频道：美食、旅游、奢侈品、机票、电影、酒店、陆上交通等
 */
public class ChannelPopWindow extends PopupWindow {

    private ImageOptions imageOptionsLogo = new ImageOptions.Builder()
            // 加载中或错误图片的ScaleType
            //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            // 默认自动适应大小
            //.setSize(DensityUtil.dip2px(context,54), DensityUtil.dip2px(context,54))
            .setCrop(false)
            .setUseMemCache(true)
            .setImageScaleType(ImageView.ScaleType.FIT_XY).build();

    private GridLayout gridLayout;

    private Context context;

    private View rootView;

    private Handler handler;

    public ChannelPopWindow(Handler handler, Context context)
    {

        super(context);

        this.handler = handler;
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.pop_view_channel, null);
        gridLayout = (GridLayout) rootView.findViewById(R.id.gridLayout);

        initView(260);
        initData();
    }

    private void initView(int w)
    {
        this.setContentView(rootView);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setTouchable(true);

        this.setWidth(CommonUtil.dip2px(context, w));
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    private void initData()
    {

        String childTypeJson = SharePrefUtil.getValueFromSp(GlobalApp.getInstance().getPublicActivity(), AppConstant.sp_childtypelist_home, "[]");

        if (childTypeJson == null && TextUtils.isEmpty(childTypeJson)) {

            return;
        }

        List<ChildTypeBean> childTypeBeans = JSON.parseArray(childTypeJson, ChildTypeBean.class);

        for (int i = 0; i < childTypeBeans.size(); i++) {
            View view = View.inflate(GlobalApp.getInstance().getContext(), R.layout.item_view_channel, null);
            ChildTypeBean item = childTypeBeans.get(i);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView name = (TextView) view.findViewById(R.id.name);
            if (!TextUtils.isEmpty(item.typeImage)) {
                x.image().bind(imageView, item.typeImage, imageOptionsLogo);
            }
            name.setText(item.typeName);
            view.setTag(item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    ChildTypeBean bean = (ChildTypeBean) v.getTag();

                    if (handler != null) {

                        Message message = handler.obtainMessage();

                        message.obj = bean;

                        message.what = 0;

                        handler.sendMessage(message);
                    }

                    if (context != null) {

                        Intent itS = new Intent(ChannelMainActivity.CLASS_NAME);

                        itS.putExtra("data", bean);

                        context.sendBroadcast(itS);
                    }
                    dismiss();

                }
            });
            gridLayout.addView(view);
        }
    }

}
