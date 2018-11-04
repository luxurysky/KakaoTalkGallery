package luxurysky.kakaotalkgallery.view.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.dummy.DummyContent
import luxurysky.kakaotalkgallery.util.replaceFragmentInActivity

class GalleryListActivity : AppCompatActivity(), GalleryListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_list)

        if (savedInstanceState == null) {
            replaceFragmentInActivity(GalleryListFragment.newInstance(4), R.id.container)
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {

    }
}