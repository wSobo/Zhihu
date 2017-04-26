package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sobo on 17/3/19.
 */

public class ZhihuTheme {


    // 该主题日报中的文章列表
    @SerializedName("stories")
    private ArrayList<ZhihuThemeItem> stories;
    // 该主题日报的介绍
    @SerializedName("description")
    private String description;
    // 该主题日报的背景图片 大图
    @SerializedName("background")
    private String background;
    // 颜色,作用未知
    @SerializedName("color")
    private int color;
    // 该主题日报的名称
    @SerializedName("name")
    private String name;
    // 背景图片的小图版本
    @SerializedName("image")
    private String image;
    // 背景图片的图片来源
    @SerializedName("image_source")
    private String image_source;
    // 该主题日报的主编
    @SerializedName("editors")
    private ArrayList<Editor> editors;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public ArrayList<ZhihuThemeItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuThemeItem> stories) {
        this.stories = stories;
    }

    public ArrayList<Editor> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<Editor> editors) {
        this.editors = editors;
    }



    public static class Editor {

        // 主编的知乎用户主页
        @SerializedName("url")
        private String url;
        // 主编的个人简介
        @SerializedName("bio")
        private String bio;
        // 主编的ID
        @SerializedName("id")
        private int id;
        // 主编的头像
        @SerializedName("avatar")
        private String avatar;
        // 主编的名字
        @SerializedName("name")
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
