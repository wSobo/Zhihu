package com.example.sobo.zhihu;

import android.app.Application;

/**
 * Created by sobo on 16/11/15.
 */

public class MyApplication extends Application {

    public static MyApplication myApplication;

    //获取到当前应用 Application
    public static Application getContext(){

        return myApplication;

    }

    @Override
    public void onCreate() {

        super.onCreate();
        myApplication = this;

    }
}
