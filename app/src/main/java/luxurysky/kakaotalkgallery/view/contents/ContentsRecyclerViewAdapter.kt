package luxurysky.kakaotalkgallery.view.contents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_content.view.*
import luxurysky.kakaotalkgallery.GlideApp
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.dummy.DummyContent.DummyItem
import luxurysky.kakaotalkgallery.model.Content
import luxurysky.kakaotalkgallery.util.StringUtils
import luxurysky.kakaotalkgallery.view.contents.ContentsFragment.OnListFragmentInteractionListener

class ContentsRecyclerViewAdapter(private val mListener: OnListFragmentInteractionListener?) :
    RecyclerView.Adapter<ContentsRecyclerViewAdapter.ViewHolder>() {

    private val mValues: MutableList<Content> = mutableListOf()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun setItems(items: List<Content>) {
        mValues.clear()
        mValues.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
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
        GlideApp.with(holder.thumbnailImageView)
            .load(item.thumbnail)
            .into(holder.thumbnailImageView)

        holder.fileSizeTextView.text = StringUtils.convertFileSize(item.size)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnailImageView: ImageView = view.thumbnailImageView
        val fileSizeTextView: TextView = view.fileSizeTextView
//        val mIdView: TextView = view.item_number
//        val mContentView: TextView = view.content
//
//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
    }
}
