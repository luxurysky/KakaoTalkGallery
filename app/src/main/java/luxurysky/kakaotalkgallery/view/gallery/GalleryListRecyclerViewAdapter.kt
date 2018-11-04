package luxurysky.kakaotalkgallery.view.gallery


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_gallery.view.*
import luxurysky.kakaotalkgallery.GlideApp
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.dummy.DummyContent.DummyItem
import luxurysky.kakaotalkgallery.view.gallery.GalleryListFragment.OnListFragmentInteractionListener
import java.io.File

class GalleryListRecyclerViewAdapter(

    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<GalleryListRecyclerViewAdapter.ViewHolder>() {

    private val mValues: MutableList<File> = mutableListOf()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun setItems(items: List<File>) {
        mValues.clear()
        mValues.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
//        holder.mIdView.text = item.id
//        holder.mContentView.text = item.content

//        with(holder.mView) {
//            tag = item
//            setOnClickListener(mOnClickListener)
//        }
        GlideApp.with(holder.mImageView)
            .load(mValues[position])
            .into(holder.mImageView)
        Log.d("TAG", "onBind position : $position, file : ${mValues[position]}")
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView.imageView
//        val mIdView: TextView = mView.item_number
//        val mContentView: TextView = mView.content
//
//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
    }
}
