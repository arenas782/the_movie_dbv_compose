package com.example.themoviedb.data.api

import com.example.themoviedb.data.model.response.tvshows.LatestTVShowsResponse
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.utils.Resource
import retrofit2.http.*


interface   TVService {
    @GET("tv/popular?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun popularTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("tv/on_the_air?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun onTheAirTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("tv/airing_today?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun airingTodayTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("tv/top_rated?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun topRatedTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @GET("tv/{tvShowId}?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US")
    suspend fun getTVShowDetails(@Path("tvShowId") tvShowId : Int): TVShowDetailsResponse

    @GET("search/tv?api_key=f52f4a0bd6f7db6d9bab750909ed1736&language=en-US&page=1&include_adult=false")
    suspend fun searchTVShows(@Query("query") query : String,@Query("page") page : Int): LatestTVShowsResponse

}
