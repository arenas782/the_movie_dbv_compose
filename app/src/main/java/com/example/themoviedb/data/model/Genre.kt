package com.example.themoviedb.data.model

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Genre(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?
)