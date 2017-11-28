package com.yzb.card.king.ui.order.model;

import java.util.Map;

/**
 * Created by sushuiku on 2016/11/5.
 */

public interface HotelTicketDetailDAO {
    void sendRequst(String serviceName,Map<String, Object> paramMap);
}
