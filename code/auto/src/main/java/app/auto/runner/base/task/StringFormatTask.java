package app.auto.runner.base.task;

import android.app.Activity;
import android.view.View;

import app.auto.runner.base.RRes;
import app.auto.runner.base.action.Actions;
import app.auto.runner.base.action.Task;

/**
 * Created by admin on 2017/9/3.
 */

public abstract class StringFormatTask extends Task implements Expression{

    public Activity act;

    public StringFormatTask with(Activity act) {
        this.act = act;
        return this;
    }

    public void exeId_Exp(String exp) {
        String[] ps = exp.split(":");
        Actions.withView(act.findViewById(RRes.get("R.id."+ps[0]).getAndroidValue())).parse(getClass().getSimpleName()+"("+ps[1]+")").action();
    }

    public String getDefaultEmpty(String paramsX){
        return paramsX==null?"":paramsX;
    }

    public Object run(View view, Object... params){
        String[] sp = new String[params.length];
        for(int j = 0;j< params.length;j++){
            sp[j] = getDefaultEmpty(params[j].toString());
        }
        return run(view,sp);

    }

    public abstract Object run(View view, String... params);}
