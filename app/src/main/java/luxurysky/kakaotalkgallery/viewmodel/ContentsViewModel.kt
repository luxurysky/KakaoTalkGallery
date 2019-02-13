package luxurysky.kakaotalkgallery.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import luxurysky.kakaotalkgallery.model.Content
import luxurysky.kakaotalkgallery.model.ContentsRepository

class ContentsViewModel : ViewModel() {

    companion object {
        private val TAG = ContentsViewModel::class.java.simpleName
    }

    var mCurrentSortByDate: ObservableField<SortByDate> = ObservableField(SortByDate.DESCENDING)
        private set
    var mCurrentSortBySize: ObservableField<SortBySize> = ObservableField(SortBySize.DESCENDING)
        private set

    private val mContentsUpdateSubject = BehaviorSubject.create<List<Content>>()

    private val mLoadingIndicatorSubject = BehaviorSubject.create<Boolean>()
    private val mSnackbarText = BehaviorSubject.create<Int>()
    private val mDisposables = CompositeDisposable()

    enum class SortByDate {
        DESCENDING,
        ASCENDING
    }

    enum class SortBySize {
        DESCENDING,
        ASCENDING
    }

    override fun onCleared() {
        super.onCleared()
        mDisposables.dispose()
    }

    fun getContentsUpdate(): Observable<List<Content>> {
        return mContentsUpdateSubject
    }

    fun getLoadingIndicatorVisibility(): Observable<Boolean> {
        return mLoadingIndicatorSubject
    }

    fun getSnackbarMessage(): Observable<Int> {
        return mSnackbarText
    }

    fun toggleSortByDate() {
        mCurrentSortByDate.set(if (mCurrentSortByDate.get() == SortByDate.DESCENDING) SortByDate.ASCENDING else SortByDate.DESCENDING)
        mCurrentSortBySize.set(SortBySize.DESCENDING)
        updateContents()
    }

    fun toggleSortBySize() {
        mCurrentSortByDate.set(SortByDate.DESCENDING)
        mCurrentSortBySize.set(if (mCurrentSortBySize.get() == SortBySize.DESCENDING) SortBySize.ASCENDING else SortBySize.DESCENDING)
        updateContents()
    }

    fun updateContents() {
        getContents()
            .subscribe {
                mContentsUpdateSubject.onNext(it)
            }.addTo(mDisposables)
    }

    private fun getContents(): Observable<List<Content>> {
        return ContentsRepository.getContents()
            .doOnSubscribe { mLoadingIndicatorSubject.onNext(true) }
            .doOnNext { mLoadingIndicatorSubject.onNext(false) }
            .map {
                if (mCurrentSortByDate.get() == SortByDate.DESCENDING) {
                    it.sortedByDescending { it.size }
                } else {
                    it.sortedBy { it.size }
                }
            }
    }
}