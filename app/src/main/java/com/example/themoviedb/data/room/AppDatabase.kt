package com.example.themoviedb.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.model.RemoteKeys

@Database(entities = [TVShow::class, RemoteKeys::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tvShowDAO(): TVShowDAO
    abstract fun remoteKeysDao() : RemoteKeysDao

    companion object {
        const val DATABASE_NAME : String = "tv_shows_db"
    }
}