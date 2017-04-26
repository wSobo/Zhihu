package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;


public class ZhihuTopDailyItem{

    //图片地址
    @SerializedName("image")
    private String image;
    //作用未知,通常为0
    @SerializedName("type")
    private int type;
    //内容id
    @SerializedName("id")
    private String id;
    //标题
    @SerializedName("title")
    private String title;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

}
