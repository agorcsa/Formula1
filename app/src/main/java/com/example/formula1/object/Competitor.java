package com.example.formula1.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competitor {

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
    @SerializedName("code")
    @Expose
    private String abbreviation;
    @SerializedName("url")
    @Expose
    private String wikiUrl;

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
