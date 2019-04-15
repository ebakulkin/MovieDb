package com.autoscout.moviedb.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autoscout.moviedb.R
import com.autoscout.moviedb.entity.MovieCast


class MovieCastsAdapter() : RecyclerView.Adapter<MovieCastsViewHolder>() {

    private val dataList = mutableListOf<MovieCast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastsViewHolder = MovieCastsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false))

    override fun onBindViewHolder(viewHolder: MovieCastsViewHolder, position: Int) = viewHolder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    fun update(updatedList: List<MovieCast>) {
        dataList.addAll(updatedList)
        notifyDataSetChanged()
    }
}