package moms.app.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by klam on 3/29/14.
 */
public class DashBoard extends Activity {
    private SharedPreferences mPreferences;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        TextView mAuth = (TextView)findViewById(R.id.auth);
        String auth_token = mPreferences.getString("AuthToken","");
        mAuth.setText(auth_token);

    }
}