package com.nativo.nativo_android_unifiedsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nativo.nativo_android_unifiedsample.NativeAdImpl.NativeAd;
import com.nativo.nativo_android_unifiedsample.NativeAdLandingImpl.NativeLandingPage;
import com.nativo.nativo_android_unifiedsample.NativeAdVideo.FullScreenVideoImpl;
import com.nativo.nativo_android_unifiedsample.NativeAdVideo.VideoImpl;
import com.nativo.nativo_android_unifiedsample.ViewFragment.GridFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.RecyclerViewFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.SingleViewFragment;
import com.nativo.nativo_android_unifiedsample.ViewFragment.TableFragment;

import net.nativo.sdk.NativoSDK;
import net.nativo.sdk.ntvcore.NtvAdData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentViewAdapter fragmentViewAdapter = new FragmentViewAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(fragmentViewAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        init();
    }

    private void init() {
        NativoSDK.getInstance().init(this);
        NativoSDK.getInstance().registerNativeAd(new NativeAd());
        NativoSDK.getInstance().registerLandingPage(new NativeLandingPage());
        NativoSDK.getInstance().registerVideoAd(new VideoImpl());
        NativoSDK.getInstance().registerFullscreenVideo(new FullScreenVideoImpl());
        NativoSDK.getInstance().enableTestAdvertisements(NtvAdData.NtvAdType.IN_FEED_VIDEO);
        NativoSDK.getInstance().enableDevLogs();
    }

    private class FragmentViewAdapter extends FragmentPagerAdapter {


        public FragmentViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new TableFragment();
                case 1:
                    return new GridFragment();
                case 2:
                    return new RecyclerViewFragment();
                case 3:
                    return new SingleViewFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.table_tab);
                case 1:
                    return getResources().getText(R.string.grid_tab);
                case 2:
                    return getResources().getText(R.string.recycle_list_tab);
                case 3:
                    return getResources().getText(R.string.single_view);
            }
            return null;
        }
    }
}
