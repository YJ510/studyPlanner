package com.yj510.studyplanner;

public class TodoItemInfo {
    int id;
    String title;
    String date;
    String _class;
    String content;
    int state;

    public TodoItemInfo() {

    }

    public TodoItemInfo(int id, String title, String date, String _class, String content, int state) {
        this.id = id;
        this.title = title;
        this.date = date;
        this._class = _class;
        this.content = content;
        this.state = state;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
