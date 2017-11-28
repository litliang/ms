package com.yzb.card.king.ui.credit.watcher;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import com.yzb.card.king.ui.app.presenter.GetBankBinPresenter;
import com.yzb.card.king.ui.app.view.AppBaseView;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名：${CLASSNAME}
 * 作者：殷曙光
 * 日期：2016/6/20 16:22
 * 描述：
 */
public abstract class CardNumWatcher implements TextWatcher, AppBaseView
{
    int beforeTextLength = 0;
    int onTextLength = 0;
    boolean isChanged = false;

    int location = 0;// 记录光标的位置
    private char[] tempChar;
    private StringBuffer buffer = new StringBuffer();
    int konggeNumberB = 0;

    private EditText editText;
    public String cardNo;
    public GetBankBinPresenter getBankBinPresenter;

    public CardNumWatcher(EditText editText)
    {
        this.editText = editText;
        getBankBinPresenter = new GetBankBinPresenter(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        onTextLength = s.length();
        buffer.append(s.toString());
        cardNo = editText.getText().toString();
        cardNo = cardNo.replaceAll("\\s*", "");
        if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged)
        {
            isChanged = false;
            return;
        }
        isChanged = true;

        if (cardNo.length() >= 15)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("creditNo", cardNo);
            getBankBinPresenter.loadData(params);
        }

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
                if ((index == 4 || index == 9 || index == 14 || index == 19))
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


    protected abstract void parseData(String data);

    protected abstract void fail();

    @Override
    public void onViewCallBackSucess(Object data)
    {
        if (data != null)
        {
            parseData(String.valueOf(data));
        }
    }

    @Override
    public void onViewCallBackFail(String failMsg)
    {
        fail();
    }
}
