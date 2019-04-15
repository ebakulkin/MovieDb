package com.autoscout.moviedb.entity

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
        @SerializedName("id")
        val id: Int,

        @SerializedName("cast")
        val casts: List<MovieCast>,

        @SerializedName("crew")
        val crews: List<MovieCrew>
)

data class MovieCast(
        @SerializedName("cast_id")
        var castId: Int,

        @SerializedName("character")
        var character: String,

        @SerializedName("credit_id")
        var creditId: String,

        @SerializedName("gender")
        var gender: Int,

        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("order")
        var order: Int,

        @SerializedName("profile_path")
        var profilePath: String
)

class MovieCrew(
        @SerializedName("credit_id")
        var creditId: String,

        @SerializedName("department")
        var department: String,

        @SerializedName("gender")
        var gender: Int,

        @SerializedName("id")
        var id: Int,

        @SerializedName("job")
        var job: String,

        @SerializedName("name")
        var name: String,

        @SerializedName("profile_path")
        var profilePath: String
)