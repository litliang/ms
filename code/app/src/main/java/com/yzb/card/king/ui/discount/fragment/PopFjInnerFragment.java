package com.yzb.card.king.ui.discount.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.luxury.activity.IMenuDataCallBack;
import com.yzb.card.king.ui.discount.bean.CircleRegion;
import com.yzb.card.king.ui.discount.bean.FjTjOutBean;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近popupwindow的外部容器Fragment；
 * 在xml布局文件中注册；
 * 用于解决Popwindow中的view无法直接使用Fragment的问题；
 * <p/>
 * created by gengqiyun on 2016.4.22
 */
public class PopFjInnerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listview1;
    private ListView listview2;
    private ListView listview3;
    private FjList1Adapter adapter1;
    private FjList2Adapter adapter2;
    private FjList3Adapter adapter3;
    private FjTjOutBean dataList; // 数据集；
    private List<CircleRegion> dataList1;
    private List<CircleRegion> dataList2;
    private List<CircleRegion> dataList3;

    private LayoutInflater inflater;
    private IMenuDataCallBack callBack;
    private String uid;

    public PopFjInnerFragment() {
    }

    public void setMenuDataCallBack(IMenuDataCallBack callBack) {
        this.callBack = callBack;
    }

    public static PopFjInnerFragment newInstance(String param1, String param2) {
        PopFjInnerFragment fragment = new PopFjInnerFragment();
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
        View view = inflater.inflate(R.layout.fragment_pop_inner_fj, container, false);
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    /**
     * 设置数据列表；
     *
     * @param dataList
     */
    public void setDataList(FjTjOutBean dataList) {
        if (dataList == null) return;

        this.dataList = dataList;
        // 获取第一列的实体，便于检索；
        String[] fjColum1 = {"附近", "商业区", "机场车站", "行政区", "地铁线", "热门地标"};

        // 初始化dataList1中的数据；
        dataList1 = new ArrayList<>();

        CircleRegion circleRegion;
        for (int i = 0; i < fjColum1.length; i++) {
            circleRegion = new CircleRegion();
            circleRegion.id = i + "";
            circleRegion.name = fjColum1[i];
            dataList1.add(circleRegion);
        }

        // 默认数据；
        dataList2 = initFjListData();
        dataList3 = null;

        adapter1 = new FjList1Adapter();
        listview1.setAdapter(adapter1);

        if (adapter1.getCount() > 0) {
            adapter1.getView(0, null, null).performClick();
        }

        adapter2 = new FjList2Adapter();
        listview2.setAdapter(adapter2);

        adapter3 = new FjList3Adapter();
        listview3.setAdapter(adapter3);
    }

    public void setSelectItemUid(String uid) {
        this.uid = uid;
        LogUtil.i("uid==" + uid);
    }

    private class FjList1Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList1 == null ? 0 : dataList1.size();
        }

        public int getSelectedItem() {
            if (dataList1 != null && dataList1.size() > 0) {
                for (int i = 0; i < dataList1.size(); i++) {
                    if (dataList1.get(i).isSelected) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public CircleRegion getItem(int position) {
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
            final CircleRegion itemBean = getItem(position);

            //1项；
            viewHolder.tv.setText(itemBean.name);
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

                    // 更新第二列中的数据；position(1-5)
                    if (dataList != null) {

                        if (position == 0) {  // 附近；
                            dataList2 = initFjListData();
                            LogUtil.i("点击的第二列数据列表==" + JSON.toJSONString(dataList2));
                        } else if (position == 1) { // 商业区；
                            dataList2 = dataList.circle;
                        } else if (position == 2) { // 机场车站；
                            dataList2 = dataList.airport;
                        } else if (position == 3) { // 行政区；
                            dataList2 = dataList.region;
                        } else if (position == 4) { // 地铁线；
                            dataList2 = dataList.metro;
                        } else if (position == 5) { // 热门地标；
                            dataList2 = dataList.landmark;
                        }

                        if (dataList2 != null) {
                            for (int i = 0; i < dataList2.size(); i++) {
                                dataList2.get(i).isSelected = false;
                            }
                        }
                    }

                    LogUtil.i("点击的第二列数据列表==" + JSON.toJSONString(dataList2));

                    if (adapter2 != null) {
                        adapter2.notifyDataSetChanged();
                    }

                    dataList3 = null;
                    if (adapter3 != null) {
                        adapter3.notifyDataSetChanged();
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

    /**
     * init附近的子数据集；  List<String>  ==> List<CircleRegion>
     */
    private List<CircleRegion> initFjListData() {
        if (dataList != null) {
            List<String> tempListStr = dataList.near;
            if (tempListStr != null) {

                //构造 List<CircleRegion>类型的数据；
                List<CircleRegion> targetList = new ArrayList<CircleRegion>();
                CircleRegion circleRegion;
                for (int i = 0; i < tempListStr.size(); i++) {
                    circleRegion = new CircleRegion();
                    circleRegion.name = tempListStr.get(i);
                    circleRegion.isSelected = false;

                    targetList.add(circleRegion);
                }
                return targetList;
            }
        }
        return null;
    }

    private class FjList2Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList2 == null ? 0 : dataList2.size();
        }

        @Override
        public CircleRegion getItem(int position) {
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
            final CircleRegion itemBean = getItem(position);
            viewHolder.tv.setSelected(itemBean.isSelected);

            // 先设置选中状态，在根据情况当前选中的item的背景；

            switch (adapter1.getSelectedItem()) {
                case 0: // 附近；
                    viewHolder.tv.setText(position == 0 ? itemBean.name : itemBean.name + "km");
                    break;
                case 1: //商业区；回传圈id；
                    viewHolder.tv.setText(itemBean.circleName);
                    break;
                case 2: //机场车站；回传圈id；
                    viewHolder.tv.setText(itemBean.airName);
                    break;
                case 3: //行政区；回传圈id；
                    viewHolder.tv.setText(itemBean.cityName);
                    break;
                case 4: //地铁线；回传圈id；
                    viewHolder.tv.setText(itemBean.metroName);
                    break;
                case 5: //热门地标；
                    viewHolder.tv.setText(itemBean.spotName);
                    break;

                default:
                    viewHolder.tv.setText("");
                    break;
            }
            // 判断第二列是否是选中的title；
//            if (!TextUtils.isEmpty(uid) && uid.equals(itemBean.uuid)) {
//                viewHolder.tv.setTextColor(getContext().getResources().getColor(R.color.text_color_white));
//                viewHolder.tv.setBackgroundColor(getContext().getResources().getColor(R.color.color_status));
//            }

            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < dataList2.size(); i++) {
                        if (i == position) {
                            itemBean.isSelected = true;
                        } else {
                            dataList2.get(i).isSelected = false;
                        }
                    }
                    notifyDataSetChanged();

                    // 更新第三列中的数据；
                    dataList3 = itemBean.circle;

                    if (dataList3 != null) {
                        // 清空状态；
                        for (int i = 0; i < dataList3.size(); i++) {
                            dataList3.get(i).isSelected = false;
                        }
                    }
                    LogUtil.i("点击的第二列itemBean==" + JSON.toJSONString(itemBean));

                    //第二列没有子集，直接返回；
                    if ((dataList3 == null || dataList3.size() == 0) && callBack != null) {
                        switch (adapter1.getSelectedItem()) {
                            case 0: // 附近；  无3级；第一项为全部；distance参数回传；item名称回传；第一项为全部(回传不加km)，以下全部为数字；
                                callBack.menuDataCallBack(1, position == 0 ? "" : itemBean.name, 10,
                                        position == 0 ? itemBean.name : itemBean.name + "km");
                                break;
                            case 1: // 商业区；  无3级；
                                callBack.menuDataCallBack(1, itemBean.id, 11, itemBean.circleName);
                                break;
                            case 2: // 机场车站；  无3级；
                                callBack.menuDataCallBack(1, itemBean.id, 12, itemBean.airName);
                                break;
                            // case 3：行政区，有3级；此处不用回调；
                            // 在第三列回调；
                            case 4: // ； 地铁线 无3级；
                                callBack.menuDataCallBack(1, itemBean.id, 14, itemBean.metroName);
                                break;
                            case 5: // 热门地标；  无3级；
                                String[] args = new String[4];
                                args[0] = itemBean.id;
                                args[1] = itemBean.lng;
                                args[2] = itemBean.lat;
                                args[3] = itemBean.spotName;
                                callBack.menuDataCallBack(1, args, 15);
                                break;
                        }
                    }
                    if (dataList3 != null) {
                        LogUtil.i("第三列数据列表==" + JSON.toJSONString(dataList3));
                    }

                    if (adapter3 != null) {
                        adapter3.notifyDataSetChanged();
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
        public CircleRegion getItem(int position) {
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
            final CircleRegion itemBean = getItem(position);


            viewHolder.tv.setText(itemBean.circleName);
            viewHolder.tv.setSelected(itemBean.isSelected);

            viewHolder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        // 回传圈id；行政区第三级；
                        callBack.menuDataCallBack(1, itemBean.id, 13, itemBean.circleName);
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
