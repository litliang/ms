package com.yzb.card.king.ui.integral.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;
import com.yzb.card.king.ui.appwidget.popup.AddRosterPopup;
import com.yzb.card.king.ui.appwidget.MyTextWatcher;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.http.integral.ContactsCreateRequest;
import com.yzb.card.king.ui.manage.IntegralDataManager;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.ValidatorUtil;
import com.yzb.card.king.util.AppUtils;

/**
 * @author gengqiyun
 * @date 2016/6/7
 * 积分分享-->添加名单 对话框；
 */
public class AddRosterDialogFragment extends DialogFragment implements View.OnClickListener {

    private static AddRosterDialogFragment dialogFragment;

    private IDialogCallBack callBack;

    public void setCallBack(IDialogCallBack callBack) {
        this.callBack = callBack;
    }

    public static AddRosterDialogFragment getInstance(String arg1, String arg2) {
        if (dialogFragment == null) {
            dialogFragment = new AddRosterDialogFragment();
        }
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_jfb_add_roster_dialog, null);
        dialog.setContentView(view);
        initView(view);
        return dialog;
    }


    private EditText etInputNickname;//昵称；
    private EditText etInputPhone;//手机号；
    private TextView tvInputRelation;//关系；
    private TextView tvCommit;
    private TextView tv_relation;

    private View rlOne;

    private void initView(View view) {
        if (view != null) {
            view.findViewById(R.id.iv_close_window).setOnClickListener(this);

            etInputNickname = (EditText) view.findViewById(R.id.et_input_nickname);
            etInputNickname.addTextChangedListener(new MyTextWatcher() {
                @Override
                public void textChange(String s) {
                    judgeAllInputEmpty();
                }
            });
            etInputPhone = (EditText) view.findViewById(R.id.et_input_phone);
            etInputPhone.addTextChangedListener(new MyTextWatcher() {
                @Override
                public void textChange(String s) {
                   judgeAllInputEmpty();
                }
            });
            view.findViewById(R.id.iv_phone_book).setOnClickListener(this);

            tvInputRelation = (TextView) view.findViewById(R.id.tv_input_relation);
            view.findViewById(R.id.panel_arrow_down).setOnClickListener(this);

            view.findViewById(R.id.tv_input_relation).setOnClickListener(this);
            tvCommit = (TextView) view.findViewById(R.id.tv_commit);
            enableTvCommitByFlag(false);
            tvCommit.setOnClickListener(this);

            tv_relation = (TextView) view.findViewById(R.id.tv_relation);

            rlOne = view.findViewById(R.id.rlOne);
        }
    }

    /**
     * 判断提交区域的空值情况；
     */
    public void judgeAllInputEmpty() {
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
    public void enableTvCommitByFlag(boolean enable) {
        tvCommit.setEnabled(enable);
        tvCommit.setSelected(enable);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_window:
                dismiss();
                break;
            case R.id.iv_phone_book:
                // 联系人；
                AppUtils.callContact(getActivity());
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
    private String ralation ="null";
    private void showRelationList() {

        AddRosterPopup addRosterPopup = new AddRosterPopup(rlOne,getActivity(),new AddRosterPopup.PriorityListener(){


            @Override
            public void refreshPriorityUI(Object o) {

                IntegralShareLinkman temp = (IntegralShareLinkman)o;

                tvInputRelation.setText(temp.getRelationName());

                ralation = temp.getRelationship();

               judgeAllInputEmpty();
            }
        });

        addRosterPopup.create();
    }


    public void setData(String name,String phoneNumber){

        etInputNickname.setText(name);

        etInputPhone.setText(phoneNumber);

        judgeAllInputEmpty();

    }

    /**
     * 提交；
     */
    private void exeCommit() {
        if (isInputRight()) {
            String nickName = etInputNickname.getText().toString().trim();

            //手机号；
            String phoneBook = etInputPhone.getText().toString().trim();

            String relation = tvInputRelation.getText().toString().trim();

          boolean  ifSame =  IntegralDataManager.getInstance().checkIntegralShareLinkmanSame(phoneBook,nickName);

            if(ifSame){

                ToastUtil.i(getActivity(),"联系人内已有此信息");
                return;
            }

          final  IntegralShareLinkman temp = new IntegralShareLinkman();



            temp.setNickName(nickName);

            temp.setMobile(phoneBook);

            temp.setRelationName(relation);

            temp.setRelationship(ralation);


            //发送添加联系人请求
            ProgressDialogUtil.getInstance().showProgressDialog(getActivity(), false);

            ContactsCreateRequest request = new ContactsCreateRequest("1",temp, new DataCallBack() {

                @Override
                public void requestSuccess(Object o, int type)
                {


                    callBack.dialogCallBack(temp);

                    dismiss();

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
    public boolean isInputRight() {
        //昵称；
        String nickName = etInputNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.i(getActivity(), getString(R.string.jfb_nickname_no_empty));
            return false;
        }

        //手机号；
        String phoneBook = etInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneBook)) {
            ToastUtil.i(getActivity(), getString(R.string.jfb_phone_no_empty));
            return false;
        }

        //手机号；
        if (!ValidatorUtil.isMobile(phoneBook)) {
            ToastUtil.i(getActivity(), getString(R.string.toast_chech_your_phone_number));
            return false;
        }
        // 关系；
        String relation = tvInputRelation.getText().toString().trim();
        if (TextUtils.isEmpty(relation)) {
            ToastUtil.i(getActivity(), getString(R.string.jfb_relation_hint));
            return false;
        }
        return true;
    }
}
