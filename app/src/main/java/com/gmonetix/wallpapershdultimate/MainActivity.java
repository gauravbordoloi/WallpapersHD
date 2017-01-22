package com.gmonetix.wallpapershdultimate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionMenu;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionMenu fabMore;
    private com.github.clans.fab.FloatingActionButton fab_settings ,fab_camera;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MobileAds.initialize(getApplicationContext(),getResources().getString(R.string.ad1_unit_id_MainActivity));
        adView = (AdView) findViewById(R.id.adViewMainActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        final Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText("Wallpapers HD");
        font.setFont(getApplicationContext(), toolBarTextView);

        fabMore = (FloatingActionMenu) findViewById(R.id.fab_more);
        fab_settings = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_settings);
        fab_camera = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_camera);

        fabMore.setClosedOnTouchOutside(true);
        fab_settings.setOnClickListener(this);
        fab_camera.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Categories(),getResources().getString(R.string.categories_tab));
        adapter.addFragment(new ColoursPrimary(), getResources().getString(R.string.colors_tab));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_settings:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
            case R.id.fab_camera:
                startActivity(new Intent(getApplicationContext(),Camera.class));
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.visit_our_site:
                    startActivity(openSite(getApplicationContext()));
                break;
            case R.id.like_our_fb_page:
                    startActivity(openFacebook(getApplicationContext()));
                break;
            case R.id.subscribe_our_chanel:
                    startActivity(openYoutube(getApplicationContext()));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static Intent openFacebook(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/122591891284060"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://facebook.com/gmonetix"));
        }
    }

    public static Intent openYoutube(Context context){
        try {
            context.getPackageManager().getPackageInfo("com.google.android.youtube",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCpfLFzQqRl01sXP7dxIQ_rg"));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/channel/UCpfLFzQqRl01sXP7dxIQ_rg"));
        }
    }

    public static Intent openSite(Context context){
        try {
            return new Intent(context.getPackageManager().getLaunchIntentForPackage("com.gmonetix.gmonetix").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (Exception e){
            return new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.gmonetix.com"));
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}

