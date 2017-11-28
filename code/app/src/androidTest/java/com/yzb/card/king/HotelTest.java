package com.yzb.card.king;

import com.yzb.card.king.util.RegexUtil;

import org.junit.Test;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/1 14:35
 */
public class HotelTest
{
    @Test
    public void testQueryHotelList(){
        boolean valid = RegexUtil.validEmail("915752357.com");
        if(valid){
            System.out.print("正确");
        }else {
            System.out.print("错误");
        }
        assert valid == true;
    }
}
