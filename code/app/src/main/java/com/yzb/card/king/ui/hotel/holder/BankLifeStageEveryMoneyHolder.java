package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzb.card.king.R;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：
 */
public class BankLifeStageEveryMoneyHolder extends BaseViewHolder<String> {//

    private Context context;

    private TextView tvBankName;

    public BankLifeStageEveryMoneyHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_bank_life_stage_every_money);

        context = parent.getContext();
    }

    @Override
    public void setData(String data)
    {

        tvBankName.setText(data);

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvBankName = findViewById(R.id.tvBankName);


    }
}


