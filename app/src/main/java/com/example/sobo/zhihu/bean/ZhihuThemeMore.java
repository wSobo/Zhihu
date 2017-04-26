package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sobo on 17/3/19.
 */

public class ZhihuThemeMore {

    @SerializedName("stories")
    private ArrayList<ZhihuThemeItem> stories;

    public ArrayList<ZhihuThemeItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuThemeItem> stories) {
        this.stories = stories;
    }
}
