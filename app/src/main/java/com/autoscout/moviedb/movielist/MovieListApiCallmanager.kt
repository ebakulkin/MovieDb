package com.autoscout.moviedb.movielist

import com.autoscout.moviedb.MovieApi
import com.autoscout.moviedb.entity.Movie
import com.autoscout.moviedb.entity.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListApiCallmanager {
    fun loadMovies(page: Int, onSuccess: (List<Movie>) -> Unit, onFailure: (Throwable) -> Unit) {
        MovieApi.movieApi.getMovies(page).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.results ?: emptyList())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}