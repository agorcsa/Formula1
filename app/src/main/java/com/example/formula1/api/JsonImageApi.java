package com.example.formula1.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonImageApi {

    // full url from Google Search API
    // [API_KEY] = AIzaSyADx9HTfg1vEtKt2KllxBhwpjB5qUvO52k
    // [ENGINE_KEY] = 000213537299717655806: fsqehiydnxg
    // [SEARCH_STRING] = <arbitrarily defined in your application> = full driver's name
    // https://www.googleapis.com/customsearch/v1?key=AIzaSyADx9HTfg1vEtKt2KllxBhwpjB5qUvO52k&cx=000213537299717655806:fsqehiydnxg&q=schumacher


    @GET("{name.json")
    Call<ImageApiResponse> getItems(
            @Query("key") String key,
            @Query("cx") String cx,
            @Query("q") String q
    );
}
