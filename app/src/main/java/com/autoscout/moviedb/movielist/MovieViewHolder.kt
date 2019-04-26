package com.autoscout.moviedb.movielist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.autoscout.moviedb.R
import com.autoscout.moviedb.displayPoster
import com.autoscout.moviedb.entity.Movie


class MovieViewHolder(itemView: View, onClickListener: (Movie) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private companion object {
        const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w300"
    }

    private lateinit var movieItem: Movie

    private val movieName: TextView = itemView.findViewById(R.id.movie_name)
    private val container: View = itemView.findViewById(R.id.clickable_surface)
    private val posterImg: ImageView = itemView.findViewById(R.id.movie_poster)

    init {
        itemView.tag = this
        container.setOnClickListener {
            onClickListener(movieItem)
        }
    }

    fun bind(item: Movie) {
        movieItem = item
        movieName.text = item.title

        posterImg.displayPoster(BASE_URL_IMG + item.posterPath, movieName)
    }


}