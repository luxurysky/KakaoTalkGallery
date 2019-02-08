package luxurysky.kakaotalkgallery.view.contents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.dummy.DummyContent
import luxurysky.kakaotalkgallery.util.replaceFragmentInActivity

class ContentsActivity : AppCompatActivity(), ContentsFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents)

        if (savedInstanceState == null) {
            replaceFragmentInActivity(ContentsFragment.newInstance(4), R.id.container)
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        //todo show viewpager
    }
}