package com.yzb.card.king.ui.transport.bean;

import java.util.List;

/**
 * Created by yinsg on 2016/5/31.
 */
public class ShipRoute {
    public String title;
    public List<Route> lineList;

    public static class Route{
        public String startCityId;
        public String startCityName;
        public String endCityId;
        public String endCityName;

    }
}
