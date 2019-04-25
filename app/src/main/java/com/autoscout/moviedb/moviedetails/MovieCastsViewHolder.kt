package com.autoscout.moviedb.moviedetails

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.autoscout.moviedb.R
import com.autoscout.moviedb.displayImageUrl
import com.autoscout.moviedb.entity.MovieCast


class MovieCastsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private companion object {
        const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w300/"
    }

    private val castImageView: ImageView = itemView.findViewById(R.id.image_view_cast)
    private val nameTextView: TextView = itemView.findViewById(R.id.text_view_cast_name)
    private val characterTextView: TextView = itemView.findViewById(R.id.text_view_cast_as)

    fun bind(item: MovieCast) {
        castImageView.displayImageUrl(BASE_URL_IMG + item.profilePath)
        nameTextView.text = item.name
        characterTextView.text = item.character
    }
}