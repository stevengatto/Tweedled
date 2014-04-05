package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by klam on 3/29/14.
 * DashboardFragment View
 */
public class DashboardFragment extends Fragment {

    private Activity thisActivity;
    private SharedPreferences mPreferences;
    private String mAuth_token = null;
    final String LOGOUT_URL = "http://107.170.50.231/api/v1/sessions/?auth_token=";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = getActivity();
        mPreferences = thisActivity.getSharedPreferences("CurrentUser", thisActivity.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        TextView mAuth = (TextView)view.findViewById(R.id.auth);
        mAuth_token = mPreferences.getString("AuthToken", "");
        mAuth.setText(mAuth_token);

        //set logout button onclick
        Button butLogout = (Button) view.findViewById(R.id.but_logout);
        butLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutTask loginTask = new LogoutTask(thisActivity);
                loginTask.setMessageLoading("Logging out...");
                loginTask.execute(LOGOUT_URL + mAuth_token);
            }
        });

        return view;
    }

    private class LogoutTask extends UrlJsonAsyncTask {
        public LogoutTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpDelete delete = new HttpDelete(urls[0]);
            String response;
            JSONObject json = new JSONObject();

            try {
                try {
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    delete.setHeader("Accept", "application/json");
                    delete.setHeader("Content-Type", "application/json");
                    response = client.execute(delete, responseHandler);
                    json = new JSONObject(response);
                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info",
                            "Logout Failed!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("logout_info", json.getString("info"));
                    editor.commit();

                    //thisActivity.finish();

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    LoginFragment loginFragmentFragment = new LoginFragment();
                    ft.replace(R.id.dashboard_fragment, loginFragmentFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                Toast.makeText(context, json.getString("info"),
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                        .show();
            } finally {
                super.onPostExecute(json);
            }
        }

    }
}