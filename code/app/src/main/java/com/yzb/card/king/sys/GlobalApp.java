package com.yzb.card.king.sys;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.AppBaseDataBean;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.ui.discount.bean.Location;
import com.yzb.card.king.ui.manage.TravelDataManager;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.util.AppUtils;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.ProgressDialogUtil;
import com.yzb.card.king.util.SharePrefUtil;
import com.yzb.card.king.util.ToastUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.Utils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * Created by gengqiyun on 2016/4/13.
 */
public class GlobalApp extends Application {

    public static boolean updateFlag = false; //是否取消过更新；

    public LocationClient mLocationClient = null;

    public BDLocationListener myListener = new MyLocationListener();

    private static Location mCity;//定位城市

    private static Location selectedCity = null;//手动选择的城市
    /**
     * 查询处三级或者四级城市定位信息资料，如上海、天津使用三级城市数据信息，合肥使用四级城市信息
     */
    private static Location threeFourCityPostionCity = null;//

    private Context context;

    private static GlobalApp instance;

    private GeoCoder mSearch; // 地理编码检索；
    /**
     * 特殊标记字段
     */
    public static boolean specialFlag = false;
    /**
     * 返回功能的特殊字段
     */
    public static boolean backFlag = false;

    public static String orderType = "";//17:码尚付；18：牵手付

    public static boolean ifOpenPermission = true;

    public static String activityStr = null;//"mfQr":二维码生成activity;"qsNfcLinked":nfc传递页面

    public BMapManager mBMapManager;

    private ImageOptions imageOptionsLogo;

    private ImageOptions imageOptionsFitXY;

    public static GlobalApp getInstance()
    {
        return instance;
    }

    /**
     * app基本数据类
     */
    private AppBaseDataBean appBaseDataBean = null;

    private Activity publicActivity;

    public Activity getPublicActivity()
    {
        return publicActivity;
    }

    public void setPublicActivity(Activity publicActivity)
    {
        this.publicActivity = publicActivity;
    }

    /**
     * 获取当前的经纬度；
     *
     * @return
     */
    public static LatLng getCurLocation()
    {
        return new LatLng(mCity.latitude, mCity.longitude);
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        this.context = getApplicationContext();
        //配置xUtil jar包
        x.Ext.init(this);
        x.Ext.setDebug(true);
        instance = this;
    }

    public void initApp(){

        baiduLBSConfig();
        umengShareConfig();
        jPushConfig();
        umengCountConfig();

        imageOptionsLogo = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(false)
                // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                .setUseMemCache(true)
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER).build();

        imageOptionsFitXY = new ImageOptions.Builder()
                .setIgnoreGif(false)
                .setUseMemCache(true)
                .setImageScaleType(ImageView.ScaleType.FIT_XY).build();

    }

    public ImageOptions getImageOptionsFitXY()
    {
        return imageOptionsFitXY;
    }

    public ImageOptions getImageOptionsLogo()
    {
        return imageOptionsLogo;
    }

    /**
     * 友盟统计配置
     */
    private void umengCountConfig()
    {
        // 开发者模式可打开，打包前需要关闭；
        MobclickAgent.setDebugMode(true);

        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "583fc42a65b6d610d9000169", "Umeng",
        // EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    /**
     * 极光推送配置；
     */
    private void jPushConfig()
    {
        // 开发者模式可打开，打包前需要关闭；
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        Set<String> tags = new HashSet<>();
        //1：系统标签：针对系统通知提示定义的tag（如：软件变更等）；
        //2：常用标签：针对系统用户分类定义的tag（如：黄金vip，钻石vip等）
        //3：临时标签：针对商家活动分类定义的tag（如：活动，优惠、红包等）
        //4：私有标签：针对个人私有标签定义的tag（如：关注的航班消息、商家动态等）
        //5：公共标签：针对商家活动有效期定义的tag（红包、优惠券过期提醒）
        tags.add("1");
        tags.add("2");
        tags.add("3");
        tags.add("4");
        tags.add("5");
        JPushInterface.setTags(this, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set)
            {
            }
        });
        JPushInterface.setAliasAndTags(this, "11111111", tags);

        //Jpush的调用参数 用来测试
        JPushLocalNotification jPushLocalNotification = new JPushLocalNotification();
        long broadcastTime = jPushLocalNotification.getBroadcastTime();
        long builderId = jPushLocalNotification.getBuilderId();
        String extras = jPushLocalNotification.getExtras();
        String title = jPushLocalNotification.getTitle();
        Log.d("gl", "broadcastTime" + broadcastTime + "builderId" + builderId + "extras" + extras + "title" + title);
        JPushInterface.addLocalNotification(this, jPushLocalNotification);

        //用户第一次注册得到的Id
        String registrationID = JPushInterface.getRegistrationID(this);
        if (!TextUtils.isEmpty(registrationID)) {
            Map<String, String> map = new HashMap<>();
            if (!TextUtils.isEmpty(registrationID)) {
                map.put("regId", registrationID);
                GlobalApp.regIdList.add(map);
            }
        }
        //用来判断用户是否设置了停止推送
        boolean pushStopped = JPushInterface.isPushStopped(this);
        Log.d("gl", "JPushInterface.isPushStopped(this)" + pushStopped);

        BasicPushNotificationBuilder bpnb = new BasicPushNotificationBuilder(getApplicationContext());
        // 修改状态栏和通知图标；
        bpnb.statusBarDrawable = R.mipmap.ic_launcher;
        JPushInterface.setDefaultPushNotificationBuilder(bpnb);
    }

    public List<Map<String, Object>> jpushData = new ArrayList<>();
    public static List<Map<String, String>> regIdList = new ArrayList<>();

    private void baiduLBSConfig()
    {
        SDKInitializer.initialize(this);
        mCity = new Location();
        selectedCity = new Location();
        mCity.msg = getString(R.string.is_located);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.setLocOption(getOption());
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mLocationClient.start();

        //检查本地记录的最近一次使用的城市信息
        String historyJsonObject =  SharePrefUtil.getValueFromSp(context,SharePrefUtil.CURRENT_HISTORY,null);

        if(historyJsonObject != null){

            Location historyLocation = JSONObject.parseObject(historyJsonObject,Location.class);

            if(historyLocation != null){

                mCity = historyLocation;

                selectedCity = new Location(mCity);
            }

        }
    }

    /**
     * 请重新定位
     */
    public void toReposition()
    {
        mCity.msg = getString(R.string.is_located);
        mLocationClient.start();
    }


    /**
     * 常用事件监听，用来处理通常的网络错误，授权验证错误等
     */
    public static class MyGeneralListener implements MKGeneralListener {
        @Override
        public void onGetPermissionState(int iError)
        {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                UiUtils.shortToast("页面初始化失败");
            }
        }
    }

    /**
     * 开始定位；
     */
    public void startLocate()
    {
        if (mLocationClient == null) {
            baiduLBSConfig();
        } else {
            mLocationClient.start();
        }
    }

    private LocationClientOption getOption()
    {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        //option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内，部是一个SERVICE，并放到了独立进程设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedAddress(true);
        return option;
    }

    /**
     * 地址-->经纬度；
     */
    public void updateLngLatByCity(final Location city)
    {
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult)
            {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    city.longitude = 0;
                    city.latitude = 0;
                    return;
                }
                //获取地理编码结果
                if (geoCodeResult.getLocation() != null) {
                    city.longitude = geoCodeResult.getLocation().longitude;
                    city.latitude = geoCodeResult.getLocation().latitude;
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult)
            {

            }
        });
        //发起地理编码检索
        mSearch.geocode(new GeoCodeOption().city(city.cityName).address(city.address));
        if (onCityChangeListeners.size() > 0) {
            for (OnCityChangeListener listener : onCityChangeListeners) {
                listener.onCityChange(selectedCity);
            }
        }
    }

    public Context getContext()
    {
        return context;
    }

    public void setSelectedCity(String cityId, String cityName)
    {
        selectedCity.cityName = cityName;
        selectedCity.cityId = cityId;
        updateLngLatByCity(selectedCity);
    }

    public void setSelectedCity(Location selectedCity)
    {
        GlobalApp.selectedCity = selectedCity;
    }

    /**
     * 转换地理数据
     *
     * @return
     */
    public Location nationalContryBeanToLocation(NationalCountryBean bean)
    {

        Location location = new Location();

        location.cityId = bean.getCityId() + "";

        location.cityName = bean.getCityName();

        location.cityLevel = bean.getCityLevel();

        location.latitude = Double.parseDouble(bean.getLat());

        location.longitude = Double.parseDouble(bean.getLng());

        selectedCity = location;

        return location;

    }

    /**
     * 转地理信息对象
     * @return
     */
    public  NationalCountryBean locationToNationalCountryBean(Location location){

        NationalCountryBean bean = new NationalCountryBean();

        bean.setCityId(Integer.parseInt(location.getCityId()));

        bean.setCityName(location.cityName);

        bean.setCityLevel(location.cityLevel);

        bean.setLat(location.latitude+"");

        bean.setLng(location.longitude+"");

        return  bean;
    }


    public static Location getSelectedCity()
    {
        return selectedCity;
    }

    public static Location getThreeFourCityPostionCity()
    {

        return threeFourCityPostionCity;
    }


    public static Location getPositionedCity()
    {
        return mCity;
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation)
        {
            if (bdLocation.getCountry() != null)//定位成功
            {
                mCity.longitude = bdLocation.getLongitude();
                mCity.latitude = bdLocation.getLatitude();
                mCity.cityName = chechCityLastChar(bdLocation.getCity());
                mCity.country = bdLocation.getCountry();
                mCity.baiduLocationDescribe = bdLocation.getLocationDescribe();
                mCity.countryCode = bdLocation.getCountryCode();
                mCity.province = bdLocation.getProvince();
                mCity.address = bdLocation.getAddrStr();
                mCity.district = bdLocation.getDistrict();
                mCity.street = bdLocation.getStreet();
                mCity.streetNumber = bdLocation.getStreetNumber();
                queryCityInfoByCityName();
            } else//定位失败
            {
                mCity.msg = getString(R.string.locate_failure_reposition);
            }
            mLocationClient.stop();

            LogUtil.e("----------------end1--------size="+onCityChangeListeners.size());

            if (onCityChangeListeners.size() > 0) {

                for (OnCityChangeListener listener : onCityChangeListeners) {

                    listener.onCityChange(mCity);

                }
            }
        }
    }

    /**
     * 根据城市名查询城市信息
     */
    public void queryCityInfoByCityName()
    {
        NationalCountryPresenter presenter = new NationalCountryPresenter();

        NationalCountryBean nationalCountryBean = presenter.selectOneDataByCityNameFromDb(mCity.cityName);
        if (nationalCountryBean != null) {
            mCity.cityId = nationalCountryBean.getCityId() + "";
            mCity.provinceId = nationalCountryBean.getParentId() + "";
            mCity.cityName = nationalCountryBean.getCityName();
            mCity.cityLevel = nationalCountryBean.getCityLevel();
            LogUtil.e(nationalCountryBean.getCityId()+"----1----"+nationalCountryBean.getCityLevel());
        }else {
        }

//        NationalCountryBean nationalCountryBeanTemp = presenter.selectOneDataByNameFromDb(mCity.cityName);
//
//        if(nationalCountryBeanTemp != null){

            threeFourCityPostionCity = new Location(mCity);
//
//            threeFourCityPostionCity.setCityId(nationalCountryBeanTemp.getCityId()+"");
//
//            threeFourCityPostionCity.setProvinceId(nationalCountryBeanTemp.getParentId()+"");
//
//            threeFourCityPostionCity.setCityName(nationalCountryBeanTemp.getCityName());
//
//            threeFourCityPostionCity.setCityLevel(nationalCountryBeanTemp.getCityLevel());
//
//            LogUtil.e(nationalCountryBeanTemp.getCityId()+"----2----"+nationalCountryBeanTemp.getCityLevel());
//        }

        selectedCity = new Location(mCity);


        presenter = null;
    }

    /**
     * 检查城市名最后一个字符是否是“市”，如果是则砍出
     *
     * @param cityName
     * @return
     */
    private String chechCityLastChar(String cityName)
    {

        int length = cityName.length();

        String charStr = cityName.substring(length - 1, length);

        if ("市".equals(charStr)) {

            cityName = cityName.substring(0, length - 1);
        }

        return cityName;
    }

    /**
     * 友盟分享；
     */
    private void umengShareConfig()
    {
        UMShareAPI.get(this);
        //微信,朋友圈；
        PlatformConfig.setWeixin(AppConstant.weixin_id, AppConstant.weixin_secret);
        PlatformConfig.setQQZone(AppConstant.qq_id, AppConstant.qq_secret);
        PlatformConfig.setSinaWeibo(AppConstant.sina_id, AppConstant.sina_secret);
//        PlatformConfig.setTencentWB(AppConstants.qq_id, AppConstants.qq_secret);

        // 关闭友盟分享的Log和Toast
        Log.LOG = false;
        //Config.isShowDialog = false;
        Config.IsToastTip = false;
        //修改默认的progress dialog
//        Config.dialog=...;
        /*
         * 微博开放平台设置的授权回调URL必须与代码中设置一致
         * 新浪分享的title是不显示的，URL链接只能加在分享文字后显示，并且需要确保withText()不为空
         */
//        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    }

    private List<OnCityChangeListener> onCityChangeListeners = new LinkedList<>();

    public void setOnCityChangeListeners(OnCityChangeListener listener)
    {
        onCityChangeListeners.add(listener);
    }

    public interface OnCityChangeListener {

        void onCityChange(Location city);
    }

    public void removeListener(OnCityChangeListener listener)
    {
        onCityChangeListeners.remove(listener);
    }

    public AppBaseDataBean getAppBaseDataBean()
    {
        return appBaseDataBean;
    }

    public void setAppBaseDataBean(AppBaseDataBean appBaseDataBean)
    {
        this.appBaseDataBean = appBaseDataBean;
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();

        //清理旅游缓存数据
        TravelDataManager.getInstance().clearData();
    }
}
