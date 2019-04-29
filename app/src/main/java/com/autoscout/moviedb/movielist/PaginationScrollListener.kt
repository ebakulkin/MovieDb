package com.autoscout.moviedb.movielist


abstract class PaginationScrollListener(private val layoutManager: androidx.recyclerview.widget.LinearLayoutManager) :
    androidx.recyclerview.widget.RecyclerView.OnScrollListener() {

    abstract val totalPageCount: Int
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= totalPageCount) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
}