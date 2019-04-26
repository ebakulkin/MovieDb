package com.autoscout.moviedb.moviedetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.autoscout.moviedb.R
import com.autoscout.moviedb.displayImageurl
import com.autoscout.moviedb.entity.Movie
import com.autoscout.moviedb.entity.MovieCast
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    private val movieCastsAdapter = MovieCastsAdapter()
    private val movieDetailApiCallManager = MovieDetailApiCallManager()

    private var movieId: Int = 0

    private lateinit var toolbar: Toolbar
    private lateinit var posterImg: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var movieReleaseDateTextView: TextView
    private lateinit var voteAverageTextView: TextView
    private lateinit var voteCountTextView: TextView
    private lateinit var favButton: FloatingActionButton
    private lateinit var castsRecyclerView: RecyclerView
    private lateinit var showMoreTextView: ShowMoreTextView

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.movie_details, viewGroup, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val detailPageUiModel = arguments?.get(DETAIL_PAGE_DATA) as DetailPageUiModel
        movieId = detailPageUiModel.id

        initViews(view)
        setViewsWithArguments(detailPageUiModel)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favButton.setOnClickListener {
            Toast.makeText(
                    activity, it.context.getText(R.string.fav_added),
                    Toast.LENGTH_LONG
            ).show()
        }

        castsRecyclerView.apply {
            layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = movieCastsAdapter
        }


        movieDetailApiCallManager.loadCasts(movieId, ::onLoadCastsSuccess, ::onLoadCastsFailure)
    }

    private fun initViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        posterImg = view.findViewById(R.id.movie_poster)
        titleTextView = view.findViewById(R.id.title)
        movieReleaseDateTextView = view.findViewById(R.id.movie_release_date)
        voteAverageTextView = view.findViewById(R.id.vote_average)
        voteCountTextView = view.findViewById(R.id.vote_count)
        favButton = view.findViewById(R.id.fav_icon)
        castsRecyclerView = view.findViewById(R.id.recycler_view_cast_movie_detail)
        showMoreTextView = view.findViewById(R.id.movie_description)
    }

    private fun setViewsWithArguments(detailPageUiModel: DetailPageUiModel) {
        toolbar.title = detailPageUiModel.name
        posterImg.displayImageurl(BASE_URL_IMG + detailPageUiModel.backdropPath)
        titleTextView.text = detailPageUiModel.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            movieReleaseDateTextView.text =
                    LocalDate.parse(detailPageUiModel.releaseDate).format(DateTimeFormatter.ofPattern(RELEASE_DATE_FORMAT))
        }
        voteAverageTextView.text = detailPageUiModel.voteAverage.toString()
        voteCountTextView.text = detailPageUiModel.voteCount.toString()
        showMoreTextView.setText(detailPageUiModel.overview)
    }

    private fun onLoadCastsSuccess(casts: List<MovieCast>) {
        movieCastsAdapter.update(casts)
    }

    private fun onLoadCastsFailure(t: Throwable) {
        t.printStackTrace()
    }

}