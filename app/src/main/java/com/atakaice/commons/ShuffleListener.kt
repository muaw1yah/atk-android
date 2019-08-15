package com.atakaice.commons

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.meetic.dragueur.Direction
import com.meetic.dragueur.DraggableView
import com.meetic.shuffle.Shuffle

class ShuffleListener(
    val func: () -> Unit,
    val layoutManager: LinearLayoutManager
) : Shuffle.Listener {
    override fun onViewChanged(position: Int) {
        Log.i("INFO", "onViewChanged")
    }

    override fun onScrollFinished() {
        Log.i("INFO", "onScrollFinished")
    }

    override fun onViewScrolled(draggableView: DraggableView?, percentX: Float, percentY: Float) {
        Log.i("INFO", "onViewScrolled")
    }

    override fun onScrollStarted() {
        Log.i("INFO", "onScrollStarted")
    }

    override fun onViewExited(draggableView: DraggableView?, direction: Direction?) {
        Log.i("INFO", "onViewExited")
    }

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//
//        if (dy > 0) {
//            visibleItemCount = recyclerView.childCount;
//            totalItemCount = layoutManager.itemCount;
//            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//
//            if (loading) {
//                if (totalItemCount > previousTotal) {
//                    loading = false;
//                    previousTotal = totalItemCount;
//                }
//            }
//            if (!loading && (totalItemCount - visibleItemCount)
//                <= (firstVisibleItem + visibleThreshold)) {
//                // End has been reached
//                Log.i("InfiniteScrollListener", "End reached");
//                func()
//                loading = true;
//            }
//        }
//    }

}