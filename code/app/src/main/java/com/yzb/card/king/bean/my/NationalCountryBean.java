package com.yzb.card.king.bean.my;

import android.text.TextUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 类 名： 国籍选择
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：
 */
@Table(name="NationCountryTbbb")
public class NationalCountryBean implements Serializable{


    @Column(name="_id",isId = true,autoGen = true)
    private int _id;
    /**
     * 城市名称
     */
    @Column(name = "cityName")
    private String cityName;
    /**
     * 城市等级
     */
    @Column(name = "cityLevel")
    private int cityLevel;
    /**
     * 城市首拼音
     */
    @Column(name = "firstSpell")
    private String firstSpell;
    /**
     * 城市id
     */
    @Column(name = "cityId")
    private int cityId;
    /**
     * 上级id
     */
    @Column(name="parentId")
    private int parentId;
    /**
     * 城市拼音
     */
    @Column(name = "cityRuby")
    private String cityRuby;
    /**
     * 经度
     */
   @Column(name = "lng")
    private String lng;
    /**
     * 维度
     */
   @Column(name = "lat")
    private String lat;

    @Column(name = "ifForeign")
   private boolean ifForeign = false;

    private String type;

    private  String firstLetter ;

    public String getFirstLetter()
    {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter)
    {
        this.firstLetter = firstLetter;
    }

    public NationalCountryBean(String cityName, int _id) {
        this.cityName = cityName;
        this._id = _id;
    }

    public NationalCountryBean() {
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(int cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getFirstSpell() {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCityRuby() {
        return cityRuby;
    }

    public void setCityRuby(String cityRuby) {
        this.cityRuby = cityRuby;
    }

    public String getLng()
    {
        return TextUtils.isEmpty(lng)?"0":lng;
    }

    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getLat()
    {
        return TextUtils.isEmpty(lat)?"0":lat;
    }

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public boolean isIfForeign()
    {
        return ifForeign;
    }

    public void setIfForeign(boolean ifForeign)
    {
        this.ifForeign = ifForeign;
    }
}
