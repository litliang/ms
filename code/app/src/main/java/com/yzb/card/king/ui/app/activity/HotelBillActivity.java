package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.hotel.HotelOrderParam;
import com.yzb.card.king.bean.hotel.InvoiceInfoParam;
import com.yzb.card.king.bean.ticket.PostFeeBean;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.bean.GoodsAddressBean;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.appwidget.popup.BottomDataListPP;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.ticket.presenter.GetPostFeePresenter;
import com.yzb.card.king.ui.ticket.view.GetPostFeeView;
import com.yzb.card.king.util.ContactUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/7
 * 描  述：
 */
@ContentView(R.layout.activity_hotel_bill)
public class HotelBillActivity extends BaseActivity implements View.OnClickListener, GetPostFeeView {

    private final static int REQ_GET_DEBITRISE = 1; //请求发票抬头code

    private static final int REQ_GET_ADDRESS = 3;//获取收获地址；

    @ViewInject(R.id.llBillContent)
    private LinearLayout llBillContent;

    @ViewInject(R.id.slideButton)
    private SlideButton slideButton;

    @ViewInject(R.id.tvOneMsg)
    private TextView tvOneMsg;

    @ViewInject(R.id.llExpressFee)
    private LinearLayout llExpressFee;

    @ViewInject(R.id.tvQuickPrice)
    private TextView tvQuickPrice;

    @ViewInject(R.id.tvRoomComboPrice)
    private TextView tvRoomComboPrice;

    @ViewInject(R.id.tvPeopleType)
    private TextView tvPeopleType;

    @ViewInject(R.id.tvTabName)
    private TextView tvTabName;

    @ViewInject(R.id.tvBumfBill)
    private TextView tvBumfBill;

    @ViewInject(R.id.tvInvoiceTypeName)
    private TextView tvInvoiceTypeName;

    @ViewInject(R.id.tvAddressEmailMsg)
    private TextView tvAddressEmailMsg;

    @ViewInject(R.id.ivArrowGrayAddEmail)
    private ImageView ivArrowGrayAddEmail;

    @ViewInject(R.id.tvInvoiceContent)
    private TextView tvInvoiceContent;

    @ViewInject(R.id.llCompanyInfo)
    private LinearLayout llCompanyInfo;

    @ViewInject(R.id.llPrice)
    private LinearLayout llPrice;

    @ViewInject(R.id.llAddressEmail)
    private LinearLayout llAddressEmail;

    @ViewInject(R.id.etInvoiceRise)
    private EditText etInvoiceRise;

    @ViewInject(R.id.etTaxpayerID)
    private EditText etTaxpayerID;

    @ViewInject(R.id.etTaxpayerAddress)
    private EditText etTaxpayerAddress;

    @ViewInject(R.id.etCompanyPhone)
    private EditText etCompanyPhone;

    @ViewInject(R.id.etAccountBank)
    private EditText etAccountBank;

    @ViewInject(R.id.etBankAccount)
    private EditText etBankAccount;

    @ViewInject(R.id.etEmailAddress)
    private EditText etEmailAddress;

    @ViewInject(R.id.etRemark)
    private EditText etRemark;

    @ViewInject(R.id.tvTxtNum)
    private TextView tvTxtNum;

    private BottomDataListPP pp = null;

    private BottomDataListPP invoicePp = null;

    private BottomDataListPP invoiceContentPp = null;

    private int selectedApplicantIndex = 1;

    private int selectedInvoicIndex = 0;

    private int selectedCInvoicIndex = 0;

    private int type = 2;//1企业；2个人；3政府机关行政单位；

    private int invoicType = 1;//1普通发票；2专用发票；

    private int invoicCType = 1;//1房费、2代订房费、3住宿费、4代订住宿费、5旅游费、6差旅费、7团费、8考察费、9服务费、10代办签证费

    private String invoiceForm = "1";//1电子发票；2纸质发票

    private String totalPrice = "0";

    private InvoiceInfoParam invoiceInfoParam;

    private GetPostFeePresenter postFeePresenter;//计算邮费

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        postFeePresenter = new GetPostFeePresenter(this);

        totalPrice = getIntent().getStringExtra("totalPrice");

        if (getIntent().hasExtra("invoiceDataBean")) {

            invoiceInfoParam = (InvoiceInfoParam) getIntent().getSerializableExtra("invoiceDataBean");
        }

        setTitleNmae(R.string.seting_debit_info, R.string.setting_verify_cg);

        initView();

        initData();

    }

    private void initData()
    {

        if (invoiceInfoParam != null) {

            int applicantInt = Integer.parseInt(invoiceInfoParam.getApplicant());

            String[] nameArray = getResources().getStringArray(R.array.applicant_name_array);

            int[] nameValueArray = getResources().getIntArray(R.array.applicant_value_array);

            for (int i = 0; i < nameValueArray.length; i++) {

                int a = nameValueArray[i];

                if (a == applicantInt) {

                    selectedApplicantIndex = i;

                    type = a;

                    tvPeopleType.setText(nameArray[i]);

                    if (type == 2) {

                        llCompanyInfo.setVisibility(View.GONE);

                        etInvoiceRise.setHint("请输入个人名字");
                    } else {

                        llCompanyInfo.setVisibility(View.VISIBLE);

                        etInvoiceRise.setHint("请输入公司名称");
                    }

                    break;
                }
            }

            int invoiceType = Integer.parseInt(invoiceInfoParam.getType());

            String[] nameArrayTwo = getResources().getStringArray(R.array.invoice_type_name_array);

            int[] nameValueArrayTwo = getResources().getIntArray(R.array.invoice_type_value_array);

            for (int i = 0; i < nameValueArrayTwo.length; i++) {

                int a = nameValueArrayTwo[i];

                if (a == invoiceType) {

                    selectedInvoicIndex = i;

                    invoicType = a;

                    tvInvoiceTypeName.setText(nameArrayTwo[i]);


                    break;
                }
            }

            int invoiceItemInt = Integer.parseInt(invoiceInfoParam.getInvoiceItem());

            String[] nameArrayThree = getResources().getStringArray(R.array.invoice_content1_name_array);

            int[] nameValueArrayThree = getResources().getIntArray(R.array.invoice_content1_value_array);

            for (int i = 0; i < nameValueArrayThree.length; i++) {

                int a = nameValueArrayThree[i];

                if (a == invoiceItemInt) {

                    selectedCInvoicIndex = i;

                    invoicCType = a;

                    tvInvoiceContent.setText(nameArrayThree[i]);

                    break;
                }
            }


            etInvoiceRise.setText(invoiceInfoParam.getTitle());

            etTaxpayerID.setText(invoiceInfoParam.getIdentificationNo());

            etTaxpayerAddress.setText(invoiceInfoParam.getCompanyAddr());

            etCompanyPhone.setText(invoiceInfoParam.getCompanyTel());

            etAccountBank.setText(invoiceInfoParam.getBankName());

            etBankAccount.setText(invoiceInfoParam.getBankAccount());

            tvRoomComboPrice.setText(Utils.subZeroAndDot(totalPrice));

            etRemark.setText(invoiceInfoParam.getRemark());

            invoiceForm = invoiceInfoParam.getInvoiceForm();

            if ("1".equals(invoiceForm)) {

                tvTabName.setEnabled(true);

                tvBumfBill.setEnabled(false);

                etEmailAddress.setText(invoiceInfoParam.getEmail());

            } else if ("2".equals(invoiceForm)) {

                tvTabName.setEnabled(false);

                tvBumfBill.setEnabled(true);

                etEmailAddress.setText(invoiceInfoParam.getAddress());
            }
            initDataAddressEmalUI();

            slideButton.setToggleState(SlideButton.ToggleState.open);
        }

    }

    private void initView()
    {

        llPrice.setVisibility(View.INVISIBLE);

        tvOneMsg.setText("不需要发票");

        findViewById(R.id.llOne).setOnClickListener(this);

        llBillContent.setVisibility(View.GONE);

        slideButton.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(SlideButton.ToggleState state)
            {
                llBillContent.setVisibility(state == SlideButton.ToggleState.open ? View.VISIBLE : View.GONE);

                llPrice.setVisibility(state == SlideButton.ToggleState.open ? View.VISIBLE : View.GONE);

                //检测当前发票数据 是否是用户需要的纸质发票
                if (invoiceInfoParam != null && state == SlideButton.ToggleState.open && tvBumfBill.isEnabled()) {

                    llExpressFee.setVisibility(View.VISIBLE);

                    tvQuickPrice.setText(Utils.subZeroAndDot(invoiceInfoParam.getExpressAmount() + ""));

                } else {

                    llExpressFee.setVisibility(View.GONE);
                }

                if (state == SlideButton.ToggleState.open) {

                    tvOneMsg.setText("开票金额");

                } else {
                    tvOneMsg.setText("不需要发票");
                }

            }
        });

        tvTabName.setEnabled(true);

        tvBumfBill.setEnabled(false);

        initDataAddressEmalUI();

        findViewById(R.id.llEleFound).setOnClickListener(this);

        findViewById(R.id.llBumfBill).setOnClickListener(this);

        findViewById(R.id.llFapiao).setOnClickListener(this);

        findViewById(R.id.llInvoiceContent).setOnClickListener(this);

        findViewById(R.id.llInvoiceRise).setOnClickListener(this);

        etRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                int strLength = s.toString().length();

                tvTxtNum.setText(strLength + "/225");

            }
        });

        findViewById(R.id.llRight).setOnClickListener(this);

        tvRoomComboPrice.setText(totalPrice);

    }

    private void initDataAddressEmalUI()
    {
        addressBean = null;

        boolean oneFlag = tvTabName.isEnabled();

        boolean twoFlag = tvBumfBill.isEnabled();

        if (oneFlag && !twoFlag) {

            tvAddressEmailMsg.setText("收件邮箱");

            etEmailAddress.setText("");

            etEmailAddress.setHint("请输入接收电子发票的邮箱");

            etEmailAddress.setFocusable(true);

            etEmailAddress.setFocusableInTouchMode(true);

            ivArrowGrayAddEmail.setVisibility(View.INVISIBLE);

            invoiceForm = "1";

            llAddressEmail.setOnClickListener(null);
            etEmailAddress.setOnClickListener(null);
        } else {

            etEmailAddress.setText("");
            tvAddressEmailMsg.setText("收货地址");

            etEmailAddress.setHint("请填写完整的配送地址");

            etEmailAddress.setFocusable(false);

            etEmailAddress.setFocusableInTouchMode(false);

            etEmailAddress.setOnClickListener(this);

            ivArrowGrayAddEmail.setVisibility(View.VISIBLE);

            invoiceForm = "2";

            llAddressEmail.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.etEmailAddress:
            case R.id.llAddressEmail://纸质发票收件地址
                Intent addressIntent = new Intent(this, AddressManageActivity.class);
                addressIntent.putExtra("flag", AddressManageActivity.GET_ADDRESS_DATA);
                startActivityForResult(addressIntent, REQ_GET_ADDRESS);
                break;

            case R.id.llInvoiceRise://发票抬头

                Intent debitRiseIntent = new Intent(this, DebitRiseManageActivity.class);
                debitRiseIntent.putExtra("flag", DebitRiseManageActivity.GET_RISE_DATA);
                startActivityForResult(debitRiseIntent, REQ_GET_DEBITRISE);

                break;

            case R.id.llRight://完成

                if (slideButton.getState() == SlideButton.ToggleState.open) {

                    String etInvoiceRiseStr = etInvoiceRise.getText().toString();

                    if(TextUtils.isEmpty(etInvoiceRiseStr)){

                        ToastUtil.i(HotelBillActivity.this,"请输入发票抬头");

                        return;
                    }

                    String etEmailAddressStr = etEmailAddress.getText().toString();

                    if ("1".equals(invoiceForm)) {


                        if(TextUtils.isEmpty(etEmailAddressStr)){
                            ToastUtil.i(HotelBillActivity.this,"请输入邮箱地址");
                            return;

                        }

                    } else if ("2".equals(invoiceForm)) {

                        if(TextUtils.isEmpty(etEmailAddressStr)){
                            ToastUtil.i(HotelBillActivity.this,"请输入选择邮寄地址");
                            return;
                        }
                    }

                    InvoiceInfoParam invoiceInfoParam = new InvoiceInfoParam();

                    float tvRoomComboPriceF = Float.parseFloat(tvRoomComboPrice.getText().toString());

                    invoiceInfoParam.setInvoiceAmount(tvRoomComboPriceF);


                    float tvQuickPriceF = Float.parseFloat(tvQuickPrice.getText().toString());

                    //检查是否是纸质发票
                    if(tvBumfBill.isEnabled()){

                        invoiceInfoParam.setExpressAmount(tvQuickPriceF);

                    }else {

                        invoiceInfoParam.setExpressAmount(0);

                    }

                    invoiceInfoParam.setApplicant(type + "");

                    invoiceInfoParam.setType(invoicType + "");

                    invoiceInfoParam.setInvoiceItem(invoicCType + "");

                    invoiceInfoParam.setInvoiceForm(invoiceForm);


                    if (!TextUtils.isEmpty(etInvoiceRiseStr)) {

                        invoiceInfoParam.setTitle(etInvoiceRiseStr);
                    }

                    String etTaxpayerIDStr = etTaxpayerID.getText().toString();

                    if (!TextUtils.isEmpty(etTaxpayerIDStr)) {

                        invoiceInfoParam.setIdentificationNo(etTaxpayerIDStr);
                    }

                    String etTaxpayerAddressStr = etTaxpayerAddress.getText().toString();

                    if (!TextUtils.isEmpty(etTaxpayerAddressStr)) {

                        invoiceInfoParam.setCompanyAddr(etTaxpayerAddressStr);
                    }

                    String etCompanyPhoneStr = etCompanyPhone.getText().toString();
                    if (!TextUtils.isEmpty(etCompanyPhoneStr)) {

                        invoiceInfoParam.setCompanyTel(etCompanyPhoneStr);
                    }

                    String etAccountBankStr = etAccountBank.getText().toString();

                    if (!TextUtils.isEmpty(etAccountBankStr)) {
                        invoiceInfoParam.setBankName(etAccountBankStr);
                    }

                    String etBankAccountStr = etBankAccount.getText().toString();

                    if (!TextUtils.isEmpty(etBankAccountStr)) {

                        invoiceInfoParam.setBankAccount(etBankAccountStr);
                    }


                    if (!TextUtils.isEmpty(etEmailAddressStr)) {

                        if ("1".equals(invoiceForm)) {

                            invoiceInfoParam.setEmail(etEmailAddressStr);

                        } else if ("2".equals(invoiceForm)) {

                            invoiceInfoParam.setAddress(etEmailAddressStr);
                        }
                    }

                    String etRemarkStr = etRemark.getText().toString();
                    if (!TextUtils.isEmpty(etRemarkStr)) {

                        invoiceInfoParam.setRemark(etRemarkStr);
                    }

                    if (addressBean != null) {

                        invoiceInfoParam.setDistrictId(Integer.parseInt(addressBean.getDistrictId()));

                        invoiceInfoParam.setContacts(addressBean.getContacts());

                        invoiceInfoParam.setPhone(addressBean.getPhone());
                    } else {

                        invoiceInfoParam.setDistrictId(-1);
                    }

                    Intent intent = new Intent();

                    intent.putExtra("invoiceInfoParam", invoiceInfoParam);

                    setResult(1000, intent);

                    finish();

                } else {
                    Intent intent = new Intent();

                    setResult(1000, intent);

                    finish();
                }

                break;


            case R.id.llInvoiceContent://发票内容
                if (invoiceContentPp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.invoice_content1_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.invoice_content1_value_array);

                    invoiceContentPp = new BottomDataListPP(this, -1);

                    invoiceContentPp.setTitleName(R.string.tv_invoice_content_str);

                    invoiceContentPp.setLogicData(nameArray, nameValueArray);

                    invoiceContentPp.setSelectIndex(selectedCInvoicIndex);

                    invoiceContentPp.setCallBack(invoiceCCallBack);
                }

                invoiceContentPp.showPP(getWindow().getDecorView());
                break;
            case R.id.llFapiao://发票类型
                if (invoicePp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.invoice_type_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.invoice_type_value_array);

                    invoicePp = new BottomDataListPP(this, -1);

                    invoicePp.setTitleName(R.string.tv_invoice_str);

                    invoicePp.setLogicData(nameArray, nameValueArray);

                    invoicePp.setSelectIndex(selectedInvoicIndex);

                    invoicePp.setCallBack(invoiceCallBack);
                }

                invoicePp.showPP(getWindow().getDecorView());
                break;
            case R.id.llOne://申请方

                if (pp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.applicant_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.applicant_value_array);

                    pp = new BottomDataListPP(this, -1);

                    pp.setTitleName(R.string.tv_application_str);

                    pp.setLogicData(nameArray, nameValueArray);

                    pp.setSelectIndex(selectedApplicantIndex);

                    pp.setCallBack(callBack);
                }

                pp.showPP(getWindow().getDecorView());
                break;
            case R.id.llEleFound://电子发票
                boolean ef = tvTabName.isEnabled();

                tvTabName.setEnabled(!ef);

                tvBumfBill.setEnabled(ef);

                initDataAddressEmalUI();
                break;
            case R.id.llBumfBill://邮箱或地址

                boolean ef1 = tvBumfBill.isEnabled();

                tvBumfBill.setEnabled(!ef1);

                tvTabName.setEnabled(ef1);

                initDataAddressEmalUI();
                break;

            default:
                break;
        }
    }

    private BottomDataListPP.BottomDataListCallBack callBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            tvPeopleType.setText(name);

            type = nameValue;

            if (type == 2) {

                llCompanyInfo.setVisibility(View.GONE);

                etInvoiceRise.setHint("请输入个人名字");
            } else {

                llCompanyInfo.setVisibility(View.VISIBLE);

                etInvoiceRise.setHint("请输入公司名称");
            }


        }
    };

    private BottomDataListPP.BottomDataListCallBack invoiceCallBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            tvInvoiceTypeName.setText(name);

            invoicType = nameValue;

        }
    };

    private BottomDataListPP.BottomDataListCallBack invoiceCCallBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            tvInvoiceContent.setText(name);

            invoicCType = nameValue;

        }
    };

    private GoodsAddressBean addressBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        switch (requestCode) {

            case REQ_GET_ADDRESS:  //选择收货地址

                Serializable addressObj = data.getSerializableExtra("addressBeanTemp");
                if (addressObj != null) {
                    addressBean = (GoodsAddressBean) addressObj;


                    etEmailAddress.setText(addressBean.getProvinceName() + addressBean.getCityName() + addressBean.getAddress());


                    loadPostageFee(addressBean.getCityId());
                }
                break;

            case REQ_GET_DEBITRISE:  //选择发票抬头
                Serializable debitRiseObj = data.getSerializableExtra("risetBean");
                if (debitRiseObj != null) {
                    DebitRiseBean debitRiseBean = (DebitRiseBean) debitRiseObj;

                    int applicantInt = (int) debitRiseBean.getType();

                    String[] nameArray = getResources().getStringArray(R.array.applicant_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.applicant_value_array);

                    for (int i = 0; i < nameValueArray.length; i++) {

                        int a = nameValueArray[i];

                        if (a == applicantInt) {

                            selectedApplicantIndex = i;

                            type = a;

                            tvPeopleType.setText(nameArray[i]);


                            if (type == 2) {

                                llCompanyInfo.setVisibility(View.GONE);

                                etInvoiceRise.setHint("请输入个人名字");
                            } else {

                                llCompanyInfo.setVisibility(View.VISIBLE);

                                etInvoiceRise.setHint("请输入公司名称");
                            }

                            break;
                        }
                    }
                    etInvoiceRise.setText(debitRiseBean.getContent());

                    etTaxpayerID.setText(debitRiseBean.getIdentificationNo());

                    etTaxpayerAddress.setText(debitRiseBean.getCompanyAddr());

                    etCompanyPhone.setText(debitRiseBean.getCompanyTel());

                    etAccountBank.setText(debitRiseBean.getBankName());

                    etBankAccount.setText(debitRiseBean.getBankAccount());
                }
                break;
        }
    }

    /**
     * 获取邮费信息；
     *
     * @cityId 目的地的cityId；
     */
    private void loadPostageFee(String cityId)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("cityId", cityId);
        params.put("pageStart", "0");
        params.put("pageSize", "1");
        postFeePresenter.loadData(params);
    }


    @Override
    public void onGetPostFeeSucess(Object data)
    {
        if (data != null) {

            List<PostFeeBean> postFeeBeans = (List<PostFeeBean>) data;

            if (postFeeBeans != null && postFeeBeans.size() > 0) {

                PostFeeBean postFeeBean = postFeeBeans.get(0);

                llExpressFee.setVisibility(View.VISIBLE);

                tvQuickPrice.setText(Utils.subZeroAndDot(postFeeBean.getFee() + ""));

            }
        }
    }

    @Override
    public void onGetPostFeeFail(String failMsg)
    {

    }
}
