package com.yzb.card.king.ui.credit.popup;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.credit.bean.CardLevel;
import com.yzb.card.king.ui.credit.holder.BankListPopHolder;
import com.yzb.card.king.ui.credit.interfaces.IOnlineCardPop;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.DensityUtil;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/22
 */

public class CardLevelPop extends PopupWindow
{
    private View mRoot;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private List<IOnlineCardPop> levels = new ArrayList<>();

    public CardLevelPop()
    {
        initView();
        initData();
        init();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.pop_all_bank_list);
        listView = (ListView) mRoot.findViewById(R.id.listView);
        listView.setLayoutParams(getParams());
        mRoot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    @NonNull
    private LinearLayout.LayoutParams getParams()
    {
        int  screenWith = GlobalApp.getInstance().getAppBaseDataBean().getScreenWith();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWith / 3
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        return params;
    }

    private void initData()
    {
        setAdapter();
    }

    private void setAdapter()
    {
        adapter = new AbsBaseListAdapter<IOnlineCardPop>(levels)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new BankListPopHolder();
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (onItemClickListener != null)
                {
                    onItemClickListener.onItemClick(levels.get(position));
                    dismiss();
                }
            }
        });
    }

    private void init()
    {
        this.setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#33000000"));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setTouchable(true);
        setOutsideTouchable(false);
    }

    private OnItemClickListener<IOnlineCardPop> onItemClickListener;

    public void setListener(OnItemClickListener<IOnlineCardPop> onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<CardLevel> rankList)
    {
        levels.clear();
        levels.addAll(rankList);
        adapter.notifyDataSetChanged();
    }


}
