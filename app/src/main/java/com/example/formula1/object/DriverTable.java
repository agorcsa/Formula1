package com.example.formula1.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverTable {

    @SerializedName("season")
    @Expose
    private int season;

    @SerializedName("driverId")
    @Expose
    private String driverId;

    @SerializedName("Drivers")
    @Expose
    private List<Driver> driverList;

    /*@SerializedName("Drivers")
    @Expose
    private List<Competitor> competitorList;*/

    public DriverTable(int season, List<Driver> driverList) {
        this.season = season;
        this.driverList = driverList;
    }

   /* public List<Competitor> getCompetitorList() {
        return competitorList;
    }*/

    public List<Driver> getDriverList() {
        return driverList;
    }

    public int getSeason() {
        return season;
    }

    public String getDriverId() {
        return driverId;
    }
}
