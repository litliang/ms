package com.yzb.card.king.ui.my.presenter;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.http.DataCallBack;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.my.model.NationalCountryDao;
import com.yzb.card.king.ui.my.model.NationalCountryDaoImpl;
import com.yzb.card.king.util.LogUtil;

import org.xutils.DbManager;
import org.xutils.common.util.FileUtil;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类 名： Presenter
 * 作 者： gaolei
 * 日 期：2016年12月20日
 * 描 述：国籍的Presenter
 */

public class NationalCountryPresenter implements DataCallBack {

    public static final int SUCUESS_QUERY_CHIAN_INFO_CODE = 1001;

    private NationalCountryDao dao;

    DbManager.DaoConfig daoConfig ;

    private BaseViewLayerInterface baseViewLayerInterface;

    private String typeData;

    public NationalCountryPresenter()
    {
        dao = new NationalCountryDaoImpl(this);
        File file = FileUtil.getCacheDir("");
        String path =file.getAbsolutePath();// + "/";

        daoConfig = new DbManager.DaoConfig()
                .setDbName(AppConstant.APP_DB_NAME)
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File(path)) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(AppConstant.APP_DB_VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db)
                    {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion)
                    {
                        // TODO: ...
                        LogUtil.e("oldVersion=" + oldVersion + " newVersion=" + newVersion);

                    }
                });
    }

    public void setBaseViewLayerInterface(BaseViewLayerInterface baseViewLayerInterface)
    {
        this.baseViewLayerInterface = baseViewLayerInterface;
    }

    public void getListData(Map<String, Object> map, String service, int type, String typeData)
    {
        this.typeData = typeData;

        dao.getNationalCountry(map, service, type);
    }

    /**
     * 发送请求
     * @param type
     */
    public  void sendRequest(String type){
        Map<String, Object> param = new HashMap<>();
        param.put("regionId", 1);
        param.put("type", type);
        getListData(param, CardConstant.QUREY_BASE, 1, type);
    }

    /**
     * 存储数据到数据表
     *
     * @param list
     */
    public void saveDataToDb(final List<NationalCountryBean> list, final boolean flag)
    {
        x.task().run(new Runnable() {
            @Override
            public void run()
            {

                DbManager db = x.getDb(daoConfig);

                if (flag) {

                    List<NationalCountryBean> listTemp = null;

                    try {

                        listTemp = db.selector(NationalCountryBean.class).findAll();

                    } catch (DbException e) {

                        e.printStackTrace();

                    }

                    if (listTemp != null && listTemp.size() > 0) {

                        try {

                            //删除用户数据
                            db.delete(listTemp);

                        } catch (DbException e) {

                            e.printStackTrace();

                        }
                    }
                }

                for (NationalCountryBean bn : list) {

                    try {

                        if(flag){//国内城市数据

                            bn.setIfForeign(false);

                        }else {//国外城市数据

                            bn.setIfForeign(true);
                        }
                        db.saveBindingId(bn);

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

                if(flag){

                    if(baseViewLayerInterface != null){

                        baseViewLayerInterface.callSuccessViewLogic(null,SUCUESS_QUERY_CHIAN_INFO_CODE);

                    }
                }

            }
        });

    }

    /**
     * 查询国家信息
     *
     * @return
     */
    public List<NationalCountryBean> selectCountryData()
    {
        DbManager db = x.getDb(daoConfig);
        try {

            List<NationalCountryBean> list1 = db.selector(NationalCountryBean.class).where("cityLevel", "=", "2").findAll();

            //过滤出中国香港、中国澳门、中国台湾，按序列放置到集合顶部位置
            int index = 0;

            for (int a = 0 ; a < list1.size() ; a++){

                NationalCountryBean bean = list1.get(a);

                String coutryName = bean.getCityName();

                if("中国香港".equals(coutryName)){

                    list1.remove(bean);

                    list1.add(1,bean);

                    index= index+1;

                }else if("中国澳门".equals(coutryName)){

                    list1.remove(bean);

                    list1.add(2,bean);

                    index= index+1;

                }else if("中国台湾".equals(coutryName)){

                    list1.remove(bean);

                    list1.add(3,bean);

                    index= index+1;
                }

                if(index == 3){

                    break;
                }

            }

            return list1;

        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 可分别查询国内或国际的所有城市、县城数据信息
     * @param flag   false:查询中国内的所有城市信息；true:查询国外的所有城市信息
     * @return
     */
    public List<NationalCountryBean> selectIfForeignCountryData(boolean flag)
    {
        DbManager db = x.getDb(daoConfig);
        try {

            WhereBuilder b = WhereBuilder.b();

            b.and("ifForeign", "=", flag);//条件

            b.and("cityLevel", ">", 3);//条件

            b.and("cityLevel", "<", 6);//条件

            List<NationalCountryBean> list1 = db.selector(NationalCountryBean.class).where(b).findAll();

            return list1;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 模糊搜索城市
     * @param searchKey 搜索关键字
     * @return
     */
    public List<NationalCountryBean>  dimSearchTotalCity(String searchKey){

        DbManager db = x.getDb(daoConfig);
        WhereBuilder b = WhereBuilder.b();

        b.or("cityName", "like","%"+ searchKey+"%");//条件

        b.or("cityRuby", "like", "%"+searchKey+"%");//条件

        b.or("firstSpell", "like", "%"+searchKey+"%");//条件

        try {
            List<NationalCountryBean> lists = db.selector(NationalCountryBean.class).where(b).findAll();

            if(lists!= null){

                return lists;

            }else {

                LogUtil.e("无结果");
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 根据parentId查询下级信息
     *
     * @param parentId
     */
    public List<NationalCountryBean> selectAllDataForParentIdFromDb(String parentId)
    {

        DbManager db = x.getDb(daoConfig);
        try {

            List<NationalCountryBean> list1 = db.selector(NationalCountryBean.class).where("parentId", "=", parentId).findAll();

            return list1;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据名查询
     *
     * @param contryName
     */
    public NationalCountryBean selectOneDataByNameFromDb(String contryName)
    {

        DbManager db = x.getDb(daoConfig);
        try {

            NationalCountryBean list1 = db.selector(NationalCountryBean.class).where("cityName", "=", contryName).findFirst();

            return list1;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据城市名查询四级信息
     *
     * @param contryName
     */
    public NationalCountryBean selectOneDataByCityNameFromDb(String contryName)
    {

        DbManager db = x.getDb(daoConfig);
        try {

            WhereBuilder b = WhereBuilder.b();

            b.and("cityName", "=", contryName);//条件

            b.and("cityLevel", "=", 4);//条件

            NationalCountryBean bean = db.selector(NationalCountryBean.class).where(b).findFirst();

            return bean;

        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 根据城市id查询
     *
     * @param cityId
     */
    public NationalCountryBean selectOneDataByCityIdFromDb(String cityId)
    {

        DbManager db = x.getDb(daoConfig);
        try {

            NationalCountryBean list1 = db.selector(NationalCountryBean.class).where("cityId", "=", cityId).findFirst();

            return list1;
        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 查询数据库中所有城市数据
     * @return
     */
    public List<NationalCountryBean> selectAllDataFromDb()
    {
        DbManager db = x.getDb(daoConfig);

        try {
            List<NationalCountryBean> list = db.selector(NationalCountryBean.class).findAll();
            if (list != null) {


                return list;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void requestSuccess(Object o, int type)
    {
        if (type == 1) {//如果是第一个接口

            List<NationalCountryBean> nationalListBeen = JSON.parseArray(String.valueOf(o), NationalCountryBean.class);
            //存储数据
            if (typeData.equals("1")) {
                //第一次往数据库添加数据
                saveDataToDb(nationalListBeen,true);
            }else{
                //第二次往数据库添加数据
                saveDataToDb(nationalListBeen,false);
            }


        }
    }

    @Override
    public void requestFailedDataCall(Object o, int type)
    {
        if (type == 1) {
//            if(view!=null){
//                view.onLoadFail(o, 1);
//            }

        }
    }

}
