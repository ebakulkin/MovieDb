package com.autoscout.moviedb.moviedetails

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.autoscout.moviedb.MovieApi
import com.autoscout.moviedb.R
import com.autoscout.moviedb.displayImageurl
import com.autoscout.moviedb.entity.Movie
import com.autoscout.moviedb.entity.MovieCreditsResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MovieDetailsFragment : androidx.fragment.app.Fragment() {

    companion object {
        private const val BASE_URL_IMG = "https://image.tmdb.org/t/p/w780"
        private const val DETAIL_PAGE_DATA = "detailPageData"
        private const val RELEASE_DATE_FORMAT = "dd MMMM yyyy"

        fun newInstance(movie: Movie): MovieDetailsFragment {
            val detailPageUiModel = DetailPageUiModel(
                movie.id,
                movie.title,
                movie.overview,
                movie.releaseDate,
                movie.voteAverage,
                movie.voteCount,
                movie.backdropPath
            )

            return MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DETAIL_PAGE_DATA, detailPageUiModel)
                }
            }
        }

    }

    private val movieCastsAdapter: MovieCastsAdapter = MovieCastsAdapter()

    private var movieId: Int = 0

    private var overviewExpanded: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.movie_details, viewGroup, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val detailPageUiModel = arguments?.get(DETAIL_PAGE_DATA) as DetailPageUiModel
        movieId = detailPageUiModel.id

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.title = detailPageUiModel.name
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val posterImg = view.findViewById<ImageView>(R.id.movie_poster)
        posterImg.displayImageurl(BASE_URL_IMG + detailPageUiModel.backdropPath)

        (view.findViewById(R.id.title) as TextView).text = detailPageUiModel.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (view.findViewById(R.id.movie_release_date) as TextView).text =
                LocalDate.parse(detailPageUiModel.releaseDate).format(DateTimeFormatter.ofPattern(RELEASE_DATE_FORMAT))
        }
        (view.findViewById(R.id.vote_average) as TextView).text = detailPageUiModel.voteAverage.toString()
        (view.findViewById(R.id.vote_count) as TextView).text = detailPageUiModel.voteCount.toString()

        val favButton = view.findViewById<FloatingActionButton>(R.id.fav_icon)
        favButton.setOnClickListener {
            Toast.makeText(
                activity, it.context.getText(R.string.fav_added),
                Toast.LENGTH_LONG
            ).show()
        }

        val castsRecyclerView: androidx.recyclerview.widget.RecyclerView =
            view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recycler_view_cast_movie_detail).apply {
                layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = movieCastsAdapter
            }

        val movieDetailsTextView: TextView = view.findViewById(R.id.movie_details_overview)
        val showMoreButton: Button = view.findViewById(R.id.show_more)
        showMoreButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                if (overviewExpanded) {
                    val animation: ObjectAnimator = ObjectAnimator.ofInt(movieDetailsTextView, "maxLines", 3);
                    animation.setDuration(200).start();
                    showMoreButton.setText(R.string.show_more)
                    overviewExpanded = false
                } else {
                    val animation: ObjectAnimator = ObjectAnimator.ofInt(movieDetailsTextView, "maxLines", 40)
                    animation.setDuration(200).start()
                    showMoreButton.setText(R.string.show_less)
                    overviewExpanded = true
                }
            }
        })


        movieDetailsTextView.text = detailPageUiModel.overview

        loadCasts()
    }

    private fun loadCasts() {
        MovieApi.movieApi.getMovieCredits(movieId).enqueue(object : Callback<MovieCreditsResponse> {
            override fun onResponse(call: Call<MovieCreditsResponse>, response: Response<MovieCreditsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.casts?.let {
                        movieCastsAdapter.update(it)
                    }
                }
            }

            override fun onFailure(call: Call<MovieCreditsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}