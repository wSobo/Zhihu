package com.example.sobo.zhihu.presenter.implPresenter;

import com.example.sobo.zhihu.presenter.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sobo on 16/11/8.
 */

public class BasePresenterImpl implements BasePresenter {

    private CompositeSubscription mCompositeSubscription;

    //添加订阅
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    //取消订阅
    @Override
    public void unSubcribe() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
