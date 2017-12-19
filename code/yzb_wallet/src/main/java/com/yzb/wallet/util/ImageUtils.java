package com.yzb.wallet.util;


import com.yzb.wallet.R;

/**
 * 图片工具
 */
public class ImageUtils {

    private static int[] bankIds = new int[]{1,2,3,9,14,16,21,29,31,44,45,46,47,48,49,52,55,57,58,61};
    private static int[] bankLogo = new int[]{
            R.drawable.bank_logo_boc,
            R.drawable.bank_logo_abc,
            R.drawable.bank_logo_icbc,
            R.drawable.bank_logo_pingan,
            R.drawable.bank_logo_nbcb,
            R.drawable.bank_logo_dlcb,
            R.drawable.bank_logo_jsbk,
            R.drawable.bank_logo_desz,
            R.drawable.bank_logo_psbc,
            R.drawable.bank_logo_cmb,
            R.drawable.bank_logo_comm,
            R.drawable.bank_logo_cmbc,
            R.drawable.bank_logo_cib,
            R.drawable.bank_logo_ceb_1,
            R.drawable.bank_logo_ccb,
            R.drawable.bank_logo_shrcb,
            R.drawable.bank_logo_gdb,
            R.drawable.bank_logo_spdb,
            R.drawable.bank_logo_bosh,
            R.drawable.bank_logo_cncb};

    private static int[] bankFont = new int[]{
            R.drawable.bank_zgjsyh,
            R.drawable.bank_zsyh,
            R.drawable.bank_jtyh,
            R.drawable.bank_zgyh,
            R.drawable.bank_zggsyh,
            R.drawable.bank_zgnyyh,
            R.drawable.bank_shpdfzyh,
            R.drawable.bank_shpdfzyh,
            R.drawable.bank_zxyh,
            R.drawable.bank_zgmsyh,
            R.drawable.bank_zggdyh,
            R.drawable.bank_font_hs,
            R.drawable.bank_zgyz,
            R.drawable.bank_font_pa};

    /**
     * 根据银行id获取银行logo
     * @param bankId
     * @return
     */
    public static int getBankImage(int bankId){
        int index = binarySearch(bankIds, bankId, 0, bankIds.length - 1);
        return bankLogo[index];
    }

    /**
     * 根据银行id获取银行logo+银行名称图片
     * @param bankId
     * @return
     */
    public static int getBankFont(int bankId){
        int index = binarySearch(bankIds, bankId, 0, bankIds.length - 1);
        return bankFont[index];
    }

    /**
     *二分查找特定整数在整型数组中的位置(递归)
     *@paramdataset
     *@paramdata
     *@parambeginIndex
     *@paramendIndex
     *@returnindex
     */
    public static int binarySearch(int[] dataset,int data,int beginIndex,int endIndex){
        int midIndex = (beginIndex+endIndex)/2;
        if(data <dataset[beginIndex]||data>dataset[endIndex]||beginIndex>endIndex){
            return -1;
        }
        if(data <dataset[midIndex]){
            return binarySearch(dataset,data,beginIndex,midIndex-1);
        }else if(data>dataset[midIndex]){
            return binarySearch(dataset,data,midIndex+1,endIndex);
        }else {
            return midIndex;
        }
    }
}
