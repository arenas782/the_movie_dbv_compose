package com.example.themoviedb.data.model.response

import com.example.themoviedb.data.model.TVShow
import com.squareup.moshi.Json

data class LatestTVShowsResponse(
    @field:Json(name = "results")
    val results : ArrayList<TVShow>,

    @field:Json(name = "page")
    val page : Int,
    )

