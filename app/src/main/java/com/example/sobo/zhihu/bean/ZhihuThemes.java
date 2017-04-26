package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sobo on 17/3/19.
 */

public class ZhihuThemes {

    // 返回数目之限制（仅为猜测）
    @SerializedName("limit")
    private int limit;
    // 已订阅条目
    @SerializedName("subscribed")
    private ArrayList<ZhihuThemesOther> subscribed;
    // 其他条目
    @SerializedName("others")
    private ArrayList<ZhihuThemesOther> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public ArrayList<ZhihuThemesOther> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(ArrayList<ZhihuThemesOther> subscribed) {
        this.subscribed = subscribed;
    }

    public ArrayList<ZhihuThemesOther> getOthers() {
        return others;
    }

    public void setOthers(ArrayList<ZhihuThemesOther> others) {
        this.others = others;
    }


}
