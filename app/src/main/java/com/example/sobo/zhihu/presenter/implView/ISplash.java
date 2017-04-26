package com.example.sobo.zhihu.presenter.implView;


import com.example.sobo.zhihu.bean.LaunchImage;

public interface ISplash {

    // 设置闪屏图片
    void setSplashImage(LaunchImage launchImage);

    // 返回错误
    void onRequestError();

    // 启动结束
    void onRequestEnd();
}
