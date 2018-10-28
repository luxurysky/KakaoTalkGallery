package luxurysky.kakaotalkgallery.view.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.dummy.DummyContent
import luxurysky.kakaotalkgallery.util.replaceFragmentInActivity

class GalleryActivity : AppCompatActivity(), GalleryFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        if (savedInstanceState == null) {
            replaceFragmentInActivity(GalleryFragment.newInstance(4), R.id.container)
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {

    }
}
