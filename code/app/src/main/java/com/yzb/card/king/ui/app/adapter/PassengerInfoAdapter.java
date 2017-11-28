package com.yzb.card.king.ui.app.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.app.presenter.SettingUpdatePresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuAdapter;
import com.yzb.card.king.ui.appwidget.menulistview.ItemMenuView;
import com.yzb.card.king.ui.app.bean.PassengerInfoBean;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.RegexUtil;
import com.yzb.card.king.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 功能：旅客信息；
 *
 * @author:gengqiyun
 * @date: 2016/6/20
 */
public class PassengerInfoAdapter extends ItemMenuAdapter implements BaseViewLayerInterface {
    private Context context;
    private LayoutInflater inflater;
    private List<PassengerInfoBean> passengers;
    public static final int DEL_SUC_EVENT = 0x003;
    public static final int UPDATE_DEFAULT_EVENT = 0x001; // 更新默认默认旅客；
    public static final int ITEM_CLICK_EVENT = 0x002;
    private String[] passportArray;
    private String[] typeArray;
    private SettingUpdatePresenter settingUpdatePresenter;
    private Handler dataHandler;

    public PassengerInfoAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        passportArray = context.getResources().getStringArray(R.array.setting_certificate_type);
        typeArray = context.getResources().getStringArray(R.array.setting_indexs);
        settingUpdatePresenter = new SettingUpdatePresenter(this);
    }

    public void appendDataList(List<PassengerInfoBean> jdmsBeans)
    {
        if (jdmsBeans == null || jdmsBeans.size() == 0) return;
        if (this.passengers == null) {
            this.passengers = new ArrayList<>();
        }
        this.passengers.addAll(jdmsBeans);
        notifyDataSetChanged();
    }

    public void clear()
    {
        if (passengers != null) {
            passengers.clear();
            notifyDataSetChanged();
        }
    }

    public List<PassengerInfoBean> getDataList()
    {
        return passengers;
    }

    @Override
    public int getCount()
    {
        return passengers == null ? 0 : passengers.size();
    }

    @Override
    public PassengerInfoBean getItem(int position)
    {
        return passengers == null ? null : passengers.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public void onDeleItem(int pos)
    {
        if (passengers != null) {
            passengers.remove(pos);
            notifyDataSetChanged();

            if (dataHandler != null) {
                dataHandler.sendMessage(dataHandler.obtainMessage(DEL_SUC_EVENT));
            }
        }
    }

    @Override
    public ItemMenuView getItemView(final int position, ItemMenuView convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = (ItemMenuView) inflater.inflate(R.layout.listview_item_passenger_info, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PassengerInfoBean data = getItem(position);

        //1身份证；2军人证件
        boolean state = "1".equals(data.certType) || "5".equals(data.certType);
        String enName = data.getEngName() + "  " + data.getEngSurname();
        viewHolder.ps_name.setText(state ? data.getName() : enName);

        //身份证隐藏中间部分；
        String handleCertNo = "1".equals(data.certType) ? RegexUtil.hideIdNum(data.certNo) : data.certNo;
        String certType = "";
        for (int i = 0; i < typeArray.length; i++) {
            if (typeArray[i].equals(data.certType)) {
                certType = passportArray[i];
                break;
            }
        }
        viewHolder.tvCertType.setText(certType);
        viewHolder.ps_id.setText(handleCertNo);

        if ("2".equals(data.getStatus())) {
            viewHolder.ivDot.setSelected(true);

        } else {
            viewHolder.ivDot.setSelected(false);
        }



        //设置为默认；
        viewHolder.panelSetDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null && !viewHolder.ivDot.isSelected()) {
                    Message msg = dataHandler.obtainMessage(UPDATE_DEFAULT_EVENT);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });

        viewHolder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (dataHandler != null) {
                    Message msg = dataHandler.obtainMessage(ITEM_CLICK_EVENT);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });
        final ItemMenuView finalConvertView = convertView;
        viewHolder.llUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendDelData(position, finalConvertView);
            }
        });
        return viewHolder.root;
    }

    private int intentDelPosition = -1; //要删除的bean的下标；
    private ItemMenuView finalConvertView;////要删除的item；

    public void setHandler(Handler dataHandler)
    {
        this.dataHandler = dataHandler;
    }

    /**
     * 删除旅客
     *
     * @param position 点击的下标；
     */
    private void sendDelData(final int position, final ItemMenuView finalConvertView)
    {
        this.intentDelPosition = position;
        this.finalConvertView = finalConvertView;

        final PassengerInfoBean data = getItem(position);
        Map<String, Object> params = new HashMap<>();
        params.put("guestId", data.id);
        params.put("status", "0");
        params.put("serviceName", CardConstant.setting_customerguestupdate);

        LogUtil.i("删除常用旅客-提交参数:" + params);
        settingUpdatePresenter.loadData(params);
    }

    @Override
    public void callSuccessViewLogic(Object data, int type)
    {
        ToastUtil.i(context, context.getString(R.string.app_delete_success));
        if (passengers != null && passengers.size() > 0) {
            if (intentDelPosition >= 0 && finalConvertView != null) {
                finalConvertView.hideMenu();
                deleteItem(intentDelPosition, finalConvertView);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        ToastUtil.i(context, o + "");
    }

    /**
     * 更新默认item;
     *
     * @param id
     */
    public void updateDefaultById(String id)
    {
        if (!TextUtils.isEmpty(id)) {
            PassengerInfoBean pib;
            for (int i = 0; i < passengers.size(); i++) {
                pib = passengers.get(i);
                //默认；
                if(id.equals(pib.getId())){

                    pib.setStatus("2");
                }else {

                    pib.setStatus("1");
                }

            }
            notifyDataSetChanged();
        }
    }

    public void deleteById(String id)
    {
        if (!TextUtils.isEmpty(id)) {
            for (int i = 0; i < passengers.size(); i++) {
                if (id.equals(passengers.get(i).getId())) {
                    passengers.remove(i);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    public class ViewHolder {
        public final TextView ps_name;
        public final TextView ps_id;
        public final TextView tvCertType;
        public final ImageView ivDot;
        public final View panelSetDefault;
        public final ItemMenuView root;
        public final View llUnbind;
        public final View ll_content;

        public ViewHolder(ItemMenuView root)
        {
            ivDot = (ImageView) root.findViewById(R.id.ivDot);
            panelSetDefault = root.findViewById(R.id.panelSetDefault);

            ps_name = (TextView) root.findViewById(R.id.tv_passenger_name);
            tvCertType = (TextView) root.findViewById(R.id.tvCertType);
            ps_id = (TextView) root.findViewById(R.id.tv_passenger_id);
            llUnbind = root.findViewById(R.id.llUnbind);
            ll_content = root.findViewById(R.id.ll_content);
            this.root = root;
        }
    }
}