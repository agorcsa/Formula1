package com.example.formula1.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonErgastApi {
    // full url
    //http://ergast.com/api/f1/seasons.json
    //relative url
    @GET("seasons.json")
    Call<ApiResponse> getSeasons();
}
