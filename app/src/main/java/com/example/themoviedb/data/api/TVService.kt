package com.example.themoviedb.data.api

import com.example.themoviedb.data.model.response.tvshows.LatestTVShowsResponse
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.utils.Resource
import retrofit2.http.*


interface   TVService {
    @GET("popular?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun popularTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("on_the_air?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun onTheAirTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("airing_today?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun airingTodayTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("top_rated?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun topRatedTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("{tvShowId}?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun getTVShowDetails(@Path("tvShowId") tvShowId : Int): TVShowDetailsResponse
}
