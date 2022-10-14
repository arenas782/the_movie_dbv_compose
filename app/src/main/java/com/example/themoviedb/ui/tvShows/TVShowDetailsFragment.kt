package com.example.themoviedb.ui.tvShows

import android.annotation.SuppressLint
import android.media.Rating
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.themoviedb.R

import com.example.themoviedb.data.model.response.tvshows.TVShowDetailsResponse
import com.example.themoviedb.data.model.response.tvshows.TVShowSeason
import com.example.themoviedb.databinding.FragmentTvShowDetailsBinding
import com.example.themoviedb.ui.base.BaseFragment
import com.example.themoviedb.ui.composables.RatingBar
import com.example.themoviedb.ui.theme.AppTheme
import com.example.themoviedb.ui.tvShows.viewmodel.TVShowsViewModel
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@AndroidEntryPoint
class TVShowDetailsFragment : BaseFragment() {
    private val viewModel: TVShowsViewModel by viewModels()
    private var _binding: FragmentTvShowDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: TVShowDetailsFragmentArgs by navArgs()


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.getTVShowDetails(args.tvShowId!!)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        MainContent()
                    }
                }
            }
        }
        return view
    }



    @Composable
    fun TVShowDetails(tvShowDetails: Resource<TVShowDetailsResponse>) {
        tvShowDetails.data?.name?.let { Text(it) }
    }

    @Composable
    fun MainContent() {

        val state = rememberCollapsingToolbarScaffoldState()
        val tvShowDetails = viewModel.tvShowDetails.observeAsState().value
        val progress = state.toolbarState.progress // how much the toolbar is expanded (0: collapsed, 1: expanded)


        if (tvShowDetails!= null){
            val seasons = tvShowDetails.seasons
            CollapsingToolbarScaffold(
                modifier = Modifier.background(MaterialTheme.colors.background),
                state = state,
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                toolbar = {

                    val textSize = (14 + (30 - 12) * progress).sp
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .pin()
                            .background(color = MaterialTheme.colors.primary)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).
                        data( Constants.BASE_URL_POSTS.plus(tvShowDetails.backdrop_path)).
                        crossfade(true).
                        build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .alpha(if (textSize.value == 14f) 0f else 1f)
                            .fillMaxWidth()
                            .height(200.dp)

                    )
                    Text(
                        tvShowDetails.name,
                        style = TextStyle(color = Color.White, fontSize = textSize),
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 40.dp, top = 16.dp)
                            .road(
                                whenCollapsed = Alignment.TopCenter,
                                whenExpanded = Alignment.BottomStart
                            )
                    )

                    RatingBar(rating = tvShowDetails.vote_average*0.5f, modifier = Modifier
                        .padding(top = 160.dp, start = 16.dp)
                        .alpha(if (progress < 1) 0f else 1f))
                    IconButton(onClick = {
                        findNavController().navigateUp()
                    }) {
                        Icon(Icons.Default.ArrowBack, "Open/Close menu", tint = Color.White)
                    }
                }) {

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = "Summary",
                            style = TextStyle(color = MaterialTheme.colors.primary, fontSize = 18.sp),
                        )
                    }
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = tvShowDetails.overview,
                            style = TextStyle( fontSize = 14.sp),
                        )
                    }
                    items(seasons) { season ->
                        if (season.episode_count!! > 0)
                            TVShowSeason(season = season)
                    }
                }
            }
        }
    }
    @Composable
    fun TVShowSeason(season : TVShowSeason){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(160.dp)
        ) {
            Row(){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).
                    data( Constants.BASE_URL_POSTS.plus(season.poster_path)).
                    crossfade(true).
                    placeholder(R.drawable.placeholder).
                    build(),

                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.width(120.dp)
                )
                Column() {
                    Text(
                        text =season.name.toString(), modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        style = TextStyle(fontSize = 18.sp)
                    )

                    Text(
                        text ="${season.episode_count} episodes", modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        style = TextStyle(color = MaterialTheme.colors.primary)
                    )
                    Text(
                        text =season.overview.toString(), modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp),
                        style = TextStyle(fontSize = 12.sp),
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}