package com.example.productsapo.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreOnScrollListener(private var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val  visibleItemCount : Int = layoutManager.childCount
        var  totalItemCount = layoutManager.itemCount
        val firstVisibleitemPosition: Int = layoutManager.findFirstVisibleItemPosition()


        if ((firstVisibleitemPosition >= 0) && ((visibleItemCount + firstVisibleitemPosition) >= totalItemCount)
        ) {

            if(isLoading()||isLastPage()){
                return
            }
            loadMoreItems()
        }

    }



    abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}