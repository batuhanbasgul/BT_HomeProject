package com.example.bt_homeproject.db.service.api

import com.example.bt_homeproject.db.data_model.forecast.ForecastData
import com.example.bt_homeproject.db.data_model.geo_position.GeoPositionData
import com.example.bt_homeproject.utils.Constants.forecastForFiveDays
import com.example.bt_homeproject.utils.Constants.geoPositionUrl
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(geoPositionUrl)   //q -> lat,long
    suspend fun getGeo(
        @Query("q") q:String,
        @Query("apikey") apiKey:String,
        @Query("language") language:String,
        @Query("toplevel") toplevel:String,
        @Query("details") details:String
    ): GeoPositionData


    @GET("$forecastForFiveDays{locationKey}")
    suspend fun getForecast(
        @Path("locationKey") locationKey:String,
        @Query("apikey") apiKey:String,
        @Query("language") language:String,
        @Query("toplevel") toplevel:String,
        @Query("details") details:String
    ): ForecastData
}