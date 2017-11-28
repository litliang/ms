package com.yzb.card.king.bean.common;

import java.io.Serializable;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/11/21
 */

public class Error implements Serializable
{
    private String code;
    private String error;

    public Error()
    {
    }

    public Error(String code, String error)
    {
        this.code = code;
        this.error = error;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}
