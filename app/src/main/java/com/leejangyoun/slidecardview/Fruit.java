package com.leejangyoun.slidecardview;

public class Fruit {

    private int no;
    private String title;
    private String thumb;
    private String desc;

    public Fruit(int no, String title, String thumb, String desc) {
        this.no    = no;
        this.title = title;
        this.thumb = thumb;
        this.desc  = desc;
    }

    public int getNo() {
        return no;
    }

    public String getTitle() {
        return title;
    }

    public String getThumb() {
        return thumb;
    }

    public String getDesc() {
        return desc;
    }
}