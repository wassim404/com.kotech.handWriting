package com.kotech.njoscribe.utils;

public class Textnote {

    int id;
    String name;
    String title;
    String date;
    String body;

    public Textnote(int id, String name, String title, String date, String body) {
        super();
        this.id = id;
        this.name = name;
        this.title = title;
        this.date = date;
        this.body = body;
    }

    public Textnote() {
        super();
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
