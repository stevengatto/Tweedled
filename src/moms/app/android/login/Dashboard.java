package moms.app.android.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
import moms.app.android.R;
import moms.app.android.ui.BaseActivity;
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
public class Dashboard extends BaseActivity {
    private SharedPreferences mPreferences;
    private String mAuth_token = null;
    final String LOGOUT_URL = "http://localhost/api/v1/sessions/?auth_token=";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        TextView mAuth = (TextView)findViewById(R.id.auth);
        mAuth_token = mPreferences.getString("AuthToken", "");
        mAuth.setText(mAuth_token);




    }

    public void logout(View logoutButton)
    {
        LogoutTask loginTask = new LogoutTask(this);
        loginTask.setMessageLoading("Logging out...");
        loginTask.execute(LOGOUT_URL + mAuth_token);
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

                    finish();

                    Intent intent = new Intent(getApplicationContext(),
                            Login.class);
                    startActivity(intent);
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