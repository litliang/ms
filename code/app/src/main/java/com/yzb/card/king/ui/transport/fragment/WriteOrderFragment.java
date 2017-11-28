package com.yzb.card.king.ui.transport.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.ui.manage.RentCarLogicManager;
import com.yzb.card.king.ui.transport.activity.PriceEvaluateActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.transport.activity.SelfDriveLogicActivity;
import com.yzb.card.king.ui.discount.bean.BusShopBean;
import com.yzb.card.king.ui.discount.bean.BusTypeBean;
import com.yzb.card.king.ui.discount.bean.BusTypeGradeBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.AppUtils;

import org.xutils.x;

/**
 * 填写订单
 * Created by tarena on 2016/5/27.
 */
    public class WriteOrderFragment extends Fragment
{
    //接口
    private SwitchFragmentCallBack callBack;
    /**
     * 控件
     */
    //车费预估
    private ImageView ivFareEstimateEnd, ivFareEstimateStart, ivBusImage;
    //车型,车牌,公司,供应商,价格,取车城市,取车时间，取车门店,还车城市,还车日期,还车门店
    private TextView tvCarType, tvCardBand, tvCarCompany, tvProvider, tvPrice, tvGetBusCity, tvGetButData, tvGetBusShopName, tvBackBusCity, tvBackBusTime, tvBackBusShopName;
    //订单总价
    private TextView tvTotalPrice;

    private TextView tvGetBusServicePrice;//取车服务费

    private TextView tvBackBusServicePrice;//还车服务费

    private ImageView ivPriceDesc, ivGetBus, ivBackBus;

    private View viewBackBusDesc, viewSendBusDesc;//还车联系人，送车联系人

    private ImageView ivSendBusAddLink, ivBackBusAddLink;

    private EditText etSendBusLinkMan, etSendBusPhone, etSendBusAddress, etBackBusLinkMan, etBackBusPhone, etBackBusAddress;

    private TextView tvSendBusLinekAddress, tvBackBusLinekAddress;

    private ImageView ivCancelRule;//展示取消规则

    private View viewCancelRule;//取消规则内容

    private LinearLayout llCancelRule;//

    private View viewLine;

    private LinearLayout llGetBusPrompt;

    private ImageView ivPrompt;

    private View viewGetBusPrompt;

    private ImageView ivOpen;

    private View viewInvoiceReimbursement;

    private View lineInvoiceReimbursement;

    private EditText etXiadanLinkman, etXiadanPhone;

    private ImageView tvAddLinkman;

    private TextView tvTeHuFuKuan;


    /**
     * 常量
     */
    private String SEND_BUS_FLAG = "SEND_BUS_FLAG";

    private String BACK_BUS_FLAG = "BACK_BUS_FLAG";

    private int ADD_LINKMAN_FLAG;

    /**
     *
     *
     */
    //取车服务标记
    private boolean isGetBusFlag = false;
    //还车服务标记
    private boolean isBackBusFlag = false;


    private InvoiceVoucherView invoiceVoucherView;

    private int logicFlag = SelfDriveLogicActivity.SELF_DRIVE_FLAG;

    private String moneyStr = null;

    public void setCallBack(SwitchFragmentCallBack callBack) {
        this.callBack = callBack;
    }


    //     private  BaiduMapUtil baiduMapUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write_order, null);

        Bundle bundle = getArguments();

        if (bundle != null) {

            logicFlag = bundle.getInt("logic_flag");

        }

        iniView(view);

        initData();


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //  baiduMapUtil.destoryMsearch();
    }

    private void initData() {

        BusTypeBean busTypeBeanTemp = RentCarLogicManager.getInstance().getBusTypeBean();

        x.image().bind(ivBusImage,ServiceDispatcher.getImageUrl(busTypeBeanTemp.imageCode));

        tvCarType.setText(busTypeBeanTemp.carTypeName);

        tvCardBand.setText(busTypeBeanTemp.cardBrand);

        BusShopBean busShopBean = (BusShopBean) RentCarLogicManager.getInstance().getO();

        tvCarCompany.setText(busShopBean.shopName);

        BusTypeGradeBean busTypeGradBean = RentCarLogicManager.getInstance().getBusTypeGradeBeanTemp();

        tvProvider.setText(busTypeGradBean.supplierName);


        tvPrice.setText(getString(R.string.tv_selfdrive_money_unit) + busTypeGradBean.price);


        String place = RentCarLogicManager.getInstance().getPlaceName();
        tvGetBusCity.setText(place);

        long startTime = 0;

        if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG) {

            startTime = RentCarLogicManager.getInstance().getStartCurrentTime();

        } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG) {

            startTime = RentCarLogicManager.getInstance().getDailyRentStartTime();
        }

        tvGetButData.setText(Utils.toData(startTime, 5));

        tvGetBusShopName.setText(busShopBean.storeName);

        tvBackBusCity.setText(place);

        long endTime = 0;

        if (logicFlag == SelfDriveLogicActivity.SELF_DRIVE_FLAG) {

            endTime = RentCarLogicManager.getInstance().getEndCurrentTime();

        } else if (logicFlag == SelfDriveLogicActivity.DAIL_YRENT_FLAG) {

            endTime = startTime + (int) RentCarLogicManager.getInstance().getUseBusDuration() * 24 * 60 * 60 * 1000;
        }

        tvBackBusTime.setText(Utils.toData(endTime, 5));

        tvBackBusShopName.setText(busShopBean.storeName);

        tvTotalPrice.setText(getString(R.string.tv_selfdrive_money_unit) + busTypeGradBean.price);

        moneyStr = busTypeGradBean.price;

        tvSendBusLinekAddress.setText(R.string.tv_selfdrive_send_bus_address);

        tvBackBusLinekAddress.setText(R.string.tv_selfdrive_back_bus_address);

        etSendBusAddress.setHint(R.string.hint_selfdrive_send_bus_address);

        etBackBusAddress.setHint(R.string.hint_selfdrive_back_bus_address);


    }

    private void iniView(View view) {

        ivFareEstimateEnd = (ImageView) view.findViewById(R.id.ivFareEstimateEnd);

        ivFareEstimateStart = (ImageView) view.findViewById(R.id.ivFareEstimateStart);

        ivFareEstimateStart.setOnClickListener(listener);

        ivFareEstimateEnd.setOnClickListener(listener);

        ivBusImage = (ImageView) view.findViewById(R.id.ivBusImage);

        tvCarType = (TextView) view.findViewById(R.id.tvCarType);

        tvCardBand = (TextView) view.findViewById(R.id.tvCardBand);

        tvCarCompany = (TextView) view.findViewById(R.id.tvCarCompany);

        tvProvider = (TextView) view.findViewById(R.id.tvProvider);

        tvPrice = (TextView) view.findViewById(R.id.tvPrice);

        ivPriceDesc = (ImageView) view.findViewById(R.id.ivPriceDesc);

        ivPriceDesc.setOnClickListener(listener);

        tvGetBusCity = (TextView) view.findViewById(R.id.tvGetBusCity);

        tvGetButData = (TextView) view.findViewById(R.id.tvGetButData);

        tvGetBusShopName = (TextView) view.findViewById(R.id.tvGetBusShopName);

        tvBackBusCity = (TextView) view.findViewById(R.id.tvBackBusCity);

        tvBackBusTime = (TextView) view.findViewById(R.id.tvBackBusTime);

        tvBackBusShopName = (TextView) view.findViewById(R.id.tvBackBusShopName);

        tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice);

        ivGetBus = (ImageView) view.findViewById(R.id.ivGetBus);

        ivGetBus.setOnClickListener(listener);

        tvGetBusServicePrice = (TextView) view.findViewById(R.id.tvGetBusServicePrice);

        ivBackBus = (ImageView) view.findViewById(R.id.ivBackBus);

        ivBackBus.setOnClickListener(listener);

        tvBackBusServicePrice = (TextView) view.findViewById(R.id.tvBackBusServicePrice);

        viewBackBusDesc = view.findViewById(R.id.viewBackBusDesc);

        viewSendBusDesc = view.findViewById(R.id.viewSendBusDesc);

        etSendBusLinkMan = (EditText) viewSendBusDesc.findViewById(R.id.etLinkMan);

        etSendBusPhone = (EditText) viewSendBusDesc.findViewById(R.id.etPhonenum);

        etSendBusAddress = (EditText) viewSendBusDesc.findViewById(R.id.etAddress);

        tvSendBusLinekAddress = (TextView) viewSendBusDesc.findViewById(R.id.tvBusAddress);

        ivSendBusAddLink = (ImageView) viewSendBusDesc.findViewById(R.id.ivAddLinkMan);

        etBackBusLinkMan = (EditText) viewBackBusDesc.findViewById(R.id.etLinkMan);

        etBackBusPhone = (EditText) viewBackBusDesc.findViewById(R.id.etPhonenum);

        etBackBusAddress = (EditText) viewBackBusDesc.findViewById(R.id.etAddress);

        tvBackBusLinekAddress = (TextView) viewBackBusDesc.findViewById(R.id.tvBusAddress);

        ivBackBusAddLink = (ImageView) viewBackBusDesc.findViewById(R.id.ivAddLinkMan);

        ivBackBusAddLink.setTag(BACK_BUS_FLAG);

        ivBackBusAddLink.setOnClickListener(listener);

        ivSendBusAddLink.setTag(SEND_BUS_FLAG);

        ivSendBusAddLink.setOnClickListener(listener);

        ivCancelRule = (ImageView) view.findViewById(R.id.ivCancelRule);

        //ivCancelRule.setOnClickListener(listener);

        viewCancelRule = view.findViewById(R.id.viewCancelRule);

        llCancelRule = (LinearLayout) view.findViewById(R.id.llCancelRule);

        llCancelRule.setOnClickListener(listener);

        viewLine = view.findViewById(R.id.viewLine);

        llGetBusPrompt = (LinearLayout) view.findViewById(R.id.llGetBusPrompt);

        llGetBusPrompt.setOnClickListener(listener);

        ivPrompt = (ImageView) view.findViewById(R.id.ivPrompt);

        viewGetBusPrompt = view.findViewById(R.id.viewGetBusPrompt);

        ivOpen = (ImageView) view.findViewById(R.id.ivOpen);

        ivOpen.setOnClickListener(listener);

        viewInvoiceReimbursement = view.findViewById(R.id.viewInvoiceReimbursement);

        invoiceVoucherView = new InvoiceVoucherView(viewInvoiceReimbursement, getActivity(), WriteOrderFragment.this.getActivity());

        lineInvoiceReimbursement = view.findViewById(R.id.lineInvoiceReimbursement);

        etXiadanLinkman = (EditText) view.findViewById(R.id.etXiadanLinkman);

        etXiadanPhone = (EditText) view.findViewById(R.id.etXiadanPhone);
        //处理软键盘
        etXiadanLinkman.setFocusable(false);

        etXiadanPhone.setFocusable(false);

        etHandler.sendEmptyMessageDelayed(0, 1000);

        tvAddLinkman = (ImageView) view.findViewById(R.id.tvAddLinkman);

        tvAddLinkman.setOnClickListener(listener);

        tvTeHuFuKuan = (TextView) view.findViewById(R.id.tvTeHuFuKuan);

        tvTeHuFuKuan.setOnClickListener(listener);


        view.findViewById(R.id.svParent).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                AppUtils.hideSoftWindow(getActivity(), etXiadanLinkman);
                return false;
            }
        });

//         baiduMapUtil = new BaiduMapUtil();
//
//        baiduMapUtil.getGeoCodeByAddress("北京", "海淀区上地十街10号", new BaiduMapUtil.DataCallBack() {
//            @Override
//            public void onGetGeoCodeResult(double lat, double log) {
//
//                Log.e("XY",lat+"-----------------"+log);
//
//            }
//        });

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // callBack.transmitFragmentIndex(3);

            switch (v.getId()) {

                case R.id.ivPriceDesc:

                case R.id.ivFareEstimateEnd:

                case R.id.ivFareEstimateStart:

                    startActivity(new Intent(getActivity(), PriceEvaluateActivity.class));
                    break;
                case R.id.ivGetBus:


                    if (!isGetBusFlag) {

                        isGetBusFlag = true;

                        ivGetBus.setBackgroundResource(R.mipmap.icon_check_red_active);

                        tvGetBusServicePrice.setText("100");

                        viewSendBusDesc.setVisibility(View.VISIBLE);
                    } else {

                        isGetBusFlag = false;

                        ivGetBus.setBackgroundResource(R.mipmap.icon_check_red_defaul);

                        tvGetBusServicePrice.setText("0");

                        viewSendBusDesc.setVisibility(View.GONE);
                    }
                    myHandler.sendEmptyMessage(0);
                    break;
                case R.id.ivBackBus:

                    if (!isBackBusFlag) {

                        isBackBusFlag = true;

                        ivBackBus.setBackgroundResource(R.mipmap.icon_check_red_active);

                        tvBackBusServicePrice.setText("100");

                        viewBackBusDesc.setVisibility(View.VISIBLE);

                    } else {
                        isBackBusFlag = false;

                        ivBackBus.setBackgroundResource(R.mipmap.icon_check_red_defaul);

                        tvBackBusServicePrice.setText("0");

                        viewBackBusDesc.setVisibility(View.GONE);

                    }

                    myHandler.sendEmptyMessage(0);

                    break;
                case R.id.ivAddLinkMan:

                    String flag = (String) v.getTag();

                    if (flag.equals(SEND_BUS_FLAG)) {//添加发送方信息

                        ADD_LINKMAN_FLAG = 2;

                    } else if (flag.equals(BACK_BUS_FLAG)) {//添加接收方信息

                        ADD_LINKMAN_FLAG = 3;
                    }
                    AppUtils.callContact(WriteOrderFragment.this);
                    break;
                case R.id.llCancelRule:

                    int vis = viewCancelRule.getVisibility();

                    if (vis == View.GONE) {

                        viewCancelRule.setVisibility(View.VISIBLE);

                        ivCancelRule.setBackgroundResource(R.mipmap.icon_footer_arrow_down);

                        viewLine.setVisibility(View.GONE);

                    } else {

                        viewCancelRule.setVisibility(View.GONE);

                        viewLine.setVisibility(View.VISIBLE);

                        ivCancelRule.setBackgroundResource(R.mipmap.icon_arrow_right_red);
                    }


                    break;
                case R.id.llGetBusPrompt:

                    int vistPromt = viewGetBusPrompt.getVisibility();

                    if (vistPromt == View.GONE) {

                        viewGetBusPrompt.setVisibility(View.VISIBLE);

                        ivPrompt.setBackgroundResource(R.mipmap.icon_footer_arrow_down);
                    } else {

                        viewGetBusPrompt.setVisibility(View.GONE);

                        ivPrompt.setBackgroundResource(R.mipmap.icon_arrow_right_red);
                    }

                    break;
                case R.id.ivOpen:

                    int visOpen = viewInvoiceReimbursement.getVisibility();

                    if (visOpen == View.GONE) {

                        ivOpen.setBackgroundResource(R.mipmap.icon_checked_active);

                        //  lineInvoiceReimbursement.setVisibility(View.GONE);
                        viewInvoiceReimbursement.setVisibility(View.VISIBLE);
                    } else {
                        ivOpen.setBackgroundResource(R.mipmap.icon_check_default_white);
                        // lineInvoiceReimbursement.setVisibility(View.VISIBLE);
                        viewInvoiceReimbursement.setVisibility(View.GONE);
                    }

                    break;
                case R.id.tvAddLinkman://添加联系人

                    ADD_LINKMAN_FLAG = 1;

                    AppUtils.callContact(WriteOrderFragment.this);

                    break;
                case R.id.tvTeHuFuKuan://付款

                    String etXiadanLinkmanStr = etXiadanLinkman.getText().toString().trim();

                    if (TextUtils.isEmpty(etXiadanLinkmanStr)) {


                        ToastUtil.i(getActivity(), getString(R.string.hint_selfdrive_input_your_name));
                        return;
                    }


                    String etXiadanPhoneStr = etXiadanPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(etXiadanPhoneStr)) {

                        ToastUtil.i(getActivity(), getString(R.string.hint_selfdrive_input_your_phone));
                        return;
                    }
                    int isOpen = viewInvoiceReimbursement.getVisibility();

                    if (isOpen == View.GONE) {//无需送货地址


                    } else {

                        if (invoiceVoucherView.getDebitRiseBean() == null) {

                            ToastUtil.i(getActivity(), getString(R.string.toast_setting_rise));

                            return;
                        }

                        if (invoiceVoucherView.getGoodsAddressBean() == null) {

                            ToastUtil.i(getActivity(), getString(R.string.toast_setting_address));

                            return;
                        }

                    }


                    //TODO 付款完毕，通知相应的界面关闭

//                    Intent itS = new Intent(SelfDriveLogicActivity.CLASS_NAME);
//
//                    itS.putExtra("hasFinish",true);
//
//                    getActivity().sendBroadcast(itS);
//
//                    Intent it= new Intent(SelectMotorcycleTypeActivity.CLASS_NAME);
//
//                    it.putExtra("hasFinish",true);
//
//                    getActivity().sendBroadcast(it);
                    UserBean userBean = UserManager.getInstance().getUserBean();

                    if (userBean == null) {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                        startActivity(intent);

                    } else {

                        BusShopBean busShopBean = (BusShopBean) RentCarLogicManager.getInstance().getO();
                        Intent intent1 = new Intent(getActivity(), DiscountPayActivity.class);
                        intent1.putExtra("storeName", busShopBean.shopName);
                        intent1.putExtra("amount", moneyStr);
                        intent1.putExtra("isInput", false);
                        startActivityForResult(intent1, AppConstant.REQ_CODE);
                    }
                    break;


                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {

            case 1000:

                if (resultCode == 1001) {//抬头信息

                    DebitRiseBean temp = (DebitRiseBean) data.getSerializableExtra("risetBean");

                    invoiceVoucherView.setRiseName(temp.content);

                    invoiceVoucherView.setDebitRiseBean(temp);

                } else if (resultCode == 1002) {//送货地址
                    GoodsAddressBean temp = (GoodsAddressBean) data.getSerializableExtra("addressBeanTemp");


                    invoiceVoucherView.setGoodsAddress(temp);

                    invoiceVoucherView.setGoodsAddressBean(temp);
                }

                break;

            case AppUtils.PICK_CONTACT:
                if (resultCode != Activity.RESULT_OK)
                    return;

                String[] arry = AppUtils.analyContactData(getActivity(), data);
                if (arry.length == 2) {
                    String linkMan = arry[0];
                    String linkManPhone = arry[1];

                    if (TextUtils.isEmpty(linkMan) || TextUtils.isEmpty(linkManPhone)) {

                        return;
                    }

                    if (ADD_LINKMAN_FLAG == 1) {

                        etXiadanLinkman.setText(linkMan);

                        etXiadanPhone.setText(linkManPhone);

                    } else if (ADD_LINKMAN_FLAG == 2) {

                        etSendBusLinkMan.setText(linkMan);

                        etSendBusPhone.setText(linkManPhone);

                    } else if (ADD_LINKMAN_FLAG == 3) {

                        etBackBusLinkMan.setText(linkMan);

                        etBackBusPhone.setText(linkManPhone);

                    }


                }
                break;
            default:
                break;
        }
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int servicePrice = Integer.parseInt(RentCarLogicManager.getInstance().getBusTypeGradeBeanTemp().price);

            if (isGetBusFlag) {

                servicePrice = servicePrice + 100;
            } else {

                servicePrice = servicePrice + 0;
            }

            if (isBackBusFlag) {

                servicePrice = servicePrice + 100;

            } else {

                servicePrice = servicePrice + 0;
            }
            tvTotalPrice.setText(getString(R.string.tv_selfdrive_money_unit) + servicePrice);

            moneyStr = servicePrice + "";

        }
    };

    private Handler etHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            etXiadanPhone.setFocusable(true);
            etXiadanPhone.setFocusableInTouchMode(true);
            etXiadanPhone.requestFocus();//etXiadanPhone

            etXiadanLinkman.setFocusable(true);
            etXiadanLinkman.setFocusableInTouchMode(true);
            etXiadanLinkman.requestFocus();//etXiadanPhone


        }
    };


}
