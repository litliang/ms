package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * Created by 玉兵 on 2017/11/25.
 */

public class UseShopCouponNumBean implements Serializable{

    /**
     * 优惠券数量
     */
    private int couponQuantity;

    /**
     * 代金券数量
     */
    private int cashCouponQuantity;

    public int getCouponQuantity() {
        return couponQuantity;
    }

    public void setCouponQuantity(int couponQuantity) {
        this.couponQuantity = couponQuantity;
    }

    public int getCashCouponQuantity() {
        return cashCouponQuantity;
    }

    public void setCashCouponQuantity(int cashCouponQuantity) {
        this.cashCouponQuantity = cashCouponQuantity;
    }
}
