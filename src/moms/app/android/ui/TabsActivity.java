package moms.app.android.ui;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import moms.app.android.R;

/**
 * Created by Steve on 5/4/14.
 */
public class TabsActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //enable indefinite progress bar in action bar (can be turned on and off)
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.tabs_activity);

        setProgressBarIndeterminateVisibility(false);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        final ActionBar actionBar = getActionBar();
        final TabsPagerAdapter mPagerAdapter = new TabsPagerAdapter((getSupportFragmentManager()));
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
        actionBar.setLogo(R.drawable.action_bar_logo);
        actionBar.setTitle("");

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mPagerAdapter.getPageTitle(i))
                    .setTabListener(new ActionBar.TabListener() {
                        @Override
                        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            mViewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            //hide soft keyboard
                            Activity currentActivity = TabsActivity.this;
                            InputMethodManager inputManager = (InputMethodManager) currentActivity
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(currentActivity.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            return;
                        }

                        @Override
                        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                            return;
                        }
                    }));
        }
    }

    private class TabsPagerAdapter extends FragmentPagerAdapter {
        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case(0):
                    return new HomeFragment();
                case(1):
                    return new PostPollFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Polls";
                case 1:
                    return "Create";
                default:
                    return null;
            }
        }
    }
}