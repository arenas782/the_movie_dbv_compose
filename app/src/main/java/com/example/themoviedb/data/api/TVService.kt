package com.example.themoviedb.data.api

import com.example.themoviedb.data.model.response.tvshows.LatestTVShowsResponse
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.utils.Resource
import retrofit2.http.*


interface   TVService {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTczYjI5NWQ5OGQzNTBlNWMwZWNkOTIyOWFmMjg3ZSIsInN1YiI6IjVmOTM4YmE1MmI5MzIwM2YyZjc0YzBmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SbV9Zq6HY8BlaFNmbt8WqD-Q1xo_FjtiV9VgSF_t6Wc")
    @GET("tv/popular?language=en-US")
    suspend fun popularTVShows(@Query("page") page : Int): LatestTVShowsResponse
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTczYjI5NWQ5OGQzNTBlNWMwZWNkOTIyOWFmMjg3ZSIsInN1YiI6IjVmOTM4YmE1MmI5MzIwM2YyZjc0YzBmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SbV9Zq6HY8BlaFNmbt8WqD-Q1xo_FjtiV9VgSF_t6Wc")
    @GET("tv/on_the_air?language=en-US")
    suspend fun onTheAirTVShows(@Query("page") page : Int): LatestTVShowsResponse
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTczYjI5NWQ5OGQzNTBlNWMwZWNkOTIyOWFmMjg3ZSIsInN1YiI6IjVmOTM4YmE1MmI5MzIwM2YyZjc0YzBmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SbV9Zq6HY8BlaFNmbt8WqD-Q1xo_FjtiV9VgSF_t6Wc")
    @GET("tv/airing_today?language=en-US")
    suspend fun airingTodayTVShows(@Query("page") page : Int): LatestTVShowsResponse
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTczYjI5NWQ5OGQzNTBlNWMwZWNkOTIyOWFmMjg3ZSIsInN1YiI6IjVmOTM4YmE1MmI5MzIwM2YyZjc0YzBmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SbV9Zq6HY8BlaFNmbt8WqD-Q1xo_FjtiV9VgSF_t6Wc")
    @GET("tv/top_rated?language=en-US")

    suspend fun topRatedTVShows(@Query("page") page : Int): LatestTVShowsResponse

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTczYjI5NWQ5OGQzNTBlNWMwZWNkOTIyOWFmMjg3ZSIsInN1YiI6IjVmOTM4YmE1MmI5MzIwM2YyZjc0YzBmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SbV9Zq6HY8BlaFNmbt8WqD-Q1xo_FjtiV9VgSF_t6Wc")
    @GET("tv/{tvShowId}?language=en-US")
    suspend fun getTVShowDetails(@Path("tvShowId") tvShowId : Int): TVShowDetailsResponse

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTczYjI5NWQ5OGQzNTBlNWMwZWNkOTIyOWFmMjg3ZSIsInN1YiI6IjVmOTM4YmE1MmI5MzIwM2YyZjc0YzBmMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.SbV9Zq6HY8BlaFNmbt8WqD-Q1xo_FjtiV9VgSF_t6Wc")
    @GET("search/tv?language=en-US&page=1&include_adult=false")
    suspend fun searchTVShows(@Query("query") query : String,@Query("page") page : Int): LatestTVShowsResponse

}
