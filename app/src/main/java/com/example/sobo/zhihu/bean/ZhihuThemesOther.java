package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sobo on 17/3/19.
 */

public class ZhihuThemesOther {

    // 颜色，作用未知
    @SerializedName("color")
    private int color;
    // 供显示的图片地址
    @SerializedName("thumbnail")
    private String thumbnail;
    // 主题日报的介绍
    @SerializedName("description")
    private String description;
    // 主题日报的ID
    @SerializedName("id")
    private int id;
    // 供显示的主题日报名称
    @SerializedName("name")
    private String name;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
