package com.yzb.card.king.ui.appwidget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yzb.card.king.bean.ticket.GoodActivityBean;
import com.yzb.card.king.bean.ticket.ShopGoodsActivityBean;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.bean.travel.TravelProduDetailBean;
import com.yzb.card.king.ui.discount.bean.BankBean;
import com.yzb.card.king.ui.discount.bean.PjBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.travel.view.ITravelDetailDataProvider;
import com.yzb.card.king.ui.travel.view.ITravelDetailViewNotifier;
import com.yzb.card.king.util.ToastUtil;

import java.util.List;


/**
 * 功能：ViewGroup 基类；
 *
 * @author:gengqiyun
 * @date: 2016/11/18
 */
public abstract class BaseViewGroup extends LinearLayout implements ITravelDetailViewNotifier
{
    protected int curHeight = 0;
    protected LayoutInflater inflater;
    protected View rootView;
    protected Context mContext;
    protected ITravelDetailDataProvider provider; //数据提供者；

    public BaseViewGroup(Context context)
    {
        super(context);
        init();
    }

    public BaseViewGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BaseViewGroup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }


    protected void toast(int resId)
    {
        ToastUtil.i(mContext, resId);
    }

    protected void toast(String msg)
    {
        ToastUtil.i(mContext, msg);
    }

    /**
     * 初始化view；
     */
    protected void init()
    {
        mContext = getContext();
        inflater = LayoutInflater.from(mContext);
        setOrientation(VERTICAL);
        if (getLayoutResId() > 0)
        {
            rootView = inflater.inflate(getLayoutResId(), this);
        }
    }

    protected abstract int getLayoutResId();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        curHeight = h;
    }

    /**
     * 判断登录；
     *
     * @return
     */
    protected boolean isLogin()
    {
        return UserManager.getInstance().isLogin();
    }

    public boolean isEmpty(String msg)
    {
        if (TextUtils.isEmpty(msg))
        {
            return true;
        }
        return false;
    }

    /**
     * 获取当前view的新高度；
     */
    public int getCurHeight()
    {
        return curHeight;
    }

    @Override
    public void notifyDataChanged()
    {
        if (provider != null && provider.getProduDetailBean() != null)
        {
            fillViewData(provider.getProduDetailBean());
        }
    }

    /**
     * 通知优惠银行数据改变；
     */
    public void notifyCouponBanksChanged()
    {
        if (provider != null && provider.getCouponBankBean() != null)
        {
            fillViewCouponBanks(provider.getCouponBankBean());
        }
    }

    /**
     * 通知线路列表数据改变；
     */
    public void notifyTravelLineChanged()
    {
        if (provider != null && provider.getTravelLineBeans() != null)
        {
            fillViewTravelLines(provider.getTravelLineBeans());
        }
    }

    /**
     * 通知评价列表数据改变；
     */
    public void notifyEvaluateChanged()
    {
        if (provider != null && provider.getPjBeanList() != null)
        {
            fillViewEvaluates(provider.getPjBeanList());
        }
    }

    /**
     * 通知日期列表数据改变；
     */
    public void notifyDateListChanged()
    {
        if (provider != null && provider.getDateBeanList() != null)
        {
            fillViewDateList(provider.getDateBeanList());
        }
    }

    /**
     * 子类填充评价列表；
     *
     * @param pjBeanList
     */
    public void fillViewDateList(List<DateBean> pjBeanList)
    {

    }

    /**
     * 子类填充评价列表；
     *
     * @param pjBeanList
     */
    public void fillViewEvaluates(List<PjBean> pjBeanList)
    {

    }

    /**
     * 子类填充线路列表数据；
     *
     * @param lineBeans
     */
    public void fillViewTravelLines(List<TravelLineBean> lineBeans)
    {

    }

    /**
     * 子类填充优惠银行数据；
     *
     * @param detailBean
     */
    protected void fillViewCouponBanks(List<GoodActivityBean> detailBean)
    {

    }

    /**
     * 子类填充数据；
     *
     * @param detailBean
     */
    protected void fillViewData(TravelProduDetailBean detailBean)
    {

    }

    @Override
    public void setDataProvider(ITravelDetailDataProvider provider)
    {
        this.provider = provider;
    }
}
