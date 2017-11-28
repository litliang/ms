package com.yzb.card.king.bean.hotel;

import com.yzb.card.king.bean.ticket.FilterType;
import com.yzb.card.king.bean.ticket.SortType;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/3 15:54
 */
public class HotelFilterData {
    private List<FilterType> publicList;
    private List<FilterType> pointList;
    private List<FilterType> breakfastList;
    private List<FilterType> bedList;
    private List<FilterType> positionList;
    private List<FilterType> searchList;
    //银行优惠筛选条件;0,非银行优惠；1，关联个人银行优惠；2.更多银行优惠
    private FilterType disBank = new FilterType("银行优惠", "bankStruts", "0");
    private SortType price;//价格排序
    private FilterType luxury;
    private FilterType cancel;
    private List<OnDataChangeListener> listeners = new ArrayList<>();

    public List<FilterType> getSearchList() {
        return searchList;
    }

    public void setList(ArrayList<FilterType> listPublic, List<FilterType> searchList, List<FilterType> pointList, List<FilterType>
            breakfastList, List<FilterType> bedList) {
        this.publicList = listPublic;
        this.searchList = searchList;
        this.pointList = pointList;
        this.breakfastList = breakfastList;
        this.bedList = bedList;
        dataChange();
    }

    public List<FilterType> getPositionList() {
        return positionList;
    }

    public FilterType getLuxury() {
        return luxury;
    }

    public String getCancelStatus() {
        if (publicList != null) {
            return publicList.get(2).isSelected() ? "1" : "0";
        }
        return null;
    }

    public void setLuxury(FilterType luxury) {
        this.luxury = luxury;
        dataChange();
    }

    public void setPositionList(List<FilterType> positionList) {
        this.positionList = positionList;
        dataChange();
    }

    public String getDisBankCode() {
        return disBank.getCode();
    }

    public void setDisBankCode(String code) {
        this.disBank.setCode(code);
        bankChange(code);
    }

    public SortType getPrice() {

        return price == null ? new SortType("1", "价格由低到高") : price;
    }

    public void setPrice(SortType price) {
        this.price = price;
        dataChange();
    }

    public List<FilterType> getPointList() {
        return pointList;
    }


    public List<FilterType> getBreakfastList() {
        return breakfastList;
    }


    public List<FilterType> getBedList() {
        return bedList;
    }

    public float getMinVote() {
        float minVote = Integer.MAX_VALUE;
        if (pointList != null && pointList.size() > 0) {
            for (int i = 0; i < pointList.size(); i++) {
                if (pointList.get(i).isSelected()) {
                    float vote = Float.parseFloat(pointList.get(i).getCode());
                    minVote = vote < minVote ? vote : minVote;
                }
            }
        }
        return minVote == Integer.MAX_VALUE ? 0 : minVote;
    }

    public String getBedTypes() {
        StringBuilder sb = new StringBuilder();
        if (bedList != null && bedList.size() > 0) {
            for (int i = 0; i < bedList.size(); i++) {
                sb.append(bedList.get(i).getCode());
                sb.append(",");
            }
            sb = sb.replace(sb.length() - 1, sb.length(), "");
        }

        if (publicList != null && publicList.size() >=0) {
            if (publicList.get(0).isSelected()) {
                String code = publicList.get(0).getCode();
                if(!sb.toString().contains(code))sb.append(","+code);
            }
        }
        return sb.toString();
    }

    public String getBreakfasts() {
        StringBuilder sb = new StringBuilder();
        if (breakfastList != null && breakfastList.size() > 0) {
            for (int i = 0; i < breakfastList.size(); i++) {
                sb.append(breakfastList.get(i).getCode());
                sb.append(",");
            }
            sb = sb.replace(sb.length() - 1, sb.length(), "");
        }

        if (publicList != null && publicList.size() >= 2) {
            if (publicList.get(1).isSelected()) {
                String code = publicList.get(1).getCode();
                if(!sb.toString().contains(code))sb.append(","+code);
            }
        }

        return sb.toString();
    }

    public void setListener(OnDataChangeListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    public void removeListener(OnDataChangeListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }


    public interface OnDataChangeListener {
        void onDataChange();

        void onBankChanged(String code);
    }

    private void dataChange() {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onDataChange();
        }
    }

    private void bankChange(String code) {
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).onBankChanged(code);
        }
    }

    public void reset() {
        publicList = null;
        pointList = null;
        breakfastList = null;
        bedList = null;
        positionList = null;
        searchList = null;
        luxury = null;
        disBank = new FilterType("银行优惠", "bankStruts", "0");
        price = new SortType("1", "价格由低到高");
        listeners.clear();
    }
}
