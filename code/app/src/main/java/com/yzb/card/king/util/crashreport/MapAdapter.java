package com.yzb.card.king.util.crashreport;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import app.auto.runner.base.MapViewConf;
import app.auto.runner.base.action.ViewInflater;
import app.auto.runner.base.task.Compt;


/***
 * abslistview适配器
 *
 * @author Administrator
 */
public class MapAdapter extends BaseAdapter {
    public ContainerInfo linearInfo;
    public List<View> groupViewSet = new ArrayList<View>();
    protected Context context;
    protected List<String> fieldnames;
    protected List<Integer> viewsid;
    private int itemLayout;
    private MapViewConf mapconf;

    public int getItemLayout() {
        return itemLayout;
    }

    private MapContent itemDataSrc;
    private Set<ActionListener> handlers = new HashSet<ActionListener>();
    private Map<Integer, ListenerBox> listenerMaps = new TreeMap<Integer, ListenerBox>();
    private Map<Integer, StyleBox> styleMaps;
    private Map<View, Integer> viewContentMap = new HashMap<View, Integer>();
    boolean ischecked;
    public boolean isVisible = true;
    public Map<Integer, Boolean> checkBoxVisibleOptions = new HashMap<Integer, Boolean>();
    public List<Integer> viewInitIndices = new ArrayList<Integer>();
    public int viewChildCount = 0;
    public boolean startChildViewsCount = true;
    protected boolean isCreatedView;
    public int selectedNum;
    public int latestPosition;
    public List<Integer> selected = new ArrayList<Integer>();
    public Class clazz;
    public String checkboxname;
    public List<Integer> selectedbck;
    private String imageField;

    public List<Object> selectedItems = new ArrayList<Object>();

    public MapAdapter addActionListener(ActionListener actionListener) {
        this.handlers.add(actionListener);
        deployListener(actionListener);
        return this;
    }

    public void clearStyles() {
        styleMaps.clear();
    }


    boolean cacheoutofdate = false;

    public boolean isCacheoutofdate() {
        return cacheoutofdate;
    }

    public void setCacheoutofdate(boolean cacheoutofdate) {
        this.cacheoutofdate = cacheoutofdate;
    }

    long timemills = -1L;
    long tmptimemills;

    public void setCheckBoxName(String checkboxFieldName) {
        if (this.fieldnames.indexOf(checkboxFieldName) == -1) {
            throw new IllegalStateException("");
        }
        checkboxname = checkboxFieldName;
    }

    public int getTotalSelection() {
        return this.selected.size();
    }

    public void setImageLoadedField(String tosetImageField) {
        this.imageField = tosetImageField;
        this.isSyncInImage = false;
    }

    public void selectAll(boolean shouldSelect) {
        if (shouldSelect) {
            if (this.selectedbck != null) {
                this.selected = new ArrayList(this.selectedbck);
            }
        } else {
            if (this.selected != null) {
                this.selected.clear();
            }
        }
    }

    public boolean isAdaptEmpty() {
        return this.fieldnames == null || this.fieldnames.size() == 0
                || this.adaptInfo == null;
    }

    public boolean isEmpty() {
        return getItemDataSrc() == null || getItemDataSrc().getCount() == 0;
    }

    Integer pageSize = -1;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean addSelected(int pos, boolean isAdd) {
        if (isAdd) {
            selected.add(pos);
            selectedItems.add(getItem(pos));
        } else {
            int i = selected.lastIndexOf(pos);
            if (i >= 0) {
                selected.remove(i);
            }
            selectedItems.remove(getItem(pos));
        }
        return isAdd;
    }

    public boolean toggleSelected(int pos) {
        return addSelected(pos, !selected.contains(pos));
    }

    public boolean rmSelectedCollection() {
        if (getItemDataSrc().getContent() instanceof List) {
            ((List) getItemDataSrc().getContent()).removeAll(selectedItems);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    Map<Integer, Bitmap> noStyncImageMap = new HashMap<Integer, Bitmap>();
    private List<AdaptInfo.Style> styles = new ArrayList<AdaptInfo.Style>();


    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }

    public void reinitSelectedAllBck(int count) {
        selected = new ArrayList<Integer>(count);
        selectedbck = new ArrayList<Integer>(count);
        fill(selectedbck, count);
        viewContentMap.clear();
        groupViewSet.clear();
    }

    public void treatCursor(Object item, View convertView, int position) {
        Object value = null;
        String name;
        Cursor cur = (Cursor) item;
        Class type;
        if (cur.getColumnCount() > 0) {
            while (cur.moveToNext()) {
                for (int i = 0; i < cur.getColumnCount(); i++) {
                    name = cur.getColumnName(i);
                    type = itemDataSrc.getNameTypePair().get(name);
                    if (type == null) {
                        continue;
                    }
                    try {
                        if (type == Integer.class) {
                            value = cur.getInt(i);
                        } else if (type == Long.class) {
                            value = cur.getLong(i);
                        } else if (type == Double.class) {
                            value = cur.getDouble(i);
                        } else if (type == String.class) {
                            value = cur.getString(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (value != null)
                        findAndBindView(convertView, position, item, name,
                                value);
                }
            }
        }
    }

    public void treatObject(Object item, View convertView, int position)
            throws SecurityException {
        Object value = null;
        String name;
        boolean isAccessible;
        clazz = item.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            if (this.fieldnames.contains(field.getName())) {
                name = field.getName();
                try {
                    value = field.get(item);
                    // value = value == null ? "" : value;
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (value != null)
                    findAndBindView(convertView, position, item, name, value);
            }
            field.setAccessible(isAccessible);
        }
    }

    public void treatMap(Object item, View convertView, int position) {

        String name;
        Object value;
        Map<String, Object> items = (Map<String, Object>) item;
        for (int i = 0; i < this.fieldnames.size(); i++) {
            name = this.fieldnames.get(i);
            if (items.containsKey(name)) {
                value = items.get(name);
                if (value != null) {
                    findAndBindView(convertView, position, item, name, value);
                }
            }
        }
    }

    private void fill(List<Integer> boolList, int count) {
        boolList.removeAll(boolList);
        for (int i = 0; i < count; i++) {
            boolList.add(i);
        }
    }

    public boolean isCreatedView() {
        return isCreatedView;
    }

    public void setCreatedView(boolean isCreateView) {
        this.isCreatedView = isCreateView;
    }

    public Map<View, Integer> getViewContentMap() {
        return viewContentMap;

    }

    public void setStartChildViewsCount(boolean flag) {
        startChildViewsCount = flag;
        viewChildCount = 0;
    }

    public void setItemDataSrc(MapContent itemDataSrc) {
        this.itemDataSrc = itemDataSrc;
        visibleALlCheckBox();
    }

    public MapAdapter addItemDataSrcList(List<? extends Object> objs) {
        if (this.getItemDataSrc() == null
                || this.getItemDataSrc().getContent() == null) {
            this.setItemDataSrc(new MapContent(objs));
        } else {
            ((List) this.getItemDataSrc().getContent()).addAll(objs);
        }
        visibleALlCheckBox();
        return this;
    }

    public void clearSelectOption() {
    }

    public void clearDataSrc() {
        if (this.getItemDataSrc() != null) {
            this.getItemDataSrc().clear();
        }
        continues.clear();
        noStyncImageMap.clear();
        notifyDataSetChanged();
    }

    public void addAdaptInfo(AdaptInfo adaptInfo) {
        addMapInfo(adaptInfo, false);
    }

    public void addMapInfo(AdaptInfo adaptInfo, boolean idDefault) {

        if (adaptInfo.adaptviewid != -1) {
            this.id_adaptinfo.put(adaptInfo.adaptviewid, adaptInfo);
        }
        if (idDefault) {
            setAdaptInfo(adaptInfo);
        }
        // TODO Auto-generated constructor stub
    }

    public MapAdapter(Context context, AdaptInfo adaptInfo) {
        init(context);
        addMapInfo(adaptInfo, true);
        // TODO Auto-generated constructor stub
    }

    private void init(Context context) {
        this.context = context;
        if (styleMaps == null) {
            styleMaps = new HashMap<Integer, StyleBox>();
        }
    }

    private void visibleALlCheckBox() {
        if (this.itemDataSrc == null) {
            return;
        }
        this.markVisible(true);
    }

    public MapContent getItemDataSrc() {
        return itemDataSrc;
    }

    public MapAdapter(Context context) {
        init(context);
    }

    AdaptInfo adaptInfo;
    private View footerView;

    public void setAdaptInfo(AdaptInfo adaptInfo) {
        this.fieldnames = adaptInfo.objectFieldList.size() > 0 ? adaptInfo.objectFieldList
                : Arrays.asList(adaptInfo.objectFields);

        this.viewsid = adaptInfo.viewIdList.size() > 0 ? adaptInfo.viewIdList
                : Arrays.asList(adaptInfo.viewIds);
        this.itemLayout = adaptInfo.listviewItemLayoutId;
        this.handlers = adaptInfo.actionListeners;
        this.styles.clear();
        this.styles.addAll(adaptInfo.styles);

        this.pageSize = adaptInfo.pageSize;
        this.adaptInfo = adaptInfo;
        if (this.adaptInfo.pagefooterId != 0) {
            this.footerView = LayoutInflater.from(context).inflate(
                    this.adaptInfo.pagefooterId, null);
        }

        deployListeners(handlers);

    }

    int scrollpos;

    public void deployAdapter(int id) {
        AdaptInfo adaptInfo = id_adaptinfo.get(id);
        if (adaptInfo != null) {
            setAdaptInfo(adaptInfo);

            ((AbsListView) ((Activity) context).findViewById(id))
                    .setAdapter(this);
            ((AbsListView) ((Activity) context).findViewById(id))
                    .setSelection(scrollpos);
            notifyDataSetChanged();

        }
    }

    public void deployListeners(Set<ActionListener> actionListeners) {
        if (listenerMaps == null) {
            listenerMaps = new HashMap<Integer, ListenerBox>();
        }
        if (actionListeners == null || actionListeners.size() == 0) {
            return;
        }
        for (ActionListener listener : actionListeners) {
            deployListener(listener);
        }
    }

    public void deployListener(ActionListener listener) {
        listener.setBaseAdapter(this);
        if (!listenerMaps.containsKey(listener.getResrcId())) {
            listenerMaps.put(listener.getResrcId(), new ListenerBox(this,
                    listener).setBasicAdapter(this));
        } else {
            listenerMaps.get(listener.getResrcId()).addActionListener(listener);
        }
    }

    public void deployStyleBoxes(AdaptInfo.Style[] styls) {
        if (styleMaps == null) {
            styleMaps = new HashMap<Integer, StyleBox>();
        }
        List<AdaptInfo.Style> styles = Arrays.asList(styls);
        if (styles == null || styles.size() == 0) {
            return;
        }
        for (AdaptInfo.Style style : styles) {
            deployStyleBox(style);
        }
    }

    public void deployStyleBoxes(Collection<AdaptInfo.Style> styles) {
        if (styleMaps == null) {
            styleMaps = new HashMap<Integer, StyleBox>();
        }
        if (styles == null || styles.size() == 0) {
            return;
        }
        for (AdaptInfo.Style style : styles) {
            deployStyleBox(style);
        }
    }

    public void deployStyleBox(AdaptInfo.Style style) {
        if (!styleMaps.containsKey(style.viewid)) {
            styleMaps.put(style.viewid, new StyleBox(style));
        } else {
            styleMaps.get(style.viewid).addStyle(style);
        }
    }

    private void addListener(Integer resid, ListenerBox listener) {
        if (this.listenerMaps == null) {
            this.listenerMaps = new HashMap<Integer, ListenerBox>();
        }
        this.listenerMaps.put(resid, listener);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count = itemDataSrc == null ? 0 : itemDataSrc.getCount();
        if (isPaged()) {
            if ((count >= this.adaptInfo.pageSize && (count
                    % this.adaptInfo.pageSize == 0))) {
                inpage = true;
                count += 1;
            } else {
                inpage = false;
            }

        }
        return count;
    }

    private boolean isPaged() {
        if (adaptInfo != null && this.adaptInfo.pagefooterId != 0
                && this.adaptInfo.pageSize != -1) {
            return true;
        }
        return false;
    }

    private boolean inpage;

    public boolean isInpage() {
        return inpage;
    }

    public void setInpage(boolean inpage) {
        this.inpage = inpage;
    }

    public void setMapConf(MapViewConf mapconf) {
        this.mapconf = mapconf;
    }


    public static class ContainerInfo {
        public AdaptInfo adaptInfo = new AdaptInfo();
        public List<Integer> itemsid = new ArrayList<Integer>();
        public List<String> itemsname = new ArrayList<String>();
        public List<String> lineardatas = new ArrayList<String>();
        public Object containerId;
        public boolean simple;

        public List<String> getLinearnames() {
            return itemsname;
        }

        public void setLinearnames(String[] linearnames) {
            this.itemsname = Arrays.asList(linearnames);
        }

        public void setLinearids(Integer[] linearids) {
            this.itemsid = Arrays.asList(linearids);
        }

        public void setLineardatas(String[] lineardatas) {
            this.lineardatas = Arrays.asList(lineardatas);
        }

        public AdaptInfo getAdaptInfo() {
            return adaptInfo;
        }

        public void setAdaptInfo(AdaptInfo adaptInfo) {
            this.adaptInfo = adaptInfo;
        }

        public void setContainerId(Object id) {
            // TODO Auto-generated method stub
            this.containerId = id;
        }

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        // dangerous itemDataSrc.getCount() <= position
        try {
            if (itemDataSrc == null || itemDataSrc.getCount() < position) {
                return null;
            } else

                return itemDataSrc.getItem(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    private class ViewHolder {

        View[] viewCaches;
    }

    int forceHeight = -1;

    public View createItemView() {
        // TODO Auto-generated method stub
        View itemView = new ViewInflater(context).inflate(itemLayout, null);

        return itemView;
    }

    public Set<Integer> continues = new HashSet<Integer>();
    public boolean loadExclude;
    private boolean isSyncInImage = true;

    protected void getViewInDetail(Object item, int position, View convertView) {
        if (item == null) {
            return;
        }

        if (item instanceof Cursor || item instanceof SQLiteCursor) {
            treatCursor(item, convertView, position);
        }
//        else if (item instanceof Map || item instanceof JSONObject) {
//            if (mapconf != null) {
//                if (mapconf.viewlayoutid != 0) {
//                    mapconf.source(item, convertView).toView();
//                    return;
//
//                }
//
//            }
//            treatMap(item, convertView, position);
//        } else if (item instanceof Entity) {
//            if (mapconf != null) {
//                mapconf.source(((Entity) item).fieldContents, convertView).toView();
//                return;
//            }
//            treatMap(((Entity) item).fieldContents, convertView, position);
//        }
        else if (item instanceof JSONObject) {
        }
//        else if (item instanceof List) {
//            if (((List) item).size() > 0) {
//                if (((List) item).get(0) instanceof String) {
//                    treatFakeList(item, convertView, position);
//                }
//            }
//        }
        else if (item instanceof String && !item.toString().contains(":")) {
            treatFakeString(item, convertView, position);
        } else if (item instanceof List) {
//            treatObject(item, convertView, position);
            treatList(item, convertView, position);
        }

    }

    List<View> baseviews = new ArrayList<View>();

    private void treatList(Object item, View convertView, int position) {

        baseviews.clear();
        List<ViewGroup> baseGroup = new ArrayList<ViewGroup>();
        baseGroup.add((ViewGroup) convertView);
        getChildBaseViews(baseGroup);
        List list = ((ArrayList) item);

        for (int j = 0; j < list.size(); j++) {
            if (baseviews.size() - 1 >= j) {

            } else {
                break;
            }
            MapViewConf.with(context).setView(list.get(j).toString(), baseviews.get(j));
        }
    }

    private void getChildBaseViews(List<ViewGroup> baseGroup) {
        if (baseGroup.size() > 0) {
            for (int g = 0; g < baseGroup.size(); g++) {

                View paramGroup = baseGroup.get(g);
                List<ViewGroup> paramChildGroup = new ArrayList<ViewGroup>();
                for (int i = 0; i < ((ViewGroup) paramGroup).getChildCount(); i++) {
                    View view = ((ViewGroup) paramGroup).getChildAt(i);
                    if (view instanceof TextView || view instanceof ImageView) {
                        baseviews.add(view);
                    } else if (view instanceof ViewGroup) {
                        paramChildGroup.add((ViewGroup) view);
                    }
                    if (i == ((ViewGroup) paramGroup).getChildCount() - 1) {
                        getChildBaseViews(paramChildGroup);
                    }
                }

            }
        }
    }

    private void treatFakeString(Object item, View convertView, int position) {
        String astring = null;
        if (item instanceof String) {
            astring = (String) item;
            fakeStringViewIterate(convertView, astring, position);
        }
    }

    private void treatFakeList(Object item, View convertView, int position) {
        String name;
        Object value;
        List list = (List) item;
        String astring = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof String) {
                astring = (String) list.get(i);
                fakeStringViewIterate(convertView, astring, i);
            }
        }
    }

    private boolean fakeStringViewIterate(View convertView, String astring, int i) {
        if (convertView instanceof ViewGroup) {
            for (int j = 0; j < ((ViewGroup) convertView).getChildCount(); j++) {
                View view = ((ViewGroup) convertView).getChildAt(j);
                boolean stop = fakeStringViewIterate(view, astring, j);
                if (stop) {
                    return true;
                }
            }


        } else {
            if (convertView instanceof TextView || convertView instanceof ImageView) {

                MapViewConf.with(context).setView(astring, astring, astring, astring, convertView, convertView);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private void treatFake(Object item, View convertView, int position) {
        String name;
        Object value;
        List list = (List) item;
        List alist = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof List) {
                alist = (List) list.get(i);
                for (int j = 0; j < alist.size(); j++) {
                    name = this.fieldnames.get(j);
                    String val = (String) alist.get(j);
                    if (val != null) {
                        findAndBindView(convertView, j, alist, name, val);
                    }
                }

            }
        }
    }



    Exception imagexcepion;

    public int clickedItem = -1;

    public int getClickedPos() {
        return clickedItem;
    }

    public void setClickedItem(int clickedItem) {
        this.clickedItem = clickedItem;
    }

    protected boolean findAndBindView(View convertView, int pos, Object item,
                                      String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(
                    "check the 'value' data:ensure it is not null.thanq");
        }
        int theViewId = this.fieldnames.indexOf(name);
        View theView = convertView.findViewById(this.viewsid.get(theViewId));
        return MapViewConf.with(convertView.getContext()).setView(item, value, name, name, convertView, theView);

    }

    protected boolean setView(int pos, Object item, Object value,
                              View convertView, View theView) {
        if (theView == null) {
            return false;
        }
        theView.setVisibility(View.VISIBLE);
        StyleBox styleBox = null;

        if (theView instanceof ImageView) {
            if (value == null || value.toString().equals("-1")) {
                return false;
            }

            if (value instanceof Integer || TextUtils.isDigitsOnly(value.toString())) {
                ((ImageView) theView).setImageResource(Integer.parseInt(value
                        .toString()));
            } else if (value.getClass() == BitmapDrawable.class) {
                ((ImageView) theView).setImageDrawable((BitmapDrawable) value);
            } else if (value instanceof Drawable) {
                ((ImageView) theView).setImageDrawable((Drawable) value);
            } else if (value instanceof String) {
                Glide.with(context).load(value.toString()).into((ImageView) theView);

            }
            return true;
        } else if (theView instanceof CheckBox) {

            ((CheckBox) theView).setChecked(selected.contains(pos));
            ((CheckBox) theView).setVisibility(getVisibleOption(isVisible));
            return true;
        } else if (theView instanceof TextView) {

            ((TextView) theView)
                    .setText(value instanceof SpannableStringBuilder ? (SpannableStringBuilder) value
                            : value.toString());
            return true;
        }
        return false;
    }

    public void callbackImageLoadingDone(Exception lastimageException) {
        // TODO Auto-generated method stub

    }

    public Object getClickedItem() {
        // TODO Auto-generated method stub
        if (clickedItem == -1) {
            return null;
        }
        return getItem(clickedItem);
    }

    public void markVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getVisibleOption(boolean isVisible) {
        if (isVisible) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public boolean hasSelected() {
        return this.selected.size() > 0;
    }

    public boolean isSelectAll() {

        return this.selected.size() == this.getCount();
    }

    public View getView(Object item, View convertView, int position,
                        ViewGroup parent) {

        try {

            ListenerBox listener;
            viewContentMap.put(convertView, position);
            if (listenerMaps != null) {
                for (Entry en : listenerMaps.entrySet()) {

                    Integer resourceid = ((Integer) en.getKey());
                    listener = ((ListenerBox) en.getValue());
                    listener.setPos(position);
                    View view = ViewUtil.findViewById(convertView, resourceid);
                    if (view == null) {
                        continue;
                    }
                    for (Entry<Integer, ActionListener> e : listener.handlers
                            .entrySet()) {
                        int actionType = e.getKey().intValue();
                        ListenerBox instance = new ListenerBox(listener,
                                actionType);
                        switch (actionType) {
                            case ActionListener.OnClick:
                                view.setOnClickListener(instance);
                                break;
                            case ActionListener.OnLongClick:
                                view.setOnLongClickListener(instance);
                                break;
                            case ActionListener.OnTouch:
                                view.setOnTouchListener(instance);
                                break;
                            case ActionListener.OnCheckChanged:
                                if (view instanceof CheckBox) {
                                    ((CheckBox) view)
                                            .setOnCheckedChangeListener(instance);
                                }
                                break;
                            case ActionListener.OnTextChanged:
                                if (view instanceof EditText) {
                                    ((EditText) view)
                                            .addTextChangedListener(instance);
                                }
                                break;
                        }
                    }
                }
            }

            addStyles(position, convertView);

            getViewInDetail(item, position, convertView);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (isPaged()) {
            if (inpage && (position == getCount() - 1)) {
                return this.footerView;
            }
            if (convertView == this.footerView) {
                convertView = null;
            }
        }
        if (parent != null && parent instanceof AbsListView) {
            this.scrollpos = ((AbsListView) parent).getFirstVisiblePosition();
        }
        this.latestPosition = position;
        Object item = getItem(position);
        // Logs.i("item " + item + " position " + position);
        setCreatedView(false);
        if (null == convertView) {
            setCreatedView(true);
            convertView = createItemView();
        }

//		if (this.adaptInfo.scaledImageId != 0) {
//			View view = parent;
//			if (view instanceof GridView) {
//				convertView.findViewById(this.adaptInfo.scaledImageId)
//						.getLayoutParams().height = this.itemHeight = ((GridView) view)
//						.getWidth() / ((GridView) view).getNumColumns() - 20;
//			}
//		}
        ListenerBox listener;
        viewContentMap.put(convertView, position);
        if (listenerMaps != null) {
            for (Entry en : listenerMaps.entrySet()) {

                Integer resourceid = ((Integer) en.getKey());
                listener = ((ListenerBox) en.getValue());
                listener.setPos(position);
                View view = ViewUtil.findViewById(convertView, resourceid);
                if (view == null) {
                    continue;
                }
                for (Entry<Integer, ActionListener> e : listener.handlers
                        .entrySet()) {
                    int actionType = e.getKey().intValue();
                    ListenerBox instance = new ListenerBox(listener, actionType);
                    switch (actionType) {
                        case ActionListener.OnClick:
                            view.setOnClickListener(instance);
                            break;
                        case ActionListener.OnLongClick:
                            view.setOnLongClickListener(instance);
                            break;
                        case ActionListener.OnTouch:
                            view.setOnTouchListener(instance);
                            break;
                        case ActionListener.OnCheckChanged:
                            if (view instanceof CheckBox) {
                                ((CheckBox) view)
                                        .setOnCheckedChangeListener(instance);
                            }
                            break;
                        case ActionListener.OnTextChanged:
                            if (view instanceof EditText) {
                                ((EditText) view)
                                        .addTextChangedListener(instance);
                            }
                            break;
                    }
                }
            }
        }

        addStyles(position, convertView);


        getViewInDetail(item, position, convertView);
        int getcount = inpage ? this.getItemDataSrc().getCount() - 1 : this
                .getCount() - 1;
        if (isPaged() && inpage && position == getcount) {
            if (continueRunner != null) {
                continueRunner.run();
            }
        }
        return convertView;
    }

    boolean allowLoadMore;

    private void addStyles(int position, View convertView) {
        for (Entry<Integer, StyleBox> en : styleMaps.entrySet()) {

            View view = convertView.findViewById(en.getKey());
            if (view == null) {
                continue;
            }
            StyleBox stylebox = styleMaps.get(en.getKey());
            Map<Integer, AdaptInfo.Style> styles = (position == clickedItem ? stylebox.selectedStyle
                    : stylebox.handlers);
            for (AdaptInfo.Style style : styles.values()) {
                switch (style.styleitem) {
                    case AdaptInfo.Style.BACKGROUND_COLOR:
                        if (style.value instanceof Integer) {
                            view.setBackgroundColor(context.getResources()
                                    .getColor((Integer) style.value));

                        } else if (style.value instanceof String) {
                            view.setBackgroundColor(Color
                                    .parseColor((String) style.value));
                        }
                        break;
                    case AdaptInfo.Style.STRIKE_THRU_TEXT_FLAG:
                        if (view instanceof TextView) {
                            ((TextView) view).getPaint().setFlags(
                                    Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                        break;
                    case AdaptInfo.Style.TEXT_COLOR:
                        if (view instanceof TextView) {

                            if (style.value instanceof Integer) {
                                ((TextView) view).setTextColor(context
                                        .getResources().getColor(
                                                (Integer) style.value));
                            } else if (style.value instanceof String) {
                                ((TextView) view).setTextColor(Color
                                        .parseColor((String) style.value));
                            }
                        } else {
                            try {
                                throw new Exception(
                                        "TEXT_COLOR's view is not isntanceof TextView but "
                                                + view.getClass());
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        break;
                    case AdaptInfo.Style.VISIBLE:
                        view.setVisibility((Integer) style.value);
                        break;
                }
            }
        }
    }

    public static abstract class ActionListener {

        public static final int OnTextChanged = 4;
        public static final int OnClick = 0;
        public static final int OnLongClick = 1;
        public static final int OnTouch = 2;
        public static final int OnCheckChanged = 3;
        public int listenerType = -1;
        private MapAdapter baseAdapter;
        public int resrcId;
        public View itemView;
        private ListenerBox listener;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + listenerType;
            result = prime * result + resrcId;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ActionListener other = (ActionListener) obj;
            if (listenerType != other.listenerType)
                return false;
            if (resrcId != other.resrcId)
                return false;
            return true;
        }

        public int getListenerType() {
            return listenerType;
        }

        public int getResrcId() {
            return resrcId;
        }

        public ActionListener(int resrcId, int listenerType) {
            this.listenerType = listenerType;
            this.resrcId = resrcId;
        }

        public abstract void handle(MapAdapter mapAdapter, View view, int pos,
                                    ListenerBox listenerBox, Object retainedObj);

        public void invokeHandle(View view, ListenerBox listener) {
            this.listener = listener;
            handle(getBaseAdapter(), view, listener.pos, listener, null);
        }

        public int findViewIndex(View view) {
            View vg = (View) view.getParent();
            if (vg.getTag() == null) {
                return findViewIndex(vg);
            }
            return (Integer) vg.getTag();
        }

        public void setBaseAdapter(MapAdapter baseAdapter) {
            this.baseAdapter = baseAdapter;
        }

        public MapAdapter getBaseAdapter() {
            return baseAdapter;
        }
    }

    private int itemHeight = -1;

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public static class AdaptInfo {
        public static AdaptInfo generate() {
            return new AdaptInfo();
        }

        public int scaledImageId = -1;

        public int getScaledImageId() {
            return scaledImageId;
        }

        public void setScaledImageId(int scaledImageId) {
            this.scaledImageId = scaledImageId;
        }

        public ContinueRunner continueRunner;
        public MapContent listviewItemData;// actual data-carried object
        /**
         * data fields in
         */
        public String[] objectFields = new String[]{};
        // sequence which list to
        // view ids
        public Integer[] viewIds = new Integer[0]; // id array in listview item
        /**
         * layout id for each item in listview
         */
        public int listviewItemLayoutId;//
        public Set<ActionListener> actionListeners = new HashSet<ActionListener>(
                0);// varied action listeners
        public int pageSize = -1;
        public int adaptviewid = -1;
        public List<String> objectFieldList = new ArrayList<String>(0);
        public List viewIdList = new ArrayList(0);
        public Set<Style> styles = new HashSet<Style>();
        public int pagefooterId = 0;

        public ContinueRunner getContinueRunner() {
            return continueRunner;
        }

        public AdaptInfo addContinueRunner(ContinueRunner continueRunner) {
            this.continueRunner = continueRunner;
            return this;
        }

        public MapContent getListviewItemData() {
            return listviewItemData;
        }

        public AdaptInfo addListviewItemData(MapContent listviewItemData) {
            this.listviewItemData = listviewItemData;
            return this;
        }

        public String[] getObjectFields() {
            return objectFields;
        }

        public AdaptInfo addObjectFields(String[] objectFields) {
            this.objectFields = objectFields;
            return this;
        }

        public Integer[] getViewIds() {
            return viewIds;
        }

        public AdaptInfo addViewIds(Integer[] viewIds) {
            this.viewIds = viewIds;
            return this;
        }

        public int getListviewItemLayoutId() {
            return listviewItemLayoutId;
        }

        public AdaptInfo addListviewItemLayoutId(int listviewItemLayoutId) {
            this.listviewItemLayoutId = listviewItemLayoutId;
            return this;
        }

        public Set<ActionListener> getActionListeners() {
            return actionListeners;
        }

        public AdaptInfo addActionListeners(Set<ActionListener> actionListeners) {
            this.actionListeners = actionListeners;
            return this;
        }

        public int getAdaptviewid() {
            return adaptviewid;
        }

        public AdaptInfo addAdaptviewid(int adaptviewid) {
            this.adaptviewid = adaptviewid;
            return this;
        }

        public void setUnpaged() {
            pageSize = -1;
            pagefooterId = 0;
        }

        public float heightRatio;

        // in
        // which all events would be
        // received and have been done
        // handling.
        public AdaptInfo addActionListener(ActionListener actionListener) {
            actionListeners.add(actionListener);
            return this;
        }

        public AdaptInfo addStyle(Style style) {
            styles.add(style);
            return this;
        }

        public void appendResCntPair(int id, String item) {
            if (objectFieldList.size() == 0) {
                objectFieldList.addAll(Arrays.<String>asList(objectFields));
            }
            if (viewIdList.size() == 0) {
                viewIdList.addAll(Arrays.asList(viewIds));
            }
            objectFieldList.add(item);
            viewIdList.add(id);
        }

        public static class Style {
            public static final int TEXT_COLOR = 0;
            public static final int BACKGROUND_COLOR = 1;
            public static final int VISIBLE = 2;
            public static final int STRIKE_THRU_TEXT_FLAG = 3;
            public int viewid;
            public int styleitem;
            public Object value;
            public boolean selectedItem;

            @Override
            public String toString() {
                return "Style [viewid=" + viewid + "]";
            }

            public Style(int viewid, int style, Object value) {
                super();
                this.viewid = viewid;
                this.styleitem = style;
                this.value = value;
            }

            public Style(boolean selectedItem, int viewid, int styleitem,
                         Object value) {
                this(viewid, styleitem, value);
                this.selectedItem = selectedItem;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (obj == null)
                    return false;
                if (getClass() != obj.getClass())
                    return false;
                Style other = (Style) obj;
                if (viewid != other.viewid)
                    return false;
                return true;
            }

        }

    }

    public ContinueRunner continueRunner;

    public ContinueRunner getContinueRunner() {
        return continueRunner;
    }

    public void setContinueRunner(ContinueRunner continueRunner) {
        this.continueRunner = continueRunner;
    }

    public static abstract class ContinueRunner {
        public Object lastItm;
        public String obj;
        public Compt httpFetchCompt;

        public abstract void run(Compt httpFetchCompt, Object obj,
                                 Object lastItm);

        public void run() {
            obj = getParam();
            run(httpFetchCompt, obj, lastItm);
        }

        public abstract String getParam();

        public ContinueRunner setHttpFetchCompt(Compt httpFetchCompt) {
            this.httpFetchCompt = httpFetchCompt;
            return this;
        }

    }

    Map<Integer, AdaptInfo> id_adaptinfo = new HashMap<Integer, AdaptInfo>();

    public void notifyDataSetChanged(int id) {
        // TODO Auto-generated method stub

        if (id_adaptinfo.containsKey(id)) {

            deployAdapter(id);
        } else
            super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        deployListeners(handlers);
        deployStyleBoxes(styles);

        super.notifyDataSetChanged();
    }

    public void addStyle(AdaptInfo.Style style) {
        this.styles.remove(style);
        this.styles.add(style);
    }

    public MapAdapter(Context context, ContainerInfo linearInfo) {
        init(context);
        addMapInfo(linearInfo.adaptInfo, true);
        this.linearInfo = linearInfo;

        // TODO Auto-generated constructor stub
    }
}
