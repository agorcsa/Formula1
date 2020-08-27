package com.example.formula1.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonCompetitorApi {
    // full url example
    // http://ergast.com/api/f1/drivers/ricciardo.json  
    // relative url
    // driverId, example: ricciardo.json
    @GET("{competitor}.json")
    Call<ApiResponse> getCompetitorDetails(
            @Path("competitor") String competitor
    );
}
