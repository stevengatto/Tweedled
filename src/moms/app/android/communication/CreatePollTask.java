package moms.app.android.communication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
 * Created by klam on 4/22/14.
 * createPollTask
 */
public class CreatePollTask implements TaskInterface{
    private JSONObject respond;
    private String mQuestion;
    private String mTitle1;
    private String mTitle2;
    private String mAuth_token;
    private String mEncodedImage1;
    private String mEncodedImage2;
    private Activity mActivity;
    String picture1Url;
    String picture2Url;
    boolean isPicture1Url;
    boolean isPicture2Url;
    String mDescription;
    Context mContext;

    public CreatePollTask(Activity activity)
    {
        this.mActivity = activity;

    }

    public void submitRequest(Context context, String question, String title1, String title2, String auth_token
            , String encodedImage1, String encodedImage2, boolean isPicture1Url,boolean isPicture2Url, String picutre1Url, String picture2Url, String descrition)
    {
        this.mContext = context;
        this.mQuestion = question;
        this.mTitle1 = title1;
        this.mTitle2 = title2;
        this.mAuth_token = auth_token;
        this.mEncodedImage1 = encodedImage1;
        this.mEncodedImage2 = encodedImage2;
        this.isPicture1Url = isPicture1Url;
        this.isPicture2Url = isPicture2Url;
        this.picture1Url = picutre1Url;
        this.picture2Url = picture2Url;
        this.mDescription = descrition;
        CreatePollAsyncTask pollTask = new CreatePollAsyncTask(mActivity);
        pollTask.setMessageLoading("Creating poll...");
        pollTask.execute(WebGeneral.CREATING_NEW_POLL_URL);

    }

    public void onPostExecuteAction(JSONObject respond){
        try {
            if (respond.getBoolean("success")) {
                Toast.makeText(mActivity, respond.getString("info"),
                        Toast.LENGTH_LONG).show();
                mContext.startActivity(new Intent(mActivity, TabsActivity.class));
                mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        } catch (Exception e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private class CreatePollAsyncTask extends UrlJsonAsyncTask {
        public CreatePollAsyncTask(Context context) {
            super(context);
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
                    pollObj.put("question", mQuestion);
                    pollObj.put("title_one", mTitle1);
                    pollObj.put("title_two", mTitle2);
                    pollObj.put("auth_token", mAuth_token);
                    pollObj.put("encode1", mEncodedImage1);
                    pollObj.put("encode2", mEncodedImage2);
                    pollObj.put("is_picture1_url", isPicture1Url);
                    pollObj.put("is_picture2_url", isPicture2Url);
                    pollObj.put("picture1_url", picture1Url);
                    pollObj.put("picture2_url", picture2Url);
                    pollObj.put("description", mDescription);
                    json.put("poll",pollObj);

                    StringEntity se = new StringEntity(json.toString());
                    post.setEntity(se);
                    post.setHeader("Content-Type", "application/json");
                    post.setHeader("Accept", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    respond = new JSONObject(response);

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

            return respond;
        }

        @Override
        protected void onPostExecute(JSONObject respond) {
                onPostExecuteAction(respond);
                super.onPostExecute(respond);
        }

    }
}
