package com.example.sobo.zhihu.presenter.implView;

import com.example.sobo.zhihu.bean.ZhihuTheme;
import com.example.sobo.zhihu.bean.ZhihuThemeMore;

/**
 * Created by sobo on 17/3/19.
 */

public interface IOthereThemeFragment extends IBaseFragment{

    // 第一次加载数据时更新界面
    void updateList(ZhihuTheme zhihuTheme);

    // 加载更多数据时更新界面
    void updateMoreList(ZhihuThemeMore zhihuThemeMore);

}
