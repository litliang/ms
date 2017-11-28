package com.yzb.card.king.ui.discount.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IMenuDataCallBack;
import com.yzb.card.king.ui.discount.bean.MenuItemBean;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能排序popupwindow的外部容器Fragment；
 * 在xml布局文件中注册；
 * 用于解决Popwindow中的view无法直接使用Fragment的问题；
 * <p/>
 * created by gengqiyun on 2016.4.22
 */
public class PopZnpxInnerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listview1;
    private ListView listview2;
    private List<MenuItemBean> dataList1;
    private List<MenuItemBean> dataList2;

    private Znpxist1Adapter adapter1;
    private Znpxist2Adapter adapter2;
    private IMenuDataCallBack callBack;
    private LayoutInflater inflater;
    private String curSort;
    private String parentId;

    public void setMenuDataCallBack(IMenuDataCallBack callBack) {
        this.callBack = callBack;
    }

    public PopZnpxInnerFragment() {
    }

    public static PopZnpxInnerFragment newInstance(String param1, String param2) {
        PopZnpxInnerFragment fragment = new PopZnpxInnerFragment();
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
        View view = inflater.inflate(R.layout.fragment_pop_inner_znpx, container, false);
        init(view);
        return view;
    }

    private void init(View contentView) {
        if (contentView == null) return;
        inflater = LayoutInflater.from(getActivity());
        listview1 = (ListView) contentView.findViewById(R.id.listview1);
        adapter1 = new Znpxist1Adapter();
        listview1.setAdapter(adapter1);

        listview2 = (ListView) contentView.findViewById(R.id.listview2);
        adapter2 = new Znpxist2Adapter();
        listview2.setAdapter(adapter2);
    }

    public void setDataList() {

        dataList1 = getList1Data();

        LogUtil.i("dataList1==" + dataList1);
        dataList2 = null;

        adapter1 = new Znpxist1Adapter();
        listview1.setAdapter(adapter1);

        adapter2 = new Znpxist2Adapter();
        listview2.setAdapter(adapter2);


        // 此处不能默认执行performClick；否则会刷新数据；
        if (this.dataList1 != null && dataList1.size() > 0) {
            boolean hasFind = false;
            int index = -1;
            // 根据curSort检索二级选中的一级item；并选中；
            for (int i = 0, j = dataList1.size(); i < j; i++) {
                if (dataList1.get(i).innerList != null) {
                    for (MenuItemBean itemBean : dataList1.get(i).innerList) {
                        if (!TextUtils.isEmpty(curSort) && curSort.equals(itemBean.id)) {
                            hasFind = true;
                            index = i;
                            break;
                        }
                    }
                }
            }

            // 如果找到选中的二级分类，一级执行点击，否则默认选中智能排序，但不执行点击；
            if (hasFind) {
                adapter1.getView(index, null, null).performClick();
            } else {
                dataList1.get(0).isSelected = true;
                adapter1.notifyDataSetChanged();
            }

//            for (int i = 0; i < dataList1.size(); i++) {
//                // 找到大分类id；
//                if (!TextUtils.isEmpty(dataList1.get(i).id) && dataList1.get(i).id.equals(parentId)) {
//                    adapter1.getView(i, null, null).performClick();
//                    break;
//                }
//            }
        }
    }

    /**
     * 获取第一列的数据；
     *
     * @return
     */
    public List<MenuItemBean> getList1Data() {

        List<MenuItemBean> lastData = new ArrayList<>();
        String[] colum1 = {"智能", "距离", "价格", "人气", "评价"};
        for (int i = 0; i < colum1.length; i++) {
            MenuItemBean itemBean = new MenuItemBean();
            itemBean.name = colum1[i];
            itemBean.id = i + "";

            // 智能排序,点击直接跳转；
            if (i == 0) {
                itemBean.innerList = null;
                lastData.add(itemBean);
                continue;
            }

            List<MenuItemBean> innerList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                MenuItemBean itemBean2 = new MenuItemBean();
                itemBean2.id = j + "";
                itemBean2.parentId = i + "";
                switch (i) {
                    case 0: // 智能；
                        itemBean2.id = "";
                        itemBean2.name = "";
                        break;
                    case 1:
                        if (j == 0) {
                            itemBean2.id = "1";
                            itemBean2.name = "由近到远";
                        } else if (j == 1) {
                            itemBean2.id = "2";
                            itemBean2.name = "由远到近";
                        }
                        break;
                    case 2:
                        if (j == 0) {
                            itemBean2.id = "3";
                            itemBean2.name = "升序";
                        } else if (j == 1) {
                            itemBean2.id = "4";
                            itemBean2.name = "降序";
                        }
                        break;
                    case 3:
                        if (j == 0) {
                            itemBean2.id = "5";
                            itemBean2.name = "由高到低";
                        } else if (j == 1) {
                            itemBean2.id = "6";
                            itemBean2.name = "由低到高";
                        }
                        break;
                    case 4:
                        if (j == 0) {
                            itemBean2.id = "7";
                            itemBean2.name = "由高到低";
                        } else if (j == 1) {
                            itemBean2.id = "8";
                            itemBean2.name = "由低到高";
                        }
                        break;
                }
                innerList.add(itemBean2);
            }
            itemBean.innerList = innerList;
            lastData.add(itemBean);

        }
        return lastData;
    }

    public void setCurSort(String sort) {
        curSort = sort;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    private class Znpxist1Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList1 == null ? 0 : dataList1.size();
        }

        @Override
        public MenuItemBean getItem(int position) {
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
                convertView = inflater.inflate(R.layout.listview_item_menu2_2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final MenuItemBean itemBean = getItem(position);

            viewHolder.tv.setText(itemBean.name);
            viewHolder.tv.setSelected(itemBean.isSelected);

            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < dataList1.size(); i++) {
                        if (i == position) {
                            dataList1.get(i).isSelected = true;
                        } else {
                            dataList1.get(i).isSelected = false;
                        }
                    }
                    notifyDataSetChanged();

                    if (dataList1 != null) {
                        // 更新第二列中的数据；
                        dataList2 = dataList1.get(position).innerList;
                    }

                    // 第二列没有子集；，直接跳转；
                    if (dataList2 == null || dataList2.size() == 0) {
                        if (callBack != null) {
                            // 回传相应的id；（typeId=id 进行筛选）
                            callBack.menuDataCallBack(3, itemBean.id);
                        }
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
            public final View root;

            public ViewHolder(View root) {
                tv = (TextView) root.findViewById(R.id.tv);
                this.root = root;
            }
        }
    }

    private class Znpxist2Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList2 == null ? 0 : dataList2.size();
        }

        @Override
        public MenuItemBean getItem(int position) {
            return dataList2 == null ? null : dataList2.get(position);
        }


        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_item_menu2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final MenuItemBean itemBean = getItem(position);
            viewHolder.tv.setText(itemBean.name);

            // 当前分类的id；
            if (!TextUtils.isEmpty(itemBean.id) && itemBean.id.equals(curSort)) {
//                viewHolder.tv.setSelected(true);
                viewHolder.tv.setTextColor(getActivity().getResources().getColor(R.color.text_color_white));
                viewHolder.tv.setBackgroundColor(getActivity().getResources().getColor(R.color.color_status));

            } else {
                viewHolder.tv.setTextColor(getActivity().getResources().getColor(R.color.text_color_22));
                viewHolder.tv.setBackgroundColor(getActivity().getResources().getColor(R.color.expand_content_bg));
//                viewHolder.tv.setSelected(false);
            }

            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {

                        LogUtil.i("点击的itemBean.id==" + itemBean.id);
                        callBack.menuDataCallBack(3, itemBean.id, itemBean.name, itemBean.parentId);
                    }
                }
            });
            return viewHolder.root;
        }

        public class ViewHolder {
            public final TextView tv;
            public final View root;

            public ViewHolder(View root) {
                tv = (TextView) root.findViewById(R.id.tv);
                this.root = root;
            }
        }
    }
}
