package com.example.themoviedb.ui.tvShows

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
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
import com.example.themoviedb.R
import com.example.themoviedb.data.model.response.tvshows.TVShow
import com.example.themoviedb.databinding.FragmentTvShowsBinding
import com.example.themoviedb.ui.base.BaseFragment
import com.example.themoviedb.ui.composables.ChipFilter
import com.example.themoviedb.ui.composables.LoadingIndicator
import com.example.themoviedb.ui.composables.SearchAppBar
import com.example.themoviedb.ui.composables.TVShowItem
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
                tvShowsMain()
            }
        }
        return view
    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun tvShowsMain(){
        val searchWidgetState by viewModel.searchWidgetState
        val searchTextState = viewModel.searchTextState.collectAsState()
        Scaffold(
            topBar = { TVShowsAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState.value,
                onTextChanged = {
                                viewModel.updateSearchTextState(it)
                                viewModel.search()
                },
                onCloseClicked = {
                                 //viewModel.updateSearchTextState("")
                                   // viewModel.load()
                                viewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
                },
            )},
            content = {
                MainComponent()
            }
        )
    }

    @Composable
    fun TVShowsAppBar(
        searchWidgetState: SearchWidgetState,
        searchTextState : String,
        onTextChanged : (String) -> Unit,
        onCloseClicked : () -> Unit,
    ){
        when (searchWidgetState){
            SearchWidgetState.CLOSED -> {
                TopAppBar()
            }
            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChanged = onTextChanged,
                    onCloseClicked = onCloseClicked,
                    onSearchClicked = {}
                )

            }
        }
    }

    @Composable
    private fun TopAppBar() {
        TopAppBar(
            title = { Text(text = "TV Shows") },
            actions = {
                IconButton(onClick = {
                    viewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Buscar")
                }
            }
        )
    }



    @Composable
    fun getTVShows(modifier: Modifier = Modifier, viewModel:TVShowsViewModel, context: Context) {
        val tvShows by viewModel.tvshows.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val isRefreshing = viewModel.isRefreshing.collectAsState().value
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

        when {
            isLoading -> {
                Log.e("TAG","cargando")
                LoadingIndicator()
            }
            else ->{
                Log.e("TAG","mostrando")
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
                    tvShowsList(modifier, items = tvShows, context)
                }
            }
        }
    }


    @Composable
    fun tvShowsList(
        modifier: Modifier,
        items: Flow<PagingData<TVShow>>,
        context: Context) {
        val tvShowsList: LazyPagingItems<TVShow> = items.collectAsLazyPagingItems()
        val listState: LazyGridState = rememberLazyGridState()

        val searchTextState = viewModel.searchTextState.collectAsState()

        Log.e("Buscando...",searchTextState.value)
        Log.e("Items",tvShowsList.itemSnapshotList.toString())

        LazyVerticalGrid( columns = GridCells.Adaptive(minSize = 140.dp), state = listState ) {
                items(tvShowsList.itemCount) { item ->
                    item.let { index ->
                        tvShowsList[index]?.let {
                            TVShowItem(tvShowData = it, onClick = {
                                findNavController().navigate(TVShowsFragmentDirections.actionMainFragmentToDetailsFragment(it.id.toString()))
                            })
                        }
                    }
                }
                tvShowsList.apply {
                    //Log.e("Mediator state","${loadState.mediator}")
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
            filters()

            getTVShows(viewModel = viewModel, context = requireContext())
        }
    }



    @Composable
    fun filters(){
        val selectedFilter = viewModel.selectedFilter.value
        LazyRow(modifier = Modifier.padding(top = 10.dp, start = 8.dp, bottom = 8.dp)){
            items(Filters.values()){ filter ->
                if (filter.value != Filters.SEARCH.value){
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