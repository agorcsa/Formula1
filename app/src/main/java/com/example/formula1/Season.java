package com.example.formula1;

import com.google.gson.annotations.SerializedName;


public class Season {

    @SerializedName("season")
    private int year;

    @SerializedName("url")
    private String wikiUrl;

    public Season(int year, String wikiUrl) {
        this.year = year;
        this.wikiUrl = wikiUrl;
    }

    public int getYear() {
        return year;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }
}
