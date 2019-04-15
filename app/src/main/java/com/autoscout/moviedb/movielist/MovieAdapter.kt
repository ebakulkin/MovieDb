package com.autoscout.moviedb.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.autoscout.moviedb.R
import com.autoscout.moviedb.entity.Movie


class MovieAdapter(private val onClickListener: (Movie) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    private val dataList = mutableListOf<Movie>()
    private var isLoadingAdded: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder

        when (viewType) {
            LOADING -> {
                val view = layoutInflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
                viewHolder = MovieViewHolder(view, onClickListener)
            }
        }
        return viewHolder

    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int =
            if (position == dataList.lastIndex && isLoadingAdded)
                LOADING
            else
                ITEM

    override fun onBindViewHolder(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is MovieViewHolder)
            viewHolder.bind(dataList[position])
    }

    fun update(updatedList: List<Movie>) {
        dataList.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun add(mc: Movie) {
        dataList.add(mc)
        notifyItemInserted(dataList.lastIndex)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Movie())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = dataList.lastIndex

        dataList.removeAt(position)
        notifyItemRemoved(position)
    }
}

