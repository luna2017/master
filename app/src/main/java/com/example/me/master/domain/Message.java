package com.example.me.master.domain;

/**
 * Created by July on 2016/6/16.
 */
public class Message {

    private int img;//goodBarcode
    private String title;//goodName  新闻标题
    private String points;//goodProvider 新闻点击数
    private String time;//新闻发布时间
    private String comments;//新闻评论数



    private String url;//新闻地址

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Message(int img, String title, String points, String time, String comments,String url) {

        this.img = img;

        this.title = title;

        this.points = points;

        this.time = time;

        this.comments = comments;

        this.url = url;

    }

}
