package com.example.themoviedb.ui.tvShows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.themoviedb.data.model.TVShow

import com.example.themoviedb.ui.tvShows.holder.TVShowViewHolder
class TVShowAdapter ()
//
//
//class TVShowAdapter(private val callback: (TVShow) -> Unit) :
//    PagingDataAdapter<TVShow, TVShowViewHolder>(
//        MoviesClickCallback()
//    ) {
//
//    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
//
//        val data = getItem(position)
//
//        holder.bind(data!!,callback)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
//
//        return TVShowViewHolder(
//            ItemTvShowBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    fun getMovieId(position: Int):Int = getItem(position)?.id!!
//
//    private class MoviesClickCallback : DiffUtil.ItemCallback<TVShow>() {
//        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//}