package com.yzb.card.king.ui.credit.holder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.credit.activity.AddCanPayCardActivity;
import com.yzb.card.king.ui.credit.bean.CreditCard;
import com.yzb.card.king.ui.credit.fragment.CreditIndexFragment;
import com.yzb.card.king.ui.credit.interfaces.ICardHolder;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.UiUtils;

import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/23
 */
public class CardHolder extends Holder<CreditCard> {
    private ImageView ivCard;
    private TextView tvSearch;
    private TextView tvDuration;
    private TextView tvRepayment;
    private ICardHolder<CreditCard> iCardHolder;
    private View view;
    private TextView tvBind;
    private TextView tvUserCardInfo;

    public CardHolder(ICardHolder<CreditCard> iCardHolder) {
        super();
        this.iCardHolder = iCardHolder;
        initListener();
    }

    @Override
    public View initView() {
        view = UiUtils.inflate(R.layout.holder_card);
        ivCard = (ImageView) view.findViewById(R.id.ivCard);
        tvRepayment = (TextView) view.findViewById(R.id.tvRepayment);
        tvDuration = (TextView) view.findViewById(R.id.tvDuration);
        tvSearch = (TextView) view.findViewById(R.id.tvSearch);
        tvBind = (TextView) view.findViewById(R.id.tvBind);
        tvUserCardInfo = (TextView) view.findViewById(R.id.tvUserCardInfo);
        return view;
    }

    private void initListener() {
        tvBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iCardHolder.unBindCard(getData());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreditCard  cc = getData();

                if(cc.isPayType()){

                    iCardHolder.goNextPage(cc);

                }else {

                    iCardHolder.bindCard(cc);
                }

            }
        });
    }

    @Override
    public void refreshView(CreditCard data) {
        x.image().bind(ivCard, ServiceDispatcher.getImageUrl(data.getPhoto()));

        tvRepayment.setText(UiUtils.getString(R.string.card_repayment, data.getLastRepaymentDate()));

        tvDuration.setText(getDurationString());

       // tvBind.setText(data.isPayType()?"解 绑":"删 除");

        tvSearch.setText(data.isPayType()?"还 款":"绑 卡");

        SpannableString ss = new SpannableString("****"+data.getSortNo());
        ss.setSpan(new AbsoluteSizeSpan(10,true),  0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvUserCardInfo.setText(ss);
        setDurationColor();
    }

    private void setDurationColor() {
        SpannableStringBuilder builder = new SpannableStringBuilder(tvDuration.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(UiUtils.getColor(R.color.ticket_red));
        ForegroundColorSpan graySpan = new ForegroundColorSpan(UiUtils.getColor(R.color.gray6));
        builder.setSpan(graySpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(redSpan, 4, getDurationString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDuration.setText(builder);
    }

    @NonNull
    private String getDurationString() {
        return UiUtils.getString(R.string.card_duration, getData().getRepaymentDays());
    }
}
