package com.yzb.wallet.openInterface;

import java.util.Map;

/**
 * 选择付款方式返回调用接口
 */
public interface PayMethodListener {

    void callBack(Map<String, String> data);

}
