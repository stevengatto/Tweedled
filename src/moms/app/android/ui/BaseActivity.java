package moms.app.android.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import moms.app.android.R;

/**
 * Created by Steve on 4/3/14.
 */
public class BaseActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //enable indefinite progress bar in action bar (can be turned on and off)
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
//        setProgressBarIndeterminateVisibility(false);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));
        actionBar.setLogo(R.drawable.action_bar_logo);
        actionBar.setTitle("");
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