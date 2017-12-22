package com.yzb.card.king.bean.hotel;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/7/19
 * 描  述：酒店的星级/价格
 */
public class HotelLevelBean {

    private String startName;

    private String startValue;

    private int lpIndex;

    public int getLpIndex() {
        return lpIndex;
    }

    public void setLpIndex(int lpIndex) {
        this.lpIndex = lpIndex;
    }

    private boolean isDeafault = false;

    public String getStartName()
    {
        return startName;
    }

    public void setStartName(String startName)
    {
        this.startName = startName;
    }

    public boolean isDeafault()
    {
        return isDeafault;
    }

    public void setDeafault(boolean deafault)
    {
        isDeafault = deafault;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public boolean ifSame(HotelLevelBean bean){

        if(startName.equals(bean.getStartName())&& startValue.equals(bean.getStartValue())){

            return true;
        }else {
            return  false;
        }

    }
}
