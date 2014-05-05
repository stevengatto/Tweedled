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
 * Created by klam on 5/4/14.
 */
public class CreateCommentTask {
    private JSONObject respond;
    private String mBody;
    private int mPollId;
    private Context mContext;

    public CreateCommentTask(Context context)
    {
        this.mContext = context;
    }

    public void submitRequest(int poll_id, String body)
    {
        this.mBody = body;
        this.mPollId = poll_id;

        CreateCommentAsyncTask createCommentAsyncTask = new CreateCommentAsyncTask(mContext);
        createCommentAsyncTask.execute(WebGeneral.generateCreatingCommentURL(poll_id));
    }

    private void onPostExecuteAction(JSONObject respond)
    {
        try {
            if (respond.getBoolean("success")) {
                Toast.makeText(mContext, respond.getString("info"),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
    }


    private class CreateCommentAsyncTask  extends UrlJsonAsyncTask {
        public CreateCommentAsyncTask(Context context) {
            super(context);
        }

        //override on pre execute so that progress dialog doesn't show up recursively
        @Override
        protected void onPreExecute() { }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            String response;
            try {
                try {
                    post.setHeader("Accept", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    respond.put("authenticity", "");
                    respond.put("auth_token", WebGeneral.getsPreferences().getString("auth_token", ""));
                    JSONObject commentObj = new JSONObject();
                    commentObj.put("body", mBody);
                    respond.put("comment", commentObj);
                    respond.put("commit", "Create Comment");
                    respond.put("action", "create");
                    respond.put("controller", "comments");
                    respond.put("poll_id", mPollId);


                    StringEntity se = new StringEntity(respond.toString());
                    post.setEntity(se);
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");
                    response = client.execute(post, responseHandler);
                    respond = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e + " " + urls[0]);
                    respond.put("info", "Failed to create comment. Retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    respond.put("info", "You must be logged in to create comment.");
                    return respond;
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
