package moms.app.android.ui;

import android.os.Bundle;
import moms.app.android.R;

/**
 * Created by Steve on 4/3/14.
 */
public class DashboardActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        // Enable application home button
//        ActionBar bar = getActionBar();
//        bar.setHomeButtonEnabled(true);
//        bar.setDisplayHomeAsUpEnabled(true);
    }
}