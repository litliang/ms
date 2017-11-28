package com.yzb.card.king.ui.appwidget.popup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.HotelFilterView;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/10/28 10:36
 */
public class HotelLuxuryPop extends PopupWindow {
    private View mRoot;
    private GridView gridView;
    private List<ChildTypeBean> listData;
    private AbsBaseListAdapter adapter;
    private View headerLeft;
    private ImageView headerLeftImage;
    private TextView headerTitle;
    private List<Boolean> status;

    public HotelLuxuryPop() {
        initView();
        initListener();
        initData();
        init();
    }

    private void initView() {
        mRoot = UiUtils.inflate(R.layout.hotel_luxury_pop);

        headerLeft = mRoot.findViewById(R.id.headerLeft);
        headerLeftImage = (ImageView) mRoot.findViewById(R.id.headerLeftImage);
        headerTitle = (TextView) mRoot.findViewById(R.id.headerTitle);

        gridView = (GridView) mRoot.findViewById(R.id.gridView);
    }

    private void initListener() {
        headerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initData() {
        setHeader();
        setGridAdapter();
        loadGridData();
    }


    private void setHeader() {
        headerLeftImage.setImageResource(R.mipmap.icon_back_white);
        headerLeftImage.setVisibility(View.VISIBLE);
        headerTitle.setText("高端奢华");
    }

    private void setGridAdapter() {
        listData = new ArrayList<>();
        status = new ArrayList<>();
//        adapter = new LbtGvAdapter(listData);
//        adapter.setCallBack(new LbtGvAdapter.IGvItemClickCallBack()
//        {
//            @Override
//            public void callBack(ChildTypeBean typeBean)
//            {
//                HotelFilterView.data.setLuxury(new FilterType(typeBean.typeName,"",typeBean.id));
//                dismiss();
//            }
//        });
        adapter = getAdapter();
        gridView.setAdapter(adapter);
    }

    private AbsBaseListAdapter getAdapter() {

        return new AbsBaseListAdapter<ChildTypeBean>(listData) {
            @Override
            protected Holder getHolder(int position) {
                return new LuxHolder();
            }
        };
    }

    private LuxHolder selectedHolder;

    private class LuxHolder extends Holder<ChildTypeBean> {

        private ImageView imageView;
        private TextView text;
        private View view;
        private View llBg;

        @Override
        public View initView() {
            view = UiUtils.inflate(R.layout.holder_lux);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            text = (TextView) view.findViewById(R.id.text);
            llBg = view.findViewById(R.id.llBg);
            llBg.setEnabled(false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HotelFilterView.data.setLuxury(new FilterType(getData().typeName, "", getData().id));
                    if (selectedHolder != null) selectedHolder.setEnabled(false);
                    selectedHolder = LuxHolder.this;
                    selectedHolder.setEnabled(true);
                    dismiss();
                }
            });
            return view;
        }

        private void setEnabled(boolean b) {
            llBg.setEnabled(b);
        }

        @Override
        public void refreshView(ChildTypeBean data) {

            x.image().bind(imageView, ServiceDispatcher.getImageUrl(data.typeImage));
            text.setText(data.typeName);
        }
    }

    private void loadGridData() {
        listData.clear();
        String childTypeJson = SharePrefUtil.getValueFromSp(UiUtils.getContext(),
                AppConstant.sp_childtypelist_hotel, "[]");

        listData.addAll(JSON.parseArray(childTypeJson, ChildTypeBean.class));
        adapter.notifyDataSetChanged();
    }

    private void init() {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setTouchable(true);
        setOutsideTouchable(true);
    }
}
