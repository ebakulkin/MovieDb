package com.autoscout.moviedb.entity

import com.google.gson.annotations.SerializedName

data class MovieResponse(

        @SerializedName("page")
        val page: Int,

        @SerializedName("totalResults")
        val totalResults: Int,

        @SerializedName("totalPages")
        val totalPages: Int,

        @SerializedName("results")
        val results: List<Movie>
)

data class Movie(

        @SerializedName("vote_count")
        val voteCount: Int = 0,

        @SerializedName("id")
        val id: Int = 0,

        @SerializedName("video")
        val video: Boolean = false,

        @SerializedName("vote_average")
        val voteAverage: Double = .0,

        @SerializedName("title")
        val title: String = "",

        @SerializedName("popularity")
        val popularity: Double = .0,

        @SerializedName("poster_path")
        val posterPath: String = "",

        @SerializedName("original_language")
        val originalLanguage: String = "",

        @SerializedName("original_title")
        val originalTitle: String = "",

        @SerializedName("genreIds")
        val genreIds: List<Int> = mutableListOf(),

        @SerializedName("backdrop_path")
        val backdropPath: String = "",

        @SerializedName("adult")
        val adult: Boolean = false,

        @SerializedName("overview")
        val overview: String = "",

        @SerializedName("release_date")
        val releaseDate: String = ""
)