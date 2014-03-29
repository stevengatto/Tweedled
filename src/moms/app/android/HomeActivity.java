package moms.app.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_activity_menu, menu);
        return true;
    }

    public void homeClick(MenuItem item){
        Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }

    public void loginClick(MenuItem item){
        Intent myIntent = new Intent(HomeActivity.this, Login.class);
        HomeActivity.this.startActivity(myIntent);
    }

    public void extraButton(MenuItem item){
        Toast.makeText(getApplicationContext(), "Third button pressed", Toast.LENGTH_SHORT).show();
    }
}
