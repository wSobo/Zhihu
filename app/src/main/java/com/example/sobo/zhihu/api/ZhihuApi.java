package com.example.sobo.zhihu.api;


import com.example.sobo.zhihu.bean.LaunchImage;
import com.example.sobo.zhihu.bean.ZhihuDaily;
import com.example.sobo.zhihu.bean.ZhihuStorie;
import com.example.sobo.zhihu.bean.ZhihuTheme;
import com.example.sobo.zhihu.bean.ZhihuThemeMore;
import com.example.sobo.zhihu.bean.ZhihuThemes;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ZhihuApi {

    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getLastDaily();


    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStorie> getZhihuStorie(@Path("id") String id);

    @GET("/api/4/themes")
    Observable<ZhihuThemes> getZhihuThemes();

    @GET("/api/4/theme/{id}")
    Observable<ZhihuTheme> getZhihuTheme(@Path("id") String id);

    @GET("/api/4/theme/{id}/before/{storyId}")
    Observable<ZhihuThemeMore> getZhihuThemeMore(@Path("id") String id, @Path("storyId") String storyId);

    @GET("/api/7/prefetch-launch-images/1080*1920")
    Observable<LaunchImage> getLaunchImage();


}