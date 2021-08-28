package com.example.LifeCo.model;

import com.google.firebase.Timestamp;

public class ppt extends pptId{
    private String title;
    private Timestamp created, updated;

    public ppt() {
        this.title = "";
    }
    public ppt(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
