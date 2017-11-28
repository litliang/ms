package com.yzb.card.king.ui.app.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.activity.SafeVerifyOldPayPwdActivity;
import com.yzb.card.king.ui.credit.interfaces.OnItemClickListener;
import com.yzb.card.king.util.CommonUtil;

/**
 * Created by gengqiyun on 2016/6/20.
 * 密码输入键盘对话框；
 */
public class PwdInputDialogFragment extends DialogFragment
{
    private static PwdInputDialogFragment dialogFragment;
    private GridView gvInputPanel;
    private Activity activity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    public static PwdInputDialogFragment getInstance(String arg1, String arg2)
    {

        synchronized (PwdInputDialogFragment.class)
        {
            if (dialogFragment == null)
            {
                dialogFragment = new PwdInputDialogFragment();
            }
        }
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), R.style.dialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_pwd_input_dialog, null);
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

    private void initView(View view)
    {
        if (view != null)
        {
            gvInputPanel = (GridView) view.findViewById(R.id.gv_input_panel);
            gvInputPanel.setAdapter(new GvInputAdater(getActivity()));
            gvInputPanel.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    String callBackStr = null;

                    switch (position)
                    {
                        case 9:  // 小数点；
                            callBackStr = ".";
                            break;
                        case 10: // 数字0；
                            callBackStr = "0";
                            break;
                        case 11: //删除；
                            break;
                        default: //1-9数字键；
                            callBackStr = String.valueOf(position + 1);
                            break;
                    }
                    if (activity != null && activity instanceof SafeVerifyOldPayPwdActivity)
                    {
                        ((IInputDialog) activity).updatePwd(callBackStr);
                    } else
                    {
                        if (listener != null)
                        {
                            listener.onItemClick(callBackStr);
                        }
                    }
                }
            });
        }
    }

    private OnItemClickListener<String> listener;

    public void setListener(OnItemClickListener<String> listener)
    {
        this.listener = listener;
    }

    private class GvInputAdater extends BaseAdapter
    {
        private final LayoutInflater inflater;
        private int[] imgResIds = {R.mipmap.key_number_01, R.mipmap.key_number_02, R.mipmap
                .key_number_03, R.mipmap.key_number_04, R.mipmap.key_number_05, R.mipmap
                .key_number_06, R.mipmap.key_number_07, R.mipmap.key_number_08, R.mipmap
                .key_number_09, R.mipmap.key_point, R.mipmap.key_number_00, R.mipmap
                .key_del};

        public GvInputAdater(Context context)
        {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount()
        {
            return imgResIds.length;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.gv_item_input_panel, parent, false);
            }
            ImageView iv_key = (ImageView) convertView.findViewById(R.id.iv_key);
            iv_key.setBackgroundResource(imgResIds[position]);
            return convertView;
        }
    }

}
