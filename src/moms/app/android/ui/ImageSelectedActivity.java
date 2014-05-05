package moms.app.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import moms.app.android.R;

/**
 * Created by Steve on 4/27/14.
 */
public class ImageSelectedActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.image_selected_activty);
    }
}