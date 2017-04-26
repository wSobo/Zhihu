package com.example.sobo.zhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sobo.zhihu.MyApplication;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.adapter.OtherThemeAdapter;
import com.example.sobo.zhihu.bean.ZhihuTheme;
import com.example.sobo.zhihu.bean.ZhihuThemeMore;
import com.example.sobo.zhihu.presenter.ZhihuThemePresenter;
import com.example.sobo.zhihu.presenter.implPresenter.ZhihuThemePresenterImpl;
import com.example.sobo.zhihu.presenter.implView.IOthereThemeFragment;
import com.example.sobo.zhihu.util.NetWorkUtil;
import com.example.sobo.zhihu.widget.WrapContentLinearLayoutManager;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sobo on 17/3/19.
 */

public class OtherThemeFragment extends BaseFragment implements IOthereThemeFragment {


    @BindView(R.id.recycler_ot)
    RecyclerView mRecyclerView;

    private boolean loading;
    private String mThemeId;
    private String mStoryId;
    private Unbinder mUnbinder;
    private ZhihuThemePresenter mZhihuThemePresenter;
    private OtherThemeAdapter mOtherThemeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.OnScrollListener loadingMoreListener; //向上滚动时 则加载更多的  监听器


    public OtherThemeFragment(String themeId) {
        mThemeId = themeId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_othere_theme, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initialData(); //初始化数据
        initialView();//初始化view
    }

    private void initialData() {
        mZhihuThemePresenter = new ZhihuThemePresenterImpl(getContext(), this);
        mOtherThemeAdapter = new OtherThemeAdapter(getContext());
    }


    private void initialView() {
        // 初始化监听器
        initialListener();
        mLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);  //设置布局管理器
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(mOtherThemeAdapter);//设置适配器
        mRecyclerView.addOnScrollListener(loadingMoreListener);//添加监听器 监听划到底部
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
            loadDate();
        }


    }

    private void loadDate() {
        if (mOtherThemeAdapter.getItemCount() > 0) {
            mOtherThemeAdapter.clearData();
        }
        mZhihuThemePresenter.getZhihuTheme(mThemeId);
    }

    private void initialListener() {
        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    /*第一个可见item的位置 + 当前可见的item个数 >= item的总个数  这样就可以判断出来，是在底部了。*/
                    int visibleItemCount = mLinearLayoutManager.getChildCount();// 当前可见的item个数
                    int totalItemCount = mLinearLayoutManager.getItemCount();// item的总个数
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();//第一个可见的item
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMoreDate();
                    }
                }
            }
        };
    }

    @Override
    public void updateList(ZhihuTheme zhihuTheme) {
        // 是否加载中
        if (loading) {
            loading = false;
            mOtherThemeAdapter.loadingFinish();
        }
        mOtherThemeAdapter.addItems(zhihuTheme.getStories());
        mStoryId = zhihuTheme.getStories().get(zhihuTheme.getStories().size() - 1).getId();

        // 如果加载的条数太少还不够滚动 则加载更多
        if (!mRecyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }

    @Override
    public void updateMoreList(ZhihuThemeMore zhihuThemeMore) {
        // 是否加载中
        if (loading) {
            loading = false;
            mOtherThemeAdapter.loadingFinish();
        }
        mOtherThemeAdapter.addItems(zhihuThemeMore.getStories());
        mStoryId = zhihuThemeMore.getStories().get(zhihuThemeMore.getStories().size() - 1).getId();
    }

    private void loadMoreDate() {
        mOtherThemeAdapter.loadingStart();
        mZhihuThemePresenter.getZhihuThemeMore(mThemeId, mStoryId);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hidProgress() {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        mZhihuThemePresenter.unSubcribe();
        super.onDestroyView();
    }


}
