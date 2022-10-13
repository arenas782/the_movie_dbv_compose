package com.example.themoviedb.data.model.response.tvshows

import com.squareup.moshi.Json

data class LatestTVShowsResponse(
    @field:Json(name = "results")
    val results : ArrayList<TVShow>,

    @field:Json(name = "page")
    val page : Int,
    )

