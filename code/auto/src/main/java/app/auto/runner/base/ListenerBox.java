package app.auto.runner.base;

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

import app.auto.runner.base.MapAdapter.ActionListener;

/**
 * 对onclick、ontouch、onlongclick、oncheckChange等listener的实现
 * @author gyz
 *
 */
public class ListenerBox implements OnClickListener, OnTouchListener,
		OnLongClickListener, OnCheckedChangeListener,TextWatcher {
	public int pos;
	public Map<Integer, ActionListener> handlers = new HashMap<Integer, ActionListener>();
	private MotionEvent onTouch_MotionEvent;
	private boolean onCheckedChange_BooleanArg;
	public MapAdapter basicAdapter;

	public ListenerBox(MapAdapter ba, ActionListener lwListViewHandler) {
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

	public void addActionListener(ActionListener lwListViewHandler) {
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
		if (handlers.containsKey(ActionListener.OnLongClick)) {
			ActionListener handler = handlers.get(ActionListener.OnLongClick);
			handler.handle(handler.getBaseAdapter(), view, pos, this, null);
		}
		return false;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		// TODO Auto-generated method stub
		Logs.i("", "onTouch --------------- ");
		if (handlers.containsKey(ActionListener.OnTouch)) {
			onTouch_MotionEvent = motionEvent;

			ActionListener handler = handlers.get(ActionListener.OnTouch);
			handlers.get(ActionListener.OnTouch).handle(
					handler.getBaseAdapter(), view, pos, this, null);
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (handlers.containsKey(ActionListener.OnClick)) {
			ActionListener handler = handlers.get(ActionListener.OnClick);
			handlers.get(ActionListener.OnClick).handle(
					handler.getBaseAdapter(), view, pos, this, null);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean bArg) {
		// TODO Auto-generated method stub
		if (handlers.containsKey(ActionListener.OnCheckChanged)) {
			onCheckedChange_BooleanArg = bArg;
			ActionListener handler = handlers
					.get(ActionListener.OnCheckChanged);
			handlers.get(ActionListener.OnCheckChanged).handle(
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
		if (handlers.containsKey(ActionListener.OnTextChanged)) {
			ActionListener handler = handlers
					.get(ActionListener.OnTextChanged);
			handlers.get(ActionListener.OnTextChanged).handle(
					handler.getBaseAdapter(), null, pos, this,s);
		}
	}
}
