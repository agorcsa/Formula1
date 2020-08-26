package com.example.formula1.api;

import com.example.formula1.object.MRData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("MRData")
    @Expose
    private MRData mRData;

    public MRData getMRData() {
        return mRData;
    }

    public void setMRData(MRData mRData) {
        this.mRData = mRData;
    }
}
