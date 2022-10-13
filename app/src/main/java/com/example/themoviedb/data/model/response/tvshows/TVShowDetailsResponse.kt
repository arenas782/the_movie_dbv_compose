package com.example.themoviedb.data.model.response.tvshows

import com.squareup.moshi.Json

data class TVShowDetailsResponse(
    @field:Json(name = "name")
    val name : String,

    @field:Json(name = "backdrop_path")
    val backdrop_path : String,

    @field:Json(name = "vote_average")
    val vote_average : Float,

    @field:Json(name = "overview")
    val overview : String,

    @field:Json(name = "seasons")
    val seasons : ArrayList<TVShowSeason>,
)

