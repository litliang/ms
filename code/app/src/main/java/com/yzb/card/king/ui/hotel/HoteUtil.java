package com.yzb.card.king.ui.hotel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yzb.card.king.bean.common.PayFormIf;
import com.yzb.card.king.bean.order.HotelOrderDetailBean;
import com.yzb.card.king.bean.order.HotelOrderServeBean;
import com.yzb.card.king.bean.order.OrderBean;
import com.yzb.card.king.bean.order.ProductBaseOrderBean;
import com.yzb.card.king.sys.AppConstant;
import com.yzb.card.king.ui.hotel.activtiy.HotelOnlineServiceActivity;
import com.yzb.card.king.ui.hotel.activtiy.HotelReturnMoneyRateActivity;

/**
 * 类  名：
 * 作  者：Li Yubing
 * 日  期：2017/8/3
 * 描  述：
 */
public class HoteUtil {

    /**
     * 1房间、2餐厅、3酒吧、4会场、5SPA、6大堂吧、7卡权益、8限时抢购、9健身房、10游泳池
     */

    public final  static String HOTEL_ROOM_CODE ="1";

    public final static String HOTEL_DINING_ROOM_CODE ="2";

    public final static String  HOTEL_BAR_CODE ="3";

    public final static String HOTEL_MEETING_PLACE_CODE="4";

    public final static String HOTEL_SPA_CODE="5";

    public final static String HOTEL_LOBBY_BAR_CODE="6";

    public final static String HOTEL_CARD_EQUITY_CODE="7";

    public final static String HOTEL_FAST_BUY_CODE="8";

    public final static String HOTEL_GYM_CODE="9";

    public final static String HOTEL_SWMMING_POOL_CODE="10";

    /**
     * 催确认、查询酒店信息、如何开发票、查看确认信息、取消订单、如何返现、支付遇到问题、担保何时退还、忘记开发票、补开发票、发票何时寄送、联系客服
     */
    public final static int HOTEL_CUIKUANQUEREN_CODE = 7001;
    public final static int HOTEL_CHAXUNJIUDIANXIN_CODE = 7002;
    public final static int HOTEL_RUHEKAIFAPIAO_CODE = 7003;
    public final static int HOTEL_CHAKANQUERENXINXI_CODE = 7004;
    public final static int HOTEL_CANCELORDER_CODE = 7005;
    public final static int HOTEL_RUHEFANXIAN_CODE = 7006;
    public final static int HOTEL_YUDAOWENTI_CODE = 7007;
    public final static int HOTEL_DANBAOHESHITUIHUAN_CODE = 7008;
    public final static int HOTEL_WANGJIKAIFAPIAO_CODE  = 7009;
    public final static int HOTEL_BUKAIFAPIAO_CODE = 7010;
    public final static int HOTEL_FAPIAOHESHIJISONG_CODE = 7011;
    public final static int HOTEL_LIANXIKEFU_CODE = 7012;

    /**
     * 酒店问题处理
     * @param activity
     * @param code
     */
    public static void hotelQuestionAction(Activity activity, HotelOrderServeBean bean, int code){

        int orderStatus = bean.getOrderStatus();//订单状态

        if(code == HOTEL_RUHEFANXIAN_CODE && OrderBean.ORDER_STATUS_YILIDIAN ==orderStatus ){//如何返现--已离店，进入发现进度

            /**
             * 订单id
             */
             long orderId = bean.getOrderId();
            /**
             * 类型
             */
             int hotelType = Integer.parseInt(AppConstant.hotel_id);
            /**
             * 返现价格
             */
             float money = bean.getCashbackAmount();
            /**
             * 状态
             */
             int orderStutas = bean.getOrderStatus() ;


            Intent intent = new Intent(activity, HotelReturnMoneyRateActivity.class);

            intent.putExtra("orderId",orderId);
            intent.putExtra("hotelType",hotelType);
            intent.putExtra("money",money);
            intent.putExtra("orderStutas",orderStutas);

            activity.startActivity(intent);

        }else {

            Intent intent = new Intent(activity, HotelOnlineServiceActivity.class);

            intent.putExtra("orderInfo",bean);

            intent.putExtra("code",code);

            activity.startActivity(intent);

        }

    }

    /**
     * 酒店评分信息
     * @param vote
     * @param tvHoteVoteMessage
     */
    public static void hotelVoteMessage(double vote, TextView tvHoteVoteMessage){

        tvHoteVoteMessage.setVisibility(View.VISIBLE);

        if(vote>=4.5d){

            tvHoteVoteMessage.setText("很好");

        }else if(vote>=4d && vote< 4.5d){

            tvHoteVoteMessage.setText("好");

        }else if(vote>=3d && vote< 4d){

            tvHoteVoteMessage.setText("不错");

        }else {

            tvHoteVoteMessage.setText("");
            tvHoteVoteMessage.setVisibility(View.GONE);
        }

    }

    /**
     *  设置支付方式文字信息
     * @param paymentType  1到店付、2在线支付、3担保支付
     * @param textView
     */

    public static void payTypeSetMessage( int paymentType,TextView textView){

        if(paymentType == PayFormIf.PAYMENT_TYPE_DAODIANFU){

            textView.setText("到店付");

        }else  if(paymentType == PayFormIf.PAYMENT_TYPE_ONLINE){

            textView.setText("在线支付");

        }else  if(paymentType ==PayFormIf.PAYMENT_TYPE_DANBAO){

            textView.setText("担保支付");

        }

    }

    /**
     * 解析餐种信息
     * @param sbOne
     * @param mealTypesStr
     */
    public static void handleMealTypes(StringBuffer sbOne, String mealTypesStr)
    {

        int index = mealTypesStr.indexOf(",");

        if (index != -1) {

            String[] strArray = mealTypesStr.split(",");

            int length = strArray.length;

            for (int i = 0; i < length; i++) {

                int a = Integer.parseInt(strArray[i]);

                if (a == 0) {

                    sbOne.append("不限 ");

                } else if (a == 1) {

                    sbOne.append("限早餐");

                } else if (a == 2) {

                    sbOne.append("限午餐");

                } else if (a == 3) {

                    sbOne.append("限晚餐");

                }

                if (i > 0 && i != length - 1) {
                    sbOne.append("、");
                }

            }

        }else {
            int a = Integer.parseInt(mealTypesStr);

            if (a == 0) {

                sbOne.append("不限 ");

            } else if (a == 1) {

                sbOne.append("限早餐");

            } else if (a == 2) {

                sbOne.append("限午餐");

            } else if (a == 3) {

                sbOne.append("限晚餐");

            }

        }
    }

    /**
     * 处理套餐人数信息
     * @param sbOne
     * @param policysLimits
     */
    public static void handlerPolicysLimits(StringBuffer sbOne, String policysLimits)
    {
        int lengthPl = policysLimits.lastIndexOf(",");

        if (lengthPl != -1) {

            String[] policysLimitsArray = policysLimits.split(",",-1);

            String mineStrOne = policysLimitsArray[0];

            String mineStrTwo = policysLimitsArray[1];

            sbOne.append(mineStrOne + "-" + mineStrTwo + "人");

        } else {

            sbOne.append(policysLimits + "人");
        }

    }

    /**
     * 处理退款信息
     * @param tvCancelStatus
     * @param cancelStatusStr
     */
    public static void handlerRefundMessage(TextView tvCancelStatus,String cancelStatusStr){

        tvCancelStatus.setVisibility(View.VISIBLE);

        if ("1".equals(cancelStatusStr)) {

            tvCancelStatus.setText("过期自动退");

        } else if ("2".equals(cancelStatusStr)) {

            tvCancelStatus.setText("随时退");

        } else if ("3".equals(cancelStatusStr)) {

            tvCancelStatus.setText("不可退");

        } else {
            tvCancelStatus.setVisibility(View.GONE);
        }
    }
}
