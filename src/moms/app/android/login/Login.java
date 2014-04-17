package moms.app.android.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import moms.app.android.R;
import moms.app.android.communication.LoginTask;
import moms.app.android.ui.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by klam on 4/8/14.
 */
public class Login extends BaseActivity {
    private String mUserEmail;
    private String mUserPassword;
    private static SharedPreferences mPreferences;

    //private static final String LOGIN_URL = "http://10.0.0.18/api/v1/sessions";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

    }
    public static SharedPreferences getSharedPreferences()
    {
        return mPreferences;
    }
    public void login(View loginButton) {
        EditText userEmailField = (EditText) findViewById(R.id.et_userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.et_userPassword);
        mUserPassword = userPasswordField.getText().toString();
        if (mUserEmail.length() == 0 || mUserPassword.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
        } else {
            LoginTask loginTask = new LoginTask(this);
            loginTask.submitRequest(mUserEmail, mUserPassword);
        }
    }


//    private class LoginTask extends UrlJsonAsyncTask {
//        public LoginTask(Context context) {
//            super(context);
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... urls) {
//            DefaultHttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost(urls[0]);
//            JSONObject holder = new JSONObject();
//            JSONObject userObj = new JSONObject();
//            String response;
//            JSONObject json = new JSONObject();
//
//            try {
//                try {
//                    json.put("success", false);
//                    json.put("info", "Something went wrong. Retry!");
//                    userObj.put("email", mUserEmail);
//                    userObj.put("password", mUserPassword);
//                    holder.put("user", userObj);
//                    StringEntity se = new StringEntity(holder.toString());
//                    post.setEntity(se);
//                    post.setHeader("Accept", "application/json");
//                    post.setHeader("Content-Type", "application/json");
//
//                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                    response = client.execute(post, responseHandler);
//                    json = new JSONObject(response);
//
//                } catch (HttpResponseException e) {
//                    e.printStackTrace();
//                    Log.e("ClientProtocol", "" + e);
//                    json.put("info",
//                            "Email and/or password are invalid. Retry!");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.e("IO", "" + e);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.e("JSON", "" + e);
//            }
//
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject json) {
//            try {
//                if (json.getBoolean("success")) {
//                    SharedPreferences.Editor editor = mPreferences.edit();
//                    editor.putString("AuthToken", json.getJSONObject("data")
//                            .getString("auth_token"));
//                    editor.commit();
//
//                    finish();
//                    Intent intent = new Intent(getApplicationContext(),
//                            moms.app.android.ui.HomeActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                }
//                Toast.makeText(context, json.getString("info"),
//                        Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
//                        .show();
//            } finally {
//                super.onPostExecute(json);
//            }
//        }
//
//    }
}