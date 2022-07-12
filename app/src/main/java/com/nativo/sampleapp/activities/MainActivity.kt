package com.nativo.sampleapp.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import com.nativo.sampleapp.NativeAdImpl.NativeAd
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAd
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAd
import com.nativo.sampleapp.NativeAdLandingImpl.NativeLandingPage
import com.nativo.sampleapp.NativeAdVideo.FullScreenVideoImpl
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewFragment.*
import com.nativo.sampleapp.databinding.ActivityMainBinding
import com.nativo.sampleapp.util.AppConstants
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.ntvadtype.NtvBaseInterface
import net.nativo.sdk.ntvconstant.NtvConstants
import net.nativo.sdk.ntvcore.NtvAdData
import net.nativo.sdk.ntvcore.NtvAdData.NtvAdTemplateType
import net.nativo.sdk.ntvcore.NtvSectionAdapter

internal enum class NtvFragmentType {
    RECYCLE_LIST, GRID, TABLE, SINGLEVIEW, SINGLEVIEW_VIDEO, GAM_INTEGRATION, MIDDLE_OF_ARTICLE
}

class MainActivity : AppCompatActivity(), NtvSectionAdapter {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private var mainFragment: NtvFragmentType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        nativoInit()
        //NativoSDK.prefetchAdForSection(SECTION_URL, this, null);

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ // Set desired fragment for app
            setMainFragment(NtvFragmentType.RECYCLE_LIST)
            setContentView(binding.root)

            binding.pager.apply {
                adapter = FragmentViewAdapter(supportFragmentManager)
                offscreenPageLimit = 0
            }

            binding.tabs.setupWithViewPager(binding.pager)
        }, 2000)
    }

    private fun setPrivacyAndTransparencyKeys() {
        val editor = editor
        editor.putString(NtvConstants.GDPR_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_GDPR_CONSENT)
        editor.putString(NtvConstants.CCPA_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_CCPA_VALID_CONSENT)
        editor.apply()
    }

    private fun nativoInit() {
        NativoSDK.init(this)
        NativoSDK.registerNativeAd(NativeAd())
        NativoSDK.registerLandingPage(NativeLandingPage())
        NativoSDK.registerVideoAd(NativeVideoAd())
        NativoSDK.registerFullscreenVideo(FullScreenVideoImpl())
        NativoSDK.registerStandardDisplayAd(StandardDisplayAd())
        NativoSDK.enableDevLogs()

        // Force specific ad types if needed
        NativoSDK.enableTestAdvertisements(NtvAdData.NtvAdType.IN_FEED_AUTO_PLAY_VIDEO)
    }

    private fun setMainFragment(fragmentType: NtvFragmentType) {
        mainFragment = fragmentType
    }

    private inner class FragmentViewAdapter(fm: FragmentManager) : FragmentPagerAdapter(
        fm
    ) {
        override fun getItem(i: Int): Fragment {
            return when (mainFragment) {
                NtvFragmentType.RECYCLE_LIST -> RecyclerViewFragment()
                NtvFragmentType.GRID -> GridFragment()
                NtvFragmentType.TABLE -> ListViewFragment()
                NtvFragmentType.SINGLEVIEW -> SingleViewFragment()
                NtvFragmentType.SINGLEVIEW_VIDEO -> SingleViewVideoFragment()
                NtvFragmentType.GAM_INTEGRATION -> DfpFragment()
                else -> MOAPFragment()
            }
        }

        override fun getCount(): Int {
            return 1
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (mainFragment) {
                NtvFragmentType.RECYCLE_LIST -> resources.getText(R.string.recycle_list_tab)
                NtvFragmentType.GRID -> resources.getText(R.string.grid_tab)
                NtvFragmentType.TABLE -> resources.getText(R.string.table_tab)
                NtvFragmentType.SINGLEVIEW -> resources.getText(R.string.single_view)
                NtvFragmentType.SINGLEVIEW_VIDEO -> resources.getText(R.string.single_view_video)
                NtvFragmentType.GAM_INTEGRATION -> resources.getText(R.string.gam)
                else -> resources.getText(R.string.moap)
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

    private fun invalidPrivacyAndTransparencyKeys() {
        val editor = editor
        editor.putString(NtvConstants.GDPR_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_GDPR_INVALID_CONSENT)
        editor.putString(NtvConstants.CCPA_SHARED_PREFERENCE_STRING, AppConstants.SAMPLE_CCPA_INVALID_CONSENT)
        editor.apply()
    }

    private val editor: SharedPreferences.Editor
        get() {
            val preferenceManager = PreferenceManager.getDefaultSharedPreferences(this)
            return preferenceManager.edit()
        }

    private fun removePrivacyAndTransparencyKeys() {
        val editor = editor
        editor.remove(NtvConstants.GDPR_SHARED_PREFERENCE_STRING)
        editor.remove(NtvConstants.CCPA_SHARED_PREFERENCE_STRING)
        editor.apply()
    }

    /**
     *
     * THIS IS NATIVE SECTION ADAPTER INTERFACE
     */
    override fun registerLayoutClassForIndex(
        i: Int,
        ntvAdTemplateType: NtvAdTemplateType
    ): Class<*>? {
        return null
    }

    override fun needsDisplayLandingPage(s: String, i: Int) {}
    override fun needsDisplayClickOutURL(s: String, s1: String) {}
    override fun hasbuiltView(
        view: View,
        ntvBaseInterface: NtvBaseInterface,
        ntvAdData: NtvAdData
    ) {
    }

    override fun onReceiveAd(s: String, ntvAdData: NtvAdData, index: Int) {
        Log.e(TAG, "Did receive ad at index: $index")
    }

    override fun onFail(s: String, integer: Int) {}
}