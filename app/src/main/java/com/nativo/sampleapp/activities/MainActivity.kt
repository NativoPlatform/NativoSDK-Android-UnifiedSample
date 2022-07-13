package com.nativo.sampleapp.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
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

class MainActivity : AppCompatActivity(), NtvSectionAdapter {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    private val tabTitles = listOf(
        R.string.recycle_list_tab,
        R.string.grid_tab,
        R.string.table_tab,
        R.string.single_view,
        R.string.single_view_video,
        R.string.gam,
        R.string.moap
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        nativoInit()
        //NativoSDK.prefetchAdForSection(SECTION_URL, this, null);

        setContentView(binding.root)

        binding.pager.apply {
            adapter = FragmentViewAdapter(this@MainActivity)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.setText(tabTitles[position])
        }.attach()
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

    private inner class FragmentViewAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return tabTitles.count()
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RecyclerViewFragment()
                1 -> GridFragment()
                2 -> ListViewFragment()
                3 -> SingleViewFragment()
                4 -> SingleViewVideoFragment()
                5 -> DfpFragment()
                6 -> MOAPFragment()
                else -> RecyclerViewFragment()
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