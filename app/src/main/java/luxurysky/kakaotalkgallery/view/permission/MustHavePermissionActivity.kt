package luxurysky.kakaotalkgallery.view.permission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.util.replaceFragmentInActivity

class MustHavePermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.must_have_permission_activity)
        if (savedInstanceState == null) {
            replaceFragmentInActivity(MustHavePermissionFragment.newInstance(), R.id.container)
        }
    }
}