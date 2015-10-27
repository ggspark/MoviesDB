package com.fastacash.moviesdb.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import com.fastacash.moviesdb.DataSingleton;
import com.fastacash.moviesdb.Fragments.FragmentSection1;
import com.fastacash.moviesdb.Fragments.FragmentSection2;
import com.fastacash.moviesdb.R;
import com.fastacash.moviesdb.controller.APIServices;
import com.fastacash.moviesdb.models.NowPlaying;
import com.fastacash.moviesdb.utils.Constant;
import com.fastacash.moviesdb.utils.QLog;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieListingActivity extends BaseActivity implements ActionBar.TabListener{

    public SwipeRefreshLayout swipeRefreshLayout;
    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_listing);
        fillUi();

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());


        // Set the Action Bar to use tabs for navigation
        final ActionBar ab = getSupportActionBar();
        ab.show();
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                ab.setSelectedNavigationItem(position);
                if (position == 1) {
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            ab.addTab(ab.newTab().setText(mAppSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }

        queryServer();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void fillUi() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(
                R.color.accent, R.color.primary,
                R.color.primary_dark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryServer();
            }
        });

    }

    synchronized void queryServer() {
        swipeRefreshLayout.setRefreshing(true);
        APIServices.getMovieService().getNowPlaying(Constant.API_KEY, 1, new Callback<NowPlaying>() {
            @Override
            public void success(NowPlaying nowPlaying, Response response) {
                DataSingleton.getInstance().getNowPlayingMovies().clear();
                DataSingleton.getInstance().getNowPlayingMovies().addAll(nowPlaying.getResults());
                FragmentSection1.getInstance().notifyDataSetChanged();
                // Stop the refreshing indicator
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                // Stop the refreshing indicator
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a tab is selected.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is unselected.
    }

    // Implemented from ActionBar.TabListener
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // This is called when a previously selected tab is selected again.
    }


    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app
                    return FragmentSection1.getInstance();

                case 1:
                    // The Second section of the app
                    return FragmentSection2.getInstance();

                default:
                    return FragmentSection1.getInstance();

            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Now Showing";
                case 1:
                    return "Favourites";
                default:
                    return ("Section" + (position + 1));
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        return true;
    }

}
