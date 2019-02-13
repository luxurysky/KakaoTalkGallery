package luxurysky.kakaotalkgallery.model

import android.os.Environment
import io.reactivex.Observable
import luxurysky.kakaotalkgallery.util.Constants
import java.io.File

object ContentsRepository {

    private val mCachedContents = mutableListOf<Content>()

    fun getContents(): Observable<List<Content>> {
        mCachedContents.clear()
        val externalStorageDirectory = Environment.getExternalStorageDirectory()
        val kakaoTalkDirectory = File(externalStorageDirectory, Constants.KAKAO_TALK_CONTENTS_PATH)
        if (kakaoTalkDirectory.isDirectory && kakaoTalkDirectory.exists()) {
            getFiles(mCachedContents, kakaoTalkDirectory)
        }
        return Observable.fromArray(mCachedContents)
    }

    private fun getFiles(files: MutableList<Content>, rootDirectory: File) {
        val listFiles = rootDirectory.listFiles()
        for (file in listFiles) {
            if (file.isDirectory) {
                getFiles(files, file)
            } else if (file.length() != 0L) {
                files.add(Content(file.path, file.lastModified(), file.length()))
            }
        }
    }
}