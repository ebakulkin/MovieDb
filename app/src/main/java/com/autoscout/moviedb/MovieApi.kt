package com.autoscout.moviedb

import com.autoscout.moviedb.entity.MovieCreditsResponse
import com.autoscout.moviedb.entity.MovieResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/top_rated")
    fun getMovies(@Query("page") page: Int, @Query("language") language: String = "de-DE", @Query("api_key") apiKey: String = API_KEY): Call<MovieResponse>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") movieId: Int, @Query("api_key") apiKey: String = API_KEY): Call<MovieCreditsResponse>

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "e3c587eeed7d9e7f82c3a74ca3557075"

        fun create(): MovieApi {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(MovieApi::class.java)
        }
    }
}