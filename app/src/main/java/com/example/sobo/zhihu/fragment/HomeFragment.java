package com.example.sobo.zhihu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sobo.zhihu.MyApplication;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.adapter.ZhihuAdapter;
import com.example.sobo.zhihu.bean.ZhihuDaily;
import com.example.sobo.zhihu.presenter.implPresenter.ZhihuPresenterImpl;
import com.example.sobo.zhihu.presenter.implView.IZhihuFragment;
import com.example.sobo.zhihu.util.NetWorkUtil;
import com.example.sobo.zhihu.widget.WrapContentLinearLayoutManager;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment implements IZhihuFragment {

    //view
    @BindView(R.id.recycler_home)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress)
    ProgressBar progress;

    private boolean loading;
    private String currentLoadDate;
    private Unbinder unbinder;
    private View view = null;
    private ZhihuPresenterImpl zhihuPresenter;
    private ZhihuAdapter zhihuAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.OnScrollListener loadingMoreListener; //向上滚动时 则加载更多的  监听器


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialData();//初始化数据
        initialView();//初始化view
    }

    //初始化数据
    private void initialData() {
        zhihuPresenter = new ZhihuPresenterImpl(getContext(), this);
        zhihuAdapter = new ZhihuAdapter(getContext());
    }

    //初始化view
    private void initialView() {
        // 初始化监听器
        initialListener();
        mLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);  //设置布局管理器
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(zhihuAdapter);//设置适配器
        mRecyclerView.addOnScrollListener(loadingMoreListener);//添加监听器 监听划到底部
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
            loadDate();
        }
    }

    //更新数据
    private void loadDate() {
        if (zhihuAdapter.getItemCount() > 0) {
            zhihuAdapter.clearData();
        }
        currentLoadDate = "0";
        zhihuPresenter.getLastDaily();
    }

    //加载更多日期的日报
    private void loadMoreDate() {
        zhihuAdapter.loadingStart();
        zhihuPresenter.getTheDaily(currentLoadDate);
    }


    //初始化监听器
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
    public void updateList(final ZhihuDaily zhihuDaily) {
        // 是否加载中
        if (loading) {
            loading = false;
            zhihuAdapter.loadingFinish();
        }

        currentLoadDate = zhihuDaily.getDate();
        if (zhihuDaily.getTopStories() != null) {
            zhihuAdapter.addItems(zhihuDaily.getStories(), zhihuDaily.getTopStories());
        } else {
            zhihuAdapter.addItems(zhihuDaily.getStories());
        }

        // 如果加载的条数太少还不够滚动 则加载更多
        if (!mRecyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
            loadMoreDate();
        }
    }


    //显示加载进度条
    @Override
    public void showProgress() {
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    //隐藏加载进度条
    @Override
    public void hidProgress() {
        if (progress != null) {
            progress.setVisibility(View.INVISIBLE);
        }

    }

    //显示错误信息
    @Override
    public void showError(Throwable e) {
        if (mRecyclerView != null) {
            e.printStackTrace();
            Log.e("homefagment", e.getMessage());
            Snackbar.make(mRecyclerView, getString(R.string.snack_infor), Snackbar.LENGTH_SHORT).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentLoadDate.equals("0")) {
                        zhihuPresenter.getDailyFromCache();
                    } else {
                        zhihuPresenter.getTheDaily(currentLoadDate);
                    }
                }
            }).show();

        }

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        zhihuPresenter.unSubcribe();
        super.onDestroyView();
    }


}

