package com.example.themoviedb.ui.tvShows.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.*
import com.example.themoviedb.data.api.TVService
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.data.repository.TVShowsRepository
import com.example.themoviedb.ui.tvShows.Filters
import com.example.themoviedb.ui.tvShows.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(private val repository : TVShowsRepository,private val service : TVService) : ViewModel() {


    val selectedFilter : MutableState<String> = mutableStateOf(Filters.TOP_RATED.value)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _searchWidgetState : MutableState<SearchWidgetState> = mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState : State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState = MutableStateFlow("")
    val searchTextState  = _searchTextState.asStateFlow()

    fun updateSearchWidgetState(newValue : SearchWidgetState){
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState (newValue: String){
        _searchTextState.value = newValue
    }



    private val _tvShows = MutableStateFlow(emptyFlow<PagingData<TVShow>>())
    val tvshows: StateFlow<Flow<PagingData<TVShow>>> = _tvShows.asStateFlow()



    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    val tvShowDetails: MutableLiveData<TVShowDetailsResponse?> by lazy {
        MutableLiveData<TVShowDetailsResponse?>()
    }



    fun updateFilter(filter : String) = effect {
            selectedFilter.value = filter
    }



    fun getTVShowDetails(tvShowId : String) = effect{
        repository.getTVShowDetails(tvShowId.toInt()).collect{
            response -> tvShowDetails.postValue(response)
        }
    }


    fun deleteSearchResults()= effect{
        repository.deleteSearchResults()
    }
    fun newSearch()= effect {
        repository.deleteSearchResults()
        _isLoading.value = true
        _tvShows.value = repository.getTVShowsPerFilter(selectedFilter.value).cachedIn(GlobalScope)
        _isLoading.value = false
    }



    private fun performSearching(query : String) : Flow<PagingData<TVShow>> {
        Log.e("TAG","Performing search")
        viewModelScope.launch (Dispatchers.IO){
            if(query != "")
                _tvShows.value = repository.getTVShowsPerQuery(query)
            else
                _tvShows.value =  repository.getTVShowsPerFilter(selectedFilter.value).cachedIn(GlobalScope)        // 3
        }
        return _tvShows.value
    }
    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }    // 4
    }
    fun load() = effect {
        _isLoading.value = true
        _tvShows.value =  repository.getTVShowsPerFilter(selectedFilter.value).cachedIn(GlobalScope)        // 3
        _isLoading.value = false
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun search() = effect {

        _searchTextState.debounce (1000)
            .distinctUntilChanged()
            .flatMapLatest {
                performSearching(it)
            }
            .launchIn(viewModelScope)
    }





//    fun getMovieDetails(movieId : Int) = liveData(Dispatchers.Main) {
//            emit(Resource.loading(data = null))
//            try {
//                val movieDetails = repository.getMovieDetails(tvShowId = movieId)
//                Log.e("movie",movieDetails.toString())
//                emit(Resource.success(data = movieDetails))
//            } catch (exception: Exception) {
//                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//            }
//    }


    sealed class TVShowDetailState {
        object Empty : TVShowDetailState()
        object Loading : TVShowDetailState()
        class Loaded(val data: TVShowDetailsResponse) : TVShowDetailState()
        class Error(val message: String) : TVShowDetailState()
    }
}