package com.example.themoviedb.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedb.data.model.TVShow


@Dao
interface TVShowDAO {
    @Query("SELECT * FROM tvshow where tv_show_type= :tvShowType")
    fun getTVShowsPerType(tvShowType : String): PagingSource<Int,TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShow(TVShow : TVShow?)

    @Query("DELETE FROM tvshow WHERE id = :id")
    suspend fun deleteTVShowById(id: Int)

    suspend fun insertAllTVShows(TVShows: List<TVShow?>?,tvShowType: String){

        TVShows?.forEach {
            insertTVShow(it.apply {
                this!!.tv_show_type = tvShowType
                this!!.createdAt = System.currentTimeMillis()
            })
        }
    }

    @Query("SELECT COUNT(id) from tvshow")
    suspend fun count(): Int
}