package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;


public class ZhihuDailyItem{

    //图片地址
    @SerializedName("images")
    private String[] images;
    //作用未知,通常为0
    @SerializedName("type")
    private int type;
    //内容id
    @SerializedName("id")
    private String id;
    //标题
    @SerializedName("title")
    private String title;

    private String date;

    public boolean hasFadedIn = false;


    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }


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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
