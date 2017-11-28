package com.yzb.card.king.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.common.Error;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.ui.appwidget.SideBar;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.credit.bean.Bank;
import com.yzb.card.king.ui.credit.holder.AllBankHolder;
import com.yzb.card.king.ui.my.bean.SelectBankGroup;
import com.yzb.card.king.ui.other.listeners.SimpleWatcher;
import com.yzb.card.king.util.UiUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：选择开户银行
 * 作者：殷曙光
 * 日期：2016/12/29 10:25
 */
@ContentView(R.layout.activity_select_bank)
public class SelectBankActivity extends BaseActivity
{

    @ViewInject(R.id.etSearch)
    private EditText etSearch;

    @ViewInject(R.id.ivClear)
    private ImageView ivClear;

    @ViewInject(R.id.expandListView)
    private ExpandableListView expandListView;

    @ViewInject(R.id.sideBar)
    private SideBar sideBar;

    @ViewInject(R.id.rlContent)
    private View rlContent;

    @ViewInject(R.id.llMsg)
    private View llMsg;

    @ViewInject(R.id.tvMsg)
    private TextView tvMsg;

    private SelectBankAdapter adapter;
    private AbsListView.LayoutParams parentLayout = new AbsListView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dp2px(39));

    private List<SelectBankGroup> groupList = new ArrayList<>();
    private List<String> letters = new ArrayList<>();
    private SelectBankGroup hotGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        defaultFlag = true;
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        ivClear.setVisibility(View.GONE);
        llMsg.setVisibility(View.GONE);
        rlContent.setVisibility(View.GONE);
        sideBar.setTextColor(UiUtils.getColor(R.color.user_center_black));
    }

    private void initListener()
    {
        etSearch.addTextChangedListener(new SimpleWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (s.length() > 0)
                {
                    ivClear.setVisibility(View.VISIBLE);
                    sideBar.setVisibility(View.GONE);
                    search(s.toString());
                } else
                {
                    ivClear.setVisibility(View.GONE);
                    sideBar.setVisibility(View.VISIBLE);
                    search(null);
                }
            }
        });

        ivClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                etSearch.setText("");
            }
        });

        sideBar.setOnLetterTouchListener(new SideBar.OnLetterTouchListener()
        {
            @Override
            public void onLetterTouch(String letter, int position)
            {
                if (expandListView != null && position < letters.size())
                    expandListView.setSelectedGroup(position);
            }

            @Override
            public void onActionUp()
            {

            }
        });
    }

    private void search(final String s)
    {
        SimpleRequest<List<SelectBankGroup>> request =
                new SimpleRequest<List<SelectBankGroup>>("QueryAllBank")
                {
                    @Override
                    protected List<SelectBankGroup> parseData(String data)
                    {
                        return JSON.parseArray(data, SelectBankGroup.class);
                    }
                };
        Map<String, Object> param = new HashMap<>();
        param.put("name", s);
        request.setParam(param);
        request.sendRequestNew(new BaseCallBack<List<SelectBankGroup>>()
        {
            @Override
            public void onSuccess(List<SelectBankGroup> data)
            {
                groupList.clear();
                if (TextUtils.isEmpty(s))
                {
                    groupList.add(hotGroup);
                }
                groupList.addAll(data);
                setSideBar(groupList);
                adapter.notifyDataSetChanged();
                setResultView(true, s);
            }

            @Override
            public void onFail(Error error)
            {
                groupList.clear();
                adapter.notifyDataSetChanged();
                setResultView(false, s);
            }
        });
    }

    private void setResultView(boolean hasData, String searchText)
    {
        if (hasData)
        {
            llMsg.setVisibility(View.GONE);
            rlContent.setVisibility(View.VISIBLE);
        } else
        {
            llMsg.setVisibility(View.VISIBLE);
            rlContent.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(searchText))
        {
            tvMsg.setText("没有数据");
        } else
        {
            tvMsg.setText("没有找到，换一个试试~~");
        }
    }

    private void initData()
    {
        setDefaultHeader("选择开户银行");
        setAdapter();
        loadHotBank();
    }

    private void loadHotBank()
    {
        SimpleRequest<List<Bank>> request = new SimpleRequest<List<Bank>>("QueryHotBankService")
        {
            @Override
            protected List<Bank> parseData(String data)
            {
                return JSON.parseArray(data, Bank.class);
            }
        };
        request.sendRequestNew(new BaseCallBack<List<Bank>>()
        {
            @Override
            public void onSuccess(List<Bank> data)
            {
                hotGroup = new SelectBankGroup();
                hotGroup.setLetter("☆");
                hotGroup.setBankList(data);
                search(null);
            }

            @Override
            public void onFail(Error error)
            {
                search(null);
            }
        });
    }

    private void setSideBar(List<SelectBankGroup> groups)
    {
        if (groups != null && groups.size() > 0)
        {
            for (int i = 0; i < groups.size(); i++)
            {
                letters.add(groups.get(i).getLetter());
            }
//            letters.add(0, "☆");
            letters.add("※");
        }
        sideBar.setShowString(letters);
    }

    private void setAdapter()
    {

        adapter = new SelectBankAdapter();
        expandListView.setAdapter(adapter);
    }

    class SelectBankAdapter extends BaseExpandableListAdapter
    {
        @Override
        public int getGroupCount()
        {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return groupList.get(groupPosition).getBankList().size();
        }

        @Override
        public String getGroup(int groupPosition)
        {
            return groupList.get(groupPosition).getLetter();
        }

        @Override
        public Bank getChild(int groupPosition, int childPosition)
        {
            return groupList.get(groupPosition).getBankList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return 0;
        }

        @Override
        public boolean hasStableIds()
        {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
        {

            if (!isExpanded)
            {
                expandListView.expandGroup(groupPosition);//展开组
            }

            if (convertView == null)
            {
                TextView textView = new TextView(SelectBankActivity.this);
                textView.setLayoutParams(parentLayout);
                int padding = UiUtils.dp2px(16);
                textView.setPadding(padding, 0, padding, 0);
                textView.setTextColor(UiUtils.getColor(R.color.gray6));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setBackgroundColor(UiUtils.getColor(R.color.hotel_f5f5f5));
                convertView = textView;
            }
            TextView textView = (TextView) convertView;
            String letter = getGroup(groupPosition);
            if ("☆".equals(letter))
            {
                letter = "热门银行";
            }
            textView.setText(letter);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent)
        {
            AllBankHolder holder;
            if (convertView == null)
            {
                holder = new AllBankHolder();
                holder.setArrowVisibility(View.GONE);
            } else
            {
                holder = (AllBankHolder) convertView.getTag();
            }
            holder.refreshView(getChild(groupPosition, childPosition));
            holder.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent();
                    intent.putExtra("bank", getChild(groupPosition, childPosition));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return holder.getConvertView();
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }
    }
}
