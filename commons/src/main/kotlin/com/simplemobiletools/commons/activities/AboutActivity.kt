package com.simplemobiletools.commons.activities

import android.content.Intent
import android.os.Bundle
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.*
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseSimpleActivity() {
    private var appName = ""
    private var primaryColor = 0

    override fun getAppIconIDs() = intent.getIntegerArrayListExtra(APP_ICON_IDS) ?: ArrayList()

    override fun getAppLauncherName() = intent.getStringExtra(APP_LAUNCHER_NAME) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        appName = intent.getStringExtra(APP_NAME) ?: ""
        val textColor = getProperTextColor()
        val backgroundColor = getProperBackgroundColor()
        primaryColor = getProperPrimaryColor()

        arrayOf(
            about_licenses_icon,
            about_version_icon
        ).forEach {
            it.applyColorFilter(textColor)
        }

        arrayOf(about_other_holder).forEach {
            it.background.applyColorFilter(backgroundColor.getContrastColor())
        }
    }

    override fun onResume() {
        super.onResume()
        updateTextColors(about_nested_scrollview)
        setupToolbar(about_toolbar, NavigationIcon.Arrow)

        setupLicense()
        setupVersion()
    }

    private fun setupLicense() {
        about_licenses_holder.setOnClickListener {
            Intent(applicationContext, LicenseActivity::class.java).apply {
                putExtra(APP_ICON_IDS, getAppIconIDs())
                putExtra(APP_LAUNCHER_NAME, getAppLauncherName())
                putExtra(APP_LICENSES, intent.getLongExtra(APP_LICENSES, 0))
                startActivity(this)
            }
        }
    }

    private fun setupVersion() {
        var version = intent.getStringExtra(APP_VERSION_NAME) ?: ""
        if (baseConfig.appId.removeSuffix(".debug").endsWith(".pro")) {
            version += " ${getString(R.string.pro)}"
        }

        val fullVersion = String.format(getString(R.string.version_placeholder, version))
        about_version.text = fullVersion
    }
}
