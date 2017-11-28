package com.yzb.card.king.ui.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.bean.user.UserBean;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.adapter.MemberLevelAdapterAbs;
import com.yzb.card.king.ui.app.bean.Level;
import com.yzb.card.king.ui.app.bean.LevelCondition;
import com.yzb.card.king.ui.app.bean.LevelGridItem;
import com.yzb.card.king.ui.app.bean.LevelPageData;
import com.yzb.card.king.ui.appwidget.MemberSeekBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员等级
 */
public class MemberLevelActivity extends BaseActivity
{
    private static final int LEVEL_SIZE = 4;

    private GridView gridView;
    private List<LevelGridItem> dataList = new ArrayList<>();
    private MemberLevelAdapterAbs adapter;
    private MemberSeekBar seekBar;
    private ImageView ivPhoto;
    private TextView tvName;
    private ImageView[] levels = new ImageView[LEVEL_SIZE];
    private TextView[] texts = new TextView[LEVEL_SIZE];
    private TextView[] lines = new TextView[LEVEL_SIZE];
    private TextView tvStandard;
    private List<List<LevelGridItem>> privilegeList;
    private List<Level> levelList;
    private List<List<LevelCondition>> conditionList;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initListener()
    {
        for (int i = 0; i < LEVEL_SIZE; i++)
        {
            levels[i].setOnClickListener(new OnLevelSelected(i));
        }
    }

    private class OnLevelSelected implements View.OnClickListener
    {

        private final int position;

        public OnLevelSelected(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View v)
        {
            Level level = (Level) v.getTag();
            setLevelInfo(level);
            setSelected(position);
        }
    }

    private void setLevelInfo(Level level)
    {
        if (level != null)
        {
            setConditions(level.getConditionList());
            setGridView(level.getPrivilegeList(),level.getLevelName());
        }
    }

    private void initView()
    {
        setContentView(R.layout.activity_member_level);
        setTitleNmae(getString(R.string.integral_hui_yuan_rank2));

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new MemberLevelAdapterAbs(dataList);
        gridView.setAdapter(adapter);

        seekBar = (MemberSeekBar) findViewById(R.id.seekBar);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        tvName = (TextView) findViewById(R.id.tvName);

        tvStandard = (TextView) findViewById(R.id.tvStandard);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        levels[0] = (ImageView) findViewById(R.id.tvLevel1);
        levels[1] = (ImageView) findViewById(R.id.tvLevel2);
        levels[2] = (ImageView) findViewById(R.id.tvLevel3);
        levels[3] = (ImageView) findViewById(R.id.tvLevel4);

        texts[0] = (TextView) findViewById(R.id.text1);
        texts[1] = (TextView) findViewById(R.id.text2);
        texts[2] = (TextView) findViewById(R.id.text3);
        texts[3] = (TextView) findViewById(R.id.text4);

        lines[0] = (TextView) findViewById(R.id.line1);
        lines[1] = (TextView) findViewById(R.id.line2);
        lines[2] = (TextView) findViewById(R.id.line3);
        lines[3] = (TextView) findViewById(R.id.line4);
    }

    private void initData()
    {
        UserManager userManager = UserManager.getInstance();
        UserBean userBean = userManager.getUserBean();
        x.image().bind(ivPhoto, userBean.getPic(),
                XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(40),
                        ImageView.ScaleType.FIT_XY));

        tvName.setText(userBean.getNickName());
        loadData();
    }

    private void loadData()
    {
        SimpleRequest<LevelPageData> request = new SimpleRequest<LevelPageData>("CustomerLevelDetail")
        {
            @Override
            protected LevelPageData parseData(String data)
            {
                return JSON.parseObject(data, LevelPageData.class);
            }
        };

        Map<String, Object> param = new HashMap<>();
        param.put("levelId", getLevelid());
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<LevelPageData>()
        {
            @Override
            public void onSuccess(LevelPageData data)
            {
                seekBar.setProgress(data.getPercent());
                levelList = data.getLevelList();
                setLevel(levelList);
                Level level = getCurrentLevel(levelList);
                setLevelInfo(level);
            }

            @Override
            public void onFail(Error error)
            {
                UiUtils.shortToast(error.getError());
            }
        });
    }

    private Level getCurrentLevel(List<Level> levelList)
    {
        for (int i = 0; i < levelList.size(); i++)
        {
            if (getLevelid() == levelList.get(i).getLevelId())
            {
                setSelected(i);
                return levelList.get(i);
            }
        }
        return null;
    }

    private void setSelected(int position)
    {
        for (int i = 0; i < lines.length; i++)
        {
            lines[i].setEnabled(position == i);
        }
    }

    private long getLevelid()
    {
        return UserManager.getInstance().getUserBean().getLevelInfo().getLevelid();
    }

    private void setConditions(List<LevelCondition> conditionList)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (conditionList != null && conditionList.size() > 0)
        {
            for (int i = 0; i < conditionList.size(); i++)
            {
                stringBuilder.append(conditionList.get(i).getConditionDesc());
                if (i != conditionList.size() - 1)
                {
                    stringBuilder.append("\n");
                }
            }
        }
        tvStandard.setText(stringBuilder.toString());
    }

    private void setGridView(List<LevelGridItem> privilegeList,String title)
    {
        tvTitle.setText(title+"特权");
        dataList.clear();
        if (privilegeList != null || privilegeList.size() != 0)
        {
            dataList.addAll(privilegeList);
        }
        adapter.notifyDataSetChanged();
    }

    private void setLevel(List<Level> levelList)
    {
        if (levelList != null && levelList.size() == LEVEL_SIZE)
        {
            for (int i = 0; i < LEVEL_SIZE; i++)
            {
                levels[i].setTag(levelList.get(i));
                x.image().bind(levels[i], ServiceDispatcher.getImageUrl(levelList.get(i).getLevelLogo()));
                texts[i].setText(levelList.get(i).getLevelName());
            }
        } else
        {
            UiUtils.shortToast("等级信息加载失败");
        }
    }
}