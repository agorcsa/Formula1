package com.example.formula1.object;

import com.example.formula1.object.Season;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonTable {

    @SerializedName("Seasons")
    private List<Season> seasonList;

    public List<Season> getSeasonList() {
        return seasonList;
    }
}
