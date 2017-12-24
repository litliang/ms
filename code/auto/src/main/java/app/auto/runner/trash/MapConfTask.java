package app.auto.runner.trash;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.auto.runner.base.action.Task;

/**
 * Created by admin on 2017/9/11.
 */

public abstract class MapConfTask extends Task {

    @Override
    public Object run(View view, Object... params) {
        Object item = params[0];
        String name = params[1].toString();
        Object value = params[2];
        Object casevalue = params[3];
        View convertView = (View) params[4];
        List list = new ArrayList(Arrays.asList(params));
        list.remove(0);list.remove(0);list.remove(0);list.remove(0);list.remove(0);

        run(view,item,name,value,casevalue,convertView,list.toArray());
        return null;
    }

    public abstract void run(View view, Object item, String name, Object value, Object casevalue, View convertView, Object... objects);
}
