package luxurysky.kakaotalkgallery.view.permission

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_must_have_permission.view.*
import luxurysky.kakaotalkgallery.R
import luxurysky.kakaotalkgallery.util.Constants
import luxurysky.kakaotalkgallery.util.finish
import luxurysky.kakaotalkgallery.view.gallery.GalleryListActivity
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MustHavePermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    companion object {
        private const val PERMISSION_REQUIRED = 0
        fun newInstance() = MustHavePermissionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_must_have_permission, container, false)

        view.confirmButton.setOnClickListener {
            if (EasyPermissions.hasPermissions(context!!, *Constants.REQUIRED_PERMISSIONS)) {
                checkPermissionCompleted()
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.required_permission_desc),
                    PERMISSION_REQUIRED,
                    *Constants.REQUIRED_PERMISSIONS
                )
            }
        }
        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .setTitle(R.string.setting_permission)
                .setRationale(R.string.setting_permission_desc)
                .setPositiveButton(R.string.setting)
                .setNegativeButton(R.string.cancel)
                .build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.hasPermissions(context!!, *Constants.REQUIRED_PERMISSIONS)) {
            checkPermissionCompleted()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(context!!, *Constants.REQUIRED_PERMISSIONS)) {
                checkPermissionCompleted()
            }
        }
    }

    private fun checkPermissionCompleted() {
        finish()
        startActivity(Intent(activity, GalleryListActivity::class.java))
    }
}
