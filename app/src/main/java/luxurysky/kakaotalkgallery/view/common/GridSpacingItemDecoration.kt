package luxurysky.kakaotalkgallery.view.common

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(var spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    companion object {
        private val TAG = GridSpacingItemDecoration::class.java.simpleName
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        spanCount = (parent.layoutManager as GridLayoutManager).spanCount

        val position = parent.getChildAdapterPosition(view) // item position
        val itemCount = parent.adapter!!.itemCount


        if (position >= 0) {
            val column = position % spanCount // item column

//            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
//            outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            outRect.left = spacing / 2
            outRect.right = spacing / 2

            if (column == 0) {
                outRect.left = spacing
            } else if (column + 1 == spanCount) {
                outRect.right = spacing
            }


            if (position < spanCount) { // top edge
                outRect.top = spacing
            } else {
                outRect.top = spacing / 2
            }

            if (isLastGridRow(position, itemCount, spanCount)) {
                outRect.bottom = spacing // item bottom
            } else {
                outRect.bottom = spacing / 2 // item bottom
            }

        } else {
            outRect.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
        }
        Log.d(TAG, "spancount : $spanCount, getItemOffsets: $position, rect : $outRect")
    }

    private fun isLastGridRow(position: Int, itemSize: Int, columnSize: Int): Boolean {
        val temp = itemSize % columnSize
        if (temp == 0 && position >= itemSize - columnSize) {
            return true
        } else if (position >= itemSize / columnSize * columnSize) {
            return true
        }
        return false
    }
}
