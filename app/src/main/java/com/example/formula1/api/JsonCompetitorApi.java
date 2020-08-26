package com.example.formula1.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonCompetitorApi {
    // full url example
    // http://ergast.com/api/f1/drivers/ricciardo.json  
    // relative url
    // driverId, example: ricciardo.json
    @GET("ricciardo.json")
    Call<ApiResponse> getCompetitorDetails();
}
