package com.example.themoviedb.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter




@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("date")
fun date(tv: TextView, date : Long) {
    tv.text = Commons.millisToDate(date)
}


