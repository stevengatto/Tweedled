package moms.app.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import moms.app.android.R;

/**
 * Created by Steve on 4/26/14.
 */
public class ImageSearchActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.image_search_activity);
    }
}