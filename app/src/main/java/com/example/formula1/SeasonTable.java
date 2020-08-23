package com.example.formula1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonTable {

    @SerializedName("Seasons")
    private List<Season> seasonList;

    public List<Season> getSeasonList() {
        return seasonList;
    }
}
