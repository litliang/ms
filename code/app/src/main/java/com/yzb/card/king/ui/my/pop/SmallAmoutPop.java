package com.yzb.card.king.ui.my.pop;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.ui.my.bean.SmallAmount;
import com.yzb.card.king.ui.my.holder.SmallAmountHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/26 18:34
 */
public class SmallAmoutPop extends PopupWindow
{

    private List<SmallAmount> data = new ArrayList<>();
    private View mRoot;
    private ListView listView;
    private AbsBaseListAdapter adapter;
    private OnItemClickListener<String> listener;


    public void setListener(OnItemClickListener<String> listener)
    {
        this.listener = listener;
    }

    public SmallAmoutPop()
    {
        initView();
        initListener();
        initData();
        init();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.pop_small_amout);
        listView = (ListView) mRoot.findViewById(R.id.listView);
    }

    private void initListener()
    {

    }

    private void initData()
    {
        data.clear();
        data.add(new SmallAmount("100"));
        data.add(new SmallAmount("200"));
        data.add(new SmallAmount("300"));

        adapter = new AbsBaseListAdapter<SmallAmount>(data)
        {
            @Override
            protected Holder getHolder(int position)
            {
                return new SmallAmountHolder();
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                setSelected(position);
                if (listener != null)
                {
                    listener.onItemClick(data.get(position).getValue());
                }
                dismiss();
            }
        });
    }

    private void setSelected(int position)
    {
        for (int i = 0; i < data.size(); i++)
        {
            data.get(i).setChecked(i == position);
        }
        adapter.notifyDataSetChanged();
    }

    public void setSelected(String amount)
    {
        for (int i = 0; i < data.size(); i++)
        {
            data.get(i).setChecked(data.get(i).getValue().equals(amount));
        }
        adapter.notifyDataSetChanged();
    }

    private void init()
    {
        setContentView(mRoot);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#00000000"));
        this.setBackgroundDrawable(dw);
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
    }
}
