package com.yzb.card.king.ui.discount.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.my.activity.CouponMoreShopsActivity;
import com.yzb.card.king.ui.user.LoginActivity;
import com.yzb.card.king.util.Utils;

/**
 * Created by 玉兵 on 2017/10/30.
 */

public class AppCouponSucessDialgFragment extends BaseDialogFragment implements View.OnClickListener
{
    private static AppCouponSucessDialgFragment dialogFragment;


    private String je = "";


    private String flag = ""; // 1代金券；2优惠券；

    private  String couponType = "";//1满减券；2折扣券；3抵扣券


    public static AppCouponSucessDialgFragment getInstance(String je, String flag,String couponType)
    {

        dialogFragment = new AppCouponSucessDialgFragment();
        Bundle args = new Bundle();
        args.putString("je", je);
        args.putString("flag", flag);
        args.putString("couponType", couponType);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            je = getArguments().getString("je");
            flag = getArguments().getString("flag");

            couponType = getArguments().getString("couponType");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    protected int getGravity()
    {
        return Gravity.CENTER;
    }

    @Override
    protected int getWindowAnimation()
    {
        return 0;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_lqsuccess_dialog;
    }

    @Override
    protected void initView(View view)
    {
        if (view != null)
        {
            StringBuffer sb = new StringBuffer();

            sb.append("恭喜您获得" );

            if("1".equals(couponType)){//1满减券；

                sb.append(Utils.subZeroAndDot(je) + "元");

            }else  if("2".equals(couponType)){//2折扣券；

                String zhekouStr = Utils.handNumberToString(Float.parseFloat(je));

                sb.append(zhekouStr + "折");

            }else   if("3".equals(couponType)){//3抵扣券；

                sb.append(Utils.subZeroAndDot(je) + "元");

            }

            if("1".equals(flag)){// 1代金券；

                sb.append("代金券");

            }else  if("2".equals(flag)){//2优惠券；

                sb.append("优惠券");
            }


            TextView label = (TextView) view.findViewById(R.id.label);
            label.setText(sb);


            view.setOnClickListener(this);

            view.findViewById(R.id.tvChakan).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v)
    {
        this.dismiss();

        if(v.getId() ==R.id. tvChakan){
            Intent intentR = new Intent(getContext(), LoginActivity.class);
            startActivity(intentR);

        }
    }
}
