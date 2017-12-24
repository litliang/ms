package app.auto.runner.trash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/11/2.
 */

public class MapHttpConf {


    private Map outfromValues = new TreeMap();

    public void addOutPairValue(String outfield, Object outvalue) {
        outfromValues.put(outfield,outvalue);
    }

    public MapHttpConf pair(String p, Object value) {
        String[] pair = p.split("->");
        if (pair.length == 1) {
            return null;
        } else if (pair.length > 1) {
            addPair(pair[0], pair[1]);
        }
        addOutPairValue(pair[0],value);
        return this;
    }

    public Map IONamePairs = new TreeMap();

    public void addPair(String outfield, String recvfield) {
        IONamePairs.put(outfield,recvfield);
    }







    String url;

    public static enum REQ_TYPE {POST, GET, DEL, INPUT}


    REQ_TYPE type;

    public MapHttpConf(String url, REQ_TYPE type) {
        this.url = url;
        this.type = type;
    }

    List<JsonParamBase> jsonParamBases = new ArrayList<JsonParamBase>();

    public static class JsonParamBase {
        public String name;
        public String type;
        public String defaultvalue;

        public JsonParamBase(String name, String type, String defaultvalue) {
            this.name = name;
            this.type = type;
            this.defaultvalue = defaultvalue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDefaultvalue() {
            return defaultvalue;
        }

        public void setDefaultvalue(String defaultvalue) {
            this.defaultvalue = defaultvalue;
        }
    }

    public final static String PARAM_TYPE_PATH = "PARAM_TYPE_PATH";
    public final static String PARAM_TYPE_PARAM = "PARAM_TYPE_PARAM";

    public enum REQUEST_HTTP_PARAM_TYPE {
        PARAM_TYPE_PATH,
        PARAM_TYPE_PARAM
    }

    ;

    public MapHttpConf addparam(String name, REQUEST_HTTP_PARAM_TYPE type, String defaultvalue) {
        String requestparam_type = null;
        switch (type) {
            case PARAM_TYPE_PATH:
                requestparam_type = MapHttpConf.PARAM_TYPE_PATH;
            case PARAM_TYPE_PARAM:
                requestparam_type = MapHttpConf.PARAM_TYPE_PARAM;
        }
        jsonParamBases.add(new JsonParamBase(name, requestparam_type, defaultvalue));

        return this;
    }

    public Result getResult() {
        if(!result.isTackled()){
            tackleIt();
            result.setTackled(true);
        }
        return result;
    }

    public String pathprefix = "<";
    public String pathpostfix = ">";

    private void tackleIt() {

        for(JsonParamBase  paramBase:jsonParamBases){


        }
    }

    Result result = new Result();
    public static class Result{
        public boolean isTackled() {
            return tackled;
        }

        public void setTackled(boolean tackled) {
            this.tackled = tackled;
        }

        boolean tackled;
        String paramJson;
        String url;
        String requesttype;

        public String getParamJson() {
            return paramJson;
        }

        public void setParamJson(String paramJson) {
            this.paramJson = paramJson;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRequesttype() {
            return requesttype;
        }

        public void setRequesttype(String requesttype) {
            this.requesttype = requesttype;
        }
    }


    public void inputValue(){

    }
}

