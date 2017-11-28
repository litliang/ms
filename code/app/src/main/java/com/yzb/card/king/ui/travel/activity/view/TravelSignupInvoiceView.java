package com.yzb.card.king.ui.travel.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.appwidget.BaseViewGroup;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.util.Utils;

/**
 * 功能：旅游报名报销凭证view；
 *
 * @author:gengqiyun
 * @date: 2016/11/25
 */
public class TravelSignupInvoiceView extends BaseViewGroup implements View.OnClickListener, SlideButton.OnToggleStateChangeListener
{
    private SlideButton bxpzSlideButton; //报销凭证开关；
    private View panelInvoiceView;//报销凭证块；
    private ImageView ivAddressArrow;
    private View panelSubAddress;
    private TextView tvPeopleName;
    private TextView tvPeoplePhone;
    private TextView tvPeopleAddress;
    private TextView tvPostage;
    private PostFeeBean postFeeBean; //邮费；
    private DebitRiseBean debitRiseBean;
    private TextView tvRiseName;
    private GoodsAddressBean addressBean;
    private boolean addressExpanded;//收获地址的伸展状态；false：合并；true：展开；
    private OnClickListener listener;

    public TravelSignupInvoiceView(Context context)
    {
        super(context);
    }

    public TravelSignupInvoiceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void init()
    {
        super.init();
        panelInvoiceView = rootView.findViewById(R.id.panelInvoiceView);
        panelInvoiceView.setVisibility(GONE);

        bxpzSlideButton = (SlideButton) rootView.findViewById(R.id.bxpzSlideButton);
        bxpzSlideButton.setOnToggleStateChangeListener(this);
        bxpzSlideButton.setToggleState(SlideButton.ToggleState.close);

        rootView.findViewById(R.id.panelRise).setOnClickListener(this);
        rootView.findViewById(R.id.panelAddress).setOnClickListener(this);

        ivAddressArrow = (ImageView) rootView.findViewById(R.id.ivAddressArrow);

        rootView.findViewById(R.id.panelGetAddress).setOnClickListener(this);

        panelSubAddress = rootView.findViewById(R.id.panelSubAddress);
        panelSubAddress.setVisibility(GONE);

        tvRiseName = (TextView) rootView.findViewById(R.id.tvRiseName);
        tvPostage = (TextView) rootView.findViewById(R.id.tv_postage);
        tvPeopleName = (TextView) rootView.findViewById(R.id.tvPeopleName);
        tvPeoplePhone = (TextView) rootView.findViewById(R.id.tvPeoplePhone);
        tvPeopleAddress = (TextView) rootView.findViewById(R.id.tvPeopleAddress);
    }

    public void setViewClickCall(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.panelRise: //发票抬头
            case R.id.panelGetAddress: //获取收获地址；
                if (listener != null)
                {
                    listener.onClick(v);
                }
                break;
            case R.id.panelAddress: //收获地址；
                if (listener != null)
                {
                    listener.onClick(v);
                }
                updateAddressState();
                break;
        }
    }

    public PostFeeBean getPostFeeBean()
    {
        return postFeeBean;
    }

    /**
     * 更改收获地址块的伸展状态；
     */
    private void updateAddressState()
    {
        panelSubAddress.setVisibility(addressExpanded ? GONE : VISIBLE);
        ivAddressArrow.setImageResource(addressExpanded ? R.mipmap.icon_arrow_right_grey_travel : R.mipmap.icon_arrow_down_middle);
        addressExpanded = !addressExpanded;
    }

    /**
     * 设置邮费；
     *
     * @param postFee
     */
    public void setPostage(PostFeeBean postFee)
    {
        this.postFeeBean = postFee;
        tvPostage.setText(postFeeBean.getLogisticsName() + "(" + Utils.subZeroAndDot(postFeeBean.getFee() + "") + "元)");
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_travel_signup_invoice;
    }

    @Override
    public void onToggleStateChange(SlideButton.ToggleState state)
    {
        panelInvoiceView.setVisibility(state == SlideButton.ToggleState.open ? View.VISIBLE : View.GONE);
        //显示的时候，获取相关信息；

        //同时打开收货地址；
        if (state == SlideButton.ToggleState.open)
        {
            rootView.findViewById(R.id.panelAddress).performClick();
        }
        if (listener != null)
        {
            listener.onClick(bxpzSlideButton);
        }
    }

    public boolean isChecked()
    {
        return bxpzSlideButton.getState() == SlideButton.ToggleState.open;
    }

    /**
     * 获取发票抬头；
     *
     * @return
     */
    public DebitRiseBean getDebitRiseBean()
    {
        return debitRiseBean;
    }

    public void setDebitRiseBean(DebitRiseBean debitRiseBean)
    {
        this.debitRiseBean = debitRiseBean;
        if (debitRiseBean != null)
        {
            tvRiseName.setText(debitRiseBean.content);
        }
    }

    public void setGoodsAddress(GoodsAddressBean addressBean)
    {
        this.addressBean = addressBean;
        if (addressBean != null)
        {
            tvPeopleName.setText(addressBean.getContacts());
            tvPeoplePhone.setText(addressBean.getPhone());

            String city = addressBean.cityName.equals(addressBean.provinceName) ?
                    addressBean.cityName : addressBean.provinceName + addressBean.cityName;
            tvPeopleAddress.setText(city + addressBean.getDistrictName() + addressBean.address);
        }
    }

    public GoodsAddressBean getAddressBean()
    {
        return addressBean;
    }

}
