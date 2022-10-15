package com.example.themoviedb.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.model.RemoteKeys
import com.example.themoviedb.data.model.response.tvshows.LatestTVShowsResponse
import com.example.themoviedb.data.room.AppDatabase
import com.example.themoviedb.ui.tvShows.enums.Filters
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class MoviesRemoteMediator @Inject constructor(private val api : TVService, private val appDatabase: AppDatabase,
                                               private val filter : String,private val query: String?): RemoteMediator<Int, TVShow>() {


    override suspend fun load(loadType: LoadType, state: PagingState<Int, TVShow>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (appDatabase.tvShowDAO().countByTVShowType(filter) > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        try {
            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }
            val page: Int = key?.nextKey ?: 1
            val apiResponse = getTVShowsFromType(tvShowType = filter, page = page, query = query)
            val moviesList = apiResponse.results



            val endOfPaginationReached =
                apiResponse.results.isEmpty()

            appDatabase.withTransaction {
                val nextKey = page + 1
                appDatabase.remoteKeysDao().insertKey(
                    RemoteKeys(
                        0,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached,
                        tvShowsType = filter
                    )
                )
                appDatabase.tvShowDAO().insertAllTVShows(moviesList,filter)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getTVShowsFromType(tvShowType : String,page : Int,query : String? = null): LatestTVShowsResponse {
        return when (tvShowType) {
            Filters.POPULAR.value -> api.popularTVShows(page)
            Filters.AIRING.value -> api.airingTodayTVShows(page)
            Filters.ON_TV.value -> api.onTheAirTVShows(page)
            Filters.SEARCH.value -> api.searchTVShows(query.toString(),page)
            else -> api.topRatedTVShows(page)
        }
    }
    private suspend fun getKey(): RemoteKeys? {
        return appDatabase.remoteKeysDao().getKeys().firstOrNull()
    }
}