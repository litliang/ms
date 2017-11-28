package com.yzb.card.king.ui.app.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.adapter.IdentificationsListAdapterAbs;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.my.bean.CertType;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 19:24
 * 描述：
 */
public class IdentificationsPopup extends PopupWindow
{
    private final Context context;
    private View rootView;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<CertType> dataList = new ArrayList<>();
    private OnItemClickListener<CertType> onItemClickListener;
    private int selectItemType = -1; //选中的item的type；默认护照；

    public IdentificationsPopup(OnItemClickListener<CertType> onItemClickListener)
    {
        this(onItemClickListener, 0);
    }

    public IdentificationsPopup(OnItemClickListener<CertType> onItemClickListener, int certType)
    {
        context = UiUtils.getContext();
        this.onItemClickListener = onItemClickListener;

        if (certType > 0)
        {
            this.selectItemType = certType;
        }

        initView();
        initData();
        init();
    }

    private void init()
    {
        setContentView(rootView);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        setBackgroundDrawable(dw);
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
    }

    private void initView()
    {
        rootView = UiUtils.inflate(R.layout.popwindow_identifications);
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new IdentificationsListAdapterAbs(dataList, selectItemType);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (onItemClickListener != null)
                {
                    dismiss();
                    onItemClickListener.onItemClick(dataList.get(position));
                }
            }
        });
    }

    private void initData()
    {
        dataList.add(new CertType("1", "身份证"));
        dataList.add(new CertType("2", "护照"));
        dataList.add(new CertType("3", "台胞证"));
        dataList.add(new CertType("4", "回乡证"));
        dataList.add(new CertType("5", "军人证"));
        dataList.add(new CertType("6", "港澳通行证"));
        adapter.notifyDataSetChanged();
    }

    public CertType getDefaultValue()
    {
        return dataList.get(0);
    }
}
