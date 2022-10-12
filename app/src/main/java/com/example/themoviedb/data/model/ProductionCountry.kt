package com.example.themoviedb.data.model

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class ProductionCountry(
    @field:Json(name = "name")
    val name: String?,

    @field:Json(name = "id")
    val id: Int?,
)