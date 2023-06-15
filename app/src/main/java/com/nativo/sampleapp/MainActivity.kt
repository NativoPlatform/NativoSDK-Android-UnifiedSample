package com.nativo.sampleapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nativo.sampleapp.NativoAds.NativeAd
import com.nativo.sampleapp.NativoAds.NativeVideoAd
import com.nativo.sampleapp.NativoAds.StandardDisplayAd
import com.nativo.sampleapp.databinding.ActivityMainBinding
import net.nativo.sdk.NativoSDK

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.pager.adapter = pagerAdapter


        supportActionBar?.let {
            it.setLogo(R.drawable.favicon)
            it.setDisplayUseLogoEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        // Nativo Setup
        NativoSDK.enableDevLogs()
        NativoSDK.registerClassForLandingPage(SponsoredContentActivity::class.java)
        NativoSDK.registerClassForNativeAd(NativeAd::class.java)
        NativoSDK.registerClassForVideoAd(NativeVideoAd::class.java)
        NativoSDK.registerClassForStandardDisplayAd(StandardDisplayAd::class.java)
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        var currentFragment: Fragment? = null

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RecylerListFragment()
                else -> AdTypesFragment()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reload -> {
                reloadAds()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun reloadAds() {
//        val fragment = binding.pager.adapter?.
//        fragment.reloadAds()
    }
}