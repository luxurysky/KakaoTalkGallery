package luxurysky.kakaotalkgallery.view.gallery

import android.os.Environment
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.io.File


class GalleryListViewModel : ViewModel() {

    private val mLoadingIndicatorSubject = BehaviorSubject.create<Boolean>()
    private val mSnackbarText = BehaviorSubject.create<Int>()

    fun getLoadingIndicatorVisibility(): Observable<Boolean> {
        return mLoadingIndicatorSubject
    }

    fun getSnackbarMessage(): Observable<Int> {
        return mSnackbarText
    }

    fun getContent(): Observable<List<File>> {
        return Observable.fromCallable { getContentFiles() }
            .doOnSubscribe { mLoadingIndicatorSubject.onNext(true) }
            .doOnNext { mLoadingIndicatorSubject.onNext(false) }
    }

    private fun getContentFiles(): List<File> {
        val files = mutableListOf<File>()
        val externalStorageDirectory = Environment.getExternalStorageDirectory()
        val kakaoTalkDirectory = File(externalStorageDirectory, "android/data/com.kakao.talk/contents")
        if (kakaoTalkDirectory.isDirectory && kakaoTalkDirectory.exists()) {
            getFiles(files, kakaoTalkDirectory)
        }
        return files
    }

    private fun getFiles(files: MutableList<File>, rootDirectory: File) {
        val listFiles = rootDirectory.listFiles()
        for (file in listFiles) {
            if (file.isDirectory) {
                getFiles(files, file)
            } else if (file.length() != 0L) {
                files.add(file)
            }
        }
    }
}