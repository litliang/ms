package com.yzb.wallet.openInterface;

import java.util.Map;

/**
 * 返回调用接口
 */
public interface WalletBackListener {

    void setSuccess(String RESULT_CODE);

    void setSuccess(Map<String, String> resultMap, String RESULT_CODE);

    void setError(String RESULT_CODE, String ERROR_MSG);

}
