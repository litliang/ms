package app.auto.runner.base.intf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */
public class ListBuilder {
    public static ListBuilder with(List list){
        ListBuilder lb = build();
        lb.list = list;
        return lb;
    }

    public List list = new ArrayList();
    public static ListBuilder build(){
        return new ListBuilder();
    }
    public ListBuilder add( Object v){
        if(v==null||v.toString().equals("null")){

        }else {
            list.add(v);
        }
        return this;
    }

    public List get(){
        return list;
    }

}
