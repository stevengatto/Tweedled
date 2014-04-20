package moms.app.android.communication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.savagelook.android.UrlJsonAsyncTask;
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
 * Created by Steve on 4/19/14.
 */
public class CreatePollTask extends UrlJsonAsyncTask {

    private String mQuestion_str;
    private String mTitle1_str;
    private String mTitle2_str;
    private String encodedImage1;
    private String encodedImage2;

    public CreatePollTask(Context context, String mQuestion_str, String mTitle1_str,
                          String mTitle2_str, String encodedImage1, String encodedImage2) {
        super(context);

        this.mQuestion_str = mQuestion_str;
        this.mTitle1_str = mTitle1_str;
        this.mTitle2_str = mTitle2_str;
        this.encodedImage1 = encodedImage1;
        this.encodedImage2 = encodedImage2;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urls[0]);
        JSONObject pollObj = new JSONObject();
        String response;
        JSONObject json = new JSONObject();
        try {
            try {
                json.put("utf8", "\u2713");
                json.put("authenticity_token", "");
                pollObj.put("question", mQuestion_str);
                pollObj.put("title_one", mTitle1_str);
                pollObj.put("title_two", mTitle2_str);
                pollObj.put("auth_token", WebGeneral.getsPreferences().getString("auth_token", ""));
                pollObj.put("encode1", encodedImage1);
                pollObj.put("encode2", encodedImage2);

                json.put("poll",pollObj);

                StringEntity se = new StringEntity(json.toString());
                post.setEntity(se);
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Accept", "application/json");

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
                Toast.makeText(context, json.getString("info"),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        } finally {
            super.onPostExecute(json);
        }
    }

}
