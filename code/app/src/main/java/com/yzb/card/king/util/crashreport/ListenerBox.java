package com.yzb.card.king.util.crashreport;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.HashMap;
import java.util.Map;


/**
 * 对onclick、ontouch、onlongclick、oncheckChange等listener的实现
 * @author gyz
 *
 */
public class ListenerBox implements OnClickListener, OnTouchListener,
		OnLongClickListener, OnCheckedChangeListener,TextWatcher {
	public int pos;
	public Map<Integer, MapAdapter.ActionListener> handlers = new HashMap<Integer, MapAdapter.ActionListener>();
	private MotionEvent onTouch_MotionEvent;
	private boolean onCheckedChange_BooleanArg;
	public MapAdapter basicAdapter;

	public ListenerBox(MapAdapter ba, MapAdapter.ActionListener lwListViewHandler) {
		// TODO Auto-generated constructor stub
		this.basicAdapter = ba;
		handlers.put(lwListViewHandler.getListenerType(), lwListViewHandler);

	}

	public ListenerBox(ListenerBox listenerBox,Integer actiontype) {
		super();
		this.pos = listenerBox.pos;
		this.basicAdapter = listenerBox.basicAdapter;
		this.handlers.put(actiontype, listenerBox.handlers.get(actiontype));
	}

	public void addActionListener(MapAdapter.ActionListener lwListViewHandler) {
		// TODO Auto-generated constructor stub
		handlers.put(lwListViewHandler.getListenerType(), lwListViewHandler);
	}

	public ListenerBox setBasicAdapter(MapAdapter basicAdapter) {
		this.basicAdapter = basicAdapter;
		return this;
	}

	public MotionEvent getOnTouch_MotionEvent() {
		return onTouch_MotionEvent;
	}

	public boolean isOnCheckedChange_BooleanArg() {
		return onCheckedChange_BooleanArg;
	}


	public int getPos() {
		return pos;
	}

	public ListenerBox setPos(int pos) {
		this.pos = pos;
		return this;
	}

	@Override
	public boolean onLongClick(View view) {
		// TODO Auto-generated method stub
		if (handlers.containsKey(MapAdapter.ActionListener.OnLongClick)) {
			MapAdapter.ActionListener handler = handlers.get(MapAdapter.ActionListener.OnLongClick);
			handler.handle(handler.getBaseAdapter(), view, pos, this, null);
		}
		return false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		// TODO Auto-generated method stub
		Logs.i("", "onTouch --------------- ");
		if (handlers.containsKey(MapAdapter.ActionListener.OnTouch)) {
			onTouch_MotionEvent = motionEvent;

			MapAdapter.ActionListener handler = handlers.get(MapAdapter.ActionListener.OnTouch);
			handlers.get(MapAdapter.ActionListener.OnTouch).handle(
					handler.getBaseAdapter(), view, pos, this, null);
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (handlers.containsKey(MapAdapter.ActionListener.OnClick)) {
			MapAdapter.ActionListener handler = handlers.get(MapAdapter.ActionListener.OnClick);
			handlers.get(MapAdapter.ActionListener.OnClick).handle(
					handler.getBaseAdapter(), view, pos, this, null);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean bArg) {
		// TODO Auto-generated method stub
		if (handlers.containsKey(MapAdapter.ActionListener.OnCheckChanged)) {
			onCheckedChange_BooleanArg = bArg;
			MapAdapter.ActionListener handler = handlers
					.get(MapAdapter.ActionListener.OnCheckChanged);
			handlers.get(MapAdapter.ActionListener.OnCheckChanged).handle(
					handler.getBaseAdapter(), compoundButton, pos, this, null);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (handlers.containsKey(MapAdapter.ActionListener.OnTextChanged)) {
			MapAdapter.ActionListener handler = handlers
					.get(MapAdapter.ActionListener.OnTextChanged);
			handlers.get(MapAdapter.ActionListener.OnTextChanged).handle(
					handler.getBaseAdapter(), null, pos, this,s);
		}
	}
}
