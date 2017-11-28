package com.yzb.card.king.ui.integral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;
import com.yzb.card.king.http.integral.ContactsCreateRequest;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.ui.appwidget.popup.AddRosterPopup;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.manage.IntegralDataManager;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2016/7/9
 * 描  述：
 */
public class IntegralAddRosterActivity extends Activity implements View.OnClickListener {


    private EditText etInputNickname;//昵称；
    private EditText etInputPhone;//手机号；
    private TextView tvInputRelation;//关系；
    private TextView tvCommit;
    private TextView tv_relation;

    private View rlOne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_jfb_add_roster_dialog);

        initView();
    }


    private void initView()
    {
        findViewById(R.id.iv_close_window).setOnClickListener(this);

        etInputNickname = (EditText) findViewById(R.id.et_input_nickname);
        etInputNickname.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void textChange(String s)
            {
                judgeAllInputEmpty();
            }
        });
        etInputPhone = (EditText) findViewById(R.id.et_input_phone);
        etInputPhone.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void textChange(String s)
            {
                judgeAllInputEmpty();
            }
        });
        findViewById(R.id.iv_phone_book).setOnClickListener(this);

        tvInputRelation = (TextView) findViewById(R.id.tv_input_relation);
        findViewById(R.id.panel_arrow_down).setOnClickListener(this);

        findViewById(R.id.tv_input_relation).setOnClickListener(this);
        tvCommit = (TextView) findViewById(R.id.tv_commit);
        enableTvCommitByFlag(false);
        tvCommit.setOnClickListener(this);

        tv_relation = (TextView) findViewById(R.id.tv_relation);

        rlOne = findViewById(R.id.rlOne);
    }

    /**
     * 判断提交区域的空值情况；
     */
    public void judgeAllInputEmpty()
    {
        if (!TextUtils.isEmpty(etInputNickname.getText().toString().trim()) &&
                !TextUtils.isEmpty(etInputPhone.getText().toString().trim())
                && !TextUtils.isEmpty(tvInputRelation.getText().toString().trim())) {
            enableTvCommitByFlag(true);
        } else {
            enableTvCommitByFlag(false);
        }
    }

    /**
     * 更新提交textview的可用状态；
     *
     * @param enable
     */
    public void enableTvCommitByFlag(boolean enable)
    {
        tvCommit.setEnabled(enable);
        tvCommit.setSelected(enable);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.iv_close_window:
                finish();
                break;
            case R.id.iv_phone_book:
                // 联系人；
                AppUtils.callContact(this);
                break;
            case R.id.tv_input_relation:
            case R.id.panel_arrow_down:
                // 下拉关系列表；
                showRelationList();
                break;
            case R.id.tv_commit:
                exeCommit();
                break;
        }
    }

    /**
     * 显示联系列表；
     */
    private String ralation = "null";

    private void showRelationList()
    {

        AddRosterPopup addRosterPopup = new AddRosterPopup(rlOne, IntegralAddRosterActivity.this, new AddRosterPopup.PriorityListener() {


            @Override
            public void refreshPriorityUI(Object o)
            {

                if (o == null) {

                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlOne.getLayoutParams();

                    lp.bottomMargin = 0;
                    rlOne.setLayoutParams(lp);

                } else {

                    IntegralShareLinkman temp = (IntegralShareLinkman) o;

                    tvInputRelation.setText(temp.getRelationName());

                    ralation = temp.getRelationship();

                    judgeAllInputEmpty();
                }
            }
        });


        addRosterPopup.create();

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rlOne.getLayoutParams();

        lp.bottomMargin = 300;
        rlOne.setLayoutParams(lp);

    }


    public void setData(String name, String phoneNumber)
    {

        etInputNickname.setText(name);

        etInputPhone.setText(phoneNumber);

        judgeAllInputEmpty();

    }

    /**
     * 提交；
     */
    private void exeCommit()
    {
        if (isInputRight()) {
            String nickName = etInputNickname.getText().toString().trim();

            //手机号；
            String phoneBook = etInputPhone.getText().toString().trim();

            String relation = tvInputRelation.getText().toString().trim();

            boolean ifSame = IntegralDataManager.getInstance().checkIntegralShareLinkmanSame(phoneBook, nickName);

            if (ifSame) {

                ToastUtil.i(IntegralAddRosterActivity.this, "联系人内已有此信息");
                return;
            }

            final IntegralShareLinkman temp = new IntegralShareLinkman();


            temp.setNickName(nickName);

            temp.setMobile(phoneBook);

            temp.setRelationName(relation);

            temp.setRelationship(ralation);


            //发送添加联系人请求
            ProgressDialogUtil.getInstance().showProgressDialog(IntegralAddRosterActivity.this, false);

            ContactsCreateRequest request = new ContactsCreateRequest("1", temp, new DataCallBack() {
                @Override
                public void requestSuccess(Object o, int type)
                {
                    Intent it = new Intent();

                    it.putExtra("data", temp);

                    setResult(1001, it);

                    finish();

                    ProgressDialogUtil.getInstance().closeProgressDialog();
                }

                @Override
                public void requestFailedDataCall(Object o, int type)
                {
                    ProgressDialogUtil.getInstance().closeProgressDialog();
                }

            });

            request.execute();


        }
    }


    /**
     * 输入合法验证；
     *
     * @return
     */
    public boolean isInputRight()
    {
        //昵称；
        String nickName = etInputNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.i(this, getString(R.string.jfb_nickname_no_empty));
            return false;
        }

        //手机号；
        String phoneBook = etInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneBook)) {
            ToastUtil.i(this, getString(R.string.jfb_phone_no_empty));
            return false;
        }

        //手机号；
        if (!ValidatorUtil.isMobile(phoneBook)) {
            ToastUtil.i(this, getString(R.string.toast_chech_your_phone_number));
            return false;
        }
        // 关系；
        String relation = tvInputRelation.getText().toString().trim();
        if (TextUtils.isEmpty(relation)) {
            ToastUtil.i(this, getString(R.string.jfb_relation_hint));
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (data == null) {
            return;
        }
        switch (requestCode) {


            case AppUtils.PICK_CONTACT:
                if (resultCode != Activity.RESULT_OK) return;

                String[] arry = AppUtils.analyContactData(this, data);
                if (arry.length == 2) {
                    String linkMan = arry[0];
                    String linkManPhone = arry[1];

                    if (linkManPhone != null) {

                        etInputNickname.setText(linkMan);

                        etInputPhone.setText(linkManPhone);

                        judgeAllInputEmpty();
                    }

                }
                break;
            default:
                break;
        }
    }

}
