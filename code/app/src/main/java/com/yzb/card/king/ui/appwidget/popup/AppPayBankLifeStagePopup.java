package com.yzb.card.king.ui.appwidget.popup;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.BankActivityInfoBean;
import com.yzb.card.king.bean.common.LifeStageDetailBean;
import com.yzb.card.king.bean.common.PaymethodAndBankPreStageBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.hotel.adapter.SingleConditionAdapter;
import com.yzb.card.king.bean.common.PayMethod;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/13
 * 描  述：
 */
public class AppPayBankLifeStagePopup {

    private ImageView ivBankLogo;

    private ImageView ivLifeStages;


    private TextView tvBankName;

    private TextView tvBankCode;

    private TextView tvYouhui;

    private TextView tvConfirm;

    private BaseFullPP baseBottomFullPP;

    private Activity activity;

    private BottomDataListCallBack callBack;

    private PaymethodAndBankPreStageBean payMethod;

    private SingleConditionAdapter subItemAdapter;

    /**
     * @param activity
     * @param defineHeight 自定义子视图的高度
     * @param postion
     */
    public AppPayBankLifeStagePopup(Activity activity, int defineHeight, BaseFullPP.ViewPostion postion)
    {

        this.activity = activity;

        initView(defineHeight, postion);

    }

    private void initView(int defineHeight, BaseFullPP.ViewPostion postion)
    {


        baseBottomFullPP = new BaseFullPP(activity, postion);

        View view = LayoutInflater.from(this.activity).inflate(R.layout.view_bottom_app_pay_bank_life_stage, null);

        ivBankLogo = (ImageView) view.findViewById(R.id.ivBankLogo);

        ivLifeStages = (ImageView) view.findViewById(R.id.ivLifeStages);

        tvBankCode = (TextView) view.findViewById(R.id.tvBankCode);

        tvYouhui = (TextView) view.findViewById(R.id.tvYouhui);

        tvBankName = (TextView) view.findViewById(R.id.tvBankName);

        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subItemAdapter!= null){

                    int subItemBean = subItemAdapter.getCurrentIndex();
                    selectedItemObject(subItemBean);
                }

            }
        });

        if (defineHeight > 0) {

            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defineHeight);


            view.setLayoutParams(lp);

        }

        baseBottomFullPP.addChildView(view);

//        baseBottomFullPP.setListener(new BaseFullPP.PpOndismisssListener() {
//            @Override
//            public void onClickListenerDismiss()
//            {
//                int subItemBean = subItemAdapter.getCurrentIndex();
//                selectedItemObject(subItemBean);
//            }
//        });

        RecyclerView wvBankLvData = (RecyclerView) view.findViewById(R.id.wvBankLvData);

        List<String> brandBeanList = new ArrayList<>();

        brandBeanList.add("不分期");

        subItemAdapter = new SingleConditionAdapter(activity, brandBeanList);

//        subItemAdapter.setItemDataCallBack(new SingleConditionAdapter.ItemDataCallBack() {
//            @Override
//            public void onSelectorItem(int subItemBean)
//            {
//
//                selectedItemObject(subItemBean);
//            }
//        });

        wvBankLvData.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(activity, 7)));

        wvBankLvData.setLayoutManager(new GridLayoutManager(activity, 3));

        wvBankLvData.setAdapter(subItemAdapter);
    }

    /**
     * 用户选择生活分期
     * @param subItemBean
     */
    private  void selectedItemObject(int subItemBean ){

        if (callBack != null) {

            if( subItemBean != 0){

                List<LifeStageDetailBean.StageBean> list =  payMethod.getStageList();

                payMethod.setSelectedBean(list.get(subItemBean-1));
            }

            callBack.onClickItemDataBack(payMethod, subItemBean);
        }
    }

    /**
     * 在屏幕底部全屏展示
     * @param rootView
     */
    public void showBottomByViewPP(View rootView)
    {

        baseBottomFullPP.show(rootView);

    }

    public void setCallBack(BottomDataListCallBack callBack)
    {
        this.callBack = callBack;
    }

    public void setPayMethod(PaymethodAndBankPreStageBean payMethod)
    {
        this.payMethod = payMethod;


        BankActivityInfoBean infoBean = payMethod.getBankActInfo();

        double favMoney = 0;

        if (infoBean != null) {

            int actCls = infoBean.getActCls();

            if (actCls == 1) {//满减

                tvYouhui.setText("满" + Utils.subZeroAndDot(infoBean.getFuuAmount()+"") + "减" + Utils.subZeroAndDot(infoBean.getDisContent()+""));

                if(payMethod.getPaymentMoney()>=infoBean.getFuuAmount()){

                    favMoney = infoBean.getDisContent();
                }

            } else if (actCls == 2) {//折扣

                String disStr = Utils.handNumberToString(Float.parseFloat("" + infoBean.getDisContent()));

                tvYouhui.setText("打" + disStr+"折");

                double discountRate = Double.valueOf("0."+infoBean.getDisContent());

                favMoney = discountRate*payMethod.getPaymentMoney();

            } else if (actCls == 3) {//随机立减

                tvYouhui.setText("随机立减");

                favMoney = 0;
            }

        } else {

            tvYouhui.setText("");
        }


        List<LifeStageDetailBean.StageBean> list = payMethod.getStageList();

        if (list != null && list.size() > 0) {

            ivLifeStages.setVisibility(View.VISIBLE);

            List<String> bankLifeStage = new ArrayList<>();

            bankLifeStage.add("不分期");

            //显示分期内容
            for (LifeStageDetailBean.StageBean stageBean : list) {

                String stageStr = stageBean.getStage().replace("期", "");

                int stageNumber = Integer.parseInt(stageStr);

                /**
                 * 扣除优惠信息，如减去满减、打折信息、随即减
                 */
                double totalMoneyKouMoney = payMethod.getPaymentMoney();

                if (infoBean != null){

                    totalMoneyKouMoney = totalMoneyKouMoney - favMoney;
                }
                /**
                 * 每期费用
                 */
                double avgMone =  totalMoneyKouMoney/ stageNumber;

                String msgStr = Utils.subZeroAndDot(avgMone+"") + "元×" + stageBean.getStage();

                bankLifeStage.add(msgStr);
            }

            subItemAdapter.addNewList(bankLifeStage);

        } else {

            ivLifeStages.setVisibility(View.GONE);

        }

        tvBankName.setText(payMethod.getBankName());

        tvBankCode.setText(payMethod.getTypeName() + " (" + payMethod.getSortNo() + ")");

        String bankLogo = payMethod.getBankLogo();

        if (!TextUtils.isEmpty(bankLogo)) {

            Glide.with(activity).load(ServiceDispatcher.getImageUrl(bankLogo)).into(ivBankLogo);
        }

    }

    public BaseFullPP getBaseBottomFullPP()
    {
        return baseBottomFullPP;
    }

    public interface BottomDataListCallBack {

        /**
         *
         * @param payMethod
         * @param selectIndex  0 表示 不分期数据；其它表示分期数据
         */
        void onClickItemDataBack(PaymethodAndBankPreStageBean payMethod, int selectIndex);
    }
}
