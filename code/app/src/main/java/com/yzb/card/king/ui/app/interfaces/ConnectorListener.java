package com.yzb.card.king.ui.app.interfaces;

import com.yzb.card.king.ui.app.bean.Connector;

public interface ConnectorListener {
    void onCheckChange(Connector connector);

    void onDelete(Connector connector);

    void onUpdate(Connector connector);

    void onSelect(Connector connector);
}