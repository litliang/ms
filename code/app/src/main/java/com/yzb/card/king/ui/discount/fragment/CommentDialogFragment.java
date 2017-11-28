package com.yzb.card.king.ui.discount.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yzb.card.king.R;
import com.yzb.card.king.sys.CardConstant;
import com.yzb.card.king.ui.base.BaseDialogFragment;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.luxury.activity.IDialogCallBack;
import com.yzb.card.king.ui.luxury.presenter.impl.VotePresenter;
import com.yzb.card.king.ui.other.presenter.CommonReviewPresenter;
import com.yzb.card.king.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiyun
 * @date 2016/5/24
 * 评论对话框；
 */
public class CommentDialogFragment extends BaseDialogFragment implements View.OnClickListener, BaseViewLayerInterface
{
    private static CommentDialogFragment dialogFragment;
    private IDialogCallBack resultCallBack;
    private Map<String, String> argMap;
    private EditText etComment;
    private TextView ivComment;

    private CommonReviewPresenter commonReviewPresenter;
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        commonReviewPresenter = new CommonReviewPresenter(this);
    }

    public static CommentDialogFragment getInstance(String je, String flag)
    {
        synchronized (CommentDialogFragment.class)
        {
            if (dialogFragment == null)
            {
                dialogFragment = new CommentDialogFragment();
            }
        }
        Bundle args = new Bundle();
        args.putString("je", je);
        args.putString("flag", flag);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    /**
     * 参数map；
     *
     * @param argMap
     */
    public CommentDialogFragment setArgs(Map<String, String> argMap)
    {
        this.argMap = argMap;
        return this;
    }

    public CommentDialogFragment setResultCallBack(IDialogCallBack resultCallBack)
    {
        this.resultCallBack = resultCallBack;
        return this;
    }

    @Override
    protected int getDialogStyle()
    {
        return R.style.dialog__no_dark_style;
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_comment_dialog;
    }

    @Override
    protected void initView(View view)
    {
        etComment = (EditText) view.findViewById(R.id.et_comment);
        etComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(140)});
        ivComment = (TextView) view.findViewById(R.id.iv_comment);
        ivComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_comment:
                String commentContent = etComment.getText().toString().trim();
                if (TextUtils.isEmpty(commentContent))
                {
                    ToastUtil.i(getActivity(), "请输入评论内容");
                    return;
                }
                try
                {
                    exeComment(URLEncoder.encode(commentContent, "utf-8"));
                } catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }


    /**
     * 评论；
     *
     * @param commentContent
     */
    private void exeComment(final String commentContent)
    {
        if (argMap == null || argMap.isEmpty()) return;

        if (isEmpty(argMap.get("voteId"))) return;

        commonReviewPresenter.sendCreateVoteReplyRequest(Long.parseLong(argMap.get("voteId")),commentContent);
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        if (resultCallBack != null)
        {
            dismiss();
            resultCallBack.dialogCallBack();
        }
    }

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        toastCustom(o+"");
    }
}
