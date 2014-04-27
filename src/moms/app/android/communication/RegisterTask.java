package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.ui.HomeActivity;
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
 * Created by klam on 4/23/14.
 */
public class RegisterTask implements TaskInterface{
    private String mUserEmail;
    private String mUserPassword;
    private Activity mActivity;
    public RegisterTask(Activity activity)
    {
        this.mActivity = activity;
        WebGeneral.setsPreferences(mActivity.getSharedPreferences("CurrentUser", Activity.MODE_PRIVATE));

    }
    public void onPostExecuteAction(JSONObject respond)
    {

    }

    public void submitRequest(String email, String password)
    {
        mUserEmail = email;
        mUserPassword = password;
        RegisterAsyncTask registerTask = new RegisterAsyncTask(mActivity);
        registerTask.setMessageLoading("Registering new account...");
        registerTask.execute(WebGeneral.REGISTER_URL);
    }
    private class RegisterAsyncTask extends UrlJsonAsyncTask {

        public RegisterAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    json.put("success", false);
                    json.put("info", "Failed to create account. Retry!");
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
                    SharedPreferences.Editor editor = WebGeneral.getsPreferences().edit();
                    editor.putString("auth_token", json.getJSONObject("data")
                            .getString("auth_token"));
                    editor.commit();

                    Intent intent = new Intent(mActivity.getApplicationContext(),
                            HomeActivity.class);
                    mActivity.startActivity(intent);
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
