package com.example.sobo.zhihu.presenter.implView;

import com.example.sobo.zhihu.bean.ZhihuDaily;

/**
 * Created by sobo on 16/11/9.
 */

public interface IZhihuFragment extends IBaseFragment{
    //传入数据更新界面
    void updateList(ZhihuDaily zhihuDaily);
}
