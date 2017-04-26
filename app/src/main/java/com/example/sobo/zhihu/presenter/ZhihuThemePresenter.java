package com.example.sobo.zhihu.presenter;

/**
 * Created by sobo on 17/3/19.
 */

public interface ZhihuThemePresenter extends BasePresenter {

    // 获取主题列表
    void getZhihuThemes();

    // 获取指定主题详细内容
    void getZhihuTheme(String id);

    // 获取更多的指定主题详细内容
    void getZhihuThemeMore(String id, String storyId);
}
