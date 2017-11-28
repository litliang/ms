package com.yzb.card.king.sys;

import android.support.v4.app.Fragment;

import com.yzb.card.king.ui.citylife.fragment.CityActivityFragment;
import com.yzb.card.king.ui.credit.fragment.CreditIndexFragment;
import com.yzb.card.king.ui.discount.fragment.DiscountIndexFragment;
import com.yzb.card.king.ui.discount.fragment.FilmMainFragment;
import com.yzb.card.king.ui.discount.fragment.MsMainFragment;
import com.yzb.card.king.ui.discount.fragment.ScpMainFragment;
import com.yzb.card.king.ui.gift.fragment.GiftMallFragment;
import com.yzb.card.king.ui.integral.fragment.IntegralMainFragment;
import com.yzb.card.king.ui.my.MyIndexFragment;
import com.yzb.card.king.ui.my.fragment.CouponsMallFragment;
import com.yzb.card.king.ui.ticket.fragment.TicketHomeFragment;
import com.yzb.card.king.ui.transport.fragment.BusTicketFragment;
import com.yzb.card.king.ui.transport.fragment.CharterCarFragment;
import com.yzb.card.king.ui.transport.fragment.SelfDrivingFragment;
import com.yzb.card.king.ui.transport.fragment.ShipTicketFragment;
import com.yzb.card.king.ui.transport.fragment.ShuttlePlaneFragment;
import com.yzb.card.king.ui.transport.fragment.ShuttleTrainFragment;
import com.yzb.card.king.ui.transport.fragment.SpecialCarFragment;
import com.yzb.card.king.ui.transport.fragment.TrainTicketFragment;
import com.yzb.card.king.ui.transport.fragment.TransportFragment;
import com.yzb.card.king.ui.travel.fragment.TravelMainFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：App工厂类
 * 作  者：Li Yubing
 * 日  期：2016/9/2
 * 描  述：收集、管理和分配各个频道需要用到的业务类
 */
public class AppFactory {

    /**
     *   获取主页tab的业务碎片集合
     * @return
     */
    public static List<Fragment> getHomeTabFragmentList(){

        List<Fragment>   fragments = new ArrayList<>();
        //信用卡 0
        fragments.add(new CreditIndexFragment());
        //金融生活 1
        fragments.add(new IntegralMainFragment());
        //商户优惠  2
        fragments.add(new DiscountIndexFragment());
        //我的 3
        fragments.add(new MyIndexFragment());
//        //礼品卡商城 4
//        fragments.add(new GiftMallFragment());
//        //优惠卷商城 5
//        fragments.add(new CouponsMallFragment());

        return  fragments;
    }

    /**
     * 根据频道的typeId，转换成本地相对应的模块fragment编号
     * @param channelId
     * @return  返回的值表示在getAllOneChannel方法里面的计划，此业务类所在的位置, -1表示暂时尚未开发
     */
    public static int channelIdToFragmentIndex(String channelId)
    {

        int fragmentIndex = 0;

         if (channelId.equals(AppConstant.ticket_id)) {//机票

            fragmentIndex = 1;

        }  else if (channelId.equals(AppConstant.travel_id)) {//旅游

            fragmentIndex = 0;

        }else if (channelId.equals(AppConstant.hotel_id)) {//酒店

             fragmentIndex = 2;

         }
        else {
            fragmentIndex = -1;
        }

        return fragmentIndex;
    }

    /**
     * 得到所有的一级频道碎片
     *
     * @return
     */
    public static List<Fragment> getAllOneChannel()
    {
        List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
         fragments.add(TravelMainFragment.newInstance("", ""));

        return fragments;
    }
////////////////////////////////陆上交通////////////////////////////////////////////////////////

    /**
     * 陆地交通---创建陆上交通各个子频道业务碎片
     *
     * @param typeId
     * @return
     */
    public static Fragment createFragment(String typeId)
    {
        Fragment fragment = null;
        int id = Integer.parseInt(typeId);
        switch (id) {
            case 77://火车票
                // 火车票
                fragment = TrainTicketFragment.newInstance();
                break;
            case 78://车票
                // 汽车票
                fragment = BusTicketFragment.newInstance();
                break;
            case 79://船票
                // 船票
                fragment = ShipTicketFragment.newInstance();
                break;
            case 80://接送机
                // 接送机
                fragment = ShuttlePlaneFragment.newInstance();
                break;
            case 81://接送车
                // 接送火车
                fragment = ShuttleTrainFragment.newInstance();
                break;
            case 82://专车出行
                // 专车出行
                fragment = SpecialCarFragment.newInstance();
                break;
            case 83://自驾租车
                fragment = SelfDrivingFragment.newInstance();
                break;
            case 84://日租包车
                fragment = CharterCarFragment.newInstance();
                break;
        }
        return fragment;
    }
}
