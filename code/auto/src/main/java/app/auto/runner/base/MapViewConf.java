package app.auto.runner.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import app.auto.runner.base.action.BaseTask;
import app.auto.runner.base.intf.MapBuilder;
import app.auto.runner.base.widget.RoundImageView;

/**
 * Created by admin on 2017/8/16.
 */
public class MapViewConf {

    public boolean fakeString;

    public boolean isFakeEnabled() {
        return fakeString;
    }

    public void setFakeEnabled(boolean fakeString) {
        this.fakeString = fakeString;
    }

    private Map<String, String> vmap = new TreeMap<String, String>();


    private static int defaultImg;

    public static void initDefaultInmg(int pdefaultInmg) {
        defaultImg = pdefaultInmg;
    }

    public Map<String, MapViewConf> confs = new TreeMap<String, MapViewConf>();


    private MapViewConf() {
    }

    public static MapViewConf build() {
        return new MapViewConf();
    }

    private List<String> fieldnames = new ArrayList<String>();

    private Context context;
    private List<Integer> viewsid = new ArrayList<Integer>();

    public static MapViewConf with(Context context) {
        MapViewConf conf = build();
        conf.context = context;
        return conf;
    }

    public MapViewConf addPair(Integer viewid, String fieldname) {
        fieldnames.add(fieldname);
        viewsid.add(viewid);
        return this;
    }

    public MapViewConf addPair(String fieldname, Integer viewid) {
        fieldnames.add(fieldname);
        viewsid.add(viewid);
        return this;
    }

    public MapViewConf addPair(String fieldname, String viewid) {
        fieldnames.add(fieldname);
        viewsid.add(RRes.get("R.id." + viewid).getAndroidValue());
        return this;
    }

    public Object item;
    private View convertView;


    public MapViewConf source(Object item, android.support.v4.app.Fragment convertView) {
        this.item = item;
        this.convertView = convertView.getView();
        return this;
    }

    public MapViewConf source(Object item, Fragment convertView) {
        this.item = item;
        this.convertView = convertView.getView();
        return this;
    }

    public MapViewConf source(Object item, Activity convertView) {
        this.item = item;
        this.convertView = convertView.getWindow().getDecorView();
        return this;
    }

    public MapViewConf source(Object item, View convertView) {
        this.item = item;
        this.convertView = convertView;
        return this;
    }

    int viewlayoutid;

    public int getViewlayoutid() {
        return viewlayoutid;
    }

    public MapViewConf source(int viewlayout) {
        viewlayoutid = viewlayout;
        return this;
    }

    public MapViewConf source(int viewlayout, MapViewConf conf) {
        viewlayoutid = viewlayout;

        confs.put(viewlayoutid + "", conf);
        return this;
    }

    public void toMap() {
        if (item instanceof Map) {
            int id;
            Map map = (Map) item;
            for (int i = 0; i < this.viewsid.size(); i++) {
                id = this.viewsid.get(i);
                View view = convertView.findViewById(id);
                if (view != null) {

                    if (view instanceof TextView) {

                        String name = fieldnames.get(i);

                        map.put(name, ((TextView) view).getText().toString());
                    }
                }
            }
        }

    }


    public MapViewConf toView() {
        try {
            if (item instanceof String) {
                item = JsonUtil.extractJsonRightValue(((String) item));
            }
                if (item instanceof List) {
                    if (((List) item).size() > 0) {
                        if (((List) item).get(0) instanceof List) {
                            fakeList();
                            return this;
                        } else if (((List) item).get(0) instanceof String&&!((List) item).get(0).toString().contains(":")) {
                            fakeList();
                            return this;
                        }
                    }
                }

//            if (fieldnames.size() == 0 && viewsid.size() == 0) {
//                link();
//                return;
//            }
            if (fieldnames.size() == 0 && viewsid.size() == 0) {
                link();
                return this;
            }
            String name;
            Object value = null;
            String textepr = "";
            if (item instanceof Map) {
                Map<String, Object> items = (Map<String, Object>) item;
                Map<String, Object> perItem = (Map<String, Object>) item;
                for (int i = 0; i < this.fieldnames.size(); i++) {
                    name = this.fieldnames.get(i);
                    String n;
                    if (name.contains(":")) {
                        n = name.split(":")[0];
                        textepr = ":" + name.split(":")[1];
                    } else {
                        n = name;
                        textepr = "";
                    }
                    String[] names = n.split("-");
                    for (int ix = 0; ix < names.length; ix++) {
                        String nnode = names[ix];
                        if (perItem.containsKey(nnode)) {
                            value = perItem.get(nnode);
                            if (value == null) {
                                value = "";
                            }
                            if (ix == names.length - 1) {
                                if (value != null) {
                                    findAndBindView(convertView, perItem, nnode + textepr, nnode + textepr, value, i);
                                    perItem = (Map<String, Object>) item;
                                    break;
                                }

                            } else {
                                if (value instanceof Map) {
                                    perItem = (Map<String, Object>) value;
                                    continue;
                                } else {
                                    continue;
                                }
                            }

                        }

                    }


                }
            } else if (item instanceof String) {
                for (int i = 0; i < this.fieldnames.size(); i++) {
                    name = this.fieldnames.get(i);

                    if (name.contains(",")) {
                        Map values = new TreeMap();
                        String[] names = name.split(",");
                        for (String ne : names) {

                            String n;
                            if (ne.contains(":")) {
                                n = ne.split(":")[0];
                            } else {
                                n = ne;
                            }
                            value = JsonUtil.findJsonLink(n, item);
                            value = JsonUtil.extractJsonRightValue(value.toString());
                            values.put(n, value);
                        }
                        if (values != null) {
                            findAndBindView(convertView, item, name, name, values, i);
                        }

                    } else {
                        String n;
                        if (name.contains(":")) {
                            n = name.split(":")[0];
                        } else {
                            n = name;
                        }
                        value = JsonUtil.findJsonLink(n, item);
                        value = JsonUtil.extractJsonRightValue(value.toString());
                        if (value != null) {
                            findAndBindView(convertView, item, name, name, value, i);
                        }
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    private void fakeList() {
        setView(item, item, "", "", convertView, convertView);
    }

    private void link() {
        if (item instanceof String) {
            item = app.auto.runner.base.JsonUtil.extractJsonRightValue((String) item);
        }
        setView(item, item, "", "", convertView, convertView);
    }

    protected boolean findAndBindView(View convertView, Object item,
                                      String name, String splitname, Object value, int fieldpos) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "check the 'value' data:ensure it is not null.thanq");
        }
        int theViewId = fieldpos;
        int viewid = this.viewsid.get(theViewId);
        if (viewid == -1) {
            return false;
        } else {
            View theView = convertView.findViewById(viewid);
            return setView(item, value, name, splitname, convertView, theView);
        }

    }

    @Deprecated
    public MapViewConf fields(String... fields) {
        fieldnames.clear();
        fieldnames.addAll(Arrays.asList(fields));
        return this;
    }

    @Deprecated
    public MapViewConf viewids(String... viewids) {
        viewsid.clear();
        for (String s : viewids) {
            viewsid.add(RRes.get("R.id." + s).getAndroidValue());
        }
        return this;
    }

    public MapViewConf pair(String p, MapViewConf conf) {
        String mc = p.split("->")[0];
        if (mc.contains(":")) {
            mc = mc.split(":")[0];
        }
        confs.put(mc, conf);
        return pair(p);
    }

    public MapViewConf pair(String p) {
        String[] pair = p.split("->");
        if (pair.length == 1) {
            return addPair(pair[0], new Integer(-1));
        } else if (pair.length > 1) {
            addPair(pair[0], pair[1]);
        }
        return this;
    }

    public MapViewConf pairs(String... ps) {
        for (String p : ps) {
            pair(p);
        }
        return this;
    }


    public MapViewConf pair(String p, String switchcase) {
        pair(p);
        String sc = switchcase;
        if (switchcase.contains(":")) {
            String[] s = sc.split(";");
            MapBuilder mapBuilder = MapBuilder.build();
            for (String c : s) {
                String[] cays = c.split(":");
                mapBuilder.add(cays[0], cays[1]);
            }
            String longname = p.split("->")[0].split(":")[0];
            String finalname = "";
            if (longname.contains("-")) {
                String[] longnames = longname.split("-");
                finalname = longnames[longnames.length - 1];
            } else {
                finalname = longname;
            }
            mSwitchcase.put(finalname, mapBuilder.get());

        }


        return this;
    }

    public MapViewConf pair(String p, String switchcase, String saction) {
        pair(p, switchcase);
        BaseTask baseTask = null;
        try {
            baseTask = BaseTask.parseAction(new BaseTask(saction));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = p.split("->")[0];
        if (name.contains(":")) {
            name = name.split(":")[0];
        }
        mAction.put(name, baseTask);
        return this;
    }


    private void toMap(Map map, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View view = vg.getChildAt(i);
            if (view instanceof ViewGroup) {
                toMap(map, (ViewGroup) view);
            } else {
                Object o = RRes.getAttrValue_itsname().get(view.getId());
                if (o != null) {
                    o = o.toString().replace("id/","");
                    if(viewsid.size()!=0){
                        if(viewsid.contains(view.getId())){
                            String name = fieldnames.get(viewsid.indexOf(view.getId()));
                            if(name!=null){
                            toMapInner(map, view, name);

                            }
                        }
                    }
                    else {
                        toMapInner(map, view, o);
                    }
                }
            }
        }
    }

    private void toMapInner(Map map, View view, Object o) {
        if (view instanceof TextView) {
            String text = ((TextView) view).getText().toString();
            if (text != null) {
                map.put(o.toString(), text);
            }
        } else if (view instanceof app.auto.runner.base.widget.ImageView) {
            String url = ((app.auto.runner.base.widget.ImageView) view).getUrl();
            if (url != null) {
                map.put(o.toString(), url.toString());
            }
        }
    }


    public Map toMap(Activity aty) {
        Map map = new TreeMap();
        ViewGroup vg = (ViewGroup) aty.getWindow().getDecorView();
        toMap(map, vg);
        return map;
    }

    Map<String, Map> mSwitchcase = new TreeMap<String, Map>();
    Map<String, BaseTask> mAction = new TreeMap<String, BaseTask>();

    public MapViewConf conf(MapViewConf mc) {
        confs.put("", mc);
        return this;
    }

    public MapViewConf enbaleFake(boolean b) {
        setFakeEnabled(b);
        return this;
    }

    public static abstract class Tackle {
        public abstract void tackleBefore(Object item, Object value, String name,
                                          View convertView, View theView);

        public abstract void tackleAfter(Object item, Object value, String name,
                                         View convertView, View theView);
    }

    Set<String> resMaps = new TreeSet<String>();

    {
        resMaps.add("mipmap");
        resMaps.add("drawable");
    }


    public MapViewConf addTackle(Tackle tackle) {
        this.tackle = tackle;
        return this;
    }

    Tackle tackle;

    public boolean setView( Object value, final View theView) {
        return setView("", value, "",
                "", theView, theView);
    }

        public boolean setView(Object item, Object value, String name,
                           String splitname, View convertView, final View theView) {

        if (theView == null) {
            return false;
        }

        if (tackle != null) {
            tackle.tackleBefore(item, value, name, convertView, theView);
        }
        if (value != null && value.toString().toLowerCase().equals("null")) {
            value = "";
        }
        if (value instanceof Map) {
            if (mAction.containsKey(name)) {
                mAction.get(name).addParams(0, Arrays.asList(item, name, value, "", convertView)).setEventView(theView).innerrun();
            }
            return false;
        }
        String casevalue = "";
        String rawname = name;
        if (name.contains(":")) {
            rawname = name.split(":")[0];
        }

        if (mSwitchcase.containsKey(rawname)) {
            if (mSwitchcase.get(rawname).containsKey(value.toString())) {
                casevalue = value.toString();
                value = mSwitchcase.get(rawname).get(value.toString()).toString();
                if (value.toString().contains(".") && resMaps.contains(value.toString().split("\\.")[0]) && value.toString().split("\\.")[0].length() != value.toString().length()) {
                    value = "R." + value.toString();
                    value = new Integer(RRes.get(value.toString()).getAndroidValue());
                }
            }
        }
        if (value instanceof String && value.toString().contains(".") && resMaps.contains(value.toString().split("\\.")[0]) && value.toString().split("\\.")[0].length() != value.toString().length()) {
            value = "R." + value.toString();
            value = new Integer(RRes.get(value.toString()).getAndroidValue());
        }
        if (name.toString().contains(":")) {
            String[] ns = name.toString().split(":");
            if (!(ns[1].contains("(") && ns[1].contains(")") && ns[1].contains("#"))) {
                if (ns[1].contains("%s")) {
                    value = ns[1].replaceAll("%s", value.toString());
                }

                String fepr = ns[1];
                int l = -1, r = -1;
                while ((l = fepr.indexOf("%")) != -1 && (r = fepr.indexOf("%", l + 1)) != -1) {
                    String inner = fepr.substring(l + 1, r);
                    if (vmap.containsKey(inner)) {
                        value = ns[1].replaceAll("%" + inner + "%", vmap.get(inner).toString());
                    }
                }

            }
        }

        vmap.put(rawname, value.toString());
        theView.setVisibility(View.VISIBLE);
        StyleBox styleBox = null;
        if (theView instanceof WebView) {
            if (value instanceof String) {
                ((WebView) theView).loadData(value.toString(), "text/html;charset=UTF-8", null);
            }
        } else if (theView instanceof AdapterView) {
            MapViewConf conf = confs.get(name);
            if (conf != null) {
                MapAdapter mapadapter;
                ((AdapterView) theView).setAdapter(new MapAdapter(context));
                if (((AdapterView) theView).getAdapter() instanceof HeaderViewListAdapter) {
                    mapadapter = (MapAdapter) ((HeaderViewListAdapter) ((AdapterView) theView).getAdapter()).getWrappedAdapter();
                } else {
                    mapadapter = (MapAdapter) ((AdapterView) theView).getAdapter();
                }
                if (mapadapter instanceof MapAdapter) {
                    mapadapter.setItemLayout(conf.getViewlayoutid());
                    mapadapter.setMapConf(conf);
                    mapadapter.setItemDataSrc(new MapContent(value));
                    mapadapter.notifyDataSetChanged();
                }
            }
        }
        else if (theView instanceof ImageView) {
            if (value == null || value.toString().equals("-1")) {
                return false;
            }
            if (value instanceof Integer) {
                ((ImageView) theView).setImageResource(Integer.parseInt(value
                        .toString()));
            } else if (value.getClass() == BitmapDrawable.class) {
                ((ImageView) theView).setImageDrawable((BitmapDrawable) value);
            } else if (value instanceof Drawable) {
                ((ImageView) theView).setImageDrawable((Drawable) value);
            } else if (value instanceof String) {
                if (theView instanceof app.auto.runner.base.widget.ImageView) {
                    ((app.auto.runner.base.widget.ImageView) theView).setUrl(value.toString());
                }
//                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(value.toString(), (ImageView) theView, ImageUtil.getOptions(), ImageUtil.getImageLoadingListener(true));

                if (theView instanceof RoundImageView) {
                    Glide.with(context).load(value.toString()).asBitmap().centerCrop().error(defaultImg).placeholder(defaultImg).into(new BitmapImageViewTarget((ImageView) theView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ((ImageView) theView).setImageDrawable(circularBitmapDrawable);
                        }
                    });
                } else {
                    DrawableTypeRequest drawableTypeRequest = Glide.with(context).load(value.toString());
                    DrawableRequestBuilder drawableRequestBuilder;
                    if (defaultImg != 0) {
                        drawableRequestBuilder = drawableTypeRequest.placeholder(defaultImg).error(defaultImg);
                    } else {
                        drawableRequestBuilder = drawableTypeRequest.clone();
                    }
                    drawableRequestBuilder.into(new GlideDrawableImageViewTarget((ImageView) theView) {

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                        }
                    });
                }
            }

        } else if (theView instanceof CheckBox) {
            if (value != null && (value.toString().trim().toLowerCase().equals("true") || value.toString().trim().toLowerCase().equals("false"))) {
                ((CheckBox) theView).setChecked(Boolean.parseBoolean(value.toString()));
                ((CheckBox) theView).setVisibility(View.VISIBLE);
            }

        } else if (theView instanceof TextView) {
            if (value.getClass()==Integer.class&&RRes.getAttrValue_itsname().containsKey(value)) {

                theView.setBackgroundResource((Integer) value);
            } else {
                ((TextView) theView)
                        .setText(value instanceof SpannableStringBuilder ? (SpannableStringBuilder) value
                                : value.toString());
            }

        } else if (theView instanceof View) {
            if (value instanceof Integer) {
                theView.setBackgroundResource((Integer) value);
            }


        }
        if (tackle != null) {
            tackle.tackleAfter(item, value, name, convertView, theView);
        }
        if (mAction.containsKey(rawname)) {
            mAction.get(rawname).addParams(0, Arrays.asList(item, name, value, casevalue, convertView)).setEventView(theView).innerrun();
        }
        return false;
    }


}
