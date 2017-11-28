package com.yzb.card.king.ui.my.holder;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.my.bean.CardInfo;
import com.yzb.card.king.ui.other.Holder;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.UiUtils;
import com.yzb.card.king.util.photoutils.BitmapUtil;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * 描述：
 * 作者：殷曙光
 * 日期：2016/12/22 17:03
 */
public class MyCardHolder extends Holder<CardInfo>
{
    private ImageView ivBankLogo;
    private TextView tvBankName;
    private TextView tvCardType;
    private TextView tvTailNum;
    private ImageView tvDelete;
    private View llContent;

    @Override
    public View initView()
    {
        View view = UiUtils.inflate(R.layout.holder_my_add_card);
        ivBankLogo = (ImageView) view.findViewById(R.id.ivBankLogo);
        tvBankName = (TextView) view.findViewById(R.id.tvBankName);
        tvCardType = (TextView) view.findViewById(R.id.tvCardType);
        tvTailNum = (TextView) view.findViewById(R.id.tvTailNum);
        tvDelete = (ImageView) view.findViewById(R.id.tvDelete);
        llContent = view.findViewById(R.id.llContent);
        return view;
    }

    @Override
    public void refreshView(CardInfo data)
    {

        x.image().bind(ivBankLogo, ServiceDispatcher.getImageUrl(data.getArchivesPhoto()),
                new Callback.CommonCallback<Drawable>()
                {
                    @Override
                    public void onSuccess(Drawable drawable)
                    {
                       ivBankLogo.setImageDrawable(drawable);
//                        Bitmap bitmap = BitmapUtil.drawableToBitmap(drawable);
//                        Palette.Builder builder = Palette.from(bitmap);
//                        Palette palette = builder.generate();
//                        Palette.Swatch s1 = palette.getVibrantSwatch();
//                        Palette.Swatch s2 = palette.getDarkVibrantSwatch(); //充满活力的暗色类型色板
//                        Palette.Swatch s3 = palette.getLightVibrantSwatch(); //充满活力的亮色类型色板
//                        Palette.Swatch s4 = palette.getMutedSwatch(); //黯淡的色板
//                        Palette.Swatch s5 = palette.getDarkMutedSwatch(); //黯淡的暗色类型色板（翻译过来没有原汁原味的赶脚啊！）
//                        Palette.Swatch s6 = palette.getLightMutedSwatch(); //黯淡的亮色类型色板
//
//                        if (getSwatch(s1, s2, s3, s4, s5, s6) != null)
//                        {
//                            setBg(getSwatch(s1, s2, s3, s4, s5, s6));
//                        } else
//                        {
//                            llContent.setBackgroundResource(R.drawable.my_card_bg_red);
//                        }
                    }

//                    private Palette.Swatch getSwatch(Palette.Swatch s1, Palette.Swatch s2,
//                                                     Palette.Swatch s3, Palette.Swatch s4,
//                                                     Palette.Swatch s5, Palette.Swatch s6)
//                    {
//                        if (s1 != null) return s1;
//                        if (s2 != null) return s2;
//                        if (s3 != null) return s3;
//                        if (s4 != null) return s4;
//                        if (s5 != null) return s5;
//                        if (s6 != null) return s6;
//                        return null;
//                    }
//
//                    private void setBg(Palette.Swatch s1)
//                    {
//                        int strokeWidth = 1;
//                        int roundRadius = UiUtils.dp2px(5);
//                        int strokeColor = s1.getRgb();
//                        int fillColor = s1.getRgb();
//                        LogUtil.e("#" + fillColor);
//                        GradientDrawable gd = new GradientDrawable();
//                        gd.setColor(fillColor);
//                        gd.setCornerRadius(roundRadius);
//                        gd.setStroke(strokeWidth, strokeColor);
//                        llContent.setBackground(gd);
//                    }

                    @Override
                    public void onError(Throwable throwable, boolean b)
                    {

                    }

                    @Override
                    public void onCancelled(CancelledException e)
                    {

                    }

                    @Override
                    public void onFinished()
                    {

                    }
                });
        tvBankName.setText(data.getBankName());
        tvCardType.setText(data.getTypeName());
        tvTailNum.setText(data.getFullNo());
    }

    public void setDeleteListener(View.OnClickListener listener)
    {
        tvDelete.setOnClickListener(listener);
    }
}
