package com.yzb.card.king.ui.app.popup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/17 14:11
 * 描述：
 */
public class GetPicPopup extends PopupWindow {
    private Context context;
    private View.OnClickListener listener;
    private View rootView;
    private TextView tvCamera,tvAlbum,tvCancel;
    public GetPicPopup(View.OnClickListener listener) {
        context = UiUtils.getContext();
        this.listener = listener;
        initView();
        initData();
        init();
    }

    private void initView() {
        rootView = UiUtils.inflate(R.layout.popwindow_get_pic);
        tvCamera = (TextView) rootView.findViewById(R.id.tvCamera);
        tvAlbum = (TextView) rootView.findViewById(R.id.tvAlbum);
        tvCancel = (TextView) rootView.findViewById(R.id.tvCancel);

        tvCamera.setOnClickListener(listener);
        tvAlbum.setOnClickListener(listener);
        tvCancel.setOnClickListener(listener);
    }

    private void initData() {
    }

    private void init() {
        setContentView(rootView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(false);
        setTouchable(true);
    }


}
