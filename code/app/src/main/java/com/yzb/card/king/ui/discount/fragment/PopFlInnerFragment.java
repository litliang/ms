package com.yzb.card.king.ui.discount.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IMenuDataCallBack;
import com.yzb.card.king.ui.discount.bean.CategoryBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.util.LogUtil;

import java.util.List;

/**
 * 分类popupwindow的外部容器Fragment；
 * 在xml布局文件中注册；
 * 用于解决Popwindow中的view无法直接使用Fragment的问题；
 * <p/>
 * created by gengqiyun on 2016.4.22
 */
public class PopFlInnerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listview1;
    private ListView listview2;
    private ListView listview3;
    private List<CategoryBean> dataList1;
    private List<CategoryBean.Child> dataList2;
    private List<CategoryBean.Child.GrandChild> dataList3;

    private FjList1Adapter adapter1;
    private FjList2Adapter adapter2;
    private FjList3Adapter adapter3;
    private IMenuDataCallBack callBack;
    private LayoutInflater inflater;
    private String curTypeId; // 当前选中的分类的id；
    private String typeParentId; // 大分类的id；
    private Context context;
    private String typeGrandParentId;

    public void setMenuDataCallBack(IMenuDataCallBack callBack) {
        this.callBack = callBack;
    }

    public PopFlInnerFragment() {
    }

    public void setCurentFlId(String typeId) {
        this.curTypeId = typeId;
    }

    public void setParentId(String typeParentId) {
        this.typeParentId = typeParentId;
    }

    public void setGrandParentId(String typeGrandParentId) {
        this.typeGrandParentId = typeGrandParentId;
    }

    public static PopFlInnerFragment newInstance(String param1, String param2) {
        PopFlInnerFragment fragment = new PopFlInnerFragment();
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
        View view = inflater.inflate(R.layout.fragment_pop_inner_fl, container, false);
        init(view);
        return view;
    }

    private void init(View contentView) {
        if (contentView == null) return;
        inflater = LayoutInflater.from(getActivity());

        listview1 = (ListView) contentView.findViewById(R.id.listview1);
        adapter1 = new FjList1Adapter();
        listview1.setAdapter(adapter1);

        listview2 = (ListView) contentView.findViewById(R.id.listview2);
        adapter2 = new FjList2Adapter();
        listview2.setAdapter(adapter2);

        listview3 = (ListView) contentView.findViewById(R.id.listview3);
        adapter3 = new FjList3Adapter();
        listview3.setAdapter(adapter3);
    }

    public void setDataList(List<CategoryBean> dataList) {
        dataList1 = dataList;
        dataList2 = null;
        dataList3 = null;

        adapter1 = new FjList1Adapter();
        listview1.setAdapter(adapter1);
        adapter2 = new FjList2Adapter();
        listview2.setAdapter(adapter2);
        adapter3 = new FjList3Adapter();
        listview3.setAdapter(adapter3);

        LogUtil.i("typeGrandParentId=" + typeGrandParentId +
                ",typeParentId=" + typeParentId + "," + ",curTypeId=" + curTypeId);

        if (this.dataList1 != null && dataList1.size() > 0) {
            String id;
            String targetParentId = (TextUtils.isEmpty(typeGrandParentId) ? typeParentId : typeGrandParentId);//第一列目标id；
            for (int i = 0; i < dataList1.size(); i++) {
                id = dataList1.get(i).id;
                // 找到大分类id；
                if (!TextUtils.isEmpty(id) && id.equals(targetParentId)) {
                    adapter1.getView(i, null, null).performClick();
                    listview1.setSelection(i);
                    break;
                }
            }
        }
    }

    private class FjList1Adapter extends BaseAdapter {

        public CategoryBean getSelectedBean() {
            if (dataList1 != null) {
                for (int i = 0; i < dataList1.size(); i++) {
                    if (dataList1.get(i).isSelected) {
                        return dataList1.get(i);
                    }
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return dataList1 == null ? 0 : dataList1.size();
        }

        @Override
        public CategoryBean getItem(int position) {
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
                convertView = inflater.inflate(R.layout.listview_item_menu1, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final CategoryBean itemBean = getItem(position);
            String typeName = itemBean.typeName;
            String size;

            if (AppConstant.travel_id.equals(itemBean.id)) {//旅游
                size = itemBean.count;
            } else if (AppConstant.hotel_id.equals(itemBean.id)) {//酒店
                size = itemBean.hotelCount;
            } else {
                size = itemBean.count;
            }
            SpannableString ss = new SpannableString(typeName + " (" + (TextUtils.isEmpty(size) ? "0" : size) + ")");
            //设置前景色为洋红色  
            ss.setSpan(new ForegroundColorSpan((Color.RED)), typeName.length(), ss.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            viewHolder.tv.setText(ss);
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

                    // 第一列的id；
                    String id = itemBean.id;
                    if (AppConstant.meishi_id.equals(id) || AppConstant.travel_id.equals(id)) {
                        typeGrandParentId = id;
                    } else {
                        typeParentId = id;
                    }
                    LogUtil.i("第一列选中的item==" + JSON.toJSONString(getItem(position)));

                    // 更新第二列中的数据；
                    dataList2 = dataList1.get(position).childList;
                    dataList3 = null;

                    // 第一列没有子集，直接跳转；
                    if ((dataList2 == null || dataList2.size() == 0) && callBack != null) {
                        String[] args = new String[5];
                        // 处理美食，旅游三级的问题；
                        if (AppConstant.meishi_id.equals(itemBean.id) || AppConstant.travel_id.equals(itemBean.id)) {
                            args[0] = ""; // 此时id即为parentId；
                            args[1] = "";
                            args[4] = itemBean.id; // typeGrandParentId;
                        } else {
                            args[0] = itemBean.id;
                            args[1] = "";
                            args[4] = "";
                        }
                        args[2] = itemBean.typeName; //typeParentName
                        args[3] = itemBean.typeName;  //typeName

                        callBack.menuDataCallBack(2, args);
                    } else {
                        //第一列有子集,
                        if (adapter2 != null) {
                            adapter2.notifyDataSetChanged();
                        }
                        if (adapter3 != null) {
                            adapter3.notifyDataSetChanged();
                        }
                    }

                    LogUtil.i("找到的dataList2==" + dataList2);
                    notifyDataSetChanged();
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

    private class FjList2Adapter extends BaseAdapter {
        public void clear() {
            if (dataList2 != null) {
                dataList2.clear();
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return dataList2 == null ? 0 : dataList2.size();
        }

        @Override
        public CategoryBean.Child getItem(int position) {
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
                convertView = inflater.inflate(R.layout.listview_item_menu2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final CategoryBean.Child itemBean = getItem(position);
            String typeName = itemBean.typeName;

            String size;
            if (AppConstant.hotel_id.equals(itemBean.parentId)) {//酒店
                size = itemBean.hotelCount;
            } else {
                size = itemBean.count;
            }
            SpannableString ss = new SpannableString(typeName + " (" + (TextUtils.isEmpty(size) ? "0" : size) + ")");
            //设置前景色为洋红色  
            ss.setSpan(new ForegroundColorSpan((Color.RED)), typeName.length(), ss.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            viewHolder.root_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataList3 = itemBean.grandChildList;

                    LogUtil.i("第三列数据==" + JSON.toJSONString(dataList3));

                    for (int i = 0; i < dataList2.size(); i++) {
                        if (i == position) {
                            itemBean.isSelected = true;
                        } else {
                            dataList2.get(i).isSelected = false;
                        }
                    }

                    // 第二列的parentId；
                    String id = itemBean.id;
                    String parentId = itemBean.parentId;
                    if (AppConstant.meishi_id.equals(parentId) || AppConstant.travel_id.equals(parentId)) {
                        typeParentId = id;
                    } else {
                        curTypeId = id;
                    }

                    //第二列没有子集，直接返回；
                    if ((dataList3 == null || dataList3.size() == 0) && callBack != null) {
                        // 回传相应的id；（typeId=id 进行筛选）
                        String[] args = new String[5];
                        // 处理美食，旅游三级的问题；
                        if (AppConstant.meishi_id.equals(itemBean.parentId) || AppConstant.travel_id.equals(itemBean.parentId)) {
                            args[0] = itemBean.id; // 此时id即为parentId；
                            args[1] = "";
                            args[4] = itemBean.parentId; //此时为typeGrandParentId;
                        } else {
                            args[0] = itemBean.parentId;
                            args[1] = itemBean.id;
                            args[4] = ""; // 非美食，旅游时为空;
                        }
                        args[2] = adapter1.getSelectedBean().typeName; // 大分类的name；
                        args[3] = itemBean.typeName;// 小分类的name；
                        callBack.menuDataCallBack(2, args);
                    } else {
                        if (adapter3 != null) {
                            adapter3.notifyDataSetChanged();
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            //第二列id；
            String id = itemBean.id;
            String targetId = (TextUtils.isEmpty(typeGrandParentId) ? curTypeId : typeParentId);

            if (!TextUtils.isEmpty(id) && id.equals(targetId)) {
                viewHolder.tv.setText(ss);
                viewHolder.tv.setSelected(true);

                List<CategoryBean.Child.GrandChild> grandChildren = itemBean.grandChildList;
                //有第三列时才点击；
                if (grandChildren != null && grandChildren.size() > 0) {
                    viewHolder.root_.performClick();
                } else {
                    //没有第三列；// 改变第二列的状态；待定；
//                    viewHolder.tv.setTextColor(Color.parseColor("#ffffff"));
//                    viewHolder.tv.setBackgroundColor(Color.parseColor("#d84043"));
//                    ss.setSpan(new ForegroundColorSpan((Color.WHITE)), 0, ss.length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                viewHolder.tv.setText(ss);
                viewHolder.tv.setSelected(false);
            }
            return viewHolder.root_;
        }

        public class ViewHolder {
            public TextView tv;
            public View root_ = null;

            public ViewHolder(View root) {
                tv = (TextView) root.findViewById(R.id.tv);
                root_ = root.findViewById(R.id.root);
            }
        }
    }

    private class FjList3Adapter extends BaseAdapter {
        public void clear() {
            if (dataList3 != null) {
                dataList3.clear();
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return dataList3 == null ? 0 : dataList3.size();
        }

        @Override
        public CategoryBean.Child.GrandChild getItem(int position) {
            return dataList3 == null ? null : dataList3.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listview_item_menu3, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final CategoryBean.Child.GrandChild itemBean = getItem(position);
            String typeName = itemBean.typeName;

            String size = itemBean.count;
            SpannableString ss = new SpannableString(typeName + " (" + (TextUtils.isEmpty(size) ? "0" : size) + ")");
            String typeId = itemBean.id;

            LogUtil.i("第三列typeId" + typeId + ",curTypeId=" + curTypeId);

            if (!TextUtils.isEmpty(typeId) && typeId.equals(curTypeId)) {
                LogUtil.i("第三列发现相同的--------------curTypeId=" + curTypeId);
                viewHolder.tv.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.tv.setBackgroundColor(Color.parseColor("#d84043"));
                ss.setSpan(new ForegroundColorSpan((Color.WHITE)), 0, ss.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                viewHolder.tv.setTextColor(Color.parseColor("#676767"));
                viewHolder.tv.setBackgroundColor(Color.WHITE);

                ss.setSpan(new ForegroundColorSpan((Color.RED)), typeName.length(), ss.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            viewHolder.tv.setText(ss);

            viewHolder.root_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curTypeId = itemBean.id;

                    if (callBack != null) {
                        String[] args = new String[5];
                        args[0] = itemBean.parentId;
                        args[1] = itemBean.id;    //typeId;
                        args[4] = typeGrandParentId; // typeGrandParentId;
                        args[2] = adapter1.getSelectedBean().typeName; // 大分类的name；
                        args[3] = itemBean.typeName;// 小分类的name；
                        callBack.menuDataCallBack(2, args);
                    }
                }
            });
            return viewHolder.root_;
        }

        public class ViewHolder {
            public TextView tv;
            public View root_ = null;

            public ViewHolder(View root) {
                tv = (TextView) root.findViewById(R.id.tv);
                root_ = root.findViewById(R.id.root);
            }
        }
    }
}
