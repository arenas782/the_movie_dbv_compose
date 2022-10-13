package com.example.themoviedb.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

//
//@BindingAdapter(value= ["bind:adapter", "bind:viewmodel"],requireAll = true)
//fun setAdapter(rv: RecyclerView, mAdapter: PagingDataAdapter<*,*>,vm : TVShowsViewModel) {
//    rv.apply {
//        setHasFixedSize(true)
//        mAdapter as TVShowAdapter
//        val swipeGesture = object : SwipeGesture(){
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                Log.e("TAG","$direction")
//              //  vm.deletePost(viewHolder.adapterPosition)
//            }
//        }
//        val touchHelper = ItemTouchHelper(swipeGesture)
//        touchHelper.attachToRecyclerView(rv)
//        adapter = mAdapter
//    }
//}



@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("date")
fun date(tv: TextView, date : Long) {
    tv.text = Commons.millisToDate(date)
}


