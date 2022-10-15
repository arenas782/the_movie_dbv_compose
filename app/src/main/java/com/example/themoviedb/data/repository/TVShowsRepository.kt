package com.example.themoviedb.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.data.remotemediator.MoviesRemoteMediator
import com.example.themoviedb.data.room.AppDatabase
import com.example.themoviedb.ui.tvShows.enums.Filters
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class TVShowsRepository @Inject constructor(private val db: AppDatabase, private val TVService: TVService) {

    suspend fun getTVShowDetails(tvShowId: Int): Flow<TVShowDetailsResponse> {
        return flow {
            emit(TVService.getTVShowDetails(tvShowId))
        }
    }

    suspend fun updateTVShowFavorite(tvShowId: Int) {
        try {
            Log.e("TAG", "Updating favorite $tvShowId")
            db.tvShowDAO().getCurrentTVShow(tvShowId).take(1).collectLatest { actualTVShow ->
                db.tvShowDAO().updateFavorite(tvShowId, if (actualTVShow.is_favorite == 1) 0 else 1)
                Log.e("TAG", "TV Show $actualTVShow")
            }
        } catch (e: Exception) {
            Log.e("TAG", "$e.localizedMessage")
        }
    }


    fun getTVShowsPerQuery(query: String): Flow<PagingData<TVShow>> {
        val pagingSourceFactory = {
            db.tvShowDAO().getTVShowsPerQuery(query)
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                jumpThreshold = 20,
                initialLoadSize = 40
            ),
            remoteMediator = MoviesRemoteMediator(
                TVService,
                db,
                Filters.SEARCH.value,
                query = query
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    fun getTVShowsPerFilter(tvShowFilter: String): Flow<PagingData<TVShow>> {
        val pagingSourceFactory = {
            db.tvShowDAO().getTVShowsPerType(tvShowFilter)
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                jumpThreshold = 20,
                initialLoadSize = 40
            ),
            remoteMediator = MoviesRemoteMediator(
                TVService,
                db,
                tvShowFilter, null
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun deleteSearchResults() {
        try {
            db.tvShowDAO().deleteSearches()
        } catch (e: Exception) {
            Log.e("TAG", "$e.localizedMessage")
        }
    }

    suspend fun clearDB() {
        try {
            db.tvShowDAO().clearDB()
        } catch (e: Exception) {
            Log.e("TAG", "$e.localizedMessage")
        }
    }
    fun getCurrentTVShow(tvShowId : Int) : Flow<TVShow> {
        return db.tvShowDAO().getCurrentTVShow(tvShowId)
    }

    fun getFavoriteTVShows() : Flow<List<TVShow>> {
        return db.tvShowDAO().getFavorites(1)
    }
}