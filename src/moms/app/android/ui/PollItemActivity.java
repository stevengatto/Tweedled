package moms.app.android.ui;

import android.os.Bundle;
import moms.app.android.R;

/**
 * Created by Steve on 4/4/14.
 */
public class PollItemActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_item_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // Enable application home button
//        ActionBar bar = getActionBar();
//        bar.setHomeButtonEnabled(true);
//        bar.setDisplayHomeAsUpEnabled(true);
    }
}