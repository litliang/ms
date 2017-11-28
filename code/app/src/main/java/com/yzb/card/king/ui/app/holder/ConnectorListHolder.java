package com.yzb.card.king.ui.app.holder;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.app.bean.Connector;
import com.yzb.card.king.ui.app.interfaces.ConnectorListener;
import com.yzb.card.king.ui.app.popup.ConnectorPopup;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/22 14:38
 * 描述：
 */
public class ConnectorListHolder extends Holder<Connector>
{
    private TextView tvName;
    private TextView tvMobile;
    private ImageView ivEdit;
    private Connector connector;
    private ImageView tvDelete;
    private View view;
    private View llContent;
    private Activity activity;
    private ImageView ivDot;

    public ConnectorListHolder(Activity activity)
    {
        super();
        this.activity = activity;
    }

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.item_settings_connctor);
        llContent = view.findViewById(R.id.llContent);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvMobile = (TextView) view.findViewById(R.id.tvMobile);
        ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        ivDot = (ImageView) view.findViewById(R.id.ivDot);

        tvDelete = (ImageView) view.findViewById(R.id.tvDelete);

        view.findViewById(R.id.panelSetDefault).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (listener != null)
                {
                    listener.onCheckChange(connector);
                }
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onDelete(connector);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ConnectorPopup connectorPopup = new ConnectorPopup(activity, connector, listener);
                connectorPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        llContent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onSelect(connector);
            }
        });
        return view;
    }

    @Override
    public void refreshView(Connector data)
    {
        connector = data;
        tvName.setText(data.nickName);
        tvMobile.setText(data.mobile);
        ivDot.setSelected(data.isDefault);
    }

    private ConnectorListener listener;

    public void setOnCheckChangeListener(ConnectorListener listener)
    {
        this.listener = listener;
    }
}
