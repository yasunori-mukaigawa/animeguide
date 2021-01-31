package com.android.animeguide.service.model;

import androidx.databinding.BaseObservable;

public class SelectableYear extends BaseObservable {
    private String year;
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public SelectableYear(String year){
        this.year = year;
    }
}