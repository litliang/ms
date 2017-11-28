package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.popup.ListPopup;
import com.yzb.card.king.ui.appwidget.popup.MyBankPopup;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.ui.other.activity.SelectPlaceActivity;
import com.yzb.card.king.util.CommonUtil;

import java.util.List;

/**
 * 类名：OptionView
 * 作者：殷曙光
 * 日期：2016/6/13 15:45
 * 描述：
 */

public class OptionView extends LinearLayout implements View.OnClickListener, ListPopup.OnItemClick {
    private LinearLayout llMyBank;
    private LinearLayout llType;
    private LinearLayout llCity;
    private PopupWindow flPopWindow;
    private List<CategoryBean> categoryBeans;
    private TextView tvCity;
    private TextView tvType;
    private ListPopup listPopup;
    private MyBankPopup myBankPopup;
    private ImageView ivType;
    private ImageView ivBank;
    private TextView tvMyBank;

    public OptionView(Context context) {
        super(context);
        init();
    }


    public OptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.view_option, this);
        llMyBank = (LinearLayout) rootView.findViewById(R.id.llMyBank);
        llType = (LinearLayout) rootView.findViewById(R.id.llType);
        llCity = (LinearLayout) rootView.findViewById(R.id.llCity);
        tvCity = (TextView) rootView.findViewById(R.id.tvCity);
        tvType = (TextView) rootView.findViewById(R.id.tvType);
        tvMyBank = (TextView) rootView.findViewById(R.id.tvMyBank);
        ivBank = (ImageView) rootView.findViewById(R.id.ivBank);
        ivType = (ImageView) rootView.findViewById(R.id.ivType);

        llMyBank.setOnClickListener(this);
        llType.setOnClickListener(this);
        llCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyBank:
                myBankPopup = new MyBankPopup(getContext());
                myBankPopup.showAsDropDown(this, 0, 1);
                startInverse(ivBank, R.anim.rotate_animation_start);
                myBankPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        startInverse(ivBank, R.anim.rotate_animation_end);
                    }
                });
                myBankPopup.setOnBankClickListener(new OnBankClickListener());
                break;
            case R.id.llType:
                listPopup = new ListPopup(getContext());
                listPopup.showAsDropDown(this, CommonUtil.dip2px(getContext(), 120), 1);
                startInverse(ivType, R.anim.rotate_animation_start);
                listPopup.setOnItemClick(this);
                listPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        startInverse(ivType, R.anim.rotate_animation_end);
                    }
                });
                break;
            case R.id.llCity:
                getContext().startActivity(new Intent(getContext(), SelectPlaceActivity.class));
                break;
        }
    }

    public void setCity(String startCityName) {
        tvCity.setText(startCityName);
    }

    /**
     * 旋转动画
     *
     * @author ysg
     * created at 2016/6/8 10:08
     */
    public void startInverse(View view, int animationResId) {
        view.startAnimation(AnimationUtils.loadAnimation(getContext(), animationResId));
    }

    @Override
    public void onItemClick(String name, String typeId) {
        tvType.setText(name);
        listPopup.dismiss();
        if(onTypeChangeListener != null){
            onTypeChangeListener.onTypeChange(typeId);
        }
    }

    public void setType(String typeName) {
        tvType.setText(typeName);

    }

    public void setBankName(String bankName) {
        tvMyBank.setText(bankName);
    }

    private OnBankChangeListener onBankChangeListener;

    public void setOnBankChangeListener(OnBankChangeListener onBankChangeListener) {
        this.onBankChangeListener = onBankChangeListener;
    }

    public void setOnTypeChangeListener(OnTypeChangeListener onTypeChangeListener) {
        this.onTypeChangeListener = onTypeChangeListener;
    }

    class OnBankClickListener implements MyBankPopup.OnBankClickListener {
        @Override
        public void onBankClick(String bankId, String bankName) {
            tvMyBank.setText(bankName);
            if(onBankChangeListener!=null){
                onBankChangeListener.onBankChange(bankId);
            }
        }
    }

    public interface OnBankChangeListener {
        void onBankChange(String bankId);
    }

    private OnTypeChangeListener onTypeChangeListener;
    public interface OnTypeChangeListener {
        void onTypeChange(String typeId);
    }
}
