package com.autoscout.moviedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


fun ViewGroup.inflate(layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)

fun ImageView.displayImageurl(url: String) =
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions().crossFade())
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
        .into(this)