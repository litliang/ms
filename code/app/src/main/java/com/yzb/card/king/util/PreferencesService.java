package com.yzb.card.king.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.sys.AppConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dev on 2015/12/28.
 */
public class PreferencesService {
    private Context context;

    public PreferencesService(Context context){
        this.context = context;
    }

    public void save(String sessionId, String mobile, String passwd, String isAutoLogin, String personInfo){
        SharedPreferences preferences = context.getSharedPreferences("jifenbao", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("sessionId", sessionId);
        editor.putString("mobile", mobile);
        editor.putString("passwd", passwd);
        editor.putString("isAutoLogin", isAutoLogin);
        editor.putString("pic", "");
        if(!"".equals(personInfo)){
            Map<String, Object> personInfoMap = JSON.parseObject(personInfo, Map.class);
            editor.putString("pic", String.valueOf(personInfoMap.get("pic")));
        }
        editor.putString("personInfo", personInfo);
        editor.commit();
    }

    public Map<String, String> getPreferences() {
        Map<String, String> result = new HashMap<String, String>();
        SharedPreferences preferences = context.getSharedPreferences("jifenbao", Context.MODE_PRIVATE);
        if("".equals(preferences.getString("sessionId", ""))) return null;
        result.put("sessionId", preferences.getString("sessionId", ""));
        result.put("mobile", preferences.getString("mobile", ""));
        result.put("passwd", preferences.getString("passwd", ""));
        result.put("isAutoLogin", preferences.getString("isAutoLogin", ""));
        result.put("pic", preferences.getString("pic", ""));
        result.put("personInfo", preferences.getString("personInfo", ""));
        return result;
    }

    public void updatePreferences(String data){
        PreferencesService preferences = new PreferencesService(context);
        Map<String, String> pmap = preferences.getPreferences();

        preferences.save(AppConstant.sessionId, pmap.get("mobile"), pmap.get("passwd"), pmap.get("isAutoLogin"), data);
    }

    /**
     * 更新值
     * @param key
     * @param value
     */
    public void updatePreferences(String key, String value){
        SharedPreferences preferences = context.getSharedPreferences("jifenbao", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String getValue(String key){

        SharedPreferences preferences = context.getSharedPreferences("jifenbao", Context.MODE_PRIVATE);

        return preferences.getString(key, "");
    }

    public String getPersonPic(){
        Map<String, String> result = new HashMap<String, String>();
        SharedPreferences preferences = context.getSharedPreferences("jifenbao", Context.MODE_PRIVATE);
        if("".equals(preferences.getString("sessionId", ""))) return null;

        Map<String, Object> personInfo = JSON.parseObject(preferences.getString("personInfo", ""), Map.class);
        if(null != personInfo && !personInfo.isEmpty()) return String.valueOf(personInfo.get("pic"));

        return null;
    }
}
