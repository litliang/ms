package com.yzb.card.king.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yzb.card.king.bean.order.FastPaymentOrderBean;
import com.yzb.card.king.ui.appwidget.popup.GetPayMsgDialog;
import com.yzb.card.king.bean.common.PayMethod;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * app手机系统自带的公共方法
 * Created by tarena on 2016/6/1.
 */
public class AppUtils
{

    public static final int PICK_CONTACT = 0;
    private static LocationManager manager;

    /**
     * 读取assets资源文件夹下的图片
     *
     * @param mContext 上下文 实例化AssetsManage
     * @param fileName 图片名称
     * @return
     */
    public static Bitmap getImageFromAssets(Context mContext, String fileName)
    {

        Bitmap bitmap = null;
        // Ass管理器
        AssetManager manager = mContext.getAssets();
        InputStream is = null;
        try
        {
            is = manager.open("images/" + fileName);
            // 将is转换成bitmap
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    /**
     * 读取assets资源文件夹下的图片
     *
     * @param activity 上下文 实例化AssetsManage
     * @param fileName 图片名称
     * @return
     */
    public static Bitmap getImageFromAssets(Activity activity, String fileName)
    {
        Bitmap bitmap = null;
        // Ass管理器
        AssetManager manager = activity.getResources().getAssets();
        InputStream is = null;
        try
        {
            is = manager.open("images/" + fileName);
            // 将is转换成bitmap
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 调用手机联系人
     *
     * @param context
     */
    public static void callContact(Fragment context)
    {
        //联系人选择
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        context.startActivityForResult(intent, PICK_CONTACT);
    }

    /**
     * 调用手机联系人
     *
     * @param context
     */
    public static void callContact(Activity context)
    {
        //联系人选择
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        context.startActivityForResult(intent, PICK_CONTACT);
    }

    /**
     * 检查是否开启GPS
     *
     * @param context
     * @return
     */
    public static boolean checkIsGPSOpen(Context context)
    {
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 解析选中的联系人
     *
     * @param act
     * @param data
     * @return 0:联系人；1：联系人手机号
     */
    public static String[] analyContactData(Activity act, Intent data)
    {
        String[] contactDataArray = new String[2];

        ActivityManager am = (ActivityManager) act.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : infos)
        {
            if (!"com.yzb.card.king".equals(info.processName))
                continue;

            if (act.checkPermission(Manifest.permission.READ_CONTACTS, info.pid, info.uid) == PackageManager.PERMISSION_GRANTED)
            {
                Intent query = new Intent(Intent.ACTION_MAIN);
                query.addCategory("android.intent.category.LAUNCHER");

                Uri contactData = data.getData();
                Cursor cursor = act.managedQuery(contactData, null, null, null, null);
                int contactIdIndex = 0;
                if (cursor.getCount() > 0)
                {
                    contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                }

                if (cursor.moveToFirst())
                {
                    String contactId = cursor.getString(contactIdIndex);
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String phoneNumber = null;
                    if (hasPhone.equalsIgnoreCase("1"))
                    {
                        Cursor phones = act.getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                                , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext())
                        {
                            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneNumber = phoneNumber.replaceAll("-", "");
                            phoneNumber = phoneNumber.replaceAll(" ", "");

                            if (phoneNumber.length() > 11)
                            {
                                ToastUtil.i(act, "手机号有误");
                                return contactDataArray;
                            }

                            String linkMan = name;

                            String linkPhone = phoneNumber;

                            contactDataArray[0] = linkMan;

                            contactDataArray[1] = linkPhone;

                            break;
                        }
                        phones.close();
                        return contactDataArray;
                    }
                }
            } else
            {
                ToastUtil.i(act, "此应用没有配置读取联系人权限");
            }
        }

        return contactDataArray;
    }

    /**
     * 对金额处理
     *
     * @param l
     * @return
     */
    public static String handleNumberStr(double l)
    {

        StringBuffer sb = new StringBuffer();
        if (l >= 0 && l < 10000)
        {

            BigDecimal b = new BigDecimal(l);
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            String lTemp = String.valueOf(f1);
            ;

            String[] str = lTemp.split("\\.");

            if (str.length == 2)
            {


                int xiaoshu = Integer.parseInt(str[1]);

                if (xiaoshu > 0)
                {
                    sb.append(lTemp);
                } else
                {
                    sb.append((int) l + "");
                }

            }


        } else if (l >= 10000 && l < 100000000)
        {

            double a = l / 10000;

            BigDecimal b = new BigDecimal(a);
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            String lTemp = String.valueOf(f1);
            ;
            String[] str = lTemp.split("\\.");

            if (str.length == 2)
            {
                int xiaoshu = Integer.parseInt(str[1]);

                if (xiaoshu > 0)
                {

                    sb.append(lTemp + "万");

                } else
                {
                    sb.append(str[0] + "万");
                }
            }


        } else if (l >= 100000000)
        {


            double a = l / 100000000;


            BigDecimal b = new BigDecimal(a);
            double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            String lTemp = String.valueOf(f1);
            ;

            String[] str = lTemp.split("\\.");

            if (str.length == 2)
            {
                int xiaoshu = Integer.parseInt(str[1]);
                if (xiaoshu > 0)
                {

                    sb.append(lTemp + "亿");

                } else
                {
                    sb.append(str[0] + "亿");
                }
            }

        }

        return sb.toString();
    }

    /**
     * 隐藏软键盘显示光标
     *
     * @param activity
     * @param et
     */
    public static void hideSoftShowCursor(Activity activity, EditText et)
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT <= 10)
        {
            // 点击EditText，屏蔽默认输入法
            et.setInputType(InputType.TYPE_NULL); // editText是声明的输入文本框。
        } else
        {
            // 点击EditText，隐藏系统输入法
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try
            {
                Class<EditText> cls = EditText.class;
                Method method = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);// 4.0的是setShowSoftInputOnFocus，4.2的是setSoftInputShownOnFocus
                method.setAccessible(false);
                method.invoke(et, false); // editText是声明的输入文本框。
            } catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    LogUtil.LCi(" 当前网络链接状态 ");
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        LogUtil.LCi("  当前网络链接失败状态 ");
        return false;
    }


    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context)
    {
        try
        {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex)
        {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
    }

    /**
     * 隐藏键盘
     *
     * @param act
     * @param edit
     */
    public static void hideSoftWindow(Activity act, EditText edit)
    {

        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    /**
     * 计算两地的距离
     */
    public static double calculateDistance(LatLng totalLL, LatLng selfLL)
    {

        double distance = DistanceUtil.getDistance(totalLL, selfLL);

        return distance;
    }


    /**
     * 检查付款方式是否可以通过平台验证码发送转账验证码
     * @param method
     * @return
     */
    public static  boolean ifPlatorm(PayMethod method){

        String bankCode = method.getBankCode();

        if (TextUtils.isEmpty(bankCode) || "spdb".equals(bankCode)) {

            return  false;

        } else {
            return true;
        }

    }

    private static List<String> specialCityList = null;
    /**
     * 检查是否是直辖市
     * @return true
     */
    public static boolean ifSpecialCityInfo(String name){

        if(name == null){

            return  false;
        }

        if(specialCityList==null){

            specialCityList = new ArrayList<String>();
            specialCityList.add("北京市");
            specialCityList.add("天津市");
            specialCityList.add("上海市");
            specialCityList.add("重庆市");

        }

        boolean flag = false;

        for(String str : specialCityList){

            int index = str.indexOf(name);

            if(index != -1){

                flag = true;

                break;
            }

        }

        return  flag;
    }

    /**
     * 转账成功信息提示
     * @param activity
     * @param flagPayType
     * @param fastPaymentOrderBean
     */
    public static void  transferInforRemind(final Activity activity, boolean flagPayType, FastPaymentOrderBean fastPaymentOrderBean){
        if (fastPaymentOrderBean != null) {
            String a = "1";
            String strTemp;
            if (flagPayType) {//付款方
                a = "2";
                strTemp = "付款成功";
            } else {//收款方

                a = "1";
                strTemp = "收款成功";
            }
            final GetPayMsgDialog.Builder msgBuilder = new GetPayMsgDialog.Builder(activity);
            msgBuilder.setColor(a);//1：蓝色标题 2：红色标题
            msgBuilder.setMsg(strTemp, fastPaymentOrderBean.getOrderNo(), fastPaymentOrderBean.getOrderTime(), fastPaymentOrderBean.getPaymentDesc(), strTemp, fastPaymentOrderBean.getOrderAmount());
            GetPayMsgDialog dialog = msgBuilder.create();
            dialog.show();
            TimerTask task = new TimerTask() {
                public void run()
                {
                    msgBuilder.dismiss();
                    // 返回
                    activity.finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 3000);
        }
    }

    public static String ToCH(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (GetCH(intInput / 10) + "十");
            sd += ToCH(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (GetCH(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += ToCH(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (GetCH(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += ToCH(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (GetCH(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += ToCH(intInput % 10000);
        }

        return sd;
    }

    private static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }



}
