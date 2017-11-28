package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.credit.activity.AddNoPayCardActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.presenter.CreditIndexPresenter;
import com.yzb.card.king.ui.my.holder.SelectCreditHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2017/1/19 18:17
 */
public class SelectCreditActivity extends SelectCardActivity implements BaseViewLayerInterface
{
    private static final int REQ_ADD_NEW_CARD = 2;
    private List<CreditCard> creditList = new ArrayList<>();
    private AbsBaseListAdapter<CreditCard> creditAdapter;
    private CreditIndexPresenter creditIndexPresenter;

    @Override
    protected void initData()
    {
        setTitleNmae("选择信用卡");
        creditAdapter = new AbsBaseListAdapter<CreditCard>(creditList)
        {

            @Override
            protected Holder getHolder(final int position)
            {
                SelectCreditHolder holder =  new SelectCreditHolder();
                holder.setListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("creditCard",creditList.get(position));
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
                return holder;
            }
        };
        listView.setAdapter(creditAdapter);

        List<CreditCard> list = (List<CreditCard>) getIntent().getSerializableExtra("creditList");
        if (list != null)
        {
            creditList.addAll(list);
        }

        creditAdapter.notifyDataSetChanged();

    }

    private void loadData()
    {
        creditIndexPresenter = new CreditIndexPresenter(this, null);
        creditIndexPresenter.queryUserBindCard();
    }

    @NonNull
    @Override
    protected String getFooterText()
    {
        return "添加信用卡";
    }

    @Override
    protected void addNewCard()
    {
        startActivityForResult(new Intent(this, AddNoPayCardActivity.class), REQ_ADD_NEW_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == REQ_ADD_NEW_CARD)
        {
            loadData();
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        creditList.clear();
        creditList.addAll((Collection<? extends CreditCard>) o);
        creditAdapter.notifyDataSetChanged();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        creditList.clear();
        creditAdapter.notifyDataSetChanged();
    }
}
