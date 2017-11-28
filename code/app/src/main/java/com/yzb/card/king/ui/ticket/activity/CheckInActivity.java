package com.yzb.card.king.ui.ticket.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.fragment.PlaneHkSelectDialogFragment;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;

import java.util.Map;

public class CheckInActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout hkgsRl;

    private LinearLayout ivBack;

    private TextView txHkgs;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        initView();
    }

    private void initView()
    {
        hkgsRl= (RelativeLayout) findViewById(R.id.hkgs_rl);
        hkgsRl.setOnClickListener(this);

        txHkgs= (TextView) findViewById(R.id.tx_hkgs);

        ivBack= (LinearLayout) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.hkgs_rl: //航空公司
                showPlaneSelectDialog();
                break;
            case R.id.iv_back://关闭
                finish();
                break;

        }
    }
    /**
     * 航班选择dialog；
     */
    private void showPlaneSelectDialog()
    {
        String cr = txHkgs.getText().toString();
        PlaneHkSelectDialogFragment.getInstance(cr)
                .setCallBack(new IDialogCallBack()
                {
                    @Override
                    public void dialogCallBack(Object... obj)
                    {
                        if (obj != null && obj[0] instanceof Map)
                        {
                            Map<String, String> backData = (Map<String, String>) obj[0];
                            txHkgs.setText(backData.get("cr"));
                        }
                    }

                })
                .show(getSupportFragmentManager(), "");
    }
}
