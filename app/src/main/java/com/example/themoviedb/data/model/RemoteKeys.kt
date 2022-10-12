package com.example.themoviedb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviedb.ui.tvShows.Filters


@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nextKey: Int?,
    val isEndReached: Boolean,
    val tvShowsType : String
)