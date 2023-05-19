package com.nativo.sampleapp.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.nativo.sampleapp.NativoAds.NativeAd
import com.nativo.sampleapp.NativoAds.NativeVideoAd
import com.nativo.sampleapp.NativoAds.StandardDisplayAd
import com.nativo.sampleapp.R
import com.nativo.sampleapp.ViewFragment.*
import com.nativo.sampleapp.databinding.ActivityMainBinding
import com.nativo.sampleapp.util.AppConstants
import com.nativo.sampleapp.util.Reloadable
import net.nativo.sdk.NativoSDK
import net.nativo.sdk.NtvTestAdType

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val tabTitles = listOf(
        R.string.recycle_list_tab,
        R.string.grid_tab,
        R.string.table_tab,
        R.string.single_view,
        R.string.gam,
        R.string.moap
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nativo Setup
        NativoSDK.enableDevLogs()
        NativoSDK.enableTestAdvertisements(NtvTestAdType.VIDEO_SCROLL_TO_PLAY)
        NativoSDK.registerClassForLandingPage(SponsoredContentActivity::class.java)
        NativoSDK.registerClassForNativeAd(NativeAd::class.java)
        NativoSDK.registerClassForVideoAd(NativeVideoAd::class.java)
        NativoSDK.registerClassForStandardDisplayAd(StandardDisplayAd::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.apply {
            adapter = FragmentViewAdapter(this@MainActivity)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.setText(tabTitles[position])
        }.attach()
    }

    private inner class FragmentViewAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        var currentItem : Fragment? = null

        override fun getItemCount(): Int {
            return tabTitles.count()
        }

        override fun createFragment(position: Int): Fragment {
            currentItem = when (position) {
                0 -> RecyclerViewFragment()
                1 -> GridFragment()
                2 -> ListViewFragment()
                3 -> SingleViewFragment()
                4 -> GamFragment()
                5 -> MOAPFragment()
                else -> RecyclerViewFragment()
            }
            return currentItem!!
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
            R.id.reload -> {
                reloadAds()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun reloadAds() {
        val adapter = binding.pager.adapter as FragmentViewAdapter
        val fragment = adapter.currentItem
        if (fragment is Reloadable) {
            fragment.reload()
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