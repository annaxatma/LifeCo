package com.dokterkit.LifeCo.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

@Keep
public class ItemId {
    @Exclude
    public String itemId;

    public <T extends ItemId> T withId(@NonNull final String id) {
        this.itemId = id;
        return (T) this;
    }
}
