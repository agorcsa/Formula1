package com.example.formula1;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonErgastApi {

    // full url
    //http://ergast.com/api/f1/seasons.json
    //relative url
    @GET("seasons")
    Call<SeasonTable> getSeasons();
}
