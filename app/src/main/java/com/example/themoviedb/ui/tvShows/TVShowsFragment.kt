package com.example.themoviedb.ui.tvShows

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.themoviedb.R
import com.example.themoviedb.data.model.TVShow
import com.example.themoviedb.databinding.FragmentTvShowsBinding
import com.example.themoviedb.ui.base.BaseFragment
import com.example.themoviedb.ui.tvShows.viewmodel.TVShowsViewModel
import com.example.themoviedb.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TVShowsFragment  : BaseFragment(){

    private val viewModel : TVShowsViewModel by viewModels()
    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding!!



    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                Scaffold(
                    topBar = { TopAppBar() },
                    content = {
                        Column {
                            filters()
                            getTVShows(viewModel = viewModel, context = requireContext())
                        }
                    }
                )
            }
        }
        return view
    }


    @Composable
    private fun TopAppBar() {
        TopAppBar(
            title = { Text(text = "TV Shows") },
            actions = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar")
                }
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Buscar")
                }
            }
        )
    }

    @Composable
    fun TVShowItem(tvShowData: TVShow, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(
                    bottom = 5.dp, top = 5.dp,
                    start = 5.dp, end = 5.dp
                )
                .clickable(onClick = onClick)
                .height(240.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = 12.dp,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
                ) {
                Row(
                    modifier = Modifier.height(160.dp)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).
                        data( Constants.BASE_URL_POSTS.plus(tvShowData.poster_path)).
                        crossfade(true).
                        build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 10.dp, start = 16.dp,end = 16.dp)
                ){
                    Text(text =  tvShowData.name.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                }
                Row (
                    modifier =  Modifier.padding(start = 16.dp, top = 8.dp, end = 8.dp)

                ){
                    RatingBar( rating = mapRange(tvShowData.vote_average!!,IntRange(0,10),IntRange(0,5)))
                    Text( text =  String.format("%.1f", mapRange(tvShowData.vote_average,IntRange(0,10),IntRange(0,5))), modifier = Modifier.padding(start = 4.dp,top=2.dp))
                }
            }
        }
    }

    @Composable
    fun getTVShows(modifier: Modifier = Modifier, viewModel:TVShowsViewModel, context: Context) {
        val tvShows by viewModel.tvshows.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        when {
            isLoading -> {
                Log.e("TAG","cargando")
                LoadingIndicator()
            }
            else ->{
                Log.e("TAG","mostrando")
                tvShowsList(modifier, items = tvShows, context)
            }
        }

    }


    @Composable
    fun tvShowsList(
        modifier: Modifier,
        items: Flow<PagingData<TVShow>>,
        context: Context) {
        val tvShowsList: LazyPagingItems<TVShow> = items.collectAsLazyPagingItems()


            LazyVerticalGrid( columns = GridCells.Adaptive(minSize = 140.dp) ) {
                items(tvShowsList.itemCount) { item ->
                    item.let { index ->
                        tvShowsList[index]?.let {
                            TVShowItem(tvShowData = it, onClick = {
                                Toast.makeText(context, tvShowsList[index]?.name.toString(),   Toast.LENGTH_SHORT).show()
                            })
                        }
                    }
                }
                tvShowsList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            //You can add modifier to manage load state when first time response page is loading
                        }
                        loadState.append is LoadState.Loading -> {
                            //You can add modifier to manage load state when next response page is loading
                        }
                        loadState.append is LoadState.Error -> {
                            //You can use modifier to show error message
                        }
                    }
                }
            }
    }

    @Composable
    fun RatingBar(
        modifier: Modifier = Modifier,
        rating: Float = 0f,
        stars: Int = 5,
        starsColor: Color = Color(R.color.stars_color)
    ) {
        val filledStars = kotlin.math.floor(rating).toInt()
        val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
        val halfStar = !(rating.rem(1).equals(0.0))

        Row(modifier = modifier) {
            repeat(filledStars) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
            }
            if (halfStar) {
                Icon(
                    imageVector = Icons.Outlined.StarHalf,
                    contentDescription = null,
                    tint = starsColor
                )
            }
            repeat(unfilledStars) {
                Icon(
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = null,
                    tint = starsColor
                )
            }
        }
    }

    @Composable
    fun filters(){
        val selectedFilter = viewModel.selectedFilter.value
        LazyRow(modifier = Modifier.padding(top = 10.dp, start = 8.dp, bottom = 8.dp)){
            items(Filters.values()){ filter ->
                filterChip(
                    filterName = filter.value,
                    isSelected = selectedFilter == filter.value,
                    onSelectedCategoryChanged ={ viewModel.updateFilter(filter.value)},
                    onExecuteSearch = viewModel::newSearch
                )
            }
        }
    }
    @Preview
    @Composable
    fun LoadingIndicator() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun filterChip(
        filterName : String,
        isSelected : Boolean = false,
        onSelectedCategoryChanged : (String) -> Unit,
        onExecuteSearch : () -> Unit
    ){
        Surface(
            modifier = Modifier.padding(end = 8.dp),
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium,
            color = if (!isSelected) Color.LightGray else MaterialTheme.colors.primary
        ){
            Row(
                modifier = Modifier.toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelectedCategoryChanged(filterName)
                        onExecuteSearch()
                    }
                )
            ){
                Text(
                    text = filterName,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }


    private fun mapRange(number: Float, prevRange: IntRange, newRange: IntRange) : Float {
        val ratio = number/ (prevRange.last - prevRange.first)
        return (ratio * (newRange.last - newRange.first) + newRange.first)
    }
}