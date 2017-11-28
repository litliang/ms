package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.integral.IntegralShareLinkman;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.integral.PointShareRequest;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.wallet.openInterface.PayPointHandle;
import com.yzb.wallet.openInterface.WalletBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gengqiyun on 2016/6/20.
 * 密码输入键盘对话框；
 */
public class IntegralInputDialogFragment extends DialogFragment {

    private static IntegralInputDialogFragment dialogFragment;
    private GridView gvInputPanel;
    private Activity activity;
    private EditText etMoney;
    private TextView tvConfirm;

    private IntegralShareLinkman integralShareLinkman;

    private long integralNum;

    private TextView tvIntegralNum;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public static IntegralInputDialogFragment getInstance() {

        synchronized (IntegralInputDialogFragment.class) {
            if (dialogFragment == null) {
                dialogFragment = new IntegralInputDialogFragment();
            }
        }
        return dialogFragment;
    }

    public void setIntegralShareLinkman( IntegralShareLinkman integralShareLinkman, long integralNum ){

        this.integralShareLinkman = integralShareLinkman;

        this.integralNum = integralNum;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_integral_input_dialog, null);
        dialog.setContentView(view);

        initView(view);
        Window window = dialog.getWindow();
        //底部弹出动画;
        window.setWindowAnimations(R.style.dialog_bottom_animation_style);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        layoutParams.width = CommonUtil.getScreenWidth(getActivity());
//        layoutParams.height = CommonUtil.getScreenHeight(getActivity()) * 2 / 5;
        window.setAttributes(layoutParams);

        return dialog;
    }

    private void initView(View view) {
        if (view != null) {
            etMoney = (EditText) view.findViewById(R.id.etMoney);

            tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);

            tvIntegralNum = (TextView) view.findViewById(R.id.tvIntegralNum);

            tvIntegralNum.setText(integralNum+"");

           AppUtils.hideSoftShowCursor(activity,etMoney);

            gvInputPanel = (GridView) view.findViewById(R.id.gv_input_panel);
            gvInputPanel.setAdapter(new GvInputAdater(getActivity()));
            gvInputPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String callBackStr = null;

                    String etTmepStr = etMoney.getText().toString().trim();

                    switch (position) {
                        case 9:  // 小数点；


                            break;
                        case 10: // 数字0；
                            callBackStr = "0";
                            etTmepStr = etTmepStr + callBackStr;
                            break;
                        case 11: //删除；
                            int length = etTmepStr.length();

                            if (length > 0) {

                                etTmepStr = etTmepStr.substring(0, length - 1);
                            }


                            break;
                        default: //1-9数字键；
                            callBackStr = String.valueOf(position + 1);

                            etTmepStr = etTmepStr + callBackStr;
                            break;
                    }

                    etMoney.setText(etTmepStr);
                    etMoney.setSelection(etTmepStr.length());


                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String inputNumber = etMoney.getText().toString().trim();

                    if(TextUtils.isEmpty(inputNumber)){
                        ToastUtil.i(activity,getString(R.string.hini_input_red_bao_money));
                        return;

                    }

                  final int temp = Integer.parseInt(inputNumber);

                    if(temp<=0){
                        ToastUtil.i(activity,"分享的积分数必须大于0");
                        return;

                    }

                    if( integralShareLinkman == null){

                        return;
                    }

                    if(temp <= integralNum){

                        UserBean userBean = UserManager.getInstance().getUserBean();

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customerId", userBean.getAmountAccount());
                        params.put("point", ""+temp);
                        params.put("summary", "积分分享");
                        params.put("sessionId", AppConstant.sessionId);
                        params.put("UUID", AppConstant.UUID);

                        PayPointHandle payHandle = new PayPointHandle(activity);
                        payHandle.pay(params);
                        payHandle.setCallBack(new WalletBackListener() {
                            @Override
                            public void setSuccess(String RESULT_CODE) {
                            }

                            @Override
                            public void setSuccess(Map<String, String> resultMap, String RESULT_CODE) {
                                System.out.println("======积分付款=======>" + RESULT_CODE);

                                UserManager.getInstance().subtractionShareNumber(temp);


                                ProgressDialogUtil.getInstance().showProgressDialog(activity, false);

                                PointShareRequest request = new PointShareRequest(integralShareLinkman.getNickName(),integralShareLinkman.getMobile(),integralShareLinkman.getRelationship(),temp+"",new DataCallBack() {
                                    @Override
                                    public void requestSuccess(Object o, int type)
                                    {

                                        ProgressDialogUtil.getInstance().closeProgressDialog();

                                        dismiss();

                                        ToastUtil.i(activity, "成功分享");
                                    }

                                    @Override
                                    public void requestFailedDataCall(Object o, int type)
                                    {
                                        ProgressDialogUtil.getInstance().closeProgressDialog();

                                        ToastUtil.i(activity, "成功失败");
                                    }


                                });

                                request.execute();


                            }

                            @Override
                            public void setError(String RESULT_CODE, String ERROR_MSG) {
                                System.out.println("======积分付款=======>" + RESULT_CODE);
                                ToastUtil.i(activity, "成功失败");

                            }
                        });



                    }else{

                        ToastUtil.i(activity,getString(R.string.integral_jine_no));
                    }

                }
            });
        }
    }

    private class GvInputAdater extends BaseAdapter {
        private final LayoutInflater inflater;
        private int[] imgResIds = {R.mipmap.key_number_01, R.mipmap.key_number_02, R.mipmap
                .key_number_03, R.mipmap.key_number_04, R.mipmap.key_number_05, R.mipmap
                .key_number_06, R.mipmap.key_number_07, R.mipmap.key_number_08, R.mipmap
                .key_number_09, R.mipmap.key_point, R.mipmap.key_number_00, R.mipmap
                .key_del};

        public GvInputAdater(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imgResIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gv_item_input_panel, parent, false);
            }
            ImageView iv_key = (ImageView) convertView.findViewById(R.id.iv_key);
            iv_key.setBackgroundResource(imgResIds[position]);

            return convertView;
        }
    }

}
