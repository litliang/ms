package com.yzb.card.king.ui.manage;

/**
 * 类  名：地址搜索管理
 * 作  者：Li Yubing
 * 日  期：2016/6/2
 * 描  述：
 */
public class AddressSearchManager {

    private static AddressSearchManager instance = null;

    /**
     * 地理信息ID
     */
    private String addrId = null;
    /**
     * 地理信息名称
     */
    private String addrName = null;

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    private String street = null;

    private boolean ifSelectAddress = false;

    private AddressSearchManager() {

    }

    public static AddressSearchManager getInstance() {

        if (instance == null) {

            instance = new AddressSearchManager();
        }

        return instance;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public boolean isIfSelectAddress() {
        return ifSelectAddress;
    }

    public void setIfSelectAddress(boolean ifSelectAddress) {
        this.ifSelectAddress = ifSelectAddress;
    }

    /**
     * 清理缓存数据
     */
    public void clearData() {

        addrName = null;

        street = null;

        addrId = null;
    }


}
