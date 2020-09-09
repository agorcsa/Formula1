package com.example.formula1.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competitor {

    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("givenName")
    @Expose
    private String surname;
    @SerializedName("familyName")
    @Expose
    private String name;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("permanentNumber")
    @Expose
    private int startNumber;

    public Competitor(String driverId, String surname, String name, String nationality, int startNumber, String abbreviation, String wikiUrl) {
        this.driverId = driverId;
        this.surname = surname;
        this.name = name;
        this.nationality = nationality;
        this.startNumber = startNumber;
        this.abbreviation = abbreviation;
        this.wikiUrl = wikiUrl;
    }

    @SerializedName("code")
    @Expose
    private String abbreviation;
    @SerializedName("url")
    @Expose
    private String wikiUrl;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }
}
