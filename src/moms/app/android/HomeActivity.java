package moms.app.android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.zip.Inflater;

public class HomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // add fragments to activity
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.main_fragment, new HomeFragment(), "Home").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_activity_menu, menu);
        return true;
    }

    public void homeClick(MenuItem item){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        ft.replace(R.id.main_fragment, homeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void loginClick(MenuItem item){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Login loginFragment = new Login();
        ft.replace(R.id.main_fragment, loginFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void extraButton(MenuItem item){
        Toast.makeText(getApplicationContext(), "Third button pressed", Toast.LENGTH_SHORT).show();
    }


}
