package com.example.themoviedb.ui.tvShows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.themoviedb.R
import com.example.themoviedb.data.model.response.MovieDetailsResponse
import com.example.themoviedb.databinding.FragmentTvShowDetailsBinding
import com.example.themoviedb.ui.base.BaseFragment
import com.example.themoviedb.ui.tvShows.viewmodel.TVShowsViewModel
import com.example.themoviedb.utils.Commons
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.utils.Status
import com.example.themoviedb.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {
    private val args : DetailsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentTvShowDetailsBinding::bind)
    private val viewModel : TVShowsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setupListener()
    }

    private fun setupListener(){
//        val movieId = args.movieId
//        if(movieId != null)
//        viewModel.getMovieDetails(movieId = movieId.toInt()).observe(viewLifecycleOwner){
//            when (it.status){
//                Status.SUCCESS -> {
//                    binding.progress.isVisible = false
//                    it.data.let { movie ->
//                        bindMovie(movie)
//                    }
//                }
//                Status.LOADING -> {
//                    binding.progress.isVisible = true
//                    Log.e("TAG","loading")
//                }
//                Status.ERROR -> {
//                    binding.progress.isVisible = false
//                    Log.e("TAG","${it.message}")
//                }
//            }
//        }
    }

    private fun bindMovie(movie : MovieDetailsResponse?){
        if (movie != null){
            var genresList  = ""
            binding.tvTitle.text = movie.title
            binding.tvReleaseDate.text = Commons.getString(R.string.release_date,movie.release_date.toString())

            movie.genres?.forEachIndexed {index, element ->
                genresList = genresList.plus(element.name)
                if(index != movie.genres.size - 1)
                    genresList = genresList.plus(", ")
            }

            binding.tvGenres.text = Commons.getString(R.string.genres,genresList)
            binding.tvOverview.text = movie.overview
            binding.ivPoster.load(Constants.BASE_URL_POSTS.plus(movie.poster_path)){
                crossfade(true)
                placeholder(R.drawable.ic_100tb)
                scale(Scale.FIT)
            }
        }
    }
}