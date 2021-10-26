package com.dokterkit.LifeCo.model;

import com.google.firebase.Timestamp;

public class Information extends ItemId {
    private String title;
    private Timestamp created;

    public Information() {
        this.title = "";
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
}
