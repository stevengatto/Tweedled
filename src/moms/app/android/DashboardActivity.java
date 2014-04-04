package moms.app.android;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Steve on 4/3/14.
 */
public class DashboardActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        // Enable application home button
//        ActionBar bar = getActionBar();
//        bar.setHomeButtonEnabled(true);
//        bar.setDisplayHomeAsUpEnabled(true);
    }
}