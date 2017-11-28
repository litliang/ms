package com.yzb.card.king.ui.app.holder;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.ui.app.interfaces.ConnectorListener;
import com.yzb.card.king.ui.app.popup.ConnectorPopup;
import com.yzb.card.king.util.UiUtils;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/22 15:20
 * 描述：
 */
public class ConnectorListHeaderHolder extends Holder
{
    private ConnectorListener listener;
    private View view;
    private Activity activity;
    private View llImport;
    private View llAdd;

    public ConnectorListHeaderHolder(Activity activity)
    {
        super();
        this.activity = activity;
    }

    @Override
    public View initView()
    {
        view = UiUtils.inflate(R.layout.header_connector_list);
        llImport = view.findViewById(R.id.llImport);
        llAdd = view.findViewById(R.id.llAdd);
        llImport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (addConnector != null)
                {
                    addConnector.importConnector();
                }
            }
        });

        llAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ConnectorPopup connectorPopup = new ConnectorPopup(activity, null, listener);
                connectorPopup.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });
        return view;
    }

    @Override
    public void refreshView(Object data)
    {

    }

    private AddConnector addConnector;

    public void setAddConnector(AddConnector addConnector)
    {
        this.addConnector = addConnector;
    }

    public void setListener(ConnectorListener listener)
    {
        this.listener = listener;
    }

    public interface AddConnector
    {
        void importConnector();
    }
}
