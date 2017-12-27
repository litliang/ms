package com.yzb.card.king.ui.credit.watcher;

import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.http.BaseCallBack;
import com.yzb.card.king.http.SimpleRequest;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.credit.bean.CardBin;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/2
 */

public class CardTextWatcher implements TextWatcher
{
    private EditText editText;
    int beforeTextLength = 0;
    int onTextLength = 0;
    boolean isChanged = false;

    int location = 0;// 记录光标的位置
    private char[] tempChar;
    private StringBuffer buffer = new StringBuffer();
    int konggeNumberB = 0;
    private BaseCallBack<CardBin> listener;
    private SimpleRequest<CardBin> request;

    public CardTextWatcher(EditText editText)
    {
        this.editText = editText;
    }

    public void setListener(BaseCallBack<CardBin> listener)
    {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        beforeTextLength = s.length();
        if (buffer.length() > 0)
        {
            buffer.delete(0, buffer.length());
        }
        konggeNumberB = 0;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == ' ')
            {
                konggeNumberB++;
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        onTextLength = s.length();
        buffer.append(s.toString());
        if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged)
        {
            isChanged = false;
            return;
        }
        isChanged = true;
        onTextChanged(s.toString());
    }

    protected void onTextChanged(String s)
    {
       String num = s.trim().replace(" ", "");
        if (s.length() > 14)
        {
            if(request == null)
            {
                request = new SimpleRequest<CardBin>(CardConstant.QUERY_CARDBIN)
                {
                    @Override
                    protected CardBin parseData(String data)
                    {

                        if(TextUtils.isEmpty(data) || "{}".equals(data)){

                            return null;
                        }else {
                            return JSON.parseObject(data, CardBin.class);
                        }

                    }
                };
            }
            Map<String, Object> param = new HashMap<>();
            param.put("cardNo", num);
            request.setParam(param);
            request.sendRequestNew(listener);
        }
    }

    @Override
    public void afterTextChanged(Editable s)
    {

        if (isChanged)
        {
            location = editText.getSelectionEnd();
            int index = 0;
            while (index < buffer.length())
            {
                if (buffer.charAt(index) == ' ')
                {
                    buffer.deleteCharAt(index);
                } else
                {
                    index++;
                }
            }

            index = 0;
            int konggeNumberC = 0;
            while (index < buffer.length())
            {
                // if (index % 5 == 4) {
                //      buffer.insert(index, ' ');
                //      konggeNumberC++;
                // }
                if (index == 4 || index == 9 || index == 14 || index == 19)
                {
                    buffer.insert(index, ' ');
                    konggeNumberC++;
                }
                index++;
            }

            if (konggeNumberC > konggeNumberB)
            {
                location += (konggeNumberC - konggeNumberB);
            }

            tempChar = new char[buffer.length()];
            buffer.getChars(0, buffer.length(), tempChar, 0);
            String str = buffer.toString();
            if (location > str.length())
            {
                location = str.length();
            } else if (location < 0)
            {
                location = 0;
            }

            editText.setText(str);
            Editable etable = editText.getText();
            Selection.setSelection(etable, location);
            isChanged = false;
        }

    }

}
