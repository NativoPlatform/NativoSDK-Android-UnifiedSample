package com.nativo.sampleapp.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import com.nativo.sampleapp.NativeAdImpl.NativeAd
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAd
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAd
import com.nativo.sampleapp.NativeAdVideo.FullScreenVideoImpl
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewFragment.*
import com.nativo.sampleapp.databinding.ActivityMainBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK

/**
 * Various of Fragment Types
 */
internal enum class NtvFragmentType {
    RECYCLE_LIST,
    GRID,
    TABLE,
    SINGLEVIEW,
    SINGLEVIEW_VIDEO,
    GAM_INTEGRATION,
    MIDDLE_OF_ARTICLE
}

/**
 * Main Activity
 */
class MainActivity : AppCompatActivity() {
    companion object {
        const val DELAY_DURATION = 2000L
    }

    // Binding variable to inflate layout
    private lateinit var binding: ActivityMainBinding

    // Main fragment type (NtvFragmentType)
    private var mainFragmentType: NtvFragmentType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        nativoInit()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            setContentView(binding.root)

            // Setup view pager and tab layout
            binding.pager.offscreenPageLimit = 0
            binding.tabs.setupWithViewPager(binding.pager)

            // Set desired fragment for app
            setMainFragment(NtvFragmentType.RECYCLE_LIST)
        }, DELAY_DURATION)
    }

    private fun nativoInit() {
        // Register the class that will be used for Nativo Content Landing Page
        NativoSDK.registerClassForLandingPage(SponsoredContentActivity::class.java)
        NativoSDK.registerClassForNativeAd(NativeAd::class.java)
        NativoSDK.registerClassForVideoAd(NativeVideoAd::class.java)
        NativoSDK.registerClassForStandardDisplayAd(StandardDisplayAd::class.java)
        NativoSDK.registerFullscreenVideo(FullScreenVideoImpl())

        // Force specific ad types if needed
        NativoSDK.enableTestAdvertisements()
        NativoSDK.enableDevLogs()
    }

    private inner class FragmentViewAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm) {
        override fun getItem(i: Int): Fragment {
            return when (mainFragmentType) {
                NtvFragmentType.RECYCLE_LIST -> RecyclerViewFragment()
                NtvFragmentType.GRID -> GridFragment()
                NtvFragmentType.TABLE -> ListViewFragment()
                NtvFragmentType.SINGLEVIEW -> SingleViewFragment()
                NtvFragmentType.SINGLEVIEW_VIDEO -> SingleViewVideoFragment()
                NtvFragmentType.GAM_INTEGRATION -> DfpFragment()
                NtvFragmentType.MIDDLE_OF_ARTICLE -> MOAPFragment()
                else -> MOAPFragment()
            }
        }

        override fun getCount(): Int {
            return 1
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (mainFragmentType) {
                NtvFragmentType.RECYCLE_LIST -> resources.getText(R.string.recycle_list_tab)
                NtvFragmentType.GRID -> resources.getText(R.string.grid_tab)
                NtvFragmentType.TABLE -> resources.getText(R.string.table_tab)
                NtvFragmentType.SINGLEVIEW -> resources.getText(R.string.single_view)
                NtvFragmentType.SINGLEVIEW_VIDEO -> resources.getText(R.string.single_view_video)
                NtvFragmentType.GAM_INTEGRATION -> resources.getText(R.string.gam)
                NtvFragmentType.MIDDLE_OF_ARTICLE -> resources.getText(R.string.moap)
                else -> null
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
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

    private fun setPrivacyAndTransparencyKeys() {
        getEditor().apply {
            putString(
                AppConstants.GDPR_SHARED_PREFERENCE_STRING,
                AppConstants.SAMPLE_GDPR_CONSENT
            )
            putString(
                AppConstants.CCPA_SHARED_PREFERENCE_STRING,
                AppConstants.SAMPLE_CCPA_VALID_CONSENT
            )
            apply()
        }
    }

    private fun invalidPrivacyAndTransparencyKeys() {
        getEditor().apply {
            putString(
                AppConstants.GDPR_SHARED_PREFERENCE_STRING,
                AppConstants.SAMPLE_GDPR_INVALID_CONSENT
            )
            putString(
                AppConstants.CCPA_SHARED_PREFERENCE_STRING,
                AppConstants.SAMPLE_CCPA_INVALID_CONSENT
            )
            apply()
        }
    }

    private fun removePrivacyAndTransparencyKeys() {
        getEditor().apply {
            remove(AppConstants.GDPR_SHARED_PREFERENCE_STRING)
            remove(AppConstants.CCPA_SHARED_PREFERENCE_STRING)
            apply()
        }
    }

    private fun setMainFragment(fragmentType: NtvFragmentType) {
        mainFragmentType = fragmentType
        binding.pager.adapter = FragmentViewAdapter(supportFragmentManager)
    }

    /**
     * @return Editor of Preference Manager instance
     */
    private fun getEditor() = PreferenceManager.getDefaultSharedPreferences(this).edit()
}