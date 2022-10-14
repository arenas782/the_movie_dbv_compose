package com.example.themoviedb.data.model.response.tvshows

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow

@Entity
data class TVShowSeason(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int,

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "name")
    val name: String? = null,


    @field:Json(name = "episode_count")
    val episode_count: Int? = null,


    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "poster_path")
    val poster_path: String? = null,






    )
