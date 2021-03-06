package luxurysky.kakaotalkgallery.view.intro

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.util.Constants
import luxurysky.kakaotalkgallery.util.startActivity
import luxurysky.kakaotalkgallery.view.contents.ContentsActivity
import luxurysky.kakaotalkgallery.view.permission.MustHavePermissionActivity
import pub.devrel.easypermissions.EasyPermissions

class IntroActivity : AppCompatActivity() {

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        mHandler.postDelayed({
            if (EasyPermissions.hasPermissions(this, *Constants.REQUIRED_PERMISSIONS)) {
                startActivity<ContentsActivity>()
            } else {
                startActivity<MustHavePermissionActivity>()
            }
            finish()
        }, 1000)
    }

    override fun onBackPressed() {
        // nothing
    }
}
