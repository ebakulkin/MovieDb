package com.autoscout.moviedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette


fun ViewGroup.inflate(layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)

fun ImageView.displayImageurl(url: String) =
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions().crossFade())
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
        .into(this)

fun ImageView.displayPoster(url: String, captionTextView: TextView) =
    Glide
        .with(this.context)
        .load(url)
        .listener(
            GlidePalette.with(url)
                .use(BitmapPalette.Profile.VIBRANT)
                .intoBackground(captionTextView, BitmapPalette.Swatch.RGB)
                .intoTextColor(captionTextView, BitmapPalette.Swatch.BODY_TEXT_COLOR)
                .crossfade(true)
        )
        .transition(DrawableTransitionOptions().crossFade())
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
        .into(this)