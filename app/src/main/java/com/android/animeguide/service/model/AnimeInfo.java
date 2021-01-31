package com.android.animeguide.service.model;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;

import java.io.Serializable;

public class AnimeInfo extends BaseObservable implements Serializable
{
    private static final long serialVersionUID = 1L;
    public int id;
    public String title;
    public String public_url;
    public String twitter_account;
    public Drawable drawable;

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
}
