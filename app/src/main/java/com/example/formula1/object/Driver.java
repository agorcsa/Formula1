package com.example.formula1.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

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
    @SerializedName("dateOfBirth")
    @Expose
    private String birthDate;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("permanentNumber")
    @Expose
    private int startNumber;
    @SerializedName("url")
    @Expose
    private String driversWiki;

    public Driver(String driverId, String surname, String name, String nationality, String birthDate, String code, int competitorNumber, String driversWiki) {
        this.driverId = driverId;
        this.surname = surname;
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.code = code;
        this.startNumber = competitorNumber;
        this.driversWiki = driversWiki;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

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

    public int getStartNumber() {
        return startNumber;
    }

    public String getDriversWiki() {
        return driversWiki;
    }
}
