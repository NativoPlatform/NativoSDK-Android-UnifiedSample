package com.nativo.sampleapp

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar.*
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.nativo.sampleapp.NativoAds.NativeAd
import com.nativo.sampleapp.NativoAds.NativeVideoAd
import com.nativo.sampleapp.NativoAds.StandardDisplayAd
import com.nativo.sampleapp.databinding.ActivityMainBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nativo Setup
        NativoSDK.enableDevLogs()
        NativoSDK.enableTestAdvertisements()
        NativoSDK.registerClassForLandingPage(SponsoredContentActivity::class.java)
        NativoSDK.registerClassForNativeAd(NativeAd::class.java)
        NativoSDK.registerClassForVideoAd(NativeVideoAd::class.java)
        NativoSDK.registerClassForStandardDisplayAd(StandardDisplayAd::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            it.setLogo(R.drawable.favicon)
            it.setDisplayUseLogoEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.yes -> {
                setPrivacyAndTransparencyKeys()
                true
            }
            R.id.no -> {
                removePrivacyAndTransparencyKeys()
                true
            }
            R.id.invalid -> {
                invalidPrivacyAndTransparencyKeys()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val editor: SharedPreferences.Editor
        get() {
            val preferenceManager = PreferenceManager.getDefaultSharedPreferences(this)
            return preferenceManager.edit()
        }

    private fun setPrivacyAndTransparencyKeys() {
        val editor = editor
        editor.putString(AppConstants.GDPR_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_GDPR_CONSENT)
        editor.putString(AppConstants.CCPA_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_CCPA_VALID_CONSENT)
        editor.apply()
    }

    private fun invalidPrivacyAndTransparencyKeys() {
        val editor = editor
        editor.putString(AppConstants.GDPR_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_GDPR_INVALID_CONSENT)
        editor.putString(AppConstants.CCPA_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_CCPA_INVALID_CONSENT)
        editor.apply()
    }

    private fun removePrivacyAndTransparencyKeys() {
        val editor = editor
        editor.remove(AppConstants.GDPR_SHARED_PREFERENCE_STRING)
        editor.remove(AppConstants.CCPA_SHARED_PREFERENCE_STRING)
        editor.apply()
    }
}