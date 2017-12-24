package app.auto.runner.base.action;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhua on 2015/10/23.
 */

public class BaseTask extends Epr {

    public static BaseTask parseAction(String action) {
        return new BaseTask(action);
    }

    public static BaseTask parseAction(BaseTask baseTask) throws Exception {
        String raw = baseTask.raw;
        String parameters = baseTask.parameters;
        String taskname = baseTask.taskname;
        Class clazz = baseTask.viewContext;
        if (raw.endsWith(";")) {
            raw = raw.substring(0, raw.length() - 1);
        }

        parameters = raw.substring(raw.indexOf("(") + 1, raw.length() - 1);
        taskname = raw.substring(0, raw.indexOf("("));

        String strclz = null;
        strclz = "app.auto.runner.func." + taskname.toUpperCase().substring(0, 1) + taskname.substring(1).toLowerCase();

        try {
            clazz = (Class<BaseTask>) Class.<BaseTask>forName(strclz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (clazz == null) {
            strclz = "app.auto.runner.func.stringtask." + taskname.toUpperCase().substring(0, 1) + taskname.substring(1).toLowerCase();

            try {
                clazz = (Class<BaseTask>) Class.<BaseTask>forName(strclz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (clazz == null) {
            strclz = Epr.getCtx().getPackageName() + ".baseTasks." + taskname.toUpperCase().substring(0, 1) + taskname.substring(1).toLowerCase();

            try {
                clazz = (Class<BaseTask>) Class.<BaseTask>forName(strclz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        baseTask.raw = raw;
        baseTask.parameters = parameters;
        baseTask.taskname = taskname;
        baseTask.viewContext = clazz;
        return baseTask;
    }


    public List<Object> params = new ArrayList<Object>();

    public BaseTask addParams(List objects) {
        params.addAll(objects);
        return this;
    }

    public BaseTask addParams(int pos, List objects) {
        params.addAll(pos, objects);
        return this;
    }

    public String taskname;


    public BaseTask(String raw) {
        super(raw);
        try {
            if (!raw.contains("(")) {
                return;
            }
            BaseTask.parseAction(this);
            BaseTask.parseParams(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getParams() {
        return params;
    }


    public Object innerrun() {
        if (!raw.contains("(")) {
            return raw;
        }
        if (viewContext != null) {

            Object aresult;
            List<Object> rt = new ArrayList<Object>();
            for (Epr epr : preparedParams) {
                if (epr == null) {
                    continue;
                }

                aresult = epr.innerrun();
                if (aresult != null) {
                    rt.add(aresult.toString());
                } else {
                    rt.add("");
                }

            }
            rt.addAll(params);
            try {
                if (runnable == null) {
                    runnable = viewContext.newInstance();
                }
                return runnable.run(getEventView(), rt.toArray());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    String parameters;
    Class<Task> viewContext;

    public BaseTask withView(View view) {
        setEventView(view);
        return this;
    }

    public static BaseTask buildExp(String raw) {
        return new BaseTask(raw);
    }

    public static BaseTask parseParams(BaseTask baseTask) {

        String param = baseTask.parameters;
        List<Epr> ps = new ArrayList<Epr>();
        String[] params = param.split(",");
        for (String p : params) {
            if (p == null || p.equals("")) {
                continue;
            }
            ps.add(app.auto.runner.base.action.Epr.parseParam(p, baseTask.getEventView()));
        }
        baseTask.preparedParams = ps;
        return baseTask;
    }

    List<Epr> preparedParams;

}
