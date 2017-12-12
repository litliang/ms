package com.yzb.card.king.ui.bonus.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.ui.bonus.bean.BounsThemeBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.base.BaseListAdapter;
import com.yzb.card.king.util.XImageOptionUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 红包主题；
 *
 * @author:gengqiyun
 * @date: 2016/12/21
 */
public class BounsThemeAdapter extends BaseListAdapter<BounsThemeBean> {
    private ImageOptions imageOptions;

    private Handler bounsThemeHandler;

    public BounsThemeAdapter(Context context, Handler bounsThemeHandler) {
        super(context);
        imageOptions = XImageOptionUtil.getRoundImageOption(DensityUtil.dip2px(10), ImageView.ScaleType.FIT_XY);
        this.bounsThemeHandler = bounsThemeHandler;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_bouns_theme, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BounsThemeBean bean = getItem(position);

        if (bean == null) {

            holder.panelIv.setBackgroundResource(R.mipmap.bg_defined_red_packet);
            holder.tv.setText(R.string.define_red_envelope);

        } else {

            holder.panelIv.setBackgroundResource(bean.isSelect() ? R.drawable.shape_solid_white_red_stroke
                    : R.drawable.bg_transparent);

            holder.tv.setSelected(bean.isSelect());

            x.image().bind(holder.iv, ServiceDispatcher.getImageUrl(bean.getCloseImageCode()), imageOptions);

            holder.tv.setText(bean.getThemeName());
        }

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean == null) {

                    bounsThemeHandler.sendEmptyMessage(0);

                } else {
                    singleSelectItem(bean);
                    bounsThemeHandler.sendEmptyMessage(1);
                }
            }
        });

        return holder.root;
    }


    /**
     * 单选item；
     */
    private void singleSelectItem(BounsThemeBean bean) {

        for (int i = 0; i < mList.size(); i++) {
            BounsThemeBean bounsThemeBean = mList.get(i);
            if (bounsThemeBean != null) {
                bounsThemeBean.setIsSelect(false);
            }

        }
        if (bean != null) {
            bean.setIsSelect(true);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取选中的item;
     */
    public BounsThemeBean getSelectItem() {
        for (int i = 0; i < mList.size(); i++) {
            BounsThemeBean bounsThemeBean = mList.get(i);

            if (bounsThemeBean != null) {

                if (bounsThemeBean.isSelect()) {
                    return mList.get(i);
                }
            } else {
                break;
            }

        }
        return null;
    }

    /**
     * 选中某一项；
     *
     * @param themeId
     */
    public void selectItem(String themeId) {
        if (!isEmpty(themeId)) {
            for (int i = 0; i < mList.size(); i++) {
                BounsThemeBean bounsThemeBean = mList.get(i);
                if (bounsThemeBean != null) {
                    if (themeId.equals(bounsThemeBean.getThemeId())) {
                        mList.get(i).setIsSelect(true);
                        break;
                    }
                }
            }
        }
    }

    public class ViewHolder {
        @ViewInject(R.id.panelIv)
        public View panelIv;
        @ViewInject(R.id.iv)
        public ImageView iv;
        @ViewInject(R.id.tv)
        public TextView tv;
        public View root;

        public ViewHolder(View root) {
            this.root = root;
            x.view().inject(this, root);
        }
    }


}
