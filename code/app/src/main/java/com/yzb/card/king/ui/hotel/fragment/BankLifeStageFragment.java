package com.yzb.card.king.ui.hotel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.GoldTicketParam;
import com.yzb.card.king.bean.common.LifeStageDetailBean;
import com.yzb.card.king.jpush.util.DecorationUtil;
import com.yzb.card.king.ui.appwidget.appdialog.HotelProductRoomInfoFragmentDialog;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.adapter.SingleConditionAdapter;
import com.yzb.card.king.ui.hotel.holder.BankLifeStageEveryMoneyHolder;
import com.yzb.card.king.ui.hotel.persenter.HotelBankActivityPersenter;
import com.yzb.card.king.ui.ticket.fragment.TicketDetailFragmentDialog;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.lemon.view.adapter.MultiTypeAdapter;

/**
 * 类  名：银行生活分期
 * 作  者：Li Yubing
 * 日  期：2017/8/9
 * 描  述：
 */
public class BankLifeStageFragment extends Fragment implements View.OnClickListener ,BaseViewLayerInterface {

    private HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall;

    private RecyclerView wvLvData, wvBankLvData, wvLifeStageLvData;

    private TextView tvBankLifeMsg;

    private MultiTypeAdapter mAdapter;

    private SingleConditionAdapter bankLifeStageAdapter;

    private HotelBankActivityPersenter persenter;

    private  double productMoney;

    private TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        persenter = new HotelBankActivityPersenter(this);

        View view = inflater.inflate(R.layout.view_bank_life_stage, null);

        initView(view);

        initData();

        return view;
    }

    private void initData()
    {

     Bundle bundle =   getArguments();

        if(bundle.containsKey("goldTicketParam")){

            GoldTicketParam goldTicketParam = (GoldTicketParam) bundle.getSerializable("goldTicketParam");

             productMoney = bundle.getDouble("productMoney");

            int goodsType = bundle.getInt("goodsType");

            ProgressDialogUtil.getInstance().showProgressDialogMsg("正在请求数据……", getContext(), false);

            persenter.sendProductLifeStageActivityInfoRequest(goldTicketParam,goodsType,productMoney);

        }
    }

    private void initView(View view)
    {

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        tvTitle.setText("生活分期");

        tvBankLifeMsg = (TextView) view.findViewById(R.id.tvBankLifeMsg);

        tvBankLifeMsg.setText("¥"+productMoney);

        view.findViewById(R.id.llTemp).setOnClickListener(this);
        //银行
        wvBankLvData = (RecyclerView) view.findViewById(R.id.wvBankLvData);

        //分期数
        wvLifeStageLvData = (RecyclerView) view.findViewById(R.id.wvLifeStageLvData);

        //月供详情
        wvLvData = (RecyclerView) view.findViewById(R.id.wvLvData);

        mAdapter = new MultiTypeAdapter(getContext());

        wvLvData.setLayoutManager(new LinearLayoutManager(getContext()));

        wvLvData.setAdapter(mAdapter);


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llTemp:

                if(dataCall!=null){
                    dataCall.backAction();
                }

                if(ticketDetailDataCall != null){

                    ticketDetailDataCall.backAction(2);
                }

                break;
            default:
                break;

        }
    }

    public void setDataCall(HotelProductRoomInfoFragmentDialog.HotelDialogInterface dataCall)
    {
        this.dataCall = dataCall;
    }

    /**
     * 默认银行编号
     */
    private int collectionBankLifeStageDataIndex = 0;
    /**
     * 默认银行分期数据的编号
     */
    private int collectionLifeStageIndex = 0;


    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        final  List<LifeStageDetailBean> lifeStageDetailBeanList = JSONArray.parseArray(o+"",LifeStageDetailBean.class);

        loadLifeStageData(lifeStageDetailBeanList);


    }

    private void loadLifeStageData(final List<LifeStageDetailBean> lifeStageDetailBeanList){

        int size = lifeStageDetailBeanList.size();

        //手机银行信息，默认收集第一个银行分期数据
        List<String> brandBeanList = new ArrayList<>();

        List<String> beanLifeStageList = new ArrayList<>();

        for( int i = 0 ; i < size ; i++){

            LifeStageDetailBean detailBean = lifeStageDetailBeanList.get(i);

            String bankName = detailBean.getBankName();

            brandBeanList.add(bankName);

            if( i == collectionBankLifeStageDataIndex){

                List<LifeStageDetailBean.StageBean> stageBeanList = detailBean.getStageList();

                int stageSize = stageBeanList.size();

                for( int j =0 ; j <stageSize ; j++){

                    LifeStageDetailBean.StageBean stageBean = stageBeanList.get(j);

                    beanLifeStageList.add(stageBean.getStage());

                    if(j == collectionLifeStageIndex){

                        tvBankLifeMsg.setText("¥"+productMoney+"×"+stageBean.getStage());

                        //月供详情
                        mAdapter.clear();

                        mAdapter.addAll(BankLifeStageEveryMoneyHolder.class, calLifeStageDetail(stageBean));
                    }
                }

            }

        }


        SingleConditionAdapter subItemAdapter = new SingleConditionAdapter(getActivity(), brandBeanList);

        subItemAdapter.setItemDataCallBack(new SingleConditionAdapter.ItemDataCallBack() {
            @Override
            public void onSelectorItem(int index)
            {

                collectionBankLifeStageDataIndex = index;

                collectionLifeStageIndex = 0;

                List<LifeStageDetailBean.StageBean>  dataList =   lifeStageDetailBeanList.get(index).getStageList();

                List<String> brandBeanList12 = new ArrayList<>();

                if( dataList != null && dataList.size() >0){

                    for( int j =0 ; j <dataList.size() ; j++){

                        LifeStageDetailBean.StageBean stageBean = dataList.get(j);

                        brandBeanList12.add(stageBean.getStage());

                        if(j == collectionLifeStageIndex){

                            tvBankLifeMsg.setText("¥"+productMoney+"×"+stageBean.getStage());

                            //月供详情
                            mAdapter.clear();

                            mAdapter.addAll(BankLifeStageEveryMoneyHolder.class, calLifeStageDetail(stageBean));
                        }
                    }
                }
                bankLifeStageAdapter.addNewList(brandBeanList12);



            }
        });

        wvBankLvData.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(getContext(), 7)));

        wvBankLvData.setLayoutManager(new GridLayoutManager(getContext(), 3));

        wvBankLvData.setAdapter(subItemAdapter);


        bankLifeStageAdapter = new SingleConditionAdapter(getActivity(), beanLifeStageList);

        bankLifeStageAdapter.setItemDataCallBack(new SingleConditionAdapter.ItemDataCallBack() {
            @Override
            public void onSelectorItem(int index)
            {

                if (index == collectionLifeStageIndex) {

                } else {

                    collectionLifeStageIndex = index;

                    //查询银行下的某个分期信息
                    List<LifeStageDetailBean.StageBean>  dataList =   lifeStageDetailBeanList.get(collectionBankLifeStageDataIndex).getStageList();


                    tvBankLifeMsg.setText("¥"+productMoney+"×"+dataList.get(index).getStage());

                    //月供详情
                    mAdapter.clear();

                    mAdapter.addAll(BankLifeStageEveryMoneyHolder.class, calLifeStageDetail(dataList.get(index)));
                }

            }
        });

        wvLifeStageLvData.addItemDecoration(new DecorationUtil(CommonUtil.dip2px(getContext(), 7)));

        wvLifeStageLvData.setLayoutManager(new GridLayoutManager(getContext(), 3));

        wvLifeStageLvData.setAdapter(bankLifeStageAdapter);

    }

    /**
     * 计算月供详情
     */
    private List<String > calLifeStageDetail( LifeStageDetailBean.StageBean stageBean){

        List<String> list = new ArrayList<>();

        String stageStr = stageBean.getStage().replace("期","");

        int stageNumber = Integer.parseInt(stageStr);
        /**
         * 计算每期月供详情，第一期需要添加个手续费用
         * 每期还款费用需要添加个利率金额
         */
        double avgMone = productMoney/stageNumber;

        double rateMoney = avgMone*stageBean.getRate();

        double shouxuMoney = productMoney*stageBean.getServiceFee();

        double tatalMoney = avgMone+rateMoney;

        for( int a = 0 ; a < stageNumber ; a++){

            StringBuffer sb = new StringBuffer();

            int b = a+1;

            sb.append("第").append(AppUtils.ToCH(b)).append("期  ¥");

            if(b == 1){

                double t1 = tatalMoney+shouxuMoney;
                String a1 = Utils.subZeroAndDot(t1+"");
                sb.append(a1);

            }else {
                String a1 = Utils.subZeroAndDot(tatalMoney+"");
                sb.append(a1);

            }

            list.add(sb.toString());
        }

        return  list;

    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();
    }

    public void setTicketDetailDataCall(TicketDetailFragmentDialog.TicketDialogInterface ticketDetailDataCall)
    {
        this.ticketDetailDataCall = ticketDetailDataCall;
    }
}
