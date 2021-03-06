package moms.app.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.outdated.PostPollActivity;
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
 */
public class RegisterFragment extends Fragment {

    private Activity mActivity;
    private View mlayout;

    private String mUserEmail;
    private String mUserPassword;
    private SharedPreferences mPreferences;
    private static final String REG_URL = "http://107.170.50.231/api/v1/sessions";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mActivity = getActivity();
        mlayout = inflater.inflate(R.layout.register_fragment, container, false);

        return mlayout;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mPreferences = mActivity.getSharedPreferences("CurrentUser", mActivity.MODE_PRIVATE);
    }

    public void registerNewAccount(View registerButton) {
        EditText userEmailField = (EditText) mActivity.findViewById(R.id.et_userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) mActivity.findViewById(R.id.et_userPassword);
        mUserPassword = userPasswordField.getText().toString();
        if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
            // input fields are empty
            Toast.makeText(mActivity, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
        } else {
            RegisterTask regTask = new RegisterTask(mActivity);
            regTask.setMessageLoading("Registering...");
            regTask.execute(REG_URL);
        }
    }


    private class RegisterTask extends UrlJsonAsyncTask {
        public RegisterTask(Context context) {
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

                    mActivity.finish();
                    Intent intent = new Intent(mActivity,
                            PostPollActivity.class);
                    startActivity(intent);
                    mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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