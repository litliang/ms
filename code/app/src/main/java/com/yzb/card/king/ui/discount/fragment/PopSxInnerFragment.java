package com.yzb.card.king.ui.discount.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IMenuDataCallBack;
import com.yzb.card.king.ui.discount.bean.FilterBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 筛选popupwindow的外部容器Fragment；
 * 在xml布局文件中注册；
 * <p/>
 * created by gengqiyun on 2016.4.22
 */
public class PopSxInnerFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ListView listview1;
    private ListView listview2;
    private Sx1ListAdapter adapter1;
    private Sx2ListAdapter adapter2;
    private List<FilterBean> dataList1;
    private List<FilterBean> dataList2;
    private IMenuDataCallBack callBack;
    private LayoutInflater inflater;
    private TextView tv_hfmr; // 恢复默认；
    private TextView tv_qr; // 确认；

    public PopSxInnerFragment() {
    }

    public void setMenuDataCallBack(IMenuDataCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inflater = LayoutInflater.from(context);
    }

    public static PopSxInnerFragment newInstance(String param1, String param2) {
        PopSxInnerFragment fragment = new PopSxInnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pop_inner_sx, container, false);
        init(view);
        return view;
    }

    private void init(View contentView) {
        if (contentView == null) return;
        tv_hfmr = (TextView) contentView.findViewById(R.id.tv_hfmr);
        tv_hfmr.setOnClickListener(this);
        tv_qr = (TextView) contentView.findViewById(R.id.tv_qr);
        tv_qr.setOnClickListener(this);

        listview1 = (ListView) contentView.findViewById(R.id.listview1);
        adapter1 = new Sx1ListAdapter();
        listview1.setAdapter(adapter1);

        listview2 = (ListView) contentView.findViewById(R.id.listview2);
        adapter2 = new Sx2ListAdapter();
        listview2.setAdapter(adapter2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hfmr: // 恢复默认；
                if (dataList2 != null && dataList2.size() > 0) {
                    for (FilterBean itemBean : dataList1) {
                        if (itemBean.filterList != null) {
                            List<FilterBean> localBeans = itemBean.filterList;
                            for (int i = 0; i < localBeans.size(); i++) {
                                localBeans.get(i).isSelected = false;
                            }
                        }
                    }
                    adapter2.notifyDataSetChanged();
                }
                break;
            case R.id.tv_qr:

//                选中的item;

                StringBuilder sb = new StringBuilder();
                for (FilterBean filterBean : dataList1) {
                    int size = filterBean.filterList.size();

                    boolean flag = false;

                    for (int i = 0; i < size; i++) {
                        FilterBean filterBean2 = filterBean.filterList.get(i);

                        if (filterBean2.isSelected && !"0".equals(filterBean2.id)) {//选中，id不等于0

                            sb.append(filterBean2.id).append(",");

                        } else {

                            if (filterBean2.isSelected && "0".equals(filterBean2.id)) {//id等于0，且选中

                                flag = true;

                            } else {

                                if (flag) {
                                    sb.append(filterBean2.id).append(",");
                                }
                            }
                        }
                    }
                }

                String str = sb.toString();

                if (!TextUtils.isEmpty(str)) {
                    str = str.substring(0, str.length() - 1);
                }
                Map<String, String> map = new HashMap<String,String>();
                map.put(AppConstant.functionCode,"ok");
                map.put("data",str);
                callBack.menuDataCallBack(map);
                break;
        }
    }

    /**
     * 设置数据；
     *
     * @param dataList
     */
    public void setDataList(List<FilterBean> dataList) {
        this.dataList1 = dataList;

        dataList2 = null;

        adapter1 = new Sx1ListAdapter();
        listview1.setAdapter(adapter1);

        adapter2 = new Sx2ListAdapter();
        listview2.setAdapter(adapter2);

        // 默认第一个选中；
        if (this.dataList1 != null && dataList1.size() > 0) {
            //选中第一项；
            adapter1.getView(0, null, null).performClick();
        }
    }

    private class Sx1ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList1 == null ? 0 : dataList1.size();
        }

        @Override
        public FilterBean getItem(int position) {
            return dataList1 == null ? null : dataList1.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_item_menu2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final FilterBean itemBean = getItem(position);

            viewHolder.tv.setText(itemBean.filterName);
            viewHolder.tv.setSelected(itemBean.isSelected);

            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < dataList1.size(); i++) {
                        if (i == position) {
                            itemBean.isSelected = true;
                        } else {
                            dataList1.get(i).isSelected = false;
                        }
                    }
                    notifyDataSetChanged();

                    LogUtil.i("第一列选中的item==" + JSON.toJSONString(getItem(position)));

                    if (dataList1 != null) {
                        // 更新第二列中的数据；
                        dataList2 = dataList1.get(position).filterList;
                    }

                    if (adapter2 != null) {
                        adapter2.notifyDataSetChanged();
                    }
                }
            });
            return viewHolder.root;
        }

        public class ViewHolder {
            public final TextView tv;
            public View root;

            public ViewHolder(View root) {
                this.root = root;
                tv = (TextView) root.findViewById(R.id.tv);
            }
        }
    }

    private class Sx2ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList2 == null ? 0 : dataList2.size();
        }

        @Override
        public FilterBean getItem(int position) {
            return dataList2 == null ? null : dataList2.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_item_sx2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final FilterBean itemBean = getItem(position);
            LogUtil.e("position:" + position + "bean:" + JSON.toJSONString(itemBean));
            viewHolder.tvleft.setText(itemBean.name);
            viewHolder.checkbox.setChecked(itemBean.isSelected);

            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // viewHolder.checkbox.setChecked(!viewHolder.checkbox.isChecked());
                    setCheckState(position, !viewHolder.checkbox.isChecked());
                    notifyDataSetChanged();
                }
            });
            return viewHolder.root;
        }

        public class ViewHolder {
            public final TextView tvleft;
            public final CheckBox checkbox;
            public View root;

            public ViewHolder(View root) {
                this.root = root;
                tvleft = (TextView) root.findViewById(R.id.tv_left);
                checkbox = (CheckBox) root.findViewById(R.id.checkbox);
            }
        }
    }

    private void setCheckState(int position, boolean isChecked) {
        if (position == 0) {
            for (int i = 0; i < dataList2.size(); i++) {
                if (i == 0) {
                    dataList2.get(i).isSelected = true;
                } else {
                    dataList2.get(i).isSelected = false;
                }
            }
        } else {
            dataList2.get(0).isSelected = false;
            dataList2.get(position).isSelected = isChecked;
        }
    }
}
