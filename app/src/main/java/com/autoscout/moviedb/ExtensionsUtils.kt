package com.autoscout.moviedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ViewGroup.inflate(layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)

fun ImageView.displayImageUrl(url: String) =
    Glide.with(this.context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
        .into(this)