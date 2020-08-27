package com.example.formula1.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("givenName")
    @Expose
    private String surname;

    @SerializedName("familyName")
    @Expose
    private String name;

    @SerializedName("nationality")
    @Expose
    private String nationality;

    @SerializedName("dateOfBirth")
    @Expose
    private String birthDate;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("permanentNumber")
    @Expose
    private int competitorNumber;

    @SerializedName("url")
    @Expose
    private String driversWiki;

    public String getCode() {
        return code;
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

    public String getBirthDate() {
        return birthDate;
    }

    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public String getDriversWiki() {
        return driversWiki;
    }
}
