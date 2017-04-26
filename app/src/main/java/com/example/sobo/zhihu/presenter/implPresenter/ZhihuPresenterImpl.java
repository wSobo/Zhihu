package com.example.sobo.zhihu.presenter.implPresenter;

import android.content.Context;

import com.example.sobo.zhihu.api.ApiManage;
import com.example.sobo.zhihu.bean.ZhihuDaily;
import com.example.sobo.zhihu.bean.ZhihuDailyItem;
import com.example.sobo.zhihu.presenter.ZhihuPresenter;
import com.example.sobo.zhihu.presenter.implView.IZhihuFragment;
import com.example.sobo.zhihu.util.CacheUtil;
import com.google.gson.Gson;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sobo on 16/11/9.
 */

public class ZhihuPresenterImpl extends BasePresenterImpl implements ZhihuPresenter {

    private IZhihuFragment mZhihuFragment;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();


    public ZhihuPresenterImpl(Context context, IZhihuFragment zhihuFragment) {

        mZhihuFragment = zhihuFragment;
        mCacheUtil = CacheUtil.get(context);
    }

    @Override
    public void getLastDaily() {
        mZhihuFragment.showProgress();//显示Progress

        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getLastDaily()
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {

                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {

                        String date = zhihuDaily.getDate();

                        // 为 ZhihuDaily 中的 ZhihuDailyItem 设置 date
                        for (ZhihuDailyItem zhihuDailyItem:zhihuDaily.getStories()){
                            zhihuDailyItem.setDate(date);
                        }

                        return zhihuDaily;
                    }
                })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 弹出窗口显示错误信息
                        mZhihuFragment.hidProgress();
                        mZhihuFragment.showError(e);
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mZhihuFragment.hidProgress();//隐藏进度条
                        mCacheUtil.put("zhihu",gson.toJson(zhihuDaily));//缓存
                        mZhihuFragment.updateList(zhihuDaily);//更新List
                    }
                });

        addSubscription(subscription);

    }

    @Override
    public void getTheDaily(String date) {
        Subscription subscription = ApiManage.getInstence().getZhihuApiService().getTheDaily(date)
                .map(new Func1<ZhihuDaily, ZhihuDaily>() {

                    @Override
                    public ZhihuDaily call(ZhihuDaily zhihuDaily) {

                        String date = zhihuDaily.getDate();

                        // 为 ZhihuDaily 中的 ZhihuDailyItem 设置 date
                        for (ZhihuDailyItem zhihuDailyItem:zhihuDaily.getStories()){
                            zhihuDailyItem.setDate(date);
                        }

                        return zhihuDaily;
                    }
                })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 弹出窗口显示错误信息
                        mZhihuFragment.hidProgress();
                        mZhihuFragment.showError(e);
                    }

                    @Override
                    public void onNext(ZhihuDaily zhihuDaily) {
                        mZhihuFragment.hidProgress();//隐藏进度条
                        mZhihuFragment.updateList(zhihuDaily);//更新List

                    }
                });

        addSubscription(subscription);

    }

    @Override
    public void getDailyFromCache() {
        if (mCacheUtil.getAsJSONObject("zhihu") != null){
            ZhihuDaily zhihuDaily = gson.fromJson(mCacheUtil.getAsJSONObject("zhihu").toString(),ZhihuDaily.class);
            mZhihuFragment.updateList(zhihuDaily);
        }

    }

}
