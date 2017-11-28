package com.yzb.card.king.ui.discount.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yzb.card.king.R;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.discount.bean.JdmsBean;
import com.yzb.card.king.sys.GlobalApp;
import com.yzb.card.king.util.CommonUtil;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengqiyun on 2016/4/18.
 * 景点美食；
 */
public class JdmsAdapter extends BaseAdapter {
    private final Location selectedCity;
    private Context context;
    private LayoutInflater inflater;
    private int imgWidth;
    private int imgHeight;
    private List<JdmsBean> jdmsBeans;
    private float wHRate = 272 / 676.0f;
    private int height;

    public JdmsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        selectedCity = GlobalApp.getSelectedCity();
        height = (int) ((CommonUtil.getScreenWidth(context) - CommonUtil.dip2px(context, 20)) * wHRate + 0.5f);
    }

    public void setMeasure(int imgWidth, int imgHeight) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }

    public void appendDataList(List<JdmsBean> jdmsBeans) {
        if (jdmsBeans == null || jdmsBeans.size() == 0) return;
        if (this.jdmsBeans == null) {
            this.jdmsBeans = new ArrayList<>();
        }
        this.jdmsBeans.addAll(jdmsBeans);
        notifyDataSetChanged();
    }

    public void clear() {
        if (jdmsBeans != null) {
            jdmsBeans.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return jdmsBeans == null ? 0 : jdmsBeans.size();
    }

    @Override
    public JdmsBean getItem(int position) {
        return jdmsBeans == null ? null : jdmsBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_jdms, parent, false);
            viewHolder.rl_root = (RelativeLayout) convertView.findViewById(R.id.rl_root);
//            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(imgWidth, imgHeight);
//            viewHolder.rl_root.setLayoutParams(lp);

            viewHolder.ivpic = (ImageView) convertView.findViewById(R.id.iv_pic);
            viewHolder.tvname = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvdistance = (TextView) convertView.findViewById(R.id.tv_distance);
            viewHolder.tvct = (TextView) convertView.findViewById(R.id.tv_ct);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivpic.getLayoutParams().height = height;

        JdmsBean jdmsBean = getItem(position);
        x.image().bind(viewHolder.ivpic,jdmsBean.image,GlobalApp.getInstance().getImageOptionsLogo());
        viewHolder.tvname.setText(jdmsBean.spotName);

        //两点距离，单位为：米,转换错误时返回-1.
        double distance = DistanceUtil.getDistance(new LatLng(selectedCity.latitude, selectedCity.longitude),
                new LatLng(jdmsBean.lat, jdmsBean.lng));

        if (distance < 1000) {
            viewHolder.tvdistance.setText(String.format("%.1f", distance) + "m");
        } else if (distance > 1000 && distance <= 100000) {
            viewHolder.tvdistance.setText(String.format("%.1f", distance / 1000) + "km");
        } else if (distance > 100000) {
            viewHolder.tvdistance.setText(">100km");
        }

        //recommNum特色餐厅数量
        viewHolder.tvct.setText(jdmsBean.storeNum + "家餐厅，" + jdmsBean.recommNum + "家特色餐厅");

        return convertView;
    }

    public class ViewHolder {
        public RelativeLayout rl_root;
        public ImageView ivpic;
        public TextView tvname;
        public TextView tvdistance;
        public TextView tvct;

    }
}
