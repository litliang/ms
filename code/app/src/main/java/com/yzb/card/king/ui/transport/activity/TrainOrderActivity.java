package com.yzb.card.king.ui.transport.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.yzb.card.king.ui.other.activity.WebViewClientActivity;
import com.yzb.card.king.ui.transport.presenter.impl.TrainOrderPresenter;
import com.yzb.card.king.ui.transport.presenter.impl.TrainOrderPresenterImpl;
import com.yzb.card.king.ui.transport.view.TrainOrderView;
import com.yzb.card.king.ui.travel.presenter.impl.GetConnectorPresenter;
import com.yzb.card.king.ui.travel.view.GetDefaultConnectorView;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.DateUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;
import com.yzb.card.king.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 火车票提交订单页面；
 * 第一次修改  gengqiyun  2016.9.7
 */
public class TrainOrderActivity extends BaseActivity implements View.OnClickListener, TrainOrderView,
        GetDefaultConnectorView
{
    private static final int PICK_CONTACT = 0;
    private static final int ADD_CONNECTOR = 1;
    private static final int SELECT_PASSENGER = 2;

    private Map trainInfo = null;
    private View view = null;

    private EditText lxrName = null;  //联系人姓名；
    private EditText lxrMobile = null;//联系人手机号；

    private RecyclerView ccrListView = null;
    private PlaneCjrAdapter ccrAdapter = null;
    private List<PassengerInfoBean> ccrListData = null;
    private RelativeLayout orderDetailsLayout = null;
    private ImageView orderDetailsImage = null;
    private PopupWindow orderPopWindow = null;

    private TextView totalAmount = null;

    private SlideButton slideRecvNoSeat; //接受无座；
    private SlideButton slideInsurance;  //意外险；
    private int amount;

    private Map supplier;
    private TrainOrderPresenter trainOrderPresenter;
    private GetConnectorPresenter getConnectorPresenter; //获取默认联系人；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();

        trainOrderPresenter = new TrainOrderPresenterImpl(this);
        getConnectorPresenter = new GetConnectorPresenter(this);
        initData();
    }

    private void initView()
    {
        view = LayoutInflater.from(this).inflate(R.layout.activity_train_order, null);
        setContentView(view);
        trainInfo = JSON.parseObject(getIntent().getStringExtra("trainInfo"), Map.class);
        supplier = JSON.parseObject(getIntent().getStringExtra("supplier"), Map.class);
        initTrainInfo();

        findViewById(R.id.tvSubmit).setOnClickListener(this);

        slideRecvNoSeat = (SlideButton) findViewById(R.id.recvNoSeat);
        slideRecvNoSeat.setToggleState(SlideButton.ToggleState.open);
        slideRecvNoSeat.setOnToggleStateChangeListener(slideRecvNoSeatListener);

        slideInsurance = (SlideButton) findViewById(R.id.sbInsurance);
        slideInsurance.setToggleState(SlideButton.ToggleState.open);
        slideInsurance.setOnToggleStateChangeListener(slideAccidentInsuranceListener);

        this.findViewById(R.id.yuDindXuZhi).setOnClickListener(this);
        this.findViewById(R.id.iv_back).setOnClickListener(this);
        this.findViewById(R.id.seatLayout).setOnClickListener(this);
        this.findViewById(R.id.cjrLayout).setOnClickListener(this);

        findViewById(R.id.lxrEditButton).setOnClickListener(this);

        ccrListView = (RecyclerView) findViewById(R.id.ccrListView);
        ccrListData = new ArrayList<>();
        ccrAdapter = new PlaneCjrAdapter(TrainOrderActivity.this, ccrListData, R.layout.plane_cjr_item, deleteCcrListener);
        ccrListView.setAdapter(ccrAdapter);
        ccrAdapter.setOnItemClickListener(planeCjrAdapterClickListener);

        findViewById(R.id.lxrButton).setOnClickListener(this);

        lxrName = (EditText) this.findViewById(R.id.lxrName);
        lxrMobile = (EditText) this.findViewById(R.id.lxrMobile);

        findViewById(R.id.insuranceLayout).setOnClickListener(this);

        orderDetailsLayout = (RelativeLayout) findViewById(R.id.orderDetailsLayout);
        orderDetailsLayout.setTag(0);
        orderDetailsLayout.setOnClickListener(this);

        orderDetailsImage = (ImageView) this.findViewById(R.id.orderDetailsImage);
        totalAmount = (TextView) findViewById(R.id.totalAmount);
    }

    /**
     * 接受无座
     */
    private SlideButton.OnToggleStateChangeListener slideRecvNoSeatListener = new SlideButton.OnToggleStateChangeListener()
    {
        @Override
        public void onToggleStateChange(SlideButton.ToggleState state)
        {

        }
    };
    /**
     * 意外险
     */
    private SlideButton.OnToggleStateChangeListener slideAccidentInsuranceListener = new SlideButton.OnToggleStateChangeListener()
    {
        @Override
        public void onToggleStateChange(SlideButton.ToggleState state)
        {
            setTotalAmount();
        }
    };

    /**
     * 删除监听；
     */
    private View.OnClickListener deleteCcrListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (1 == ccrListData.size())
            {
                ccrListData.clear();
                ccrListView.setVisibility(View.GONE);
                return;
            }

            String cjrId = (String) v.getTag();
            for (PassengerInfoBean map : ccrListData)
            {
                if (cjrId.equals(map.id))
                {
                    ccrListData.remove(map);
                    ccrAdapter.notifyDataSetChanged();
                    setTotalAmount();
                    return;
                }
            }
        }
    };

    private PlaneCjrAdapter.OnItemClickListener planeCjrAdapterClickListener = new PlaneCjrAdapter.OnItemClickListener()
    {

        @Override
        public void setOnItemClickListener(int position)
        {
            Intent intent = new Intent(UiUtils.getContext(), PassengerEditActivity.class);
            intent.putExtra(PassengerEditActivity.ARG_DATA_BEAN, ccrListData.get(position));
            startActivity(intent);
        }
    };


    private Connector selectConnector; //选择的联系人；

    @Override
    public void onCreateOrderSucess(Object data)
    {
    }

    @Override
    public void onCreateOrderFail(String failMsg)
    {

    }

    /**
     * 获取联系人成功；
     *
     * @param event_tag  true:获取联系人成功;false:获取所有联系人成功；
     * @param connectors
     */
    @Override
    public void onGetConnectorSucess(boolean event_tag, Object connectors)
    {
        LogUtil.i("获取默认联系人成功==" + connectors);
        if (connectors == null)
        {
            return;
        }
        List<Connector> connectorList = (List<Connector>) connectors;
        Connector connector;
        for (int i = 0; i < connectorList.size(); i++)
        {
            connector = connectorList.get(i);
            //默认联系人；
            if ((event_tag && connector.isDefault))
            {
                selectConnector = connector;
                break;
            } else if (selectConnector != null && !isEmpty(connector.getId())
                    && connector.getId().equals(selectConnector.getId()))//选中的联系人；
            {
                selectConnector = connector;
                break;
            }
        }
        if (selectConnector != null)
        {
            LogUtil.i("selectConnector==" + JSON.toJSONString(selectConnector));
            lxrName.setText(selectConnector.nickName);
            lxrMobile.setText(selectConnector.mobile);
        }
    }

    @Override
    public void onGetConnectorFail(String failMsg)
    {
        LogUtil.i("获取默认联系人失败==" + failMsg);
    }

    private void initData()
    {
        getConnector(true);
        setTotalAmount();
    }

    /**
     * 获取联系人；
     *
     * @param event_flag true：获取默认联系人；false：获取所有联系人；
     */
    private void getConnector(boolean event_flag)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("type", "2");
        getConnectorPresenter.loadData(event_flag, param);
    }

    private Handler mainHandler = new Handler();

    @Override
    protected void onResume()
    {
        super.onResume();
        //选择的是通讯录联系人；
        if (selectConnector != null && isEmpty(selectConnector.getId()))
        {
            return;
        }

        mainHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //刷新联系人；
                getConnector(false);
            }
        }, 1000);
    }

    private void initTrainInfo()
    {
        TextView startTrainName = (TextView) findViewById(R.id.startTrainName);
        TextView timeLength = (TextView) findViewById(R.id.timeLength);
        TextView endTrainName = (TextView) findViewById(R.id.endTrainName);

        TextView startTime = (TextView) findViewById(R.id.startTime);
        TextView trainNumber = (TextView) findViewById(R.id.trainNumber);
        TextView endTime = (TextView) findViewById(R.id.endTime);

        TextView startDate = (TextView) findViewById(R.id.startDate);
        TextView endDate = (TextView) findViewById(R.id.endDate);

        startTrainName.setText(String.valueOf(trainInfo.get("startTrainName")));
        timeLength.setText(String.valueOf(trainInfo.get("timeLength")));
        endTrainName.setText(String.valueOf(trainInfo.get("endTrainName")));

        startTime.setText(String.valueOf(trainInfo.get("startTime")).substring(11, 16));
        trainNumber.setText(String.valueOf(trainInfo.get("trainNumber")));
        endTime.setText(String.valueOf(trainInfo.get("endTime")).substring(11, 16));

        startDate.setText(DateUtil.getDate(DateUtil.string2Date(String.valueOf(trainInfo.get("startTime"))), 0));

        findViewById(R.id.llStopPosition).setOnClickListener(this);
        endDate.setText(DateUtil.getDate(DateUtil.string2Date(String.valueOf(trainInfo.get("endTime"))), 0));

        TextView seatPrice = (TextView) findViewById(R.id.seatPrice);
        seatPrice.setText(String.valueOf(trainInfo.get("price")));

        TextView seatType = (TextView) findViewById(R.id.tv_seat_type);
        seatType.setText(String.valueOf(trainInfo.get("seatType")));
    }

    /**
     * 计算总金额；
     */
    private void setTotalAmount()
    {
        amount = Integer.parseInt(String.valueOf(trainInfo.get("price")));
        if (slideInsurance.getState() == SlideButton.ToggleState.open)
        {
            amount += 30;
        }
        amount = amount * ccrListData.size();
        totalAmount.setText(amount + "");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.llStopPosition: //车次详情列表
                Intent trainInfoIntent = new Intent(TrainOrderActivity.this, TrainTimetableActivity.class);
                trainInfoIntent.putExtra("trainInfo", JSON.toJSONString(trainInfo));
                startActivity(trainInfoIntent);
                break;
            case R.id.iv_back:
                setResult(AppConstant.RES_BACK);
                finish();
                break;
            case R.id.yuDindXuZhi://预定须知
                goWebViewClient();
                break;
            case R.id.cjrLayout:
                goSelectPassenger();
                //乘机人选择

                break;
            case R.id.lxrEditButton://添加联系人
                addConnector();
                break;
            case R.id.lxrButton://联系人选择
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
                break;

            case R.id.orderDetailsLayout://机票订单详情
                int orderTag = (int) orderDetailsLayout.getTag();
                if (0 == orderTag)
                {
                    orderDetailsLayout.setTag(1);
                    orderDetailsImage.setBackgroundResource(R.mipmap.icon_footer_arrow_down);
                    ViewGroup.LayoutParams params = orderDetailsImage.getLayoutParams();
                    params.width = 40;
                    params.height = 23;
                    orderDetailsImage.setLayoutParams(params);
                    showPopWindow(R.id.orderDetailsLayout);
                } else
                {
                    orderDetailsLayout.setTag(0);
                    orderDetailsImage.setBackgroundResource(R.mipmap.icon_footer_arrow_right);
                    ViewGroup.LayoutParams params = orderDetailsImage.getLayoutParams();
                    params.width = 23;
                    params.height = 40;
                    orderDetailsImage.setLayoutParams(params);
                    if (null != orderPopWindow && orderPopWindow.isShowing())
                        orderPopWindow.dismiss();
                }
                break;
            case R.id.tvSubmit:
                submit();
                break;
        }
    }

    /**
     * 预定须知
     *
     * @author ysg
     * created at 2016/7/20 10:50
     */
    private void goWebViewClient()
    {
        Bundle bundle = new Bundle();
        bundle.putString("category", "1");
        bundle.putString("titleName", getString(R.string.transport_book_need_know));
        readyGoWithBundle(this, WebViewClientActivity.class, bundle);
    }

    /**
     * 提交订单
     *
     * @author ysg
     * created at 2016/7/12 15:10
     */
    private void submit()
    {
        if (UserManager.getInstance().isLogin())
        {
            //此处添加入参；
            Map<String, Object> params = new HashMap<>();
            trainOrderPresenter.loadData(params);

            Intent intent = new Intent(this, DiscountPayActivity.class);
            intent.putExtra("amount", amount + "");
            intent.putExtra("storeName", String.valueOf(supplier.get("supplierName")));
            startActivity(intent);
        } else
        {
            new GoLoginDailog(this).show();
        }
    }

    /**
     * 选择乘车人
     *
     * @author ysg
     * created at 2016/7/11 11:00
     */
    private void goSelectPassenger()
    {
        if (UserManager.getInstance().isLogin())
        {
            Intent intent = new Intent(this, PassengerManageActivity.class);
            intent.putExtra(PassengerManageActivity.KEY, this.getLocalClassName());
            startActivityForResult(intent, SELECT_PASSENGER);
        } else
        {
            new GoLoginDailog(this).show();
        }
    }

    /**
     * 添加联系人
     *
     * @author ysg
     * created at 2016/7/8 17:34
     */
    private void addConnector()
    {
        if (UserManager.getInstance().isLogin())
        {
            Intent intent = new Intent(this, ConnectorManageActivity.class);
            intent.putExtra(ConnectorManageActivity.KEY, "TrainOrderActivity");
            startActivityForResult(intent, ADD_CONNECTOR);
        } else
        {
            new GoLoginDailog(this).show();
        }
    }

    /**
     * 打开泡泡窗口
     *
     * @param id
     */
    private void showPopWindow(int id)
    {
        switch (id)
        {
            case R.id.orderDetailsLayout:
                if (orderPopWindow == null)
                {
                    orderPopWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                            Utils.getDisplayHeight(this) - orderDetailsLayout.getHeight());
                    initPopWindow(orderPopWindow, R.layout.popwindow_content_plane_order);
                }
                initOrderViewData();
                orderPopWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                break;
        }
    }

    private void initPopWindow(PopupWindow window, int contentViewId)
    {
        if (window != null)
        {
            window.setOutsideTouchable(true);
            window.setAnimationStyle(R.style.popupwindow_animation_style);
            window.setFocusable(true);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        if (window != null && contentViewId != 0)
        {
            window.setContentView(getLayoutInflater().inflate(contentViewId, null));
            if (contentViewId == R.layout.popwindow_content_plane_order)
            {
                window.setOnDismissListener(new PopupWindow.OnDismissListener()
                {
                    @Override
                    public void onDismiss()
                    {
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
    private void initOrderViewData()
    {
        View cityContentView = orderPopWindow.getContentView();
        if (cityContentView != null)
        {
            ListView listView = (ListView) cityContentView.findViewById(R.id.listview);
            List<Map<String, String>> orderListData = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", "成人");
            map.put("details", "票价");
            map.put("price", "¥ " + String.valueOf(trainInfo.get("price")));
            map.put("count", "x" + ccrListData.size() + "人");
            orderListData.add(map);

            if (slideInsurance.getState() == SlideButton.ToggleState.open)
            {
                map = new HashMap<>();
                map.put("title", "意外险");
                map.put("details", "");
                map.put("price", "¥ 30");
                map.put("count", "x" + ccrListData.size() + "人");
                orderListData.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, orderListData, R.layout.popwindow_content_plane_order_item,
                    new String[]{"title", "details", "price", "count"},
                    new int[]{R.id.title, R.id.details, R.id.price, R.id.count});

            listView.setAdapter(adapter);

            ViewUtil.setListViewHeightBasedOnChildren(listView);
            View filterBlack = cityContentView.findViewById(R.id.filterBlack);

            filterBlack.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        LogUtil.i("onActivityResult=========" + requestCode);
        switch (requestCode)
        {
            case PICK_CONTACT: //选择通讯录联系人；
                if (resultCode != Activity.RESULT_OK)
                {
                    return;
                }
                Connector connector = new Connector();
                ContactUtil.getConnector(this, data, connector);
                selectConnector = connector;
                //此时通讯录中的联系人没有id；
                lxrName.setText(selectConnector.nickName);
                lxrMobile.setText(selectConnector.mobile);
                break;
            case ADD_CONNECTOR://选择常用联系人；
                if (data == null) return;
                selectConnector = (Connector) data.getSerializableExtra("connector");
                lxrName.setText(selectConnector.nickName);
                lxrMobile.setText(selectConnector.mobile);
                break;
            case SELECT_PASSENGER:
                if (data == null) return;
                PassengerInfoBean passengerInfoBean = (PassengerInfoBean) data.getSerializableExtra("passenger");
                ccrListData.add(passengerInfoBean);
                ccrAdapter.notifyDataSetChanged();
                ccrListView.setVisibility(View.VISIBLE);
                setTotalAmount();
                break;
            default:
                break;
        }
    }

}
