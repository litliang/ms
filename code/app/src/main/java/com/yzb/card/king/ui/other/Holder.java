package com.yzb.card.king.ui.other;

import android.view.View;

import com.yzb.card.king.util.UiUtils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/15 15:58
 * 描述：
 */
public abstract class Holder<T>
{
    private View convertView;
    private T data;
    private int postition;

    public Holder()
    {
        this.convertView = initView();
        convertView.setTag(this);
    }

    public Holder(int resId)
    {
        this.convertView = initView(resId);
        convertView.setTag(this);
    }

    protected View initView(int resId)
    {
        return UiUtils.inflate(resId);
    }

    public void setData(T data)
    {
        this.data = data;
        if (data == null)
        {
            OnDataNull();
        } else
        {
            refreshView(data);
        }
    }

    protected void OnDataNull()
    {

    }

    public abstract View initView();

    public abstract void refreshView(T data);

    public View getConvertView()
    {
        return this.convertView;
    }

    public T getData()
    {
        return data;
    }

    public void setPosition(int postition) {
        this.postition = postition;
    }

    public int getPostition() {
        return postition;
    }
}
