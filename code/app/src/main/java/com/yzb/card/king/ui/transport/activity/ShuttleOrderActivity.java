package com.yzb.card.king.ui.transport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.app.activity.ConnectorManageActivity;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.discount.activity.pay.DiscountPayActivity;
import com.yzb.card.king.ui.discount.bean.BusTypeGradeBean;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.other.task.GetDefaultConnectorTask;
import com.yzb.card.king.ui.transport.bean.SpecialCar;
import com.yzb.card.king.ui.transport.fragment.ShuttleOrderBaseInfoFragment;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.StringUtils;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

/**
 * 接送机订单
 */
public class ShuttleOrderActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final int ADD_CONNECTOR = 2;
    private BusTypeGradeBean busTypeGradeBean = null;
    private SpecialCar busTypeBean = null;
    private View view = null;

    private ShuttleOrderBaseInfoFragment baseInfoFragment = null;

    private TextView seatImg = null;

    private EditText lxrMobile1 = null;

    private LinearLayout secondLxrLayout = null;
    private EditText lxrMobile2 = null;
    private SlideButton mySlideButton = null;

    private EditText lxrName = null;
    private EditText lxrMobile = null;

    private RelativeLayout orderDetailsLayout = null;
    private ImageView orderDetailsImage = null;
    private PopupWindow orderPopWindow = null;

    private TextView totalAmount = null;

    private static final int PICK_CONTACT = 0;
    private static final int PICK_CONTACT1 = 1;
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
        view = LayoutInflater.from(this).inflate(R.layout.activity_shuttle_order, null, false);
        setContentView(view);

        busTypeGradeBean = (BusTypeGradeBean) getIntent().getSerializableExtra("busTypeGradeBean");

        busTypeBean = (SpecialCar) getIntent().getSerializableExtra("specialCar");

        // 加载基本信息
        baseInfoFragment = ShuttleOrderBaseInfoFragment.newInstance("", JSON.toJSONString(busTypeBean));
        getFragmentManager().beginTransaction().replace(R.id.baseInfo, baseInfoFragment).commit();

        addConnector = (ImageView) findViewById(R.id.lxrEditButton);

        addConnector.setOnClickListener(this);
        // 联系人电话1
        lxrMobile1 = (EditText) this.findViewById(R.id.lxrMobile1);
        lxrMobile1.setOnFocusChangeListener(this);
        ImageButton lxrButton1 = (ImageButton) this.findViewById(R.id.lxrButton1);
        lxrButton1.setOnClickListener(this);
        // 联系人电话2
        lxrMobile2 = (EditText) this.findViewById(R.id.lxrMobile2);
        lxrMobile2.setOnFocusChangeListener(this);
        mySlideButton = (SlideButton) view.findViewById(R.id.my_slide_button);
        mySlideButton.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(SlideButton.ToggleState state) {
                if (state == SlideButton.ToggleState.open) {
                    secondLxrLayout.setVisibility(View.VISIBLE);
                } else {
                    secondLxrLayout.setVisibility(View.GONE);
                }
            }
        });

        // 联系人
        secondLxrLayout = (LinearLayout) this.findViewById(R.id.secondLxrLayout);
        ImageButton lxrButton = (ImageButton) this.findViewById(R.id.lxrButton);
        lxrButton.setOnClickListener(this);
        lxrName = (EditText) this.findViewById(R.id.lxrName);
        lxrMobile = (EditText) this.findViewById(R.id.lxrMobile);
        lxrMobile.setOnFocusChangeListener(this);

        orderDetailsLayout = (RelativeLayout) this.findViewById(R.id.orderDetailsLayout);
        orderDetailsLayout.setTag(0);
        orderDetailsLayout.setOnClickListener(this);

        orderDetailsImage = (ImageView) this.findViewById(R.id.orderDetailsImage);

        totalAmount = (TextView) findViewById(R.id.totalAmount);

        LinearLayout llBack = (LinearLayout) findViewById(R.id.iv_back);
        llBack.setOnClickListener(this);

        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);
    }



    private void initData() {


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
        amount = Integer.parseInt(busTypeGradeBean.price) + 30;
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
            case R.id.lxrEditButton://添加联系人
                addConnector();
                break;
            case R.id.iv_back:
                setResult(AppConstant.RES_BACK);
                finish();
                break;
            case R.id.yuDindXuZhi:
                ToastUtil.i(ShuttleOrderActivity.this, "敬请期待");
                break;

            case R.id.lxrButton:
                //联系人选择
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
                break;
            case R.id.lxrButton1:
                //联系人选择
                Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent1, PICK_CONTACT1);
                break;
            case R.id.orderDetailsLayout:
                //机票订单详情
                startActivity(new Intent(this,PriceEvaluateActivity.class));
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
            intent.putExtra("storeName", busTypeGradeBean.supplierName);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (PICK_CONTACT):
                if(resultCode != Activity.RESULT_OK)
                    return ;
                Connector connector = new Connector();
                ContactUtil.getConnector(this,data,connector);
                lxrName.setText(connector.nickName);
                lxrMobile.setText(connector.mobile);
                lxrMobile2.setText(connector.mobile);
                break;
            case (PICK_CONTACT1):
                if(resultCode != Activity.RESULT_OK)
                    return ;
                Connector connector1 = new Connector();
                ContactUtil.getConnector(this,data,connector1);
                lxrMobile1.setText(connector1.mobile);
                break;
            case ADD_CONNECTOR:
                if (data == null) return;
                Connector connector2 = (Connector) data.getSerializableExtra("connector");
                lxrName.setText(connector2.nickName);
                lxrMobile.setText(connector2.mobile);
                lxrMobile2.setText(connector2.mobile);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {

        } else {
            String mobileValue = lxrMobile.getText().toString().trim();

            if(v.getId() == R.id.lxrMobile1)
                mobileValue = lxrMobile1.getText().toString().trim();

            if(v.getId() == R.id.lxrMobile2)
                mobileValue = lxrMobile2.getText().toString().trim();

            if (StringUtils.isEmpty(mobileValue)) {
                ToastUtil.i(ShuttleOrderActivity.this, "联系人手机号不能为空");
                return;
            }

            if(!ValidatorUtil.isMobile(mobileValue)){
                ToastUtil.i(ShuttleOrderActivity.this, "联系人手机号格式有误");
                return ;
            }
        }
    }
}
