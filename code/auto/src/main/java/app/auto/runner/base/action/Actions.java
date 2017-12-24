package app.auto.runner.base.action;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;


public class Actions {

    String callflow = "get()--print()-back();back();";
    List<BaseTask> baseTasks = new ArrayList<BaseTask>();
    private View view;

    public Actions() {
        this.actionGroup = actionGroup;
//        parseActions(actionGroup);

    }

    String actionGroup;

    public Actions setView(View view) {
        this.view = view;
        return this;
    }

    public static Actions withView(View view) {
        return new Actions().setView(view).setApplicationContext(view.getContext().getApplicationContext());
    }

    List<Object> params = new ArrayList<Object>();

    Context application;

    public Actions setApplicationContext(Context application) {
        this.application = application;
        return this;
    }

    public Actions setActionGroup(String actionGroup) {
        this.actionGroup = actionGroup;
        return this;
    }

    public Actions parse(String actionGroup) {
        Actions actions = this;
        this.setActionGroup(actionGroup);
//        if (!actionGroup.contains("-")) {
            String[] g = actionGroup.split(";");
            for (String s : g) {
                if (s == null || s.trim().equals("")) {
                    continue;
                }
                actions.baseTasks.add((BaseTask) new BaseTask(s).addParams(objects).setEventView(view));
            }
//        }

        return actions;
    }

    public void action() {
        for (BaseTask baseTask : baseTasks) {
            baseTask.innerrun();
        }
    }

    List objects = new ArrayList();

    public Actions addParams(AdapterView<?> adapterView, View view, Integer integer) {
        objects.add(adapterView);
        objects.add(view);
        objects.add(integer);
        return this;
    }

    public List<BaseTask> getBaseTasks() {
        return baseTasks;
    }
}
