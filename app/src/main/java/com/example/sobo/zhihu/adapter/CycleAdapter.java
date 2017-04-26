package com.example.sobo.zhihu.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.sobo.zhihu.widget.CycleView;

import java.util.List;

/**
 * Created by sobo on 17/2/14.
 */

public class CycleAdapter extends PagerAdapter {

    // 轮播view集合
    private List<View> mViewList;

    // 轮播监听
    private CycleView.CycleViewListener mCycleViewListener;

    //数据集合大小
    private int mCount;

    public CycleAdapter(List<View> viewList, CycleView.CycleViewListener listener, int count) {
        mViewList = viewList;
        mCycleViewListener = listener;
        mCount = count;
    }

    @Override
    public int getCount() {
        return mViewList != null ? mViewList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mViewList.get(position);
        if (mCycleViewListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vPosition = position;
                    // 处理极端情况，此情况出现在轮播最后一张图切换到第一张图，ViewPaper实现轮播原理决定的。
                    if (vPosition > mCount)
                        vPosition = vPosition % mCount;
                    vPosition --;
                    if (vPosition < 0)
                        vPosition = 0;
                    mCycleViewListener.onItemClick(vPosition);
                }
            });
        }
        container.addView(view);
        return view;
    }


}
