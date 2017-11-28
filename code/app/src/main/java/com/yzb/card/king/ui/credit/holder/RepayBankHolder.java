package com.yzb.card.king.ui.credit.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.presenter.CreditIndexPresenter;
import com.yzb.card.king.ui.my.activity.SelectCreditActivity;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

import java.util.ArrayList;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/25
 */

public class RepayBankHolder extends Holder<CreditCard> implements BaseViewLayerInterface
{
    private static final int LOAD_IDLE = 0;
    private static final int LOAD_BEGIN = 1;
    private static final int LOAD_END = 2;
    private static final int REQ_SELECT_CARD = 3;
    private final Activity activity;
    private ImageView ivBankLogo;
    private TextView tvTailNum;
    private TextView tvBankName;
    private TextView tvUserName;
    private CreditIndexPresenter creditIndexPresenter;
    private ArrayList<CreditCard> creditList;
    private int state = LOAD_IDLE;

    public RepayBankHolder(Activity activity)
    {
        super();
        this.activity = activity;
        loadData();
    }

    private void loadData()
    {
        creditIndexPresenter = new CreditIndexPresenter(this, null);
        state = LOAD_BEGIN;
        creditIndexPresenter.queryUserBindCard();
    }

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_repay_bank);
        ivBankLogo = (ImageView) view.findViewById(R.id.ivBankLogo);
        tvBankName = (TextView) view.findViewById(R.id.tvBankName);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvTailNum = (TextView) view.findViewById(R.id.tvTailNum);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (creditList != null && creditList.size() > 0)
                {
                    Intent intent = new Intent(activity, SelectCreditActivity.class);
                    intent.putExtra("creditList", creditList);
                    activity.startActivityForResult(intent, REQ_SELECT_CARD);
                } else
                {
                    UiUtils.shortToast(getMsg());
                }
            }
        });
        return view;
    }

    private String getMsg()
    {
        if (state == LOAD_END)
        {
            return "数据加载失败";
        } else
        {
            return "正在加载数据，请稍后再试";
        }
    }

    @Override
    public void refreshView(CreditCard data)
    {
        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(data.getLogo()));
        tvBankName.setText(data.getBankName());
        tvUserName.setText(data.getUserName());
        tvTailNum.setText(UiUtils.getString(R.string.card_tail_num, data.getSortNo()));
    }

    @Override
    protected void OnDataNull()
    {
        ivBankLogo.setImageResource(R.drawable.bg_transparent);
        tvBankName.setText(null);
        tvUserName.setText(null);
        tvTailNum.setText(null);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case REQ_SELECT_CARD:
                    setData((CreditCard) data.getSerializableExtra("creditCard"));
                    break;
            }
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        state = LOAD_END;
        creditList = (ArrayList<CreditCard>) o;
        if (creditList != null && creditList.size() > 0)
        {
            if (getData() == null)
            {
                setData(creditList.get(0));
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        state = LOAD_END;
    }
}
