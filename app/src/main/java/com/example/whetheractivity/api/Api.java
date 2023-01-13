package com.example.whetheractivity.api;

import com.example.whetheractivity.model.Weather;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;


public interface Api {
    @GET("/data/2.5/onecall")
    Call<Weather> getWeather(@Query("lat") double lat, @Query("lon") double
                             lon, @Query("appid") String token, @Query("units") String unit);
}