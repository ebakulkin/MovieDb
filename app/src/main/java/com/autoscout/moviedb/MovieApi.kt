package com.autoscout.moviedb

import com.autoscout.moviedb.entity.MovieCreditsResponse
import com.autoscout.moviedb.entity.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/top_rated")
    fun getMovies(@Query("page") page: Int, @Query("language") language: String = "de-DE"): Call<MovieResponse>

    @GET("movie/{id}/credits")
    fun getMovieCredits(@Path("id") movieId: Int): Call<MovieCreditsResponse>

    companion object {

        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "e3c587eeed7d9e7f82c3a74ca3557075"

        val movieApi: MovieApi = create()

        fun create(): MovieApi {
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor { chain ->
                val originalRequest = chain.request()
                val urlBuilder = originalRequest.url().newBuilder()
                urlBuilder.addQueryParameter("api_key", MovieApi.API_KEY)

                val request = originalRequest.newBuilder()
                    .url(urlBuilder.build())
                    .build()
                chain.proceed(request)
            }
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(MovieApi::class.java)
        }
    }
}