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
import com.example.sobo.zhihu.adapter.ThemeListAdpater;
import com.example.sobo.zhihu.bean.ZhihuThemes;
import com.example.sobo.zhihu.presenter.ZhihuThemePresenter;
import com.example.sobo.zhihu.presenter.implPresenter.ZhihuThemePresenterImpl;
import com.example.sobo.zhihu.presenter.implView.INavFragment;
import com.example.sobo.zhihu.util.NetWorkUtil;
import com.example.sobo.zhihu.widget.WrapContentLinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sobo on 17/3/29.
 */

public class NavFragment extends BaseFragment implements INavFragment {

    @BindView(R.id.nav_recycler)
    RecyclerView mRecyclerView;

    private View view;
    private Unbinder mUnbinder;
    private ZhihuThemePresenter mZhihuThemePresenter;
    private ThemeListAdpater mThemeListAdpater;
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.drawer_theme, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialData();//初始化数据
        initialView();//初始化view
    }

    private void initialData() {
        mZhihuThemePresenter = new ZhihuThemePresenterImpl(getContext(), this);
        mThemeListAdpater = new ThemeListAdpater(getContext());
    }

    private void initialView() {
        mLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);  //设置布局管理器
        mRecyclerView.setAdapter(mThemeListAdpater);//设置适配器
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getContext())) {
            loadThemeList();
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    public void loadThemeList() {
        mZhihuThemePresenter.getZhihuThemes();
    }

    @Override
    public void updateThemeList(ZhihuThemes themes) {
        mThemeListAdpater.setItems(themes);
    }
}
