package com.autoscout.moviedb.movielist

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.autoscout.moviedb.MovieApi
import com.autoscout.moviedb.moviedetails.MovieDetailsFragment
import com.autoscout.moviedb.R
import com.autoscout.moviedb.entity.Movie
import com.autoscout.moviedb.entity.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieListFragment : androidx.fragment.app.Fragment() {

    private companion object {
        const val TAG: String = "MovieListFragment"
        const val TOTAL_PAGES = 5
    }

    private lateinit var progressBar: ProgressBar

    private val movieAdapter = MovieAdapter(::handleClick)

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.movie_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))

        val recyclerView: androidx.recyclerview.widget.RecyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.movie_list_recycler_view).apply {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
            (layoutManager as androidx.recyclerview.widget.GridLayoutManager).spanSizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (movieAdapter.getItemViewType(position)) {
                        1 -> 2
                        else -> 1
                    }
                }
            }
            adapter = movieAdapter
        }
        progressBar = view.findViewById(R.id.movie_list_progress)

        recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager) {

            override val totalPageCount: Int = TOTAL_PAGES

            override fun loadMoreItems() {
                this@MovieListFragment.isLoading = true
                currentPage += 1

                Handler().postDelayed({ loadNextPage() }, 1000)
            }

            override val isLoading: Boolean
                get() = this@MovieListFragment.isLoading

            override val isLastPage: Boolean
                get() = this@MovieListFragment.isLastPage

        })

        loadFirstPage()

    }

    private fun handleClick(movie: Movie) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.output, MovieDetailsFragment.newInstance(movie))
        transaction?.addToBackStack(null)
        transaction?.commit()
    }


    private fun loadFirstPage() {
        MovieApi.movieApi.getMovies(currentPage).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        progressBar.visibility = View.GONE
                        movieAdapter.update(it)
                        if (currentPage <= TOTAL_PAGES)
                            movieAdapter.addLoadingFooter()
                        else
                            isLastPage = true
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun loadNextPage() {
        Log.d(TAG, "loadNextPage: $currentPage")

        MovieApi.movieApi.getMovies(currentPage).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movieAdapter.removeLoadingFooter()
                    isLoading = false
                    response.body()?.results?.let {
                        movieAdapter.update(it)
                    }
                    if (currentPage <= TOTAL_PAGES)
                        movieAdapter.addLoadingFooter()
                    else
                        isLastPage = true
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}

