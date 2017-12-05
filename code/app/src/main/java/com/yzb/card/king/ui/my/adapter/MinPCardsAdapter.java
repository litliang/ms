package com.yzb.card.king.ui.my.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.image.AbImageLoader;
import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.ui.gift.bean.ECardBean;
import com.yzb.card.king.util.FastBlur;
import com.yzb.card.king.util.ImageBlur;
import com.yzb.card.king.util.XImageOptionUtil;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 类  名：功能：礼品卡-选购心意e卡；
 * 作  者：Li Yubing
 * 日  期：2017/10/17
 * 描  述：
 */
public class MinPCardsAdapter extends BaseListAdapter<ECardBean> {
    public static final int WHAT_ITEM = 0x001;
    private ImageOptions imageOptions;
    private Handler dataHandler;
    private Callback.CommonCallback<Drawable> commonCallback;

    public MinPCardsAdapter(Context context) {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(2), ImageView.ScaleType.FIT_XY);

    }

    LinkedList linkedlist = new LinkedList();

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        linkedlist.addFirst(position);
        if (linkedlist.size() > 3) {
            linkedlist.removeLast();
        }
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_mind_pcards, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ECardBean bean = getItem(position);

        final View finalConvertView = convertView;
        commonCallback = new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(final Drawable drawable) {
                if (finalConvertView.findViewById(R.id.blur).getTag() == null) {
                    final View view = finalConvertView.findViewById(R.id.shadow);
                    final int rectx = 0;
                    int y = new Float(drawable.getIntrinsicHeight() * (1f - new Float(view.getLayoutParams().height) / new Float(finalConvertView.findViewById(R.id.ivCardLogo).getLayoutParams().height))).intValue();
                    int w = drawable.getIntrinsicWidth();
                    final int h = view.getLayoutParams().height;

                    if (y + h > drawable.getIntrinsicHeight()) {
                        y = drawable.getIntrinsicHeight() - h;
                    }
                    final int finalY = y;
                    final int finalW = w;
                    final Bitmap raw = BitmapUtil.drawableToBitmap(drawable);

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Looper.prepare();

                            final Bitmap bmp = Bitmap.createBitmap(raw, rectx, finalY, finalW, h);
                            final Bitmap blur = FastBlur.doBlur(bmp, 20, true);
                            new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    if (linkedlist.contains(position)) {

                                        ((ImageView) finalConvertView.findViewById(R.id.blur)).setImageBitmap(blur);
                                    }
                                }
                            }.sendEmptyMessage(0);
                            Looper.loop();
                        }
                    }.start();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            Map map = new HashMap();
        };
        ((ImageView) finalConvertView.findViewById(R.id.blur)).setImageBitmap(null);
//        x.image().loadDrawable(ServiceDispatcher.getImageUrl(bean.getImageCode()), imageOptions, commonCallback);
        x.image().bind(holder.ivCardLogo,ServiceDispatcher.getImageUrl(bean.getImageCode()),imageOptions);
        holder.tvCardIntro.setText(bean.getBlessWord());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataHandler != null) {
                    Message msg = dataHandler.obtainMessage(WHAT_ITEM);
                    msg.arg1 = position;
                    dataHandler.sendMessage(msg);
                }
            }
        });
        return holder.root;
    }

    public void setDataHandler(Handler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public class ViewHolder {
        @ViewInject(R.id.ivCardLogo)
        public ImageView ivCardLogo;

        @ViewInject(R.id.tvCardIntro)
        public TextView tvCardIntro;

        public View root;


        public ViewHolder(View root) {
            this.root = root;
            x.view().inject(this, root);
        }
    }
}

