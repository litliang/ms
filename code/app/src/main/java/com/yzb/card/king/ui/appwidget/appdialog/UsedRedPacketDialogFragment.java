package com.yzb.card.king.ui.appwidget.appdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.ToastUtil;

import java.text.NumberFormat;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/9/1
 * 描  述：使用红包
 */
public class UsedRedPacketDialogFragment extends DialogFragment {

    public static final int RED_PACKET_CODE = 10;

    public static final int GIFT_CARD_CODE = 11;

    private static UsedRedPacketDialogFragment dialogFragment;
    private GridView gvInputPanel;
    private Activity activity;
    private EditText etMoney;
    private TextView tvConfirm;
    private TextView tvRedPacketNum;
    private TextView tvTitleName;
    private double redPackegNum = 0;

    private Handler dataHandler;

    private int moneyType ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public static UsedRedPacketDialogFragment getInstance() {

        synchronized (UsedRedPacketDialogFragment.class) {
            if (dialogFragment == null) {
                dialogFragment = new UsedRedPacketDialogFragment();
            }
        }
        return dialogFragment;
    }

    public void setRedPackegNum(  double redPackegNum ){

        this.redPackegNum = redPackegNum;


    }

    private String titleName ="";

    /**
     * 设置标题名
     * @param titleName
     * @param moneyType
     */
    public void setTvTitleName(String titleName,int moneyType){

        this.titleName = titleName;

        this.moneyType = moneyType;
    }


    public void setDataHandler(Handler dataHandler){

        this.dataHandler = dataHandler;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_redpacket_use_dialog, null);
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

            tvRedPacketNum = (TextView) view.findViewById(R.id.tvRedPacketNum);

            tvTitleName = (TextView) view.findViewById(R.id.tvTitleName);

            tvTitleName.setText(titleName);

            NumberFormat numberFormat = NumberFormat.getNumberInstance();

            numberFormat.setGroupingUsed(false);

            tvRedPacketNum.setText(numberFormat.format(redPackegNum));

            AppUtils.hideSoftShowCursor(activity,etMoney);

            gvInputPanel = (GridView) view.findViewById(R.id.gv_input_panel);

            gvInputPanel.setAdapter(new UsedRedPacketDialogFragment.GvInputAdater(getActivity()));

            gvInputPanel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String callBackStr = null;

                    String etTmepStr = etMoney.getText().toString().trim();

                    switch (position) {
                        case 9:  // 小数点按键；

                            if(!TextUtils.isEmpty(etTmepStr)){
                                //检测是否有小数点以及第一个是否是小数点
                                int index = etTmepStr.indexOf(".");

                                if(index == -1){

                                    callBackStr = ".";

                                    etTmepStr = etTmepStr + callBackStr;

                                }
                            }

                            break;
                        case 10: // 数字0按键；
                            /**
                             * 0 不能连续存在 数字头部
                             */
                            if(!TextUtils.isEmpty(etTmepStr)){

                                if(!"0".equals(etTmepStr)){

                                    /**
                                     * 检查数字0
                                     */
                                    callBackStr = "0";

                                    etTmepStr = etTmepStr + callBackStr;
                                }

                            }else {

                                /**
                                 * 检查数字0
                                 */
                                callBackStr = "0";

                                etTmepStr = etTmepStr + callBackStr;

                            }

                            break;
                        case 11: //删除按键；
                            int length = etTmepStr.length();

                            if (length > 0) {

                                etTmepStr = etTmepStr.substring(0, length - 1);
                            }

                            break;
                        default: //1-9数字键；

                            if(!"0".equals(etTmepStr)){

                                callBackStr = String.valueOf(position + 1);

                                etTmepStr = etTmepStr + callBackStr;
                            }

                            break;
                    }

                    if(TextUtils.isEmpty(etTmepStr)){

                        etMoney.setText(etTmepStr);

                    }else {

                        double moenDouble = Double.parseDouble(etTmepStr);

                        if( moenDouble <= redPackegNum){
                            etMoney.setText(etTmepStr);

                            etMoney.setSelection(etTmepStr.length());
                        }else {

                            ToastUtil.i(activity,"你的红包剩余金额："+tvRedPacketNum.getText().toString());

                        }
                    }



                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String inputNumber = etMoney.getText().toString().trim();

                    if(TextUtils.isEmpty(inputNumber)){
                        ToastUtil.i(activity,"请输入红包金额");
                        return;

                    }


                    if( dataHandler != null){

                        Message message = dataHandler.obtainMessage();

                        message.obj = inputNumber;

                        message.what = moneyType;

                        dataHandler.sendMessage(message);
                    }

                    dismiss();
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

