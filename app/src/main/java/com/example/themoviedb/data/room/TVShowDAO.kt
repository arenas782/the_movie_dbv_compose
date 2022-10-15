package com.example.themoviedb.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.themoviedb.data.model.response.tvshows.TVShow
import kotlinx.coroutines.flow.Flow


@Dao
interface TVShowDAO {
    @Query("SELECT * FROM tvshow where tv_show_type= :tvShowType order by created_at asc")
    fun getTVShowsPerType(tvShowType : String): PagingSource<Int, TVShow>

    @Query("SELECT * FROM tvshow where tv_show_type= 'Search' and name like '%'||:query||'%'  order by created_at asc")
    fun getTVShowsPerQuery(query : String): PagingSource<Int, TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShow(TVShow : TVShow?)

    @Query("DELETE FROM tvshow WHERE id = :id")
    suspend fun deleteTVShowById(id: Int)


    @Query("DELETE FROM tvshow WHERE tv_show_type = 'Search'")
    suspend fun deleteSearches()

    @Query("UPDATE tvshow set is_favorite = :isFavorite where id = :tvShowId" )
    suspend fun updateFavorite(tvShowId : Int, isFavorite : Int)

    @Update
    fun updateTVShow(tvShow : TVShow) // no need of suspend



    @Query("SELECT * from tvshow where is_favorite = :is_favorite" )
    fun getFavorites(is_favorite: Int) : Flow<List<TVShow>>

    @Query("SELECT * from tvshow where id = :id" )
    fun getCurrentTVShow(id : Int) : Flow<TVShow>



    suspend fun insertAllTVShows(TVShows: List<TVShow?>?, tvShowType: String){

        TVShows?.forEach {
            insertTVShow(it.apply {
                this!!.tv_show_type = tvShowType
                this.createdAt = System.currentTimeMillis()
            })
        }
    }



    @Query("SELECT COUNT(id) from tvshow where tv_show_type= :tvShowType")
    suspend fun countByTVShowType(tvShowType : String): Int

}