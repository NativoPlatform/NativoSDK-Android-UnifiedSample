package com.nativo.sampleapp.activities;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nativo.sampleapp.NativeAdImpl.NativeAd;
import com.nativo.sampleapp.NativeAdImpl.NativeVideoAd;
import com.nativo.sampleapp.NativeAdImpl.StandardDisplayAd;
import com.nativo.sampleapp.NativeAdVideo.FullScreenVideoImpl;
import com.nativo.sampleapp.R;
import com.nativo.sampleapp.ViewFragment.DfpFragment;
import com.nativo.sampleapp.ViewFragment.GridFragment;
import com.nativo.sampleapp.ViewFragment.ListViewFragment;
import com.nativo.sampleapp.ViewFragment.MOAPFragment;
import com.nativo.sampleapp.ViewFragment.RecyclerViewFragment;
import com.nativo.sampleapp.ViewFragment.SingleViewFragment;
import com.nativo.sampleapp.ViewFragment.SingleViewVideoFragment;

import net.nativo.sdk.NativoSDK;

import static com.nativo.sampleapp.util.AppConstants.GDPR_SHARED_PREFERENCE_STRING;
import static com.nativo.sampleapp.util.AppConstants.CCPA_SHARED_PREFERENCE_STRING;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_CCPA_INVALID_CONSENT;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_CCPA_VALID_CONSENT;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_GDPR_CONSENT;
import static com.nativo.sampleapp.util.AppConstants.SAMPLE_GDPR_INVALID_CONSENT;


enum NtvFragmentType {
    RECYCLE_LIST,
    GRID,
    TABLE,
    SINGLEVIEW,
    SINGLEVIEW_VIDEO,
    GAM_INTEGRATION,
    MIDDLE_OF_ARTICLE
}

public class MainActivity extends AppCompatActivity {

    NtvFragmentType fragmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nativoInit();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set desired fragment for app
                setMainFragment(NtvFragmentType.RECYCLE_LIST);

                setContentView(R.layout.activity_main);
                FragmentViewAdapter fragmentViewAdapter = new FragmentViewAdapter(getSupportFragmentManager());
                ViewPager viewPager = findViewById(R.id.pager);
                viewPager.setAdapter(fragmentViewAdapter);
                viewPager.setOffscreenPageLimit(0);
                TabLayout tabLayout = findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);


            }
        }, 2000);
    }

    private void setPrivacyAndTransparencyKeys() {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(GDPR_SHARED_PREFERENCE_STRING, SAMPLE_GDPR_CONSENT);
        editor.putString(CCPA_SHARED_PREFERENCE_STRING, SAMPLE_CCPA_VALID_CONSENT);
        editor.apply();
    }

    private void nativoInit() {

        // Register the class that will be used for Nativo Content Landing Page
        NativoSDK.registerClassForLandingPage(SponsoredContentActivity.class);
        NativoSDK.registerClassForNativeAd(NativeAd.class);
        NativoSDK.registerClassForVideoAd(NativeVideoAd.class);
        NativoSDK.registerClassForStandardDisplayAd(StandardDisplayAd.class);
        NativoSDK.registerFullscreenVideo(new FullScreenVideoImpl());

        // Force specific ad types if needed
        NativoSDK.enableTestAdvertisements();
        NativoSDK.enableDevLogs();
    }

    private void setMainFragment(NtvFragmentType fragmentType) {
        this.fragmentType = fragmentType;
    }

    private NtvFragmentType getMainFragment() {
        return this.fragmentType;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        NativoSDK.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NativoSDK.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NativoSDK.onResume();
    }

    private class FragmentViewAdapter extends FragmentPagerAdapter {


        public FragmentViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (getMainFragment()) {
                case RECYCLE_LIST:
                    return new RecyclerViewFragment();
                case GRID:
                    return new GridFragment();
                case TABLE:
                    return new ListViewFragment();
                case SINGLEVIEW:
                    return new SingleViewFragment();
                case SINGLEVIEW_VIDEO:
                    return new SingleViewVideoFragment();
                case GAM_INTEGRATION:
                    return new DfpFragment();
                case MIDDLE_OF_ARTICLE:
                    return new MOAPFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (getMainFragment()) {
                case RECYCLE_LIST:
                    return getResources().getText(R.string.recycle_list_tab);
                case GRID:
                    return getResources().getText(R.string.grid_tab);
                case TABLE:
                    return getResources().getText(R.string.table_tab);
                case SINGLEVIEW:
                    return getResources().getText(R.string.single_view);
                case SINGLEVIEW_VIDEO:
                    return getResources().getText(R.string.single_view_video);
                case GAM_INTEGRATION:
                    return getResources().getText(R.string.gam);
                case MIDDLE_OF_ARTICLE:
                    return getResources().getText(R.string.moap);
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.yes:
                setPrivacyAndTransparencyKeys();
                return true;
            case R.id.no:
                removePrivacyAndTransparencyKeys();
                return true;
            case R.id.invalid:
                invalidPrivacyAndTransparencyKeys();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void invalidPrivacyAndTransparencyKeys() {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(GDPR_SHARED_PREFERENCE_STRING, SAMPLE_GDPR_INVALID_CONSENT);
        editor.putString(CCPA_SHARED_PREFERENCE_STRING, SAMPLE_CCPA_INVALID_CONSENT);
        editor.apply();

    }

    private SharedPreferences.Editor getEditor() {
        SharedPreferences preferenceManager = PreferenceManager.getDefaultSharedPreferences(this);
        return preferenceManager.edit();
    }

    private void removePrivacyAndTransparencyKeys() {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(GDPR_SHARED_PREFERENCE_STRING);
        editor.remove(CCPA_SHARED_PREFERENCE_STRING);
        editor.apply();
    }
}
