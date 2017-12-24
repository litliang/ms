package app.auto.runner.base.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import app.auto.runner.base.intf.json.JsonToMapUtils;
import app.auto.runner.base.intf.json.JsonUtil;

/**
 * Created by Administrator on 2017/11/18.
 */
public class MapUtil {


    private List<Map> list;


    public static MapUtil build() {
        return new MapUtil();
    }

    public MapUtil get(List<Map> list) {
        this.list = list;
        return this;
    }

    public MapUtil get(String json) {
        list = (List<Map>) JsonUtil.extractJsonRightValue(json);
        return this;
    }

    public MapUtil sure(String key_name_value) {
        String[] nv = key_name_value.split("-");
        return sure(nv[0],nv[1]);
    }
    public MapUtil sure(String key_name, String key_value) {
        this.key_name = key_name;
        this.key_value = key_value;
        return this;
    }

    private String key_name;
    private String key_value;


    private TreeMap<String, Object> map = new TreeMap<String, Object>();
    public MapUtil transfer() {
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).get(key_name).toString();

            String value = list.get(i).get(key_value).toString();
            map.put(name, value);
        }
        return this;
    }

    public Map toMap(){
        return map;
    }

    public String toJson(){
        return JsonToMapUtils.entityToJson(map);
    }
}
