package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.ui.TabsActivity;
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
 * Created by klam on 4/16/14.
 * login task
 */
public class LoginTask implements TaskInterface{
    private String mUserEmail;
    private String mUserPassword;
    private Activity mActivity;
    private JSONObject respond = new JSONObject();

    public LoginTask(Activity activity){
        mActivity = activity;
        WebGeneral.setsPreferences(mActivity.getSharedPreferences("CurrentUser", Activity.MODE_PRIVATE));
    }

    public void submitRequest(String email, String password)
    {
        mUserEmail = email;
        mUserPassword = password;

        LoginAsyncTask loginTask = new LoginAsyncTask(mActivity);
        loginTask.setMessageLoading("Logging in...");
        loginTask.execute(WebGeneral.LOGIN_URL);
    }

    public void onPostExecuteAction(JSONObject respond) {
        try {
            if (respond.getBoolean("success")) {
                SharedPreferences.Editor editor = WebGeneral.getsPreferences().edit();
                editor.putString("auth_token", respond.getJSONObject("data").getString("auth_token"));
                editor.commit();

                Intent intent = new Intent(mActivity.getApplicationContext(), TabsActivity.class);
                mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                mActivity.startActivity(intent);
            }
            else
              Toast.makeText(mActivity, respond.getString("info"), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class LoginAsyncTask extends UrlJsonAsyncTask {
        public LoginAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
            String response;
            respond = new JSONObject();


            try {
                try {
                    respond.put("success", false);
                    respond.put("info", "Something went wrong. Retry!");
                    userObj.put("email", mUserEmail);
                    userObj.put("password", mUserPassword);
                    holder.put("user", userObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    respond = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    respond.put("info",
                            "Email and/or password are invalid. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return respond;
        }

        @Override
        protected void onPostExecute(JSONObject respond) {
                onPostExecuteAction(respond);
                super.onPostExecute(respond);

        }

    }
}
