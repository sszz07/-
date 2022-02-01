package com.example.walkandeco;


public class a_item {

    private String title;
    private String content;


    private String name;
    private String date;
    String number;
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

    public a_item(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
