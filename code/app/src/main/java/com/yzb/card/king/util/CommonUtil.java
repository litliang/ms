package com.yzb.card.king.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.bean.travel.TravelLineBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.app.interfaces.XUtilCommonCallback;
import com.yzb.card.king.ui.discount.bean.ChildTypeBean;
import com.yzb.card.king.ui.travel.view.ITextClickCall;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gqy on 2016/2/23.
 */
public class CommonUtil
{

    public static void callHotelPhone(Context context,String number)
    {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str)
    {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input)
    {

        input = stringFilter(input);

        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == 12288)
            {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 数组转中文格式；
     *
     * @return
     */
    public static String numberToStr(int number)
    {
        String[] numArray = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        if (number > 0 && number < numArray.length)
        {
            return numArray[number - 1];
        }
        return null;
    }

    /**
     * ArrayList  MyPlan中对象按照属性planType进行分类
     *
     * @param list
     * @return
     */
    public static Map<String, ArrayList<TravelLineBean.MyPlan>> sort(List<TravelLineBean.MyPlan> list)
    {
        Map tm = new TreeMap();
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                TravelLineBean.MyPlan t = list.get(i);

                if (tm.containsKey(t.getPlanType()))
                {
                    ArrayList array = (ArrayList) tm.get(t.getPlanType());
                    array.add(t);
                } else
                {
                    ArrayList tem = new ArrayList();
                    tem.add(t);
                    tm.put(t.getPlanType(), tem);
                }
            }
        }
        return tm;
    }

    /**
     * 改变view的width和height；
     *
     * @param view
     * @param dpWidthValue
     * @param dpHeightValue
     */
    public static void changeViewMeasure(View view, int dpWidthValue, int dpHeightValue)
    {
        if (view != null)
        {
            view.getLayoutParams().width = CommonUtil.dip2px(view.getContext(), dpWidthValue);
            view.getLayoutParams().height = CommonUtil.dip2px(view.getContext(), dpHeightValue);
        }
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmptyCharacter(String input)
    {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String text)
    {
        if (TextUtils.isEmpty(text))
        {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String text)
    {
        if (!TextUtils.isEmpty(text))
        {
            return true;
        }
        return false;
    }

    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context)
    {
        return (int) (context.getResources().getDisplayMetrics().widthPixels + 0.5);
    }

    public static int getScreenHeight(Context context)
    {
        return (int) (context.getResources().getDisplayMetrics().heightPixels + 0.5);
    }

    /**
     * 剔除localLists中不存在的数据项；
     *
     * @param localLists         本地的List；
     * @param displayChannelList 所有的频道LIst；
     * @return
     */
    public static List<ChildTypeBean> filterList(List<ChildTypeBean> localLists, List<ChildTypeBean> displayChannelList)
    {
        if (localLists == null || localLists.size() == 0)
        {
            return displayChannelList;
        }
        if (displayChannelList == null || displayChannelList.size() == 0)
        {
            return localLists;
        }

        List<ChildTypeBean> newList = new ArrayList<>();
        //遍历localLists中每一项，和displayChannelList中的所有项比较；
        //id相同，更新，否则添加；

        int size1 = localLists.size();
        int size2 = displayChannelList.size();
        boolean isExist = false;//displayChannelList中是否存在与localLists相同的数据项；
        String id1;
        String id2;
        ChildTypeBean equalBean = null;//迭代中相同的bean；

        for (int i = 0; i < size1; i++)
        {
            id1 = localLists.get(i).id;
            for (int j = 0; j < size2; j++)
            {
                id2 = displayChannelList.get(j).id;
                //发现相同的；
                if (!TextUtils.isEmpty(id1) && id1.equals(id2))
                {
                    LogUtil.i("发现相同的id1===" + id1);
                    isExist = true;
                    equalBean = displayChannelList.get(j);
                    break;
                }
            }
            //存在相同的；
            if (isExist && equalBean != null)
            {
                newList.add(equalBean);
            }
            isExist = false;
            equalBean = null;
        }
        return newList;
    }

    /**
     * 剔除displayChannelList中 在myItems中已经存在的item；
     *
     * @param partItems 筛选项；
     * @param allItems  所有的bean；
     * @return
     */
    public static List<ChildTypeBean> filterList2(List<ChildTypeBean> partItems, List<ChildTypeBean> allItems)
    {
        if (partItems == null || partItems.size() == 0)
        {
            return allItems;
        }
        if (allItems == null || allItems.size() == 0)
        {
            return null;
        }

        int allLen = allItems.size();
        int partLen = partItems.size();
        boolean isExist = false;
        //迭代所有bean；
        List<ChildTypeBean> targetBeans = new ArrayList<>();
        String partId;
        String allId;
        for (int i = 0; i < allLen; i++)
        {
            allId = allItems.get(i).id;
            //遍历；
            for (int j = 0; j < partLen; j++)
            {
                partId = partItems.get(j).id;
                //发现相同的；
                if (!TextUtils.isEmpty(allId) && allId.equals(partId))
                {
                    isExist = true;
                    break;
                }
            }
            //添加不同的；
            if (!isExist)
            {
                targetBeans.add(allItems.get(i));
            }
            isExist = false;
        }
        return targetBeans;
    }

    /**
     * 分钟转化为小时；
     *
     * @param trafficLength 分钟数量；
     * @return
     */
    public static String formatMmToHh(int trafficLength)
    {
        if (trafficLength < 60)
        {
            return trafficLength + "分钟";
        } else
        {
            return trafficLength % 60 == 0 ? trafficLength / 60 + "小时" :
                    trafficLength / 60 + "小时" + trafficLength % 60 + "分钟";
        }
    }

    /**
     * 获取字符串中某个字符的数量；
     *
     * @param scenerySpot
     * @param charS
     */
    public static int getCharNum(String scenerySpot, char charS)
    {
        if (!TextUtils.isEmpty(scenerySpot))
        {
            char[] chars = scenerySpot.toCharArray();
            int num = 0;
            for (int i = 0; i < chars.length; i++)
            {
                if (chars[i] == charS)
                {
                    num++;
                }
            }
            return num;
        }

        return 0;
    }

    /**
     * 打开输入法；
     *
     * @param v EditText 实例
     * @param c
     */
    public static void showKeyboard(View v, Context c)
    {
        if (v != null && c != null)
        {
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 打开输入法；
     *
     * @param v EditText 实例
     * @param c
     */
    public static void hideKeyboard(View v, Context c)
    {
        if (v != null && c != null)
        {
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    /**
     * 获取规定数量的字符数；（不包含空格）
     *
     * @return
     * @content 含空格；
     */
    public static String getSubStr(String content, int maxLen)
    {
        if (!TextUtils.isEmpty(content))
        {
            //空格数量；
            int spaceCount = getCharNum(content, ' ');
            //非空字符超过字数上限；
            if (content.length() - spaceCount > maxLen)
            {
                return content.substring(0, spaceCount + maxLen) + "...";
            }
        }
        return content;
    }


    /**
     * List<T>转  T[]
     *
     * @param mList
     */
    public static String[] convertListToStrArray(List<String> mList)
    {
        if (mList != null && mList.size() > 0)
        {
            String[] arrayT = new String[mList.size()];
            for (int i = 0; i < mList.size(); i++)
            {
                arrayT[i] = mList.get(i);
            }
            return arrayT;
        }
        return null;
    }

    /**
     * 给关键字着色；
     *
     * @param msg
     * @param keywords
     * @param colorResId
     * @param args
     * @param callBack
     * @return
     */
    public static CharSequence colourTxt(CharSequence msg, String keywords, int colorResId, String args,
                                         ITextClickCall callBack)
    {
        if (!TextUtils.isEmpty(msg))
        {
            int startIndex = 0;
            int keywordsLen = keywords.length();
            SpannableString ssb = new SpannableString(msg);

            while (startIndex < msg.length())
            {
                //找到第一个；
                int index = msg.toString().indexOf(keywords, startIndex);
                //找到关键字；
                if (index != -1)
                {
                    ForegroundColorSpan span = new ForegroundColorSpan(colorResId);
                    ssb.setSpan(span, index, index + keywordsLen, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    //设置文本点击事件；
                    if (callBack != null)
                    {
                        setTextClickableSpan(ssb, index, index + keywordsLen, colorResId, args, callBack);
                    }
                    //更新起始位置；
                    startIndex = index + keywordsLen + 1;
                } else
                {
                    //找不到直接退出；
                    break;
                }
            }
            return ssb;
        }
        return msg;
    }

    /**
     * 添加可点击区域；
     *
     * @param ssb
     * @param startIndex
     * @param endIndex
     * @param id         点击入参参数；
     * @param callBack
     */
    private static void setTextClickableSpan(SpannableString ssb, int startIndex, int endIndex, final int colorResId, final String id,
                                             final ITextClickCall callBack)
    {
        LogUtil.i("startIndex=" + startIndex + ",endIndex=" + endIndex);

        ssb.setSpan(new TextClickableSpan()
        {
            @Override
            public void onClick()
            {
                LogUtil.i("点击事件========");
                if (callBack != null)
                {
                    callBack.callBack(id);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds)
            {
                ds.setColor(colorResId);
                ds.setUnderlineText(false);
            }
        }, startIndex, endIndex, 0);
    }


    /**
     * 将Image code码列表转换为 url；
     *
     * @param imageCodes
     * @return
     */
    public static List<String> convetCodesToUrls(List<String> imageCodes)
    {
        if (imageCodes != null && imageCodes.size() > 0)
        {
            for (int i = 0; i < imageCodes.size(); i++)
            {
                imageCodes.set(i, ServiceDispatcher.getImageUrl(imageCodes.get(i)));
            }
        }
        return imageCodes;
    }


    /**
     * 获取礼品卡分享内容；
     *
     * @param commandAndUrl 口令和url  以#分割；
     */
    public static String getGiftcardShareContent(String prefix, String commandAndUrl)
    {
        String result = "";
        if (!isEmpty(commandAndUrl) && commandAndUrl.contains("#"))
        {
            String[] array = commandAndUrl.split("#");
            result = "【" + prefix + "】" + array[1] +
                    "击链接，再选择浏览器打开；或复制这条信息，打开嗨生活¥" + array[0] + "¥";
        }
        return result;
    }

    /**
     * 获取礼品卡分享内容；
     *
     * @param commandAndUrl 口令和url  以#分割；
     */
    public static String getGiftcardShareUrl(String commandAndUrl)
    {
        if (!isEmpty(commandAndUrl) && commandAndUrl.contains("#"))
        {
            String[] array = commandAndUrl.split("#");
            return array[1];
        }
        return null;
    }

    /**
     * 发送短信
     * @param context
     * @param content
     */
    public static void sendSmsAction(Context context,String content){

        Uri smsToUri = Uri.parse("smsto:");

        Intent intentNew = new Intent(Intent.ACTION_SENDTO, smsToUri);

        intentNew.putExtra("sms_body", content);

        context.startActivity(intentNew);

    }

    /**
     * 发送email
     * @param context
     * @param title
     * @param content
     */
    public static void sendEmail(Context context,String title,String content){

        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:"));
        data.putExtra(Intent.EXTRA_SUBJECT, title);
        data.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(data);
    }


    /**
     * 获取商家或商品分享内容；
     *
     * @param commandAndUrl 口令和url  以#分割；
     */
    public static String getShopShareContent(String prefix, String commandAndUrl)
    {
        String result = "";
        if (!isEmpty(commandAndUrl) && commandAndUrl.contains("#"))
        {
            String[] array = commandAndUrl.split("#");
            result = "【" + prefix + "】" + array[1] +
                    "击链接，再选择浏览器打开；或复制这条信息，打开嗨生活¥" + array[0] + "¥";
        }
        return result;
    }

    /**
     * 口令提取处理；
     *
     * @param commandContent
     * @return
     */
    public static String handleCommand(String commandContent)
    {
        LogUtil.i("处理前的口令==" + commandContent);

        String command = null;
        if (!isEmpty(commandContent) && commandContent.contains("¥"))
        {
            int firstIndex = commandContent.indexOf("¥");
            int lastIndex = commandContent.indexOf("¥", firstIndex + 1);
            command = commandContent.substring(firstIndex + 1, lastIndex);
        }
        LogUtil.i("处理后的口令==" + command);
        return command;
    }

    /**
     * 下载图片并根据网络图片的宽高比例动态设置ImageView的宽高；
     *
     * @param imageUrl
     * @param iv
     * @param fixDpHeight
     */
    public static void downloadImageForView(String imageUrl, final ImageView iv, final int fixDpHeight)
    {
        x.image().loadDrawable(imageUrl, null, new XUtilCommonCallback<Drawable>()
        {
            @Override
            protected void success(Drawable drawable)
            {
                if (drawable != null && drawable instanceof BitmapDrawable)
                {
                    BitmapDrawable bd = (BitmapDrawable) drawable;
                    Bitmap bitmap = bd.getBitmap();
                    int lastHeight = org.xutils.common.util.DensityUtil.dip2px(fixDpHeight);
                    int lastWidth = (int) (bitmap.getWidth() * 1.0f / bitmap.getHeight() * lastHeight);
                    ViewGroup.LayoutParams vl = iv.getLayoutParams();
                    vl.height = lastHeight;
                    vl.width = lastWidth;
                    iv.setLayoutParams(vl);

                    iv.setImageBitmap(bitmap);
                }
            }
        });
    }


    /**
     * @param iv
     * @param fixDpHeight
     */
    public static void fitImageForView(final ImageView iv, final int fixDpHeight)
    {
        int lastHeight = org.xutils.common.util.DensityUtil.dip2px(fixDpHeight);
        int lastWidth = lastHeight;
        ViewGroup.LayoutParams vl = iv.getLayoutParams();
        vl.height = lastHeight;
        vl.width = lastWidth;
        iv.setLayoutParams(vl);
    }
}
