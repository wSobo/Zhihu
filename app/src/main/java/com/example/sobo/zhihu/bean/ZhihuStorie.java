package com.example.sobo.zhihu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sobo on 16/11/15.
 */

public class ZhihuStorie {

    // HTML 格式的新闻
    @SerializedName("body")
    private String body;
    // 新闻标题
    @SerializedName("title")
    private String title;
    // 图片
    @SerializedName("image")
    private String image;
    // 供在线查看内容与分享至 SNS 用的 URL
    @SerializedName("share_url")
    private String mShareUrl;
    // 供手机端的 WebView(UIWebView) 使用
    @SerializedName("css")
    private String[] css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.mShareUrl = shareUrl;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
