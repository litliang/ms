package com.yzb.card.king.ui.discount.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.ui.luxury.activity.IDialogDismissCallBack;
import com.yzb.card.king.ui.discount.bean.HbBean;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.bean.WalletHomeInfo;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.wallet.openInterface.AccountChargeHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by gengqiyun on 2016/4/20.
 * 领取红包对话框；
 */
public class LqhbDialogFragment extends BaseDialogFragment {
    private static LqhbDialogFragment dialogFragment;
    private HbDialogAdapter adapter = null;
    private List<HbBean> hbBeans;
    private String storeId = ""; //门店id
    private IDialogDismissCallBack callBack;

    public static LqhbDialogFragment getInstance(String arg1, String arg2) {

        synchronized (LqhbDialogFragment.class) {
            if (dialogFragment == null) {
                dialogFragment = new LqhbDialogFragment();
            }
        }
        return dialogFragment;
    }


    public void setShopId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (callBack != null) {
                    callBack.dialogDismissCallBack(1);
                }
            }
        });
        return dialog;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_hb_dialog;
    }

    @Override
    protected int getDialogHeight() {
        return CommonUtil.getScreenHeight(getActivity()) * 2 / 5;
    }

    /**
     * 获取红包列表；
     */
    private void getData() {
        if (TextUtils.isEmpty(storeId)) {
            return;
        }

        SimpleRequest<List<HbBean> > request = new SimpleRequest<List<HbBean> >(CardConstant.card_app_bounsbatchquery) {
            @Override
            protected List<HbBean>  parseData(String data)
            {
                return JSON.parseArray(data, HbBean.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("storeId", storeId);
        param.put("status", "1"); // 1:已发布；
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<HbBean>>() {
            @Override
            public void onSuccess(List<HbBean> data)
            {
                hbBeans = data;
                LogUtil.i("获取红包列表实体-result:" + hbBeans);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Error error)
            {
                if(error!= null){
                    ToastUtil.i(GlobalApp.getInstance().getContext(),error.getError());
                }

            }
        });


    }

    protected void initView(View view) {
        if (view != null) {
            ListView listView = (ListView) view.findViewById(R.id.listview);
            adapter = new HbDialogAdapter();
            listView.setAdapter(adapter);
        }
    }

    public void setDialogDismissCallBack(IDialogDismissCallBack iDialogDismissCallBack) {
        this.callBack = iDialogDismissCallBack;
    }

    private class HbDialogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hbBeans == null ? 0 : hbBeans.size();
        }

        @Override
        public HbBean getItem(int position) {
            return hbBeans == null ? null : hbBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_item_hb, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final HbBean hbBean = getItem(position);

            viewHolder.tvhbje.setText(hbBean.amount);
            viewHolder.tvsytj.setText("");

            viewHolder.tvsyqx.setText("使用期限  " + DateUtil.long2String(hbBean.startDate, DateUtil.DATE_FORMAT_DATE) + "~" +
                    DateUtil.long2String(hbBean.endDate, DateUtil.DATE_FORMAT_DATE));

            // 红包可用显示红色；否则显示灰色且不可点击；

            //是否可领取(0已领取完，1可领取)
            if (hbBean.receiveCode == 1) {
                viewHolder.tvljlq.setClickable(true);
                viewHolder.tvljlq.setTextColor(getResources().getColor(R.color.color_status));
                viewHolder.tvljlq.setBackgroundResource(R.drawable.btn_ljlq_red_selector);
                viewHolder.tvljlq.setText("立即领取");

                viewHolder.tvljlq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发送领取请求；
                        exeLqhb(hbBean);
                    }
                });
            } else {
                viewHolder.tvljlq.setClickable(false);
                viewHolder.tvljlq.setTextColor(getResources().getColor(R.color.text_color_1));
                viewHolder.tvljlq.setBackgroundResource(R.drawable.btn_ljlq_gray_bg);
                viewHolder.tvljlq.setText("已领完");
            }

            return convertView;
        }

        public class ViewHolder {
            public final TextView tvhbje; //红包金额；
            public final TextView tvsytj; //使用条件；
            public final TextView tvsyqx; //使用期限；
            public final TextView tvljlq; //立即领取；
            public final View root;

            public ViewHolder(View root) {
                tvhbje = (TextView) root.findViewById(R.id.tv_hbje);
                tvsytj = (TextView) root.findViewById(R.id.tv_sytj);
                tvsyqx = (TextView) root.findViewById(R.id.tv_syqx);
                tvljlq = (TextView) root.findViewById(R.id.tv_ljlq);
                this.root = root;
            }
        }
    }

    private void deleSpeBean(String id) {
        if (hbBeans != null && hbBeans.size() > 0) {
            for (int i = 0; i < hbBeans.size(); i++) {
                if (!TextUtils.isEmpty(id) && id.equals(hbBeans.get(i).id)) {
                    hbBeans.remove(i);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    /**
     * 领红包；
     *
     * @param hbBean
     */
    private void exeLqhb(final HbBean hbBean) {
        SimpleRequest<String> request = new SimpleRequest<String>( CardConstant.card_app_addcustomerbouns) {
            @Override
            protected String parseData(String data)
            {
                return data;
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("batchId", hbBean.id);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<String>() {
            @Override
            public void onSuccess(String data)
            {
                collectBill(hbBean);
            }

            @Override
            public void onFail(Error error)
            {
              //  UiUtils.shortToast(error.getError());
                ToastUtil.i(getActivity(), "领取失败");
            }
        });

    }

    /**
     * 账户收款
     *
     * @param hbBean
     */
    private void collectBill(final HbBean hbBean) {
        if (hbBean == null) return;

        Map<String, String> params = new HashMap<>();

        if ( UserManager.getInstance().getUserBean() != null) {
            params.put("customerId",  UserManager.getInstance().getUserBean().getAmountAccount());
        }
        params.put("accountType", "2"); //账户类型（1余额,2红包,3礼品卡,4积分)
        params.put("amount", hbBean.amount);

        AccountChargeHandle chargeHandle = new AccountChargeHandle(getActivity());
        chargeHandle.charge(params);
        chargeHandle.setCallBack(new WalletBackListener() {
                                     @Override

                                     public void setSuccess(String RESULT_CODE) {
                                         LogUtil.i("======收款操作=======>" + RESULT_CODE);
                                         LqSucessDialogFragment sdf0 = LqSucessDialogFragment.getInstance(hbBean.amount, "1");
                                         sdf0.show(getChildFragmentManager(), "LqSucessDialogFragment");

                                         adapter.notifyDataSetChanged();
                                         deleSpeBean(hbBean.id);
                                     }

                                     @Override
                                     public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                                     }

                                     @Override
                                     public void setError(String RESULT_CODE, String ERROR_MSG) {
                                         LogUtil.i("======收款操作=======>" + RESULT_CODE);
                                         ToastUtil.i(getActivity(), "领取失败");
                                     }
                                 }
        );
    }
}
