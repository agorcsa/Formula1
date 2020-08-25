package com.example.formula1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverTable {

    @SerializedName("season")
    @Expose
    private int season;

    @SerializedName("Drivers")
    private List<Driver> driverList;

    public List<Driver> getDriverList() {
        return driverList;
    }

    public int getSeason() {
        return season;
    }

    public DriverTable(int season, List<Driver> driverList) {
        this.season = season;
        this.driverList = driverList;
    }
}
