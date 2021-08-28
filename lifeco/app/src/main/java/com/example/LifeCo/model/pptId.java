package com.example.LifeCo.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class pptId {
    @Exclude
    public String pptId;

    public <T extends pptId> T withId(@NonNull final String id) {
        this.pptId = id;
        return (T) this;
    }
}
