package com.yzb.card.king.ui.gift.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.bonus.activity.RedBagDetailSendActivity;
import com.yzb.card.king.ui.bonus.view.FavorPayeeView;
import com.yzb.card.king.ui.gift.adapter.AddContactAdapter;
import com.yzb.card.king.ui.gift.adapter.PayeeAdapter;
import com.yzb.card.king.ui.gift.bean.FavorPayee;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.gift.presenter.FavorPayeePresenter;
import com.yzb.card.king.ui.gift.presenter.GiveCardPresenter;
import com.yzb.card.king.ui.my.bean.Payee;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.DialogUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：礼品卡--赠送礼品卡；
 *
 * @author:gengqiyun
 * @date: 2016/12/20
 */
@ContentView(R.layout.activity_give_card)
public class GiveCardActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface, FavorPayeeView {

    private static final int REQ_GET_MOBILE = 0x001;

    public static final int TYPE_GIFTCARD = 0;

    public static final int TYPE_BOUNS = 1;

    private LinearLayout llParentContent;

    private GridLayout gridLayout;

    private LayoutInflater inflater;

    private GiveCardPresenter giveCardPresenter;

    private String recordIds;

    private int pageType = 0;//0:礼品卡；1：红包；

    private String clickItemTag;
    /**
     * 当前输入信息总数
     */
    private int currentInputNum = 1;
    /**
     * 有效信息总数
     */
    private int totalNum;

    //红包发送
    private AddContactAdapter adapter;

    private EditText etPhoneAccount;

    private FavorPayeePresenter favorPayeePresenter;

    private PayeeAdapter payeeWvadapter;

    private TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        giveCardPresenter = new GiveCardPresenter(this);
        recvIntentData();
        initView();
    }

    private void recvIntentData() {
        recordIds = getIntent().getStringExtra("recordIds");

        pageType = getIntent().getIntExtra("pageType", 0);

        if (getIntent().hasExtra("totalNum")) {

            totalNum = getIntent().getIntExtra("totalNum", 1);

        } else {

            totalNum = Integer.MAX_VALUE;

        }

    }

    private void initView() {
        findViewById(R.id.headerLeft).setOnClickListener(this);

        inflater = LayoutInflater.from(this);

        llParentContent = (LinearLayout) findViewById(R.id.llParentContent);
        String title = null;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (pageType == TYPE_GIFTCARD) {
            title = "送礼品卡";

//            View giftView = inflater.inflate(R.layout.activity_view_send_gift, null);
//            giftView.findViewById(R.id.panelAdd).setOnClickListener(this);
//            llParentContent.addView(giftView, lp);
//            gridLayout = (GridLayout) giftView.findViewById(R.id.gridLayout);
//            gridLayout.removeAllViews();
//            addItemToGrid();
        } else if (pageType == TYPE_BOUNS) {
            title = "选择联系人";
        }
        favorPayeePresenter = new FavorPayeePresenter(this);

        View redEnvelepoView = inflater.inflate(R.layout.activity_view_send_red_envelepo, null);

        //初始化红包发送试图
        initRedEnvelepo(redEnvelepoView);

        redEnvelepoView.findViewById(R.id.tvAddAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectPeopleActivity.class);
                intent.putExtra("recordIds", getIntent().getStringExtra("recordIds"));
                intent.putExtra("pageType", GiveCardActivity.TYPE_BOUNS);
                intent.putExtra("totalNum", getIntent().getStringExtra("totalNum"));
                startActivityForResult(intent, 1002);
            }
        });
        llParentContent.addView(redEnvelepoView, lp);

        initContactData();
        setHeader(R.mipmap.icon_back_white, title);
        findViewById(R.id.tvSend).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.etPhoneAccount)).setText("");
            }
        });

    }

    private void initContactData() {
        isDialogShowing();
        Map<String, Object> args = new HashMap<>();
        args.put("sessionId", AppConstant.sessionId);
        args.put("type", "1"); //0全部；1平台账户；2银行卡
        args.put("pageSize", Integer.MAX_VALUE);

        args.put("pageStart", 0 + "");

        favorPayeePresenter.loadData(true, args);
    }

    private void initRedEnvelepo(View redEnvelepoView) {
        WholeRecyclerView addPayeeWv = (WholeRecyclerView) redEnvelepoView.findViewById(R.id.addPayeeWv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        addPayeeWv.setLayoutManager(linearLayoutManager);
        adapter = new AddContactAdapter(this, listener);
        addPayeeWv.setAdapter(adapter);


        WholeRecyclerView payeeWv = (WholeRecyclerView) redEnvelepoView.findViewById(R.id.payeeWv);
        LinearLayoutManager payeeWvM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        payeeWv.setLayoutManager(payeeWvM);
        payeeWvadapter = new PayeeAdapter(this, checkedList);
        payeeWvadapter.setDel(true);
        payeeWv.setAdapter(payeeWvadapter);

//        redEnvelepoView.findViewById(R.id.llAddContact).setOnClickListener(this);

        redEnvelepoView.findViewById(R.id.tvAddAccount).setOnClickListener(this);

        etPhoneAccount = (EditText) redEnvelepoView.findViewById(R.id.etPhoneAccount);

        tvNum = (TextView) redEnvelepoView.findViewById(R.id.tvNum);

        tvNum.setText("已选择 0");
        etPhoneAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    addAccount(s.toString());
                }
            }
        });
        redEnvelepoView.findViewById(R.id.oftenuse).setVisibility(View.GONE);
    }

    private PayeeAdapter.PayeeAdapterOnChecked checkedList = new PayeeAdapter.PayeeAdapterOnChecked() {
        @Override
        public void checkedChanged() {
            calculateNumber();
        }
    };

    private AddContactAdapter.CurrentClickListener listener = new AddContactAdapter.CurrentClickListener() {
        @Override
        public void delAction() {
            calculateNumber();
        }
    };

    private void calculateNumber() {

        int sizeA = adapter.getList().size();

        int sizeB = payeeWvadapter.getCheckList().size();

        int totalAB = sizeA + sizeB;

        tvNum.setText("已选择 " + totalAB);
        changeStatus();
    }

    /**
     * 添加item；
     */
    private void addItemToGrid() {


        final View item = inflater.inflate(R.layout.item_gv_give_giftcard, null);
        item.setTag(System.currentTimeMillis());
        ImageView ivMobile = (ImageView) item.findViewById(R.id.ivMobile);
        TextView tvDelete = (TextView) item.findViewById(R.id.tvDelete);
        ivMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemTag = String.valueOf(item.getTag());

                LogUtil.i("点击的clickItemTag=" + clickItemTag);
                Intent intent = new Intent(GiveCardActivity.this, FavorPayeeActivity.class);
                intent.putExtra("sourceActivity", "sourceActivity");
                startActivityForResult(intent, REQ_GET_MOBILE);
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridLayout != null) {
                    gridLayout.removeView(item);
                    totalNum = totalNum - 1;
                }
            }
        });
        gridLayout.addView(item);

        currentInputNum = currentInputNum + 1;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;

        switch (requestCode) {
            case 1002:
                List list = (List) data.getSerializableExtra("data");
                payeeWvadapter.addNewDataList(list);
                payeeWvadapter.notifyDataSetChanged();
                calculateNumber();

                break;
            case REQ_GET_MOBILE: //获取手机号；
                Serializable obj = data.getSerializableExtra("payeeData");
                if (obj != null) {
                    FavorPayee payee = (FavorPayee) obj;
                    initSpecEditInput(payee);
                }
                break;
            case AppUtils.PICK_CONTACT:
                if (resultCode != Activity.RESULT_OK) return;

                String[] arry = AppUtils.analyContactData(this, data);
                if (arry.length == 2) {

                    String linkMan = arry[0];

                    String linkManPhone = arry[1];

                    if (linkManPhone != null) {

                        giveCardPresenter.checkAccoutRequest(linkManPhone);

                    }

                }
                break;

            default:
                break;
        }
    }

    private void changeStatus() {
        if(payeeWvadapter.getList().size()==totalNum){
            findViewById(R.id.tvSend).setBackgroundResource(R.color.titleRed);
        }else{
            findViewById(R.id.tvSend).setBackgroundColor(Color.parseColor("#999999"));
        }
    }


    /**
     * 填充特定位置的输入框；
     */
    private void initSpecEditInput(FavorPayee payee) {
        if (payee != null) {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View view = gridLayout.getChildAt(i);
                LogUtil.i("clickItemTag=" + String.valueOf(view.getTag()));
                if (!isEmpty(clickItemTag) && clickItemTag.equals(String.valueOf(view.getTag()))) {
                    EditText etMobile = (EditText) view.findViewById(R.id.etMobile);
                    etMobile.setText(payee.getTradeAccount());
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panelAdd:
                addItemToGrid();
                break;
            case R.id.headerLeft:
                finish();
                break;
            case R.id.tvSend: //发送；
                if (pageType == TYPE_GIFTCARD) {

                    if (TextUtils.isEmpty(recordIds)) {
                        ToastUtil.i(this, "请选择收款人");

                        return;
                    }
                    if (payeeWvadapter.getList().size() == 0) {
                        Toast.makeText(this, "没有选择发送人哦", Toast.LENGTH_SHORT).show();
                    } else if (checkCount()) {
                        exeSend();
                    } else {
                        Toast.makeText(this, "不能超" + getIntent().getIntExtra("totalNum", 1) + "人哦~", Toast.LENGTH_SHORT).show();
                    }

                } else if (pageType == TYPE_BOUNS) {
                    if (TextUtils.isEmpty(recordIds)) {
                        ToastUtil.i(this, "请选择收款人");
                        return;
                    }

                    if (payeeWvadapter.getList().size() == 0) {
                        Toast.makeText(this, "没有选择发送人哦", Toast.LENGTH_SHORT).show();
                    } else if (checkCount()) {
                        exeSend();
                    } else {
                        Toast.makeText(this, "不能超" + getIntent().getIntExtra("totalNum", 1) + "人哦~", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.llAddContact://调用联系人

                AppUtils.callContact(this);

                break;
            case R.id.tvAddAccount://添加账户

//                addAccount(etPhoneAccount.getText().toString());

                break;
            default:
                break;
        }
    }

    private boolean checkCount() {
        return payeeWvadapter.getList().size() <= getIntent().getIntExtra("totalNum", 1);
    }

    private void addAccount(String etPhoneAccountStr) {
        if (chechData(etPhoneAccountStr)) {

            showPDialog("正在加载……");
            giveCardPresenter.checkAccoutRequest(etPhoneAccountStr);

        }
    }

    /**
     * 检查手机号
     *
     * @param etPhoneAccountStr
     * @return
     */
    private boolean chechData(String etPhoneAccountStr) {
        boolean flag = true;

        int str = 0;

        String str1 = etPhoneAccount.getText().toString().trim();

        if (TextUtils.isEmpty(str1)) {

            flag = false;

            str = R.string.hint_phone_number;

        } else if (!ValidatorUtil.isMobile(str1)) {

            flag = false;

            str = R.string.toast_chech_your_phone_number;

        }
        if (!flag) {
            ToastUtil.i(this, str);
        }
        return flag;
    }

    /**
     * 发送
     */
    private void exeSend() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_send, null);
        final Dialog dialog = new AlertDialog.Builder(this).setCancelable(true).setView(view).create();
        view.findViewById(R.id.tvNegative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tvPositive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPDialog(R.string.setting_committing);
                Map<String, Object> args = new HashMap<>();

                if (pageType == TYPE_GIFTCARD) {
                    args.put("sessionId", AppConstant.sessionId);
                    args.put("orderId", recordIds); //订单id
                    args.put("receiveIds", getNoEmptyPhones());//接收方ids,多个使用英文逗号分割
                    args.put("serviceName", CardConstant.card_sendmindcard);
                } else if (pageType == TYPE_BOUNS) {
                    args.put("orderId", recordIds); //订单id
                    args.put("sendPlatform", 1); //订单id
                    args.put("receiveIds", getReceivingAccount());//接收方ids,多个使用英文逗号分割
                    args.put("serviceName", CardConstant.card_sendbonus);
                }
                giveCardPresenter.loadData(args);
                dialog.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    /**
     * 获取不为空的输入框内容 多个以逗号分割；
     */
    public String getNoEmptyPhones() {
        StringBuilder sb = new StringBuilder();
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            EditText etMobile = (EditText) gridLayout.getChildAt(i).findViewById(R.id.etMobile);
            String etContent = etMobile.getText().toString().trim();

            //剔除相同的；
            if (!sb.toString().contains(etContent)) {
                sb.append(i == childCount - 1 ? etContent : etContent + ",");
            }
        }
        return sb.toString();
    }

    /**
     * 获取接收方信息
     */
    public String getReceivingAccount() {
        List<FavorPayee> checkedList = adapter.getList();

        checkedList.addAll(payeeWvadapter.getCheckList());

        StringBuilder sb = new StringBuilder();

        int childCount = checkedList.size();

        for (int i = 0; i < childCount; i++) {

            String etContent = checkedList.get(i).getTradeAccount();

            sb.append(i == childCount - 1 ? etContent : etContent + ",");
        }

        return sb.toString();
    }


    /**
     * 标注错误的手机号码；
     *
     * @param erroPhones
     */
    private void markErrorPhones(String erroPhones) {
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            EditText etMobile = (EditText) gridLayout.getChildAt(i).findViewById(R.id.etMobile);
            //含有特定的号码；
            etMobile.setSelected(erroPhones.contains(etMobile.getText().toString().trim()));
        }
    }

    private boolean isInputRight() {
        int childCount = gridLayout.getChildCount();
        boolean allEmpty = true;
        //检查空值；
        for (int i = 0; i < childCount; i++) {
            EditText etMobile = (EditText) gridLayout.getChildAt(i).findViewById(R.id.etMobile);
            if (!isEmpty(etMobile.getText().toString().trim())) {
                allEmpty = false;
                break;
            }
        }
        if (allEmpty) {
            toastCustom(R.string.hint_phone_number);
            return false;
        }
        //检查手机号格式；
        for (int i = 0; i < childCount; i++) {
            EditText etMobile = (EditText) gridLayout.getChildAt(i).findViewById(R.id.etMobile);
            if (!RegexUtil.validPhoneNum(etMobile.getText().toString().trim())) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void callSuccessViewLogic(Object o, int type) {
        if (type == 1) {
            closePDialog();
            toastCustom(R.string.send_suc);
            finish();
        } else if (type == 2) {

            closePDialog();

            etPhoneAccount.setText("");

            List<FavorPayee> listPayee = JSON.parseArray(o + "", FavorPayee.class);

            if (listPayee != null && listPayee.size() > 0) {

                FavorPayee tempPay = listPayee.get(0);

                payeeWvadapter.addNewData(tempPay);

                calculateNumber();

            } else {

                ToastUtil.i(this, R.string.account_no_registered);
            }


        }

    }

    @Override
    public void callFailedViewLogic(Object o, int type) {
        if (type == 1) {

            closePDialog();

            String failMsg = o + "";

            //手机号格式数组；
            if (!isEmpty(failMsg) && (TextUtils.isDigitsOnly(failMsg) || failMsg.contains(","))) {
                markErrorPhones(failMsg);
                toastCustom("没有此账户");
            } else {
                toastCustom(failMsg);
            }
        } else if (type == 2) {

            ToastUtil.i(this, R.string.account_no_registered);
        }
    }

    @Override
    public void onGetFavorPayeeSuc(boolean event_tag, List<FavorPayee> list) {
        closePDialog();

//        payeeWvadapter.addNewDataList(list);
    }

    @Override
    public void onGetFavorPayeeFail(String failMsg) {
        closePDialog();
    }
}
