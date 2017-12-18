package com.yzb.wallet.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtil {

    /**
     * 隐藏姓名的名的部分。
     *
     * @param name
     * @return
     */
    public static String hideName(String name) {
        String str = name;

        if (isEmpty(str)) {
            return name;
        }

        return str.substring(0, 1).concat("**");
    }

    /**
     * 文字列分割.<BR>
     *
     * @param val   文字列
     * @param split 分割符
     * @return 配列
     */
    public static String[] splitString(String val, String split) {
        Vector<String> vals = new Vector<String>();
        int sPos = 0;
        int ePos = 0;

        while (true) {
            ePos = val.indexOf(split, sPos);
            if (ePos == -1) {
                if (sPos < val.length()) {
                    vals.add(val.substring(sPos));
                } else {
                    vals.add("");
                }
                break;
            } else {
                vals.add(val.substring(sPos, ePos));
                sPos = ePos + 1;
            }
        }

        String[] list = new String[vals.size()];
        for (int i = 0; i < vals.size(); i++) {
            list[i] = (String) vals.get(i);
        }

        return list;

    }

    /**
     * 文字列"'"和"''"置换.<BR>
     *
     * @param oldstr 转换前的值
     * @return newstr 转换后的值
     */
    public static String replaceSinglequote(String oldstr) {

        if (oldstr == null) {
            return oldstr;
        }
        if (oldstr.trim().length() == 0) {
            return oldstr;
        }
        if (oldstr.indexOf("'") == -1) {
            return oldstr;
        }
        StringBuffer newstr = new StringBuffer();
        for (int i = 0; i < oldstr.length(); i++) {
            if (oldstr.charAt(i) == '\'') {
                newstr.append("''");
            } else {
                newstr.append(oldstr.charAt(i));
            }
        }
        return newstr.toString();

    }

    /**
     * 文字列中换行符削除。<BR>
     *
     * @param oldstr 转换前的值
     * @return newstr 转换后的值
     */
    public static String replaceCRLF(String oldstr) {

        if (oldstr == null) {
            return oldstr;
        }
        if (oldstr.trim().length() == 0) {
            return oldstr;
        }
        if (oldstr.indexOf("\n") == -1 && oldstr.indexOf("\r") == -1) {
            return oldstr;
        }
        StringBuffer newstr = new StringBuffer();
        for (int i = 0; i < oldstr.length(); i++) {
            if (oldstr.charAt(i) == '\r' || oldstr.charAt(i) == '\n') {
            } else {
                newstr.append(oldstr.charAt(i));
            }
        }
        return newstr.toString();

    }

    /**
     * 替换指定的字符串里的值.<BR>
     *
     * @param oldstr     转换前的值
     * @param changefrom 要替换的值
     * @param changeto   替换的值
     * @return newstr 转换后的值
     */
    public static String replaceStr(String oldstr, String changefrom,
                                    String changeto) {

        if (oldstr == null || changefrom == null || changeto == null) {
            return oldstr;
        }
        if (oldstr.length() == 0 || changefrom.length() == 0) {
            return oldstr;
        }
        if (oldstr.indexOf(changefrom) == -1) {
            return oldstr;
        }
        StringBuffer newstr = new StringBuffer();
        for (int i = 0; i < oldstr.length(); i++) {
            if (oldstr.substring(i, i + 1).equals(changefrom)) {
                newstr.append(changeto);
            } else {
                newstr.append(oldstr.substring(i, i + 1));
            }
        }
        return newstr.toString();

    }

    /**
     * 字符串截取
     *
     * @param val 截取前的值
     * @param len 截取的长度
     * @return val 截取后的值
     */
    public static String cutString(String val, int len) {
        if (val == null || val.length() == 0 || "NULL".equals(val)) {
            return "";
        }

        if (val.length() > len) {
            val = val.substring(0, len);
        }

        return val;
    }

    /**
     * 字符串头部或者末尾追加其他字符
     *
     * @param oldVal  追加前的值
     * @param addChar 要追加的值
     * @param len     最大行数
     * @param inBegin TRUE：头/FALSE：尾追加
     * @return val 追加后的值
     */
    public static String addString(String oldVal, char addChar, int len,
                                   boolean inBegin) {
        String val = oldVal;
        if (val == null)
            val = "";

        if (val.length() > 0 && val.length() < len) {
            int cnt = len - val.length();
            for (int i = 0; i < cnt; i++) {
                if (inBegin) {
                    val = addChar + val;
                } else {
                    val = val + addChar;
                }
            }
        }

        return val;
    }

    /**
     * code变换处理
     *
     * @param val 变换前的值
     * @return newStr 变换后的值
     */
    public static String changeCharCode(String val) {
        String newStr = "";

        if (val == null || val.trim().length() == 0) {
            return newStr;
        }

        // ￢,￡,￠,∥,－


        while (val.indexOf(0xffe2) != -1 || val.indexOf(0xffe1) != -1
                || val.indexOf(0xffe0) != -1 || val.indexOf(0x2225) != -1
                || val.indexOf(0xff0d) != -1) {
            // ￢


            val = val.replace('\uffe2', '\u00ac');
            // ￡


            val = val.replace('\uffe1', '\u00a3');
            // ￠


            val = val.replace('\uffe0', '\u00a2');
            // ∥


            val = val.replace('\uff0d', '\u2212');
            // －


            val = val.replace('\u2225', '\u2016');
        }

        return val;
    }

    /**
     * 数值保留两位小数
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberType(Double convert) {
        if (convert == null) {
            return "";
        }
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("##0.00");
        return format.format(big);
    }

    /**
     * 数值保留两位小数
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberType(double convert) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("##0.00");
        return format.format(big);
    }

    /**
     * 数值保留两位小数
     *
     * @param convert 转换前数值
     * @param scale   保留小数的位数
     * @return 转换后字符串
     */
    public static String changeNumberType(double convert, int scale) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(scale, RoundingMode.HALF_UP);
        String strFormatRest = "";
        for (int i = 0; i < scale; i++) {
            strFormatRest = strFormatRest + "0";
        }
        DecimalFormat format = null;
        if (strFormatRest.length() > 0) {
            format = new DecimalFormat("##0." + strFormatRest);
        } else {
            format = new DecimalFormat("##0");
        }
        return format.format(big);
    }

    /**
     * 数值保留两位小数并且加千位符
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberCommaType(Double convert) {
        if (convert == null) {
            return "";
        }
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("#,##0.00");
        return format.format(big);
    }

    /**
     * 数值保留两位小数并且加千位符
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberCommaType(BigDecimal convert) {
        if (convert == null) {
            return "";
        }
        BigDecimal big = convert;
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("#,##0.00");
        return format.format(big);
    }

    /**
     * 数值保留两位小数并且加千位符
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberCommaType(double convert, int scale) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        String strFormatRest = "";
        for (int i = 0; i < scale; i++) {
            strFormatRest = strFormatRest + "0";
        }
        DecimalFormat format = null;
        if (strFormatRest.length() > 0) {
            format = new DecimalFormat("#,##0." + strFormatRest);
        } else {
            format = new DecimalFormat("#,##0");
        }
        return format.format(big);
    }

    /**
     * 数值保留两位小数并且加千位符
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberCommaType(double convert) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("#,##0.00");
        return format.format(big);
    }

    /**
     * 整数并且加千位符
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeIntegerCommaType(Integer convert) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("#,##0");
        return format.format(big);
    }

    /**
     * 整数
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeIntegerType(Integer convert) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("###0");
        return format.format(big);
    }

    /**
     * 判断字符串是否为空
     *
     * @param checkStr 要判断的字符串
     * @return TRUE：为空/FALSE：不为空
     */
    public static boolean isEmpty(String checkStr) {

        if (checkStr == null) {
            return true;
        } else if ("".equals(checkStr.trim())) {
            return true;
        } else if ("null".equals(checkStr.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 数值保留两位小数并且加千位符
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNumberToString(double convert) {
        BigDecimal big = new BigDecimal(convert);
        big.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat format = new DecimalFormat("#,##0");
        return format.format(big);
    }

    /**
     * 字符串为空白时转换成null
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeBlankToNull(String convert) {
        String result = null;
        if (!StringUtil.isEmpty(convert)) {
            result = convert;
        }

        return result;
    }

    /**
     * 字符串为空白时转换成null
     *
     * @param convert 转换前数值
     * @return 转换后字符串
     */
    public static String changeNullToString(String convert) {
        String result = "";
        if (!StringUtil.isEmpty(convert)) {
            result = convert;
        }

        return result;
    }


    /**
     * 字符串加成需要的长度（两边加空格内容）
     *
     * @param convert   转换前数值
     * @param extLength 需要转换的长度
     * @return 转换后字符串
     */
    public static String addStringLength(String convert, int extLength) {

        String result = "";

        if (StringUtil.isEmpty(convert)) {
            for (int i = 0; i < extLength; i++) {
                result += " ";
            }
        } else {
            if (convert.length() < extLength) {
                int iLength = extLength - convert.length();
                int pre = 0;
                int lase = 0;

                if ((iLength % 2) == 0) {
                    pre = iLength / 2;
                    lase = pre;
                } else {
                    pre = iLength / 2;
                    lase = iLength - pre;
                }

                for (int i = 0; i < pre; i++) {
                    result = result + " ";
                }
                result = result + convert;
                for (int j = 0; j < lase; j++) {
                    result = result + " ";
                }

            } else {
                result = convert;
            }
        }

        return result;
    }

    /**
     * 字符串转换成int型
     *
     * @param convert 转换前数值
     * @return 转换后int型
     */
    public static int changeToInt(String convert) {

        int result = 0;

        if (!StringUtil.isEmpty(convert)) {
            result = Integer.parseInt(convert);
        }
        return result;
    }

    /**
     * 判断字符是半角英数字还是中日文等
     *
     * @param c 转换前数值
     * @return 半角英数字：true；全角中日文：false
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static ArrayList<String> setPageInfo(int intSelectedPage, int totlePage) {
        ArrayList<String> arrPage = new ArrayList<String>();
        if (totlePage < 5) {
            for (int i = 1; i <= totlePage; i++) {
                String strCount = String.valueOf(i);
                arrPage.add(strCount);
            }
        } else {
            for (int i = 1; i <= totlePage; i++) {
                String strCount = String.valueOf(i);
                if (i <= intSelectedPage + 3) {
                    if (i < intSelectedPage - 3 && i == 1) {
                        arrPage.add("1");
                        arrPage.add("...");
                    } else if (i == intSelectedPage - 3 && i == 1) {
                        arrPage.add("1");
                    } else {
                        if (i >= intSelectedPage - 3) {
                            arrPage.add(strCount);
                        }
                    }
                } else {
                    arrPage.add("...");
                    arrPage.add(String.valueOf(totlePage));
                    break;
                }

            }
        }
        return arrPage;
    }

    /**
     * 将数字金额转换为大写中文金额。
     *
     * @param str
     * @return
     */
    public static String trans2RMB(String str) {

        String ret = cleanZero(splitNum(cleanString(str, ",")));

        return ret;
    }

    /**
     * 删除指定的字符串。
     *
     * @param source
     * @param target
     * @return
     */
    public static String cleanString(String source, String target) {

        StringBuffer retval = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(source, target, true);
        String token = null;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            if (token.equals(target)) {
                //
            } else {
                retval.append(token);
            }
        }
        return retval.toString();
    }

    /**
     * 把用户输入的数以小数点为界分割开来，并调用 numFormat() 方法
     * 进行相应的中文金额大写形式的转换
     * 注：传入的这个数应该是经过 roundString() 方法进行了四舍五入操作的
     *
     * @param s String
     * @return 转换好的中文金额大写形式的字符串
     */
    private static String splitNum(String s) {
        // 如果传入的是空串则继续返回空串
        if ("".equals(s)) {
            return "";
        }
        // 以小数点为界分割这个字符串
        int index = s.indexOf(".");
        // 截取并转换这个数的整数部分
        String intOnly = s.substring(0, index);
        String part1 = numFormat(1, intOnly);
        // 截取并转换这个数的小数部分
        String smallOnly = s.substring(index + 1);
        String part2 = numFormat(2, smallOnly);
        // 把转换好了的整数部分和小数部分重新拼凑一个新的字符串
        String newS = part1 + part2;
        return newS;
    }

    /**
     * 把传入的数转换为中文金额大写形式
     *
     * @param flag int 标志位，1 表示转换整数部分，0 表示转换小数部分
     * @param s    String 要转换的字符串
     * @return 转换好的带单位的中文金额大写形式
     */
    private static String numFormat(int flag, String s) {
        int sLength = s.length();
        // 货币大写形式
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        // 货币单位
        String unit[] = {"元", "拾", "佰", "仟", "万",
                // 拾万位到仟万位
                "拾", "佰", "仟",
                // 亿位到万亿位
                "亿", "拾", "佰", "仟", "万"};
        String small[] = {"分", "角"};
        // 用来存放转换后的新字符串
        String newS = "";
        // 逐位替换为中文大写形式
        for (int i = 0; i < sLength; i++) {
            if (flag == 1) {
                // 转换整数部分为中文大写形式（带单位）
                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];
            } else if (flag == 2) {
                // 转换小数部分（带单位）
                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];
            }
        }
        return newS;
    }

    /**
     * 把已经转换好的中文金额大写形式加以改进，清理这个字
     * 符串里面多余的零，让这个字符串变得更加可观
     * 注：传入的这个数应该是经过 splitNum() 方法进行处理，这个字
     * 符串应该已经是用中文金额大写形式表示的
     *
     * @param s String 已经转换好的字符串
     * @return 改进后的字符串
     */
    private static String cleanZero(String s) {
        // 如果传入的是空串则继续返回空串
        if ("".equals(s)) {
            return "";
        }
        // 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯
        while (s.charAt(0) == '零') {
            // 将字符串中的 "零" 和它对应的单位去掉
            s = s.substring(2);
            // 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
            if (s.length() == 0) {
                return "零";
            }
        }
        // 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
        String regex1[] = {"零仟", "零佰", "零拾"};
        String regex2[] = {"零亿", "零万", "零元"};
        String regex3[] = {"亿", "万", "元"};
        String regex4[] = {"零角", "零分"};
        // 第一轮转换 "零仟", 零佰","零拾"等字符串替换成一个"零"
        for (int i = 0; i < 3; i++) {
            s = s.replaceAll(regex1[i], "零");
        }
        // 第二轮转换  "零亿","零万","零元"等
        for (int i = 0; i < 3; i++) {
            // 当第一轮转换过后有可能有很多个零叠在一起
            // 要把很多个重复的零变成一个零
            s = s.replaceAll("零零零", "零");
            s = s.replaceAll("零零", "零");
            s = s.replaceAll(regex2[i], regex3[i]);
        }
        // 第三轮转换把"零角","零分"字符串省略
        for (int i = 0; i < 2; i++) {
            s = s.replaceAll(regex4[i], "");
        }
        // 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
        s = s.replaceAll("亿万", "亿");
        return s;
    }

    /**
     * 格式化字符窜
     *
     * @param fmt:"0000000000"
     * @param id:"1001"
     * @return "0000001001"
     */
    public static String LongToFixLengthString(String fmt, long id) {
        NumberFormat formatter = new DecimalFormat(fmt);
        return formatter.format(id);
    }

    public static String toSqlConditionStr(String target) {
        if (null == target)
            return null;
        else
            return "'" + target + "'";
    }
}
