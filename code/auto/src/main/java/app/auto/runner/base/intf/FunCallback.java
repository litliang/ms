package app.auto.runner.base.intf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litli on 2017/6/23.
 */

public abstract class FunCallback<S,F,C> {
    public abstract void simpleRun();
    public  void onSuccessConnected(S result){
        onSuccess(result,object);
    };
    public  void onFailureConnected(F result){
        onFailure(result,object);
    };
    public  void onCallbackConnected(C result){
        onCallback(result,object);
    };
    public void onSuccess(S result,List<Object> object){};
    public void onFailure(F result,List<Object> object){};
    public void onCallback(C result,List<Object> object){};
    private List<Object> object = new ArrayList<Object>();

    public List<Object> getObject() {
        return object;
    }

    public FunCallback addParam(Object object) {
        this.object.add(object);
        return this;
    }
}
