package com.autoscout.moviedb.moviedetails

import android.os.Parcel
import android.os.Parcelable


class DetailPageUiModel(
    val id: Int,
    val name: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val backdropPath: String
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<DetailPageUiModel> {
        override fun createFromParcel(parcel: Parcel) = DetailPageUiModel(parcel)
        override fun newArray(size: Int) = arrayOfNulls<DetailPageUiModel>(size)
    }


    private constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        name = parcel.readString(),
        overview = parcel.readString(),
        releaseDate = parcel.readString(),
        voteAverage = parcel.readDouble(),
        voteCount = parcel.readInt(),
        backdropPath = parcel.readString()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
        parcel.writeInt(voteCount)
        parcel.writeString(backdropPath)
    }

    override fun describeContents(): Int = 0


}