package com.yzb.card.king.http;

import com.yzb.card.king.bean.common.Error;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/21
 */

public interface BaseCallBack<T>
{
    void onSuccess(T data);
    void onFail(Error error);
}
