package com.example.themoviedb.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.model.response.tvshows.LatestTVShowsResponse
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.data.remotemediator.MoviesRemoteMediator
import com.example.themoviedb.data.room.AppDatabase
import com.example.themoviedb.ui.tvShows.Filters
import com.example.themoviedb.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class TVShowsRepository @Inject constructor(private val db: AppDatabase, private val TVService: TVService) {

    suspend fun getTVShowDetails(tvShowId : Int): Flow<TVShowDetailsResponse>{
        return flow {
            emit(TVService.getTVShowDetails(tvShowId))
        }
    }


    fun getTVShowsPerQuery(query : String):Flow<PagingData<TVShow>>{
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
                query = query),
            pagingSourceFactory = pagingSourceFactory).flow
    }


    fun getTVShowsPerFilter(tvShowFilter : String):Flow<PagingData<TVShow>>{
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
               tvShowFilter,null),
            pagingSourceFactory = pagingSourceFactory).flow
    }

    suspend fun deleteMovieById(id : Int) {
        try {
            db.tvShowDAO().deleteTVShowById(id)
        }catch (e : Exception){
            Log.e("TAG", "$e.localizedMessage")
        }
    }
}