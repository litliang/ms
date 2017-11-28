package com.yzb.card.king.ui.travel.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.ticket.OrderOutBean;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.bean.travel.DateBean;
import com.yzb.card.king.bean.travel.TravelOrderDetBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.activity.AddressManageActivity;
import com.yzb.card.king.ui.app.activity.ConnectorManageActivity;
import com.yzb.card.king.ui.app.activity.DebitRiseManageActivity;
import com.yzb.card.king.ui.app.activity.PassengerManageActivity;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.app.popup.TravelOrderDetailPopup;
import com.yzb.card.king.ui.app.presenter.AddressListPresenter;
import com.yzb.card.king.ui.app.presenter.DebitRiseListPresenter;
import com.yzb.card.king.ui.app.presenter.PassengerListPresenter;
import com.yzb.card.king.ui.app.view.AddressListView;
import com.yzb.card.king.ui.app.view.PassengerListView;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.travel.activity.view.TravelSignGuestContactView;
import com.yzb.card.king.ui.travel.activity.view.TravelSignupInvoiceView;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.manage.CalendarManager;
import com.yzb.card.king.ui.ticket.presenter.GetPostFeePresenter;
import com.yzb.card.king.ui.ticket.view.GetPostFeeView;
import com.yzb.card.king.ui.travel.presenter.impl.GetConnectorPresenter;
import com.yzb.card.king.ui.travel.presenter.impl.TravelOrderCreatePresenter;
import com.yzb.card.king.ui.travel.view.GetDefaultConnectorView;
import com.yzb.card.king.ui.travel.view.TravelOrderCreateView;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游-->旅游报名
 */
public class TravelSignUpActivity extends BaseActivity implements View.OnClickListener, GetDefaultConnectorView,
        TravelOrderCreateView, AddressListView, SlideButton.OnToggleStateChangeListener, GetPostFeeView, PassengerListView,BaseViewLayerInterface {
    private static final int ADD_CONNECTOR = 1;
    private static final int SELECT_PASSENGER = 2;

    private final static int REQ_GET_DEBITRISE = 3; //请求发票抬头code
    private static final int REQ_GET_ADDRESS = 5;//获取收获地址；

    public static final String ARG_MAP = "ARG_MAP";

    private SlideButton shareSlideButton;  //拼房开关；

    private TextView tvDate;
    private TextView tvWeek;
    private TextView tvAdultPrice;
    private TextView tvChildBedPrice;
    private TextView childNoBedPrice;
    private TextView tvDiffPrice;
    private TextView tvTotallAmount;

    private TextView tvAdultNum;
    private TextView tvChildBedNum;
    private TextView tvChildNoBedNum;

    private TextView tvRoomNum;
    private ImageView ivBottomArrow;

    private HashMap<String, Object> argMap;//接收的参数列表；

    private GetConnectorPresenter connectorPresenter;
    private TravelOrderCreatePresenter orderCreatePresenter;
    private TravelSignGuestContactView guestContactView;
    private TravelSignupInvoiceView invoiceView; //报销凭证view；
    private DateBean dateBean;
    private float adultPerPrice;// 成人单价；
    private float childPerBedPrice;// 儿童占床单价；
    private float childPerNoBedPrice;// 儿童不占床单价；
    private AddressListPresenter addressListPresenter; //收货地址；
    private DebitRiseListPresenter debitRiseListPresenter; //发票抬头；
    private View panelDiffPrice; //拼房view；
    private View panelBottom;
    private PassengerListPresenter passengerPresenter;
    private List<TravelOrderDetBean> orderDetailMap;
    private GetPostFeePresenter postFeePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_signup);
        recvIntentArgs();
        connectorPresenter = new GetConnectorPresenter(this);
        orderCreatePresenter = new TravelOrderCreatePresenter(this);
        postFeePresenter = new GetPostFeePresenter(this);
        passengerPresenter = new PassengerListPresenter(this);
        addressListPresenter = new AddressListPresenter(this);
        debitRiseListPresenter = new DebitRiseListPresenter(this);
        initView();
        calcTotalAmount();
        getDefaultPassenger();
        getConnector(true);
    }

    /**
     * 获取默认旅客
     */
    private void getDefaultPassenger() {
        Map<String, Object> params = new HashMap<>();
        passengerPresenter.loadData(true, params);
    }

    /**
     * 接收Intent参数；
     */
    private void recvIntentArgs() {
        Serializable args = getIntent().getSerializableExtra(ARG_MAP);
        if (args != null && args instanceof HashMap) {
            argMap = (HashMap<String, Object>) args;
        } else {
            argMap = new HashMap<>();
        }
        if (argMap.get("dateBean") != null) {
            dateBean = (DateBean) argMap.get("dateBean");
            adultPerPrice = dateBean.getPrice();
            childPerBedPrice = dateBean.getChildBedPrece();// 儿童占床单价；
            childPerNoBedPrice = dateBean.getChildNobedPrece();// 儿童不占床单价；
        }
        LogUtil.i("接收到的参数列表=" + JSON.toJSONString(argMap));
    }

    public void initView() {
        setHeader(R.mipmap.icon_back_white, getString(R.string.travel_take_activity));
        findViewById(R.id.headerLeft).setOnClickListener(this);
        // 拼房
        shareSlideButton = (SlideButton) findViewById(R.id.shareSlideButton);
        shareSlideButton.setOnToggleStateChangeListener(this);

        guestContactView = (TravelSignGuestContactView) findViewById(R.id.guestContactView);
        guestContactView.setViewClickCall(this);
        invoiceView = (TravelSignupInvoiceView) findViewById(R.id.invoiceView);
        invoiceView.setViewClickCall(this);

        //日期；
        tvDate = (TextView) findViewById(R.id.tv_date);
        //周次；
        tvWeek = (TextView) findViewById(R.id.tv_week);

        String date = String.valueOf(argMap.get("startDate"));
        if (date != null) {
            tvDate.setText(date);
            tvWeek.setText("周" + DateUtil.getWeek(date) + "出发");
        }
        // 成人价格
        tvAdultPrice = (TextView) findViewById(R.id.tvAdultPrice);
        tvAdultPrice.setText("¥" + Utils.subZeroAndDot(adultPerPrice + ""));
        tvAdultNum = (TextView) findViewById(R.id.tvAdultNum);
        //默认1个成人；
        tvAdultNum.setText("1");

        findViewById(R.id.adultCut).setOnClickListener(this);
        findViewById(R.id.adultAdd).setOnClickListener(this);

        // 儿童价格-占床
        tvChildBedPrice = (TextView) findViewById(R.id.tvChildBedPrice);
        tvChildBedPrice.setText("¥" + Utils.subZeroAndDot(childPerBedPrice + ""));

        tvChildBedNum = (TextView) findViewById(R.id.tvChildBedNum);

        findViewById(R.id.childBedCut).setOnClickListener(this);
        findViewById(R.id.childBedAdd).setOnClickListener(this);

        // 儿童价格-不占床
        childNoBedPrice = (TextView) findViewById(R.id.childNoBedPrice);
        childNoBedPrice.setText("¥" + Utils.subZeroAndDot(childPerNoBedPrice + ""));

        tvChildNoBedNum = (TextView) findViewById(R.id.tvChildNoBedNum);

        findViewById(R.id.childNoBedCut).setOnClickListener(this);
        findViewById(R.id.childNoBedAdd).setOnClickListener(this);

        panelBottom = findViewById(R.id.panelBottom);
        // 双人房
        tvRoomNum = (TextView) findViewById(R.id.tvRoomNum);
        tvRoomNum.setText("1");

        panelDiffPrice = findViewById(R.id.panelDiffPrice);
        panelDiffPrice.setVisibility(View.GONE);

        findViewById(R.id.roomCut).setOnClickListener(this);
        findViewById(R.id.roomAdd).setOnClickListener(this);
        // 总价格
        tvTotallAmount = (TextView) findViewById(R.id.allAmount);
        // 拼房差价
        tvDiffPrice = (TextView) findViewById(R.id.tvDiffPrice);

        ivBottomArrow = (ImageView) findViewById(R.id.ivBottomArrow);

        findViewById(R.id.paySubmit).setOnClickListener(this);
        findViewById(R.id.panelFeeDetail).setOnClickListener(this);

        shareSlideButton.setToggleState(SlideButton.ToggleState.close);
    }

    /**
     * @param event_tag  true:获取默认联系人；false：获取特定id的联系人；
     * @param connectors
     */
    @Override
    public void onGetConnectorSucess(boolean event_tag, Object connectors) {
        if (connectors != null) {
            Connector connector;
            List<Connector> connectorList = (List<Connector>) connectors;
            //获取默认联系人；
            if (event_tag) {
                connector = connectorPresenter.getDefaultConnector(connectorList);
            } else //刷新当前已经选择的联系人（用于编辑修改的处理）；
            {
                connector = connectorPresenter.updateConnector(connectorList, guestContactView.getConnector());
            }
            if (connector != null) {
                guestContactView.setConnector(connector);
            }
        }
    }

    @Override
    public void onGetConnectorFail(String failMsg) {
        LogUtil.i("获取默认联系人失败==" + failMsg);
    }

    /**
     * 获取默认联系人；
     */
    private void getConnector(boolean event_tag) {
        if (!event_tag && guestContactView.getConnector() == null) {
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("type", "2");
        connectorPresenter.loadData(event_tag, param);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.travellerAdd:// 添加旅客
                goSelectPassenger();
                break;
            case R.id.contactsAdd:// 添加联系人
                //联系人选择
                getConnector();
                break;
            case R.id.adultCut:// 减成人人数
                cutTvNum(tvAdultNum);
                updateMinMaxRoomNum();
                break;
            case R.id.adultAdd:// 加成人人数
                addTvNum(tvAdultNum);
                updateMinMaxRoomNum();
                break;
            case R.id.childBedCut:// 减占床儿童人数
                cutTvNum(tvChildBedNum);
                updateMinMaxRoomNum();
                break;
            case R.id.childBedAdd:// 加占床儿童人数
                if (verifyChildNum()) {
                    addTvNum(tvChildBedNum);
                    updateMinMaxRoomNum();
                } else {
                    toastCustom("每名成人限带一名儿童");
                }
                break;
            case R.id.childNoBedCut:// 减不占床儿童人数
                cutTvNum(tvChildNoBedNum);
                break;
            case R.id.childNoBedAdd:// 加不占床儿童人数
                if (verifyChildNum()) {
                    addTvNum(tvChildNoBedNum);
                } else {
                    toastCustom("每名成人限带一名儿童");
                }
                break;
            case R.id.roomCut:// 减房间数量
                if (verifyMinRoomNum()) {
                    cutTvNum(tvRoomNum);
                } else {
                    toastCustom("已达最小房间数");
                }
                break;
            case R.id.roomAdd:// 加房间数量
                if (verifyMaxRoomNum()) {
                    addTvNum(tvRoomNum);
                } else {
                    toastCustom("已达最大房间数");
                }
                break;

            case R.id.panelFeeDetail:// 费用明细；
                String curRoomCount = tvRoomNum.getText().toString().trim();
                //判断最小房间数量；
                if (!isEmpty(curRoomCount) && Integer.parseInt(curRoomCount) >= getMinRoomNum()) {
                    showOrderDetailPop();
                } else {
                    toastCustom("请选择正确的房间数");
                }
                break;
            case R.id.paySubmit:// 提交订单
                exeCommit();
                break;
            case R.id.panelRise: //发票抬头
                Intent debitRiseIntent = new Intent(this, DebitRiseManageActivity.class);
                debitRiseIntent.putExtra("flag", DebitRiseManageActivity.GET_RISE_DATA);
                startActivityForResult(debitRiseIntent, REQ_GET_DEBITRISE);
                break;
            case R.id.panelGetAddress: //收获地址
                Intent addressIntent = new Intent(this, AddressManageActivity.class);
                addressIntent.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);
                startActivityForResult(addressIntent, REQ_GET_ADDRESS);
                break;
            case R.id.bxpzSlideButton:  //报销凭证显示时；
                getDefaultDebitRise();
                calcTotalAmount();
                break;
            case R.id.panelAddress:  //配送地址显示时；
                getDefaultAddress();
                break;
        }
    }

    /**
     * 更新最新房间数；
     * 最少房间数：Math.ceil(（大人数+占床儿童数）/2)
     * 最大房间数：大人数量
     */
    private void updateMinMaxRoomNum() {
        int adultNum = Integer.parseInt(tvAdultNum.getText().toString());
        int roomNum = Integer.parseInt(tvRoomNum.getText().toString());
        int minRoomNum = getMinRoomNum();

        //检查边界值；
        if (roomNum < minRoomNum) {
            tvRoomNum.setText(minRoomNum + "");
        } else if (roomNum > adultNum) {
            tvRoomNum.setText(adultNum + "");
        }
    }

    /**
     * 验证最小房间数量；
     *
     * @return true:当前房间数大于最小房间数，房间数可以加1；
     */
    private boolean verifyMinRoomNum() {
        String roomNum = tvRoomNum.getText().toString().trim();
        if (!isEmpty(roomNum)) {
            int minRoomNum = getMinRoomNum();
            int roomNumInt = Integer.parseInt(roomNum);
            return roomNumInt - 1 >= minRoomNum;
        }
        return false;
    }

    /**
     * 验证最大房间数量；
     *
     * @return true:当前房间数大于最小房间数，房间数可以加1；
     */
    private boolean verifyMaxRoomNum() {
        String roomNum = tvRoomNum.getText().toString().trim();
        if (!isEmpty(roomNum)) {
            int maxRoomNum = Integer.parseInt(tvAdultNum.getText().toString());
            int roomNumInt = Integer.parseInt(roomNum);
            roomNumInt = roomNumInt <= 0 ? 0 : roomNumInt;
            return roomNumInt < maxRoomNum;
        }
        return false;
    }

    /**
     * 验证儿童数量；
     *
     * @return true:儿童总数量<成人数量；此时儿童数量可以加1；
     */
    private boolean verifyChildNum() {
        String childBedNum = tvChildBedNum.getText().toString().trim();
        String childNoBedNum = tvChildNoBedNum.getText().toString().trim();
        String adultNum = tvAdultNum.getText().toString().trim();
        if (!isEmpty(childBedNum) && !isEmpty(childNoBedNum)) {
            int bedNum = Integer.parseInt(childBedNum);
            int noBedNum = Integer.parseInt(childNoBedNum);
            int adultNumInt = Integer.parseInt(adultNum);
            return bedNum + noBedNum < adultNumInt;
        }
        return false;
    }

    /**
     * 减数量；
     *
     * @param tv
     */
    private void cutTvNum(TextView tv) {
        if (tv != null) {
            String orinalNum = tv.getText().toString().trim();
            if (isEmpty(orinalNum)) {
                orinalNum = "0";
            } else {
                int orinalNumInt = Integer.parseInt(orinalNum);
                orinalNumInt = orinalNumInt - 1 <= 0 ? 0 : orinalNumInt - 1;
                orinalNum = orinalNumInt + "";
            }
            tv.setText(orinalNum);

            calcTotalAmount();
        }
    }

    /**
     * 加数量；
     *
     * @param tv
     */
    private void addTvNum(TextView tv) {
        if (tv != null) {
            String orinalNum = tv.getText().toString().trim();
            tv.setText(isEmpty(orinalNum) ? "1" : String.valueOf(Integer.parseInt(orinalNum) + 1));
            calcTotalAmount();
        }
    }

    private TravelOrderDetailPopup orderDetailPopup;


    /**
     * 订单详情popwindow；
     */
    private void showOrderDetailPop() {
        List<TravelOrderDetBean> orderDetailData = getOrderDetailMap();
        if (orderDetailData == null || orderDetailData.size() == 0) {
            return;
        }
        if (orderDetailPopup == null) {
            orderDetailPopup = new TravelOrderDetailPopup(this);
            orderDetailPopup.setHeight(Utils.getDisplayHeight(this) - panelBottom.getHeight());
        }
        orderDetailPopup.setData(orderDetailData);
        orderDetailPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                updateFeeDetailArrowState(1);
            }
        });
        orderDetailPopup.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, 0);
        updateFeeDetailArrowState(0);
    }

    /**
     * 获取订单详情pop数据列表；
     */
    private List<TravelOrderDetBean> getOrderDetailMap() {
        if (orderDetailMap == null) {
            orderDetailMap = new ArrayList<>();
        } else {
            orderDetailMap.clear();
        }

        List<TravelOrderDetBean> detBeanList = getTicketPriceMap();
        if (detBeanList.size() > 0) {
            orderDetailMap.addAll(detBeanList);
        }
        //房价；有拼房时才显示=====================
        if (shareSlideButton.getState() == SlideButton.ToggleState.close) {
            int diffPrice = Integer.parseInt(tvDiffPrice.getText().toString().replace("¥", ""));
            if (diffPrice > 0) {
                orderDetailMap.add(getRoomPriceMap());
            }
        }

        if (invoiceView.isChecked() && invoiceView.getPostFeeBean() != null) {
            orderDetailMap.add(getPostFeeMap());
        }
        return orderDetailMap;
    }

    /**
     * 邮费；
     */
    private TravelOrderDetBean getPostFeeMap() {
        if (invoiceView.getPostFeeBean() != null) {
            TravelOrderDetBean detBean = new TravelOrderDetBean();
            detBean.setTitle(getString(R.string.ticke_yjf));
            detBean.setDetails("");
            detBean.setPrice(Utils.subZeroAndDot(invoiceView.getPostFeeBean().getFee() + ""));
            return detBean;
        }
        return null;
    }

    /**
     * 获取房价  拼房价格显示；
     *
     * @return
     */
    private TravelOrderDetBean getRoomPriceMap() {
        int diffPrice = Integer.parseInt(tvDiffPrice.getText().toString().replace("¥", ""));
        TravelOrderDetBean detBean = new TravelOrderDetBean();
        detBean.setTitle("房价");
        detBean.setDetails("不拼房" + " x" + tvRoomNum.getText().toString().toString() + "间");
        detBean.setPrice(Utils.subZeroAndDot(diffPrice + ""));
        return detBean;
    }

    /**
     * 获取房间数
     * 房間數*2-大人人數-佔床兒童
     */
    private int getFlatSharePersonNum() {
        int roomNum = Integer.parseInt(tvRoomNum.getText().toString().toString());
        int adultNum = Integer.parseInt(tvAdultNum.getText().toString().toString());
        int childBedNum = Integer.parseInt(tvChildBedNum.getText().toString().toString());
        return roomNum * 2 - adultNum - childBedNum;
    }

    /**
     * 获取票价map；
     *
     * @return
     */
    private List<TravelOrderDetBean> getTicketPriceMap() {
        int adultNum = Integer.parseInt(tvAdultNum.getText().toString());
        int childBedNum = Integer.parseInt(tvChildBedNum.getText().toString());
        int childNoBedNum = Integer.parseInt(tvChildNoBedNum.getText().toString());

        List<TravelOrderDetBean> detBeanList = new ArrayList<>();
        if (adultNum > 0) {
            TravelOrderDetBean detBean = new TravelOrderDetBean();
            detBean.setTitle("旅游费用");
            detBean.setDetails("成人 x" + adultNum + "人");
            detBean.setPrice(Utils.subZeroAndDot(adultPerPrice * adultNum + ""));
            detBeanList.add(detBean);
        }
        if (childBedNum > 0) {
            TravelOrderDetBean detBean = new TravelOrderDetBean();
            detBean.setTitle(detBeanList.size() == 0 ? "票价" : "");
            detBean.setDetails("儿童 x" + childBedNum + "人");
            detBean.setBedIntro("(占床)");
            detBean.setPrice(Utils.subZeroAndDot(childPerBedPrice * childBedNum + ""));
            detBeanList.add(detBean);
        }
        if (childNoBedNum > 0) {
            TravelOrderDetBean detBean = new TravelOrderDetBean();
            detBean.setTitle(detBeanList.size() == 0 ? "票价" : "");
            detBean.setDetails("儿童 x" + childNoBedNum + "人");
            detBean.setBedIntro("(不占床)");
            detBean.setPrice(Utils.subZeroAndDot(childPerNoBedPrice * childNoBedNum + ""));
            detBeanList.add(detBean);
        }
        return detBeanList;
    }

    /**
     * 更新订单详情pop开闭的指示器；
     *
     * @param flag 0:向上，1：向下；
     */
    private void updateFeeDetailArrowState(int flag) {
        switch (flag) {
            case 0:
                Animation startAnimation = AnimationUtils.loadAnimation(this, R.anim.shuttle_rotate_0_180);
                startAnimation.setDuration(0);
                ivBottomArrow.startAnimation(startAnimation);
                break;
            case 1:
                Animation endAnimation = AnimationUtils.loadAnimation(this, R.anim.shuttle_rotate_180_360);
                endAnimation.setDuration(0);
                ivBottomArrow.startAnimation(endAnimation);
                break;
        }
    }


    @Override
    public void onCreateTravelOrderSucess(boolean event_tag, final OrderOutBean orderData) {
        closePDialog();
        toastCustom("下单成功");

        loadDataHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TravelSignUpActivity.this, DiscountPayActivity.class);
                HashMap<String, String> map = new HashMap();
                String tvTotallAmountStr = tvTotallAmount.getText().toString();
                map.put(DiscountPayActivity.STORE_IDS, String.valueOf(argMap.get("agentId")));//商家id；
                map.put(DiscountPayActivity.STORE_NAME, String.valueOf(argMap.get("storeName")));//商家id；
                map.put(DiscountPayActivity.GOODS_IDS, String.valueOf(argMap.get("productId")));//商品id
                map.put(DiscountPayActivity.ORDER_ID, orderData.getOrderId());//订单id；
                map.put(DiscountPayActivity.NOTIFY_URL, orderData.getNotifyUrl());//
                map.put(DiscountPayActivity.ORDER_NO, orderData.getOrderNo());//订单号；
                map.put(DiscountPayActivity.ORDER_TIME, DateUtil.formatOrderTime(orderData.getOrderTime()));//订单时间；
                map.put(DiscountPayActivity.GOODS_CODES, AppConstant.discount_code_travel);

                map.put(DiscountPayActivity.INDUSTRY_ID, AppConstant.travel_id);//行业id；
                map.put(DiscountPayActivity.ORDER_AMOUNT, tvTotallAmountStr);//订单金额；
                map.put(DiscountPayActivity.IS_INPUT, "0");//0：不需要手输入；1：需要；
                intent.putExtra(DiscountPayActivity.ARG_MAP, map);
                startActivity(intent);
            }
        }, 100);
    }

    @Override
    public void onCreateTravelOrderFail(String failMsg) {
        closePDialog();
        toastCustom(failMsg);
    }

    /**
     * 提交订单；
     */
    private void exeCommit() {
        if (!isInputRight()) {
            return;
        }
        closePDialog();
        showPDialog(getString(R.string.dialog_msg_comitting));

        argMap.put("roomCount", tvRoomNum.getText().toString()); //房间数；
        argMap.put("orderAmount", tvTotallAmount.getText().toString().trim()); //订单金额；
        argMap.put("flatshareStatus", shareSlideButton.getState() == SlideButton.ToggleState.open ? "1" : "0"); //是否拼房；1:是；0：否；
        argMap.put("adultCount", tvAdultNum.getText().toString().trim()); //成人总数量；

        int bedNum = Integer.parseInt(tvChildBedNum.getText().toString());//儿童占床数量；
        int noBedNum = Integer.parseInt(tvChildNoBedNum.getText().toString());//儿童不占床数量；
        argMap.put("childCount", (bedNum + noBedNum) + ""); //儿童总数量；
        argMap.put("childBedCount", bedNum + ""); //儿童占床数量；
        argMap.put("startDate", tvDate.getText().toString().trim()); //出发日期；
        argMap.put("priceId", dateBean != null ? dateBean.getPriceId() : ""); //价格id；

        argMap.put("receipt", invoiceView.isChecked() ? "1" : "0"); //是否需要报销凭证；1：是；0：否；
        //添加联系人；
        argMap.put("contacts", getContact());
        argMap.put("guestList", getPassengerListMap()); //旅客信息；

        //需要报销凭证;
        if (invoiceView.isChecked()) {
            argMap.put("invoiceId", invoiceView.getDebitRiseBean().getInvoiceId() + ""); //发票抬头id；
            argMap.put("express", getExpress()); //配送信息
        }
        argMap.remove("dateBean");
        orderCreatePresenter.loadData(true, argMap);
    }

    /**
     * 获取旅客信息列表
     *
     * @return
     */
    private List<Map<String, String>> getPassengerListMap() {
        List<Map<String, String>> argMap = new ArrayList<>();
        Map<String, String> map;

        List<PassengerInfoBean> guests = guestContactView.getPassengers();
        if (guests != null) {
            PassengerInfoBean pib;
            for (int i = 0; i < guests.size(); i++) {
                pib = guests.get(i);
                map = new HashMap<>();
                map.put("name", pib.getName());
                map.put("mobile", pib.getMobile());
                map.put("engSurname", pib.getEngSurname());
                map.put("engName", pib.getEngName());
                map.put("certType", pib.getCertType());
                map.put("certNo", pib.getCertNo());
                argMap.add(map);
            }
        }
        return argMap;
    }

    /**
     * 获取配送信息；
     *
     * @return
     */
    private Map<String, String> getExpress() {
        GoodsAddressBean gb = invoiceView.getAddressBean();
        if (gb != null) {
            Map<String, String> address = new HashMap<>();
            address.put("name", gb.contacts);
            address.put("ophone", gb.phone);
            address.put("addRess", gb.address);
            address.put("distributionFee", invoiceView.getPostFeeBean().getFee() + "");//邮费
        }
        return null;
    }

    /**
     * 获取联系人；
     *
     * @return
     */
    private Map<String, Object> getContact() {
        Map<String, Object> contact = new HashMap<>();
        Connector connector = guestContactView.getConnector();

        //contact.put("contactId", connector.getId()); //联系人id；
        contact.put("contactName", connector.getNickName()); //联系人name；
        contact.put("contactMobile", connector.getMobile()); //联系人电话；
        contact.put("contactEmail", ""); //联系人email
        return contact;
    }

    /**
     * 判断输入的合法性；
     */
    private boolean isInputRight() {
        List<PassengerInfoBean> guests = guestContactView.getPassengers();
        if (guests == null || guests.size() == 0) {
            toastCustom("请添加旅客信息");
            return false;
        }

        // 判断人数和旅客信息人数是否一致
        if (guests != null && guests.size() != getAllPersonNum()) {
            toastCustom("旅客信息与人数不一致");
            return false;
        }
        String tvTotallAmountStr = tvTotallAmount.getText().toString();
        if (new BigDecimal(tvTotallAmountStr).compareTo(new BigDecimal(0)) == 0) {
            toastCustom("金额不能为零");
            return false;
        }
        if (guestContactView.getConnector() == null) {
            toastCustom("请选择联系人");
            return false;
        }

        //开启时验证；
        if (invoiceView.isChecked()) {
            if (invoiceView.getPostFeeBean() == null) {
                toastCustom(R.string.select_post_fee);
                return false;
            }
            if (invoiceView.getDebitRiseBean() == null) {
                toastCustom(R.string.select_debitrise);
                return false;
            }
            if (invoiceView.getAddressBean() == null) {
                toastCustom(R.string.select_address);
                return false;
            }
        }

        return true;
    }

    /**
     * 添加联系人
     *
     * @author ysg
     * created at 2016/7/8 17:34
     */
    private void getConnector() {
        if (isLogin()) {
            Intent intent = new Intent(this, ConnectorManageActivity.class);
            intent.putExtra(ConnectorManageActivity.KEY, "TrainOrderActivity");
            startActivityForResult(intent, ADD_CONNECTOR);
        } else {
            new GoLoginDailog(this).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新选中的联系人；
        if (guestContactView.getConnector() == null) {
            getConnector(false);
        }
        Date date = CalendarManager.getInstance().getDate();
        if (CalendarManager.getInstance().getDate() != null) {
            LogUtil.i("返回的date==" + date);
            String dateStr = DateUtil.date2String(date, DateUtil.DATE_FORMAT_DATE);
            String week = DateUtil.getWeek(dateStr);
            tvDate.setText(dateStr);
            tvWeek.setText("周" + week + "出发");
            CalendarManager.getInstance().clearData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        switch (requestCode) {
            case REQ_GET_DEBITRISE:  //选择发票抬头
                Serializable debitObj = data.getSerializableExtra("risetBean");
                if (debitObj != null) {
                    invoiceView.setDebitRiseBean((DebitRiseBean) debitObj);
                }
                break;
            case REQ_GET_ADDRESS:  //选择收货地址
                Serializable addressObj = data.getSerializableExtra("addressBeanTemp");
                if (addressObj != null) {
                    GoodsAddressBean addressBean = (GoodsAddressBean) addressObj;
                    invoiceView.setGoodsAddress(addressBean);
                    loadPostageFee(addressBean.getCityId());
                }
                break;
            case ADD_CONNECTOR: //添加联系人；
                Serializable connectObj = data.getSerializableExtra("connector");
                if (connectObj != null) {
                    Connector connector = (Connector) connectObj;
                    guestContactView.setConnector(connector);
                }
                break;
            case SELECT_PASSENGER://添加旅客信息；
                Serializable pibObj = data.getSerializableExtra("passenger");
                if (pibObj != null) {
                    PassengerInfoBean pib = (PassengerInfoBean) pibObj;
                    if (guestContactView.judgeSame(pib)) {
                        toastCustom("不能添加相同旅客");
                        return;
                    }
                    if (isSumNumRight(pib.getPersonTypeEn())) {
                        guestContactView.addGuest(pib);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 判断某一个乘客类型人数是否已达规定人数上限；
     *
     * @param personType 乘客类型
     * @return true:未达上限，可继续添加；false：已到规定的最大人数；
     */
    private boolean isSumNumRight(String personType) {
        //不能是婴儿；
        if (AppConstant.TYPE_BABY.equals(personType)) {
            // toastCustom("不可添加婴儿");
            return false;
        }
        int personSum = getPersonTypeNum(personType);
        switch (personType) {
            case AppConstant.TYPE_ADULT:
                if (personSum >= Integer.parseInt(tvAdultNum.getText().toString())) {
                    // toastCustom(R.string.ticket_nomore_adult);
                    return false;
                }
                break;
            case AppConstant.TYPE_CHILD:
                if (personSum >= Integer.parseInt(tvChildBedNum.getText().toString()) +
                        Integer.parseInt(tvChildNoBedNum.getText().toString())) {
                    // toastCustom(R.string.ticket_nomore_child);
                    return false;
                }
                break;
        }
        return true;
    }


    /**
     * 获取某一类型乘客的数量；
     */
    private int getPersonTypeNum(String personType) {
        int personSum = 0;
        List<PassengerInfoBean> passengers = guestContactView.getPassengers();
        if (passengers != null) {
            for (int i = 0; i < passengers.size(); i++) {
                //类型一致；
                if (personType.equals(passengers.get(i).getPersonTypeEn())) {
                    personSum++;
                }
            }
        }
        return personSum;
    }

    /**
     * 选择乘车人
     *
     * @author ysg
     * created at 2016/7/11 11:00
     */
    private void goSelectPassenger() {
        if (isLogin()) {
            Intent intent = new Intent(this, PassengerManageActivity.class);
            intent.putExtra(PassengerManageActivity.KEY, this.getLocalClassName());
            startActivityForResult(intent, SELECT_PASSENGER);
        } else {
            new GoLoginDailog(this).show();
        }
    }

    // 获取总人数
    private int getAllPersonNum() {
        int adultNumInt = Integer.parseInt(tvAdultNum.getText().toString());
        int childBedNumInt = Integer.parseInt(tvChildBedNum.getText().toString());
        int childNoBedNumInt = Integer.parseInt(tvChildNoBedNum.getText().toString());
        return adultNumInt + childBedNumInt + childNoBedNumInt;
    }

    /**
     * 获取最小房间数
     * 算法：Math.ceil(（大人数+占床儿童数）/2)
     */
    private int getMinRoomNum() {
        int adultNum = Integer.parseInt(tvAdultNum.getText().toString());
        int childBedNum = Integer.parseInt(tvChildBedNum.getText().toString());
        return (int) Math.ceil((childBedNum + adultNum) / 2.0f);
    }

    /**
     * 计算全部价格
     */
    private void calcTotalAmount() {
        float totalAmount = 0;
        // 成人价格
        int adultNumInt = Integer.parseInt(tvAdultNum.getText().toString());
        totalAmount += adultPerPrice * adultNumInt;
        // 儿童占床价格
        int childBedNumInt = Integer.parseInt((tvChildBedNum.getText().toString()));
        totalAmount += childPerBedPrice * childBedNumInt;
        // 儿童不占床价格
        int childNoBedNumInt = Integer.parseInt((tvChildNoBedNum.getText().toString()));
        totalAmount += childPerNoBedPrice * childNoBedNumInt;

        boolean hasShareFunction = hasShareFunction();

        // true:无拼房功能;  false:有拼房功能
        panelDiffPrice.setVisibility(hasShareFunction ? View.GONE : View.VISIBLE);
        //判断是否有拼房功能；
        if (!hasShareFunction) {
            //计算差价；
            float diffPrice = calcRoomDiffPrice();
            tvDiffPrice.setText("¥" + Utils.subZeroAndDot(diffPrice + ""));

            //不拼房补差价，拼房不需要补差价
            if (shareSlideButton.getState() == SlideButton.ToggleState.close) {
                totalAmount += diffPrice;
            }
        }
        //是否报销凭证
        if (invoiceView.isChecked() && invoiceView.getPostFeeBean() != null) {
            totalAmount += invoiceView.getPostFeeBean().getFee();
        }
        tvTotallAmount.setText(Utils.subZeroAndDot(totalAmount + ""));
    }

    /**
     * 判断是否有拼房功能；
     * 算法：if （大人数+占床儿童数）/2＝房间数) then 无拼房功能 else 有拼房功能
     *
     * @return true:无拼房功能;  false:有拼房功能
     */
    private boolean hasShareFunction() {
        int adultNum = Integer.parseInt(tvAdultNum.getText().toString().trim());
        int childBedNum = Integer.parseInt(tvChildBedNum.getText().toString().trim());
        int roomNum = Integer.parseInt(tvRoomNum.getText().toString().trim());
        return (adultNum + childBedNum) / 2 == roomNum;
    }

    /**
     * 计算房间差价
     * 差价公式：（房间数*2-大人人数-佔床儿童）*后台房价价差金额
     *
     * @return
     */
    private float calcRoomDiffPrice() {
        // 房间数
        int roomNumInt = Integer.parseInt(tvRoomNum.getText().toString().trim());
        int adultNum = Integer.parseInt(tvAdultNum.getText().toString().trim());
        int childBedNum = Integer.parseInt(tvChildBedNum.getText().toString().trim());
        //后台房价价差金额;
        float diffePrice = dateBean != null ? dateBean.getFlatsharePrice() : 0;
        LogUtil.i("roomNumInt=" + roomNumInt + ",adultNum=" + adultNum + ",childBedNum=" + childBedNum);
        //房间差价
        float roomDifferPrice = (roomNumInt * 2 - adultNum - childBedNum) * diffePrice;
        LogUtil.i("拼房差价=" + roomDifferPrice);
        return roomDifferPrice < 0 ? 0 : roomDifferPrice;
    }

    /**
     * 获取默认收获地址；
     */
    private void getDefaultAddress() {
        if (invoiceView.getAddressBean() == null && invoiceView.isChecked()) {
            Map<String, Object> param = new HashMap<>();
            addressListPresenter.loadData(true, param);
        }
    }

    /**
     * 获取默认收货地址；
     */
    @Override
    public void onLoadAddressListSucess(boolean event_flag, Object data) {
        GoodsAddressBean addressBean = addressListPresenter.getDefaultAddress(data);
        if (addressBean != null) {
            invoiceView.setGoodsAddress(addressBean);
            loadPostageFee(addressBean.getCityId());
        }
    }

    /**
     * 获取邮费信息；
     *
     * @cityId 目的地的cityId；
     */
    private void loadPostageFee(String cityId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", cityId);
        params.put("pageStart", "0");
        params.put("pageSize", "1");
        postFeePresenter.loadData(params);
    }

    @Override
    public void onLoadAddressListFail(String failMsg) {
        LogUtil.i("获取收获地址失败-failMsg=" + failMsg);
    }

    /**
     * 获取默认发票抬头；
     */
    private void getDefaultDebitRise() {
        if (invoiceView.getDebitRiseBean() == null && invoiceView.isChecked()) {
            Map<String, Object> param = new HashMap<>();
            debitRiseListPresenter.loadData(true, param);
        }
    }

    @Override
    public void onToggleStateChange(SlideButton.ToggleState state) {
        int diffPrice = Integer.parseInt(tvDiffPrice.getText().toString().replace("¥", ""));
        if (diffPrice > 0) {
            calcTotalAmount();
        }
    }

    @Override
    public void onGetPostFeeSucess(Object data) {
        if (data != null) {
            List<PostFeeBean> postFeeBeans = (List<PostFeeBean>) data;
            if (postFeeBeans != null && postFeeBeans.size() > 0) {
                invoiceView.setPostage(postFeeBeans.get(0));
                calcTotalAmount();
            }
        }
    }

    @Override
    public void onGetPostFeeFail(String failMsg) {
        LogUtil.i("获取邮费失败-failMsg=" + failMsg);
    }

    @Override
    public void onLoadPassengersSucess(boolean event_flag, Object data) {
        PassengerInfoBean pib = passengerPresenter.getDefaultPassenger((List<PassengerInfoBean>) data);
        if (pib != null && isSumNumRight(pib.getPersonTypeEn())) {
            guestContactView.addGuest(pib);
        }
    }

    @Override
    public void onLoadPassengersFail(String failMsg) {
        LogUtil.i("failMsg=" + failMsg);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        DebitRiseBean debitRiseBean = debitRiseListPresenter.getDefaultDebitRise(o);
        if (debitRiseBean != null) {
            invoiceView.setDebitRiseBean(debitRiseBean);
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        LogUtil.i(o+"");
    }
}
