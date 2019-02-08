package luxurysky.kakaotalkgallery.view.contents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_contents.*
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.dummy.DummyContent.DummyItem
import luxurysky.kakaotalkgallery.view.common.GridSpacingItemDecoration

class ContentsFragment : Fragment() {
    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                ContentsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    private var columnCount = 1

    private lateinit var mViewModel: ContentsViewModel
    private val mDisposables = CompositeDisposable()
    private lateinit var mAdapter: ContentsRecyclerViewAdapter
    private var listener: OnListFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(ContentsViewModel::class.java)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(contentListRecyclerView)
        {
            addItemDecoration(GridSpacingItemDecoration(3, 10))
            layoutManager = GridLayoutManager(context, 3)
            mAdapter = ContentsRecyclerViewAdapter(listener)
            adapter = mAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        bindViewModel()
    }

    override fun onStop() {
        super.onStop()
        unbindViewModel()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposables.dispose()
    }

    private fun bindViewModel() {
        mViewModel.getContents()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mAdapter.setItems(it)
                }.let(mDisposables::add)

        mViewModel.getLoadingIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    setLoadingIndicatorVisibility(it)
                }.let(mDisposables::add)

        mViewModel.getSnackbarMessage()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showSnackbar(it)
                }.let(mDisposables::add)
    }

    private fun unbindViewModel() {
        mDisposables.clear()
    }

    private fun setLoadingIndicatorVisibility(isVisible: Boolean) {
        loadingProgressBar.isVisible = isVisible
    }

    private fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: DummyItem?)
    }
}