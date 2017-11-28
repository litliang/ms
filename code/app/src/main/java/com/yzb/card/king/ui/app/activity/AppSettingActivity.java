package com.yzb.card.king.ui.app.activity;

import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.app.bean.AppSettingItem;
import com.yzb.card.king.ui.app.bean.NoticeMenu;
import com.yzb.card.king.ui.app.presenter.AppSetPresenter;
import com.yzb.card.king.ui.app.view.AppSetView;
import com.yzb.card.king.ui.appwidget.SlideButton;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.my.activity.WalletActivity;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.other.adapter.AbsBaseListAdapter;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.UiUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * App设置的辅助功能
 */
public class AppSettingActivity extends BaseActivity implements View.OnClickListener, AppSetView
{
    private SlideButton sbVoice;
    private SlideButton sbNotice;
    private ListView listView;
    private AppSettingAdapterAbs adapter;
    private List<AppSettingItem> dataList = new ArrayList<>();
    private TextView tvCacheSize;
    private TextView tvClearCache;
    private String status;
    private AppSetPresenter presenter;
    private long cacheSize = 0;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        if (presenter == null)
        {
            presenter = new AppSetPresenter(this);
        }
        initView();
        initData();
    }

    private void initView()
    {
        setContentView(R.layout.activity_app_setting_acitivity);
        setTitleNmae("设置");
        sbVoice = (SlideButton) findViewById(R.id.sbVoice);
        sbNotice = (SlideButton) findViewById(R.id.sbNotice);

        sbNotice.setBgIconResId(R.mipmap.bg_setting_blue);
        listView = (ListView) findViewById(R.id.listView);
        tvCacheSize = (TextView) findViewById(R.id.tvCacheSize);
        tvClearCache = (TextView) findViewById(R.id.tvClearCache);
        sbVoice.setToggleState(SlideButton.ToggleState.open);
        sbNotice.setToggleState(SlideButton.ToggleState.open);
        tvClearCache.setOnClickListener(this);
        sbVoice.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener()
        {
            @Override
            public void onToggleStateChange(SlideButton.ToggleState state)
            {
                if (state == SlideButton.ToggleState.close)
                {
                    JPushInterface.setSilenceTime(getApplicationContext(), 0, 1, 23, 59);//在00:01~23:59 静默
                    SharePrefUtil.saveToSp(AppSettingActivity.this, "voice", "off");
                } else
                {
                    JPushInterface.setSilenceTime(getApplicationContext(), 0, 1, 0, 2);//在00:01~00:02 静默
                    SharePrefUtil.saveToSp(AppSettingActivity.this, "voice", "on");
                }
            }
        });
        sbNotice.setOnToggleStateChangeListener(new SlideButton.OnToggleStateChangeListener()
        {
            @Override
            public void onToggleStateChange(SlideButton.ToggleState state)
            {
                if (state == SlideButton.ToggleState.open)
                {
                    listView.setVisibility(View.GONE);
                    SharePrefUtil.saveToSp(AppSettingActivity.this, "notice", "on");
                    status = "1";
                } else
                {
                    listView.setVisibility(View.GONE);
                    SharePrefUtil.saveToSp(AppSettingActivity.this, "notice", "off");
                    status = "0";
                }
            }
        });
        adapter = new AppSettingAdapterAbs(dataList);
        listView.setAdapter(adapter);

        findViewById(R.id.llPayPasswordSet).setOnClickListener(this);

        findViewById(R.id.llAppPasswordSetting).setOnClickListener(this);
    }

    private void setToggelState(String status)
    {
        if (status != null)
        {
            if ("1".equals(status))
            {//开
                listView.setVisibility(View.GONE);
                SharePrefUtil.saveToSp(AppSettingActivity.this, "notice", "on");
            } else if ("0".equals(status))
            {//关
                listView.setVisibility(View.GONE);
                SharePrefUtil.saveToSp(AppSettingActivity.this, "notice", "off");
            }
        }
    }

    private void initData()
    {
        String voice = SharePrefUtil.getValueFromSp(AppSettingActivity.this, "voice", "on");
        String notice = SharePrefUtil.getValueFromSp(AppSettingActivity.this, "notice", "on");
        if ("off".equals(voice))
        {
            sbVoice.setToggleState(SlideButton.ToggleState.close);
        } else
        {
            sbVoice.setToggleState(SlideButton.ToggleState.open);
        }
        if ("off".equals(notice))
        {
            listView.setVisibility(View.GONE);
            sbNotice.setToggleState(SlideButton.ToggleState.close);
            status = "0";
        } else
        {
            listView.setVisibility(View.GONE);
            sbNotice.setToggleState(SlideButton.ToggleState.open);
            status = "1";
        }
        getPackageInfo(getPackageName());

        getDataList();
    }

    private void getDataList()
    {
        presenter.loadNotice();
    }


    //获取应用程序信息
    public void getPackageInfo(String pkg)
    {
        PackageManager pm = getPackageManager();
        try
        {
            Method getPackageSizeInfo = pm.getClass()
                    .getMethod("getPackageSizeInfo", String.class,
                            IPackageStatsObserver.class);
            getPackageSizeInfo.invoke(pm, pkg,
                    new PkgSizeObserver());
        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void noticeLoadSuccess(NoticeMenu o)
    {
        setToggelState(o.status);
        dataList.addAll(o.remindList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void noticeLoadFail(Object o)
    {

    }

    class PkgSizeObserver extends IPackageStatsObserver.Stub
    {
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
        {
            cacheSize = pStats.cacheSize + pStats.dataSize;
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    tvCacheSize.setText(Formatter.formatFileSize(AppSettingActivity.this, cacheSize));
                }
            });
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvClearCache:
//                DataCleanManager.cleanApplicationData(AppSettingActivity.this);
//                getPackageInfo(getPackageName());
                tvCacheSize.setText("已清除");
                break;

            case R.id.llPayPasswordSet://支付密码设置
                startActivity(new Intent(this, PaySettingActivity.class));
                break;

            case R.id.llAppPasswordSetting://应用密码设置
                startActivity(new Intent(this, PwdSettingActivity.class));
                break;
        }
    }

    class AppSettingAdapterAbs extends AbsBaseListAdapter<AppSettingItem>
    {

        public AppSettingAdapterAbs(List<AppSettingItem> list)
        {
            super(list);
        }

        @Override
        protected Holder getHolder(int position)
        {
            return new AppSettingHolder();
        }
    }

    class AppSettingHolder extends Holder<AppSettingItem> implements View.OnClickListener
    {
        private View view;
        private TextView tvName;
        private ImageView ivReduce;
        private TextView tvDay;
        private ImageView ivAdd;
        private AppSettingItem item;

        @Override
        public View initView()
        {
            view = UiUtils.inflate(R.layout.item_settings_app_list);
            tvName = (TextView) view.findViewById(R.id.tvName);
            ivReduce = (ImageView) view.findViewById(R.id.ivReduce);
            tvDay = (TextView) view.findViewById(R.id.tvDay);
            ivAdd = (ImageView) view.findViewById(R.id.ivAdd);

            ivAdd.setOnClickListener(this);
            ivReduce.setOnClickListener(this);
            return view;
        }

        @Override
        public void refreshView(AppSettingItem data)
        {
            this.item = data;
            tvDay.setText(String.valueOf(data.preDay));
            tvName.setText(data.getName());
        }

        @Override
        public void onClick(View v)
        {

            switch (v.getId())
            {
                case R.id.ivReduce:
                    item.preDay--;
                    break;
                case R.id.ivAdd:
                    item.preDay++;
                    break;
            }
            if (item.preDay < 1) item.preDay = 1;
            if (item.preDay > 7) item.preDay = 7;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy()
    {
        save();
        super.onDestroy();
    }

    private void save()
    {
        presenter.save(status, dataList);
    }
}
