package com.yzb.card.king.ui.transport.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.activity.PassengerEditActivity;
import com.yzb.card.king.ui.app.activity.ConnectorManageActivity;
import com.yzb.card.king.ui.app.activity.PassengerManageActivity;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.adapter.PlaneCjrAdapter;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.task.GetDefaultConnectorTask;
import com.yzb.card.king.ui.transport.bean.BusAgent;
import com.yzb.card.king.ui.transport.bean.BusTicket;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.ValidatorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汽车票订单页面
 * Created by yinsg on 2016/5/30.
 */
public class BusOrderActivity extends BaseActivity implements View.OnClickListener{

    private static final int SELECT_PASSENGER = 2;
    private static final int ADD_CONNECTOR = 1;
    private static final int PICK_CONTACT = 0;

    private BusTicket busTicket;
    private BusAgent busAgent;
    private View view = null;

    private EditText lxrName = null;
    private EditText lxrMobile = null;

    private RecyclerView ccrListView = null;
    private PlaneCjrAdapter ccrAdapter = null;
    private List<PassengerInfoBean> ccrListData = null;
    private View.OnClickListener deleteCcrListener = null;

    private RelativeLayout orderDetailsLayout = null;
    private ImageView orderDetailsImage = null;
    private PopupWindow orderPopWindow = null;

    private TextView totalAmount = null;

    private TextView tvStartDate;
    private TextView tvStartWeek;
    private TextView tvStartTime;
    private TextView tvStartCity;
    private TextView tvStartStation;
    private TextView tvEndCity;
    private TextView tvEndStation;
    private TextView tvSeatPrice;
    private SlideButton sbInsurance;
    private ImageView addConnector;
    private int amount;
    private TextView tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }
    private void initView() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_bus_order, null, false);
        setContentView(view);

        initBusInfo();

        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
        sbInsurance = (SlideButton) findViewById(R.id.sbInsurance);
        sbInsurance.setToggleState(SlideButton.ToggleState.open);
        sbInsurance.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(SlideButton.ToggleState state) {
                setTotalAmount();
            }
        });

        tvSeatPrice = (TextView) findViewById(R.id.seatPrice);
        // 返回
        this.findViewById(R.id.iv_back).setOnClickListener(this);

        // 乘机人
        this.findViewById(R.id.cjrLayout).setOnClickListener(this);

        deleteCcrListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(1 == ccrListData.size()) {
                    ccrListData.clear();
                    ccrListView.setVisibility(View.GONE);
                    return ;
                }

                String cjrId = (String) v.getTag();
                for(PassengerInfoBean map : ccrListData) {
                    if(cjrId.equals(map.id)) {
                        ccrListData.remove(map);
                        ccrAdapter.notifyDataSetChanged();

//                        setListViewHeight(ccrListView);
                        return ;
                    }
                }
            }
        };

        ccrListView = (RecyclerView) findViewById(R.id.ccrListView);
        ccrListData = new ArrayList<>();
        ccrAdapter = new PlaneCjrAdapter(BusOrderActivity.this, ccrListData, R.layout.plane_cjr_item, deleteCcrListener);
        ccrListView.setAdapter(ccrAdapter);
        ccrAdapter.setOnItemClickListener(new PlaneCjrAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position)
            {
                Intent intent = new Intent(UiUtils.getContext(), PassengerEditActivity.class);
                intent.putExtra(PassengerEditActivity.ARG_DATA_BEAN,ccrListData.get(position));
                startActivity(intent);
            }
        });

        // 联系人
        ImageButton lxrButton = (ImageButton) this.findViewById(R.id.lxrButton);
        lxrButton.setOnClickListener(this);

        addConnector = (ImageView) findViewById(R.id.lxrEditButton);
        addConnector.setOnClickListener(this);
        lxrName = (EditText) this.findViewById(R.id.lxrName);
        lxrMobile = (EditText) this.findViewById(R.id.lxrMobile);
        lxrMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    String mobileValue = lxrMobile.getText().toString().trim();
                    if (StringUtils.isEmpty(mobileValue)) {
                        ToastUtil.i(BusOrderActivity.this, "联系人手机号不能为空");
                        return;
                    }

                    if (!ValidatorUtil.isMobile(mobileValue)) {
                        ToastUtil.i(BusOrderActivity.this, "联系人手机号格式有误");
                        return;
                    }
                }
            }
        });

        orderDetailsLayout = (RelativeLayout) this.findViewById(R.id.orderDetailsLayout);
        orderDetailsLayout.setTag(0);
        orderDetailsLayout.setOnClickListener(this);

        orderDetailsImage = (ImageView) this.findViewById(R.id.orderDetailsImage);

        totalAmount = (TextView) findViewById(R.id.totalAmount);
    }

    private void initBusInfo() {
        tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        tvStartWeek = (TextView) findViewById(R.id.tv_start_week);
        tvStartTime = (TextView) findViewById(R.id.tv_startTime);
        tvStartCity = (TextView) findViewById(R.id.tv_start_city);
        tvStartStation = (TextView) findViewById(R.id.tv_start_station);
        tvEndCity = (TextView) findViewById(R.id.tv_end_city);
        tvEndStation = (TextView) findViewById(R.id.tv_end_station);



    }

    private void initData() {
        busAgent = (BusAgent) getIntent().getSerializableExtra("busAgent");
        busTicket = (BusTicket) getIntent().getSerializableExtra("busTicket");

        tvStartDate.setText(DateUtil.date2String(busTicket.departDate,"MM-dd"));
        tvStartWeek.setText("（"+DateUtil.getDateExplain(busTicket.departDate)+"）");
        tvStartTime.setText(busTicket.departTime);

        tvStartCity.setText(busTicket.startCityName);
        tvEndCity.setText(busTicket.endCityName);
        tvStartStation.setText(busTicket.departStationName);
        tvEndStation.setText(busTicket.reachStationName);

        tvSeatPrice.setText(busAgent.price+"");

        GetDefaultConnectorTask getDefaultConnectorTask = new GetDefaultConnectorTask(new GetDefaultConnectorTask.ConnectorDataCall(){
            @Override
            public void setConnector(Connector connector)
            {
                lxrName.setText(connector.nickName);
                lxrMobile.setText(connector.mobile);
            }
        });
        getDefaultConnectorTask.sendRequest(null);
        setTotalAmount();
    }


    private void setTotalAmount() {
        amount = (int) busAgent.price;
        if(sbInsurance.getState()  == SlideButton.ToggleState.open)
            amount += 30;

        amount = amount * ccrListData.size();
        totalAmount.setText(amount + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.iv_back:
                setResult(AppConstant.RES_BACK);
                finish();
                break;
            case R.id.lxrEditButton:
                addConnector();
                break;
            case R.id.cjrLayout:
                //乘机人选择
                goSelectPassenger();
                break;
            case R.id.lxrButton:
                //联系人选择
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
                break;
            case R.id.orderDetailsLayout:
                //机票订单详情
                int orderTag = (int) orderDetailsLayout.getTag();
                if(0 == orderTag) {
                    orderDetailsLayout.setTag(1);
                    orderDetailsImage.setBackgroundResource(R.mipmap.icon_footer_arrow_down);

                    ViewGroup.LayoutParams params = orderDetailsImage.getLayoutParams();
                    params.width = 40;
                    params.height = 23;
                    orderDetailsImage.setLayoutParams(params);

                    showPopWindow(R.id.orderDetailsLayout);
                } else {
                    orderDetailsLayout.setTag(0);
                    orderDetailsImage.setBackgroundResource(R.mipmap.icon_footer_arrow_right);

                    ViewGroup.LayoutParams params = orderDetailsImage.getLayoutParams();
                    params.width = 23;
                    params.height = 40;
                    orderDetailsImage.setLayoutParams(params);

                    if(null != orderPopWindow && orderPopWindow.isShowing())
                        orderPopWindow.dismiss();
                }
                break;
            case R.id.tvSubmit:
                submit();
                break;
        }
    }
    /**
     *提交订单
     *
     *@author ysg
     *created at 2016/7/12 15:10
     */
    private void submit() {
        if(UserManager.getInstance().isLogin()) {
            Intent intent = new Intent(this, DiscountPayActivity.class);
            intent.putExtra("amount", amount + "");
            intent.putExtra("storeName", busAgent.shopName);
            startActivity(intent);
        }else {
            new GoLoginDailog(this).show();
        }
    }
    /**
     * 添加联系人
     *
     * @author ysg
     * created at 2016/7/8 17:34
     */
    private void addConnector() {
        if(UserManager.getInstance().isLogin()) {
            Intent intent = new Intent(this, ConnectorManageActivity.class);
            intent.putExtra(ConnectorManageActivity.KEY, "TrainOrderActivity");
            startActivityForResult(intent, ADD_CONNECTOR);
        }else {
            new GoLoginDailog(this).show();
        }
    }

    /**
     * 选择乘车人
     *
     *@author ysg
     *created at 2016/7/11 11:00
     */
    private void goSelectPassenger() {
        if(UserManager.getInstance().isLogin()) {
            Intent intent = new Intent(this, PassengerManageActivity.class);
            intent.putExtra(PassengerManageActivity.KEY, this.getLocalClassName());
            startActivityForResult(intent, SELECT_PASSENGER);
        }else {
            new GoLoginDailog(this).show();
        }
    }


    /**
     * 打开泡泡窗口
     * @param id
     */
    private void showPopWindow(int id) {
        switch(id) {
            case R.id.orderDetailsLayout:
                if (orderPopWindow == null) {
                    orderPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    initPopWindow(orderPopWindow, R.layout.popwindow_content_plane_order);
                }
                initOrderViewData();
                orderPopWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
        }
    }

    private void initPopWindow(PopupWindow window, int contentViewId) {
        if (window != null) {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popupwindow_animation_style);
            window.setFocusable(true);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        if (window != null && contentViewId != 0) {
            window.setContentView(getLayoutInflater().inflate(contentViewId, null));
            if(contentViewId == R.layout.popwindow_content_plane_order) {
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        orderDetailsLayout.setTag(0);
                        orderDetailsImage.setBackgroundResource(R.mipmap.icon_footer_arrow_right);

                        ViewGroup.LayoutParams params = orderDetailsImage.getLayoutParams();
                        params.width = 23;
                        params.height = 40;
                        orderDetailsImage.setLayoutParams(params);
                    }
                });
            }
        }
    }

    /**
     * 订单金额列表；
     */
    private void initOrderViewData() {
        View cityContentView = orderPopWindow.getContentView();
        if (cityContentView != null) {
            ListView listView = (ListView) cityContentView.findViewById(R.id.listview);
            //listView.setSelector(getResources().getDrawable(R.drawable.arrow_down_menu_bg_selector));

            List<Map<String, String>> orderListData = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", "成人");
            map.put("details", "票价");
            map.put("price", "￥ " + busAgent.price);
            map.put("count", "x"+ccrListData.size()+"人");
            orderListData.add(map);

            map = new HashMap<String, String>();
            map.put("title", "意外险");
            map.put("details", "");
            map.put("price", "￥ 30");
            map.put("count", "x"+ccrListData.size()+"人");
            orderListData.add(map);

            SimpleAdapter adapter = new SimpleAdapter(this, orderListData, R.layout.popwindow_content_plane_order_item,
                    new String[]{"title", "details", "price", "count"},
                    new int[] {R.id.title, R.id.details, R.id.price, R.id.count});

            listView.setAdapter(adapter);

            int height = setListViewHeight(listView);

            View filterBlack = cityContentView.findViewById(R.id.filterBlack);
            ViewGroup.LayoutParams params = filterBlack.getLayoutParams();
            params.height = Utils.getAreaThree(this)[1] - height - CommonUtil.dip2px(this, 46) - CommonUtil.dip2px(this, 28);
            filterBlack.setLayoutParams(params);

            filterBlack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderDetailsLayout.setTag(0);
                    orderDetailsImage.setBackgroundResource(R.mipmap.icon_footer_arrow_right);

                    ViewGroup.LayoutParams params = orderDetailsImage.getLayoutParams();
                    params.width = 23;
                    params.height = 40;
                    orderDetailsImage.setLayoutParams(params);

                    orderPopWindow.dismiss();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_CONTACT:
                if (resultCode != Activity.RESULT_OK)
                    return;
                Connector connector = new Connector();
                ContactUtil.getConnector(this, data, connector);
                lxrName.setText(connector.nickName);
                lxrMobile.setText(connector.mobile);
                break;
            case ADD_CONNECTOR:
                if (data==null)return;
                Connector connector1 = (Connector) data.getSerializableExtra("connector");
                lxrName.setText(connector1.nickName);
                lxrMobile.setText(connector1.mobile);
                break;
            case SELECT_PASSENGER:
                if(data == null )return;
                PassengerInfoBean passengerInfoBean = (PassengerInfoBean) data.getSerializableExtra("passenger");
                ccrListData.add(passengerInfoBean);
                ccrAdapter.notifyDataSetChanged();
                ccrListView.setVisibility(View.VISIBLE);
//                setListViewHeight(ccrListView);
                setTotalAmount();
                break;
            default:
                break;
        }
    }

    /**
     * 设置listview高度
     * @param list
     */
    private int setListViewHeight(ListView list){
        ListAdapter listAdapter = list.getAdapter();

        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, list);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = list.getLayoutParams();

        params.height = totalHeight + (list.getDividerHeight() * (listAdapter.getCount() - 1));

        list.setLayoutParams(params);

        return params.height;
    }
}
