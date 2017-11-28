package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.GiftComboBean;
import com.yzb.card.king.sys.ServiceDispatcher;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：银行生活分期
 */
public class BankLifeStageHolder extends BaseViewHolder<BankActivityInfoBean> {//

    private Context context;

    private  TextView tvBankName;

    private ImageView ivBankLogo;

    private TextView tvAcityName;

    private TextView tvActivityDate;


    public BankLifeStageHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_bank_life_stage);

        context = parent.getContext();
    }

    @Override
    public void setData(BankActivityInfoBean data)
    {
        tvBankName.setText(data.getBankName());

        tvAcityName.setText("活动名称："+data.getActName());

        tvActivityDate.setText("活动期限："+data.getStartDate()+"至"+data.getEndDate());

        Glide.with(context).load(ServiceDispatcher.getImageUrl(data.getBankLogo())).into(ivBankLogo);
    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvBankName = findViewById(R.id.tvBankName);

        tvAcityName = findViewById(R.id.tvAcityName);

        ivBankLogo = findViewById(R.id.ivBankLogo);

        tvActivityDate = findViewById(R.id.tvActivityDate);
    }
}


