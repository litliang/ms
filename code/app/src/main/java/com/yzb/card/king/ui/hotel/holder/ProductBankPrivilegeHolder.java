package com.yzb.card.king.ui.hotel.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.hotel.HotelProductListParam;
import com.yzb.card.king.sys.GlobalVariable;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.HotelLogicManager;
import com.yzb.card.king.ui.hotel.activtiy.HotelBankPreferentialListActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelProductListActivity;
import com.yzb.card.king.ui.hotel.persenter.HotelBankActivityPersenter;
import com.yzb.card.king.util.ToastUtil;

import cn.lemon.view.adapter.BaseViewHolder;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/17
 * 描  述：
 */
public class ProductBankPrivilegeHolder extends BaseViewHolder<BankActivityInfoBean> implements View.OnClickListener{

    private TextView tvBankName,tvActivityName,tvActivityData;

    private ImageView ivBankLogo;

    private LinearLayout llBankPreOne;

    private Context context;

    public ProductBankPrivilegeHolder(ViewGroup parent)
    {
        super(parent, R.layout.view_item_bank_privilege);

        context = parent.getContext();
    }

    @Override
    public void setData(BankActivityInfoBean data)
    {
        super.setData(data);

        Glide.with(context).load(ServiceDispatcher.getImageUrl(data.getBankLogo())).into(ivBankLogo);

        tvBankName.setText(data.getBankName());

        tvActivityName.setText("活动名称："+data.getActName());

        tvActivityData.setText("活动期限："+data.getStartDate()+"至"+data.getEndDate());

        llBankPreOne.setTag(data);

    }

    @Override
    public void onInitializeView()
    {
        super.onInitializeView();

        tvBankName = findViewById(R.id.tvBankName);

        tvActivityName = findViewById(R.id.tvActivityName);

        tvActivityData = findViewById(R.id.tvActivityData);

        ivBankLogo = findViewById(R.id.ivBankLogo);

        llBankPreOne = findViewById(R.id.llBankPreOne);

        llBankPreOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llBankPreOne:

                if(HotelBankPreferentialListActivity.industryId !=  GlobalVariable.industryId){

                    ToastUtil.i(context,"当前不支持其它行业银行优惠");

                    return;
                }

                if(v.getTag() != null){

                    BankActivityInfoBean data = (BankActivityInfoBean) v.getTag();

                    HotelProductListParam productListParam = HotelLogicManager.getInstance().getHotelProductListParam();

                    productListParam.setBankActId(data.getActId()+"");

                    productListParam.setBankIds(data.getBankId()+"");

                    productListParam.setBankStageId(null);

                    productListParam.setBankStageId(null);

                    Intent intent = new Intent(context, HotelProductListActivity.class);

                    intent.putExtra("bankData",data);

                    intent.putExtra("bankType", HotelBankActivityPersenter.BANK_LIFE_STAGE_LIST_CODE);

                    context.startActivity(intent);

                }

                break;
            default:
                break;

        }
    }
}
