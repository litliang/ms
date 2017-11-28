package com.yzb.card.king.ui.credit.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/21
 */
public class AllBankHolder extends Holder<Bank>
{
    private ImageView ivPic;
    private TextView tvName;
    private View view;
    private ImageView ivArrow;

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.holder_all_bank);
        ivPic = (ImageView) view.findViewById(R.id.ivPic);
        ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
        tvName = (TextView) view.findViewById(R.id.tvName);
        return view;
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        view.setOnClickListener(listener);
    }



    public void setArrowVisibility(int visibility)
    {
        ivArrow.setVisibility(visibility);
    }

    @Override
    public void refreshView(Bank data)
    {
        x.image().bind(ivPic, ServiceDispatcher.getImageUrl(data.getBankLogo()));
        tvName.setText(data.getBankName());
    }
}
