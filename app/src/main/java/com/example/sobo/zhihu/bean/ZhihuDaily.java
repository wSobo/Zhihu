package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ZhihuDaily {
    //日期
    @SerializedName("date")
    private String date;
    //界面顶部 ViewPager 滚动显示的显示内容
    @SerializedName("top_stories")
    private ArrayList<ZhihuTopDailyItem> topStories;
    //当日新闻
    @SerializedName("stories")
    private ArrayList<ZhihuDailyItem> stories;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public ArrayList<ZhihuTopDailyItem> getTopStories() {
        return topStories;
    }

    public void setTopStories(ArrayList<ZhihuTopDailyItem> topStories) {
        this.topStories = topStories;
    }


    public ArrayList<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }
}
