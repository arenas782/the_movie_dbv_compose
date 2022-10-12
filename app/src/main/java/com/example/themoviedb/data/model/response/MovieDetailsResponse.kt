package com.example.themoviedb.data.model.response

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.themoviedb.data.model.Genre
import com.example.themoviedb.data.model.ProductionCountry
import com.squareup.moshi.Json

data class MovieDetailsResponse(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "title")
    val title: String? = null,

    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "poster_path")
    val poster_path: String? = null,

    @field:Json(name = "status")
    val status: String? = null,

    @field:Json(name = "production_countries")
    val production_countries: List<ProductionCountry>? = null,

    @field:Json(name = "genres")
    val genres: List<Genre>? = null,

    @field:Json(name = "release_date")
    val release_date: String? = null,

    @ColumnInfo(name = "created_at") var createdAt: Long,

    )
