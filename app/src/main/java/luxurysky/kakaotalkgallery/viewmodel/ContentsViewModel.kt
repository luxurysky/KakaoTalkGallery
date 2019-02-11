package luxurysky.kakaotalkgallery.viewmodel

import android.os.Environment
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import luxurysky.kakaotalkgallery.util.Constants
import java.io.File


class ContentsViewModel : ViewModel() {

    private val mLoadingIndicatorSubject = BehaviorSubject.create<Boolean>()
    private val mSnackbarText = BehaviorSubject.create<Int>()

    fun getLoadingIndicatorVisibility(): Observable<Boolean> {
        return mLoadingIndicatorSubject
    }

    fun getSnackbarMessage(): Observable<Int> {
        return mSnackbarText
    }

    fun getContents(): Observable<List<File>> {
        return Observable.fromCallable { getContentFiles() }
            .doOnSubscribe { mLoadingIndicatorSubject.onNext(true) }
            .doOnNext { mLoadingIndicatorSubject.onNext(false) }
    }

    private fun getContentFiles(): List<File> {
        val files = mutableListOf<File>()
        val externalStorageDirectory = Environment.getExternalStorageDirectory()
        val kakaoTalkDirectory = File(externalStorageDirectory, Constants.KAKAO_TALK_CONTENTS_PATH)
        if (kakaoTalkDirectory.isDirectory && kakaoTalkDirectory.exists()) {
            getFiles(files, kakaoTalkDirectory)
        }
        return files.sortedByDescending { it.lastModified() }
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