package com.android.animeguide.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AppEntity {
    @PrimaryKey
    public long id;

    public int period;

    public int year;

    public String title;

    public String public_url;

    public String Twitter_url;
}
