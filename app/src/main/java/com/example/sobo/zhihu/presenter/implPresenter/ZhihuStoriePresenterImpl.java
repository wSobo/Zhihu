package com.example.sobo.zhihu.presenter.implPresenter;

import com.example.sobo.zhihu.api.ApiManage;
import com.example.sobo.zhihu.bean.ZhihuStorie;
import com.example.sobo.zhihu.presenter.ZhihuStoriePresenter;
import com.example.sobo.zhihu.presenter.implView.IZhihuStorie;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sobo on 16/12/1.
 */

public class ZhihuStoriePresenterImpl extends BasePresenterImpl implements ZhihuStoriePresenter {


    private IZhihuStorie mIZhihuStorie;

    public ZhihuStoriePresenterImpl(IZhihuStorie iZhihuStorie) {
        if (iZhihuStorie == null) {
            throw new IllegalArgumentException("zhihuStory必须不为空");
        }
        mIZhihuStorie = iZhihuStorie;
    }


    @Override
    public void getZhihuStorie(String id) {
        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getZhihuStorie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuStorie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mIZhihuStorie.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ZhihuStorie zhihuStorie) {
                        mIZhihuStorie.showZhihuStorie(zhihuStorie);
                    }
                });
        addSubscription(subscription);


    }

}
