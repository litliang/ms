package com.yzb.card.king.ui.appwidget.picks;

import com.jamgle.pickerviewlib.adapter.WheelAdapter;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/1/2
 * 描  述：
 */
public class CountryObjectWheelAdapter implements WheelAdapter {

    private List<NationalCountryBean>  list = new ArrayList<NationalCountryBean>();

    /**
     * 添加新数据集合
     * @param list
     */
    public void addNewDataList( List<NationalCountryBean> list){

        this.list.clear();

        this.list.addAll(list);

    }
    /**
     * 添加新数据集合
     */
    public void clearDataList( ){

        this.list.clear();


    }


    @Override
    public int getItemsCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int index)
    {
        return list.get(index).getCityName();
    }

    @Override
    public int indexOf(Object o)
    {

        if(o != null ){

            String  bean =  o+"";

            int index = 0;

           for ( int a = 0 ; a < list.size() ;a ++){

               NationalCountryBean temp = list.get(a);

               if(temp.getCityName().equals(bean)){

                   index = a;

                   break;
               }
           }

            return  index;

        }



        return 0;
    }
}
