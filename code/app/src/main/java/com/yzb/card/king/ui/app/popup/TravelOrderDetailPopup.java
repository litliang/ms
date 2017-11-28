package com.yzb.card.king.ui.app.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.bean.travel.TravelOrderDetBean;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.UiUtils;

import java.util.List;

/**
 * 类名：旅游订单详情弹窗
 *
 * @author ：gengqiyun
 * @date 2016.11.28
 */
public class TravelOrderDetailPopup extends PopupWindow
{
    private Context context;
    private ListView listView;

    public TravelOrderDetailPopup(Context context)
    {
        this.context = context;
        initView();
    }

    private void initView()
    {
        View rootView = UiUtils.inflate(R.layout.popwindow_content_plane_order);
        setContentView(rootView);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        setOutsideTouchable(true);
        setAnimationStyle(R.style.popupwindow_animation_style);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        listView = (ListView) rootView.findViewById(R.id.listview);
        //订单详情window中的灰色区域；
        View filterBlack = rootView.findViewById(R.id.filterBlack);
        filterBlack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    public void setData(List<TravelOrderDetBean> orderListData)
    {
        TravelOrderAdapter adapter = new TravelOrderAdapter(context, orderListData);
        listView.setAdapter(adapter);
    }

    public class TravelOrderAdapter extends BaseListAdapter<TravelOrderDetBean>
    {

        public TravelOrderAdapter(Context context, List<TravelOrderDetBean> list)
        {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            convertView = mInflater.inflate(R.layout.popwindow_content_travel_order_item, parent, false);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView details = (TextView) convertView.findViewById(R.id.details);
            TextView tvBedIntro = (TextView) convertView.findViewById(R.id.tvBedIntro);
            TextView price = (TextView) convertView.findViewById(R.id.price);

            TravelOrderDetBean detBean = getItem(position);
            title.setText(detBean.getTitle());
            details.setText(TextUtils.isEmpty(detBean.getDetails()) ? "" : detBean.getDetails());

            tvBedIntro.setText(TextUtils.isEmpty(detBean.getBedIntro()) ? "" : detBean.getBedIntro());

            price.setText("¥" + detBean.getPrice());

            return convertView;
        }
    }

}
