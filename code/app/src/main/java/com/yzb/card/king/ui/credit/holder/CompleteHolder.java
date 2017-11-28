package com.yzb.card.king.ui.credit.holder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.interfaces.IAddCardStep;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/23
 */
public class CompleteHolder extends IAddCardStep
{
    private View mRoot;
    AddCanPayCardActivity addCanPayCardActivity;
    private TextView tvContent;

    public CompleteHolder(AddCanPayCardActivity addCanPayCardActivity)
    {
        this.addCanPayCardActivity = addCanPayCardActivity;
        initView();
    }

    private void initView()
    {
        mRoot = UiUtils.inflate(R.layout.holder_complete);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRoot.setLayoutParams(params);
        tvContent = (TextView) mRoot.findViewById(R.id.tvContent);
        tvContent.setText("恭喜您"+getTitle());
    }

    public void setData(String msg)
    {
        tvContent.setText(msg);
    }

    @Override
    public boolean rightClick()
    {
        addCanPayCardActivity.nextStep();
        return true;
    }

    @Override
    public String getRightText()
    {
        return "完成";
    }

    @Override
    public String getTitle()
    {
        return "绑定成功";
    }

    @Override
    public View getView()
    {
        return mRoot;
    }

    @Override
    public void onActivityResult(int requestCode, int requestCode1, Intent data)
    {

    }

    @Override
    public int getBackgroundColor() {
        return UiUtils.getColor(R.color.gray8);
    }
}
