package com.yzb.card.king.bean.common;

/**
 * 类  名：碎片信息事件
 * 作  者：Li Yubing
 * 日  期：2017/1/10
 * 描  述：
 */
public class FragmentMessageEvent
{
    /**
     * 碎片编号
     */
    private int fragmentIndex;
    private boolean tag; //标记；

    public boolean getTag()
    {
        return tag;
    }

    public void setTag(boolean tag)
    {
        this.tag = tag;
    }

    public int getFragmentIndex()
    {
        return fragmentIndex;
    }

    public void setFragmentIndex(int fragmentIndex)
    {
        this.fragmentIndex = fragmentIndex;
    }


}
