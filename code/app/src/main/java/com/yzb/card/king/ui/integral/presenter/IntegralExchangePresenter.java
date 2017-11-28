package com.yzb.card.king.ui.integral.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jamgle.pickerviewlib.utils.AppUtils;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.ExchangeIntegralBean;
import com.yzb.card.king.bean.integral.ExchangeIntegralListBean;
import com.yzb.card.king.bean.integral.ExchangeMileageBean;
import com.yzb.card.king.bean.integral.IntegralExchangeBean;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.appwidget.popup.GetPayMsgDialog;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.integral.model.IIntegralExchange;
import com.yzb.card.king.ui.integral.model.impl.IntegralExchangeImpl;
import com.yzb.card.king.ui.manage.IntegralDataManager;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：积分兑换观察者
 * 作  者：Li Yubing
 * 日  期：2016/9/13
 * 描  述：
 */
public class IntegralExchangePresenter extends AccountSchemaPresenter implements DataCallBack {

    private BaseViewLayerInterface viewIfc;

    private IIntegralExchange exchange;

    private GetPayMsgDialog.Builder msgBuilder;

    private long needAllIntegral;

    private Activity activity;

    public IntegralExchangePresenter(BaseViewLayerInterface viewIfc)
    {

        super();

        this.viewIfc = viewIfc;

        exchange = new IntegralExchangeImpl(this);
    }

    /**
     * 查看兑换准则
     */
    public void goIntoExchangeNorm(String category, String titelName, Activity activity)
    {

        Bundle bundle = new Bundle();
        bundle.putString("category", category); //
        bundle.putString("titleName", titelName);
        Intent intent = new Intent(activity, WebViewClientActivity.class);
        intent.putExtra(AppConstant.INTENT_BUNDLE, bundle);
        activity.startActivity(intent);
    }


    /**
     * 发送航空公司列表请求
     */
    public void sendAirlineCompanyListRequest(Activity activity)
    {

        ProgressDialogUtil.getInstance().showProgressDialog(activity, false);

        exchange.sendAirlineCompanyListRequest(null);
    }


    /**
     * 发送用户积分请求
     *
     * @param act
     */
    public void sendUserIntegralRequest(Activity act)
    {
        if (UserManager.getInstance().getAccountInfo() == null) {
            //獲取账户平台积分
            UserBean userBean = UserManager.getInstance().getUserBean();

            if (userBean != null) {
                //发送用户积分请求
                sendUserIntegralRequest(userBean.getAccount(), act);

            }
        }
    }

    /**
     * 发送积分交换计划请求
     *
     * @param type
     * @param airlineId
     */
    public void sendExchangePointPlanRequest(int type, String airlineId, Activity activity)
    {

        ProgressDialogUtil.getInstance().showProgressDialog(activity, false);

        exchange.pointExchangePlanRequest(type, airlineId);

    }

    /**
     * 发送航空计划表请求
     *
     * @param airlineId 航空id
     * @param activity
     */
    public void sendFrequentPassengerPlanRequest(String airlineId, Activity activity)
    {

        ProgressDialogUtil.getInstance().showProgressDialog(activity, false);
        exchange.sendFrequentPassengerPlanRequest(airlineId);
    }

    /**
     * 发送积分兑换现金请求
     *
     * @param type
     * @param adapterDataList
     * @param planId
     * @param activity
     */
    public void sendIntegralExchangeCashRequest(int type, List<ExchangeIntegralBean> adapterDataList, String planId, Activity activity)
    {
        this.activity = activity;

        ProgressDialogUtil.getInstance().showProgressDialog(activity, false);

        exchange.sendIntegralPointExchangeCashRequest(type, adapterDataList, planId);
    }

    /**
     * 发送积分兑换油卡请求
     * @param act
     * @param type
     * @param adapterDataList
     * @param planId
     * @param status
     * @param addressId
     * @param postPage
     * @param number
     */
    public void sendIntegralExchagneYoukaRequest(Activity act,int type, List<ExchangeIntegralBean> adapterDataList,
                                                 String planId, String status,String addressId,String postPage,String number)
    {

        this.activity = act;


        exchange.sendIntegralPointExchangeYoukaRequest( type, adapterDataList,  planId,  status, addressId, postPage, number);
    }

    public void sendIntegralExchangePhoneRequest(Activity act,int type, List<ExchangeIntegralBean> adapterDataList, String planId, String moblie)
    {
        this.activity = act;
        exchange.sendIntegralPointExchangePhoneRequest( type, adapterDataList,  planId,  moblie);
    }

    /**
     * 发送扣除app平台积分
     *
     * @param needAllIntegral
     * @param name
     * @param activity
     */
    public void sendPayPointRequest(long needAllIntegral, String name,  Activity activity)
    {
        this.needAllIntegral = needAllIntegral;
        UserBean userBean = UserManager.getInstance().getUserBean();
        Map<String, String> params = new HashMap<String, String>();
        params.put("customerId", userBean.getAmountAccount());
        params.put("point", "" + needAllIntegral);
        params.put("summary", name);
        params.put("sessionId", AppConstant.sessionId);
        params.put("UUID", AppConstant.UUID);
        exchange.sendPayPointHandle(params, activity);
    }

    /**
     * 发送获取用户默认地址信息
     */
    public  void sendFaultLinkManInfo(){

        ProgressDialogUtil.getInstance().showProgressDialog(activity, false);
        exchange.getDefaultAddress();
    }

    /**
     * 检查登录
     *
     * @param act
     */
    public boolean chechLogin(Activity act)
    {

        UserBean userBean = UserManager.getInstance().getUserBean();

        if (userBean == null) {

            act.startActivity(new Intent(act, LoginActivity.class));

            return false;
        } else {
            return true;
        }
    }


    @Override
    public void requestSuccess(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (viewIfc != null) {

            if (o instanceof ExchangeMileageBean) {

                viewIfc.callSuccessViewLogic(o,1);
            }

            if (type == IIntegralExchange.POINTEXCHANGEPLAN_CODE) {//积分兑换计划

                List<ExchangeIntegralListBean> listBean = (List<ExchangeIntegralListBean>) o;

                viewIfc.callSuccessViewLogic(listBean,1);

            } else if (type == IIntegralExchange.FREQUENTPASSENGERPLANREQUEST_CODE) {

                List<ExchangeIntegralListBean> userBean = (List<ExchangeIntegralListBean>) o;

                IntegralDataManager.getInstance().setTravellerPlanBeanList(userBean);

            } else if (type == IIntegralExchange.INTEGRALPOINTEXCHANGECASH_CODE) {

                IntegralExchangeBean sucess = (IntegralExchangeBean) o;

                msgBuilder = new GetPayMsgDialog.Builder(activity);

                msgBuilder.setColor("2");//1：蓝色标题 2：红色标题

                msgBuilder.setExchangeMoneyTitle(R.string.integral_exchange_money_tv);

                String hkType = sucess.getStatus();

                String lastDate = sucess.getCreateTime();

                String date = AppUtils.toData(Long.parseLong(lastDate), 13);

                String str = activity.getString(R.string.integral_exchange_success);

                msgBuilder.setMsg(str, sucess.getOrderNo(), date, sucess.getExchangeResult() + "元", str, sucess
                        .getExchangeResult());

                msgBuilder.create().show();

                myHandler.sendEmptyMessageDelayed(0, 2 * 1000);

                viewIfc.callSuccessViewLogic(null,-1);//不做任何处理

            } else if (type == IIntegralExchange.PAYPOINT_CODE) {
                //消减用户在该平台的积分
                UserManager.getInstance().subtractionShareNumber(needAllIntegral);

                viewIfc.callSuccessViewLogic(IIntegralExchange.SPECIAL_CODE,1);

            }else if( type == IIntegralExchange.DEFAULTADDRESS_CODE){

                //遍历出默认地址信息
                List<GoodsAddressBean> list = (List<GoodsAddressBean>) o;

                for(GoodsAddressBean temp : list){

                    if(temp.isDefault){

                        viewIfc.callSuccessViewLogic(temp,1);
                        break;

                    }
                }

            }

        }

    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        ProgressDialogUtil.getInstance().closeProgressDialog();

        if (type == IIntegralExchange.PAYPOINT_CODE) {

            ToastUtil.i(activity, R.string.integral_exchange_failed);
        }
    }


    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            if (msgBuilder != null) {
                msgBuilder.dismiss();
            }
        }
    };

}
