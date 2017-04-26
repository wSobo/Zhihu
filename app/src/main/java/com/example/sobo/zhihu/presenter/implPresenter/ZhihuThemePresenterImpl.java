package com.example.sobo.zhihu.presenter.implPresenter;

import android.content.Context;

import com.example.sobo.zhihu.activity.MainActivity;
import com.example.sobo.zhihu.api.ApiManage;
import com.example.sobo.zhihu.bean.ZhihuTheme;
import com.example.sobo.zhihu.bean.ZhihuThemeMore;
import com.example.sobo.zhihu.bean.ZhihuThemes;
import com.example.sobo.zhihu.presenter.ZhihuThemePresenter;
import com.example.sobo.zhihu.presenter.implView.INavFragment;
import com.example.sobo.zhihu.presenter.implView.IOthereThemeFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sobo on 17/3/19.
 */

public class ZhihuThemePresenterImpl extends BasePresenterImpl implements ZhihuThemePresenter {

    private IOthereThemeFragment mOthereThemeFragment;
    private INavFragment mINavFragment;
    private MainActivity mMainActivity;

    public ZhihuThemePresenterImpl(Context context, IOthereThemeFragment otherThemeFragment) {
        mOthereThemeFragment = otherThemeFragment;
    }

    public ZhihuThemePresenterImpl(Context context, INavFragment iNavFragment) {
        mINavFragment = iNavFragment;
    }

    public ZhihuThemePresenterImpl(Context context, MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }


    // 获取 Theme 列表
    @Override
    public void getZhihuThemes() {
        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getZhihuThemes()
                .subscribeOn(Schedulers.io()) //发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZhihuThemes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhihuThemes list) {
                        mINavFragment.updateThemeList(list);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getZhihuTheme(String id) {
        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getZhihuTheme(id)
                .subscribeOn(Schedulers.io()) //发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZhihuTheme>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhihuTheme zhihuTheme) {
                        mOthereThemeFragment.updateList(zhihuTheme);
                    }
                });
        addSubscription(subscription);

    }

    @Override
    public void getZhihuThemeMore(String id, String storyId) {
        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getZhihuThemeMore(id, storyId)
                .subscribeOn(Schedulers.io()) //发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZhihuThemeMore>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhihuThemeMore zhihuThemeMore) {
                        mOthereThemeFragment.updateMoreList(zhihuThemeMore);
                    }
                });
        addSubscription(subscription);
    }


}
