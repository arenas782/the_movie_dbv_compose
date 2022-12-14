package com.example.themoviedb.data.model.response.tvshows

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow

@Entity
data class TVShow(
    @PrimaryKey(autoGenerate = true)
    val primaryId: Int,

    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "name")
    val name: String? = null,


    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "poster_path")
    val poster_path: String? = null,

    @field:Json(name = "backdrop_path")
    val backdrop_path: String? = null,

    @field:Json(name = "vote_average")
    val vote_average: Float? = null,

    var tv_show_type: String,

    @ColumnInfo(name = "created_at") var createdAt: Long,

    var is_favorite: Int,

    )
