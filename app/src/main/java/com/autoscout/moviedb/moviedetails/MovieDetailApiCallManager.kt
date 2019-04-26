package com.autoscout.moviedb.moviedetails

import com.autoscout.moviedb.MovieApi
import com.autoscout.moviedb.entity.MovieCast
import com.autoscout.moviedb.entity.MovieCreditsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailApiCallManager {

    fun loadCasts(movieId: Int, onSuccess: (List<MovieCast>) -> Unit, onFailure: (Throwable) -> Unit) {
        MovieApi.movieApi.getMovieCredits(movieId).enqueue(object : Callback<MovieCreditsResponse> {
            override fun onResponse(call: Call<MovieCreditsResponse>, response: Response<MovieCreditsResponse>) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.casts ?: emptyList())
                }
            }

            override fun onFailure(call: Call<MovieCreditsResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}