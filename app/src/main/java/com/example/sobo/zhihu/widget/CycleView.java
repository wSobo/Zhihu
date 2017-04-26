package com.example.sobo.zhihu.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.adapter.CycleAdapter;
import com.example.sobo.zhihu.bean.ZhihuTopDailyItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sobo on 17/2/18.
 */

public class CycleView extends FrameLayout implements ViewPager.OnPageChangeListener {

    // viewpager 轮播图控件
    private ViewPager mViewPager;
    // 轮播监听
    private CycleViewListener cycleViewListener;
    // 轮播数据源
    private List<ZhihuTopDailyItem> topDailylist;
    // 要轮播的 views
    private List<View> mViews = new ArrayList<>();
    // viewpager的适配器
    private CycleAdapter mAdapter;
    // 当前轮播位置
    private int mCurrentPosition = 1;
    // 自动轮播线程
    private Handler mHandler;
    // 轮播时间间隔 5s
    private static final int interval = 5000;

    public static final int SCROLL_START = 0;

    private class MyHandler extends Handler {

        private final WeakReference<CycleView> mCycleView;

        public MyHandler(CycleView cycleView) {
            this.mCycleView = new WeakReference<CycleView>(cycleView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_START:
                    mCurrentPosition++;
                    mViewPager.setCurrentItem(mCurrentPosition);
                    sendScrollMessage(interval);
                default:
                    break;
            }
        }
    }

    public CycleView(Context context) {
        this(context, null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化
        initView(context);
    }

    /**
     * 初始化View
     */
    private void initView(Context context) {
        // 指定布局
        View view = LayoutInflater.from(context).inflate(R.layout.cycle_view_top, null);
        // findView
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        // 添加view 到轮播view
        this.addView(view);
        mHandler = new MyHandler(this);
    }

    /**
     * 设置数据
     */
    public void setData(List<ZhihuTopDailyItem> topList, Context context,
                        CycleViewListener listener) {
        // 如果context为空 则直接返回
        if (context == null)
            return;
        // 设置数据源
        topDailylist = topList;
        int count = 0;
        if (topDailylist != null) {
            count = topDailylist.size();
        }
        // 如果数据源为空 则返回
        if (topDailylist.size() == 0 && count == 0)
            return;
        // 清空views
        mViews.clear();

        // 把需要轮播的view添加进views ，分别再前后加上'最后一个view'和'第一个view' 以达到无限轮播
        mViews.add(getTopCycleView(context, topDailylist.get(count - 1)));
        for (int i = 0; i < count; i++) {
            mViews.add(getTopCycleView(context, topDailylist.get(i)));
        }
        mViews.add(getTopCycleView(context, topDailylist.get(0)));

        // 初始化轮播监听
        cycleViewListener = listener;
        // 设置适配器
        setAdapter(mViews, cycleViewListener, count);

        sendScrollMessage(interval);

    }

    private void sendScrollMessage(int interval) {
        mHandler.removeMessages(SCROLL_START);
        mHandler.sendEmptyMessageDelayed(SCROLL_START, interval);
    }


    /**
     * 设置适配器
     */
    private void setAdapter(List<View> views, CycleViewListener listener, int count) {
        mAdapter = new CycleAdapter(views, listener, count);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        // 从第二个开始循环（数据的第一个）
        mViewPager.setCurrentItem(mCurrentPosition, false);
    }

    /**
     * 初始化views时调用 返回一个view
     */
    private View getTopCycleView(Context context, ZhihuTopDailyItem item) {
        View view = LayoutInflater.from(context).inflate(R.layout.cycle_item, null, false);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        tv.setText(item.getTitle());
        Glide.with(context).load(item.getImage()).placeholder(R.drawable.defaultimg).into(iv);
        return view;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            // 闲置时
            case ViewPager.SCROLL_STATE_IDLE:
                // 当滑动到第一个view时 则跳转到倒数二个view；当滑动到最后一个view时 则跳转到第二个view（达到无限轮播）
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(mViews.size() - 2, false);
                } else if (mViewPager.getCurrentItem() == mViews.size() - 1) {
                    mViewPager.setCurrentItem(1, false);
                }
                mCurrentPosition = mViewPager.getCurrentItem();
                break;
        }
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.e("" + this, "被按下");
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("" + this, "抬起来");
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//        }
//        return false;
//    }

    public interface CycleViewListener {
        void onItemClick(int position);
    }

}
