package com.example.formula1.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonDriverApi {
    // full url
    // http://ergast.com/api/f1/2019/drivers.json 
    // relative url
    // drivers.json
    @GET("drivers.json")
    Call<ApiResponse> getDrivers();
}
