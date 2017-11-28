package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.bean.DebitRiseBean;
import com.yzb.card.king.ui.app.presenter.DebitRiseListPresenter;
import com.yzb.card.king.ui.appwidget.popup.BottomDataListPP;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016.6.21
 * 新增/修改发票抬头；
 */
public class DebitRiseEditActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    private EditText etDebitRise;

    public static final String ARG_DATA_BEAN = "ARG_DATA_BEAN";

    private DebitRiseBean data;

    private int selectedIndex = 1;

    private DebitRiseListPresenter updateDebitRisePresenter;

    private EditText etOne, etTwo, etThree, etFour, etFive;

    private LinearLayout llInvoice;

    private LinearLayout llApplicant;

    private TextView tvPeopleType;

    private View rootView;

    BottomDataListPP pp = null;

    private  int type ;//1企业；2个人；3政府机关行政单位；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;

        super.onCreate(savedInstanceState);

        rootView = LayoutInflater.from(this).inflate(R.layout.activity_add_debit_rise, null);

        setContentView(rootView);

        updateDebitRisePresenter = new DebitRiseListPresenter(this);

        assignViews();

        recvIntentData();

        String title = data == null ? getString(R.string.seting_debit_info) : getString(R.string.setting_modify_debit_rise2);

        setHeader(R.mipmap.icon_arrow_back_black, title);

        if(data!=null){

            etDebitRise.setText( data.content);

            etOne.setText(data.getIdentificationNo());

            etTwo.setText(data.getCompanyAddr());

            etThree.setText(data.getCompanyTel());

            etFour.setText(data.getBankName());

            etFive.setText(data.getBankAccount());
        }

    }

    private void recvIntentData()
    {

        String[] nameArray = getResources().getStringArray(R.array.applicant_name_array);

        Serializable obj = getIntent().getSerializableExtra(ARG_DATA_BEAN);

        if (obj != null && obj instanceof DebitRiseBean) {
            data = (DebitRiseBean) obj;

            long type = data.getType();

            if(type>0){
                selectedIndex = (int) (type-1);
                callBack.onClickItemDataBack(nameArray[selectedIndex],(int) type,selectedIndex);
            }

            llApplicant.setEnabled(false);

        }else{
            callBack.onClickItemDataBack(nameArray[selectedIndex],selectedIndex+1,selectedIndex);
        }
    }

    private void assignViews()
    {

        findViewById(R.id.headerLeft).setOnClickListener(this);

        etDebitRise = (EditText) findViewById(R.id.et_debit_rise);

        findViewById(R.id.panelSave).setOnClickListener(this);

        llApplicant = (LinearLayout) findViewById(R.id.llApplicant);
        llApplicant.setOnClickListener(this);

        tvPeopleType = (TextView) findViewById(R.id.tvPeopleType);

        etOne = (EditText) findViewById(R.id.etOne);

        etTwo = (EditText) findViewById(R.id.etTwo);

        etThree = (EditText) findViewById(R.id.etThree);

        etFour = (EditText) findViewById(R.id.etFour);

        etFive = (EditText) findViewById(R.id.etFive);

        llInvoice = (LinearLayout) findViewById(R.id.llInvoice);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.headerLeft:
                finish();
                break;
            case R.id.panelSave:
                if (isInputRight()) {
                exeSave();
                 }
                break;
            case R.id.llApplicant://申请人


                if (pp == null) {

                    String[] nameArray = getResources().getStringArray(R.array.applicant_name_array);

                    int[] nameValueArray = getResources().getIntArray(R.array.applicant_value_array);

                    pp = new BottomDataListPP(this, -1);

                    pp.setTitleName(R.string.tv_application_str);

                    pp.setLogicData(nameArray, nameValueArray);

                    pp.setSelectIndex(selectedIndex);

                    pp.setCallBack(callBack);
                }

                pp.showPP(rootView);

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

            if(nameValue == 2){

                llInvoice.setVisibility(View.GONE);

                etDebitRise.setHint(R.string.setting_add_debit_rise_et_hint1);

            }else{

                if(nameValue==1){

                    etDebitRise.setHint(R.string.setting_add_debit_rise_et_hint);

                }else if(nameValue==3){

                    etDebitRise.setHint(R.string.setting_input_gov_hint);

                }

                llInvoice.setVisibility(View.VISIBLE);
            }
        }
    };


    /**
     * 保存发票抬头；
     */
    private void exeSave()
    {
        final String debitRise = etDebitRise.getText().toString().trim();
        if (isEmpty(debitRise)) {
            toastCustom(R.string.debit_rise_not_allow_empty);
            return;
        }


        showPDialog(R.string.setting_committing);

        Map<String, Object> param = new HashMap<>();

        param.put("content", debitRise);

        param.put("type", type); //1企业；2个人；3政府机关行政单位；

        if(type ==1 ||type==3){

            param.put("identificationNo", etOne.getText().toString());//纳税人识别号

            param.put("companyAddr", etTwo.getText().toString());//companyAddr

            param.put("companyTel", etThree.getText().toString());//companyTel

            param.put("bankName", etFour.getText().toString());//bankName

            param.put("bankAccount", etFive.getText().toString()); //bankAccount

        }

        if (data != null) {
            param.put("invoiceId", data.getInvoiceId()); //
            param.put("status", data.getStatus()); //
        }

        param.put("serviceName", data != null ? CardConstant.setting_invoiceupdate : CardConstant.setting_invoicecreate);

        updateDebitRisePresenter.addOrUpdateRiseData(param);
    }

    /**
     * 判空；
     */
    private boolean isInputRight()
    {
        if (isEmpty(etDebitRise.getText().toString().trim())) {
            toastCustom(R.string.debit_rise_not_allow_empty);
            return false;
        }

        if(type!=2){

             String addressStr = etTwo.getText().toString();

            if(TextUtils.isEmpty(addressStr)){
                toastCustom(R.string.setting_company_actual_address);
                return false;
            }

        }


        return true;
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(R.string.app_op_info_success);
        finish();
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        closePDialog();
        toastCustom(o + "");
    }


}
