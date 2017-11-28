package com.yzb.card.king.ui.transport.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.activity.DebitRiseManageActivity;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 报销凭证试图
 * Created by tarena on 2016/6/1.
 */
public class InvoiceVoucherView
{
    private TextView tvPostage; //邮费；
    /**
     * 送货地址
     */
    private LinearLayout llSendGoodAddress;

    private LinearLayout llShippingAddress;

    private LinearLayout llRise;

    private ImageView ivInvoiceArrow;

    private ImageView ivModfyAddress;//修改送货地址

    private TextView tvRiseName;//抬头名

    private TextView tvPeopleName;//收货人名字

    private TextView tvPeoplePhone;//收货人手机号

    private TextView tvPeopleAddress;//收货人地址

    private Fragment fragment;

    private Context context;

    private Activity activity;

    //数据
    //数据
    private DebitRiseBean debitRiseBean = null;

    private GoodsAddressBean goodsAddressBean = null;


    public InvoiceVoucherView(View view, Context context, Fragment fragment)
    {

        this.fragment = fragment;

        this.context = context;

        initView(view);

        //发送获取默认抬头请求
        sendGetTaitouRequest();
        //发送获取默认常联系人请求
        sendGetLinkManRequest();

    }

    public InvoiceVoucherView(View view, Context context, Activity activity)
    {

        this.context = context;

        this.activity = activity;

        initView(view);

        //发送获取默认抬头请求
        sendGetTaitouRequest();
        //发送获取收货地址请求
        sendGetLinkManRequest();

    }

    public void setGoodsAddressBean(GoodsAddressBean temp)
    {
        this.goodsAddressBean = temp;
    }

    private void initView(View view)
    {

        tvPostage = (TextView) view.findViewById(R.id.tv_postage);

        llSendGoodAddress = (LinearLayout) view.findViewById(R.id.llSendGoodAddress);


        llShippingAddress = (LinearLayout) view.findViewById(R.id.llShippingAddress);

        llShippingAddress.setOnClickListener(viewListener);

        ivInvoiceArrow = (ImageView) view.findViewById(R.id.ivInvoiceArrow);
        llRise = (LinearLayout) view.findViewById(R.id.llRise);

        llRise.setOnClickListener(viewListener);

        ivModfyAddress = (ImageView) view.findViewById(R.id.ivModfyAddress);

        ivModfyAddress.setOnClickListener(viewListener);

        tvRiseName = (TextView) view.findViewById(R.id.tvRiseName);


        tvPeopleName = (TextView) view.findViewById(R.id.tvPeopleName);

        tvPeoplePhone = (TextView) view.findViewById(R.id.tvPeoplePhone);

        tvPeopleAddress = (TextView) view.findViewById(R.id.tvPeopleAddress);

    }

    private View.OnClickListener viewListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            switch (v.getId())
            {

                case R.id.llShippingAddress:

                    if (goodsAddressBean == null)
                    {

                        Intent intentAddress = new Intent(context, AddressManageActivity.class);

                        intentAddress.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);

                        if (fragment != null)
                            fragment.startActivityForResult(intentAddress, 1000);
                        else
                            activity.startActivityForResult(intentAddress, 1000);

                        return;
                    }

                    int vist = llSendGoodAddress.getVisibility();

                    if (vist == View.GONE)
                    {

                        llSendGoodAddress.setVisibility(View.VISIBLE);

                        ivInvoiceArrow.setBackgroundResource(R.mipmap.icon_footer_arrow_down);

                    } else
                    {

                        llSendGoodAddress.setVisibility(View.GONE);

                        ivInvoiceArrow.setBackgroundResource(R.mipmap.icon_arrow_right_red);
                    }

                    break;
                case R.id.llRise://抬头

                    Intent intentRise = new Intent(context, DebitRiseManageActivity.class);

                    intentRise.putExtra("flag", DebitRiseManageActivity.GET_RISE_DATA);

                    if (fragment != null)
                        fragment.startActivityForResult(intentRise, 1000);
                    else
                        activity.startActivityForResult(intentRise, 1000);

                    break;
                case R.id.ivModfyAddress://送货地址

                    Intent intentAddress = new Intent(context, AddressManageActivity.class);

                    intentAddress.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);

                    if (fragment != null)
                        fragment.startActivityForResult(intentAddress, 1000);
                    else
                        activity.startActivityForResult(intentAddress, 1000);

                    break;
                default:
                    break;
            }

        }
    };

    public void setRiseName(String content)
    {
        tvRiseName.setText(content);//设置抬头信息
    }

    public void setGoodsAddress(GoodsAddressBean goodsAddressBean)
    {
        this.goodsAddressBean = goodsAddressBean;

        tvPeopleName.setText(goodsAddressBean.contacts);

        tvPeoplePhone.setText(goodsAddressBean.phone);

        tvPeopleAddress.setText(goodsAddressBean.provinceName + " " + goodsAddressBean.cityName + " " + goodsAddressBean.address);
    }

    /**
     * 设置邮费值；
     *
     * @param postage 格式：邮费（全国¥15）
     */
    public void setPostage(String postage)
    {
        tvPostage.setText(postage);
    }

    public GoodsAddressBean getGoodsAddressBean()
    {
        return goodsAddressBean;
    }


    public DebitRiseBean getDebitRiseBean()
    {
        return debitRiseBean;
    }

    public void setDebitRiseBean(DebitRiseBean debitRiseBean)
    {
        this.debitRiseBean = debitRiseBean;
    }

    /**
     * 发送公司抬头请求
     */
    private void sendGetTaitouRequest()
    {

//        new AsyncTask<String, Void, Map<String, String>>()
//        {
//            protected Map<String, String> doInBackground(String... params)
//            {
//                Map<String, Object> param = new HashMap<>();
//                Map<String, String> map = new HashMap<>();
//
//                map.put("sessionId", AppConstant.sessionId);
//                map.put("identification", "1");
//                map.put("serviceName", CardConstant.setting_invoicelist);
//                map.put("data", JSON.toJSONString(param));
//                LogUtil.i("发票抬头列表-提交参数:" + map);
//                return ServiceDispatcher.call(context, map);
//            }
//
//            @Override
//            protected void onPostExecute(Map<String, String> result)
//            {
//                LogUtil.i("发票抬头列表-result:" + result);
//
//                if (null != result && AppConstant.CODE_OK.equals(result.get("code")))
//                {
//                    String value = result.get("data");
//                    if (!TextUtils.isEmpty(value))
//                    {
//
//                        List<DebitRiseBean> list = JSON.parseArray(value, DebitRiseBean.class);
//
//                        for (DebitRiseBean temp : list)
//                        {
//
//                            if (temp.isDefault)
//                            {
//                                debitRiseBean = temp;
//                                setRiseName(temp.content);
//                                break;
//                            }
//
//                        }
//
//                    } else
//                    {
//                        ToastUtil.i(context, context.getString(R.string.app_load_data_error));
//                    }
//                } else
//                {
//
//
//                }
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool());

    }

    /**
     * 收货地址
     */
    private void sendGetLinkManRequest()
    {


//        new AsyncTask<String, Void, Map<String, String>>()
//        {
//            protected Map<String, String> doInBackground(String... params)
//            {
//                Map<String, Object> param = new HashMap<>();
//                Map<String, String> map = new HashMap<>();
//
//                map.put("sessionId", AppConstant.sessionId);
//                map.put("identification", "1");
//                map.put("serviceName", CardConstant.setting_customeraddresslist);
//                map.put("data", JSON.toJSONString(param));
//                LogUtil.i("收货地址列表-提交参数:" + map);
//                return ServiceDispatcher.call(context, map);
//            }
//
//            @Override
//            protected void onPostExecute(Map<String, String> result)
//            {
//                LogUtil.i("收货地址列表-result:" + result);
//
//                if (null != result && AppConstant.CODE_OK.equals(result.get("code")))
//                {
//                    String value = result.get("data");
//                    if (!TextUtils.isEmpty(value))
//                    {
//                        List<GoodsAddressBean> list = JSON.parseArray(value, GoodsAddressBean.class);
//
//                        for (GoodsAddressBean temp : list)
//                        {
//
//                            if (temp.isDefault)
//                            {
//
//                                goodsAddressBean = temp;
//
//                                setGoodsAddress(temp);
//
//                                break;
//
//                            }
//
//                        }
//
//                    }
//                } else
//                {
//                }
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool());

    }


}

