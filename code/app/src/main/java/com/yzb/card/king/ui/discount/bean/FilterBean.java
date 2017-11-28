package com.yzb.card.king.ui.discount.bean;

import java.util.List;

/**
 * Created by gengqiyun on 2016/5/12.
 * "id": "1",
 * "filterName": "优惠",
 * "filterList": [{
 * "id": "0",
 * "name": "不限",
 * "value": "0"
 * filterType
 * },
 */
public class FilterBean {
    public String id;
    public String filterName;
    public List<FilterBean> filterList;
    public String name;
    public String value;
    public String filterType;

    public boolean isSelected;
}
