package com.yzb.wallet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzb.wallet.R;
import com.yzb.wallet.openInterface.WalletBackListener;
import com.yzb.wallet.util.StringUtil;


public class BaseActivity extends AppCompatActivity {

    /**
     * header设置，中间为文字
     */
    protected void setHeader(String midTitle) {
        this.setHeaderContent(0, null, midTitle, 0, null);
    }

    /**
     * header设置，左侧，中间文字，无右侧
     */
    protected void setHeader(String leftTitle, String midTitle) {
        this.setHeaderContent(0, leftTitle, midTitle, 0, null);
    }

    /**
     * header设置，左侧图片，中间文字，无右侧
     */
    protected void setHeader(int leftResource, String midTitle) {
        this.setHeaderContent(leftResource, null, midTitle, 0, null);
    }

    /**
     * header设置，左侧，中间及右侧文字
     */
    protected void setHeader(String leftTitle, String midTitle, String rightTitle) {
        this.setHeaderContent(0, leftTitle, midTitle, 0, rightTitle);
    }

    /**
     * header设置，左侧图片，中间及右侧文字
     */
    protected void setHeader(int leftResource, String midTitle, String rightTitle) {
        this.setHeaderContent(leftResource, null, midTitle, 0, rightTitle);
    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(int leftResource, String midTitle, int rightResource) {
        this.setHeaderContent(leftResource, null, midTitle, rightResource, null);
    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(int leftResource, String leftTitle, String midTitle, int rightResource, int rightResource2, String rightTitle) {
        this.setHeaderContent(leftResource, leftTitle, midTitle, rightResource, rightResource2, rightTitle);
    }

    /**
     * header设置，左侧图片，中间文字，右侧图片
     */
    protected void setHeader(int leftResource, String leftTitle, int leftResource2, String midTitle, int midResource, int rightResource, int rightResource2, String rightTitle) {
        this.setHeaderContent(leftResource, leftTitle, leftResource2, midTitle, midResource, rightResource, rightResource2, rightTitle);
    }

    /**
     * header设置内容
     */
    private void setHeaderContent(int leftResource, String leftTitle,
                                  String midTitle, int rightResource, String rightTitle) {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        if (leftResource != 0) {
            headerLeftImage.setImageResource(leftResource);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) findViewById(R.id.headerLeftText);
        if (!StringUtil.isEmpty(leftTitle)) {// 文字
            headerLeftText.setText(leftTitle);
            headerLeftText.setTextSize(16);
            headerLeftText.setTextColor(getResources().getColor(R.color.white));
        } else {
            headerLeftText.setVisibility(View.GONE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        if (!StringUtil.isEmpty(midTitle)) {
            headerTitle.setText(midTitle);
            headerTitle.setTextSize(23);
            headerTitle.setTextColor(getResources().getColor(R.color.white));
        } else {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        if (rightResource != 0) {
            headerRightImage.setImageResource(rightResource);
            headerRightImage.setVisibility(View.VISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) findViewById(R.id.headerRightText);
        if (!StringUtil.isEmpty(rightTitle)) {
            headerRightText.setText(rightTitle);
            headerRightText.setTextSize(16);
            headerRightText.setTextColor(getResources().getColor(R.color.white));
        } else {
            headerRightText.setVisibility(View.GONE);
        }

    }

    /**
     * header设置内容
     */
    private void setHeaderContent(int leftResource, String leftTitle,
                                  String midTitle, int rightResource, int rightResource2, String rightTitle) {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        if (leftResource != 0) {
            headerLeftImage.setImageResource(leftResource);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) findViewById(R.id.headerLeftText);
        if (!StringUtil.isEmpty(leftTitle)) {// 文字
            headerLeftText.setText(leftTitle);
        } else {
            headerLeftText.setVisibility(View.GONE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        if (!StringUtil.isEmpty(midTitle)) {
            headerTitle.setText(midTitle);
        } else {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        if (rightResource != 0) {
            headerRightImage.setImageResource(rightResource);
            headerRightImage.setVisibility(View.VISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) findViewById(R.id.headerRightText);
        if (!StringUtil.isEmpty(rightTitle)) {
            headerRightText.setText(rightTitle);
        } else {
            headerRightText.setVisibility(View.GONE);
        }

    }

    /**
     * header设置内容
     */
    private void setHeaderContent(int leftResource, String leftTitle, int leftResource2,
                                  String midTitle, int midResource, int rightResource, int rightResource2, String rightTitle) {

        // 左侧图片设置(默认不显示)
        ImageView headerLeftImage = (ImageView) findViewById(R.id.headerLeftImage);
        if (leftResource != 0) {
            headerLeftImage.setImageResource(leftResource);
            headerLeftImage.setVisibility(View.VISIBLE);
        }

        // 左侧内容设置
        TextView headerLeftText = (TextView) findViewById(R.id.headerLeftText);
        if (!StringUtil.isEmpty(leftTitle)) {// 文字
            headerLeftText.setText(leftTitle);
        } else {
            headerLeftText.setVisibility(View.GONE);
        }

        // 中间内容设置
        TextView headerTitle = (TextView) findViewById(R.id.headerTitle);
        if (!StringUtil.isEmpty(midTitle)) {
            headerTitle.setText(midTitle);
        } else {
            headerTitle.setVisibility(View.INVISIBLE);
        }

        // 右侧图片设置(默认不显示)
        ImageView headerRightImage = (ImageView) findViewById(R.id.headerRightImage);
        if (rightResource != 0) {
            headerRightImage.setImageResource(rightResource);
            headerRightImage.setVisibility(View.VISIBLE);
        }

        //右侧内容设置
        TextView headerRightText = (TextView) findViewById(R.id.headerRightText);
        if (!StringUtil.isEmpty(rightTitle)) {
            headerRightText.setText(rightTitle);
        } else {
            headerRightText.setVisibility(View.GONE);
        }

    }


    /**
     * 标题左侧返回
     */
    public void switchContentLeft(final int resultCode) {

        LinearLayout headerLeft = (LinearLayout) findViewById(R.id.headerLeft);

        // 返回首页
        headerLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(resultCode);
                finish();
            }
        });
    }

    /**
     * 标题右侧返回
     */
    public void switchContentRight(final int resultCode) {

        LinearLayout headerRight = (LinearLayout) findViewById(R.id.headerRight);

        // 返回首页
        headerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(resultCode);
                finish();
            }
        });
    }

    /**
     * 点击除EditText外隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 禁止字体随系统字体变化
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    /**
     * 检查当前网络是否可用
     */
    public boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
