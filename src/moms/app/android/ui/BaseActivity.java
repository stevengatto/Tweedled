package moms.app.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import moms.app.android.R;

/**
 * Created by Steve on 4/3/14.
 */
public class BaseActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setLogo(R.drawable.action_bar_logo);
        actionBar.setTitle("");
    }

    //Inflate menu from xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    /**
     * Handles click events of items in menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_polls:
                pollClick(item);
                return true;
            case R.id.menu_login:
                loginClick(item);
                return true;
            case R.id.menu_favorites:
                favoritesClick(item);
                return true;
            case R.id.menu_post:
                postClick(item);
                return true;
            case R.id.menu_settings:
                settingsClick(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void pollClick(MenuItem item){
        //launch home activity
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void loginClick(MenuItem item){
        //launch login activity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void favoritesClick(MenuItem item){
        Toast.makeText(getApplicationContext(), "Favorites button pressed", Toast.LENGTH_SHORT).show();
    }

    public void postClick(MenuItem item){
        Toast.makeText(getApplicationContext(), "Post button pressed", Toast.LENGTH_SHORT).show();
    }

    public void settingsClick(MenuItem item){
        Toast.makeText(getApplicationContext(), "Settings button pressed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Converts an intent into a {@link Bundle} suitable for use as fragment arguments.
     */
    protected static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }

    /**
     * Converts a fragment arguments bundle into an intent.
     */
    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable("_uri");
        if (data != null) {
            intent.setData(data);
        }

        intent.putExtras(arguments);
        intent.removeExtra("_uri");
        return intent;
    }
}