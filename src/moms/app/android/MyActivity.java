package moms.app.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button changeButton = (Button)findViewById(R.id.but_changeview);
        TextView textView = (TextView) findViewById(R.id.tvText);


    }

        public void login(View button) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }
