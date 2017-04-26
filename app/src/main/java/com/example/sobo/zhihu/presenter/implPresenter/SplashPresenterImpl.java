package com.example.sobo.zhihu.presenter.implPresenter;

import com.example.sobo.zhihu.activity.SplashActivity;
import com.example.sobo.zhihu.api.ApiManage;
import com.example.sobo.zhihu.bean.LaunchImage;
import com.example.sobo.zhihu.presenter.SplashPresenter;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class SplashPresenterImpl extends BasePresenterImpl implements SplashPresenter {

    private SplashActivity mSplashActivity;

    public SplashPresenterImpl(SplashActivity splashActivity) {
        mSplashActivity = splashActivity;
    }

    @Override
    public void getLaunchImage() {

        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getLaunchImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LaunchImage>() {
                    @Override
                    public void onCompleted() {
                        mSplashActivity.onRequestEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSplashActivity.onRequestError();
                        mSplashActivity.onRequestEnd();
                    }

                    @Override
                    public void onNext(LaunchImage launchImage) {
                        mSplashActivity.setSplashImage(launchImage);
                    }
                });
        addSubscription(subscription);

    }

}
