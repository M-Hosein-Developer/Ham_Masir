package ir.androidcoder.data.remote

import ir.androidcoder.data.model.RouteResponse
import ir.androidcoder.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("route")
    suspend fun getRoute(
        @Query("type") type: String = "json",
        @Query("vehicle") vehicle: String = "car",
        @Query("locale") locale: String = "en",
        @Query("key") key: String,
        @Query("point") point1: String,
        @Query("point") point2: String
    ): RouteResponse

    @GET("geocode")
    suspend fun getGeocode(
        @Query("q") query: String,
        @Query("locale") locale: String,
        @Query("key") apiKey: String
    ): SearchResponse

}