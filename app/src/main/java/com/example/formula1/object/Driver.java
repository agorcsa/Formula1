package com.example.formula1.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver implements Parcelable {

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

    protected Driver(Parcel in) {
        driverId = in.readString();
        surname = in.readString();
        name = in.readString();
        nationality = in.readString();
        birthDate = in.readString();
        code = in.readString();
        startNumber = in.readInt();
        driversWiki = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(driverId);
        dest.writeString(surname);
        dest.writeString(name);
        dest.writeString(nationality);
        dest.writeString(birthDate);
        dest.writeString(code);
        dest.writeInt(startNumber);
        dest.writeString(driversWiki);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Driver> CREATOR = new Parcelable.Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };
}