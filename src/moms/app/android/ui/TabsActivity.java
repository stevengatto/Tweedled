package moms.app.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import moms.app.android.R;
import moms.app.android.communication.WebGeneral;
import moms.app.android.login.Login;

/**
 * Created by Steve on 5/4/14.
 */
public class TabsActivity extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tabs_activity);

        getActionBar().setHomeButtonEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabs_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_login:
                try {
                    if(!WebGeneral.getsPreferences().getString("auth_token", "").isEmpty()){
                        Toast.makeText(TabsActivity.this, "You are already logged in", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } catch (NullPointerException e) {
                    Intent i = new Intent(TabsActivity.this, Login.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                }
            case R.id.menu_refresh:
                Intent i = new Intent(TabsActivity.this, TabsActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            default:
                return false;
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