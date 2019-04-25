package com.autoscout.moviedb.moviedetails

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.autoscout.moviedb.R
import com.autoscout.moviedb.entity.MovieCast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class MovieCastsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private companion object {
        const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w300/"
    }

    private val castImageView: ImageView = itemView.findViewById(R.id.image_view_cast)
    private val nameTextView: TextView = itemView.findViewById(R.id.text_view_cast_name)
    private val characterTextView: TextView = itemView.findViewById(R.id.text_view_cast_as)

    fun bind(item: MovieCast) {
        Glide.with(itemView.context)
                .asBitmap()
                .load(BASE_URL_IMG + item.profilePath)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
                .into(castImageView)

        nameTextView.text = item.name
        characterTextView.text = item.character
    }
}