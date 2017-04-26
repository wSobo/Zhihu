package com.example.sobo.zhihu.presenter;

/**
 * Created by sobo on 16/11/9.
 */

public interface ZhihuPresenter extends BasePresenter {

    //获取最新日报
    void getLastDaily();

    //获取指定日期的日报
    void getTheDaily(String date);

    //从缓存读取日报
    void getDailyFromCache();
}
