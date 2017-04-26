package com.example.sobo.zhihu.presenter.implView;

/**
 * Created by sobo on 16/11/9.
 */

public interface IBaseFragment {

    //显示加载进度条
    void showProgress();
    //隐藏加载进度条
    void hidProgress();
    //显示错误
    void showError(Throwable e);
}
