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
import android.widget.EditText;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by klam on 3/29/14.
 * LoginFragment View
 */
public class LoginFragment extends Fragment {

    private Activity thisActivity;
    private String mUserEmail;
    private String mUserPassword;
    private SharedPreferences mPreferences;
    private static final String LOGIN_URL = "http://107.170.50.231/api/v1/sessions";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = getActivity();
        mPreferences = thisActivity.getSharedPreferences("CurrentUser", thisActivity.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final EditText userEmailField = (EditText) view.findViewById(R.id.et_userEmail);
        final EditText userPasswordField = (EditText) view.findViewById(R.id.et_userPassword);

        //set login_fragment button onclick
        Button butLogin = (Button) view.findViewById(R.id.but_loginButton);
        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserEmail = userEmailField.getText().toString();
                mUserPassword = userPasswordField.getText().toString();
                if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
                    // input fields are empty
                    Toast.makeText(thisActivity, "Please complete all the fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    LoginTask loginTask = new LoginTask(getActivity());
                    loginTask.setMessageLoading("Logging in...");
                    loginTask.execute(LOGIN_URL);

                    //switch to Dashboard fragment

                    //thisActivity.finish();
                }
            }
        });

        //set register_fragment onclick here

        return view;
    }

    private class LoginTask extends UrlJsonAsyncTask {
        public LoginTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
            String response;
            JSONObject json = new JSONObject();

            try {
                try {
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");
                    userObj.put("email", mUserEmail);
                    userObj.put("password", mUserPassword);
                    holder.put("user", userObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info",
                            "Email and/or password are invalid. Retry!");
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
                    editor.putString("AuthToken", json.getJSONObject("data")
                            .getString("auth_token"));
                    editor.commit();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    DashboardFragment dashboardFragmentFragment = new DashboardFragment();
                    ft.replace(R.id.login_fragment, dashboardFragmentFragment);
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