package com.example.themoviedb.ui.tvShows

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.databinding.FragmentTvShowsBinding
import com.example.themoviedb.ui.base.BaseFragment
import com.example.themoviedb.ui.composables.*
import com.example.themoviedb.ui.theme.AppTheme
import com.example.themoviedb.ui.tvShows.enums.SearchWidgetState
import com.example.themoviedb.ui.tvShows.viewmodel.TVShowsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class TVShowsFragment  : BaseFragment(){

    private val viewModel : TVShowsViewModel by viewModels()
    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.load()
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    TVShowsMain()
                }
            }
        }
        return view
    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun TVShowsMain(){
        val searchWidgetState by viewModel.searchWidgetState
        val searchTextState = viewModel.searchTextState.collectAsState()
        Scaffold(
            topBar = { TVShowsAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState.value,
                onTextChanged = {
                                viewModel.deleteSearchResults()
                                viewModel.updateSearchTextState(it)
                                viewModel.search()
                },
                onCloseClicked = {
                                viewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                },
                viewModel = viewModel,
                onProfileButtonClicked = {
                    findNavController().navigate(TVShowsFragmentDirections.actionMoviesFragmentToProfileFragment())
                }
            )},
            content = {
                MainComponent()
            }
        )
    }


    @Composable
    fun GetTVShows( viewModel:TVShowsViewModel) {
        val tvShows by viewModel.tvshows.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val isRefreshing = viewModel.isRefreshing.collectAsState().value
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

        when {
            isLoading -> {
                LoadingIndicator()
            }
            else ->{
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = viewModel::newSearch,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            vertical = 8.dp,
                            horizontal = 0.dp
                        )
                ){
                    tvShowsList( items = tvShows)
                }
            }
        }
    }


    @Composable
    fun tvShowsList(
        items: Flow<PagingData<TVShow>>) {
        val tvShowsList: LazyPagingItems<TVShow> = items.collectAsLazyPagingItems()
        val listState: LazyGridState = rememberLazyGridState()

        LazyVerticalGrid( columns = GridCells.Fixed(2), state = listState ) {
                items(tvShowsList.itemCount) { item ->
                    item.let { index ->
                        tvShowsList[index]?.let {
                            TVShowItem(tvShowData = it, onClick = {
                                viewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                                viewModel.updateSearchTextState("")
                                findNavController().navigate(TVShowsFragmentDirections.actionMainFragmentToDetailsFragment(it.id.toString()))
                            })
                        }
                    }
                }
                tvShowsList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            //You can add modifier to manage load state when first time response page is loading
                      //      Log.e("TAG","loading for the first time")
                        }
                        loadState.append is LoadState.Loading -> {
                        //    Log.e("TAG","loading next page")
                            //You can add modifier to manage load state when next response page is loading
                        }
                        loadState.append is LoadState.Error -> {
                          //  Log.e("TAG","error loading")
                            //You can use modifier to show error message
                        }
                        loadState.mediator?.refresh is LoadState.Error -> {
                            //Log.e("No internet connection","here")
                        }
                    }
                }
            }
    }

    @Composable
    fun MainComponent(){
        Column {
            Filters()
            GetTVShows(viewModel = viewModel)
        }
    }

    @Composable
    fun Filters(){
        val selectedFilter = viewModel.selectedFilter.value
        LazyRow(modifier = Modifier.padding(top = 10.dp, start = 8.dp, bottom = 8.dp)){
            items(com.example.themoviedb.ui.tvShows.enums.Filters.values()){ filter ->
                if (filter.value != com.example.themoviedb.ui.tvShows.enums.Filters.SEARCH.value){
                    ChipFilter(
                        filterName = filter.value,
                        isSelected = selectedFilter == filter.value,
                        onSelectedCategoryChanged ={
                            viewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                            viewModel.updateSearchTextState("")
                            viewModel.updateFilter(filter.value)
                        },
                        onExecuteSearch = viewModel::newSearch
                    )
                }
            }
        }
    }
}