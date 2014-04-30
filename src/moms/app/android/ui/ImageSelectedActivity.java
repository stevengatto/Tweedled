package moms.app.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import moms.app.android.R;

/**
 * Created by Steve on 4/27/14.
 */
public class ImageSelectedActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
        actionBar.setLogo(R.drawable.action_bar_logo);
        actionBar.setTitle("");
        setContentView(R.layout.image_selected_activty);
    }
}