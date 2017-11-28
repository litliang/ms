package com.yzb.card.king.ui.credit.interfaces;

import android.content.Intent;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.util.UiUtils;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/23
 */

public abstract class IAddCardStep {

    public abstract boolean rightClick();

    public abstract String getRightText();

    public abstract String getTitle();

    public abstract View getView();

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onStart() {

    }

    public int getBackgroundColor() {
        return UiUtils.getColor(R.color.white);
    }
}
