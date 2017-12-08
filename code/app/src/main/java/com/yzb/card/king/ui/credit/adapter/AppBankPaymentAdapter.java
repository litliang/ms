package com.yzb.card.king.ui.credit.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;

import com.yzb.card.king.bean.common.LifeStageDetailBean;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.ui.credit.holder.AppBankPaymentMethodHolder;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/15
 * 描  述：
 */
public class AppBankPaymentAdapter extends RecyclerAdapter<PaymethodAndBankPreStageBean> {

    private AdapterDataCallBack adapterDataCallBack;

    public AppBankPaymentAdapter(Context context, AdapterDataCallBack adapterDataCallBack)
    {
        super(context);

        this.adapterDataCallBack = adapterDataCallBack;
    }


    @Override
    public BaseViewHolder<PaymethodAndBankPreStageBean> onCreateBaseViewHolder(ViewGroup viewGroup, int i)
    {
        return new AppBankPaymentMethodHolder(viewGroup, adapterDataCallBack, uiHandler);
    }

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            int what = msg.what;

            if (what >= 0) {

                List<PaymethodAndBankPreStageBean> list = getData();

                int size = list.size();

                for (int i = 0; i < size; i++) {

                    PaymethodAndBankPreStageBean bean = list.get(i);

                    if (i == what) {

                        bean.setSelected(true);

                    } else {

                        bean.setSelected(false);
                    }

                }
                notifyDataSetChanged();

            } else {

                int arg1 = msg.arg1;//选择分期对象

                if (arg1 != 0) {//表示有分期信息

                    int index = arg1 - 1;//分期数据,从支付方式分期信息中获选中的分期信息，而“不分期”是本地造的标识数据,因此减一

                    int arg2 = msg.arg2;//选择支付方式,在付款方式数据集合中的编号

                    List<PaymethodAndBankPreStageBean> list = getData();

                    int size = list.size();

                    for (int i = 0; i < size; i++) {

                        PaymethodAndBankPreStageBean bean = list.get(i);

                        if (i == arg2) {

                            PaymethodAndBankPreStageBean paymethodAndBankPreStageBean = list.get(arg2);

                            if(paymethodAndBankPreStageBean.getStageList() != null){

                                LifeStageDetailBean.StageBean stageBeanList = paymethodAndBankPreStageBean.getStageList().get(index);

                                paymethodAndBankPreStageBean.setSelectedBean(stageBeanList);
                            }

                        } else {

                            bean.setSelectedBean(null);

                        }
                    }

                    notifyDataSetChanged();
                }else {

                    notifyDataSetChanged();

                }

            }
        }
    };

    public interface AdapterDataCallBack {

        public void selectedPayMethod(PaymethodAndBankPreStageBean payMethod);

        public void hideOrShowOtherPayData(boolean ifShow);

    }
}
