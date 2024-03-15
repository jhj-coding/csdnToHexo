package com.github.csccoder.csdn2md.model;

import java.util.Date;

public class Article {
    private int id;
    private String title;
    private String content;
    private String author;
    private String tags[];
    private String catagory[];
    private Date date;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory[]) {
        this.catagory = catagory;
    }
}
