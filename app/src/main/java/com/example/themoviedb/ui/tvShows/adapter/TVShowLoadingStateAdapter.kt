package com.example.themoviedb.ui.tvShows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState

import com.example.themoviedb.databinding.ItemNetworkStateBinding
import com.example.themoviedb.ui.tvShows.holder.LoadStateViewHolder


class TVShowLoadingStateAdapter(private val retry: () -> Unit) {}
//    LoadStateAdapter<LoadStateViewHolder>() {
//
//    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
//
//        val progress = holder.binding.progressBarItem
//        val retryBtn = holder.binding.retyBtn
//        val txtErrorMessage = holder.binding.errorMsgItem
//
//        if (loadState is LoadState.Loading) {
//            progress.isVisible = true
//            txtErrorMessage.isVisible = false
//            retryBtn.isVisible = false
//
//        } else {
//            progress.isVisible = false
//        }
//
//        if (loadState is LoadState.Error) {
//            txtErrorMessage.isVisible = true
//            retryBtn.isVisible = true
//            txtErrorMessage.text = loadState.error.localizedMessage
//        }
//
//
//        retryBtn.setOnClickListener {
//            retry.invoke()
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
//        return LoadStateViewHolder(
//            ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        )
//    }
//}


