package com.yzb.card.king.ui.discount.seatsale.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.yzb.card.king.R;


class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private SSView mSsView;
    private Context mContext;

    GestureListener(SSView paramSSView, Context context) {
        this.mSsView = paramSSView;
        this.mContext = context;
    }

    public boolean onDoubleTap(MotionEvent paramMotionEvent) {
        return super.onDoubleTap(paramMotionEvent);
    }

    public boolean onDoubleTapEvent(MotionEvent paramMotionEvent) {
        return super.onDoubleTapEvent(paramMotionEvent);
    }

    public boolean onDown(MotionEvent paramMotionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent paramMotionEvent1,
                           MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        return false;
    }

    public void onLongPress(MotionEvent paramMotionEvent) {
    }

    public boolean onScroll(MotionEvent paramMotionEvent1,
                            MotionEvent paramMotionEvent2, float x_scroll_distance, float y_scroll_distance) {
        //是否可以移动和点击
        if (!SSView.a(mSsView)) {
            return false;
        }
        //显示缩略图
        SSView.a(mSsView, true);
        boolean bool1 = true;
        boolean bool2 = true;
        if ((SSView.s(mSsView) < mSsView.getMeasuredWidth())
                && (0.0F == SSView.v(mSsView))) {
            bool1 = false;
        }

        if ((SSView.u(mSsView) < mSsView.getMeasuredHeight())
                && (0.0F == SSView.w(mSsView))) {
            bool2 = false;
        }

        if (bool1) {
            int k = Math.round(x_scroll_distance);
            //修改排数x轴的偏移量
            SSView.c(mSsView, (float) k);
//			Log.i("TAG", SSView.v(mSsView)+"");
            //修改座位距离排数的横向距离
            SSView.k(mSsView, k);
//			Log.i("TAG", SSView.r(mSsView)+"");
            if (SSView.r(mSsView) < 0) {
                //滑到最左
                SSView.i(mSsView, 0);
                SSView.a(mSsView, 0.0F);
            }

            if (SSView.r(mSsView) + mSsView.getMeasuredWidth() > SSView.s(mSsView)) {
                //滑到最右
                SSView.i(mSsView, SSView.s(mSsView) - mSsView.getMeasuredWidth());
                SSView.a(mSsView, (float) (mSsView.getMeasuredWidth() - SSView.s(mSsView)));
            }
        }

        if (bool2) {
            //上负下正- 往下滑则减
            int j = Math.round(y_scroll_distance);
            //修改排数y轴的偏移量
            SSView.d(mSsView, (float) j);
            //修改可视座位距离顶端的距离
            SSView.l(mSsView, j);
            Log.i("TAG", SSView.t(mSsView) + "");
            if (SSView.t(mSsView) < 0) {
                //滑到顶
                SSView.j(mSsView, 0);
                SSView.b(mSsView, 0.0F);
            }

            if (SSView.t(mSsView) + mSsView.getMeasuredHeight() > SSView
                    .u(mSsView)) {
                //滑到底
                SSView.j(mSsView, SSView.u(mSsView) - mSsView.getMeasuredHeight());
                SSView.b(mSsView, (float) (mSsView.getMeasuredHeight() - SSView.u(mSsView)));
            }
        }

        mSsView.invalidate();

//		Log.i("GestureDetector", "onScroll----------------------");
        return false;
    }

    public void onShowPress(MotionEvent paramMotionEvent) {
    }

    public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent) {
        return false;
    }

    public boolean onSingleTapUp(MotionEvent paramMotionEvent) {
//		Log.i("GestureDetector", "onSingleTapUp");
//		if(!SSView.a(mSsView)){
//			return false;
//		}
        //列数
        int i = SSView.a(mSsView, (int) paramMotionEvent.getX());
        //排数
        int j = SSView.b(mSsView, (int) paramMotionEvent.getY());

        if ((j >= 0 && j < SSView.b(mSsView).size())) {
            if (i >= 0 && i < ((ArrayList<Integer>) (SSView.b(mSsView).get(j))).size()) {
                ArrayList<Integer> localArrayList = (ArrayList<Integer>) SSView.b(mSsView).get(j);
                switch (localArrayList.get(i).intValue()) {
                    case 3://已选中
                        SSView.lessSelectedSeat(mSsView);
                        localArrayList.set(i, Integer.valueOf(1));
                        if (SSView.d(mSsView) != null) {
                            SSView.d(mSsView).a(i, j, false);
                        }
                        break;
                    case 1://可选
                        if (SSView.getSelectedSeat(mSsView) >= SSView.getImaxPay(mSsView)) {
                            String errorMsg = mContext.getResources().getString(R.string.seat_max_error_msg);
                            errorMsg = String.format(errorMsg, SSView.getImaxPay(mSsView));
                            SSView.d(mSsView).error(errorMsg);
                            return false;
                        }
                        SSView.addSelectedSeat(mSsView);
                        localArrayList.set(i, Integer.valueOf(3));
                        if (SSView.d(mSsView) != null) {
                            SSView.d(mSsView).b(i, j, false);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        //显示缩略图
        SSView.a(mSsView, true);
        mSsView.invalidate();
        return false;
    }
}