package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sobo on 17/3/19.
 */

public class ZhihuThemeItem {

    // 类型,作用未知
    @SerializedName("type")
    private int type;
    // 文章ID
    @SerializedName("id")
    private String id;
    // 文章标题
    @SerializedName("title")
    private String title;
    // 文章图片
    @SerializedName("images")
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
