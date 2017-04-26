package com.example.sobo.zhihu.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/*
        重现的方法是：使用 RecyclerView 加官方下拉刷新的时候，如果绑定的 List 对象在更新数据之前进行了 clear，
        而这时用户紧接着迅速上滑 RV，就会造成崩溃，而且异常不会报到你的代码上，属于RV内部错误。初次猜测是，
        当你 clear 了 list 之后，这时迅速上滑，而新数据还没到来，导致 RV 要更新加载下面的 Item 时候，
        找不到数据源了，造成 crash.
* */

/*
        来源于  http://blog.csdn.net/qq_24956515/article/details/52860830
* */

public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
